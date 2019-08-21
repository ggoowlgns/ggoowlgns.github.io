package hufs.eselab;

import java.util.ArrayList;

public class NoteBook {
    public static void main(String[] args){
        ArrayList<Integer> al = new ArrayList<Integer>();
        al.add(1);
        al.add(2);

        ArrayList<Integer> te = clone_arraylist(al);
        al.add(3);
        te.add(4);
        for(int i  : te){
            System.out.println(i);
        }
        for(int i  : al){
            System.out.println(i);
        }
    }
    private static ArrayList<Integer> clone_arraylist(ArrayList<Integer> al){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        for(Integer r : al){
            ret.add(r);
        }
        return ret;
    }
}
