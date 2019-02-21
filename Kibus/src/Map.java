import java.util.Arrays;
import java.util.Random;

public class Map {
    private int dimensionX = 15;
    private int dimensionY = 15;
    private MapListener listener;
    public static final int OBSTACLE = 5;
    public static final int KIBUS = 6;
    public static final int HOUSE = 7;
    public static final int FREE = 0;
    int[][] map = new int[getDimensionX()][getDimensionY()];

    public void setObstacles(int percentageObstacles){
        clear();
        Random r = new Random();
        int numberObstacles = 0;
        numberObstacles = getMapSize() * percentageObstacles / 100;
        while(numberObstacles != 0){
            int random = r.nextInt(((getMapSize() - 1) - 0) + 1) + 0;
            int coordenada1 = random / dimensionX;
            int coordenada2 = random % dimensionY;
            map[coordenada2][coordenada1] = OBSTACLE;
            numberObstacles--;
        }
        listener.mapChanged(false);
    }

    public int get(int x, int y){
        return map[x][y];
    }


    public int getDimensionX() {
        return dimensionX;
    }

    public int getDimensionY() {
        return dimensionY;
    }

    public int getMapSize(){
        return dimensionX * dimensionY;
    }

    private void clear(){
        for(int i = 0; i < getDimensionX(); i++){
            for(int j = 0; j < getDimensionY(); j++){
                map[i][j] = FREE;
            }
        }
    }

    public Coord set(int position, int value, boolean withDelay){
        int coordenada1 = position / dimensionX;
        int coordenada2 = position % dimensionY;
        map[coordenada1][coordenada2] = value;
        listener.mapChanged(withDelay);
        printArray();
        return new Coord(coordenada2, coordenada1);
    }

    public void set(int x, int y, int value, boolean withDelay){
        map[x][y] = value;
        listener.mapChanged(withDelay);
        printArray();
    }

    public void setListener(MapListener listener){
        this.listener = listener;
    }

    void printArray() {
        StringBuffer buff = new StringBuffer("[");
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                buff.append(map[i][j]);
                buff.append(" , ");
            }
            buff.append("\n\r");
        }
        buff.append("]");
        System.out.println(buff);
    }
}
