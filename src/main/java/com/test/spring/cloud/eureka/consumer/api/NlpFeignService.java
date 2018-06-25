package com.test.spring.cloud.eureka.consumer.api;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   NlpFeignService
 * Copyright:
 * Company:     xunlei
 *
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/6/22
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
@FeignClient(name = "eureka-zuul/xlcloud-aq-nlp")
public interface NlpFeignService {

    @RequestMapping(value = "/nlp/api/comment/textfilter", method = RequestMethod.POST)
    public String commentTextfilting(@RequestParam("secretId") String secretId,
                                     @RequestParam("v") String v,
                                     @RequestParam("t") String t,
                                     @RequestParam("content") String content,
                                     @RequestParam("type") String type,
                                     @RequestParam("sig") String sig);

}
