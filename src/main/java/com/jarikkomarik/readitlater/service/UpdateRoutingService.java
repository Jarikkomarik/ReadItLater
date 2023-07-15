package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.configuration.Constants;
import com.jarikkomarik.readitlater.model.Article;
import com.jarikkomarik.readitlater.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashSet;
import java.util.Optional;

import static com.jarikkomarik.readitlater.configuration.Constants.*;

@Service
@Slf4j
public record UpdateRoutingService(UserService userService,
                                   ReplyService replyService,
                                   Constants constants,
                                   ReadItLaterBot readItLaterBot,
                                   UtilService utilService
) {


    public void processUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update);
            return;
        }

        if (updateIsUnsupported(update)) return;

        if (update.getMessage() == null) return;
        Optional<User> optionalUser = userService.getUser(update.getMessage().getChatId());
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            String url = utilService.getUrl(update.getMessage().getText());

            if (utilService.urlIsValid(update.getMessage().getText()) && url != null) {
                userService.addArticle(user, url);
                replyService.sendAddedUrlMessage(user.getChatId(), url);
            } else {
                replyService.sendMessage(user.getChatId(), ADD_URL_FAILURE_MESSAGE_TEXT);
            }

        } else {
            var newUser = new User(update.getMessage().getChat().getUserName(), update.getMessage().getChatId(), new HashSet<>());
            userService.saveUser(newUser);
            log.info("Created new OptionalUser: {}.", newUser);

            replyService.sendWelcomeMessage(update.getMessage().getChatId());
        }

    }

    private boolean updateIsUnsupported(Update update) {
        if (update.getMessage().getPhoto() != null) return true;
        return false;
    }

    private void handleCallback(Update update) {
        long chatId = update.getCallbackQuery().getFrom().getId();
        User user = userService.getUser(chatId).orElseThrow();

        String[] callbackSplit = update.getCallbackQuery().getData().split("#");

        User updatedUser;
        Article article;

        switch (callbackSplit[0]) {
            case CALLBACK_GET_ALL:
                replyService.sendArticles(user.getArticles(), chatId);
                break;
            case CALLBACK_MARK_READ:
                article = userService.updateStatus(user, callbackSplit[1], true);
                replyService.sendMessage(chatId, "Marked article - " + article.getUrl() + "\nas Read ✅.");
                break;
            case CALLBACK_MARK_UNREAD:
                article = userService.updateStatus(user, callbackSplit[1], false);
                replyService.sendMessage(chatId, "Marked article - " + article.getUrl() + "\nas Unread ❌.");
                break;
            case CALLBACK_DELETE_ARTICLE:
                article = userService.deleteArticle(user, callbackSplit[1]);
                replyService.sendMessage(chatId, "Removed article - " + article.getUrl() + ".");
                break;
        }

    }


}
