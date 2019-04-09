package org.mosence.project.configurations.mongo;

import org.mosence.project.common.utils.SpringSecurityUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * @author ：mosence
 * @date ：2019/03/20
 */
@ConditionalOnClass(EnableMongoAuditing.class)
@EnableMongoAuditing
@EnableConfigurationProperties(MongoAuditingProperties.class)
public class MongoAuditingConfiguration {

    private final MongoAuditingProperties mongoAuditingProperties;

    public MongoAuditingConfiguration(MongoAuditingProperties mongoAuditingProperties) {
        this.mongoAuditingProperties = mongoAuditingProperties;
    }

    @Bean
    @ConditionalOnClass({AuditorAware.class, SecurityContextHolder.class})
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SpringSecurityUtils.userDetailsMap().get(mongoAuditingProperties.getSecurityContentUsername()).toString());
    }
}
