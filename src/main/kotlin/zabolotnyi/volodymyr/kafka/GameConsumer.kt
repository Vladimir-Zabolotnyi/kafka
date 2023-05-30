package zabolotnyi.volodymyr.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class GameConsumer(
    private val gameRepository: GameRepository,
) {

    @KafkaListener(
        topics = ["game-event"],
        groupId = "game-group-id",
    )
    fun consume(message: Game) =
        gameRepository.save(message);
}
