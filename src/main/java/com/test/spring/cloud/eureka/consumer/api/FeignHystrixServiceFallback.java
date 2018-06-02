package com.test.spring.cloud.eureka.consumer.api;

import org.springframework.stereotype.Component;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   FeignHystrixServiceCallback
 * Copyright:
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/6/2
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
@Component
public class FeignHystrixServiceFallback implements FeignHystrixService {

    @Override
    public String callHystrix() {
        return "error";
    }

}
