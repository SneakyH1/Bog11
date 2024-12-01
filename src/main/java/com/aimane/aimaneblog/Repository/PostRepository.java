package com.aimane.aimaneblog.Repository;

import com.aimane.aimaneblog.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // Update the method to use 'createdDate' instead of 'date'
    List<Post> findTop5ByOrderByCreatedDateDesc();
}

