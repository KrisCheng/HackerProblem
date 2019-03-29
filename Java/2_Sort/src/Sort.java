import java.util.Arrays;

/**
 * Created by Kris Peng on 08:35 2019/3/29 .
 * All right reserved.
 */

public class Sort {

//    bubble sort

    public void BubbleSort(int [] array){
        int length = array.length;
        for(int i = 0; i < length - 1; i++){
            for(int j = length - 1; j > i; j--) {
                if (array[j] < array[j - 1]) {
                    int swap = array[j];
                    array[j] = array[j - 1];
                    array[j - 1] = swap;
                }
            }
        }
    }

//    quick sort

    public void QuickSort(int [] array) {
        int length = array.length;
        QuickSort(array, 0, length - 1);
    }

    public void QuickSort(int [] array, int start, int end) {
        if(start >= end){
            return;
        }
        int p = Partition(array, start, end);
        QuickSort(array, start, p - 1);
        QuickSort(array, p + 1, end);
    }

    public int Partition(int [] array, int start, int end) {
        int i = start - 1;
        int pivot_index = end;
        int pivot = array[pivot_index];
        for(int j = start; j < end; j++){
            if(array[j] < pivot){
                i++;
                int swap = array[i];
                array[i] = array[j];
                array[j] = swap;
            }
        }
        i++;
        int swap = array[i];
        array[i] = pivot;
        array[pivot_index] = swap;
        return i;
    }

//    heap sort

    public void HeapSort(int [] array) {
        int length = array.length - 1;
        int beginIndex = (length - 1) / 2;
        
        for(int i = beginIndex; i >= 0; i--){
            maxHeapify(array, i, length);
        }

        for(int i = length; i > 0; i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            maxHeapify(array, 0, i - 1);
        }
    }

//    TODO 理解堆化过程。
    public void maxHeapify(int[] array, int index, int length){
        int li = (index * 2) + 1;
        int ri = li + 1;
        int cMax = li;
        if(li > length){
            return;
        }
        if(ri <= length && array[ri] > array[li]){
            cMax = ri;
        }
        if(array[cMax] > array[index]){
            int temp = array[cMax];
            array[cMax] = array[index];
            array[index] = temp;
            maxHeapify(array, cMax, length);
        }
    }



    public static void main(String[] args) {
        int[] arr = new int[]{1, 4, 8, 2, 55, 3, 4, 8, 6, 4, 0, 11, 34, 90, 23, 54, 77, 9, 2, 9, 4, 10};
        Sort s = new Sort();
//        s.BubbleSort(arr);
//        s.QuickSort(arr);
        s.HeapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
