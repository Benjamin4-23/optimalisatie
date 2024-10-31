package src;

import com.google.gson.*;
import java.lang.reflect.Type;

public class EdgeDeserializer implements JsonDeserializer<Edge> {
    @Override
    public Edge deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Edge edge = new Edge();
        
        edge.id = jsonObject.get("id").getAsInt();
        edge.edgeType = Edge.EdgeType.valueOf(jsonObject.get("edge_type").getAsString().toUpperCase());
        edge.cost = jsonObject.get("cost").getAsInt();
        edge.endpoint1 = jsonObject.get("endpoint1").getAsInt();
        edge.endpoint2 = jsonObject.get("endpoint2").getAsInt();
        
        return edge;
    }
} 