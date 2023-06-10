package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Appointment;
import org.example.service.AppointmentService;
import org.example.mapper.AppointmentMapper;
import org.springframework.stereotype.Service;


@Service
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment>
    implements AppointmentService{

}




