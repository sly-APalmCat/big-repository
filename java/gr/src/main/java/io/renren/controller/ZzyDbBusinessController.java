package io.renren.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.entity.ZzyDbBusinessEntity;
import io.renren.service.ZzyDbBusinessService;
import io.renren.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;





/**
 * 资料库
 *
 * @author zl
 * @email 
 * @date 2021-09-10 14:34:32
 */
@Slf4j
@RestController
@RequestMapping("/zzydbbusiness")
public class ZzyDbBusinessController {
    @Autowired
    private ZzyDbBusinessService zzyDbBusinessService;




    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ZzyDbBusinessEntity zzyDbBusiness = zzyDbBusinessService.getById(id);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZzyDbBusinessEntity zzyDbBusiness){
		zzyDbBusinessService.save(zzyDbBusiness);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZzyDbBusinessEntity zzyDbBusiness){
		zzyDbBusinessService.updateById(zzyDbBusiness);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		zzyDbBusinessService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
