package io.renren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理员表，享谷国际与商会的用户信息。
 *
 * @author zl
 * @email
 * @date 2021-09-10 11:20:32
 */
@Data
@TableName("zzy_coc_manage_user")
public class ZzyCocManageUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;
    /**
     * 商会id（0为亨谷用户，其它为商会用户）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cocId;
    /**
     * 角色id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;
    /**
     * 状态 1 正常 2 冻结
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;
    /**
     * 0：没有删除，1：删除
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isDeleted;
    /**
     * 创建者id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     * 创建时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createTime;
    /**
     * 更新用户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     * 更新时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime updateTime;
    /**
     * 该账号是否zst系统创建,0:是(代表超级管理员账号)，1:否
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isSysAllot;
    /**
     * 备注
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String remark;

}
