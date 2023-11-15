package zabolotnyi.volodymyr.kafka

import com.github.database.rider.core.api.dataset.DataSetProvider
import com.github.database.rider.core.dataset.builder.DataSetBuilder
import org.dbunit.dataset.IDataSet

class GameDataSet : DataSetProvider {
//    override fun provide(): IDataSet =
//        DataSetBuilder().table("game")
//            .columns("name")
//            .values("Game65")
//            .values("Game66")
//            .build()

    override fun provide(): IDataSet =
        DataSetBuilder().table("game")
            .row()
            .column("name", "Game65")
            .row()
            .column("name", "Game66")
            .build()
}
