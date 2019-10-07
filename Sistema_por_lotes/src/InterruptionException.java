public class InterruptionException extends Exception {
    private int transcurrentTime;

    public InterruptionException(int transcurrentTime){
        super();
        this.transcurrentTime = transcurrentTime;
    }

    public int getTranscurrentTime() {
        return transcurrentTime;
    }
}
