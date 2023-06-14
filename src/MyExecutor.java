import java.util.Arrays;

public class MyExecutor implements Runnable {

    private final String[] words;
    private final int start;
    private final int end;

    public MyExecutor(String[] words, int start, int end) {
        this.words = words;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        WordSort wordSort = new WordSort();
        wordSort.sort(words,start,end);
    }
}
