package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.model.Article;
import com.jarikkomarik.readitlater.model.User;
import com.jarikkomarik.readitlater.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import static com.jarikkomarik.readitlater.configuration.Constants.SEND_DAILY_READ_TEXT;

@Service
public record NotificationService (UserRepository userRepository, ReplyService replyService) {
    @Scheduled(cron = "0 30 14 * * MON-SAT")
    public void sendNotifications() {
        userRepository.findAll().forEach(this::sendRandomArticle);
    }

    private void sendRandomArticle(User user) {
        var article = getRandomArticle(user);
        replyService.sendArticle(article, user.getChatId(), SEND_DAILY_READ_TEXT + article.getUrl());
    }

    private Article getRandomArticle(User user) {
      return user.getArticles().stream().filter(article -> article.isRead() == false).findAny().orElseThrow();
    }
}
