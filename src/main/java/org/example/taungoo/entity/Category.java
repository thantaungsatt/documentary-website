package org.example.taungoo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends IdClass{
    private String categoryName;
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post){
        post.setCategory(this);
        posts.add(post);
    }

    public Category(String categoryName, String description) {
        this.categoryName = categoryName;
        this.description = description;
    }
}


