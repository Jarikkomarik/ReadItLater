package com.jarikkomarik.readitlater.model;

import org.springframework.data.annotation.Id;

import java.util.List;

public record User (String name, @Id String chatId, List<Article> articles){
}
