package com.jarikkomarik.readitlater.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtilService {
    private static final String REGEX = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&\\/\\/=]*)";
    public boolean urlIsValid(String url) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

    public String getUrl(String url) {
        try {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(url);
            matcher.find();
            return matcher.group();
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
