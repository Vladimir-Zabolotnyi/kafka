package zabolotnyi.volodymyr.kafka

import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<Game, Long>
