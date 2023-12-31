package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.configuration.Constants;
import com.jarikkomarik.readitlater.model.Article;
import com.jarikkomarik.readitlater.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;

import static com.jarikkomarik.readitlater.configuration.Constants.*;

@Service
@Slf4j
public record UpdateRoutingService(UserService userService,
                                   ReplyService replyService,
                                   Constants constants,
                                   ReadItLaterBot readItLaterBot,
                                   UtilService utilService) {


    public void processUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update);
            return;
        }

        if (updateIsUnsupported(update)) return;


        userService.getUser(update.getMessage().getChatId())
            .subscribe(optionalUser -> {
                if (optionalUser.isPresent()) {
                    var user = optionalUser.get();
                    String url = utilService.getUrl(update.getMessage().getText());

                    if (utilService.urlIsValid(update.getMessage().getText()) && url != null) {
                        userService.addArticle(user, url);
                        userService.saveUser(user).subscribe(savedUser -> {
                            log.info("added article to user: {}.", savedUser.getChatId());
                            replyService.sendAddedUrlMessage(user.getChatId(), url);
                        });
                    } else {
                        replyService.sendMessage(user.getChatId(), ADD_URL_FAILURE_MESSAGE_TEXT);
                    }

                } else {
                    var newUser = new User(update.getMessage().getChat().getUserName(), update.getMessage().getChatId(), new HashSet<>());
                    userService.saveUser(newUser).subscribe(user -> {
                        log.info("Created new OptionalUser: {}.", newUser);
                        replyService.sendWelcomeMessage(update.getMessage().getChatId());
                    });

                }

            });


    }

    private boolean updateIsUnsupported(Update update) {
        return update.getMessage().getPhoto() != null || update.getMessage() == null;
    }

    private void handleCallback(Update update) {
        long chatId = update.getCallbackQuery().getFrom().getId();

        userService.getUser(chatId).subscribe(userOptional -> {
            User user = userOptional.orElseThrow();

            String[] callbackSplit = update.getCallbackQuery().getData().split("#");

            Article article;

            switch (callbackSplit[0]) {
                case CALLBACK_GET_ALL -> replyService.sendArticles(user.getArticles(), chatId);
                case CALLBACK_MARK_READ -> {
                    article = userService.updateStatus(user, Long.valueOf(callbackSplit[1]), true);
                    userService.saveUser(user).subscribe(savedUser -> {
                        log.info("update status for user: {}.", savedUser.getChatId());
                        replyService.sendMessage(chatId, "Marked article - " + article.getUrl() + "\nas Read ✅.");
                    });

                }
                case CALLBACK_MARK_UNREAD -> {
                    article = userService.updateStatus(user, Long.valueOf(callbackSplit[1]), false);
                    userService.saveUser(user).subscribe(savedUser -> {
                        log.info("update status for user: {}.", savedUser.getChatId());
                        replyService.sendMessage(chatId, "Marked article - " + article.getUrl() + "\nas Unread ❌.");
                    });
                }
                case CALLBACK_DELETE_ARTICLE -> {
                    article = userService.deleteArticle(user, Long.valueOf(callbackSplit[1]));
                    userService.saveUser(user).subscribe(savedUser -> {
                        log.info("removed article from user: {}.", savedUser.getChatId());
                        replyService.sendMessage(chatId, "Removed article - " + article.getUrl() + ".");
                    });
                }
            }

        });

    }


}
