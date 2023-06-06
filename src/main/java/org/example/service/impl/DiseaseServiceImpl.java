package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Disease;
import org.example.service.DiseaseService;
import org.example.mapper.DiseaseMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【disease】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease>
    implements DiseaseService{

}




