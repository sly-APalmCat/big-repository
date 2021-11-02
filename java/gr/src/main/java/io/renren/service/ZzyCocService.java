package io.renren.service;


import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;

import java.util.ArrayList;

/**
 * 商会表
 *
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
public interface ZzyCocService extends IService<ZzyCocEntity> {


    public void execution(ArrayList<CocExecl> list) throws InterruptedException;

}

