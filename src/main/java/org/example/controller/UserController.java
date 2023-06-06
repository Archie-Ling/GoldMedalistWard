package org.example.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Allergy;
import org.example.entity.Company;
import org.example.entity.Medicine;
import org.example.entity.User;
import org.example.entity.dto.SearchDto;
import org.example.service.AllergyService;
import org.example.service.UserService;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin
@Api(tags = "患者管理")
public class UserController {


    @Resource
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private UserService userService;

    @Autowired
    private AllergyService allergyService;

    /**
     * 添加患者
     *
     * @param user
     * @return
     */
    @ApiOperation("添加患者")
    @PostMapping("/add")
    @ResponseBody
    public R addUser(@RequestBody User user) {
        boolean save = userService.save(user);
        if (save) {
            List<User> userList = userList(1L, 10L, null);
            redisTemplate.opsForValue().set("userlist?page=1&limit=10",userList,5, TimeUnit.MINUTES);

            return R.ok().msg("添加成功");
        } else {
            return R.error().msg("添加失败");
        }
    }



    /**
     * 患者删除
     */
    @ApiOperation("患者删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteUser(String  id) {

        boolean b = userService.removeById(id);
        if (b)
        {

        List<User> userList = userList(1L, 10L, null);
        redisTemplate.opsForValue().set("userlist?page=1&limit=10", userList, 5, TimeUnit.MINUTES);
        return R.ok().msg("删除成功");
    }
        else
            return R.error().msg("删除失败");

    }


    /**
     * 患者分页查询
     */


    @ApiOperation("患者分页")
    @GetMapping("/list")
    @ResponseBody
   public R pageAllergy(SearchDto searchDto) {
        if(searchDto.getSearch()==null&&searchDto.getPage().equals(1L)&&searchDto.getLimit().equals(10L)) {
            //从redis获取
            List<User> redislist = (List<User>)redisTemplate.opsForValue().get("userlist?page=1&limit=10");
            if (redislist!=null) {
                return R.ok().data(redislist);
            }
        }

        log.info("search++"+searchDto.getSearch());
        List<User> records = userList(searchDto.getPage(),searchDto.getLimit(),searchDto.getSearch());


        if(searchDto.getSearch()==null&&searchDto.getPage().equals(1L)&&searchDto.getLimit().equals(10L)) {
        redisTemplate.opsForValue().set("userlist?page=1&limit=10",records,5, TimeUnit.MINUTES);
        }



            return R.ok().data(records);
    }






    public  List<User> userList(Long page,Long limit,String search){


        //创建分页条件
        Page<User> userPage = new Page<>(page,limit);
        //构造搜索条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty((search))) {//如果name不为空
            userQueryWrapper.like("name", search);
        }
        userQueryWrapper.orderByAsc("name");
        userService.page(userPage, userQueryWrapper);

        List<User> records = userPage.getRecords();
        for (User user : records) {
            String allergyId = user.getAllergyId();
            if(!StringUtils.isEmpty(allergyId)){
                //如果不为空
                Allergy byId = allergyService.getById(allergyId);


                if(byId!=null){
                    user.setAllergyId(byId.getType());
                }
            }
        }

        return records;
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody User user){
        boolean b = userService.updateById(user);





        if(b) {
            List<User> userList = userList(1L, 10L, null);
            redisTemplate.opsForValue().set("userlist?page=1&limit=10",userList,5, TimeUnit.MINUTES);


            return R.ok().msg("修改成功");
        }
        else
            return R.ok().msg("修改失败");


    }



    @GetMapping("/find")
    @ApiOperation("回显")
    @ResponseBody
    public R find(String id){
        log.info(id);
        User byId = userService.getById(id);
        //暂时带id
        String allergyId = byId.getAllergyId();
        if(!StringUtils.isEmpty(allergyId)){
            Allergy byId1 = allergyService.getById(allergyId);
            if (byId1!=null)
                byId.setAllergyId(byId1.getType());
        }
        List<User> userList = userList(1L, 10L, null);
        redisTemplate.opsForValue().set("userlist?page=1&limit=10",userList,5, TimeUnit.MINUTES);

        return R.ok().data(byId);
    }




}









