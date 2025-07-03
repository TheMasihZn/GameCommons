import gameCommons.DataPack
import gameCommons.GameBase
import gameCommons.GameConfig
import gameCommons.Player
import java.io.Serializable

class Lobby(
    id: Long,
    icon: Int,
    val gameConfig: GameConfig,
    val game: GameBase? = null,
    val players: MutableList<Player> = mutableListOf()
) : DataPack(id, gameConfig.name, icon) {

    companion object {
        fun MainLobby() = Lobby(-1L, -1, GameConfig(-1, -1, -1, "MainLobby"))
    }
}