package hufs.eselab.DivideAndConquer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DivideAndConquer {
    int idx;
    public DivideAndConquer() {
        this.idx=0;
    }

    /**
     * Merge Sorting
     * @param li
     * @return
     */
    public List<Integer> mergeSort(List<Integer> li){
        List<Integer> ret = new ArrayList<Integer>();
        int half_last = 0;
        if(li.size() ==1){  //기저 사례
            return li;
        }
        if(li.size()%2 == 0){//짝수
            half_last = li.size()/2;
        }else if(li.size()%2 ==1){ //홀수
            half_last = (li.size()+1)/2;
        }
        List<Integer> ll;
        List<Integer> lr;

        ll = mergeSort((List<Integer>) li.subList(0,half_last));
        lr = mergeSort((List<Integer>) li.subList(half_last,li.size()));

        ret = mergSort_merg(ll,lr);

        return  ret;
    }

    /**
     * Merge 하는 부분
     * @param ll 왼쪽 list
     * @param lr 오른쪽 list
     * @return 정렬후 합친 부분
     */
    private List<Integer> mergSort_merg(List<Integer> ll, List<Integer> lr){
        ArrayList<Integer> ret = new ArrayList<Integer>();
        int i = 0;
        int j=0;
        // for(Intger i : list) 시도하면 list의 크기가 변동하여 에러가 난다
        while((i<(ll.size())) || (j<(lr.size()))){
            //ll이 빔
            if(i==(ll.size())) {ret.addAll(lr.subList(j,lr.size()));return ret;}
            //lr이 빔
            if(j==(lr.size())) {ret.addAll(ll.subList(i,ll.size()));return ret;}

            if(ll.get(i) < lr.get(j)){  //넣어준다.
                ret.add(ll.get(i));
                i++;
            }else{
                ret.add(lr.get(j));
                j++;
            }
        }
        return ret;
    }


    public List<Integer>  quickSoritng(List<Integer>li){
        List<Integer> ret = new ArrayList<Integer>();

        if(li.size() == 1){ //기저 사례: 1개일때 ret

            return li;
        }

        Integer pivot = li.get(0);
        li.remove(pivot);   //피벗 넣은것은 삭제해줘야한다.

        List<Integer> ll = new ArrayList<Integer>();
        List<Integer> lr = new ArrayList<Integer>();

        for(Integer i : li){    //O(n)
            if(i<pivot) ll.add(i);
            else lr.add(i);
        }
        if(!ll.isEmpty()) ll = quickSoritng(ll);
        if(!lr.isEmpty()) lr = quickSoritng(lr);

        ret = quickSorting_merge(ll,pivot,lr);


        return ret;
    }
    private List<Integer> quickSorting_merge(List<Integer> ll, int pivot, List<Integer> lr){
        List<Integer> ret = new ArrayList<Integer>();

        ret.addAll(ll);
        ret.add(pivot);
        ret.addAll(lr);

        return ret;
    }


    //공부한대로 생각하자
    //더 깊게 들어갔을때 j를 그만큼 더 이동해줘야하지만, 이전 재귀에서는 알길이 없다.
//    public String reverse_quadTree(String s_quad){
//        String ret = "";
//        List<String> li_for_4 = new ArrayList<String>();
//        if(s_quad.length() == 1) {
//            return s_quad;
//        }
//
//
//        for(int i=1; i<5; i++){ //4분면 확인 그냥 4번 돌림
//            String temp = "";
//            int j=1; //string에 접근하는 변수
//            if(s_quad.substring(j,j+1).equals("x")){
//                li_for_4.add(reverse_quadTree(s_quad.substring(j,s_quad.length())));
////                s_quad = s_quad.substring(0,i)+ s_quad.substring(i+4,s_quad.length());
//                j += 5;
//            }else {li_for_4.add(s_quad.substring(j,j+1));j++;}
//
//        }
//        ret = "x"+reverse_4(li_for_4.get(0),li_for_4.get(1),li_for_4.get(2),li_for_4.get(3));
//
//
//
//        return ret;
//    }
//    private String reverse_4(String one, String two, String tre, String four){
//        String str = tre + four + one + two;
////        System.out.println(str);
//        return str;
//
//    }

    //하나씩 이동하면서 해결

    /**
     *
     * @param s_quad 입력 str
     * @return
     */
    public String reverse_quadTree(String s_quad){
        String head = s_quad.substring(idx,idx+1);
        idx++;

        if(head.equals("b") || head.equals("w")) return head;

        String upperLeft = reverse_quadTree(s_quad);
        String upperRight = reverse_quadTree(s_quad);
        String lowerLeft = reverse_quadTree(s_quad);
        String lowerRight = reverse_quadTree(s_quad);
        return "x" + lowerLeft + lowerRight + upperLeft + upperRight;
    }


    /**
     * Brute Force - O(n^2)
     * @param blocks
     * @return
     */
    public Integer cutting_box(List<Integer> blocks){
        int ret = 0;
        if(blocks.isEmpty()) return ret;//맨 마지막에 끝내기

        int temp_res = 0;
//        blocks.remove(0);//맨앞에거 제거
//        System.out.println("in");
        for(int i=1; i<=blocks.get(0) ; i++){//위로 올라감

            for(int j=0; j<blocks.size() ; j++){//두번째것 부터
                if(i<=blocks.get(j)){
                    temp_res = Math.max(temp_res,i*(j+1));
                }else break;
            }
            temp_res = Math.max(temp_res,i);    //현재 block이 더 큼
        }

        ret = Math.max(temp_res, cutting_box(blocks.subList(1, blocks.size())));//왼쪽으로 하나씩 생각

        return ret;
    }


}
