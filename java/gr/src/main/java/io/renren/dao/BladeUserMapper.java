package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.BladeUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表，所有会员信息
 * 
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@Mapper
public interface BladeUserMapper extends BaseMapper<BladeUserEntity> {
	
}
