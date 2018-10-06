package com.xuan.chapter3.logfilter.starter.annotation;

import com.xuan.chapter3.logfilter.starter.config.EnableLogFilterImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启过滤器日志
 *
 * Created by xuan on 2018/4/9.
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableLogFilterImportSelector.class) //引入LogFilterAutoConfiguration配置类
public @interface EnableLogFilter {
}
