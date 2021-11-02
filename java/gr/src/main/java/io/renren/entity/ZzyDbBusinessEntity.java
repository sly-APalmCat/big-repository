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
 * 资料库
 *
 * @author zl
 * @email
 * @date 2021-09-10 14:34:32
 */
@Data
@TableName("zzy_db_business")
public class ZzyDbBusinessEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 企业名称
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String companyName;
    /**
     * 联系方式
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String phone;
    /**
     * 注册资本(unit:万)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer regCapital;
    /**
     * 行业
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String industry;
    /**
     * 统一社会信用代码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String uscc;
    /**
     * 注册地址-省
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String province;
    /**
     * 注册地址-市
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String city;
    /**
     * 注册地址-区
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String district;
    /**
     * 注册地址-详细地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String address;
    /**
     * 注册地址-经度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal lng;
    /**
     * 注册地址-纬度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal lat;
    /**
     * 地图编码(json)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String mapCode;
    /**
     * 固定电话
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String landline;
    /**
     * 邮箱
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String email;
    /**
     * 微信
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String wechat;
    /**
     * QQ
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String qq;
    /**
     * 传真
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String fax;
    /**
     * 经营状态
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String operStatus;
    /**
     * 成立日期
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime establishDate;
    /**
     * 法定代表人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String legalRepresentative;
    /**
     * 官网地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String website;
    /**
     * 通讯地址-省
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postalProvince;
    /**
     * 通讯地址-市
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postalCity;
    /**
     * 通讯地址-区
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postalDistrict;
    /**
     * 通讯地址-详细地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postalAddress;
    /**
     * 通讯地址-经度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal postalLng;
    /**
     * 通讯地址-纬度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal postalLat;
    /**
     * 通讯地址地图编码(json)
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String postalMapCode;
    /**
     * 产品服务
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String productService;
    /**
     * 经营范围
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String businessScope;
    /**
     * 企业简介
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String companyProfile;
    /**
     * 公司规模
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer companyScale;
    /**
     * 业务状态【0->无效；1->有效】
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;
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
     * 是否已删除【0->否；1->是】
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isDeleted;

}
