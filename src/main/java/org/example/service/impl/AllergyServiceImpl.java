package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Allergy;
import org.example.service.AllergyService;
import org.example.mapper.AllergyMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【allergy】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class AllergyServiceImpl extends ServiceImpl<AllergyMapper, Allergy>
    implements AllergyService{

}




