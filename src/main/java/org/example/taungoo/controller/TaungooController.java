package org.example.taungoo.controller;

import org.example.taungoo.dto.CategoryDto;
import org.example.taungoo.dto.PostDto;
import org.example.taungoo.dto.PostRequestDto;
import org.example.taungoo.entity.Post;
import org.example.taungoo.service.TaungooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/taungoo")
public class TaungooController {

    @Autowired
    private TaungooService taungooService;

    @GetMapping("/list/categories")
    public List<CategoryDto>listAllCategories(){
        return taungooService.getAllCategories();
    }

    @PostMapping("/create-category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(taungooService.createCategory(categoryDto));
    }

    @PutMapping("/update-category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable long id, @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(taungooService.updateCategory(id, categoryDto));
    }

    @GetMapping("/list/all-posts")
    public List<PostDto> listAllPosts(){
        return taungooService.getAllPosts();
    }

    @GetMapping("/list/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = taungooService.getPostById(id);
        return ResponseEntity.ok(post);
    }
    
    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "featured") boolean featured,
            @RequestParam(name = "categoryName") String categoryName,
            @RequestParam(value = "image", required = false) MultipartFile image,
            Authentication authentication) throws IOException {
        // Extract username from authenticated user
        String username = authentication.getName();
        String response = taungooService.createPost(title, content, featured, image, categoryName, username);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-post/{id}")
    public ResponseEntity<String> updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam boolean featured,
            @RequestParam String categoryName,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        String message = taungooService.updatePost(id, title, content, featured, categoryName, image);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        taungooService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/list/all-posts/{categoryName}")
    public List<PostDto> listAllPostsByCategory(@PathVariable String categoryName){
        return taungooService.findAllPostByCategory(categoryName);
    }

    @GetMapping("/list/featured-posts")
    public List<PostDto> getFeaturedPosts() {
        return taungooService.findFeaturedPost();
    }

    @GetMapping("/list/recent-posts")
    public List<PostDto> listRecentPost(){
        return taungooService.findRecentPost()
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    public PostDto toPostDto(Post post) {
        String imageBase64 = post.getImage() != null
                ? Base64.getEncoder().encodeToString(post.getImage())
                : null;

        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.isFeatured(),
                imageBase64, // ✅ use Base64 string here
                post.getCreatedAt().toString(),
                post.getCategory().getCategoryName(),
                post.getUser().getUsername()
        );
    }


}
