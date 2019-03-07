import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Effort {
    private Set<Node> nodes = new HashSet<>(2);
    private double eM;
    private double eP;
    private double eL;
    private double wM = Integer.MAX_VALUE / 2;
    private double wP = Integer.MAX_VALUE / 2;
    private double wL = Integer.MAX_VALUE / 2;

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
    public void setNode1(Node node1) {
        nodes.add(node1);
    }

    public void setNode2(Node node2) {
        nodes.add(node2);
    }

    public Set<Node> getNodes(){
        return nodes;
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
        return nodes.equals(((Effort) o).getNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes);
    }
}
