package io.renren.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.entity.BladeUserEntity;
import io.renren.service.BladeUserService;
import io.renren.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




/**
 * 用户表，所有会员信息
 *
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@RestController
@RequestMapping("/bladeuser")
public class BladeUserController {
    @Autowired
    private BladeUserService bladeUserService;




    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		BladeUserEntity bladeUser = bladeUserService.getById(id);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BladeUserEntity bladeUser){
		bladeUserService.save(bladeUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BladeUserEntity bladeUser){
		bladeUserService.updateById(bladeUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		bladeUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
