package com.jarikkomarik.readitlater.configuration;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String WELCOME_MESSAGE_TEXT = "Welcome to read it later bot. \nSend article you want to read later.";
    public static final String ADDED_URL_MESSAGE_TEXT = "Added following article: ";

    public static final String GET_ALL_ARTICLES_MESSAGE_TEXT = "Show all articles";

    public static final String ADD_URL_FAILURE_MESSAGE_TEXT = "Failed to add following article, invalid url.";

    public static final String SEND_DAILY_READ_TEXT = "Enjoy your daily read - ";

    public static final String CALLBACK_GET_ALL = "GET_ALL";

    public static final String CALLBACK_DELETE_ARTICLE = "DELETE_ARTICLE";

    public static final String CALLBACK_MARK_READ = "MARK_READ";

    public static final String CALLBACK_MARK_UNREAD = "MARK_UNREAD";
}
