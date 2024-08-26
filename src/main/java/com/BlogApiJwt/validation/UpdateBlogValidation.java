package com.BlogApiJwt.validation;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBlogValidation {

    @NotNull(message = "id is NULL")
    @NotEmpty(message = "id not be empty")
    private int id;

    private String title;

    private String content;

}
