import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {

    private WordSort words = new WordSort();
    private final String[] arrayOfWords;
    private final int startFromWord;
    private final int totalWords;

    public ForkJoin(String[] a, int lo, int hi)
    {
        this.arrayOfWords = a;
        this.startFromWord = lo;
        this.totalWords = hi;
    }

    @Override
    protected void compute()
    {
        if (totalWords < 10000 || totalWords == startFromWord)
        {
            words.sort(arrayOfWords,0,totalWords);
            return;
        }
        int mid = startFromWord + (totalWords - startFromWord) / 2;
        ForkJoin left = new ForkJoin(arrayOfWords, startFromWord, mid);
        ForkJoin right = new ForkJoin(arrayOfWords, mid + 1, totalWords);
        words.merge(this.arrayOfWords,this.startFromWord,mid,this.totalWords);
        invokeAll(left, right);
    }
}
