package hufs.eselab;

import java.util.ArrayList;

public class ExponentialMethods {
    int INF = 987654321;
    int M;
    ExponentialMethods(int M){
        this.M = M;
    }
    //구현 x
    private boolean canEverybodyEat(ArrayList<Integer> menu){
        return false;
    }
    /**
     * canEverybodyEat func 의 시간 복잡도는 N*M 이라 가정한다
     * 시간 복잡도 : N*M * 2^M
     *
     * @param menu
     * @param food
     * @return
     */
    private int selectMenu(ArrayList<Integer> menu, int food){
        if(food == M){ //모든 음식을 만들경우
            if(canEverybodyEat(menu)) return menu.size();
            else return INF;
        }

        //이 음식을 만들지 않는 경우의 답
        int ret = selectMenu(menu, food+1);

        //이 음식을 만들경우
        menu.add(food);
        ret = Math.min(ret, selectMenu(menu, food+1));//더 작은걸로
        menu.remove(menu.size()-1);
        return ret;
    }
}
