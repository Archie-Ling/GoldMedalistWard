package org.example.service;

import org.example.entity.Manager;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ManagerService extends IService<Manager> {

    String login(String phone, String pwd);
}
