package zabolotnyi.volodymyr.kafka

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GameController(
    private val gamePublisher: GamePublisher,
    private val gameRepository: GameRepository,
) {

    @PostMapping("/games")
    fun send() =
        gamePublisher.publish(Game(name = "id"))

    @GetMapping("/games/get")
    fun get() =
        gameRepository.findAll()
}
