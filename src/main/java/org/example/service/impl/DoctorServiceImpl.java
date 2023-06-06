package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Doctor;
import org.example.service.DoctorService;
import org.example.mapper.DoctorMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【doctor】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, Doctor>
    implements DoctorService{

}




