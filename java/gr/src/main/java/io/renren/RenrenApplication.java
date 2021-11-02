package io.renren;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class}, scanBasePackages = "io.renren")
@MapperScan("io.renren.dao")
@Component("io.renren")
@EnableScheduling
public class RenrenApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenrenApplication.class, args);
    }


    @Bean("akj")
    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(20, 30, 5, TimeUnit.SECONDS,
                new ArrayBlockingQueue(20000), new ThreadFactory() {
            private final AtomicInteger mThreadNum = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread("cutLine-add--" + mThreadNum.getAndIncrement());
            }
        });
        threadPoolExecutor.prestartAllCoreThreads();
        return threadPoolExecutor;
    }

    @Bean
    public COSClient cosClient(){
        COSClient cosClient;
        String appid = "AKIDQaVhdV4LQMgkJytXDaIkuhBXcWZlsVFe";
        String secret = "JOI1rQcZVfr3QXhcGQrAG8j6DFLYHm34";
        COSCredentials cosCredentials = new BasicCOSCredentials(appid,secret);
        ClientConfig clientConfig = new ClientConfig(new Region("ap-guangzhou"));

        cosClient = new COSClient(cosCredentials, clientConfig);


        List<Bucket> buckets = cosClient.listBuckets();
        List<Bucket> collect = buckets.stream().filter(item -> "cocstaticdate-1304584096".equals(item.getName())).collect(Collectors.toList());
        if(ObjectUtils.isEmpty(collect)){
            log.info("没有指定的bucket ，创建开始~ {}", "cocstaticdate-1304584096");
            CreateBucketRequest createBucketRequest2 = new CreateBucketRequest("cocstaticdate-1304584096");
            createBucketRequest2.setCannedAcl(CannedAccessControlList.PublicReadWrite);
            Bucket bucket2 = cosClient.createBucket(createBucketRequest2);
            buckets = cosClient.listBuckets();
        }
        System.out.println(JSON.toJSONString(buckets));
        return cosClient;
    }


}
