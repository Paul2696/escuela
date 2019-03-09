import java.util.*;

public class Dijkstra {
    private final int MAX = Integer.MAX_VALUE;  //maximo numero de vértices
    private final int INF = Integer.MAX_VALUE;  //definimos un valor grande que represente la distancia infinita inicial, basta conque sea superior al maximo valor del peso en alguna de las aristas

    private double distancia[ ] = new double[ 225 ];          //distancia[ u ] distancia de vértice inicial a vértice con ID = u
    private boolean visitado[ ] = new boolean[ 225 ];   //para vértices visitados
    private PriorityQueue< Node > Q = new PriorityQueue<Node>(); //priority queue propia de Java, usamos el comparador definido para que el de menor valor este en el tope
    private int V;                                      //numero de vertices
    private int previo[] = new int[ 225 ];              //para la impresion de caminos
    private boolean dijkstraEjecutado = false;
    private Graph graph;
    private int agentType;

    public Dijkstra(Graph graph, int agentType){
        this.graph = graph;
        this.agentType = agentType;
        V = graph.nodes.size();
    }


    //función de inicialización
    private void init(){
        for( int i = 0 ; i < V ; ++i ){
            distancia[i] = INF;  //inicializamos todas las distancias con valor infinito
            visitado[i] = false; //inicializamos todos los vértices como no visitados
            previo[i] = -1;      //inicializamos el previo del vertice i con -1
        }
    }

    //Paso de relajacion
    private void relajacion(Node actual, Node adyacente , double peso ){
        //Si la distancia del origen al vertice actual + peso de su arista es menor a la distancia del origen al vertice adyacente
        if( distancia[actual.nodeNumber] + peso < distancia[adyacente.nodeNumber] ){
            distancia[adyacente.nodeNumber] = distancia[actual.nodeNumber] + peso;  //relajamos el vertice actualizando la distancia
            previo[adyacente.nodeNumber] = actual.nodeNumber;//a su vez actualizamos el vertice previo
            adyacente.distance = distancia[adyacente.nodeNumber];
            Q.add(adyacente); //agregamos adyacente a la cola de prioridad
        }
    }

    void dijkstra(Coord inicial){
        init(); //inicializamos nuestros arreglos
        Node firstNode = graph.getNode(inicial);
        firstNode.distance = 0;
        Q.add(firstNode); //Insertamos el vértice inicial en la Cola de Prioridad
        distancia[firstNode.nodeNumber] = 0;      //Este paso es importante, inicializamos la distancia del inicial como 0
        Node actual;
        Node adyacente;
        double peso;
        while( !Q.isEmpty() ){                   //Mientras cola no este vacia
            actual = Q.element();            //Obtengo de la cola el nodo con menor peso, en un comienzo será el inicial
            Q.remove();//Sacamos el elemento de la cola
            if(actual.coord.x == 13 && actual.coord.y == 10){
                System.out.println("llego");
            }
            if(actual.coord.x == 14 && actual.coord.y == 9){
                System.out.println("llego");
            }
            if(actual.coord.x == 14 && actual.coord.y == 11){
                System.out.println("llego");
            }
            if(actual.coord.x == 13 && actual.coord.y == 11){
                System.out.println("llego");
            }
            if(actual.coord.x == 13 && actual.coord.y == 9){
                System.out.println("llego");
            }
            if(visitado[actual.nodeNumber]) continue; //Si el vértice actual ya fue visitado entonces sigo sacando elementos de la cola
            visitado[actual.nodeNumber] = true;         //Marco como visitado el vértice actual

            for(int i = 0 ; i < actual.neighbor.length ; ++i){ //reviso sus adyacentes del vertice actual
                Effort effort = actual.neighbor[i];
                if(effort == null){
                    continue;
                }
                adyacente = effort.getDestinationNode();   //id del vertice adyacente
                peso = effort.getWeight(agentType);        //peso de la arista que une actual con adyacente ( actual , adyacente )
                if(!visitado[adyacente.nodeNumber]){        //si el vertice adyacente no fue visitado
                    relajacion( actual , adyacente , peso ); //realizamos el paso de relajacion
                }
            }
        }
        dijkstraEjecutado = true;
    }


    public Stack<Integer> getShortestPath(Node house, Node start){
        if( !dijkstraEjecutado ){
            System.out.println("Es necesario ejecutar el algorithmo de Dijkstra antes de poder imprimir el camino mas corto");
            return null;
        }
        int destino = house.nodeNumber;
        Stack<Integer> route = new Stack<>();
        route.push(destino);
        getShortestPath(destino, start.nodeNumber, route);
        System.out.printf("\n");
        return route;
    }

    //Impresion del camino mas corto desde el vertice inicial y final ingresados
    public void getShortestPath(int destino, int start, Stack<Integer> route){
        if(previo[destino] != previo[start])  {         //si aun poseo un vertice previo
            route.push(previo[destino]);
            getShortestPath(previo[destino], start, route);  //recursivamente sigo explorando
        }
    }

    public int getNumberOfVertices() {
        return V;
    }

    public void setNumberOfVertices(int numeroDeVertices) {
        V = numeroDeVertices;
    }
}
