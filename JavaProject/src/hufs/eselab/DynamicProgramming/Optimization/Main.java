package hufs.eselab.DynamicProgramming.Optimization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        //addedLIS
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int c = Integer.parseInt(br.readLine());
        List<Integer> ret_li = new ArrayList();
        for(int i=0; i<c ; i++){
            String n_m = br.readLine();
            String[] n_m_sarry = n_m.split(" ");
            int n = Integer.parseInt(n_m_sarry[0]);
            int m = Integer.parseInt(n_m_sarry[1]);
            String a_str = br.readLine();
            List<Integer> a = new ArrayList();
            for(String s : a_str.split(" ")){
                a.add(Integer.parseInt(s));
            }
            String b_str = br.readLine();
            List<Integer> b = new ArrayList();
            for(String s : b_str.split(" ")){
                b.add(Integer.parseInt(s));
            }
            JoinedLIS jlis = new JoinedLIS(a,b);
            int ret = jlis.jlis(0,0);
            ret_li.add(ret);
        }


        for(Integer temp : ret_li){
            System.out.println(temp);
        }

    }
}
