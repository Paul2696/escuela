import java.util.*;


public class DijkstraAlgorithm {

    private final List<Effort> edges;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Double> distance;
    private int agentType;

    public DijkstraAlgorithm(Graph graph, int agentType) {
        // create a copy of the array so that we can operate on this array
        this.edges = graph.edges;
        this.agentType = agentType;
    }

    public void execute(Node initialNode) {
        settledNodes = new HashSet<Node>();
        unSettledNodes = new HashSet<Node>();
        distance = new HashMap<Node, Double>();
        predecessors = new HashMap<Node, Node>();
        distance.put(initialNode, new Double(0));
        unSettledNodes.add(initialNode);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            System.out.println(node);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        for (Effort target : node.neighbor) {
            if (target != null && (getShortestDistance(target.getDestinationNode()) > getShortestDistance(node)
                    + getDistance(node, target.getDestinationNode()))) {
                distance.put(target.getDestinationNode(), getShortestDistance(node)
                        + getDistance(node, target.getDestinationNode()));
                predecessors.put(target.getDestinationNode(), node);
                unSettledNodes.add(target.getDestinationNode());
            }
        }
    }

    private double getDistance(Node node, Node target) {
        for (Effort edge : edges) {
            if (edge.getOriginNode().equals(node)
                    && edge.getDestinationNode().equals(target)) {
               if(agentType == Mapa.MOMBO){
                   return edge.getwM();
               }
               if(agentType == Mapa.PIROLO){
                   return edge.getwP();
               }
               if(agentType == Mapa.LUCAS){
                   return edge.getwL();
               }
            }
        }
        return -1;
    }


    private Node getMinimum(Set<Node> vertexes) {
        Node minimum = null;
        for (Node vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(Node destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Node> getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}