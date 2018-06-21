package com.test.spring.cloud.eureka.consumer.api;

import com.test.spring.cloud.eureka.consumer.util.GenerateSigUtils;
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

import java.io.UnsupportedEncodingException;
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

    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private FeignService feignService;
    @Autowired
    private FeignHystrixService feignHystrixService;

    @LoadBalanced
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试接口
     *
     */
    @RequestMapping(value = "/call-hello", method = RequestMethod.GET)
    public String callSayHello() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/hello", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hello: " + responstBody);
        return responstBody;
    }

    /**
     * 测试get方法
     */
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

    /**
     * 测试post方法
     *
     */
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


    /**
     * 测试hystrix
     *
     */
    @RequestMapping(value = "/call-hystrix", method = RequestMethod.GET)
    public String callHystrix() {

        //标准方法: restTemplate应该放在service里面，hystrix
        return this.consumerService.callHystrix();

        /*
        long startTime = System.currentTimeMillis();
        logger.info("...start...");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/hystrix", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hystrix: " + responstBody);
        long endTime = System.currentTimeMillis();
        logger.info("spend time: " + (endTime - startTime) + "ms");
        return responstBody;
        */
    }

    public String hystrixFallback() {
        return "error";
    }


    /**
     *
     * feign测试
     *
     */
    @RequestMapping(value = "/feign-test", method = RequestMethod.GET)
    public String feignTest() {
        logger.info("call api-feign-test");
        return this.feignService.sayHello();
    }

    /**
     * feign测试，带参数，get方法
     *
     */
    @RequestMapping(value = "/feign-test-param-get", method = RequestMethod.GET)
    public String feignTestParamsGet() {
        logger.info("call api-textfilter-get");
        return this.feignService.filterGet("testMyParam1", "testMyParam2");
    }

    /**
     * feign测试，带参数，post方法
     *
     */
    @RequestMapping(value = "/feign-test-param-post", method = RequestMethod.GET)
    public String feignTestParamsPost() {
        logger.info("call api-textfilter-post");
        return this.feignService.filterPost("testMyParam1", "testMyParam2");
    }


    /**
     * feign-hystrix测试
     *
     */
    @RequestMapping(value = "/feign-hystrix-test", method = RequestMethod.GET)
    public String feignHystrixTest() {
        logger.info("call api-hystrix by feign");
        String result = this.feignHystrixService.callHystrix();
        logger.info("get result: " + result);
        return result;
    }

    /**
     * zuul测试
     * 通过zuul的url来访问
     *
     */
    @RequestMapping(value = "/zuul-test", method = RequestMethod.GET)
    public String zuulTest() {
        logger.info("call zuul-service");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-zuul/eureka-provider/hello?sig=123456", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hello: " + responstBody);
        return responstBody;
    }


    @RequestMapping(value = "/nlp-test", method = RequestMethod.GET)
    public String nlpTest() throws UnsupportedEncodingException {
        String content = "皮皮虾你们好";

        Map<String, String> params = new HashMap<String, String>();
        // 1.设置公共参数
        params.put("secretId", GenerateSigUtils.SECRETID);
        params.put("v", GenerateSigUtils.VERSION);
        params.put("t", String.valueOf(System.currentTimeMillis()));
        // 2.业务参数
        params.put("content", content);
        params.put("type", "basic-comment-text-filter");
        params.put("param1", "myParam1");
        params.put("param2", "myParam2");
        // 3.sig值
        String sig = GenerateSigUtils.genSignature(GenerateSigUtils.SECRETKEY, params);
        params.put("sig", sig);
        params.put("sig", "123");
        // 发送请求（无法走zuul，只能直接走provider的服务）
        // 错误无法获取参数
        String result = this.restTemplate.postForObject("http://eureka-zuul/eureka-provider/textfilter-post", Map.class, String.class, params);

        /*
        MultiValueMap<String, String> requestMap= new LinkedMultiValueMap<String, String>();
        requestMap.add("secretId", GenerateSigUtils.SECRETID);
        requestMap.add("v", GenerateSigUtils.VERSION);
        requestMap.add("t", String.valueOf(System.currentTimeMillis()));
        requestMap.add("content", content);
        requestMap.add("type", "basic-comment-text-filter");
        requestMap.add("param1", "myParam1");
        requestMap.add("param2", "myParam2");
        requestMap.add("sig", sig);
        // 走zuul
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("http://eureka-zuul/eureka-provider/textfilter-post", requestMap, String.class);
        String result = responseEntity.getBody();
        */
        return result;
    }


}
