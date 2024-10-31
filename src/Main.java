package src;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import com.gurobi.gurobi.*;
import java.io.IOException;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        GraphData graphData = loadGraphData("data/bagnolet_353p_3844n_4221e.json");
        if (graphData == null) {
            System.out.println("Failed to load graph data");
            return;
        }
    }


    
    private static GraphData loadGraphData(String filename) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Node.class, new NodeDeserializer())
                .registerTypeAdapter(Edge.class, new EdgeDeserializer())
                .create();

        try (FileReader reader = new FileReader(filename)) {
            Type graphDataType = new TypeToken<GraphData>() {}.getType();
            return gson.fromJson(reader, graphDataType);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

