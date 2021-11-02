package io.renren.execel;

import io.renren.entity.ZzyCocEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zl
 */
@Data
public class TwoCocExcelGroup implements Serializable {

    private Integer val = 0;
    private ZzyCocEntity zzyCocEntity;
    private  io.renren.execel.ResultCocExcel resultCocExcel;


}
