package io.renren.utils;

import io.renren.entity.BladeUserEntity;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.CocExecl;
import io.renren.execel.ResultCocExcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author zl
 */
public class ThreadUtile {

    /**
     * 无界
     */
   public static LinkedTransferQueue<CocExecl> queue = new LinkedTransferQueue<CocExecl>();
    /**
     * //有界
     */
    public static ArrayBlockingQueue<CocExecl> ay = new ArrayBlockingQueue<CocExecl>(20000);

    /**
     * 347  301
     */
    public static ArrayList<ResultCocExcel> faild = new ArrayList<>();

    /**
     * 手机号去重
     */
    public static HashMap<String , BladeUserEntity> mp = new HashMap<>();

    /**
     * 有多少个商会
     */
    public static ArrayList<ZzyCocEntity> cy = new ArrayList<>();

    /**
     * 过滤掉的商会
     */
    public static ArrayList<ZzyCocEntity> filter = new ArrayList<>();

    /**
     * 处理的商会
     */
    public static ArrayList<ZzyCocEntity> process = new ArrayList<>();

 /**
  * 预览的商会
  */
 public static ArrayList<ZzyCocEntity> predict = new ArrayList<>();

}
