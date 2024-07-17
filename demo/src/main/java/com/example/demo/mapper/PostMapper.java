package com.example.demo.mapper;

import com.example.demo.entity.Post;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

}
