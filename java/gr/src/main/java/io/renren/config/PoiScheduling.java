package io.renren.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.LocationExcel;
import io.renren.entity.ResultCocExcel;
import io.renren.entity.ResultTwo;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.execel.TwoCocExcelGroup;
import io.renren.service.ZzyCocService;
import io.renren.utils.ThreadUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zl
 */
@Component
@Configuration
@Slf4j
public class PoiScheduling {
    private static final String url = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=DR3BZ-KBQ3I-F4IG5-57UC5-XFTWV-5HBF7";
    public static Map<Long, TwoCocExcelGroup> map = new ConcurrentHashMap<Long, TwoCocExcelGroup>();
    public static ArrayList<TwoCocExcelGroup> arr = new ArrayList<>();
    @Resource
    private ZzyCocService zzyCocService;
    @Resource
    private RestTemplate restTemplate;



    @Scheduled(cron = "0/20 * * * * ? ")
    @Transactional(rollbackFor = Exception.class)
    public void scheduled2() throws InterruptedException, FileNotFoundException {
        ArrayList<ZzyCocEntity> arrayList = new ArrayList<>();
        List<ZzyCocEntity> list = zzyCocService.list(Wrappers.<ZzyCocEntity>lambdaQuery().le(ZzyCocEntity::getLocationLat, 0.00)
                .le(ZzyCocEntity::getLocationLng, 0.00).eq(ZzyCocEntity::getStatus, 1)
                .select(ZzyCocEntity::getId, ZzyCocEntity::getAddress,
                ZzyCocEntity::getLocationLat, ZzyCocEntity::getLocationLng));
        log.info("需要处理 条数：{}", list.size());
        if(ObjectUtils.isEmpty(list)){
            log.info("-------------- Null");
            return;
        }

        list.stream().filter(item -> item.getLocationLng().compareTo(BigDecimal.ZERO) <= 0 && item.getLocationLat().compareTo(BigDecimal.ZERO) <= 0)
                .map(item ->{
                    TwoCocExcelGroup group = new TwoCocExcelGroup();
                    group.setVal(group.getVal() + 1);
                    group.setZzyCocEntity(item);
                    return group;
                })
                .forEach(item ->{

                    handlerLngLat(arrayList, item );
                });
        if(ObjectUtils.isNotEmpty(arrayList)){
            zzyCocService.updateBatchById(arrayList);
        }

//        ArrayList<ZzyCocEntity> arrayList2 = new ArrayList<>();
//        handlerMap(arrayList2);
//
//        if(ObjectUtils.isNotEmpty(arrayList2)){
//            zzyCocService.updateBatchById(arrayList2);
//        }


        if(ObjectUtils.isNotEmpty(arr)){
            try(FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\".concat(System.currentTimeMillis()+"").concat(".txt")))){
                fileOutputStream.write(JSON.toJSONString(arr).replace(",","\r\n").getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    private void handlerMap(ArrayList<ZzyCocEntity> arrayList2) {
        if(ObjectUtils.isEmpty(map)){
            return;
        }
        Set<Map.Entry<Long, TwoCocExcelGroup>> entries = map.entrySet();
        for (Map.Entry<Long, TwoCocExcelGroup> it : entries) {
            handlerLngLat(arrayList2, it.getValue());
        }
        if(ObjectUtils.isNotEmpty(map)){
            handlerMap(arrayList2);
        }
    }

    private void handlerLngLat(List<ZzyCocEntity> arrayList, TwoCocExcelGroup item ) {
        ZzyCocEntity zzyCocEntity = item.getZzyCocEntity();
        TwoCocExcelGroup remove = map.remove(zzyCocEntity.getId());
        if(ObjectUtils.isNotEmpty(remove)){
            zzyCocEntity = remove.getZzyCocEntity();
        }
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
            case 120:
                item.setVal(item.getVal() + 1);
                map.put(zzyCocEntity.getId(), item);
                arrayList.add(zzyCocEntity);
                break;
            case 347:
                io.renren.execel.ResultCocExcel resultCocExcel = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                resultCocExcel.setRestCode(347);
                resultCocExcel.setMessage(body.getMessage());
                item.setResultCocExcel(resultCocExcel);
                zzyCocEntity.setStatus(-1);
                arrayList.add(zzyCocEntity);
                arr.add(item);
                break;
            case 301:
                io.renren.execel.ResultCocExcel resultCocExcel2 = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                resultCocExcel2.setRestCode(301);
                resultCocExcel2.setMessage(body.getMessage());
                item.setResultCocExcel(resultCocExcel2);
                zzyCocEntity.setStatus(-1);
                arrayList.add(zzyCocEntity);
                arr.add(item);
                break;
            default:
                zzyCocEntity.setStatus(-1);
                arrayList.add(zzyCocEntity);
                log.info("消息记录 商会信息添加失败 ：{}", JSON.toJSONString(zzyCocEntity));
        }
    }

}
