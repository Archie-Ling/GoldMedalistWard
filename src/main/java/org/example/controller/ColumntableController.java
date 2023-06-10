package org.example.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Allergy;
import org.example.entity.Columntable;
import org.example.entity.Medicine;
import org.example.entity.dto.SearchDto;
import org.example.service.ColumntableService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j//日志
@RestController//Controller组件
@RequestMapping("/colum")//这里记得改
@CrossOrigin//跨域
@Api(tags = "取药位管理")//这里也是
public class ColumntableController {

    @Autowired
    private ColumntableService columntableService;

    @ApiOperation("添加取药位")
    @PostMapping("/add")
    @ResponseBody
    public R addColumntable(@RequestBody Columntable column) {
        boolean save = columntableService.save(column);
        if (save) {
            return R.ok().msg("添加成功");
        } else {
            return R.error().msg("添加失败");
        }
    }


    @GetMapping("/find")
    @ApiOperation("回显")
    @ResponseBody
    public R find(String id){
        log.info(id);
        Columntable byId = columntableService.getById(id);
        return R.ok().data(byId);
    }


    @ApiOperation("取药位删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteColumntable(String id) {

        boolean b = columntableService.removeById(id);
        if (b)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");

    }

    @ApiOperation("取药位分页")
    @GetMapping("/list")
    @ResponseBody
    public R pageColumntable(SearchDto search) {
        //分页
        Page<Columntable> columntablePage = new Page<>(search.getPage(),search.getLimit());
        //查询的配置
        QueryWrapper<Columntable> allergyQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(search.getSearch())) {//判断这个属性存不存在,不存在就不搜索了
            allergyQueryWrapper.like("name", search.getSearch());
        }
        allergyQueryWrapper.orderByAsc("name");//按照name字段排序
        //放入查找
        columntableService.page(columntablePage, allergyQueryWrapper);
        List<Columntable> records = columntablePage.getRecords();


        records.forEach(System.out::println);
        return R.ok().data(records);


    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Columntable columntable){
        boolean b = columntableService.updateById(columntable);
        if(b)
            return R.ok().msg("修改成功");
        else
            return R.ok().msg("修改失败");


    }
}
