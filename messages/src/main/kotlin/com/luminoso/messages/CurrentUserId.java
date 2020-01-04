package com.luminoso.messages;


import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Rob Winch
 */
@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression="authentication.tokenAttributes['user_id']")
public @interface CurrentUserId {
}
