package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Dispensing;
import org.example.service.DispensingService;
import org.example.mapper.DispensingMapper;
import org.springframework.stereotype.Service;


@Service
public class DispensingServiceImpl extends ServiceImpl<DispensingMapper, Dispensing>
    implements DispensingService{

}




