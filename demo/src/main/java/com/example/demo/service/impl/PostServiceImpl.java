package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.config.GetTokenId;
import com.example.demo.entity.Post;
import com.example.demo.entity.Result;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.IPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Override
    public Result posts(Post post) {
        Integer userId = GetTokenId.getUserId();
        post.setUserId(userId);
        post.setCreated(LocalDateTime.now());
        post.setLastModified(LocalDateTime.now());
        boolean result = save(post);
        System.out.println(post);
        if (result){
            return Result.success();
        }
        return Result.error("创建文章失败");
    }

    @Override
    public Result getPosts(Integer userId) {
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>()
                .eq("user_id",userId)
                .orderByDesc("created");
        Page<Post> page = new Page();
        Page<Post> postPage = page(page, wrapper);
        return Result.success(postPage);
    }

    @Override
    public Result getPost(Integer postId) {
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>()
                .eq("post_id",postId);
        Post post = getOne(wrapper);
        return Result.success(post);
    }

    @Override
    public Result updatePost(Integer postId) {
        Integer userId = GetTokenId.getUserId();
        UpdateWrapper<Post> wrapper = new UpdateWrapper<Post>()
                .eq("user_id",userId)
                .eq("post_id",postId)
                .set("last_modified",LocalDateTime.now());
        boolean update = update(wrapper);
        if (update){
            return Result.success();
        }
        return Result.error("保存失败");
    }

    @Override
    public Result deletePost(Integer postId) {
        Integer userId = GetTokenId.getUserId();
        QueryWrapper<Post> wrapper = new QueryWrapper<Post>()
                .eq("user_id",userId)
                .eq("post_id",postId);
        boolean remove = remove(wrapper);
        if (remove){
            return Result.success();
        }
        return Result.error("删除失败");
    }
}
