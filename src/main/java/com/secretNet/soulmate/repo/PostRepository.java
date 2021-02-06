package com.secretNet.soulmate.repo;

import com.secretNet.soulmate.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
