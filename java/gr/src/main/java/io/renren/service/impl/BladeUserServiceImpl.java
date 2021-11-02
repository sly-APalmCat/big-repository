package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.BladeUserMapper;
import io.renren.entity.BladeUserEntity;
import io.renren.service.BladeUserService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;


@Service("bladeUserService")
public class BladeUserServiceImpl extends ServiceImpl<BladeUserMapper, BladeUserEntity> implements BladeUserService {


}