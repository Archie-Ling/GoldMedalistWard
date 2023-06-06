package org.example.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;
import org.example.entity.dto.SearchDto;
import org.example.service.*;
import org.example.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dispensing")
@CrossOrigin
@Api(tags = "配药管理")
public class DispensingController {

    @Autowired
    private DispensingService dispensingService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private UserService userService;
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private ColumntableService columntableService;


    /**
     * 添加配药记录
     * @param Dispensing 配药记录
     * @return
     */
    @ApiOperation("添加配药信息")
    @PostMapping("/add")
    @ResponseBody
    public R addDispensing(@RequestBody Dispensing Dispensing){

        boolean isSuccess = dispensingService.save(Dispensing);
        if(isSuccess)
            return R.ok().msg("保存成功");
        else
            return R.error().msg("保存失败");

    }

    /**
     * 根据id删除配药记录
     * @param  id
     * @return
     */
    @ApiOperation("配药删除")
    @GetMapping("/delete")
    @ResponseBody
    public R deleteDispensingRecordById( String  id){
        boolean isSuccess = dispensingService.removeById( id);
        if(isSuccess)
            return R.ok().msg("删除成功");
        else
            return R.error().msg("删除失败");


    }



    @ApiOperation("配药信息分页")
    @GetMapping("/list")
    @ResponseBody
    public R paginationByDiseaseInfo(SearchDto searchDto){
        // 构造分页构造器
        Page<Dispensing> dispensingPage=new Page<>(searchDto.getPage(),searchDto.getLimit());
        // 条件构造器
        QueryWrapper<Dispensing> dispensingQueryWrapper = new QueryWrapper<>();
        // 条件匹配 & 排序
        if(!StringUtils.isEmpty(searchDto.getSearch()))
        dispensingQueryWrapper.like("id",searchDto.getSearch());


//        dispensingQueryWrapper.like(StringUtils.isNotEmpty(searchDto.getSearch()),Dispensing::getMedicineId, searchDto.getSearch())
//                .orderByAsc(Dispensing::getCreatedTime);
        // 查询数据
        dispensingService.page(dispensingPage,dispensingQueryWrapper);
        List<Dispensing> records = dispensingPage.getRecords();
        modifyIdToName(records);
        return R.ok().data(records);
    }


    /**
     *
     *
     * @param records
     */
    private void modifyIdToName(List<Dispensing> records) {

        for (Dispensing record : records) {
            String doctorId = record.getDoctorId();
            String userId = record.getUserId();
            String medicineId = record.getMedicineId();
            String columnId = record.getColumnId();
            if (doctorId != null){
                record.setDoctorId(doctorService.getById(doctorId).getName());
            }
            if (userId != null){
                User user = userService.getById(userId);
                if (user != null){
                    if ( userId.equals(user.getId())){
                        record.setUserId(user.getName());
                    }
                }else { // 当userName 字段为空时,默认 "user"
                    record.setUserId("user");
                }
            }
            if (medicineId != null){
                String medicineName = medicineService.getById(medicineId).getName();
                if (medicineName != null){
                    record.setMedicineId(medicineName);
                }else {
                    record.setMedicineId("null");
                }

            }
            if (columnId != null){
                Columntable columntable = columntableService.getById(columnId);
               if(columntable!=null){
                   record.setColumnId(columntable.getName());
               }
               else
                   record.setColumnId("无");
            }
        }
    }

    @PostMapping("/update")
    @ApiOperation("更新")
    public  R update(@RequestBody Dispensing dispensing){
        boolean b = dispensingService.updateById(dispensing);
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
        Dispensing byId = dispensingService.getById(id);
        byId.setUserId(userService.getById(byId.getUserId()).getName());
        byId.setMedicineId(medicineService.getById(byId.getMedicineId()).getName());
        byId.setDoctorId(doctorService.getById(byId.getDoctorId()).getName());
        byId.setColumnId(columntableService.getById(byId.getColumnId()).getName());




        //暂时带id
        return R.ok().data(byId);
    }




}
