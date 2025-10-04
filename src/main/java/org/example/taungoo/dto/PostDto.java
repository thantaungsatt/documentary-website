package org.example.taungoo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Base64;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostDto {
    private long postId;
    private String title;
    private String content;
    private boolean featured;
    private String imageBase64;
    private String createdAt;
    private String category;
    private String username;

    public PostDto(long postId, String title, String content, boolean featured, byte[] imageBase64, String createdAt, String category, String username) {
        super();
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.featured = featured;
        this.imageBase64 = imageBase64 !=null ? Base64.getEncoder()
                .encodeToString(imageBase64) : null;
        this.createdAt = createdAt;
        this.category = category;
        this.username = username;
    }
}
