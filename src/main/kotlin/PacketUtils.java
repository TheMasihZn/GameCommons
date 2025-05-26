//import com.google.gson.Gson;
//import com.google.gson.JsonElement;
//import gameCommons.Card;
//import gameCommons.Player;
//import serverCommons.Packet;
//
//import java.util.Map;
//
//public class PacketUtils {
//
//    public interface PacketCallback {
//        void onInit();
//        void onReceivePacket(Packet packet);
//        void onException(Exception e);
//    }
//
//    public static Packet deserialize(Packet receivedPacket) {
//        Gson gson = new Gson();
//        Map<Packet.Type, Object> args = receivedPacket.getArgs();
//
//        Packet.Type senderKey = Packet.Type.Sender;
//        if (args.containsKey(senderKey)) {
//            JsonElement json = gson.toJsonTree(args.get(senderKey));
//            args.put(senderKey, gson.fromJson(json, Player.class));
//        }
//
//        Packet.Type cardKey = Packet.Type.Card;
//        if (args.containsKey(cardKey)) {
//            JsonElement json = gson.toJsonTree(args.get(cardKey));
//            args.put(cardKey, gson.fromJson(json, Card.class));
//        }
//
//        Packet.Type gameFrameKey = Packet.Type.GameFrame;
//        if (args.containsKey(gameFrameKey)) {
//            JsonElement json = gson.toJsonTree(args.get(gameFrameKey));
//            gson.fromJson(json, GameFrame.class);  // Deserialized but no assignment, as in Kotlin code
//        }
//
//        return receivedPacket;
//    }
//}
