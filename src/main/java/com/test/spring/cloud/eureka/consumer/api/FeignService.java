package com.test.spring.cloud.eureka.consumer.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   FeignService
 * Copyright:
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/6/1
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
@FeignClient(name = "eureka-provider")
public interface FeignService {

    /**
     * 不带参数
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello();


    @RequestMapping(value = "textfilter-get", method = RequestMethod.GET)
    public String filterGet(@RequestParam("param1") String param1, @RequestParam("param2") String param2);




}
