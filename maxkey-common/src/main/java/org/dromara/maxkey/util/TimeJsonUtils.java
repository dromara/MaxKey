package org.dromara.maxkey.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.dromara.maxkey.adapter.LocalDateTimeAdapter;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: orangeBabu
 * @time: 16/8/2024 PM6:23
 */
public class TimeJsonUtils {
    public static <T> T gsonStringToObject(String json, Class<T> cls) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        return gson.fromJson(json, cls);
    }

    /**
     * Gson Transform java bean object to json string .
     *
     * @param bean Object
     * @return string
     */
    public static String gsonToString(Object bean) {
        String json = "";
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        json = gson.toJson(bean);

        return json;
    }
}
