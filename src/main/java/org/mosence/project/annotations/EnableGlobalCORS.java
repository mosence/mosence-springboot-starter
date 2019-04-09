package org.mosence.project.annotations;

import org.mosence.project.configurations.cors.CorsConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author ：mh-ji
 * @date ：2019/04/08
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Configuration
@Import({CorsConfiguration.class})
public @interface EnableGlobalCORS {
}
