package hufs.eselab.DivideAndConquer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DivideAndConquer dc = new DivideAndConquer();

        Integer[] list = {38,27,43,9,3,82,10,1,3100,20,4,3,15,35,235,235,35235,4124,3553};
//        Integer[] list = {10,9,8,7,6,5,4,3,2,1};
        List<Integer> al = new ArrayList<Integer>(Arrays.asList(list));

        long startTime = System.nanoTime();
        al = dc.mergeSort(al);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        for(Integer a : al){
            System.out.print(a+ " ");
        }
        System.out.println();
        System.out.println("Merge Sorting duration : " + (double)duration/1000000 + "ms");
        System.out.println();



        startTime = System.nanoTime();
        al = dc.quickSoritng(al);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        for(Integer a : al){
            System.out.print(a+ " ");
        }
        System.out.println();
        System.out.println("Quick Sorting duration : " + (double)duration/1000000 + "ms");
        System.out.println();



        startTime = System.nanoTime();
        Collections.sort(al);
        endTime = System.nanoTime();
        duration = (endTime - startTime);
        for(Integer a : al){
            System.out.print(a+ " ");
        }
        System.out.println();
        System.out.println("Collections Sorting duration : " + (double)duration/1000000 + "ms");


//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int c_num =  Integer.parseInt(br.readLine());
//        List<String> ret = new ArrayList<String>();
//
//        for(int i=0; i<c_num ; i++){
//            dc = new DivideAndConquer();
//            String s_quad = br.readLine();
//            ret.add(dc.reverse_quadTree(s_quad));
//        }
//
//        for(String s : ret){
//            System.out.println(s);
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c_num =  Integer.parseInt(br.readLine());
        List<Integer> ret = new ArrayList<Integer>();

        for(int i=0; i<c_num ; i++){
            dc = new DivideAndConquer();
            Integer block_num = Integer.parseInt(br.readLine());
            List<String> block_str = Arrays.asList(br.readLine().split(" "));
            List<Integer> block_int = new ArrayList<Integer>();
            for(int j =0; j<block_num ; j++){
                block_int.add(Integer.parseInt(block_str.get(j)));
            }


            ret.add(dc.cutting_box(block_int));
        }

        for(Integer s : ret){
            System.out.println(s);
        }


    }
}
