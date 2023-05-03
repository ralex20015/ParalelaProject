import java.util.Arrays;

public class Words {
    private String [] words;
    public Words(String [] words){
        this.words = words;
    }

    public void orderWords(){
        Arrays.sort(words,
                String.CASE_INSENSITIVE_ORDER);
    }
}
