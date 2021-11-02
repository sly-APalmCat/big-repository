package io.renren.config.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.LocationExcel;
import io.renren.entity.ResultCocExcel;
import io.renren.entity.ResultTwo;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.execel.TwoCocExcel;
import io.renren.service.ZzyCocService;
import io.renren.utils.ThreadUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author zl
 */
@Slf4j
public class TwoCocListener extends AnalysisEventListener<TwoCocExcel> {

    private static LongAdder longAdder = new LongAdder();

    private static final String url = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=DR3BZ-KBQ3I-F4IG5-57UC5-XFTWV-5HBF7";

    protected ZzyCocService zzyCocService;
    private RestTemplate restTemplate;

    public static ArrayList<ZzyCocEntity> arrayList = new ArrayList<>();

    static{
        longAdder.add(719);
    }

    public TwoCocListener(ZzyCocService zzyCocService, RestTemplate restTemplate){
        this.zzyCocService = zzyCocService;
        this.restTemplate = restTemplate;
    }

    @Override
    public void invoke(TwoCocExcel twoCocExcel, AnalysisContext analysisContext) {

        if (Objects.nonNull(twoCocExcel)) {
            ZzyCocEntity zzyCocEntity = new ZzyCocEntity();
            longAdder.increment();
            zzyCocEntity.setId(longAdder.longValue());
            zzyCocEntity.setName(twoCocExcel.getName());
            zzyCocEntity.setAddress(twoCocExcel.getAddress());
            zzyCocEntity.setCreateUser(88888L);
            zzyCocEntity.setCreateDept(0L);
            zzyCocEntity.setCreateTime(LocalDateTime.now());
            zzyCocEntity.setUpdateUser(0L);
            zzyCocEntity.setUpdateTime(LocalDateTime.now());
            zzyCocEntity.setStatus(1);
            zzyCocEntity.setIsDeleted(0);
            zzyCocEntity.setIsImport(0);
            zzyCocEntity.setLevelId(0L);
            zzyCocEntity.setProvinceCode("0");
            zzyCocEntity.setCityCode("0");
            zzyCocEntity.setTownCode("0");
            zzyCocEntity.setLocationLng(new BigDecimal("0"));
            zzyCocEntity.setLocationLat(new BigDecimal("0"));
            zzyCocEntity.setCocLevel(0);


            String format = String.format(url, zzyCocEntity.getAddress());
            ResponseEntity<ResultCocExcel> forEntity = restTemplate.getForEntity(format, ResultCocExcel.class);
            ResultCocExcel body = forEntity.getBody();
            switch (body.getStatus().intValue()) {
                case 0:
                    ResultTwo result = body.getResult();
                    LocationExcel location = result.getLocation();
                    zzyCocEntity.setLocationLat(location.getLat());
                    zzyCocEntity.setLocationLng(location.getLng());
                    arrayList.add(zzyCocEntity);
                    break;
                default:
                    log.info("消息记录 商会信息添加失败 ：{}", JSON.toJSONString(zzyCocEntity));
            }
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        boolean b = zzyCocService.saveBatch(arrayList);
        log.info(" 批量添加商会成功: {}", b);
    }
}
