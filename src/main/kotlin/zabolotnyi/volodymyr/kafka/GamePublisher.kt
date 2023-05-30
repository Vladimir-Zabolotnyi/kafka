package zabolotnyi.volodymyr.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class GamePublisher(
    private val kafkaTemplate: KafkaTemplate<String, Game>,
) {

    fun publish(game: Game) =
        kafkaTemplate.send("game-event", game.id.toString(), game)
}
