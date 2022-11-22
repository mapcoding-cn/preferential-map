/**
 * @description:
 * @author: Rangobai
 * @date: 2022-11-19 12:11
 **/

package com.preferential.map;

import com.alibaba.fastjson.JSON;
import com.preferential.map.common.utils.ExcelUtils;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class ImportData {

    private static List<String> merchantTypeArray = Arrays.asList("景区", "出行", "酒店驿站", "商超", "美食", "医药",
            "生活服务");

    public static void main(String[] args) throws IOException {
        List<List<String>> data = ExcelUtils.readExcel("C:\\Users\\bycwh\\Desktop\\import.xlsx", 0);
        Map<String, List<String>> images = readImages("C:\\Users\\bycwh\\Desktop\\商户图片夹");
        File sqlFile = new File("merchant.sql");
        List<String> sqlList = new ArrayList<>();
        sqlList.add("delete from merchant_info;");
        sqlList.add("delete from merchant;");
        StringBuilder sql = new StringBuilder(
                "insert into merchant (merchant_id,merchant_name,merchant_type,profile,images,merchant_location,create_time,update_time) values ");
        StringBuilder sqlInfo = new StringBuilder(
                "insert into merchant_info (id,merchant_id,merchant_area,credential_type,config,source,create_time,update_time) values ");

        for (int i = 1; i < data.size(); i++) {
            sql.append("(");
            //获取数据
            List<String> row = data.get(i);
            //商户ID
            Long merchant_id = RandomUtils.nextLong(1, 1000000);
            sql.append(merchant_id).append(",");
            //商户名称
            String merchant_name = trim(row.get(0));
            sql.append("'").append(merchant_name).append("',");
            //行业分类
            String merchant_type = trim(row.get(2));
            int typeIndex = merchantTypeArray.indexOf(merchant_type) + 1;
            sql.append(typeIndex).append(",");
            //商户图片
            List<String> imageUrls = images.get(merchant_name);
            if (imageUrls != null) {
                String profile = imageUrls.get(0);
                sql.append("'").append(profile).append("',");
                //图片列表
                String imageStr = JSON.toJSONString(imageUrls).replace("\"", "\\\"");
                sql.append("'").append(imageStr).append("',");
            } else {
                sql.append("'',").append("'[]'").append(",");
            }

            //商户位置
            String merchant_location = trim(row.get(1));
            String[] coorStr = StringUtils.split(merchant_location, ",");
            if (coorStr.length != 2) {
                throw new RuntimeException("坐标不合法");
            }
            sql.append("ST_GeomFromText('Point(").append(coorStr[1]).append(" ").append(coorStr[0]).append(")',4326),");
            //创建时间和更新时间
            sql.append("'2022-11-19 00:00:00','2022-11-19 00:00:00'),");

            sqlInfo.append("(");
            //主键
            sqlInfo.append(merchant_id).append(",");
            //商家ID
            sqlInfo.append(merchant_id).append(",");
            //面向区域
            String merchant_area = trim(row.get(5));
            if (StringUtils.isEmpty(merchant_area)) {
                merchant_area = "0";
            }
            sqlInfo.append(merchant_area).append(",");
            //优待对象
            String credential_type = trim(row.get(4));
            sqlInfo.append("'[").append(credential_type).append("]',");
            //优待项目和优惠
            String config = row.get(3);
            config = config.replace("\r\n", "\n").replace("\t", "    ");
            Map<String, String> desc = new HashMap<>();
            desc.put("description", config);
            config = JSON.toJSONString(desc).replace("'", "\'").replace("\\n", "<repn>");
            sqlInfo.append("'").append(config).append("',");
            //信息来源
            String from = "pal.email";
            sqlInfo.append("'").append(from).append("',");
            //创建时间和更新时间
            sqlInfo.append("'2022-11-19 00:00:00','2022-11-19 00:00:00'),");

        }
        sqlList.add(sql.substring(0, sql.length() - 1) + ";");
        sqlList.add(sqlInfo.substring(0, sqlInfo.length() - 1) + ";");
        FileUtils.writeLines(sqlFile, sqlList);


    }

    private static String trim(String value) {
        if (value == null) {
            return "";
        }
        return value.trim().replace(" ", "").replace("，", ",").replace("\n", "").replace("\t", "");
    }

    private static Map<String, List<String>> readImages(String path) {
        String[] imageNames = new File(path).list();
        Map<String, List<String>> map = Arrays.stream(imageNames)
                .collect(Collectors.groupingBy(it -> it.split("_")[0]));
        map.entrySet().stream().forEach(it -> {
            List<String> image = it.getValue();
            List<String> ossPaths = image.stream().map(img -> {
                try {
                    return "https://yd-map-1255426777.cos.ap-shanghai.myqcloud.com/merchant/" + URLEncoder.encode(img,
                            "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            it.setValue(ossPaths);
        });
        return map;

    }


}
