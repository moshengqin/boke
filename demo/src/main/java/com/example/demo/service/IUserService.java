package com.example.demo.service;

import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
public interface IUserService extends IService<User> {

    Result generateLinks(User user);

    Result activationAccount(String confirmCode);

    Result login(User user);

    Result userInf();
}
