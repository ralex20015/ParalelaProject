public class WordSort {


    public void sort(String []array, int lo, int hi)
    {
        if (lo < hi)
        {
            int mid = lo + (hi - lo) / 2;

            sort(array, lo, mid);
            sort(array, mid + 1, hi);
            merge(array, lo, mid, hi);
        } else
        {
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
