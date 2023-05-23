package com.springboot.service.impl;

import com.springboot.entity.Category;
import com.springboot.entity.Post;
import com.springboot.exception.ResourceNotFoundException;
import com.springboot.payload.PostDto;
import com.springboot.payload.PostDtoV2;
import com.springboot.payload.PostResponse;
import com.springboot.repository.CategoryRepository;
import com.springboot.repository.PostRepository;
import com.springboot.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PostServiceImpl implements PostService {
    @Autowired
    ModelMapper modelMapper;
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        // getting content from page
        List<Post> postList = posts.getContent();
        List<PostDto> content = postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtoList(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDtoV2 getPostByIdV2(long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        PostDtoV2 postDtoV2 = modelMapper.map(post, PostDtoV2.class);
        List<String> tags = new ArrayList<>();
        tags.add("Java"); tags.add("Python"); tags.add("C++");
        postDtoV2.setTags(tags);
        return postDtoV2;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        return modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(category.getId());
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();
    }
}
