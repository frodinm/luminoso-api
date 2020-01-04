/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.luminoso.usersmanagement.models.pojo.representations.idm.authorization

/**
 *
 * A bounded extent of access that is possible to perform on a resource set. In authorization policy terminology,
 * a scope is one of the potentially many "verbs" that can logically apply to a resource set ("object").
 *
 *
 * For more details, [OAuth-resource-reg](https://docs.kantarainitiative.org/uma/draft-oauth-resource-reg.html#rfc.section.2.1).
 *
 */
data class ScopeRepresentation(val id: String? = null,
                               val policies: List<PolicyRepresentation>? = null,
                               val resources: List<ResourceRepresentation>? = null,
                               val displayName: String? = null)