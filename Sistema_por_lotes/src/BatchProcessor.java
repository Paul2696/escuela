import java.util.*;

public class BatchProcessor {
    private Queue<Operation> processes = new LinkedList<>();
    private Queue<Operation> runningProcesses = new LinkedList<>();
    private Queue<Operation> finishedOperations = new LinkedList<>();
    private Queue<Operation> blockedOperations = new LinkedList<>();
    private Operation actualOperation;
    private int operationsId = 0;
    private OperationListener listener;
    private boolean finished = false;
    private int tt = 0;

    public BatchProcessor(OperationListener listener){
        this.listener = listener;
    }

    public Queue<Operation> getProcesses() {
        return processes;
    }

    public boolean isFinished(){
        return finished;
    }

    public int getTt() {
        return tt;
    }

    public void setTt(int tt) {
        this.tt = tt;
    }

    public void addToProcesses(int operaciones){
        for(int i = 0; i < operaciones; i++){
            Operation operation = Operation.createOperation(operationsId);
            operationsId++;
            operation.setListener(listener);
            addOperation(operation);
        }
    }

    public Queue<Operation> getBlockedOperations() {
        return blockedOperations;
    }

    public void setBlockedOperations(Queue<Operation> blockedOperations) {
        this.blockedOperations = blockedOperations;
    }

    public int getProcessesLeft(){
        return processes.size();
    }

    public void runBatch(){
        int size = processes.size();
        for(int i = 0; i < 4 && i < size; i++){
            Operation operation = processes.poll();
            operation.setArrivalTime(tt);
            runningProcesses.add(operation);
        }
        listener.updateBatchList(runningProcesses);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Operation process = null;
        while(!runningProcesses.isEmpty() || !blockedOperations.isEmpty()){
            try{
                process = runningProcesses.element();
                runningProcesses.poll();
                actualOperation = process;
                listener.updateBatchList(runningProcesses);
                if(actualOperation.getAttendedTime() == 0){
                    actualOperation.setAttendedTime(tt);
                }
                process.runOperation();
                actualOperation.setFinishedTime(tt);
                actualOperation.setServiceTime(actualOperation.getTime());
                finishedOperations.add(actualOperation);
                listener.operationDone(process);
            }
            catch(ErrorException ee){
                ee.printStackTrace();
                actualOperation.setServiceTime(ee.getTranscurrentTime());
                actualOperation.setFinishedTime(tt);
                finishedOperations.add(actualOperation);
                listener.operationDoneWithError(actualOperation);
            }
            catch(InterruptionException ie){
                ie.printStackTrace();
                blockedOperations.add(actualOperation);
                blockOperation(actualOperation);
                //listener.updateBatchList(processes);
                actualOperation.setTranscurrentTime(ie.getTranscurrentTime());
            }
            catch(NoSuchElementException nse){

            }
            if(runningProcesses.size() + blockedOperations.size() <= 3){
                if(!processes.isEmpty()){
                    Operation operation = processes.poll();
                    operation.setArrivalTime(tt);
                    runningProcesses.add(operation);
                }
            }
        }
        listener.updateBatchList(new LinkedList<>());
        finished = true;
        listener.finishedProcesses(finishedOperations);
    }

    public void blockOperation(Operation operation){
        Thread blockedThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    operation.setBlockedTime(0);
                    for(int i = 0; i < 10; i++){
                        Thread.sleep(1000);
                        operation.setBlockedTime(i + 1);
                        listener.operationBlocked(blockedOperations);
                    }
                    Operation operation = blockedOperations.peek();
                    runningProcesses.add(operation);
                    blockedOperations.poll();
                    listener.operationBlocked(blockedOperations);
                    listener.updateBatchList(runningProcesses);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        blockedThread.start();
    }

    public void addOperation(Operation operation){
        if(operation.getNumber2() == 0 && (operation.getOperator() == Operation.DIVISION || operation.getOperator() == Operation.MODULE)) {
            throw new RuntimeException("No se puede dividir entre 0");
        }
        if(operation.getTime() <= 0){
            throw new RuntimeException("El tiempo no puede ser menor o igual a 0");
        }
        processes.add(operation);
    }

    public boolean operationFinished(Operation actualOperation){
        for(Operation operation : finishedOperations){
            if(actualOperation.equals(operation)){
                return true;
            }
        }
        return false;
    }

    public void pauseOperation(){
        actualOperation.setStop(true);
    }

    public void resumeOperation(){
        actualOperation.setStop(false);
    }

    public void errorOperation(){
        actualOperation.setError(true);
    }

    public void interruptedOperation(){
        actualOperation.setInterruption(true);
    }

}
