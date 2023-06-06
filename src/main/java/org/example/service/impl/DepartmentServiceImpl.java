package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Department;
import org.example.service.DepartmentService;
import org.example.mapper.DepartmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【department】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService{

}




