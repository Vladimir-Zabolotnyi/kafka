package zabolotnyi.volodymyr.kafka

import org.springframework.context.annotation.Bean
import org.springframework.kafka.config.TopicBuilder

class KafkaConfig {

    @Bean
    fun gameTopic() =
        TopicBuilder.name("game-event")
            .build()
}
