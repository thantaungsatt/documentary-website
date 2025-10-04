package org.example.taungoo.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.taungoo.dao.CategoryDao;
import org.example.taungoo.dao.PostDao;
import org.example.taungoo.dao.UserDao;
import org.example.taungoo.dto.CategoryDto;
import org.example.taungoo.dto.PostDto;
import org.example.taungoo.entity.Category;
import org.example.taungoo.entity.Post;
import org.example.taungoo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaungooService {

    @Autowired
    private  CategoryDao categoryDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    public List<CategoryDto> getAllCategories() {
        return categoryDao.findAll().stream()
                .map(this::toCategoryDto)
                .toList();
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.categoryName());
        category.setDescription(categoryDto.description());
        return toCategoryDto(categoryDao.save(category));
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        category.setCategoryName(categoryDto.categoryName());
        category.setDescription(categoryDto.description());
        
        return toCategoryDto(categoryDao.save(category));
    }

    @Transactional
    public String createPost(
            String title,
            String content,
            boolean featured,
            MultipartFile image,
            String categoryName,
            String username
    )throws IOException{
        Category category = categoryDao.findByCategoryName(categoryName).orElse(null);
        if (!Objects.nonNull(category)) {
            category = new Category();
            category.setCategoryName(categoryName);
            categoryDao.save(category);
        }
        User user = userDao.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setFeatured(featured);
        post.setCategory(category);
        post.setUser(user);
        if (Objects.nonNull(image)) {
            post.setImage(image.getBytes());
        }
        category.addPost(post);
        postDao.save(post);
        return "Post created successfully";
    }

    public List<PostDto> getAllPosts() {
        return postDao.findAllPost();
    }

    public List<PostDto> findAllPostByCategory(String categoryName) {
        return postDao.findAllPostByCategory(categoryName);
    }

    public List<Post> findRecentPost() {
        return postDao.findRecentPost();
    }

    private CategoryDto toCategoryDto(Category category) {
        if (category == null) {
            return null;
        }
        return new CategoryDto(
            category.getId(), 
            category.getCategoryName(),
            category.getDescription()
        );
    }

}
