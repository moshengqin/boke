package com.example.demo.controller;


import com.example.demo.entity.Post;
import com.example.demo.entity.Result;
import com.example.demo.service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class PostController {

    private final IPostService postService;

    @PostMapping
    public Result posts(@RequestBody Post post) {
        log.info("创建文章{}", post);
        return postService.posts(post);
    }

    @GetMapping
    public Result getPosts(Integer uid){
        log.info("获得用户{}的文章",uid);
        return postService.getPosts(uid);
    }

    @GetMapping("/{id}")
    public Result getPost(@PathVariable Integer id){
        log.info("获取文章{}的详情",id);
        return postService.getPost(id);
    }

    @PutMapping("{id}")
    public Result updatePost(@PathVariable Integer id){
        log.info("更新文章{}",id);
        return postService.updatePost(id);
    }

    @DeleteMapping("{id}")
    public Result deletePost(@PathVariable Integer id){
        log.info("删除文章{}",id);
        return postService.deletePost(id);
    }

}
