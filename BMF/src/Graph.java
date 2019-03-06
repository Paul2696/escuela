import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<Coord, Node> nodes = new HashMap<>();
    List<Effort>  edges = new ArrayList<>();

    public void addNode(Node node){
        nodes.put(node.coord, node);
    }

    public Effort createEdge(Node node1, Node node2){
        Effort effort = new Effort();
        effort.setNode1(node1);
        effort.setNode2(node2);
        edges.add(effort);
        return effort;
    }

    public Node getNode(Coord coord){
        return nodes.get(coord);
    }

    public Effort getEdgeIfExists(Effort effort){
        for(Effort eff : edges){
            if(eff.equals(effort)){
                return eff;
            }
        }
        return null;
    }
}
