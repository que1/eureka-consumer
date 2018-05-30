package com.test.spring.cloud.eureka.consumer.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName: com.test.spring.cloud.eureka.consumer.api
 * ClassName:   ConsumerController
 * Copyright:
 * Company:     xunlei
 * @author:     queyiwen
 * @version:    1.0
 * @since:      jdk 1.7
 * Create at:   2018/5/30
 * Description:
 * <p>
 * <p>
 * Modification History:
 * Date       Author      Version      Description
 * -------------------------------------------------------------
 *
 *
 */
@RestController
public class ConsumerController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "/call-hello", method = RequestMethod.GET)
    public String callSayHello() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/hello", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hello: " + responstBody);
        return responstBody;
    }

    @RequestMapping(value = "/call-textfilter-get", method = RequestMethod.GET)
    public String callTextfilterGet() {
        // 方法1：{1}&{2}, object...
        // ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/textfilter-get?param1={1}&param2={2}", String.class, "myParam1", "myParam2");
        // 方法2：map
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("param1", "myParam1");
        requestMap.put("param2", "myParam2");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/textfilter-get?param1={param1}&param2={param2}", String.class, requestMap);

        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-textfilter-get: " + responstBody);
        return responstBody;
    }


    @RequestMapping(value = "/call-textfilter-post", method = RequestMethod.GET)
    public String callTextfilterPost() {
        MultiValueMap<String, String> requestMap= new LinkedMultiValueMap<String, String>();
        requestMap.add("param1", "myParam1");
        requestMap.add("param2", "myParam2");
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://eureka-provider/textfilter-post", requestMap, String.class);

        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-textfilter-post: " + responstBody);
        return responstBody;
    }

}
