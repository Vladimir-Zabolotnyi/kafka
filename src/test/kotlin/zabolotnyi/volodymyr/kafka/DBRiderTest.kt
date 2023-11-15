package zabolotnyi.volodymyr.kafka

import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.core.api.dataset.DataSetFormat
import com.github.database.rider.core.api.dataset.ExpectedDataSet
import com.github.database.rider.core.api.dataset.SeedStrategy
import com.github.database.rider.core.api.exporter.ExportDataSet
import com.github.database.rider.spring.api.DBRider
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

@DBRider
@Testcontainers
@SpringBootTest
class DBRiderTest @Autowired constructor(
    val repository: GameRepository,
) {

    companion object {
        @Container
        val mySQLContainer = MySQLContainer(DockerImageName.parse("mysql:8.0-debian"))

        @JvmStatic
        @DynamicPropertySource
        fun kafkaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { mySQLContainer.jdbcUrl }
            registry.add("spring.datasource.driverClassName") { mySQLContainer.driverClassName }
            registry.add("spring.datasource.username") { mySQLContainer.username }
            registry.add("spring.datasource.password") { mySQLContainer.password }
        }
    }

    @Test
    @DataSet(value = ["games.yml"], cleanBefore = true, cleanAfter = true, strategy = SeedStrategy.CLEAN_INSERT)
    fun `should find all games from yml`() {
        // given
        val expected = listOf(Game(id = 1, name = "Game1"), Game(id = 2, name = "Game2"))

        // when
        val result = repository.findAll()

        // then
        assertThat(result).containsExactlyInAnyOrder(*expected.toTypedArray())
    }

    @Test
    @DataSet(value = ["games.xml"], cleanBefore = true, cleanAfter = true)
    fun `should find all games from xml`() {
        // given
        val expected = listOf(Game(id = 1, name = "Game1"), Game(id = 2, name = "Game2"))

        // when
        val result = repository.findAll()

        // then
        assertThat(result).containsExactlyInAnyOrder(*expected.toTypedArray())
    }

    @Test
    @DataSet(provider = GameDataSet::class)
    fun `should find all games from java class`() {
        // given
        val expected = listOf(Game(name = "Game65"), Game(name = "Game66"))

        // when
        val result = repository.findAll()

        // then
        assertThat(result).usingElementComparatorIgnoringFields("id")
            .containsExactlyInAnyOrder(*expected.toTypedArray())
    }

    @Test
    @ExpectedDataSet("expected-games.yml", ignoreCols = ["id"])
    @ExportDataSet(format = DataSetFormat.JSON, includeTables = ["game"], outputName = "target/result-games")
    fun `should save games, compare with yml and export to file`() {
        // given
        val games = listOf(Game(name = "Game5"), Game(name = "Game6"), Game(name = "Game7"))

        // when
        repository.saveAll(games)
    }
}
