package com.luminoso.candidatemanagement

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import reactor.blockhound.BlockHound


@SpringBootApplication
class CandidateManagementApplication {
    init {
        BlockHound.install()
    }
}

fun main(args: Array<String>) {
    runApplication<CandidateManagementApplication>(*args)
}
