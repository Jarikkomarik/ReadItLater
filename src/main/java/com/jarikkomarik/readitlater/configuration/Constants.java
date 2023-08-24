package com.jarikkomarik.readitlater.configuration;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String WELCOME_MESSAGE_TEXT = "\uD83D\uDC4B Welcome to the Read It Later bot!\nSend the articles you want to read later, and I'll take care of the rest.";
    public static final String ADDED_URL_MESSAGE_TEXT = "Added following article:\n";

    public static final String GET_ALL_ARTICLES_MESSAGE_TEXT = "\uD83D\uDD0D Show all articles ";

    public static final String ADD_URL_FAILURE_MESSAGE_TEXT = "❌ Oops!\nPlease make sure you're sending a valid URL. \uD83D\uDD17";

    public static final String SEND_DAILY_READ_TEXT = "\uD83C\uDF1F Enjoy your daily read! Today's article is - ";

    public static final String CALLBACK_GET_ALL = "GET_ALL";

    public static final String CALLBACK_DELETE_ARTICLE = "DELETE_ARTICLE";

    public static final String CALLBACK_MARK_READ = "MARK_READ";

    public static final String CALLBACK_MARK_UNREAD = "MARK_UNREAD";
}
