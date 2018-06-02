package com.test.spring.cloud.eureka.consumer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   FeignHystrixService
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
@FeignClient(name = "eureka-provider", fallback = FeignHystrixServiceFallback.class)
public interface FeignHystrixService {

    @RequestMapping(value = "/hystrix", method = RequestMethod.GET)
    public String callHystrix();

}
