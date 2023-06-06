package org.example.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Allergy;
import org.example.entity.Company;
import org.example.entity.Department;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.DepartmentService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Keat-Jie
 * @version 1.0
 * @date 2023/2/20
 */
@Slf4j//日志
@RestController//Controller组件
@RequestMapping("/department")//这里记得改
@CrossOrigin//跨域
@Api(tags = "科室管理")//这里也是
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;



    @ApiOperation("添加科室信息")
    @PostMapping("/add")
    @ResponseBody
    public R addDepartment(@RequestBody Department department){//请求体到的位置

        boolean save = departmentService.save(department);
        if(save)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    @ApiOperation("科室删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteDepartment(String id){
        boolean b = departmentService.removeById(id);
        if(b)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");


    }

    @ApiOperation("科室信息分页")
    @GetMapping("/list")
    @ResponseBody
    public R pageDepartment(SearchDto search){
        //分页
        Page<Department> departmentPage = new Page<>(search.getPage(),search.getLimit());
        //查询的配置
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(search.getSearch())) {//判断这个属性存不存在,不存在就不搜索了
            departmentQueryWrapper.like("name", search.getSearch());
        }
        departmentQueryWrapper.orderByAsc("name");//按照name字段排序
        //放入查找
        departmentService.page(departmentPage, departmentQueryWrapper);
        List<Department> records = departmentPage.getRecords();


        records.forEach(System.out::println);
        return R.ok().data(records);
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Department department){
        boolean b = departmentService.updateById(department);
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
        Department byId = departmentService.getById(id);
        //暂时带id

        return R.ok().data(byId);
    }





}
