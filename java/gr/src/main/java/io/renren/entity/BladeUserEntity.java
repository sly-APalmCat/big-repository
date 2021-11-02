package io.renren.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户表，所有会员信息
 *
 * @author zl
 * @email
 * @date 2021-09-10 11:20:32
 */
@Data
@TableName("blade_user")
public class BladeUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 租户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String tenantId;
    /**
     * 用户编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String code;
    /**
     * 账号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String account;
    /**
     * 密码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String password;
    /**
     * 微信昵称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String nickName;
    /**
     * 昵称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String name;
    /**
     * 真名
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String realName;
    /**
     * 头像
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String avatar;
    /**
     * 邮箱
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String email;
    /**
     * 手机
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String phone;
    /**
     * 生日
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime birthday;
    /**
     * 性别
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer sex;
    /**
     * 角色id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String roleId;
    /**
     * 部门id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String deptId;
    /**
     * 岗位id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postId;
    /**
     * 创建人
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
     * 修改人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     * 修改时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime updateTime;
    /**
     * 状态
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;
    /**
     * 0\未冻结1、冻结
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer freeze;
    /**
     * 是否已删除
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isDeleted;
    /**
     * 用户小程序OpenId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String openId;
    /**
     * 用户是否已经传递了OpenId,0:未传递,1:已传递
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isOpen;
    /**
     * 推荐人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recomUserId;
    /**
     * 自我介绍
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String introduce;
    /**
     * 国家
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String country;
    /**
     * 省
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String province;
    /**
     * 市
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String city;
    /**
     * 县/区
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String county;
    /**
     * 详情地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String address;
    /**
     * 联系号码，phone字段用于手机验证码登录
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String contactNum;
    /**
     * 微信号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String wechat;
    /**
     * 地图编码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String mapCode;
    /**
     * 经度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal latitude;

}
