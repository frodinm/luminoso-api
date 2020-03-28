/*
 * Copyright 2006-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.luminoso.authorization.models.pojo

import org.codehaus.jackson.map.annotate.JsonDeserialize
import org.codehaus.jackson.map.annotate.JsonSerialize
import org.springframework.security.oauth2.common.*
import java.util.*

/**
 * @author Dave Syer
 */
interface OAuth2ApiKey {
    /**
     * The additionalInformation map is used by the token serializers to export any fields used by extensions of OAuth.
     * @return a map from the field name in the serialized token to the value to be exported. The default serializers
     * make use of Jackson's automatic JSON mapping for Java objects (for the Token Endpoint flows) or implicitly call
     * .toString() on the "value" object (for the implicit flow) as part of the serialization process.
     */
    val additionalInformation: Map<String?, Any?>?
    val scope: Set<String?>?
    val tokenType: String?
    val value: String?

    companion object {
        const val BEARER_TYPE = "Bearer"
        const val OAUTH2_TYPE = "OAuth2"

        /**
         * The access token issued by the authorization server. This value is REQUIRED.
         */
        const val ACCESS_TOKEN = "access_token"

        /**
         * The type of the token issued as described in [Section 7.1](http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-7.1). Value is case insensitive.
         * This value is REQUIRED.
         */
        const val TOKEN_TYPE = "token_type"

        /**
         * The scope of the access token as described by [Section 3.3](http://tools.ietf.org/html/draft-ietf-oauth-v2-22#section-3.3)
         */
        const val SCOPE = "scope"
    }
}
