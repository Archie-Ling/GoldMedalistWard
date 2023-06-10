package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Department;
import org.example.service.DepartmentService;
import org.example.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




