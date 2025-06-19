import gameCommons.GameBase
import gameCommons.GameConfig
import gameCommons.DataPack
import gameCommons.Player

class Lobby(
    id: Long,
    val gameConfig: GameConfig,
    val game: GameBase? = null,
    val players: MutableList<Player> = mutableListOf()
    ): DataPack(id, gameConfig.name)