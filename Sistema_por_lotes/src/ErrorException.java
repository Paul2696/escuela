public class ErrorException extends Exception{
    private int transcurrentTime;

    public ErrorException(int transcurrentTime){
        super();
        this.transcurrentTime = transcurrentTime;
    }

    public int getTranscurrentTime() {
        return transcurrentTime;
    }
}
