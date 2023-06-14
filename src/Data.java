import java.io.Serializable;

public class Data implements Serializable {
    private long time;
    private String[]arrayOfOrderedWords;

    public Data(long time, String[] arrayOfOrderedWords) {
        this.time = time;
        this.arrayOfOrderedWords = arrayOfOrderedWords;
    }

    public long getTime() {
        return time;
    }
    public String[] getArrayOfOrderedWords() {
        return arrayOfOrderedWords;
    }
}
