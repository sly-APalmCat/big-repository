package io.renren.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.entity.ZzyCocManageUserEntity;
import io.renren.service.ZzyCocManageUserService;
import io.renren.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




/**
 * 管理员表，享谷国际与商会的用户信息。
 *
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@Slf4j
@RestController
@RequestMapping("/zzycocmanageuser")
public class ZzyCocManageUserController {
    @Autowired
    private ZzyCocManageUserService zzyCocManageUserService;



    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ZzyCocManageUserEntity zzyCocManageUser = zzyCocManageUserService.getById(id);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZzyCocManageUserEntity zzyCocManageUser){
		zzyCocManageUserService.save(zzyCocManageUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZzyCocManageUserEntity zzyCocManageUser){
		zzyCocManageUserService.updateById(zzyCocManageUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		zzyCocManageUserService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
