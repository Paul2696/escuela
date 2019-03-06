import java.util.Objects;

public class Coord {
    int x,y;
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return "Coord:[" + x + "," + y +"]";
    }

    @Override
    public boolean equals(Object obj){
        Coord coord = (Coord)obj;
        return coord.x == x && coord.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
