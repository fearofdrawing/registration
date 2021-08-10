package com.example.mysql.model.book;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Entity
@Table (name="books")
//@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Author is required")
    private String author;
    @Positive(message = "year must be positive")
    @Min(value = 1000, message = "invalid year")
    @Max(value = 2021, message = "invalid year")
    private Integer year;
    @NotNull(message = "Pages are required")
    private Integer pages;

    public Book(Long id, @NotBlank(message = "Title is required") String title, @NotBlank(message = "Author is required") String author, @Positive(message = "year must be positive") @Min(value = 1000, message = "invalid year") @Max(value = 2021, message = "invalid year") Integer year, @NotNull(message = "Pages are required") Integer pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}