import java.io.*;
import java.util.*;


public class MaxTenureFinder{

     static class Pair{
        int count;
        int sum;
        Pair(int count,int sum){
            this.count=count;
            this.sum=sum;
        }
     }
      public static ArrayList<Pair> list= new ArrayList<>();
      public static ArrayList<Integer> Par = new ArrayList<>();

    public static  void main(String args[]) throws Exception{
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();

        for(int i=0;i<n;i++)
        {
            int parent = Integer.parseInt(br.readLine());
            int child = Integer.parseInt(br.readLine());
            if(map.containsKey(parent))
            {
                ArrayList<Integer> temp =map.get(parent);
                temp.add(child);
                map.put(parent,temp);
            }
            else{
                ArrayList<Integer> temp =new ArrayList<>();
                temp.add(child);
                map.put(parent,temp);
            }

            if( !map.containsKey(child) )
            {
                map.put(child,new ArrayList<>());
            }
        }
        System.out.println(map);
        Pair temp = helper(map,20);
        double max=0;
        int par=0;
        for(int i=0;i<list.size();i++)
        {
                System.out.println(list.get(i).count+" "+list.get(i).sum);
                double tp=(double)list.get(i).sum/list.get(i).count;
                if(tp>max)
                {
                    max=tp;
                    par=Par.get(i);
                }
        }
        System.out.println(par+" "+ max);



    }
   
    
   
    public static Pair helper( HashMap<Integer, ArrayList<Integer>> map , int src)
    {
        if(map.get(src).size() == 0)
        {
            
            return new Pair(1,src);
        }
        else{
            int count = 1;
            int sum = src;
            for(int e:map.get(src)){
                Pair temp = helper(map,e);
                count+=temp.count;
                sum+=temp.sum;
            }
            Pair temp = new Pair(count,sum);
            list.add(temp);
            Par.add(src);
            return temp;
        }
    }
}