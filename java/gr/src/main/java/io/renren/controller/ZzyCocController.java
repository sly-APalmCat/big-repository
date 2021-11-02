package io.renren.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import io.renren.config.Scheduling;
import io.renren.config.listener.CocListener;
import io.renren.config.listener.TwoCocListener;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.execel.TwoCocExcel;
import io.renren.service.ZzyCocService;
import io.renren.utils.EExcelUtils;
import io.renren.utils.R;
import io.renren.utils.ThreadUtile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * 商会表
 *
 * @author zl
 * @email 
 * @date 2021-09-10 11:20:32
 */
@Slf4j
@RestController
@RequestMapping("/zzycoc")
public class ZzyCocController {
    @Autowired
    private ZzyCocService zzyCocService;
    @Autowired
    private RestTemplate restTemplate;
    @Resource
    private Scheduling scheduling;
    @Resource
    private COSClient cosClient;

    /**
     * 批量处理商会excel数据
     */
    @PostMapping("/file")
    public R processCoCData(MultipartFile file, HttpServletResponse response) throws IOException, InterruptedException {
        EasyExcel.read(file.getInputStream(), CocExecl.class, new CocListener(zzyCocService,restTemplate)).sheet().doRead();
        //EasyExcel.write(response.getOutputStream(), CocExecl.class).sheet("模板").doWrite(ThreadUtile.faild);
        while (ThreadUtile.queue.size() > 0 || ThreadUtile.ay.size() > 0){
            System.out.print(".");
            TimeUnit.SECONDS.sleep(30);
            System.out.println(ThreadUtile.queue.size()+"---  --"+ThreadUtile.ay.size());
            scheduling.scheduled();
        }
        System.out.println("queue ---  ---  --- "+ThreadUtile.queue.size());
        System.out.println("ay ---  ---  --- "+ThreadUtile.ay.size());
        System.out.println("faild ---  347  301 ---  --- "+ThreadUtile.faild.size());
        System.out.println("mp --- 手机号去重 ---  --- "+ThreadUtile.mp.size());
        System.out.println("cy --- 有多少个商会 ---  --- "+ThreadUtile.cy.size());
        System.out.println("filter --- 过滤掉的商会 ---  --- "+ThreadUtile.filter.size());
        System.out.println("process --- 处理的商会 ---  --- "+ThreadUtile.process.size());
        System.out.println("predict --- 预览的商会 ---  --- "+ThreadUtile.predict.size());

        if(ThreadUtile.ay.size() > 0){
            System.out.println("ay --->  "+ JSON.toJSONString(ThreadUtile.ay));
        }
        if(ThreadUtile.queue.size() > 0){
            System.out.println("queue --->  "+ JSON.toJSONString(ThreadUtile.queue));
        }
        if(ThreadUtile.filter.size() > 0){
            System.out.println("filter --->  "+ JSON.toJSONString(ThreadUtile.filter));
        }


        File file1 = new File("C:\\Users\\fjfokwiq\\Desktop\\".concat(RandomUtil.randomStringWithoutStr(3, "aasdfdfghjklwert"))
                .concat(".xlsx"));
        EasyExcel.write(file1).sheet().doWrite(ThreadUtile.faild);
        return R.ok();
    }


    /**
     * 批量处理商会excel数据
     */
    @PostMapping("/fileTwp")
    public R fileTwp(MultipartFile file, HttpServletResponse response) throws IOException, InterruptedException {
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(file.getInputStream()).build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet build =
                    EasyExcel.readSheet(1).head(TwoCocExcel.class).registerReadListener(new TwoCocListener(zzyCocService, restTemplate)).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(build);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }




        EasyExcel.read(file.getInputStream(), TwoCocExcel.class, new TwoCocListener(zzyCocService,restTemplate)).sheet(2).doRead();
        EasyExcel.read(file.getInputStream(), TwoCocExcel.class, new TwoCocListener(zzyCocService,restTemplate)).sheet(3).doRead();

//        File file1 = new File("C:\\Users\\fjfokwiq\\Desktop\\".concat(RandomUtil.randomStringWithoutStr(3, "aasdfdfghjklwert"))
//                .concat(".xlsx"));
//        EasyExcel.write(file1).sheet().doWrite(ThreadUtile.faild);
        return R.ok();
    }

    /**
     * 批量处理商会excel数据
     */
    @PostMapping("/filePic")
    public R filePic(MultipartFile file, HttpServletResponse response) throws IOException, InterruptedException {
        EExcelUtils eExcelUtils = new EExcelUtils(cosClient, zzyCocService);
        eExcelUtils.ImportDeptdata(1, file.getInputStream());

        return R.ok();
    }





    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ZzyCocEntity zzyCoc = zzyCocService.getById(id);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody ZzyCocEntity zzyCoc){
		zzyCocService.save(zzyCoc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ZzyCocEntity zzyCoc){
		zzyCocService.updateById(zzyCoc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		zzyCocService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
