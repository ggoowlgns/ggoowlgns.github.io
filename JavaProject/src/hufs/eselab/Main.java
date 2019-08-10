package hufs.eselab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

        /*char[][] st = {{'u','r','l','p','m'},
                        {'x','p','r','e','t'},
                        {'g','i','a','e','t'},
                        {'x','t','n','z','y'},
                        {'x','o','q','r','s'}};
        BruteForce bf = new BruteForce(st);
        boolean b = false;
        int i=0;
        int j=0;

        loop:
        for(i=0;i<st[0].length;i++){
            for(j=0;j<st.length;j++){

                b = bf.hasWord(i,j,"agdgagg");

                if(b) { break loop;}
            }
        }
        System.out.println("i: "+i+"j : "+j);*/

        //입,출력 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BruteForce bruteForce = new BruteForce();

        try {
            int num_case = Integer.parseInt(br.readLine());
            int[] ret = new int[num_case];

            for(int i=0 ;i<num_case;i++){
                String[] stu =  br.readLine().split(" ");
                int[] stu_int = new int[stu.length];
                for(int j=0;j<stu.length;j++){
                    stu_int[j] = Integer.parseInt(stu[j]); //그냥 str array to int array
                }
                String[] pairs =  br.readLine().split(" ");
                int[] p_int = new int[pairs.length];
                for(int j=0;j<pairs.length;j++){
                    p_int[j] = Integer.parseInt(pairs[j]); //그냥 str array to int array

                }
//                for(int k : p_int) {
//                    System.out.println(k);
//                }
                ret[i] = bruteForce.countPairing(stu_int,p_int);

            }
//            for(int r : ret) {
//                System.out.println(r);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
