package com.luminoso.messages.config

import com.fasterxml.jackson.core.type.TypeReference
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import java.util.*

@Configuration
class KafkaConfig {

    @Bean
    fun consumerConfigs(): Map<String, Any?> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = "message"
        return props
    }

    @Bean
    fun consumerFactory(): DefaultKafkaConsumerFactory<String, Map<String, Map<String, Any>>> {
        val type: TypeReference<Map<String, Map<String, Any>>> = object : TypeReference<Map<String, Map<String, Any>>>() {}
        val deser = JsonDeserializer(type, false)
        return DefaultKafkaConsumerFactory(consumerConfigs(), StringDeserializer(), deser)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Map<String, Map<String, Any>>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, Map<String, Map<String, Any>>>()
        factory.consumerFactory = consumerFactory()
        return factory
    }
}