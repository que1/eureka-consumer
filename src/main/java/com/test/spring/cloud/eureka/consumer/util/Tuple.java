package com.test.spring.cloud.eureka.consumer.util;

/**
 * ProjectName: com.xlcloud.aq.realtime.textfilter.util
 * ClassName:   Tuple
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
public class Tuple<A, B> implements Comparable<Tuple<A, B>> {

    private final A first;
    private final B second;

    public Tuple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;

    }

    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    @Override
    public int compareTo(Tuple<A, B> o) {
        if (this.first == null) {
            return -1;
        }

        if (o == null || o.getFirst() == null) {
            return 1;
        }

        return this.first.toString().compareTo(o.getFirst().toString());

    }


}
