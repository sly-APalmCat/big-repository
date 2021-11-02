package io.renren.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.entity.ZzyCocManageUserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员表，享谷国际与商会的用户信息。
 * 
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@Mapper
public interface ZzyCocManageUserMapper extends BaseMapper<ZzyCocManageUserEntity> {
	
}
