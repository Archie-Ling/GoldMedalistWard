package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Company;
import org.example.service.CompanyService;
import org.example.mapper.CompanyMapper;
import org.springframework.stereotype.Service;

/**
* @author 李可文
* @description 针对表【company】的数据库操作Service实现
* @createDate 2023-02-19 11:35:33
*/
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
    implements CompanyService{

}




