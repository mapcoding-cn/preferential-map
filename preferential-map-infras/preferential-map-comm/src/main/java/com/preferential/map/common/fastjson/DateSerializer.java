/**
 * @description:
 * @author: Rangobai
 * @date: 2022-03-24 3:15 下午
 **/

package com.preferential.map.common.fastjson;

import static com.preferential.map.common.constant.Global.YYYY_MM_DD_HH_MM_SS_SSS;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 序列化日期类型
 *
 * @author Rangobai
 */
public class DateSerializer implements ObjectSerializer {

    static {
        init();
        JSONObject.DEFFAULT_DATE_FORMAT = YYYY_MM_DD_HH_MM_SS_SSS;
    }

    public static void init() {
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        serializeConfig.put(Date.class, new DateSerializer());
        serializeConfig.put(LocalDateTime.class, new DateSerializer());
        serializeConfig.put(Timestamp.class, new DateSerializer());
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        if (object == null) {
            serializer.writeNull();
            return;
        }

        // 格式化日期类型为 yyyy-MM-dd HH:mm:ss.SSS
        if (object instanceof Date) {
            String dateTime = DateFormatUtils.format((Date) object, YYYY_MM_DD_HH_MM_SS_SSS);
            serializer.write(dateTime);
            return;
        }
        if (object instanceof LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS_SSS);
            String dateTime = ((LocalDateTime) object).format(formatter);
            serializer.write(dateTime);
            return;
        }

        serializer.write(object);
    }
}
