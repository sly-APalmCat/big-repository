package io.renren.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.CIUploadResult;
import com.qcloud.cos.model.ciModel.persistence.ProcessResults;
import com.qcloud.cos.region.Region;
import io.renren.entity.ZzyCocEntity;
import io.renren.execel.TwoCocExcel;
import io.renren.service.ZzyCocService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

/**
 * @author zl
 */
@Slf4j
public class EExcelUtils {
    public static LongAdder adder;
    public COSClient cosClient;
    public ZzyCocService zzyCocService;

    {
        adder = new LongAdder();
        adder.add(719);

    }

    public EExcelUtils(COSClient cosClient, ZzyCocService zzyCocService){
        this.cosClient = cosClient;
        this.zzyCocService = zzyCocService;
    }

    public void ImportDeptdata(int mode,InputStream inputStream) {
 /*
     如果是xls格式，使用HSSFWorkbook，HSSFSheet，HSSFRow来进行相关操作
     如果是xlsx格式，使用XSSFWorkbook，XSSFSheet，XSSFRow来进行相关操作,目前只支持xlsx
 */
        List<ZzyCocEntity> list = zzyCocService.list(Wrappers.<ZzyCocEntity>lambdaQuery().select(ZzyCocEntity::getId,ZzyCocEntity::getName));
        Map<String, ZzyCocEntity> collect = list.stream().collect(Collectors.toMap(ZzyCocEntity::getName, item -> item));

        try {
            //读取excel数据
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);

            try{
                AtomicInteger atomicInteger = new AtomicInteger(1);
                Iterator<Sheet> sheetIterator = wb.sheetIterator();

                sheetIterator.forEachRemaining(sheet1 ->{
                    ArrayList<ZzyCocEntity> zzyCocEntities = new ArrayList<>();
                    try {
                        if(atomicInteger.getAndIncrement() != 1){
                            XSSFSheet sheet = (XSSFSheet) sheet1;
                            Map<String, String> pictures = getPictures( sheet);

                            int firstRowNum = sheet.getFirstRowNum();
                            int lastRowNum = sheet.getLastRowNum();

                            for (int i = 1 ; i <= lastRowNum; i ++){
                                XSSFRow row = sheet.getRow(i);

                                ZzyCocEntity zzyCocEntity = new ZzyCocEntity();
                                adder.increment();
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

                                TwoCocExcel two = new TwoCocExcel();

                                if(ObjectUtils.isNotEmpty(row)){
                                    short lastCellNum = row.getLastCellNum();
                                    for (int j = 0; j < lastCellNum; j++) {
                                        XSSFCell cell = row.getCell(j);
                                        if(ObjectUtils.isNotEmpty(cell)){
                                            String stringCellValue = null;
                                            if(j != 5){
                                                cell.setCellType(CellType.STRING);
                                                stringCellValue = cell.getStringCellValue();
                                            }else {
                                                cell.setCellType(CellType.NUMERIC);
                                            }

                                            switch (j){
                                                case 0:
                                                    two.setOrder(Integer.valueOf(stringCellValue));
                                                    break;
                                                case 1:
                                                    two.setLogo(pictures.get(two.getOrder()+""));
                                                    break;
                                                case 2:
                                                    two.setName(stringCellValue);
                                                    break;
                                                case 3:
                                                    two.setPerson(stringCellValue);
                                                    break;
                                                case 4:
                                                    two.setPhone(stringCellValue);
                                                    break;
                                                case 5:
                                                    short dataFormat = cell.getCellStyle().getDataFormat();
                                                    String dataFormatString = cell.getCellStyle().getDataFormatString();
                                                    double numericCellValue = cell.getNumericCellValue();
                                                    Date date =
                                                    org.apache.poi.ss.usermodel.DateUtil.getJavaDate(numericCellValue, TimeZone.getTimeZone("+8"));
                                                    two.setTime(date);
                                                    String format = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
                                                    break;
                                                case 6:
                                                    two.setOin(stringCellValue);
                                                    break;
                                                case 7:
                                                    two.setAddress(stringCellValue);
                                                    break;
                                                case 8:
                                                    two.setMeetingMonitor(stringCellValue);
                                                    break;
                                                case 9:
                                                    two.setCompany(stringCellValue);
                                                    break;
                                                case 10:
                                                    two.setNational(stringCellValue);
                                                    break;
                                                case 11:
                                                case 12:
                                                default:
                                                    break;

                                            }
                                        }else {
                                            System.out.print("--");
                                        }
                                    }
                                }
                                adder.increment();
                                zzyCocEntity.setId(adder.longValue());
                                zzyCocEntity.setName(two.getName());
                                zzyCocEntity.setAddress(ObjectUtils.isEmpty(two.getAddress())? null : two.getAddress());
                                zzyCocEntity.setLogo(two.getLogo());
                                if(ObjectUtils.isEmpty(collect.get(zzyCocEntity.getName()))){
                                    collect.put(zzyCocEntity.getName(),zzyCocEntity);
                                    zzyCocEntities.add(zzyCocEntity);
                                }
                            }
                            boolean b = zzyCocService.saveBatch(zzyCocEntities);
                            System.out.println(b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }


            catch (Exception e){
                e.printStackTrace();
            }
          //  inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getRowCellValue(XSSFSheet xssfSheet,int rowIndex,int cellIndex){
        //判断是否为空
        if(xssfSheet.getRow(rowIndex)==null
                || xssfSheet.getRow(rowIndex).getCell(cellIndex) == null){
            return null;
        }else {
            xssfSheet.getRow(rowIndex).getCell(cellIndex).setCellType(CellType.STRING);//设置为String 类型
            return xssfSheet.getRow(rowIndex).getCell(cellIndex).getStringCellValue();
        }
    }

    /*excel 新版本xlsx*/
    public Map<String, String> getPictures(XSSFSheet sheet) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        List<XSSFShape> list = sheet.getDrawingPatriarch().getShapes();
        for (XSSFShape shape : list) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture picture = (XSSFPicture) shape;
                XSSFClientAnchor cAnchor = (XSSFClientAnchor) picture.getAnchor();
                XSSFPictureData pdata = picture.getPictureData();
                byte[] data = pdata.getData();
                // 行号-列号
                String key = cAnchor.getRow1()+"";
                String concat = "F:\\cocLogo\\".concat(String.valueOf(key)).concat(pdata.getMimeType().replace("/", "."));
                try(FileOutputStream fileOutputStream = new FileOutputStream(new File(concat))){
                    fileOutputStream.write(data);
                }
                String bucketName = "cocstaticdate-1304584096";
                String replace = concat.replace("F:\\", "").replace(File.separator, "/");
                PutObjectResult putObjectResult = cosClient.putObject(bucketName, replace, new File(concat));
                CIUploadResult ciUploadResult = putObjectResult.getCiUploadResult();
                Date expiration = new Date(System.currentTimeMillis() + 10L * 365 * 60 * 60 * 10000);
                //log.info("urlRequest :  {}", replace);
                GeneratePresignedUrlRequest request =
                        new GeneratePresignedUrlRequest(bucketName, replace, HttpMethodName.GET);
                request.setExpiration(expiration);
                URL url = cosClient.generatePresignedUrl(request);
                String substring = url.toString().substring(0, url.toString().indexOf("?"));
                //log.info("cos 文件路径“：{}",substring);
                map.put(key, substring);
            }
        }
        try(FileOutputStream fileOutputStream = new FileOutputStream(new File("F:\\cocLogo\\".concat(String.valueOf(adder.longValue())).concat(".txt")))){
            fileOutputStream.write(JSON.toJSONString(map).replace(",","\r\n").getBytes(StandardCharsets.UTF_8));
        }
        return map;
    }

    /* excel 旧版本xls
    *  public static Map<String, HSSFPictureData> getPictures(HSSFSheet sheet) throws IOException {
            Map<String, HSSFPictureData> map = new HashMap<String, HSSFPictureData>();
            List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
            for (HSSFShape shape : list) {
                if (shape instanceof HSSFPicture) {
                    HSSFPicture picture = (HSSFPicture) shape;
                    HSSFClientAnchor cAnchor = (HSSFClientAnchor) picture.getAnchor();
                    HSSFPictureData pdata = picture.getPictureData();
                    String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                    map.put(key, pdata);
                }
            }
            return map;
        }
    * */
    public Map<String,String> printImg(Map<String, XSSFPictureData> maplist) throws IOException {
        Object key[] = maplist.keySet().toArray();
        String imgSaveDir = "d://temp";
        File file = new File(imgSaveDir);
        if(!file.exists()) { //目录不存在则创建
            file.mkdirs();
        }
        Map<String,String> temMap = new HashMap<>();
        for (int i = 0; i < maplist.size(); i++) {
            // 获取图片流
            XSSFPictureData pic = maplist.get(key[i]);
            // 获取图片索引
            //String picName = key[i].toString();
            String jpgFileName = "/img_" + UUID.randomUUID().toString().replaceAll("-","")+".jpg";
            temMap.put(key[i].toString(),jpgFileName);
            byte[] data = pic.getData();
            //图片保存路径
            FileOutputStream out = new FileOutputStream(imgSaveDir+jpgFileName);
            out.write(data);
            out.close();
        }
        return temMap;
    }

    /*excel 旧版本xls
    *public static void printImg(Map<String, HSSFPictureData> maplist) throws IOException {

            //for (Map<String, PictureData> map : sheetList) {
                Object key[] = maplist.keySet().toArray();
                for (int i = 0; i < maplist.size(); i++) {
                    // 获取图片流
                    HSSFPictureData pic = maplist.get(key[i]);
                    // 获取图片索引
                    String picName = key[i].toString();


                    byte[] data = pic.getData();

               //图片保存路径
                    FileOutputStream out = new FileOutputStream("D:\\img\\pic" + picName + ".jpg");
                    out.write(data);
                    out.close();
                }
           // }

        }
    * */

    private String getImgFileName(Map<String,String> map,String val){
        return Optional.ofNullable(map.get(val)).orElse("");
    }
}
