import java.util.*;

public class Agent {
    public static final double[] MOMBO_EFFORTS = {1.0, 2.5, 0.3, 1.5};
    public static final double[] PIROLO_EFFORTS = {1.5, 0.3, 2.5, 1.0};
    public static final double[] LUCAS_EFFORTS = {0.3, 1.5, 1.0, 2.5};
    public static final int ITERATIONS = 500;

    private Coord actualCoordinate;
    private Coord houseCoordinate;
    private Mapa map;
    private int agentType;
    private Graph graph;
    private Node actualNode;
    private double maxEffort = 0;
    private double minEffort = Integer.MAX_VALUE;
    private double averageEffort = 0;
    private TrainingListener listener;

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
        Node initialNode = actualNode;
        for(int i = 0; i < ITERATIONS; i++){
            int pastValue = Mapa.AGENT;
            List<Coord> visited = new ArrayList<>();
            List<Effort> efforts = new ArrayList<>();
            while(!yaLlegue(pastValue, initialNode)){
                Coord coord = getNextCoord(visited);
                Node nextNode = graph.getNode(coord);
                if(nextNode == null){
                    nextNode = new Node(coord);
                    graph.addNode(nextNode);
                    Effort effort = graph.createEdge(actualNode, nextNode);
                    setTerrainEffort(effort, map.get(coord.y, coord.x));
                    efforts.add(effort);
                    actualNode.setNeighbor(effort);
                }
                else{
                    Effort aux = new Effort();
                    aux.setOriginNode(actualNode);
                    aux.setDestinationNode(nextNode);
                    aux = graph.getEdgeIfExists(aux);
                    if(aux == null){
                        aux = graph.createEdge(actualNode, nextNode);
                    }
                    setTerrainEffort(aux, map.get(coord.y, coord.x));
                    efforts.add(aux);
                    actualNode.setNeighbor(aux);
                }
                map.set(actualCoordinate.y, actualCoordinate.x, pastValue, false);
                pastValue = map.get(coord.y, coord.x);
                map.set(coord.y, coord.x, agentType, false);
                actualCoordinate = coord;
                actualNode = nextNode;
                visited.add(actualCoordinate);
            }
            double routeEffort = calculateRouteEffort(efforts);
            calculateAdjustment(routeEffort, efforts);
            calculateAverageEffort(routeEffort);
            listener.finishRoute(i);
        }
        System.out.println("Termina entrenamiento");

    }

    public void searchHouse(Node house, Node start){
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph, agentType);
        dijkstra.execute(start);
        LinkedList<Node> route;
        int pastValue = map.get(start.coord.y, start.coord.x);
        route = dijkstra.getPath(house);
        route.pop();
        System.out.println(start.coord);
        System.out.println(house.coord);
        System.out.println(graph.nodes.size());
        actualCoordinate = start.coord;
        while(!route.isEmpty()){
            Node node = route.pop();
            Coord coord = graph.getNode(node.nodeNumber);
            map.set(actualCoordinate.y, actualCoordinate.x, pastValue, false);
            pastValue = map.get(coord.y, coord.x);
            map.set(coord.y, coord.x, agentType, true);
            actualCoordinate = coord;
            System.out.println(coord);
        }
        map.set(house.coord.y, house.coord.x, Mapa.HOUSE, false);
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

    private boolean yaLlegue(int pastValue, Node initialNode){
        if(pastValue == Mapa.HOUSE){
            map.set(actualCoordinate.y, actualCoordinate.x, Mapa.HOUSE, false);
            actualNode = initialNode;
            actualCoordinate = initialNode.coord;
            return true;
        }
        return false;
    }


    private List<Coord> getFreeSpaces(){
        List<Coord> freeSpaces = new ArrayList<>();
        //Upper Left
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x - 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x - 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y - 1));
        }
        //Upper
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y - 1));
        }
        //Upper Right
        if(map.get(actualCoordinate.y - 1, actualCoordinate.x + 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y - 1, actualCoordinate.x + 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y -1));
        }
        //Right
        if(map.get(actualCoordinate.y, actualCoordinate.x + 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y, actualCoordinate.x + 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y));
        }
        //Lower Right
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x + 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x + 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x + 1, actualCoordinate.y + 1));
        }
        //Lower
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x, actualCoordinate.y + 1));
        }
        //Lower Left
        if(map.get(actualCoordinate.y + 1, actualCoordinate.x - 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y + 1, actualCoordinate.x - 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y + 1));
        }
        //Left
        if(map.get(actualCoordinate.y, actualCoordinate.x - 1) < Mapa.OBSTACLE &&
                map.get(actualCoordinate.y, actualCoordinate.x - 1) >= Mapa.FREE){
            freeSpaces.add(new Coord(actualCoordinate.x - 1, actualCoordinate.y));
        }
        return freeSpaces;
    }

    public double[] getTerrainEffort(){
        if(agentType == Mapa.MOMBO){
            return MOMBO_EFFORTS;
        }
        else if(agentType == Mapa.PIROLO){
            return PIROLO_EFFORTS;
        }
        else if(agentType == Mapa.LUCAS){
            return LUCAS_EFFORTS;
        }
        return null;
    }

    private double calculateRouteEffort(List<Effort> efforts){
        double routeEffort = 0;
        if(agentType == Mapa.MOMBO){
            for(Effort effort : efforts){
                routeEffort += effort.geteM();
            }
        }
        else if(agentType == Mapa.PIROLO){
            for(Effort effort : efforts){
                routeEffort += effort.geteP();
            }
        }
        else if(agentType == Mapa.LUCAS){
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
            if(agentType == Mapa.MOMBO){
                effort.setwM(effort.getwM() + adjustment);
            }
            else if(agentType == Mapa.PIROLO){
                effort.setwP(effort.getwP() + adjustment);
            }
            else if(agentType == Mapa.LUCAS){
                effort.setwL(effort.getwL() + adjustment);
            }
        }
     }


    private void setTerrainEffort(Effort effort, int terrainType){
        if(terrainType < Mapa.AGENT) {
            if (agentType == Mapa.MOMBO) {
                effort.seteM(MOMBO_EFFORTS[terrainType]);
            } else if (agentType == Mapa.PIROLO) {
                effort.seteP(PIROLO_EFFORTS[terrainType]);
            } else if (agentType == Mapa.LUCAS) {
                effort.seteL(LUCAS_EFFORTS[terrainType]);
            }
        }
        if(terrainType == Mapa.AGENT){
            if (agentType == Mapa.MOMBO) {
                effort.seteM(MOMBO_EFFORTS[Mapa.FREE]);
            } else if (agentType == Mapa.PIROLO) {
                effort.seteP(PIROLO_EFFORTS[Mapa.FREE]);
            } else if (agentType == Mapa.LUCAS) {
                effort.seteL(LUCAS_EFFORTS[Mapa.FREE]);
            }
        }
        if(terrainType == Mapa.HOUSE){
            if (agentType == Mapa.MOMBO) {
                effort.seteM(MOMBO_EFFORTS[Mapa.FREE]);
            } else if (agentType == Mapa.PIROLO) {
                effort.seteP(PIROLO_EFFORTS[Mapa.FREE]);
            } else if (agentType == Mapa.LUCAS) {
                effort.seteL(LUCAS_EFFORTS[Mapa.FREE]);
            }
        }
    }

    public void setListener(TrainingListener listener){
        this.listener = listener;
    }


    public void setActualNode(Node actualNode){
        this.actualNode = actualNode;
    }

    public void setHouseCoordinate(Coord houseCoordinate) {
        this.houseCoordinate = houseCoordinate;
    }

    public void setMap(Mapa map) {
        this.map = map;
    }
}
