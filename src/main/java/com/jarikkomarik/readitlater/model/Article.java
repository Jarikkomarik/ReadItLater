package com.jarikkomarik.readitlater.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    String url;
    boolean isRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(url, article.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
