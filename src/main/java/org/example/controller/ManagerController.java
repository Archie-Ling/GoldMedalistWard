package org.example.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Manager;
import org.example.entity.Medicine;
import org.example.entity.dto.LoginDto;
import org.example.entity.dto.PwdDto;
import org.example.entity.dto.SearchDto;
import org.example.handler.exceptionhandler.JavaWebException;
import org.example.service.ManagerService;
import org.example.utils.Md5Utils;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Keat-Jie
 * @version 1.0
 * @date 2023/2/21
 */
@Slf4j
@RestController
@RequestMapping("/manager")
@CrossOrigin
@Api(tags = "管理员管理")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @ApiOperation("添加用户信息")
    @PostMapping("/add")
    @ResponseBody
    public R addMedicineInfo(@RequestBody Manager manager) {
        String salt = Md5Utils.createSalt();
        manager.setSalt(salt);
        String md5Password = Md5Utils.md5Password(manager.getPwd(), salt);
        manager.setPwd(md5Password);
        boolean isSuccess = managerService.save(manager);
        if (isSuccess)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    @ApiOperation("检查用户信息")
    @PostMapping("/query")
    @ResponseBody
    public R check(Manager manager){
        String id = manager.getId();
        Manager managerById = managerService.getById(id);
        if (managerById == null){
            return R.error().msg("该用户不存在");
        }
        String salt = managerById.getSalt();
        manager.setSalt(salt);
        manager.setPwd(Md5Utils.md5Password(manager.getPwd(), salt));
        boolean isSuccess = manager.getPwd().equals(managerById.getPwd());
        if (isSuccess)
            return R.ok().msg("密码正确");
        else
            return R.error().msg("密码错误");

    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public R login(@RequestBody LoginDto loginDto){
        log.info(loginDto.getPhone()+"+"+loginDto.getPwd());

            //1.处理登录生成token
            String token = managerService.login(loginDto.getPhone(), loginDto.getPwd());
            //2.返回成功的结果
            return R.ok().data(token);
    }


    /**
     * 根据id信息
     *
     * @param id
     * @return
     */
    @ApiOperation("药品删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteMedicineById(String id) {
        boolean isSuccess = managerService.removeById(id);
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


        Page<Manager> managerPage=new Page<>(search.getPage(),search.getLimit());

        //查询的配置
        QueryWrapper<Manager> managerQueryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(search.getSearch())) {//判断这个属性存不存在,不存在就不搜索了
            managerQueryWrapper.like("phone", search.getSearch());
        }
        managerQueryWrapper.orderByAsc("phone");//按照name字段排序
        //放入查找
        managerService.page(managerPage,managerQueryWrapper);
        List<Manager> records = managerPage.getRecords();
        records.forEach(System.out::println);
        return R.ok().data(records);
    }


    @PostMapping("/update")
    @ApiOperation("更新")
    @ResponseBody
    public  R update(@RequestBody PwdDto pwdDto){
        Manager managerById = managerService.getById(pwdDto.getId());
        if (managerById == null){
            return R.error().msg("该用户不存在");
        }

        String salt = managerById.getSalt();
        String oldsalt = Md5Utils.md5Password(pwdDto.getOldpwd(), salt);
        String newsalt = Md5Utils.md5Password(pwdDto.getNewpwd(), salt);
        if(managerById.getPwd().equals(oldsalt)){
            managerById.setPwd(newsalt);
            managerService.updateById(managerById);
        }


        return R.ok().msg("修改成功");


    }




}
