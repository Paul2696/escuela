import java.util.Objects;

public class Node {
    public Effort[] neighbor = new Effort[7];
    public Coord coord;

    public Node(Coord coord){
        this.coord = coord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return coord.equals(node.coord);
    }
}
