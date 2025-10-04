package org.example.taungoo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post extends IdClass {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private boolean featured = false;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    public Post(String title, String content, boolean featured, byte[] image, LocalDateTime createdAt, Category category, User user) {
        this.title = title;
        this.content = content;
        this.featured = featured;
        this.image = image;
        this.createdAt = createdAt;
        this.category = category;
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
