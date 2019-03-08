import java.util.Random;

public class Mapa {
    public static int dimensionX = 15;
    public static int dimensionY = 15;
    private MapListener listener;
    public static final int FREE = 0;
    public static final int MOUNTAIN = 1;
    public static final int RIVER = 2;
    public static final int RAVINE = 3;
    public static final int OBSTACLE = 6;
    public static final int AGENT = 4;
    public static final int HOUSE = 5;
    public static final int MOMBO = 7;
    public static final int PIROLO = 8;
    public static final int LUCAS = 9;
    int[][] map = new int[getDimensionX()][getDimensionY()];

    public void setObstacles(int percentageObstacles){
        clear();
        setTerrain(percentageObstacles, OBSTACLE);
    }
    public void setMountains(int percentageMountains){
        clearMountains();
        setTerrain(percentageMountains, MOUNTAIN);
    }
    public void setRivers(int percentageWater){
        clearRivers();
        setTerrain(percentageWater, RIVER);
    }
    public void setPlain(int percentagePlanes){
        clearPlains();
        /*setTerrain(percentagePlanes, PLAIN);*/
    }
    public void setRavine(int percentageRavines){
        clearRavines();
        setTerrain(percentageRavines, RAVINE);
    }

    private void setTerrain(int percentageTerrain, int terrain){
        Random r = new Random();
        int numberTerrain = 0;
        numberTerrain = getMapSize() * percentageTerrain / 100;
        while(numberTerrain != 0){
            int random = r.nextInt(((getMapSize() - 1) - 0) + 1) + 0;
            int coordenada1 = random / dimensionX;
            int coordenada2 = random % dimensionY;
            if(map[coordenada2][coordenada1] == FREE){
                map[coordenada2][coordenada1] = terrain;
            }
            numberTerrain--;
        }
        listener.objectsSetted();
    }

    public int get(int x, int y){
        if(x < 0 || y < 0 || x >= getDimensionX() || y >= getDimensionY()){
            return -1;
        }
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

    private void clearRivers(){
        clearTerrain(RIVER);
    }

    private void clearMountains(){
        clearTerrain(MOUNTAIN);
    }
    private void clearRavines(){
        clearTerrain(RAVINE);
    }
    private void clearPlains(){
        //clearTerrain(PLAIN);
    }

    private void clearTerrain(int terrain){
        for(int i = 0; i < getDimensionX(); i++){
            for(int j = 0; j < getDimensionY(); j++){
                if(map[i][j] == terrain){
                    map[i][j] = FREE;
                }
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

    public Coord setObjects(int position, int pastPosition, int object){
        if(pastPosition >= 0){
           set(pastPosition, FREE, false);
        }
        Coord coord = set(position,  object, false);
        listener.objectsSetted();
        return coord;
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
        //System.out.println(buff);
    }
}
