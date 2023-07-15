package com.jarikkomarik.readitlater.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {

    String name;
    @Id Long chatId;
    Set<Article> articles;
}
