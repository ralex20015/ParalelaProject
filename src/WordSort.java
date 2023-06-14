public class WordSort {


    public synchronized void sort(String []array, int lo, int hi)
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

    public synchronized void  merge(String[] arr, int left, int middle, int right)
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
            if (middle + j + 1 != arr.length) {
                rightArray[j] = arr[middle + j + 1];
            }
        }

        int i = 0, j = 0;

        //Índice inicial del sub-vector arr[].
        int k = left;

        //Ordenamiento.
        while (i < n1 && j < n2)
        {
            int lengthOfWordOnLeftSide = leftArray[i].length();
            int lengthOfWordOnRightSide = rightArray[j].length();
            int minor = Math.min(lengthOfWordOnLeftSide,lengthOfWordOnRightSide);

            for (int l = 0; l < minor; l++) {
                if (leftArray[i].charAt(l) < rightArray[j].charAt(l))
                {
                    arr[k] = leftArray[i];
                    i++;
                    break;
                } else if (leftArray[i].charAt(l) == rightArray[j].charAt(l)) {
                    if (l + 1 == minor){
                        if (leftArray[i].length() < rightArray[j].length())
                        {
                            arr[k] = leftArray[i];
                            i++;
                        }else {
                            arr[k] = rightArray[j];
                            j++;
                        }
                    }
                } else{
                    arr[k] = rightArray[j];
                    j++;
                    break;
                }
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
