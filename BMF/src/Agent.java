import java.util.*;

public class Agent {
    public static final double[] MOMBO_EFFORTS = {1.0, 2.5, 0.3, 1.5};
    public static final double[] PIROLO_EFFORTS = {1.5, 0.3, 2.5, 1.0};
    public static final double[] LUCAS_EFFORTS = {0.3, 1.5, 1.0, 2.5};

    private Coord actualCoordinate;
    private Coord houseCoordinate;
    private Map map;
    private int agentType;
    private Graph graph;
    private Node actualNode;
    private double maxEffort = 0;
    private double minEffort = Integer.MAX_VALUE;
    private double averageEffort = 0;

    public Agent(int agentType, Graph graph){
        this.agentType = agentType;
        this.graph = graph;
    }

    public void setActualCoordinate(Coord actualCoordinate) {
        this.actualCoordinate = actualCoordinate;
    }

    public void startTraining() throws Exception{
        if(actualCoordinate == null){
            throw new Exception();
        }
        Coord initialCoord = actualCoordinate;
        for(int i = 0; i < 5; i++){
            int pastValue = Map.AGENT;
            List<Coord> visited = new ArrayList<>();
            List<Effort> efforts = new ArrayList<>();
            while(!yaLlegue(pastValue, initialCoord)){
                Coord coord = getNextCoord(visited);
                Node nextNode = graph.getNode(coord);
                if(nextNode == null){
                    nextNode = new Node(coord);
                    graph.addNode(nextNode);
                    Effort effort = graph.createEdge(actualNode, nextNode);
                    setTerrainEffort(effort, map.get(coord.y, coord.x));
                    efforts.add(effort);
                }
                else{
                    Effort aux = new Effort();
                    aux.setNode1(actualNode);
                    aux.setNode2(nextNode);
                    aux = graph.getEdgeIfExists(aux);
                    if(aux == null){
                        aux = graph.createEdge(actualNode, nextNode);
                        setTerrainEffort(aux, map.get(coord.y, coord.x));
                    }
                    efforts.add(aux);
                }
                map.set(actualCoordinate.y, actualCoordinate.x, pastValue, false);
                pastValue = map.get(coord.y, coord.x);
                map.set(coord.y, coord.x, agentType, true);
                actualCoordinate = coord;
                actualNode = nextNode;
                visited.add(actualCoordinate);
            }
            double routeEffort = calculateRouteEffort(efforts);
            calculateAverageEffort(routeEffort);
            calculateAdjustment(routeEffort, efforts);
        }
        System.out.println("Termina entrenamiento");
        //map.set(houseCoordinate.y, houseCoordinate.x, Map.HOUSE, false);
    }

    private Coord getNextCoord(List<Coord> visited) {
        List<Coord> freeSpaces = getFreeSpaces();
        Random r = new Random(new Date().getTime());
        Coord coord;
        List<Integer> randoms = new ArrayList<>();
        do{
            int random = r.nextInt(freeSpaces.size());
            coord = freeSpaces.get(random);
            if(!randoms.contains(random)){
                randoms.add(random);
            }
        }while(visited.contains(coord) && randoms.size() < freeSpaces.size());
        return coord;
    }

    private boolean yaLlegue(int pastValue, Coord initialCoord){
        if(pastValue == Map.HOUSE){
            map.set(actualCoordinate.y, actualCoordinate.x, Map.HOUSE, false);
            actualCoordinate = initialCoord;
            return true;
        }
        return false;
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

    public double[] getTerrainEffort(){
        if(agentType == Map.MOMBO){
            return MOMBO_EFFORTS;
        }
        else if(agentType == Map.PIROLO){
            return PIROLO_EFFORTS;
        }
        else if(agentType == Map.LUCAS){
            return LUCAS_EFFORTS;
        }
        return null;
    }

    private double calculateRouteEffort(List<Effort> efforts){
        double routeEffort = 0;
        if(agentType == Map.MOMBO){
            for(Effort effort : efforts){
                routeEffort += effort.geteM();
            }
        }
        else if(agentType == Map.PIROLO){
            for(Effort effort : efforts){
                routeEffort += effort.geteP();
            }
        }
        else if(agentType == Map.LUCAS){
            for(Effort effort : efforts){
                routeEffort += effort.geteL();
            }
        }
        return routeEffort;
    }

     private void calculateAverageEffort(double routeEffort){
        if(maxEffort < routeEffort){
            maxEffort = routeEffort;
        }
        if(minEffort > routeEffort){
            minEffort = routeEffort;
        }
        averageEffort = (maxEffort + minEffort) / 2;
     }

     private void calculateAdjustment(double routeEffort, List<Effort> efforts){
        double adjustment = routeEffort - averageEffort;
        if(routeEffort < averageEffort){
            System.out.println("Prize: " + adjustment);
        }
        else if(routeEffort > averageEffort){
            System.out.println("Punish: " + adjustment);
        }
        Set<Effort> effortsSet = new HashSet<>(efforts);
        for(Effort effort : effortsSet){
            if(agentType == Map.MOMBO){
                effort.setwM(effort.getwM() + adjustment);
            }
            else if(agentType == Map.PIROLO){
                effort.setwP(effort.getwP() + adjustment);
            }
            else if(agentType == Map.LUCAS){
                effort.setwL(effort.getwL() + adjustment);
            }
        }
     }


    private void setTerrainEffort(Effort effort, int terrainType){
        if(terrainType < Map.AGENT) {
            if (agentType == Map.MOMBO) {
                effort.seteM(MOMBO_EFFORTS[terrainType]);
            } else if (agentType == Map.PIROLO) {
                effort.seteP(PIROLO_EFFORTS[terrainType]);
            } else if (agentType == Map.LUCAS) {
                effort.seteL(LUCAS_EFFORTS[terrainType]);
            }
        }
    }


    public void setActualNode(Node actualNode){
        this.actualNode = actualNode;
    }

    public void setHouseCoordinate(Coord houseCoordinate) {
        this.houseCoordinate = houseCoordinate;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
