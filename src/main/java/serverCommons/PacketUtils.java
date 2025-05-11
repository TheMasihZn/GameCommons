package serverCommons;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import gameCommons.Card;
import gameCommons.GameFrame;
import gameCommons.Player;

import java.util.Map;

public class PacketUtils {

    public interface PacketCallback {
        void onInit();
        void onReceivePacket(Packet packet);
        void onException(Exception e);
    }

    public static Packet deserialize(Packet receivedPacket) {
        Gson gson = new Gson();
        Map<Packet.PacketKeys, Object> args = receivedPacket.getArgs();

        Packet.PacketKeys senderKey = Packet.PacketKeys.Sender;
        if (args.containsKey(senderKey)) {
            JsonElement json = gson.toJsonTree(args.get(senderKey));
            args.put(senderKey, gson.fromJson(json, Player.class));
        }

        Packet.PacketKeys cardKey = Packet.PacketKeys.Card;
        if (args.containsKey(cardKey)) {
            JsonElement json = gson.toJsonTree(args.get(cardKey));
            args.put(cardKey, gson.fromJson(json, Card.class));
        }

        Packet.PacketKeys gameFrameKey = Packet.PacketKeys.GameFrame;
        if (args.containsKey(gameFrameKey)) {
            JsonElement json = gson.toJsonTree(args.get(gameFrameKey));
            gson.fromJson(json, GameFrame.class);  // Deserialized but no assignment, as in Kotlin code
        }

        return receivedPacket;
    }
}
