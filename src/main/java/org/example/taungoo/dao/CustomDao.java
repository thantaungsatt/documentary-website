package org.example.taungoo.dao;

import org.apache.catalina.LifecycleState;
import org.example.taungoo.dto.PostDto;
import org.example.taungoo.entity.Post;

import java.util.List;

public interface CustomDao {
    List<Post> findRecentPost();
}
