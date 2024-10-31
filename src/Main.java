package src;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader("data/bagnolet_353p_3844n_4221e.json")) {
            Type graphDataType = new TypeToken<GraphData>() {}.getType();
            GraphData graphData = gson.fromJson(reader, graphDataType);

            // Example usage
            System.out.println("Number of nodes: " + graphData.nodes.size());
            System.out.println("Number of edges: " + graphData.edges.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}