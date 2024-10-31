package src;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import com.gurobi.gurobi.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        DataReader reader = new DataReader("data/bretigny_62p_1147n_1235e.json");

        // Load our data
        reader.loadData();

        // Perform the transformation
        reader.transform();

        // Initialize the Gurobi model

    }
}