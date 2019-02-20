import java.util.ArrayList;
import java.util.List;

public class Kibus {
    private Coord actualCoordinate;
    private Coord houseCoordinate;
    private Map map;

    private List<Coord> bresenham(int Bx1, int By1, int Bx2, int By2){
        List<Coord> points = new ArrayList<>();
        int e, ax, ay, temp;

        int s1, s2, intercambio, i, x, y;
        x = Bx1;
        y = By1;
        ax = Math.abs(Bx2 - Bx1);
        ay = Math.abs(By2 - By1);
        s1 = signo(Bx2 - Bx1);
        s2 = signo(By2 - By1);

        if (ay > ax){
            temp = ax;
            ax = ay;
            ay = temp;
            intercambio = 1;
        }
        else{
            intercambio = 0;
        }
        e = 2 * ay - ax;

        for (i = 0; i <= ax; i++){
            points.add(new Coord(x,y));
            if (e >= 0)	{
                if (intercambio == 1){
                    x = x + s1;
                }
                else{
                    y = y + s2;
                }
                e = e - (2 * ax);
            }
            if (intercambio == 1){
                y = y + s2;
            }
            else{
                x = x + s1;
            }
            e = e + 2 * ay;
        }
        return points;
    }
    private int signo(int num) {
        int resultado = 0;

        if (num < 0)
            resultado = -1;
        if (num > 0)
            resultado = 1;
        if (num == 0)
            resultado = 0;
        return (resultado);

    }

    public void setActualCoordinate(Coord actualCoordinate) {
        this.actualCoordinate = actualCoordinate;
    }

    public void startSearchingHouse() throws Exception{
        if(actualCoordinate == null || houseCoordinate == null){
            throw new Exception();
        }
        List<Coord> route = bresenham(
                actualCoordinate.x,
                actualCoordinate.y,
                houseCoordinate.x,
                houseCoordinate.y
        );
        while(!yaLlegue()){
            for(Coord coord : route){
                if(map.get(coord.x, coord.y) == Map.OBSTACLE){
                    break;
                }
                map.set(actualCoordinate.x, actualCoordinate.y, Map.FREE, false);
                map.set(coord.x, coord.y, Map.KIBUS, true);
                actualCoordinate = coord;
            }
            route = getNewRoute();
        }
    }

    public List<Coord> getNewRoute(){
        List<Coord> route = null;
        List<Coord> temp = null;
        for(Coord coord : getFreeSpaces()){
            temp = bresenham(
                    coord.x,
                    coord.y,
                    houseCoordinate.x,
                    houseCoordinate.y
            );
            if(route == null || route.size() > temp.size()){
                route = temp;
            }
        }
        return route;
    }

    private boolean yaLlegue(){
        return actualCoordinate.equals(houseCoordinate);
    }



    private List<Coord> getFreeSpaces(){
        List<Coord> freeSpaces = new ArrayList<>();
        //Upper Left
        if(map.get(actualCoordinate.x - 1, actualCoordinate.y - 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y - 1));
        }
        //Upper
        if(map.get(actualCoordinate.x, actualCoordinate.y - 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y - 1));
        }
        //Upper Right
        if(map.get(actualCoordinate.x + 1, actualCoordinate.y - 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y -1));
        }
        //Right
        if(map.get(actualCoordinate.x + 1, actualCoordinate.y) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y));
        }
        //Lower Right
        if(map.get(actualCoordinate.x + 1, actualCoordinate.y + 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y + 1));
        }
        //Lower
        if(map.get(actualCoordinate.x, actualCoordinate.y + 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y + 1));
        }
        //Lower Left
        if(map.get(actualCoordinate.x - 1, actualCoordinate.y + 1) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y + 1));
        }
        //Left
        if(map.get(actualCoordinate.x - 1, actualCoordinate.y) == Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y));
        }
        return freeSpaces;
    }


    private Coord calculateObstaclesAround(){
        int obstaclesRight = 0;
        int obstaclesLeft = 0;
        int obstaclesUp = 0;
        int obstaclesDown = 0;

        //Get right Obstacles
        if(map.get(actualCoordinate.x + 1, actualCoordinate.y+1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x + 1, actualCoordinate.y) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x + 1, actualCoordinate.y - 1) == Map.OBSTACLE){
            obstaclesRight++;
        }

        //Get Down Obstacles
        else if(map.get(actualCoordinate.x + 1, actualCoordinate.y + 1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x, actualCoordinate.y + 1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x - 1, actualCoordinate.y + 1 ) == Map.OBSTACLE){
            obstaclesDown++;
        }

        //Get Left Obstacles
        else if(map.get(actualCoordinate.x - 1, actualCoordinate.y + 1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x - 1, actualCoordinate.y) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x - 1, actualCoordinate.y - 1) == Map.OBSTACLE){
            obstaclesLeft++;
        }

        //Get Upper Obstacles
        else if(map.get(actualCoordinate.x - 1, actualCoordinate.y - 1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x, actualCoordinate.y - 1) == Map.OBSTACLE
        ||
        map.get(actualCoordinate.x + 1, actualCoordinate.y - 1) == Map.OBSTACLE){
            obstaclesUp++;
        }




        return null;
    }

    public void setHouseCoordinate(Coord houseCoordinate) {
        this.houseCoordinate = houseCoordinate;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
