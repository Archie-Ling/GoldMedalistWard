package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Medicine;
import org.example.service.MedicineService;
import org.example.mapper.MedicineMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【medicine】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, Medicine>
    implements MedicineService{

}




