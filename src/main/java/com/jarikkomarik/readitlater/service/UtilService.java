package com.jarikkomarik.readitlater.service;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UtilService {
    private static final String REGEX = "(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?\\/[a-zA-Z0-9]{2,}|((https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?)|(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})?";
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
