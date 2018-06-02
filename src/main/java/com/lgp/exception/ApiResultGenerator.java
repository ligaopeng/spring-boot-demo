package com.lgp.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-30 21:09
 */
public class ApiResultGenerator {

    /**
     * 创建普通消息方法
     */
    public static ApiResult result(Boolean flag, String msg, Object result, String jumpUrl, int rows, Throwable throwable){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String nowDateTime = now.format(dateTimeFormatter);
        ApiResult apiResult = ApiResult.newInstance();
        apiResult.setFlag(flag);
        apiResult.setMsg(msg == "" ? "执行成功" : msg);
        apiResult.setResult(result);
        apiResult.setRows(rows);
        apiResult.setJumpUrl(jumpUrl);
        apiResult.setDateTime(nowDateTime);
        return apiResult;
    }

    public static ApiResult successResult(Object result){
        int rows = 0;
        if (result instanceof List) {
            rows = ((List)result).size();
        }
        return result(Boolean.TRUE, "", result, "", rows, null);
    }

    public static ApiResult failResult(String msg, Throwable throwable) {
        return result(Boolean.FALSE, msg, "", "", 0, throwable);
    }
}
