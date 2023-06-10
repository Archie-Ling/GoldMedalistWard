package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Disease;
import org.example.service.DiseaseService;
import org.example.mapper.DiseaseMapper;
import org.springframework.stereotype.Service;


@Service
public class DiseaseServiceImpl extends ServiceImpl<DiseaseMapper, Disease>
    implements DiseaseService{

}




