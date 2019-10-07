import java.util.ArrayList;
import java.util.List;

public class BatchProcessor {
    private List<Batch> batches = new ArrayList<Batch>();
    private List<Batch> finishedBatches = new ArrayList<>();
    private List<Operation> finishedOperations = new ArrayList<>();
    private OperationListener listener;
    private boolean finished = false;

    public BatchProcessor(OperationListener listener){
        this.listener = listener;
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public boolean isFinished(){
        return finished;
    }

    public List<Batch> getFinishedBatches() {
        return finishedBatches;
    }

    public void addToBatch(Operation operation){
        int counter = 1;
        operation.setListener(listener);
        if(batches.isEmpty()){
            batches.add(new Batch(operation, batches.size()));
        }
        else{
            for(Batch batch : batches){
                if(batch.getBatch().size() >= 4 && counter == batches.size()){
                    batches.add(new Batch(operation, batches.size()));
                    break;
                }
                else if(batch.getBatch().size() < 4){
                    batch.addOperation(operation);
                    break;
                }
                counter++;
            }
        }
    }

    public int getMaxTime(){
        int maxTime = 0;
        for(Batch batch : batches){
            maxTime += batch.getMaxBatchTime();
        }
        return maxTime;
    }

    public int getBatchsLeft(){
        return batches.size() - finishedBatches.size();
    }

    public void runBatch(){
        int counter = 0;
        for(Batch batch : batches){
            for(Operation operation : batch.getBatch()){
                listener.updateBatchList(batch.getBatch().subList(counter,batch.getBatch().size()));
                operation.runOperation();
                finishedOperations.add(operation);
                listener.operationDone(operation);
                counter++;
            }
            finishedBatches.add(batch);
            counter = 0;
        }
        listener.updateBatchList(new ArrayList<Operation>());
        finished = true;
    }



}
