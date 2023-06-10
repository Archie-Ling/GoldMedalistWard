package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Medicine;
import org.example.service.MedicineService;
import org.example.mapper.MedicineMapper;
import org.springframework.stereotype.Service;


@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine>
    implements MedicineService{

}




