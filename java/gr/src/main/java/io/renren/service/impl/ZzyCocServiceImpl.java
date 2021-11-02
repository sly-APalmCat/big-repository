package io.renren.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.ZzyCocMapper;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.LocationExcel;
import io.renren.entity.ResultCocExcel;
import io.renren.entity.ResultTwo;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.service.BladeUserService;
import io.renren.service.ZzyCocManageUserService;
import io.renren.service.ZzyCocService;
import io.renren.service.ZzyUserCocService;
import io.renren.utils.ThreadUtile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Service("zzyCocService")
public class ZzyCocServiceImpl extends ServiceImpl<ZzyCocMapper, ZzyCocEntity> implements ZzyCocService {
    @Resource
    private BladeUserService bladeUserService;
    @Resource
    private ZzyCocManageUserService zzyCocManageUserService;
    @Resource
    private ZzyUserCocService zzyUserCocService;
    @Resource
    private RestTemplate restTemplate;
    @Resource(name = "akj")
    private ThreadPoolExecutor threadPoolExecutor;

    private static Set<Long> key = new HashSet<>();

    private static final String url = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=DR3BZ-KBQ3I-F4IG5-57UC5-XFTWV-5HBF7";


    @Override
    public void execution(ArrayList<CocExecl> list) throws InterruptedException {
        for (CocExecl item : list) {
            String format = String.format(url, item.getAddress());
            ResponseEntity<ResultCocExcel> forEntity = restTemplate.getForEntity(format, ResultCocExcel.class);
            ResultCocExcel body = forEntity.getBody();
            switch (body.getStatus().intValue()) {
                case 0:
                    ResultTwo result = body.getResult();
                    LocationExcel location = result.getLocation();
                    ZzyCocEntity zzyCocEntity = item.getZzyCocEntity();
                    zzyCocEntity.setLocationLat(location.getLat());
                    zzyCocEntity.setLocationLng(location.getLng());

                    ArrayList<BladeUserEntity> objects = new ArrayList<>();
                    List<BladeUserEntity> userList = item.getUserList();
                    Iterator<BladeUserEntity> iterator = userList.iterator();
                    while (iterator.hasNext()){
                        BladeUserEntity s = iterator.next();
                        BladeUserEntity userEntity = ThreadUtile.mp.get(s.getPhone());
                        objects.add(s);
                        if(Objects.nonNull(userEntity)){
                            iterator.remove();
                            continue;
                        }else {
                            ThreadUtile.mp.put(s.getPhone(),s);
                        }
                    }

                    if (ObjectUtil.isNotEmpty(item.getUserList())) {
                        try{

                            boolean b = bladeUserService.saveBatch(item.getUserList());
                            if (!b) {
                                throw new RuntimeException("添加错了".concat(JSON.toJSONString(item)));
                            }
                        }catch (Exception e){
                            io.renren.execel.ResultCocExcel resultCocExcel = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                            resultCocExcel.setRestCode(0);
                            resultCocExcel.setMessage(body.getMessage());
                            resultCocExcel.setReason(e.getMessage());
                            ThreadUtile.faild.add(resultCocExcel);
                        }
                    }
                    item.setUserList(objects);

                    ThreadUtile.queue.put(item);
                    break;
                case 120:
                    ThreadUtile.ay.put(item);
                    break;
                case 347:
                    io.renren.execel.ResultCocExcel resultCocExcel = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel.setRestCode(347);
                    resultCocExcel.setMessage(body.getMessage());
                    ThreadUtile.faild.add(resultCocExcel);
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    break;
                case 301:
                    io.renren.execel.ResultCocExcel resultCocExcel2 = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel2.setRestCode(301);
                    resultCocExcel2.setMessage(body.getMessage());
                    ThreadUtile.faild.add(resultCocExcel2);
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    break;
                default:
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    System.out.println("------------     呵呵，你没想到吧！！---------"+JSON.toJSONString(body));
                    io.renren.execel.ResultCocExcel resultCocExcel3 = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel3.setRestCode(body.getStatus().intValue());
                    resultCocExcel3.setMessage(body.getMessage());
                    ThreadUtile.faild.add(resultCocExcel3);
                    //item.setRestCode(body.getStatus().intValue());
            }


            key.add(body.getStatus());
           // System.out.println("error : " + JSON.toJSONString(key));

            // System.out.println(Thread.currentThread().getName() + "--- -- " + body.getStatus()+"--   - --- "+ThreadUtile.queue.size());
        }
      //  TimeUnit.SECONDS.sleep(1);
    }
}