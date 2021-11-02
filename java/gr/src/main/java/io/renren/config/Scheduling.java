package io.renren.config;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.LocationExcel;
import io.renren.entity.ResultCocExcel;
import io.renren.entity.ResultTwo;
import io.renren.entity.ZzyCocEntity;
import io.renren.entity.ZzyCocManageUserEntity;
import io.renren.entity.ZzyUserCocEntity;
import io.renren.execel.CocExecl;
import io.renren.service.BladeUserService;
import io.renren.service.ZzyCocManageUserService;
import io.renren.service.ZzyCocService;
import io.renren.service.ZzyUserCocService;
import io.renren.utils.ThreadUtile;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author zl
 */
@Component
@Configuration
public class Scheduling {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private BladeUserService bladeUserService;
    private static final String url = "https://apis.map.qq.com/ws/geocoder/v1/?address=%s&key=DR3BZ-KBQ3I-F4IG5-57UC5-XFTWV-5HBF7";

   // @Scheduled(cron = "0/1 * * * * ? ")
    @Async("akj")
    public void scheduled2() throws InterruptedException {
        System.out.println("------   execution ay  queue  ------------  + "+ThreadUtile.ay.size());
        CocExecl item = null;
        try {
            item = ThreadUtile.ay.take();
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
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    ThreadUtile.faild.add(resultCocExcel);
                    break;
                case 301:
                    io.renren.execel.ResultCocExcel resultCocExcel2 = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel2.setRestCode(301);
                    resultCocExcel2.setMessage(body.getMessage());
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    ThreadUtile.faild.add(resultCocExcel2);
                    break;
                default:
                    System.out.println("------------     呵呵，你没想到吧！！---------"+JSON.toJSONString(body));
                    io.renren.execel.ResultCocExcel resultCocExcel3 = BeanUtil.copyProperties(item, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel3.setRestCode(body.getStatus().intValue());
                    resultCocExcel3.setMessage(body.getMessage());
                    ThreadUtile.filter.add(item.getZzyCocEntity());
                    ThreadUtile.faild.add(resultCocExcel3);
                   // item.setRestCode(body.getStatus().intValue());
            }
        } catch (InterruptedException e) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            e.printStackTrace();
            if(ObjectUtil.isNotNull(item)){
                ThreadUtile.ay.put(item);
            }
        }

    }


    @Resource
    private ZzyCocManageUserService zzyCocManageUserService;
    @Resource
    private ZzyUserCocService zzyUserCocService;
    @Resource
    private ZzyCocService zzyCocService;



   // @Scheduled(cron = "0/50 * * * * ? ")
    public  void scheduled() {

        // System.out.println("----111111111111111111111111111111111111111");
        // for (; ; ) {
        final int size = ThreadUtile.queue.size();
        if(size < 1){
            return ;
        }
        System.out.println("------   execution queue  queue  ------------  + "+size);
        ArrayList<CocExecl> cocExecls = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            CocExecl poll = ThreadUtile.queue.poll();
            if (poll != null) {
                cocExecls.add(poll);
                ThreadUtile.predict.add(poll.getZzyCocEntity());
            }
        }
        ArrayList<BladeUserEntity> user = new ArrayList<BladeUserEntity>();
        ArrayList<ZzyCocManageUserEntity> userManage = new ArrayList<ZzyCocManageUserEntity>();
        ArrayList<ZzyCocEntity> coc = new ArrayList<ZzyCocEntity>();
        ArrayList<ZzyUserCocEntity> cocUser = new ArrayList<ZzyUserCocEntity>();


        try {
            cocExecls.iterator().forEachRemaining(take -> {
                List<BladeUserEntity> zy = take.getUserList();
                coc.add(take.getZzyCocEntity());
                if (ObjectUtil.isNotEmpty(zy)) {
                    List<ZzyCocManageUserEntity> list2 = bootstrapMgUser(take.getId(), zy, take);
                    List<ZzyUserCocEntity> list3 = bootstrapCocUser(take.getId(), zy, take);
                    user.addAll(zy);
                    userManage.addAll(list2);
                    cocUser.addAll(list3);
                }
            });
            zzyCocManageUserService.saveBatch(userManage);
            zzyCocService.saveBatch(coc);
            ThreadUtile.process.addAll(coc);
            zzyUserCocService.saveBatch(cocUser);
            user.clear();
            userManage.clear();
            coc.clear();
            cocUser.clear();

        } catch (Exception e) {

            cocExecls.iterator().forEachRemaining(take -> {
                user.clear();
                userManage.clear();
                coc.clear();
                cocUser.clear();
                try {
                    io.renren.execel.ResultCocExcel resultCocExcel2 = BeanUtil.copyProperties(take, io.renren.execel.ResultCocExcel.class);
                    resultCocExcel2.setRestCode(0);
                    resultCocExcel2.setMessage(e.getMessage());
                    ThreadUtile.faild.add(resultCocExcel2);
                } catch (Exception es) {
                    System.err.println("------    ----------   ------------     ----------");
                    es.printStackTrace();
                }
            });
            System.err.println("exception: " + e.getMessage());
        }


