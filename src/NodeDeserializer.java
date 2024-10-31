package src;

import com.google.gson.*;
import java.lang.reflect.Type;

public class NodeDeserializer implements JsonDeserializer<Node> {
    @Override
    public Node deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Node node = new Node();
        
        node.id = jsonObject.get("id").getAsInt();
        JsonArray coords = jsonObject.get("coords").getAsJsonArray();
        node.x = coords.get(0).getAsDouble();  // longitude
        node.y = coords.get(1).getAsDouble();  // latitude
        node.nodeType = Node.NodeType.valueOf(jsonObject.get("node_type").getAsString().toUpperCase());
        
        return node;
    }
} 