package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Appointment;
import org.example.service.AppointmentService;
import org.example.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【appointment】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment>
    implements AppointmentService{

}




