package com.example.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.GetTokenId;
import com.example.demo.entity.Result;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-17
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Value("${server1.port1}")
    private String port;
    @Value("${server1.address1}")
    private String address;
    private final MailServiceImpl mailService;

    private final UserMapper userMapper;

    @Override
    public Result generateLinks(User user) {
        String confirmCode = UUID.randomUUID().toString();

        user.setConfirmCode(confirmCode);
        user.setIsVaild((short)0);
        user.setCreated(LocalDateTime.now());
        user.setLastModified(LocalDateTime.now());

        boolean result = save(user);
        if(result){
            String Url = "http://"+address+":"+port+"/api/auth/activation?confirmCode="+confirmCode;
            mailService.sendMailForActivationAccount(Url, user.getEmail());
            return Result.success();
        }
        return Result.error("发送失败");
    }

    @Override
    public Result activationAccount(String confirmCode) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("confirm_code",confirmCode);
        User user = getOne(wrapper);
        LocalDateTime ldt = LocalDateTime.now().minusDays(1);
        boolean after = ldt.isAfter(user.getCreated());
        if(!after) {
            user.setIsVaild((short)1);
            int result = userMapper.update(user,wrapper);
            if (result > 0) {
                return Result.success();
            }
        }
        return Result.error("激活失败");
    }

    @Override
    public Result login(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username",user.getUsername())
                .eq("password",user.getPassword());
        User user1 = getOne(wrapper);
        if (user1 != null & user1.getIsVaild() == 1){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",user1.getUserId());
            claims.put("username",user1.getUsername());
            claims.put("name",user1.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误");
    }

    @Override
    public Result userInf() {
        Integer id = GetTokenId.getUserId();
        User user = getById(id);
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user,userDTO);
        return Result.success(userDTO);
    }
}
