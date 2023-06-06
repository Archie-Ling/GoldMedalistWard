package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Dispensing;
import org.example.service.DispensingService;
import org.example.mapper.DispensingMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【dispensing】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class DispensingServiceImpl extends ServiceImpl<DispensingMapper, Dispensing>
    implements DispensingService{

}




