package org.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.entity.Company;
import org.example.entity.Dispensing;
import org.example.entity.Doctor;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.DepartmentService;
import org.example.service.DispensingService;
import org.example.service.DoctorService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctor")
@CrossOrigin
@Api(tags = "医生管理")
public class DoctorController {


    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DepartmentService departmentService;


    /**
     * 新增医生记录
     * @param doctor 医生信息
     * @return
     */
    @ApiOperation("添加医生信息")
    @PostMapping("/add")
    @ResponseBody
    public R addDoctorInfo(@RequestBody Doctor doctor){

        boolean isSuccess = doctorService.save(doctor);
        if(isSuccess)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    /**
     * 删除医生记录
     * @param  id
     * @return
     */
    @ApiOperation("医生删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteDoctorById(String  id){
            boolean isSuccess = doctorService.removeById( id);
            if(isSuccess)
                return R.ok().msg("删除成功");
            else
                return R.error().msg("删除失败");


    }


    @ApiOperation("医生信息分页")
    @GetMapping("/list")
    @ResponseBody
    public R paginationByDiseaseInfo(SearchDto search){
        Page<Doctor> doctorPage=new Page<>(search.getPage(),search.getLimit());
        LambdaQueryWrapper<Doctor> doctorQueryWrapper = new LambdaQueryWrapper<>();

        doctorQueryWrapper.like(StringUtils.isNotEmpty(search.getSearch()),Doctor::getName, search.getSearch());
        doctorQueryWrapper.orderByDesc(Doctor::getUpdateTime);

        doctorService.page(doctorPage,doctorQueryWrapper);
        List<Doctor> records = doctorPage.getRecords();
        
        for (Doctor doctor : records) { // 遍历修改字段
            String departmentId = doctor.getDepartmentId();
            if (departmentId != null){
                doctor.setDepartmentId(departmentService.getById(departmentId).getName());
            }
        }

        log.info("records : {}",records);
        return R.ok().data(records);
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Doctor doctor){
        boolean b = doctorService.updateById(doctor);
        if(b)
            return R.ok().msg("修改成功");
        else
            return R.ok().msg("修改失败");
    }


    @GetMapping("/find")
    @ApiOperation("回显")
    @ResponseBody
    public R find(String id){
        log.info(id);
        Doctor byId = doctorService.getById(id);
        byId.setDepartmentId(departmentService.getById(byId.getDepartmentId()).getName());
        //暂时带id
        return R.ok().data(byId);
    }


}
