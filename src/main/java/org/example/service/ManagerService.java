package org.example.service;

import org.example.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 李可文
* @description 针对表【manager】的数据库操作Service
* @createDate 2023-02-23 20:40:59
*/
public interface ManagerService extends IService<Manager> {

    String login(String phone, String pwd);
}
