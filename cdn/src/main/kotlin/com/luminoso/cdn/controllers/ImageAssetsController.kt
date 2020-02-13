package com.luminoso.cdn.controllers

import com.luminoso.cdn.service.FileService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Controller
@RequestMapping("/image")
class ImageAssetsController(private val fileService: FileService) {

    @PostMapping("/upload")
    fun upload(@RequestPart fileParts: Mono<FilePart>): Mono<ResponseEntity<Map<String,String>>> {
        return fileService.uploadFile(fileParts).map {
            val body = mapOf<String,String>(Pair("id", it.toHexString()))
            ResponseEntity.ok().body(body)
        }
    }

    @GetMapping("/assets/{id}")
    fun getAsset(@PathVariable id: String, exchange: ServerWebExchange): Mono<ResponseEntity<Resource>> {
        return fileService.getFile(id).map { resource ->
            ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .body(resource as Resource)
        }

    }
}