package zabolotnyi.volodymyr.kafka

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@SpringBootTest
class RepositoryTestContainer @Autowired constructor(
    val repository: GameRepository,
) {

    companion object {
        @Container
        val mySQLContainer = MySQLContainer(DockerImageName.parse("mysql:8.0-debian"))

        @DynamicPropertySource
        @JvmStatic
        fun kafkaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { mySQLContainer.jdbcUrl }
            registry.add("spring.datasource.driverClassName") { mySQLContainer.driverClassName }
            registry.add("spring.datasource.username") { mySQLContainer.username }
            registry.add("spring.datasource.password") { mySQLContainer.password }
        }
    }

    @Test
    fun shouldSaveGame() {
        // given
        val message = Game(name="name")

        // when
        repository.save(Game(name = "name"))

        // then
        val savedGame = repository.findAll()
        assertThat(savedGame.first().name).isEqualTo(message.name)
    }
}
