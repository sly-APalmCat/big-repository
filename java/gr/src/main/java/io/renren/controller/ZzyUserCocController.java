package io.renren.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.entity.ZzyUserCocEntity;
import io.renren.service.ZzyUserCocService;
import io.renren.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * 用户商会映射表
 *
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@Slf4j
@RestController
@RequestMapping("/zzyusercoc")
public class ZzyUserCocController {
    @Autowired
    private ZzyUserCocService zzyUserCocService;


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ZzyUserCocEntity zzyUserCoc = zzyUserCocService.getById(id);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZzyUserCocEntity zzyUserCoc){
		zzyUserCocService.save(zzyUserCoc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZzyUserCocEntity zzyUserCoc){
		zzyUserCocService.updateById(zzyUserCoc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		zzyUserCocService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
