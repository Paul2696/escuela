import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class Operation {
    public static final int ADDITION = 1;
    public static final int SUBSTRACTION = 2;
    public static final int MULTIPLICATION = 3;
    public static final int DIVISION = 4;
    public static final int MODULE = 5;
    public static final int POWER = 6;

    private int number1;
    private int number2;
    private int operator;
    private int time;
    private int id = 0;
    private int batchId;
    private int result;
    private int transcurrentTime = 0;
    private int blockedTime = 0;
    private int arrivalTime = 0;
    private int finishedTime = 0;
    private int serviceTime = 0;
    private int waitTime = 0;
    private int attendedTime = 0;
    private AtomicBoolean stop = new AtomicBoolean(false);
    private AtomicBoolean error = new AtomicBoolean(false);
    private AtomicBoolean interruption = new AtomicBoolean(false);
    private OperationListener listener;

    public Operation(){

    }

    public Operation(int operator, int time, int number1, int number2, int batchId, int id){
        this.operator = operator;
        this.time = time;
        this.number1 = number1;
        this.number2 = number2;
        this.batchId = batchId;
        this.id = id;
    }

    public Operation(int operator, int time, int number1, int number2, int id){
        this.operator = operator;
        this.time = time;
        this.number1 = number1;
        this.number2 = number2;
        this.id = id;
    }

    public static Operation createOperation(int batchId, int operationsId){
        return new Operation(ThreadLocalRandom.current().nextInt(1, 6 + 1),
                ThreadLocalRandom.current().nextInt(5, 15 + 1),
                ThreadLocalRandom.current().nextInt(1, 500 + 1),
                ThreadLocalRandom.current().nextInt(1, 500 + 1),
                batchId, operationsId);
    }

    public static Operation createOperation(int operationsId){
        return new Operation(ThreadLocalRandom.current().nextInt(1, 6 + 1),
                ThreadLocalRandom.current().nextInt(5, 15 + 1),
                ThreadLocalRandom.current().nextInt(1, 500 + 1),
                ThreadLocalRandom.current().nextInt(1, 500 + 1),
                operationsId);
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getBlockedTime() {
        return blockedTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(int finishedTime) {
        this.finishedTime = finishedTime;
    }

    public int getReturnTime() {
        return finishedTime - arrivalTime;
    }

    public void setServiceTime(int serviceTime){
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getWaitTime() {
        return getReturnTime() - getServiceTime();
    }

    public int getAttendedTime() {
        return attendedTime;
    }

    public void setAttendedTime(int attendedTime) {
        this.attendedTime = attendedTime;
    }

    public int getWaitTimee(){
        return waitTime;
    }

    public int getAnswerTime() {
        return getAttendedTime() - getArrivalTime();
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setBlockedTime(int blockedTime) {
        this.blockedTime = blockedTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    public void setListener(OperationListener listener) {
        this.listener = listener;
    }

    public int getResult(){
        return result;
    }

    public int addition(int number1, int number2){
        return number1 + number2;
    }

    public int substraction(int number1, int number2){
        return number1 - number2;
    }

    public int multiplication(int number1, int number2){
        return number1 * number2;
    }

    public int division(int number1, int number2){
        return number1 / number2;
    }

    public int module(int number1, int number2){
        return number1 % number2;
    }

    public int power(int number1, int number2){
        int res = 1;
        for(int i = 0; i < number2; i++){
            res *= number1;
        }
        return res;
    }

    public void setStop(boolean stop) {
        this.stop.set(stop);
    }

    public void setError(boolean error) {
        this.error.set(error);
    }

    public void setInterruption(boolean interruption) {
        this.interruption.set(interruption);
    }

    public int getTranscurrentTime() {
        return transcurrentTime;
    }

    public void setTranscurrentTime(int transcurrentTime) {
        this.transcurrentTime = transcurrentTime;
    }

    public int runOperation() throws ErrorException, InterruptionException{
        try{
            for(int i = transcurrentTime; i < time; i++){
                while(stop.get()){}
                if(error.get()){
                    throw new ErrorException(i+1);
                }
                if(interruption.get()){
                    setInterruption(false);
                    throw new InterruptionException(i+1);
                }
                Thread.sleep(1000);
                listener.operationRunning(this, i+1);
            }
        }
        catch(InterruptedException ie){
            ie.printStackTrace();
        }
        result = 0;
        switch(operator){
            case ADDITION:
                result = addition(number1, number2);
                break;

            case SUBSTRACTION:
                result = substraction(number1, number2);
                break;

            case MULTIPLICATION:
                result = multiplication(number1, number2);
                break;

            case DIVISION:
                result = division(number1, number2);
                break;

            case MODULE:
                result = module(number1, number2);
                break;

            case POWER:
                result = power(number1, number2);
                break;
        }
        return result;
    }

    public String getOperatorSymbol(int type){
        switch (type){
            case ADDITION:
                return "+";

            case SUBSTRACTION:
                return "-";

            case MULTIPLICATION:
                return "*";

            case DIVISION:
                return "/";

            case MODULE:
                return "%";

            case POWER:
                return "^";
        }
        return "";
    }

    @Override
    public String toString() {
        return "Operation{" +
                "number1=" + number1 +
                ", number2=" + number2 +
                ", operator='" + operator + '\'' +
                ", time=" + time +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return number1 == operation.number1 &&
                number2 == operation.number2 &&
                operator == operation.operator &&
                time == operation.time &&
                id == operation.id &&
                batchId == operation.batchId &&
                result == operation.result &&
                transcurrentTime == operation.transcurrentTime &&
                blockedTime == operation.blockedTime &&
                Objects.equals(stop, operation.stop) &&
                Objects.equals(error, operation.error) &&
                Objects.equals(interruption, operation.interruption) &&
                Objects.equals(listener, operation.listener);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number1, number2, operator, time, id, batchId, result, transcurrentTime, blockedTime, stop, error, interruption, listener);
    }
}
