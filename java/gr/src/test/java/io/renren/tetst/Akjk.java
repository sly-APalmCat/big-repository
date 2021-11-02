package io.renren.tetst;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author zl
 */
public class Akjk {

    @Test
    public void kjk(){

        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        int second = now.getSecond();
        System.out.println(hour);
        DateUnit day = DateUnit.DAY;
        System.out.println(day.getMillis() == 24 * 60 * 60 *1000);
        long l = day.getMillis() - ((DateUnit.HOUR.getMillis() * hour) + (second * DateUnit.SECOND.getMillis()));
        System.out.println( "剩下 小时毫秒 " + l);
        long l1 = l / DateUnit.HOUR.getMillis();
        System.out.println("h " + l1);
        System.out.println(hour + l1);
        long l2 = l % DateUnit.HOUR.getMillis();
        System.out.println(" 剩下  毫秒"+l2);
        long l4 = day.getMillis() - DateUnit.HOUR.getMillis() * hour;
        System.out.println("剩下 小时毫秒 "+l4);
        long l5 = l4 / DateUnit.HOUR.getMillis();
        System.out.println(l5);

        long l3 = l2 / DateUnit.SECOND.getMillis();
        System.out.println( "s"+l3);

    }


}
