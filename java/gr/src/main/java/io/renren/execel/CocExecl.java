package io.renren.execel;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.ZzyCocEntity;
import io.renren.entity.ZzyCocManageUserEntity;
import io.renren.entity.ZzyUserCocEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author zl
 */
@Data
public class CocExecl implements Serializable {


    /**
     * 主键
     */
    @ExcelProperty(value = "序号",order = 0)
    private Long id;
    /**
     * 单位名称
     */
    @ExcelProperty(value = "单位名称",order = 1)
    private String name;
    /**
     * 会长
     */
    @ExcelProperty(value = "会长",order = 2)
    private String chairman;
    /**
     * 单位所属
     */
    @ExcelProperty(value = "单位所属",order = 3)
    private String affiliatedUnit;
    /**
     * 成立时间
     */
    @ExcelProperty(value = "成立时间",order = 4)
    private Date establishTime;
    /**
     * 商会首页展示图,多张图片以逗号隔开
     */
    @ExcelProperty(value = "统一社会信用代码",order = 5)
    private String unifiedSocialCreditCode;
    /**
     * 地址
     */
    @ExcelProperty(value = "地址",order = 6)
    private String address;
    /**
     * 职务1
     */
    @ExcelProperty(value = "职务1",order = 7)
    private String duty;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号码1",order = 8)
    private String phone;
    /**
     * 职务2
     */
    @ExcelProperty(value = "职务2",order = 9)
    private String dutyTwo;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号码2",order = 10)
    private String phoneTwo;
    /**
     * 职务3
     */
    @ExcelProperty(value = "职务3",order = 11)
    private String dutyThere;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号码3",order = 12)
    private String phoneThere;
    /**
     * 职务4
     */
    @ExcelProperty(value = "职务4",order = 13)
    private String dutyFour;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号码4",order = 14)
    private String phoneFour;
    /**
     * 职务5
     */
    @ExcelProperty(value = "职务5",order = 15)
    private String dutyFive;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号码5",order = 16)
    private String phoneFive;




    @ExcelIgnore
    private ZzyCocEntity zzyCocEntity ;
    //private List<ZzyCocManageUserEntity> zy;
    @ExcelIgnore
    private List<BladeUserEntity> userList;
   // private List<ZzyUserCocEntity> userCoc;


}
