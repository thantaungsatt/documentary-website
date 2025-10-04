package org.example.taungoo.dto;

import org.springframework.web.multipart.MultipartFile;

public record PostRequestDto(
        String title,
        String content,
        Boolean featured,
        String image,
        Long categoryId
) {}



