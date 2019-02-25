import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;
import java.util.Date;

public class Kibus {
    private Coord actualCoordinate;
    private Coord houseCoordinate;
    private Stack<Coord> recentRoutes = new Stack<>();
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
        boolean flag = false;
        Coord flagCoord = null;
        int pastValue = Map.FREE;
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
                if(map.get(coord.y, coord.x) == Map.OBSTACLE){
                    flag = true;
                    flagCoord = new Coord(actualCoordinate.x, actualCoordinate.y);
                    break;
                }
                map.set(actualCoordinate.y, actualCoordinate.x, pastValue, false);
                pastValue = map.get(coord.y, coord.x);
                map.set(coord.y, coord.x, Map.KIBUS, true);
                actualCoordinate = coord;
                if(flag){
                    setTinyFlag(flagCoord);
                    flag = false;
                }
            }
            route = getNewRouteRandom();
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
            if(route == null || route.size() > temp.size() || (!recentRoutes.isEmpty() && temp.get(0) == recentRoutes.peek())){
                route = temp;
            }
        }
        recentRoutes.push(route.get(0));
        return route;
    }

    private boolean yaLlegue(){
        return actualCoordinate.equals(houseCoordinate);
    }

    private List<Coord> getNewRouteRandom(){
        List<Coord> freeSpaces = getFreeSpaces();
        Random r = new Random(new Date().getTime());
        int random = r.nextInt(freeSpaces.size());
        Coord coord = freeSpaces.get(random);
        return bresenham(coord.x,
                coord.y,
                houseCoordinate.x,
                houseCoordinate.y);
    }



    private List<Coord> getFreeSpaces(){
        List<Coord> freeSpaces = new ArrayList<>();
        //Upper Left
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x - 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x - 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y - 1));
        }
        //Upper
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x) < Map.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y - 1));
        }
        //Upper Right
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x + 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x + 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y -1));
        }
        //Right
        if(map.get(actualCoordinate.y, actualCoordinate.x + 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y, actualCoordinate.x + 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y));
        }
        //Lower Right
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x + 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x + 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y + 1));
        }
        //Lower
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x) < Map.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y + 1));
        }
        //Lower Left
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x - 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x - 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y + 1));
        }
        //Left
        if(map.get(actualCoordinate.y, actualCoordinate.x - 1) < Map.OBSTACLE &&
                map.get(actualCoordinate.y, actualCoordinate.x - 1) >= Map.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y));
        }
        return freeSpaces;
    }

    private void setTinyFlag(Coord coord){
        int value = map.get(coord.y, coord.x);
        if(value < 6){
            map.set(coord.y, coord.x, value + 1,false);
        }
    }

    public void setHouseCoordinate(Coord houseCoordinate) {
        this.houseCoordinate = houseCoordinate;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
