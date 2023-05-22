package com.springboot.repository;

import com.springboot.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
@Repository is not required because JpaRepository extends SimpleJpaRepository
which has @Repository + @Transactional(readOnly=true)
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
}
