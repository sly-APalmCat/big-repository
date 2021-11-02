package io.renren.service.impl;

import io.renren.dao.ZzyDbBusinessMapper;
import io.renren.entity.ZzyDbBusinessEntity;
import io.renren.service.ZzyDbBusinessService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;


@Service("zzyDbBusinessService")
public class ZzyDbBusinessServiceImpl extends ServiceImpl<ZzyDbBusinessMapper, ZzyDbBusinessEntity> implements ZzyDbBusinessService {



}