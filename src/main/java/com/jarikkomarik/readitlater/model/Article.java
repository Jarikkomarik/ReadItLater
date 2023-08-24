package com.jarikkomarik.readitlater.model;

import lombok.*;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Article {
    private long creationTime = System.currentTimeMillis();
    @NonNull
    String url;
    @NonNull
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
