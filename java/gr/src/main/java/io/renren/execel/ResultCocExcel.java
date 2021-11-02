package io.renren.execel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zl
 */
@Data
public class ResultCocExcel extends CocExecl implements Serializable {

    @ExcelProperty(value = "状态",order = 17)
    private Integer restCode;
    @ExcelProperty(value = "状态解释",order = 18)
    private String message;
    @ExcelProperty(value = "原因",order = 19)
    private String reason;


}
