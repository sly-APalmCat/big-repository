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
 * 用户商会映射表
 *
 * @author zl
 * @email
 * @date 2021-09-10 11:20:32
 */
@Data
@TableName("zzy_user_coc")
public class ZzyUserCocEntity implements Serializable {
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
     * 商会id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long cocId;
    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     * 创建部门
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createDept;
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
     * 业务状态 0无效 2有效
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;
    /**
     * 是否已删除
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isDeleted;
    /**
     * 部门id，对应zzy_coc_dept表主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /**
     * 职位id，对应zzy_coc_post表主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long postId;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer sortNum;

}
