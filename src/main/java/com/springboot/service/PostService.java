package com.springboot.service;

import com.springboot.payload.PostDto;
import com.springboot.payload.PostDtoV2;
import com.springboot.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto, long id);
    void deletePost(long id);
    List<PostDto> getPostByCategory(Long categoryId);
    PostDtoV2 getPostByIdV2(long id);

}
