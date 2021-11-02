package io.renren.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnalysiUtil {

    //传递参数例子如下，imgNames是图片属性名字按顺序组成的集合,rows时有用的行，lists是某行中有用的列的集合，排序和rows中对应，names是实体类中每个单元格数据对应的属性名称
    //遇到按行列取的数据可以修改下面的for循环
    //int[] rows = {0,1,3,5};//上方有用的行数
    //List<List<Integer>> lists = Arrays.asList(Arrays.asList(0),Arrays.asList(1,8),Arrays.asList(3),Arrays.asList(1,4,7,11));//和rows对应，每行要取列的数据
    //List<List<String>> names = Arrays.asList(Arrays.asList("name"),Arrays.asList("engineeringName","engineeringPosition"),Arrays.asList("designParameters"),
    //                           Arrays.asList("constructionDate","concreteSupplier","concreteVolume","concreteStrength"));//每一格对应的实体类名称
    //获取阿里云上的excel文件
    //URL httpurl = new URL("url地址");
    //URLConnection urlConnection = httpurl.openConnection();
    //InputStream in = urlConnection.getInputStream();
    //XSSFWorkbook workbook = new XSSFWorkbook(in); 2003用HSSFWorkbook
    public static <T> boolean getExeclData(T Parame, XSSFWorkbook workbook, int[] rows, List<List<Integer>> lists, List<List<String>> names, List<String> imgNames){
        try{
            Row row= null;
            Field f = null;
            Cell cell = null;//获取到单元格内数据
            //建立文件，如果传递的是MultipartFile，则
            //Workbook workbook = WorkbookFactory.create(file.getInputStream());
            //获取第一个表
            Sheet sheet = workbook.getSheetAt(0);
            if(imgNames.size()>0){
                //1.读出的Excel中的图片
                Map<String, XSSFPictureData> maplist=null;
                XSSFSheet sheet1 = workbook.getSheetAt(0);
                // 支持word03的方法获取图片
                maplist = getPictures(sheet1);
                List<String> imgs = printImg(maplist);
                for(int g=0;g<imgs.size();g++){
                    f = Parame.getClass().getDeclaredField(imgNames.get(g));
                    f.setAccessible(true);
                    f.set(Parame, imgs.get(g));
                }
            }
            //其他比较乱的位置的数据获取
            for(int i=0;i<rows.length;i++){
                row= sheet.getRow(rows[i]);
                if(row==null){continue;}
                List<Integer> list = lists.get(i);
                List<String> name = names.get(i);
                for(int j=0;j<list.size();j++){
                    cell = row.getCell(list.get(j));
                    String value = cell != null ? convertDataType(cell) : "";
                    f = Parame.getClass().getDeclaredField(name.get(j));
                    f.setAccessible(true);
                    f.set(Parame, value);
                }
            }
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 获取图片和位置 (xls)测试过
     * @param sheet
     * @return
     * @throws IOException
     */
    public static Map<String, XSSFPictureData> getPictures(XSSFSheet sheet) throws IOException {
        Map<String, XSSFPictureData> map = new HashMap<>();
        List<XSSFShape> list = sheet.createDrawingPatriarch().getShapes();
        for (XSSFShape shape : list) {
            if (shape instanceof XSSFPicture) {
                XSSFPicture picture = (XSSFPicture) shape;
                XSSFClientAnchor cAnchor = (XSSFClientAnchor) picture.getAnchor();
                XSSFPictureData pdata = picture.getPictureData();
                String key = cAnchor.getRow1() + "-" + cAnchor.getCol1(); // 行号-列号
                map.put(key, pdata);
            }
        }
        return map;
    }

    //图片写出
    public static List<String> printImg(Map<String, XSSFPictureData> maplist) throws IOException {
        Object key[] = maplist.keySet().toArray();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < maplist.size(); i++) {
            // 获取图片流
            XSSFPictureData pic = maplist.get(key[i]);
            // 获取图片数据
            byte[] data = pic.getData();
            String img = encode(data);
            list.add(img);
        }
        return list;
    }

/**
     * 图片转字符串
     *
     * @param image
     * @return
     */

    public static String encode(byte[] image)
    {
        BASE64Encoder decoder = new BASE64Encoder();
        return replaceEnter(decoder.encode(image));
    }

    public static String replaceEnter(String str)
    {
        String reg = "[\n-\r]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    //转化数据类型
    public static String convertDataType(Cell cell){
        String value = "";
        switch (cell.getCellType()) {
            case HSSFCell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    if (date != null) {
                        value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    } else {
                        value = "";
                    }
                } else {
                    value = new DecimalFormat("0").format(cell.getNumericCellValue());
                }
                break;
            case HSSFCell.CELL_TYPE_FORMULA:
                // 导入时如果为公式生成的数据则无值
                if (!cell.getStringCellValue().equals("")) {
                    value = cell.getStringCellValue();
                } else {
                    value = cell.getNumericCellValue() + "";
                }
                break;
            case HSSFCell.CELL_TYPE_BLANK:
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                value = "";
                break;
            case HSSFCell.CELL_TYPE_BOOLEAN:
                value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                break;
            default:
                value = "";
        }
        return value;
    }
}

