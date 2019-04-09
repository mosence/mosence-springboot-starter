package org.mosence.project.common.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.*;

/**
 * @author ：mosence
 * @date ：2019/03/20
 */
@SuppressWarnings("unchecked")
public class SpringSecurityUtils {

    public static final String ADMIN_ID="0";

    public static final String AUTH_NO_LIMIT_INTERFACE_ID="01";

    public static final String CLIENT_ID = "client-id";

    public static final String ACCOUNT_ID = "account-id";

    public static final String USERNAME = "username";

    public static final String AUTHORITY_SET = "auth";

    public static final String AUTHORITY_GROUP_SET = "auth-group";

    public static final String USER_INFO = "info";

    public static final String DEPARTMENT_INFO = "dept";

    /**
     * 获取 oauth2 用户信息体<br/>
     * 该信息体来源为 配置中的 security.oauth2.resource.user-info-uri
     *
     * @return 信息体
     */
    public static Map<String, Object> userDetailsMap() {
        Map<String, Object> userMaps = new LinkedHashMap<>(0);
        if (isReactive()) {
            //如果使用 spring-webflux 则使用 ReactiveSecurityContextHolder
            ReactiveSecurityContextHolder.getContext()
                    .filter(Objects::nonNull)
                    .map(SecurityContext::getAuthentication)
                    .filter(authentication -> authentication instanceof OAuth2Authentication)
                    .map(authentication -> (OAuth2Authentication) authentication)
                    .map(authentication -> (Map<String, Object>) authentication.getDetails())
                    .doOnNext(userMaps::putAll);
        } else {
            //如果使用 spring-webmvc 则使用 SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof OAuth2Authentication) {
                OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
                userMaps.putAll(Optional.ofNullable(auth2Authentication.getUserAuthentication().getDetails())
                        .map(details -> ((Map<String, Object>) details)).get());
            }
        }
        return userMaps;
    }

    private static <T> T foundByUserDetails(String field) {
        Map<String, Object> userMaps = userDetailsMap();
        if (!userMaps.containsKey(field)) {
            throw new IllegalArgumentException("cannot found " + field + "! do you login in this system?");
        }
        return (T) userDetailsMap().get(field);
    }

    public static String username() {
        return foundByUserDetails(USERNAME);
    }

    public static String accountId() {
        return foundByUserDetails(ACCOUNT_ID);
    }

    public static List<Map<String, Object>> auths() {
        return foundByUserDetails(AUTHORITY_SET);
    }

    public static List<Map<String, Object>> authGroups() {
        return foundByUserDetails(AUTHORITY_GROUP_SET);
    }

    public static Map<String, Object> userinfo() {
        return foundByUserDetails(USER_INFO);
    }

    private static boolean isReactive() {
        try {
            Class.forName("reactor.core.publisher.Mono");
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private static ThreadLocal<Authentication> saveAuthentication = new ThreadLocal<>();

    private static OAuth2Authentication systemAuthentication;
    static {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken("SYSTEM", "");
        authenticationToken.setDetails(new HashMap<String,Object>(){{
            put(ACCOUNT_ID,"0");
        }});
        systemAuthentication = new OAuth2Authentication(
                new OAuth2Request(
                        new HashMap<>(0), "SYSTEM",
                        new HashSet<>(0), true,
                        new HashSet<>(0), new HashSet<>(0), "",
                        new HashSet<>(0),
                        new HashMap<>(0)),
                authenticationToken
        );
    }

    public static void reAuthentication() {
        SecurityContextHolder.getContext().setAuthentication(saveAuthentication.get());

    }

    public static void userSystemAuthentication() {
        saveAuthentication.set(SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.getContext().setAuthentication(systemAuthentication);
    }
}
