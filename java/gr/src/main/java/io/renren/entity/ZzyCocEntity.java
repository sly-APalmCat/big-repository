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
 * 商会表
 *
 * @author zl
 * @email
 * @date 2021-09-10 11:20:32
 */
@Data
@TableName("zzy_coc_td")
public class ZzyCocEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 商会名字
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String name;
    /**
     * 商会图片
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String logo;
    /**
     * 商会介绍
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String content;
    /**
     * 备注
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String remark;
    /**
     * 商会首页展示图,多张图片以逗号隔开
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String homeImage;
    /**
     * 省份编码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String provinceCode;
    /**
     * 城市编码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String cityCode;
    /**
     * 区县级行政编码
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String townCode;
    /**
     * 地址
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String address;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createUser;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createDept;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime createTime;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateUser;
    /**
     *
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private LocalDateTime updateTime;
    /**
     * 业务状态
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;
    /**
     * 是否删除
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isDeleted;
    /**
     * 标识商会信息是否已经被导入到商城系统
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer isImport;
    /**
     * 商会级别id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long levelId;
    /**
     * 商会域名
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String cocDomain;
    /**
     * 商会地址坐标经度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal locationLng;
    /**
     * 商会地址坐标纬度
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal locationLat;
    /**
     * 社会组织营业执照url
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String blfsoUrl;
    /**
     * 商会级别 0：国级商会、1：省级商会、2：市级商会、3：区级商会、4：街道级商会
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer cocLevel;

}
