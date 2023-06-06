package org.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Results;
import org.example.entity.Allergy;
import org.example.entity.Company;
import org.example.entity.Disease;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.DiseaseService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/disease")
@CrossOrigin
@Api(tags = "疾病管理")
public class DiseaseController {

    @Autowired
    private DiseaseService diseaseService;



    /**
     * 添加疾病
     * @param disease 疾病
     * @return
     */
    @ApiOperation("添加疾病信息")
    @PostMapping("/add")
    @ResponseBody
    public R addAllergy(@RequestBody Disease disease){

        boolean isSuccess = diseaseService.save(disease);
        if(isSuccess)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    /**
     * 删除疾病记录
     * @param  id 疾病id
     * @return
     */
    @ApiOperation("疾病删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteDiseaseById( String id){
        boolean isSuccess = diseaseService.removeById( id);
        if(isSuccess)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");


    }


    @ApiOperation("疾病信息分页")
    @GetMapping("/list")
    @ResponseBody
    public R paginationByDiseaseInfo(SearchDto searchDto)
    {
        Page<Disease> diseasePage=new Page<>(searchDto.getPage(),searchDto.getLimit());
        LambdaQueryWrapper<Disease> diseaseQueryWrapper = new LambdaQueryWrapper<>();

        diseaseQueryWrapper
                .like(StringUtils.isNotEmpty(searchDto.getSearch()),Disease::getUserDisease, searchDto.getSearch())
                .orderByAsc(Disease::getUpdateTime);

        diseaseService.page(diseasePage,diseaseQueryWrapper);
        List<Disease> records = diseasePage.getRecords();
        return R.ok().data(records);
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Disease disease){
        boolean b = diseaseService.updateById(disease);
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
        Disease byId = diseaseService.getById(id);
        //暂时带id
        return R.ok().data(byId);
    }




}
