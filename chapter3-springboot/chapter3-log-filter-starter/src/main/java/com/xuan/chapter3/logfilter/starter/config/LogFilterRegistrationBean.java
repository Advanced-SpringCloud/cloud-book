package com.xuan.chapter3.logfilter.starter.config;

import com.xuan.chapter3.logfilter.starter.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * Created by xuan on 2018/4/9.
 */
public class LogFilterRegistrationBean extends FilterRegistrationBean<LogFilter> {

    public LogFilterRegistrationBean() {
        super();
        this.setFilter(new LogFilter()); //添加LogFilter过滤器
        this.addUrlPatterns("/*"); // 匹配所有路径
        this.setName("LogFilter"); // 定义过滤器名
        this.setOrder(1); // 设置优先级

    }
}
