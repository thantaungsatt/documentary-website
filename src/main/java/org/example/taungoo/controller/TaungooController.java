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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PostMapping("/create-post")
    public ResponseEntity<String> createPost(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "featured") boolean featured,
            @RequestParam(name = "category_name") String categoryName,
            @RequestParam(name = "username") String username,
            @RequestParam(value = "image")MultipartFile image) throws IOException {
        String response = taungooService.createPost(title, content, featured, image, categoryName, username);
        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/list/all-posts/{categoryName}")
    public List<PostDto> listAllPostsByCategory(@PathVariable String categoryName){
        return taungooService.findAllPostByCategory(categoryName);
    }

    @GetMapping("/list/recent-posts")
    public List<PostDto> listRecentPost(){
        return taungooService.findRecentPost()
                .stream()
                .map(this::toPostDto)
                .toList();
    }

    public PostDto toPostDto(Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.isFeatured(),
                post.getImage(),
                post.getCreatedAt().toString(),
                post.getCategory().getCategoryName(),
                post.getUser().getUsername()
        );
    }


}
