import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Effort {
    private Node originNode;
    private Node destinationNode;
    private double eM;
    private double eP;
    private double eL;
    private double wM = 5000000;
    private double wP = 5000000;
    private double wL = 5000000;

    public double getWeight(int agentType){
        if(agentType == Mapa.MOMBO){
            return wM;
        }
        if(agentType == Mapa.PIROLO){
            return wP;
        }
        if(agentType == Mapa.LUCAS){
            return wP;
        }
        return -1;
    }

    public void seteM(double eM) {
        this.eM = eM;
    }

    public void seteP(double eP) {
        this.eP = eP;
    }

    public void seteL(double eL) {
        this.eL = eL;
    }

    public void setwM(double wM) {
        this.wM = wM;
    }

    public void setwP(double wP) {
        this.wP = wP;
    }

    public void setwL(double wL) {
        this.wL = wL;
    }
    public void setOriginNode(Node originNode) {
        this.originNode = originNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public Node getOriginNode(){
        return originNode;
    }

    public Node getDestinationNode(){
        return destinationNode;
    }

    public double geteM() {
        return eM;
    }

    public double geteP() {
        return eP;
    }

    public double geteL() {
        return eL;
    }

    public double getwM() {
        return wM;
    }

    public double getwP() {
        return wP;
    }

    public double getwL() {
        return wL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Effort effort = (Effort) o;
        return originNode.equals(effort.originNode) &&
                destinationNode.equals(effort.destinationNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originNode, destinationNode);
    }
}
