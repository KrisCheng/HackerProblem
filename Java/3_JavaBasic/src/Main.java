import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by Kris Peng on 10:37 2019/5/19 .
 * All right reserved.
 */

public class Main {
    public static int minValue(int[] arr){
        return 0;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        str = str.substring(1, str.length()-1).replace(",","");
        String[] res = str.split(" ");
        int[] arr = new int[res.length];
        for (int i = 0; i < res.length; i++) {
            arr[i] = Integer.parseInt(res[i]);
        }
        System.out.println(minValue(arr));
    }
}
