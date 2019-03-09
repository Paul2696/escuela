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

    public Effort createEdge(Node originNode, Node destinationNode){
        Effort effort = new Effort();
        effort.setOriginNode(originNode);
        effort.setDestinationNode(destinationNode);
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

    public Coord getNode(int nodeNumber){
        int coordenada1 = nodeNumber / Mapa.dimensionX;
        int coordenada2 = nodeNumber % Mapa.dimensionY;
        Coord coord = new Coord(coordenada2, coordenada1);
        return coord;
    }
}