//
//        CocExecl take = null;
//            try {
//                take = ThreadUtile.queue.take();
//                ZzyCocEntity zzyCocEntity = take.getZzyCocEntity();
//                boolean save = zzyCocService.save(zzyCocEntity);
//                if (!save) {
//                    ThreadUtile.ay.put(take);
//                }
//
//                List<BladeUserEntity> zy = take.getUserList();
//                List<ZzyCocManageUserEntity> list2 = bootstrapMgUser(take.getId(), zy,take);
//                try {
//                    boolean b = zzyCocManageUserService.saveBatch(list2);
//                    if (!b) {
//                        ThreadUtile.ay.put(take);
//                        return;
//                    }
//                    List<ZzyUserCocEntity> list3 = bootstrapCocUser(take.getId(), zy,take);
//                    boolean b1 = zzyUserCocService.saveBatch(list3);
//                    if (!b1) {
//                        ThreadUtile.ay.put(take);
//                        zzyCocManageUserService.removeByIds(list2.stream().map(ZzyCocManageUserEntity::getId).collect(Collectors.toList()));
//                    }
//
//                    System.out.println("-----   save  success  --------");
//                }catch (Exception e){
//                    io.renren.execel.ResultCocExcel resultCocExcel = BeanUtil.copyProperties(take, io.renren.execel.ResultCocExcel.class);
//                    resultCocExcel.setRestCode(0);
//                    resultCocExcel.setMessage(e.getMessage());
//                    resultCocExcel.setReason(e.getMessage());
//                    ThreadUtile.faild.add(resultCocExcel);
//                }
//
//            } catch (InterruptedException e) {
//                ThreadUtile.queue.put(take);
//                System.err.println("exception: " + e.getMessage());
//            }

        // }


    }


    private List<ZzyCocManageUserEntity> bootstrapMgUser(Long cId, List<BladeUserEntity> zys, CocExecl e) {
        List<ZzyCocManageUserEntity> list = new ArrayList<>();
        for (int i = 0; i < zys.size(); i++) {
            if (zys.get(i).getId() == null) {
                System.out.println(JSON.toJSONString(e));
                continue;
            }
            ZzyCocManageUserEntity zy = new ZzyCocManageUserEntity();
            zy.setUserId(zys.get(i).getId());
            zy.setCocId(cId);
            zy.setRoleId(0L);
            zy.setStatus(1);
            zy.setIsDeleted(0);
            zy.setCreateUser(88888L);
            zy.setCreateTime(LocalDateTime.now());
            zy.setUpdateUser(0L);
            zy.setUpdateTime(LocalDateTime.now());
            zy.setIsSysAllot(0);
            list.add(zy);
        }
        return list;
    }

    private List<ZzyUserCocEntity> bootstrapCocUser(Long id, List<BladeUserEntity> zys, CocExecl e) {
        ArrayList<ZzyUserCocEntity> arrayList = new ArrayList<>();
        for (int i = 0; i < zys.size(); i++) {
            if (zys.get(i).getId() == null) {
                System.out.println(JSON.toJSONString(e));
                continue;
            }
            ZzyUserCocEntity entity = new ZzyUserCocEntity();
            entity.setUserId(zys.get(i).getId());
            entity.setCocId(id);
            entity.setCreateUser(88888L);
            entity.setCreateDept(0L);
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateUser(0L);
            entity.setUpdateTime(LocalDateTime.now());
            entity.setStatus(2);
            entity.setIsDeleted(0);
            entity.setDeptId(0L);
            entity.setPostId(0L);
            entity.setSortNum(0);
            arrayList.add(entity);
        }
        return arrayList;
    }

}
