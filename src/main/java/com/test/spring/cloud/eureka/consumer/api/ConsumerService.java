package com.test.spring.cloud.eureka.consumer.api;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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

    /**
     * hystrix测试
     *
     * execution.isolation.thread.timeoutInMilliseconds 超时时长
     *
     */
    @HystrixCommand(fallbackMethod = "callHystrixFallback", commandProperties = {
            // 超时时长
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            // 设置滚动窗口中将使断路器跳闸的最小请求数量
            // 如果此属性值为20，则在窗口时间内（如10s内），如果只收到19个请求且都失败了，则断路器也不会开启。默认20。
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            // 断路器跳闸后，在此值的时间的内，hystrix会拒绝新的请求，只有过了这个时间断路器才会打开闸门
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000"),
            // 设置失败百分比的阈值。如果失败比率超过这个值，则断路器跳闸并且进入fallback逻辑
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            // 设置统计滚动窗口的时间长度
            // 如果此值为10s，将窗口分成10个桶，每个桶表示1s时间(1s*10), 默认值： 10000
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            // 设置统计滚动窗口的桶数量，
            // metrics.rollingStats.timeInMilliseconds % metrics.rollingStats.numBuckets == 0
            // 如：10000/10、10000/20是正确的配置,但是10000/7错误的
            // 在高并发的环境里，每个桶的时间长度建议大于100ms, 默认值：10
            @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
            // 设置执行延迟是否被跟踪，并且被计算在失败百分比中。如果设置为false,则所有的统计数据返回-1, 默认值： true
            @HystrixProperty(name = "metrics.rollingPercentile.enabled", value = "true"),
            // 此属性设置统计滚动百分比窗口的持续时间, 默认值：60000
            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "60000"),
            // 设置统计滚动百分比窗口的桶数量
            // metrics.rollingPercentile.timeInMilliseconds % metrics.rollingPercentile.numBuckets == 0
            // 如： 60000/6、60000/60是正确的配置,但是10000/7错误的
            // 在高并发的环境里，每个桶的时间长度建议大于1000ms, 默认值：6
            @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "6")
    })
    public String callHystrix() {
        long startTime = System.currentTimeMillis();
        logger.info("...start...");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("http://eureka-provider/hystrix", String.class);
        String responstBody = responseEntity.getBody();
        logger.info("/eureka-consumer, call eureka-provider api-hystrix: " + responstBody);
        long endTime = System.currentTimeMillis();
        logger.info("spend time: " + (endTime - startTime) + "ms");
        return responstBody;
    }

    public String callHystrixFallback() {
        return "error";
    }

}
