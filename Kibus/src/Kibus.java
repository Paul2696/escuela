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

        for (i = 1; i <= ax; i++){
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
        for(Coord coord : route){
            map.set(coord.x, coord.y, Map.KIBUS);
            map.set(actualCoordinate.x, actualCoordinate.y, Map.FREE);
            setActualCoordinate(coord);
        }
    }

    public void setHouseCoordinate(Coord houseCoordinate) {
        this.houseCoordinate = houseCoordinate;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
