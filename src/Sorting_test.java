import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

class Sorting_test {
    private final static String T ="--type";
    private final static String STAT ="--stat";
    private final static String MERGE="merge";
    private final static String Q ="quick";
    private final static String DQ ="dual_quick";
    private final static String I ="insert";
    private final static String M ="mquick";
    private final static String COMP ="--comp";
    private static long swaps=0;
    private static long counter=0;

    public static void print_list(int[] arr)
    {
        System.out.println("Array size: "+arr.length);
        System.out.println("Array:");
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
    private static void swap(int[] arr, int idx1,int idx2){
        swaps++;
        int temp=arr[idx1];
        arr[idx1]=arr[idx2];
        arr[idx2]=temp;
        // System.err.println("Swapping "+idx1+" with"+idx2);
    }
    private static void print_count(int first, int second)
    {
        counter++;
        //System.err.println( "Comparision: "+ first+ " "+ second);
    }


    private static void is_array_sorted(int[] array,  boolean asc)
    {

        for(int i=1;i< array.length;i++)
        {
            if(asc? array[i]<array[i-1]:array[i] > array[i-1]){
                System.out.println("nieposortowana tablica");
                return;
            }
        }
        System.out.println( "posortowana tablica");
    }



    // modified quicksorting nlogn
    private static void modified_quick_sort(boolean asc,int[] array, int first_index, int last_index)
    {
        while (first_index < last_index)
        {
            if((last_index - first_index) <= 16)
            {
                // System.out.println("swaping to InsertionSort here from"+first_index+"to"+last_index);
                insertionSort(asc,array, first_index,last_index);
                break;
            }
            int divider = split_arr(asc,array, first_index, last_index);
            if(divider-first_index < last_index-divider){
                modified_quick_sort(asc,array, first_index, divider-1);
                first_index=divider+1;
            }
            else{
                modified_quick_sort(asc,array, divider + 1, last_index);
                last_index=divider-1;
            }
        }
    }
    //(nlogn) best  O(n^2) worst scenario
    private static void quick_sort(boolean asc,int[] array, int first_index, int last_index)
    {
        if (first_index < last_index)
        {

            int divider = split_arr(asc,array, first_index, last_index);
            if(divider-first_index < last_index-divider){
                quick_sort(asc,array, first_index, divider-1);
                quick_sort(asc,array, divider + 1, last_index);
            }
            else{
                quick_sort(asc,array, divider + 1, last_index);
                quick_sort(asc,array, first_index, divider-1);
            }
        }
    }
    private static void dual_quick_sort(boolean asc,int[] arr, int low, int high)
    {
        if (low < high)
        {
            // lp means left pivot, and rp means right pivot.
            int[] rp;
            rp = partition(asc,arr, low, high);
            dual_quick_sort(asc,arr, low, rp[1] - 1);
            dual_quick_sort(asc,arr, rp[1] + 1, rp[0] - 1);
            dual_quick_sort(asc,arr, rp[0] + 1, high);
        }
    }
    private static int[] partition(boolean asc,int[] arr, int low, int high)
    {
        print_count(arr[low],arr[high]);
        if (asc ? arr[low] > arr[high]:arr[low] < arr[high])
            swap(arr,low, high);

        // p is the left pivot, and q is the right pivot.
        int j = low + 1;
        int g = high - 1, k = low + 1, p = arr[low], q = arr[high];
        while (k <= g)
        {
            // if elements are less than the left pivot
            print_count(arr[k],p);

            if (asc ? arr[k] < p : arr[k] > p)
            {
                swap(arr,k, j);
                j++;
            }

            // if elements are greater than or equal
            // to the right pivot
            else if (asc ? arr[k] >= q :arr[k] <= q)
            {            print_count(arr[k],q);

                print_count(arr[g],q);
                while ((asc ? arr[g] > q : arr[g] < q)  && k < g){
                    print_count(arr[g],q);

                    g--;
                }
                swap(arr ,k, g);
                g--;
                print_count(arr[k],p);
                if (asc ? arr[k] < p : arr[k] > p)
                {
                    swap(arr,k, j);
                    j++;
                }
            }
            k++;
        }
        j--;
        g++;

        // bring pivots to their appropriate positions.
        swap(arr, low, j);
        swap(arr,high, g);
        return new int[]{ g,j };
    }
    private static int split_arr(boolean asc,int[]arr,int first,int last){
        int idx_pivot= choose_pivot(arr,first,last);
        int val_pivot= arr[idx_pivot];
        swap(arr,idx_pivot,last);
        int c =first;
        for(int i=first;i<last;i++){
            print_count(arr[i],val_pivot);
            if(asc ? arr[i]<val_pivot : arr[i]>val_pivot){
                swap(arr,i,c);
                c+=1;
            }
        }
        swap(arr, c, last);
        return c;
    }
    private static int choose_pivot(int[] arr, int first, int last){
        if((last-first)<8)
        {
            return (int)(Math.random()*(last-first) + first);
        }
        else{
            return (int)(Math.random()*((last-first)*0.5)+first+(last-first)/4);
        }
    }
    //end quicksorting

    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    private static void merge(boolean asceding,
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            print_count(l[i],r[j]);
            if ( asceding ? l[i]<= r[j]:l[i]>= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    private static void mergeSort(boolean asceding, int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for(int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for(int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(asceding,l, mid);
        mergeSort(asceding,r, n - mid);

        merge(asceding,a, l, r, mid, n - mid);
    }
    //n2
    private static void insertionSort(boolean asc,int[] arr, int low, int n)
    {
        // Start from second element (element at index 0)
        // is already sorted)
        for (int i = low + 1; i <= n; i++)
        {
            int value = arr[i];
            int j = i;

            // Find the index j within the sorted subset arr[0..i-1]
            // where element arr[i] belongs
            print_count(arr[j],value);

            while (j > low && (asc ? arr[j - 1] > value : arr[j - 1] < value))
            {
                arr[j] = arr[j - 1];
                j--;
                print_count(arr[j],value);

            }
            // Note that subarray arr[j..i-1] is shifted to
            // the right by one position i.e. arr[j+1..i]

            arr[j] = value;
        }
    }

    private static void randomArr(int[] arr){
        for(int i=0;i<arr.length;i++)
        {
            arr[i]=(int)(Math.random()*1000)-500;
        }

    }
    private static int[] cloningArr(int[] arr){
        int[] arr2=new int[arr.length];
        for(int i=0;i<arr.length;i++)
        {
            arr2[i]=arr[i];
        }
        return arr2;
    }

    public static void main(String[] args){

        boolean stats = false;
        boolean ascending = true;
        String fileName="";
        int k=0;
        for(int i=0;i<args.length;i++)
        {
            if(args[i].equals(STAT))
            {
                stats =true;
                fileName=args[i+1];
                k=Integer.parseInt(args[i+2]);
            }
            if(args[i].equals(COMP))
            {
                if(args[i+1].equals("<="))
                    ascending=true;
                else if(args[i+1].equals(">="))
                    ascending=false;
            }

        }
        if(!stats)
        {        double time;

            if(args.length>=2 &&args[0].equals(T))
            {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Podaj rozmiar tablicy:");
                int n = scanner.nextInt();
                System.out.println("Podaj kolejne klucze tablicy");
                int[] element =new int[n];
                for(int i=0; i<element.length;i++){
                    element[i]=scanner.nextInt();
                }
                scanner.close();
                counter=0;
                swaps=0;
                switch(args[1]) {

                    case MERGE:{
                        long start=System.nanoTime();
                        mergeSort(ascending, element, element.length-1);
                        time=(double)(System.nanoTime()-start)/1_000_000;
                        System.out.println("Merge time: "+time+" ms  swaps: "+swaps+"  compares: "+counter);

                        print_list(element);
                        is_array_sorted(element,ascending);

                        break;
                    }
                    case Q:{
                        long start=System.nanoTime();
                        quick_sort(ascending, element, 0, element.length-1);
                        time=(double)(System.nanoTime()-start)/1_000_000;
                        System.out.println("QS time: "+time+" ms  swaps: "+swaps+"  compares: "+counter);

                        print_list(element);
                        is_array_sorted(element,ascending);

                        break;
                    }
                    case DQ:{
                        long start=System.nanoTime();
                        dual_quick_sort(ascending, element, 0, element.length-1);
                        time=(double)(System.nanoTime()-start)/1_000_000;
                        System.out.println("DQS time: "+time+" ms  swaps: "+swaps+"  compares: "+counter);

                        print_list(element);
                        is_array_sorted(element,ascending);

                        break;
                    }
                    case I:{

                        long start=System.nanoTime();
                        insertionSort(ascending, element, 0, element.length-1);
                        time=(double)(System.nanoTime()-start)/1_000_000;
                        System.out.println("InsertSort time: "+time+" ms  swaps: "+swaps+"  compares: "+counter);

                        print_list(element);
                        is_array_sorted(element,ascending);

                        break;
                    }
                    case M:{
                        long start=System.nanoTime();
                        modified_quick_sort(ascending, element, 0, element.length-1);
                        time=(double)(System.nanoTime()-start)/1_000_000;
                        System.out.println("modifiedQS time: "+time+" ms  swaps: "+swaps+"  compares: "+counter);

                        print_list(element);
                        is_array_sorted(element,ascending);
                        break;
                    }
                }
            }
        }
        else{
            print_stats(ascending,fileName,k);
        }


    }


    private static void print_stats(boolean ascending,String fileName,int k) {

            long start;
            StringBuilder c =new StringBuilder();
            StringBuilder c1 =new StringBuilder();
            StringBuilder c2 =new StringBuilder();
            StringBuilder c3 =new StringBuilder();
        StringBuilder c4 =new StringBuilder();



            for(int i=100; i<=10000;i+=100)
            {
                int[] arr=new int[i];
                double time1=0,time2=0, time3=0,time4=0,time5=0,time=0;
                long count1=0,count2=0,count3=0,count4=0,count5=0;
                long swaps1=0,swaps2=0,swaps3=0,swaps4=0,swaps5=0;
                for(int j=0; j<k;j++){
                    randomArr(arr);
                    counter=0;
                    swaps=0;
                    start=System.nanoTime();
                    quick_sort(ascending, cloningArr(arr), 0, arr.length-1);
                    time=(double)(System.nanoTime()-start)/1_000_000;
                    time1+=time;
                    count1+=counter;
                    swaps1+=swaps;
                    counter=0;
                    swaps=0;
                    start=System.nanoTime();
                    modified_quick_sort(ascending, cloningArr(arr), 0, arr.length-1);
                    time=(double)(System.nanoTime()-start)/1_000_000;
                    time2+=time;
                    count2+=counter;
                    swaps2+=swaps;
                    counter=0;
                    swaps=0;
                    start=System.nanoTime();
                    mergeSort(ascending, cloningArr(arr), arr.length-1);
                    time=(double)(System.nanoTime()-start)/1_000_000;
                    time3+=time;
                    count3+=counter;
                    swaps3+=swaps;
                    counter=0;
                    swaps=0;
                    start=System.nanoTime();
                    insertionSort(ascending, cloningArr(arr), 0, arr.length-1);
                    time=(double)(System.nanoTime()-start)/1_000_000;
                    time4+=time;
                    count4+=counter;
                    swaps4+=swaps;
                    counter=0;
                    swaps=0;
                    start=System.nanoTime();
                    dual_quick_sort(ascending, cloningArr(arr), 0, arr.length-1);
                    time=(double)(System.nanoTime()-start)/1_000_000;
                    time5+=time;
                    count5+=counter;
                    swaps5+=swaps;


                }
                c.append("quicksortaveragecomp "+i+" "+(double)(count1/k)+" swaps "+ (double)(swaps1/k)+" time " +(double)(time1/k)+" ms");
                c.append(System.getProperty("line.separator"));

                c1.append("modifiedquicksortaveragecomp "+i+" "+(double)(count2/k)+" swaps "+ (double)(swaps2/k)+" time " +(double)(time2/k)+" ms");
                c1.append(System.getProperty("line.separator"));

                c2.append("mergesort "+i+" "+(double)(count3/k)+" swaps "+ (double)(swaps3/k)+" time " +(double)(time3/k)+" ms");
                c2.append(System.getProperty("line.separator"));

                c3.append("insertionsortaveragecomp "+i+" "+(double)(count4/k)+" swaps "+ (double)(swaps4/k)+" time " +(double)(time4/k)+" ms");
                c3.append(System.getProperty("line.separator"));

                c4.append("dual_quicksort "+i+" "+(double)(count5/k)+" swaps "+ (double)(swaps5/k)+" time " +(double)(time5/k)+" ms");
                c4.append(System.getProperty("line.separator"));

            }
            String st=c.toString();
            String nd=c1.toString();
            String th=c3.toString();
            String rd=c2.toString();
            String s=c4.toString();

            StringBuilder fin =new StringBuilder();
            fin.append("Name                    arraylength   comparision swaps   time");
            fin.append(System.getProperty("line.separator"));
            fin.append(st);
            fin.append(nd);
            fin.append(rd);
            fin.append(th);
            fin.append(s);

            String fin1=fin.toString();
            OutputStream os = null;
            try {
                os = new FileOutputStream(new File("./"+fileName+".txt"));
                os.write(fin1.getBytes(), 0, fin1.length());
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


}
