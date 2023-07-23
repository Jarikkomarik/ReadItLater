package com.jarikkomarik.readitlater.service;

import com.jarikkomarik.readitlater.model.Article;
import com.jarikkomarik.readitlater.model.User;
import com.jarikkomarik.readitlater.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record UserService(UserRepository userRepository) {

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean userIsRegistered(long chatId) {
        return userRepository.existsById(chatId);
    }

    public Optional<User> getUser(long chatId) {
        return userRepository.findById(chatId);
    }

    public Article updateStatus(User user, Long creationTime, boolean b) {
        Article article = getArticleByCreationTime(creationTime, user);
        article.setRead(b);
        userRepository.save(user);

        return article;
    }

    public Article deleteArticle(User user, Long creationTime) {
        var article = getArticleByCreationTime(creationTime, user);
        user.getArticles().remove(article);
        userRepository.save(user);
        return article;
    }

    private Article getArticleByCreationTime(Long creationTime, User user) {
        return user.getArticles().stream()
                .filter(ar -> ar.getCreationTime() == creationTime)
                .findFirst().orElseThrow();
    }

    public void addArticle(User user, String url) {
        user.getArticles().add(new Article(url, false));
        userRepository.save(user);
    }
}
