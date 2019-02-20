public class Coord {
    int x,y;
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public String toString() {
        return "Coord:[" + x + "," + y +"]";
    }

    public boolean equals(Object obj){
        Coord coord = (Coord)obj;
        return coord.x == x && coord.y == y;
    }
}
