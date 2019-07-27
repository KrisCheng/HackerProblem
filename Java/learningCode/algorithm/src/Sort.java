/**
 * @author: Peng Cheng
 * @description:
 * @since: 2019/7/27 10:02
 */
public class Sort {
    public void BubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

    public void SelectSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[min] > arr[j]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    public void InsertSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && arr[j + 1] > arr[i]) {

                }
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 42, 6, 94 ,-45, 543, 23, -10, 4, 534};
        Sort sort = new Sort();
//        sort.BubbleSort(arr);
//        sort.SelectSort(arr);
        sort.InsertSort(arr);
        for(int i : arr) {
            System.out.println(i);
        }
    }
}
