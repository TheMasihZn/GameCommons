import gameCommons.GameBase
import gameCommons.GameConfig
import gameCommons.Player

data class Lobby(
    val id: Long,
    val gameConfig: GameConfig,
    val game: GameBase? = null,
    val players: MutableList<Player> = mutableListOf()
    )