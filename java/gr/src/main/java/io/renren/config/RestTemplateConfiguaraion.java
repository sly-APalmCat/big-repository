package io.renren.config;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import io.renren.entity.LocationExcel;
import io.renren.entity.ResultCocExcel;
import io.renren.entity.ResultTwo;
import io.renren.entity.ZzyCocEntity;
import io.renren.entity.ZzyUserCocEntity;
import io.renren.execel.CocExecl;
import io.renren.service.BladeUserService;
import io.renren.utils.ThreadUtile;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author zl
 */
@Component
public class RestTemplateConfiguaraion {


    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory){
        return new RestTemplate(clientHttpRequestFactory);
    }


    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(50000);
        simpleClientHttpRequestFactory.setReadTimeout(50000);
        return simpleClientHttpRequestFactory;
    }


}
