package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Manager;
import org.example.handler.exceptionhandler.JavaWebException;
import org.example.service.ManagerService;
import org.example.mapper.ManagerMapper;
import org.example.utils.Md5Utils;
import org.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【manager】的数据库操作Service实现
* @createDate 2023-02-23 20:40:59
*/
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, Manager>
    implements ManagerService{

    @Override
    public String login(String phone, String pwd) {
        //只需要使用用户输入的原密码再次进行加密和服务器加密后的密码进行对比 如果一致就是正确的

        //查找用户的盐
        QueryWrapper<Manager> managerQueryWrapper = new QueryWrapper<>();
        managerQueryWrapper.eq("phone",phone);
        Manager manager=baseMapper.selectOne(managerQueryWrapper);

        if(manager==null){
            throw new JavaWebException(20001,"用户名或密码错误!");
        }

        //再次进行加密
        String md5Password = Md5Utils.md5Password(pwd, manager.getSalt());


        //对比两次密码是否一致
        if(!md5Password.equals(manager.getPwd())){
            throw new JavaWebException(20001,"用户名或密码错误!");
        }

        //登录成功，生成jwt令牌，返回给前端
        String token = TokenUtils.token(manager.getId(),manager.getPhone());

        return token;
    }
}




