package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Company;
import org.example.service.CompanyService;
import org.example.mapper.CompanyMapper;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company>
    implements CompanyService{

}




