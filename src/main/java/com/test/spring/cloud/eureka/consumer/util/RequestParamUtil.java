package com.test.spring.cloud.eureka.consumer.util;

/**
 * ProjectName: com.xlcloud.aq.realtime.textfilter.util
 * ClassName:   RequestParamUtil
 * Copyright:
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/6/23
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
public class RequestParamUtil {

    private static final String SECRET_ID = "1000";
    private static final String SECRET_KEY = "4efe570cb5583b6c9c686fbc3a308513";
    private static final String VERSION = "1.0";

    public static Tuple<String, String> createSecretIdTuple() {
        return new Tuple<String, String>("secretId", SECRET_ID);
    }

    /*
    public static Tuple<String, String> createSecretKeyTuple() {
        return new Tuple<String, String>("secretKey", SECRET_KEY);
    }
    */

    public static Tuple<String, String> createVersionTuple() {
        return new Tuple<String, String>("v", VERSION);
    }

    public static Tuple<String, String> createTimestampTuple() {
        return new Tuple<String, String>("t", String.valueOf(System.currentTimeMillis()));
    }

    public static Tuple<String, String> createNonceTuple() {
        return new Tuple<String, String>("nonce", String.valueOf(Math.random() * 0x7fffffff));
    }

    public static Tuple<String, String> createTuple(String first, String second) {
        return new Tuple<String, String>(first, second);
    }

}
