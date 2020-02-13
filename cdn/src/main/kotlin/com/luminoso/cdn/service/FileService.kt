package com.luminoso.cdn.service

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FileService(private val gridFsTemplate: ReactiveGridFsTemplate) {

    fun uploadFile(fileParts: Mono<FilePart>): Mono<ObjectId> {
        return fileParts.flatMap { part ->
            gridFsTemplate.store(part.content(),part.filename())
        }
    }

    fun getFile(id: String): Mono<ReactiveGridFsResource> {
        return gridFsTemplate.findOne(query(where("_id").`is`(id))).log()
            .flatMap(gridFsTemplate::getResource)
    }
}