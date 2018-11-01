package com.f1reking.base.net;

import com.f1reking.base.base.BaseLibApplication;
import com.f1reking.base.util.SPUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author: F1ReKing
 * @date: 2017/11/15 15:43
 * @desc:
 */

public class RetrofitRequestTool {

    public static final String SP_BASE = "sp_base";
    private static final String KEY = "key";
    private static final String REQUEST_HEADERS = "request_headers";
    private static final String SP_LITER = "--base--";

    private static String sKey;
    private static Map<String, String> sHeaders;

    public static void saveKey(String key) {
        RetrofitRequestTool.sKey = key;
        SPUtils.put(BaseLibApplication.getContext(), SP_BASE, KEY, key);
    }

    public static String getKey() {
        if (sKey != null) {
            return sKey;
        }
        sKey = (String) SPUtils.get(BaseLibApplication.getContext(), SP_BASE, KEY, "");
        return sKey;
    }

    public static void addHeader(String key, String value) {
        initHeaders();
        sHeaders.put(key, value);
        save(sHeaders);
    }

    public static String getHeader(String key) {
        initHeaders();
        return sHeaders.get(key);
    }

    public static void setHeaders(Map<String, String> headers) {
        initHeaders();
        sHeaders.clear();
        for (String key : headers.keySet()) {
            sHeaders.put(key, headers.get(key));
        }
        save(headers);
    }

    public static void remove(String key) {
        initHeaders();
        if (sHeaders.containsKey(key)) {
            sHeaders.remove(key);
            save(sHeaders);
        }
    }

    public static void removeAll() {
        initHeaders();
        sHeaders.clear();
        save(sHeaders);
    }

    private static void save(Map<String, String> headers) {
        Set<String> strings = new HashSet<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            strings.add(entry.getKey() + SP_LITER + entry.getValue());
        }
        SPUtils.put(BaseLibApplication.getContext(), SP_BASE, REQUEST_HEADERS, strings);
    }

    private static void initHeaders() {
        if (null == sHeaders) {
            sHeaders = new HashMap<>();
            Set<String> strings =
                (Set<String>) SPUtils.get(BaseLibApplication.getContext(),SP_BASE,REQUEST_HEADERS,"");
            for (String string : strings) {
                String[] str = string.split(SP_LITER);
                if (str.length > 1) {
                    sHeaders.put(str[0], str[1]);
                }
            }
        }
    }
}
