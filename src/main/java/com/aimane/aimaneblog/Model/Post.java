package com.aimane.aimaneblog.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    private User user;  // The user who created the post

    // Adding a createdDate field
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] postImg;

    // Default constructor
    public Post(String title, String content, User user, LocalDateTime now) {}

    // Constructor with parameters
    public Post(String title, String content, User user, LocalDateTime createdDate, String postImg) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
        this.postImg = Base64.getDecoder().decode(postImg);

    }
    public String getPostImageBase64() {
        return postImg != null ? Base64.getEncoder().encodeToString(postImg) : null;
    }


}
