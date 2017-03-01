package courses;

import java.util.Arrays;

/**
 * Created by arxemond777 on 27.01.17.
 */
public class TwoLearning
{
    public static void main(String[] args) {

//        int [][] arr1 = new int[5][];
        int [] arr1 = new int[5];
        int [] arr2 = new int[5];
        System.out.println(Arrays.toString(arr1));
        //с многомерными не работает
//        arr1[0][0] = 1;
//        arr1[1][2] = 1;
        arr1[0] = 1;
        arr1[1] = 1;
        arr2[0] = 2;
        arr2[1] = 2;

        // copies an array from the specified source array
        System.arraycopy(arr1, 0, arr2, 0, 1);
        System.out.print("array2 = ");
        System.out.print(arr2[0] + " ");
        System.out.print(arr2[1] + " ");
        System.out.print(arr2[2] + " ");
        System.out.print(arr2[3] + " ");
        System.out.print(arr2[4] + " ");
    }
}
