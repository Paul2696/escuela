import java.util.List;
import java.util.Queue;

public interface OperationListener {
    void operationDone(Operation operation);
    void batchDone();
    void operationRunning(Operation operation, int seconds);
    void updateBatchList(Queue<Operation> operations);
    void operationDoneWithError(Operation operation);
    void operationBlocked(Queue<Operation> operations);
    void finishedProcesses(Queue<Operation> operations);
}
