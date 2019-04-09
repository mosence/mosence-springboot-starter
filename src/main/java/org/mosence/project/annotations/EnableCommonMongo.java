package org.mosence.project.annotations;

import org.mosence.project.configurations.mongo.MongoAuditingConfiguration;
import org.mosence.project.configurations.mongo.MongoNoClassFieldConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>自定义Mongodb功能注解</p>
 * <p>1、使用配置 eking.mongodb.securityContentUsername 配置从SecurityContext中取得 {@link org.springframework.data.annotation.CreatedBy}</p>
 * <p>2、配置 实体在存入Mongodb中 不存储 _class字段 </p>
 * @author ：mosence
 * @date ：2019/03/20
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Configuration
@Import({MongoAuditingConfiguration.class, MongoNoClassFieldConfiguration.class})
public @interface EnableCommonMongo {
}
