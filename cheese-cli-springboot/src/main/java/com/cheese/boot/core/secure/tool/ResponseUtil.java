package com.cheese.boot.core.secure.tool;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应结果工具类
 *
 * @author sobann
 */
public class ResponseUtil {


    /**
     * 成功响应
     *
     * @Return
     */
    public static Map<String, Object> ok() {
        return ok("");
    }

    /**
     * 成功响应
     *
     * @Return
     */
    public static Map<String, Object> ok(Object data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 200);
        resultMap.put("data", data);
        resultMap.put("message", "ok");
        return resultMap;
    }


    /**
     * 失败响应
     *
     * @Return
     */
    public static Map<String, Object> failed(String message) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", 500);
        resultMap.put("data", "");
        resultMap.put("message", message);
        return resultMap;
    }

    /**
     * 通用示例
     *
     * @Param code 信息码
     * @Param msg  信息
     * @Return
     */
    public static Map<String, Object> result(Integer code, String msg, Object data) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", code);
        resultMap.put("data", data);
        resultMap.put("message", msg);
        return resultMap;
    }

}
