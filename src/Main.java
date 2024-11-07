package src;

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
        solve(reader);
    }

    public static void solve(DataReader reader){
        List<Node> nodes = reader.nodes.values().stream().toList();
        List<Edge> directedEdges =  reader.edges.values().stream().toList();
        try {
            GRBEnv env = new GRBEnv(true);
            env.set("logFile", "IPModelOptimizer.log");
            env.start();
            GRBModel model = new GRBModel(env);

            List<Integer> prospects = nodes.stream()
                    .filter(n -> n.nodeType == Node.NodeType.PROSPECT)
                    .map(n -> n.id)
                    .toList();

            HashMap<String, GRBVar> xij = new HashMap<>();
            HashMap<String, HashMap<Integer, GRBVar>> ykij = new HashMap<>();

            for (Edge edge : directedEdges) {
                String edgeKey = edge.toString();
                xij.put(edgeKey, model.addVar(0, 1, 0, GRB.BINARY, "x_" + edgeKey));

                HashMap<Integer, GRBVar> ykVariables = new HashMap<>();
                for (int k : prospects) {
                    ykVariables.put(k, model.addVar(0, 1, 0, GRB.BINARY, "y_" + k + "_" + edgeKey));
                }
                ykij.put(edgeKey, ykVariables);
            }

            //objectief
            GRBLinExpr obj = new GRBLinExpr();
            for (Edge edge : directedEdges) {
                String edgeKey = edge.toString();
                obj.addTerm(edge.cost, xij.get(edgeKey));
            }
            model.setObjective(obj, GRB.MINIMIZE);

            for (int k : prospects) {
                // Constraint (2): Each prospect has exactly one off-street edge
                GRBLinExpr expr = new GRBLinExpr();
                for (Edge edge : reader.nodes.get(k).incomingEdges.values()) {
                    String edgeKey = edge.toString();
                    expr.addTerm(1, ykij.get(edgeKey).get(k));
                }
                model.addConstr(expr, GRB.EQUAL, 1, "offStreet_" + k);

                // Constraint (3): Path from root to each prospect
                expr = new GRBLinExpr();
                for (Edge edge : reader.nodes.get(reader.rootNode.id).outgoingEdges.values()) {
                    String edgeKey = edge.toString();
                    expr.addTerm(1, ykij.get(edgeKey).get(k));
                }
                model.addConstr(expr, GRB.EQUAL, 1, "pathFromRoot_" + k);

                // Constraint (4): Path continuity for each prospect
                for (Integer i : reader.nodes.keySet()) {
                    if (i.equals(reader.rootNode.id) || prospects.contains(i)) continue;
                    expr = new GRBLinExpr();
                    for (Edge edge : reader.nodes.get(i).outgoingEdges.values()) {
                        expr.addTerm(1, ykij.get(edge.toString()).get(k));
                    }
                    for (Edge edge : reader.nodes.get(i).incomingEdges.values()) {
                        expr.addTerm(-1, ykij.get(edge.toString()).get(k));
                    }
                    model.addConstr(expr, GRB.EQUAL, 0, "continuity_" + i + "_" + k);
                }
            }

            // Constraints (5) and (6): Link xij and ykij for each edge
            for (Edge edge : directedEdges) {
                String edgeKey = edge.toString();
                for (int k : prospects) {
                    model.addConstr(ykij.get(edgeKey).get(k), GRB.LESS_EQUAL, xij.get(edgeKey), "link1_" + edgeKey + "_" + k);
                }
                GRBLinExpr expr = new GRBLinExpr();
                for (int k : prospects) {
                    expr.addTerm(1, ykij.get(edgeKey).get(k));
                }
                model.addConstr(xij.get(edgeKey), GRB.LESS_EQUAL, expr, "link2_" + edgeKey);
            }

            // Optimize the model
            model.optimize();

            // Output results
            /*for (Edge edge : directedEdges) {
                String edgeKey = edge.toString();
                if (xij.get(edgeKey).get(GRB.DoubleAttr.X) == 1.0) {
                    System.out.println("Edge " + edgeKey + " is used.");
                }
            }*/

            // Dispose of the model and environment
            model.dispose();
            env.dispose();
        } catch (GRBException e) {
            e.printStackTrace();
        }
    }
}