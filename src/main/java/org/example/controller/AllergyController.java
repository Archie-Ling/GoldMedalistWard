package org.example.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Allergy;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.AllergyService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j//日志
@RestController//Controller组件
@RequestMapping("/allergy")//这里记得改
@CrossOrigin//跨域
@Api(tags = "过敏管理")//这里也是
public class AllergyController {
    //自动注入
    @Autowired
    private AllergyService allergyService;




    //数据添加
    @ApiOperation("过敏添加")
    @PostMapping("/add")
    public R addAllergy(@RequestBody Allergy allergy){//请求体到的位置

        boolean save = allergyService.save(allergy);
        if(save)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }





    @GetMapping("/find")
    @ApiOperation("回显")
    @ResponseBody
    public R find(String id){
        log.info(id);
        Allergy allergy = allergyService.getById(id);
        //暂时带id
        return R.ok().data(allergy);
    }
    //删除
    @ApiOperation("过敏删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteAllergy(String id){
        boolean b = allergyService.removeById(id);
        if(b)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");
    }
    //分页查询列表

    @ApiOperation("过敏分页")
    @GetMapping("/list")
    @ResponseBody
    public R pageAllergy(SearchDto search) {
        //分页
        Page<Allergy> allergyPage = new Page<>(search.getPage(),search.getLimit());
        //查询的配置
        QueryWrapper<Allergy> allergyQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(search.getSearch())) {//判断这个属性存不存在,不存在就不搜索了
            allergyQueryWrapper.like("type", search.getSearch());
        }
        allergyQueryWrapper.orderByAsc("type");//按照name字段排序
        //放入查找
        allergyService.page(allergyPage, allergyQueryWrapper);
        List<Allergy> records = allergyPage.getRecords();


        records.forEach(System.out::println);
        return R.ok().data(records);


    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Allergy allergy){
        boolean b = allergyService.updateById(allergy);
        if(b)
            return R.ok().msg("修改成功");
        else
            return R.ok().msg("修改失败");


    }
}
