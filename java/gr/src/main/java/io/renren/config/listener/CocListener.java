package io.renren.config.listener;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.renren.entity.BladeUserEntity;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.service.ZzyCocService;
import io.renren.utils.ThreadUtile;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zl
 */
public class CocListener extends AnalysisEventListener<CocExecl> {

    protected ZzyCocService zzyCocService;

    String[] str = {"联谊会", "行业商会", "行业协会", "商会", "协会", "促进会", "贤会", "联合会", ""};
    String[] st = {"秘书长", "秘书"};

    public CocListener(ZzyCocService zzyCocService, RestTemplate restTemplate) {
        this.zzyCocService = zzyCocService;
    }

    ArrayList<CocExecl> cocExecls = new ArrayList<CocExecl>(20);
    HashMap<String, Integer> mp = new HashMap<String, Integer>();


    @SneakyThrows
    @Override
    public void invoke(CocExecl o, AnalysisContext analysisContext) {
        if (Objects.nonNull(o)) {
            ZzyCocEntity zzyCocEntity = new ZzyCocEntity();
            zzyCocEntity.setId(o.getId());
            zzyCocEntity.setName(o.getName());
            zzyCocEntity.setAddress(o.getAddress());
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
            List<String> collect = Arrays.stream(str).filter(it -> o.getName().contains(it)).collect(Collectors.toList());

            int index = o.getName().indexOf(collect.get(0));
            String substring = o.getName().substring(0, index);
            String pinyin = PinyinUtil.getPinyin(substring, "");
            Integer integer = mp.get(pinyin);
            if (Objects.isNull(integer)) {
                mp.put(pinyin, 1);
            } else {
                integer = integer + 1;
                mp.put(pinyin, integer);
                pinyin = pinyin.concat(String.valueOf(integer));
                mp.put(pinyin, 1);
            }
            zzyCocEntity.setCocDomain(pinyin);

            zzyCocEntity.setLocationLng(new BigDecimal("0"));
            zzyCocEntity.setLocationLat(new BigDecimal("0"));
            zzyCocEntity.setCocLevel(0);
            o.setZzyCocEntity(zzyCocEntity);


            List<BladeUserEntity> list = bootstrapUser(o);
            o.setUserList(list);

            cocExecls.add(o);
            ThreadUtile.cy.add(o.getZzyCocEntity());
            if (cocExecls.size() > 5) {
                zzyCocService.execution(cocExecls);
                cocExecls.clear();

            }
        }

    }


    private List<BladeUserEntity> bootstrapUser(CocExecl o) {
        ArrayList<BladeUserEntity> ar = new ArrayList<>();
        cpUser(ar, o.getPhone(), o.getDuty(), o);
        cpUser(ar, o.getPhoneTwo(), o.getDutyTwo(), o);
        cpUser(ar, o.getPhoneThere(), o.getDutyThere(), o);
        cpUser(ar, o.getPhoneFour(), o.getDutyFour(), o);
        cpUser(ar, o.getPhoneFive(), o.getDutyFive(), o);
        return ar;
    }


    public void cpUser(List<BladeUserEntity> list, String phone, String name, CocExecl o) {
        if (Strings.isNotEmpty(phone) && Strings.isNotEmpty(name) && !"无".equals(phone)) {


            BladeUserEntity bladeUserEntity = new BladeUserEntity();
            bladeUserEntity.setAccount(phone);
            bladeUserEntity.setPassword("");
            String[] split = name.split("/");
            String[] split1 = name.split("／");
            String names = split.length < 2 ? split1[0] : split[0];
            if (names.length() > 7) {
                final String ns = names;
                List<String> collect = Arrays.stream(st).filter(it -> ns.contains(it)).collect(Collectors.toList());
                if (collect.size() > 0) {
                    int index = names.lastIndexOf(collect.get(0));
                    names = names.substring(0, index);
                }
            }

            bladeUserEntity.setNickName(name);
            bladeUserEntity.setName(names);
            bladeUserEntity.setRealName(names);
            bladeUserEntity.setPhone(phone);
            bladeUserEntity.setBirthday(LocalDateTime.now());
            bladeUserEntity.setSex(0);
            bladeUserEntity.setCreateUser(0L);
            bladeUserEntity.setCreateTime(LocalDateTime.now());
            bladeUserEntity.setUpdateTime(LocalDateTime.now());
            bladeUserEntity.setStatus(1);
            bladeUserEntity.setFreeze(0);
            bladeUserEntity.setIsDeleted(0);
            bladeUserEntity.setIsOpen(0);
            bladeUserEntity.setRecomUserId(0L);
            bladeUserEntity.setLongitude(new BigDecimal("0"));
            bladeUserEntity.setLatitude(new BigDecimal("0"));
            bladeUserEntity.setContactNum(phone);
            list.add(bladeUserEntity);
        }
    }


    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(cocExecls.size() + "------------------------------------     end    !!!!  -------------------------------------");
        if (cocExecls.size() > 0) {
            zzyCocService.execution(cocExecls);
            cocExecls.clear();
        }

    }


}
