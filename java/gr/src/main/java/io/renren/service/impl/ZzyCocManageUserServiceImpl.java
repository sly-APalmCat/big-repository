package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ZzyCocManageUserMapper;
import io.renren.entity.ZzyCocManageUserEntity;
import io.renren.service.ZzyCocManageUserService;
import org.springframework.stereotype.Service;


@Service("zzyCocManageUserService")
public class ZzyCocManageUserServiceImpl extends ServiceImpl<ZzyCocManageUserMapper, ZzyCocManageUserEntity> implements ZzyCocManageUserService {


}