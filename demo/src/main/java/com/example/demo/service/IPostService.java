package com.example.demo.service;

import com.example.demo.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
public interface IPostService extends IService<Post> {

    Result posts(Post post);

    Result getPosts(Integer userId);

    Result getPost(Integer postId);

    Result updatePost(Integer postId);

    Result deletePost(Integer postId);
}
