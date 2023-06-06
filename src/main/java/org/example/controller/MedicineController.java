package org.example.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Company;
import org.example.entity.Doctor;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.AllergyService;
import org.example.service.CompanyService;
import org.example.service.DoctorService;
import org.example.service.MedicineService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/medicine")
@CrossOrigin
@Api(tags = "药品管理")
public class MedicineController {


    @Autowired
    private MedicineService medicineService;


    @Autowired
    private CompanyService companyService;

    @Autowired
    private AllergyService allergyService;

    /**
     * 新增药品信息
     *
     * @param medicine 药品信息
     * @return
     */
    @ApiOperation("添加药品信息")
    @PostMapping("/add")
    @ResponseBody
    public R addMedicineInfo(@RequestBody Medicine medicine) {

        boolean isSuccess = medicineService.save(medicine);
        if (isSuccess)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    /**
     * 根据id删除药品信息
     *
     * @param id
     * @return
     */
    @ApiOperation("药品删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteMedicineById(String id) {
        boolean isSuccess = medicineService.removeById(id);
        if (isSuccess)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");
    }



    @ApiOperation("药品信息分页")
    @GetMapping("/list")
    @ResponseBody
    public R paginationByDiseaseInfo(SearchDto search){
        //分页
        Page<Medicine> medicinePage=new Page<>(search.getPage(),search.getLimit());
        //查询的配置
        QueryWrapper<Medicine>  medicineQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(search.getSearch())) {//判断这个属性存不存在,不存在就不搜索了
            medicineQueryWrapper.like("name", search.getSearch());
        }
        medicineQueryWrapper.orderByAsc("name");//按照name字段排序
        //放入查找
        medicineService.page(medicinePage,medicineQueryWrapper);
        List<Medicine> records = medicinePage.getRecords();

        //记得替换掉有id的东西
        for (Medicine record : records) {
            //没注入的记得在上面@Autowired 注入
            String companyId = record.getCompanyId();
            if(!StringUtils.isEmpty(companyId)) {
                record.setCompanyId(companyService.getById(companyId).getName());
            }
            //没注入的记得在上面@Autowired 注入
            String allergyId = record.getAllergyId();
            if(!StringUtils.isEmpty(allergyId)) {
                record.setAllergyId(allergyService.getById(allergyId).getType());
            }
        }
        records.forEach(System.out::println);
        return R.ok().data(records);
    }



    @GetMapping("/find")
    @ApiOperation("回显")
    @ResponseBody
    public R find(String id){
        log.info(id);
        Medicine medicine = medicineService.getById(id);
        //暂时带id
        return R.ok().data(medicine);
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Medicine medicine){
        boolean b = medicineService.updateById(medicine);
        if(b)
            return R.ok().msg("修改成功");
        else
            return R.ok().msg("修改失败");


    }



}



