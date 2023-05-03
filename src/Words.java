import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class Words extends RecursiveAction {
    private final String[] arrayOfWords;
    private final int lo;
    private final int hi;

    public Words(String[] a, int lo, int hi)
    {
        this.arrayOfWords = a;
        this.lo = lo;
        this.hi = hi;
    }

    public void sort()
    {
        String temp;
        for (int i = 0; i < arrayOfWords.length; i++) {
            for (int j = i + 1; j < arrayOfWords.length; j++) {

                // to compare one string with other strings
                if (arrayOfWords[i].compareTo(arrayOfWords[j]) > 0) {
                    // swapping
                    temp = arrayOfWords[i];
                    arrayOfWords[i] = arrayOfWords[j];
                    arrayOfWords[j] = temp;
                }
            }
        }
    }

    @Override
    protected void compute()
    {
        if (lo < hi)
        {
            int mid = lo + (hi - lo) / 2;
            Words left = new Words(arrayOfWords, lo, mid);
            Words right = new Words(arrayOfWords, mid + 1, hi);
            invokeAll(left, right);
            merge(this.arrayOfWords, this.lo, mid, this.hi);
        }else {
            return;
        }
    }

    public void merge(String[] arr, int left, int middle, int right)
    {
        //Encuentra el tamaño de los sub-vectores para unirlos.
        int n1 = middle - left + 1;
        int n2 = right - middle;

        //Vectores temporales.
        String[] leftArray = new String[n1];
        String[] rightArray = new String[n2];

        //Copia los datos a los arrays temporales.
        for (int i = 0; i < n1; i++)
        {
            leftArray[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++)
        {
            rightArray[j] = arr[middle + j + 1];
        }

        int i = 0, j = 0;

        //Índice inicial del sub-vector arr[].
        int k = left;

        //Ordenamiento.
        while (i < n1 && j < n2)
        {
            if (leftArray[i].charAt(0) <= rightArray[j].charAt(0))
            {
                arr[k] = leftArray[i];
                i++;
            } else
            {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }//Fin del while.

        /* Si quedan elementos por ordenar */
        //Copiar los elementos restantes de leftArray[].
        while (i < n1)
        {
            arr[k] = leftArray[i];
            i++;
            k++;
        }
        //Copiar los elementos restantes de rightArray[].
        while (j < n2)
        {
            arr[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
