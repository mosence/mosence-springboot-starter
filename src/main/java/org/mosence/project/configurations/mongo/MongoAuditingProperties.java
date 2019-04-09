package org.mosence.project.configurations.mongo;

import lombok.Data;
import org.mosence.project.common.utils.SpringSecurityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ：mosence
 * @date ：2019/03/20
 */
@ConfigurationProperties(prefix = "eking.mongodb.audit")
@Data
public class MongoAuditingProperties {
    private String securityContentUsername = SpringSecurityUtils.ACCOUNT_ID;
}
