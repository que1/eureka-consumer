package com.test.spring.cloud.eureka.consumer.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;

/**   
 * ProjectName: xlcloud-aq-test
 * ClassName:   SigUtil   
 * Copyright:    
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0   
 * @since:      jdk 1.7
 * Create at:   2018年1月17日
 * Description:  
 * 
 * 
 * Modification History:   
 * Date       Author      Version      Description   
 * -------------------------------------------------------------
 *        
 *
 */
public class GenerateSigUtils {

    /** 产品密钥ID，产品标识 */
    public final static String SECRETID = "1000";
    /** 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露 */
    public final static String SECRETKEY = "4efe570cb5583b6c9c686fbc3a308513";
    /** 迅雷评论反作弊云服务版本 */
    public final static String VERSION = "1.0";


    /**
     * 生成签名信息
     * @param secretKey 产品私钥
     * @param params 接口请求参数名和参数值map，不包括sig参数名
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String genSignature(String secretKey, Map<String, String> params) throws UnsupportedEncodingException {
        // 1. 参数名按照ASCII码表升序排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 2. 按照排序拼接参数名与参数值
        StringBuffer paramBuffer = new StringBuffer();
        for (String key : keys) {
            paramBuffer.append(key).append(params.get(key) == null ? "" : params.get(key));
        }
        // 3. 将secretKey拼接到最后
        paramBuffer.append("secretKey").append(secretKey);
        // 4. MD5是128位长度的摘要算法，用16进制表示，一个十六进制的字符能表示4个位，所以签名后的字符串长度固定为32个十六进制字符。
        return DigestUtils.md5Hex(paramBuffer.toString().getBytes("UTF-8"));
    }
}
