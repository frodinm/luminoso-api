package com.luminoso.analytics.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod


@FeignClient(value = "authorization")
interface AuthorizationClient {
    @RequestMapping(method = [RequestMethod.POST], value = ["/apikey"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun validateApiKey()
}
