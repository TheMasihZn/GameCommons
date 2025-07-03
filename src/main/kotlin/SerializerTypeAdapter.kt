import com.google.gson.*
import com.google.gson.reflect.TypeToken
import gameCommons.Player
import serverCommons.Packet
import java.lang.reflect.Type


@Suppress("UNCHECKED_CAST")
class SerializerTypeAdapter<T : Packet> : JsonDeserializer<T>, JsonSerializer<T> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): T {
        val src = json.asJsonObject
        val type = src.get("class")?.asString
            ?: throw JsonParseException("Missing 'type': $json")
        return when (type) {
            "LobbyListPacket" -> {
                LobbyListPacket(
                    context.deserialize<Set<Lobby>>(src.get("lobbies"), object : TypeToken<Set<Lobby>>() {}.type)
                ) as T
            }
            "LobbyInfoPacket" -> {
                LobbyInfoPacket(
                    context.deserialize(src.get("lobby"), object : TypeToken<Lobby>() {}.type)
                ) as T
            }
            "FunctionPacket" -> {
                FunctionPacket(
                    context.deserialize(
                        src.get("function"),
                        FunctionPacket.Function::class.java
                    )
                ) as T
            }

            "Packet" -> {
                Packet(
                    context.deserialize(
                        src.get("packetTypeKey"),
                        Packet.Type::class.java
                    ),
                    context.deserialize(
                        src.get("sender"),
                        Player::class.java
                    )
                ) as T
            }
            "AckPacket" -> {
                AckPacket(
                    context.deserialize(
                        src.get("hash"),
                        Int::class.java
                    ),
                    context.deserialize(
                        src.get("sender"),
                        Player::class.java
                    )
                ) as T
            }

            else -> throw JsonParseException("Unsupported type $type")
        }
    }

    override fun serialize(
        src: T,
        type: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonObject = JsonObject()
        jsonObject.add("packetTypeKey", context.serialize(src.packetType, Packet.Type::class.java))
        jsonObject.add("sender", context.serialize(src.sender, Player::class.java))
        jsonObject.addProperty("class", src::class.java.simpleName)

        when (src) {
            is LobbyListPacket -> jsonObject.add(
                "lobbies",
                context.serialize(src.lobbies, object : TypeToken<List<Lobby>>() {}.type)

            )
            is LobbyInfoPacket -> jsonObject.add(
                "lobby",
                context.serialize(src.lobby, Lobby::class.java)
            )
            is FunctionPacket -> jsonObject.add(
                "function",
                context.serialize(src.function, object : TypeToken<FunctionPacket.Function>() {}.type)
            )
            is AckPacket -> jsonObject.add(
                "hash",
                context.serialize(src.hash, Int::class.java)
            )
        }
        println(jsonObject)
        return jsonObject
    }
}