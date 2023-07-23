package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.configuration.Constants;
import com.jarikkomarik.readitlater.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Set;

import static com.jarikkomarik.readitlater.configuration.Constants.*;

@Service
@Slf4j
public record ReplyService(ReadItLaterBot readItLaterBot, Constants constants) {

    public void sendWelcomeMessage(long chatId) {
        try {
            readItLaterBot.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(WELCOME_MESSAGE_TEXT)
                            .build()
            );
            log.info("Welcome message sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error during message execution: {}, with chatId: {}", e.getMessage(), chatId);
            sendErrorMessage(chatId);
        }
    }

    public void sendMessage(long chatId, String message) {
        try {
            readItLaterBot.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(message)
                            .build()
            );
            log.info("Message sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error during message execution: {}, with chatId: {}", e.getMessage(), chatId);
            sendErrorMessage(chatId);
        }
    }

    public void sendAddedUrlMessage(long chatId, String url) {
        try {
            var button = new InlineKeyboardButton();
            button.setText(GET_ALL_ARTICLES_MESSAGE_TEXT);
            button.setCallbackData(CALLBACK_GET_ALL + "#");
            readItLaterBot.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(ADDED_URL_MESSAGE_TEXT + url + ".")
                            .replyMarkup(new InlineKeyboardMarkup(List.of(List.of(button))))
                            .build()
            );
            log.info("Added URL message sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error during message execution: {}, with chatId: {}", e.getMessage(), chatId);
            sendErrorMessage(chatId);
        }
    }

    public void sendArticles(Set<Article> articles, long chatId) {
        if(articles.isEmpty()) {
            sendMessage(chatId, "There are no articles :(");
        }
        articles.forEach(article -> {
            String text = "Article url - " + article.getUrl() + "\nStatus - You " + (article.isRead() ? "have read " : "haven't read ") + "this article.";
            sendArticle(article, chatId, text);
        });
    }

    public void sendArticle(Article article, long chatId, String text) {
        var removeButton = new InlineKeyboardButton();
        removeButton.setText("\uD83D\uDDD1");
        removeButton.setCallbackData(CALLBACK_DELETE_ARTICLE + "#" + article.getCreationTime());

        var readStatusButton = new InlineKeyboardButton();
        readStatusButton.setText(article.isRead() ? "Mark as unread ❌" : "Mark as read ✅");
        readStatusButton.setCallbackData((article.isRead() ? CALLBACK_MARK_UNREAD : CALLBACK_MARK_READ) + "#" + article.getCreationTime());

        try {
            readItLaterBot.execute(
                    SendMessage.builder()
                            .chatId(chatId)
                            .text(text)
                            .replyMarkup(new InlineKeyboardMarkup(List.of(List.of(readStatusButton, removeButton))))
                            .build()
            );
            log.info("Article sent to chatId: {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error during message execution: {}, with chatId: {}", e.getMessage(), chatId);
            sendErrorMessage(chatId);
        }
    }

    private void sendErrorMessage(long chatId) {
        sendMessage(chatId, "Execution failed, please report to ufland.ufland@gmail.com");
    }
}
