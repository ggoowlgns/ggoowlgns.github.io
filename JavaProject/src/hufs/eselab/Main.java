package hufs.eselab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	// write your code here
        LinearMethods l = new LinearMethods();
        SortingMethods s = new SortingMethods();
        SearchingMethods sc = new SearchingMethods();

        Integer[] temp = {67,56,43,43,41,43,56,60,65};
        Integer temp2[] = {-7,4,-3,6,3,-8,3,4};

        ArrayList<Integer> weight = new ArrayList<Integer>(Arrays.asList(temp));
        ArrayList<Integer> al_2 = new ArrayList<Integer>(Arrays.asList(temp2));

        System.out.println(l.movingAverage1(weight,3));
        System.out.println(l.movingAverage2(weight,3));
//        s.selectioonSort(weight);
        s.insertionSort(weight);
        System.out.println(weight);
//        ArrayList<Integer> li = new ArrayList<Integer>();
//        li.add(10);
//        li.add(20);
//
//        s.swap(li,0,1);
//        System.out.println(li);
//        System.out.println(sc.inefficientMaxSum(al_2));
//        System.out.println(sc.bettertMaxSum(al_2));
        System.out.println(sc.fastMaxSum(al_2,0,al_2.size()-1));
        System.out.println(sc.fastestMaxSum(al_2));
    }


}
