package org.example.taungoo.dao;

import org.example.taungoo.dto.PostDto;
import org.example.taungoo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long>, CustomDao {

    @Query("""
        SELECT new org.example.taungoo.dto.PostDto(
            p.id,
            p.title,
            p.content,
            p.featured,
            p.image,
            cast(p.createdAt as string),
            p.category.categoryName,
            p.user.username
        )
        FROM Post p
        JOIN p.category
        JOIN p.user
        ORDER BY p.createdAt DESC
    """)
    List<PostDto> findAllPost();

    @Query("""
        SELECT new org.example.taungoo.dto.PostDto(
            p.id,
            p.title,
            p.content,
            p.featured,
            p.image,
            cast(p.createdAt as string),
            p.category.categoryName,
            p.user.username
        )
        FROM Post p
        JOIN p.category
        JOIN p.user
        WHERE p.category.categoryName = :categoryName
        ORDER BY p.createdAt DESC
    """)
    List<PostDto> findAllPostByCategory(String categoryName);

    @Query("""
        SELECT new org.example.taungoo.dto.PostDto(
            p.id,
            p.title,
            p.content,
            p.featured,
            p.image,
            cast(p.createdAt as string),
            p.category.categoryName,
            p.user.username
        )
        FROM Post p
        JOIN p.category
        JOIN p.user
        WHERE p.featured = true
    """)
    List<PostDto> findByFeaturedTrue();
}
