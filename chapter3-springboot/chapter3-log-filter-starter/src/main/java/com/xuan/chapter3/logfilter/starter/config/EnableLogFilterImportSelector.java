package com.xuan.chapter3.logfilter.starter.config;

import com.xuan.chapter3.logfilter.starter.annotation.EnableLogFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by xuan on 2018/4/9.
 */

public class EnableLogFilterImportSelector
        implements DeferredImportSelector, BeanClassLoaderAware, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(EnableLogFilterImportSelector.class);

    private ClassLoader beanClassLoader;

    private Class annotationClass = EnableLogFilter.class;

    private Environment environment;


    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        // 是否生效，默认为true
        if (!isEnabled()) {
            return new String[0];
        }
        // 获取注解中的属性
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(this.annotationClass.getName(), true));

        Assert.notNull(attributes, "No " + getSimpleName() + " attributes found. Is "
                + metadata.getClassName() + " annotated with @" + getSimpleName() + "?");
        // 从spring.factories中获取所有通过EnableLogFilter注解引入的自动配置类，并进行去重操作
        List<String> factories = new ArrayList<>(new LinkedHashSet<>(SpringFactoriesLoader
                .loadFactoryNames(this.annotationClass, this.beanClassLoader)));

        if (factories.isEmpty() && !hasDefaultFactory()) {
            throw new IllegalStateException("Annotation @" + getSimpleName()
                    + " found, but there are no implementations. Did you forget to include a starter?");
        }

        if (factories.size() > 1) {

            logger.warn("More than one implementation " + "of @" + getSimpleName()
                    + " (now relying on @Conditionals to pick one): " + factories);
        }

        return factories.toArray(new String[factories.size()]);
    }

    protected boolean hasDefaultFactory() {
        return false;
    }

    protected boolean isEnabled() {
        return true;
    }

    ;

    protected String getSimpleName() {
        return this.annotationClass.getSimpleName();
    }

    protected Class<EnableLogFilter> getAnnotationClass() {
        return this.annotationClass;
    }

    protected Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }


}
