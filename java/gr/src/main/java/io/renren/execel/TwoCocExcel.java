package io.renren.execel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.apache.poi.xssf.usermodel.XSSFPictureData;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zl
 */
@Data
public class TwoCocExcel implements Serializable {
    @ExcelProperty(value = "序号",order = 0)
    private Integer order;
    @ExcelProperty(value = "LOGO",order = 1)
    private String logo;
    @ExcelProperty(value = "单位名称",order = 2)
    private String name;
    @ExcelProperty(value = "法定代表人",order = 3)
    private String person;
    @ExcelProperty(value = "联系方式",order = 4)
    private String phone;
    @ExcelProperty(value = "成立时间",order = 5)
    private Date time;
    @ExcelProperty(value = "统一社会信用代码",order = 6)
    private String oin;
    @ExcelProperty(value = "地址",order = 7)
    private String address;
    @ExcelProperty(value = "会长",order = 8)
    private String meetingMonitor;
    @ExcelProperty(value = "单位所属",order = 9)
    private String company;
    @ExcelProperty(value = "官网地址",order = 10)
    private String national;

}
