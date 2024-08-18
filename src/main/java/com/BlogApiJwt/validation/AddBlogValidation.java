package com.BlogApiJwt.validation;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddBlogValidation {

    @NotNull(message = "title is NULL")
    @NotEmpty(message = "title not be empty")
    private String title;

    @NotNull(message = "content is NULL")
    @NotEmpty(message = "content not be empty")
    private String content;



}
