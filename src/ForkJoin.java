import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {

    private WordSort words = new WordSort();
    private final String[] arrayOfWords;
    private final int lo;
    private final int hi;

    public ForkJoin(String[] a, int lo, int hi)
    {
        this.arrayOfWords = a;
        this.lo = lo;
        this.hi = hi;
    }

    @Override
    protected void compute()
    {
        if (lo < hi)
        {
            int mid = lo + (hi - lo) / 2;
            ForkJoin left = new ForkJoin(arrayOfWords, lo, mid);
            ForkJoin right = new ForkJoin(arrayOfWords, mid + 1, hi);
            invokeAll(left, right);
            words.merge(this.arrayOfWords, this.lo, mid, this.hi);
        }
    }
}
