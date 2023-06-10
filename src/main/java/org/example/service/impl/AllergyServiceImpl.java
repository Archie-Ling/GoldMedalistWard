package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Allergy;
import org.example.service.AllergyService;
import org.example.mapper.AllergyMapper;
import org.springframework.stereotype.Service;


@Service
public class AllergyServiceImpl extends ServiceImpl<AllergyMapper, Allergy>
    implements AllergyService{

}




