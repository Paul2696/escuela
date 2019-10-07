import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

public class Batch {
    private Queue<Operation> batch = new LinkedList<>();
    private int id;

    public Batch(Operation operation, int batchId){
        this.id = batchId;
        addOperation(operation);
    }

    public void addOperation(Operation operation){
        if(operation.getNumber2() == 0 && (operation.getOperator() == Operation.DIVISION || operation.getOperator() == Operation.MODULE)) {
            throw new RuntimeException("No se puede dividir entre 0");
        }
        if(operation.getTime() <= 0){
            throw new RuntimeException("El tiempo no puede ser menor o igual a 0");
        }
        for(Operation operation1 : batch){
            if(operation1.getId() == operation.getId()){
                throw new RuntimeException("El id ya existe");
            }
        }
        operation.setBatchId(id);
        batch.add(operation);
    }

    public Queue<Operation> getBatch() {
        return batch;
    }

    public void Queue(Queue<Operation> batch) {
        this.batch = batch;
    }

    public int getMaxBatchTime(){
        int maxTime = 0;
        for(Operation batch : this.batch){
            maxTime += batch.getTime();
        }
        return maxTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batch batch1 = (Batch) o;
        return id == batch1.id &&
                Objects.equals(batch, batch1.batch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(batch, id);
    }
}
