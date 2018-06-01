package com.test.spring.cloud.eureka.consumer.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   ConsumerService
 * Copyright:
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/5/31
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
@Service
public class ConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "callHystrixFallback")
    public String callHystrix() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/hystrix", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hystrix: " + responstBody);
        return responstBody;
    }

    public String callHystrixFallback() {
        return "error";
    }

}
