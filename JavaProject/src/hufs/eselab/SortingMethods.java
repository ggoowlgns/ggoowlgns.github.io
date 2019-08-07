package hufs.eselab;

import java.util.ArrayList;

public class SortingMethods {
    public SortingMethods() {
    }

    /**
     * 선택 정렬 -> 앞에서 하나씩 비교해 가면서 작으면 바꾼다.
     *  (N)(N-1)/2 = O(N^2)
     * @param li
     */
    public void selectioonSort(ArrayList<Integer> li){
        for(int i = 0 ; i < li.size() ; i++){
            int minIndex = i;

            for(int j = i+1 ; j < li.size() ;j++){  //이번 루프중에서 가장 작은 수의 idx 찾기
                if(li.get(minIndex) > li.get(j)) {
                    minIndex = j;
                }
            }
            swap(li, minIndex,i);
        }
    }

    /**
     * 삽입 정렬
     * 최선 : O(N) , 최악 : O(N^2)
     * 즉 거의 대부분의 경우 : 삽입정렬 > 선택정렬 (O(N^2) 정렬 중 가장 빠른 알고리즘)
     * @param li
     */
    public void insertionSort(ArrayList<Integer> li){
        for(int i =0; i<li.size() ; i++){
            int j = i;
            while(j > 0 && li.get(j-1)>li.get(j)){
                swap(li, j-1,j);
                --j;    //이전에 있는 것들이랑 비교
            }
        }
    }
    /**
     * 어쩔수가 없이 ArrayList를 같이 줘야함 -> C 처럼 Call by Ref가 안된다..
     * 이럴땐 C가 그립다..
     * @param li
     * @param i
     * @param j
     */
    private void swap(ArrayList<Integer> li ,int i, int j){
        int temp =0;
        temp = li.get(i);
        li.set(i,li.get(j));
        li.set(j, temp);

    }
}
