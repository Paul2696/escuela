import java.util.List;
import java.util.Objects;

public class Node implements Comparable<Node>{
    public Effort[] neighbor = new Effort[8];
    public Coord coord;
    public double distance;
    public int nodeNumber;
    private int neighborCounter = 0;

    public Node(Coord coord){
        this.coord = coord;
        nodeNumber = coord.x + Mapa.dimensionX * coord.y;
    }

    public void setNeighbor(Effort effort){
        if(neighborCounter == neighbor.length){
            return;
        }
        for(int i = 0; i < neighborCounter; i++){
            if(effort.equals(neighbor[i])){
                neighbor[i] = effort;
                return;
            }
        }
        neighbor[neighborCounter] = effort;
        neighborCounter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return coord.equals(node.coord);
    }

    @Override
    public int compareTo(Node node) {
        if(distance > node.distance){
            return 1;
        }
        if(distance < node.distance){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "coord=" + coord +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord);
    }
}
