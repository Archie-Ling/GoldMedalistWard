package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Doctor;
import org.example.service.DoctorService;
import org.example.mapper.DoctorMapper;
import org.springframework.stereotype.Service;


@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
    implements DoctorService{

}




