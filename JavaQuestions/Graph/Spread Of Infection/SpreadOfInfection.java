import java.io.*;
import java.util.*;

class SpreadOfInfection{

   static class Edge {
      int src;
      int nbr;

      Edge(int src, int nbr) {
         this.src = src;
         this.nbr = nbr;
      }
   }
    static class Pair{
        int v;
        String psf;
        int count;
        Pair(int v,String psf,int count){
            this.v=v;
            this.psf=psf;
            this.count=count;
        }
    }

   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      int vtces = Integer.parseInt(br.readLine());
      ArrayList<Edge>[] graph = new ArrayList[vtces];
      for (int i = 0; i < vtces; i++) {
         graph[i] = new ArrayList<>();
      }

      int edges = Integer.parseInt(br.readLine());
      for (int i = 0; i < edges; i++) {
         String[] parts = br.readLine().split(" ");
         int v1 = Integer.parseInt(parts[0]);
         int v2 = Integer.parseInt(parts[1]);
         graph[v1].add(new Edge(v1, v2));
         graph[v2].add(new Edge(v2, v1));
      }

      int src = Integer.parseInt(br.readLine());
      int t = Integer.parseInt(br.readLine());
      boolean visited[]=new boolean[vtces];
      int count=helper(graph,visited,src,t);

    //   when function was void then
    //   int count=0;
    //   for(int i=0;i<vtces;i++)
    //   {
    //       if(visited[i])
    //       count++;
    //   }
      System.out.println(count);
    
   }

   public static int helper(ArrayList<Edge> [] graph,boolean [] visited,int src,int t)
   {
       ArrayDeque<Pair> qu =  new ArrayDeque<>();
       qu.add(new Pair(src,src+"",1));
       int count=0;
       while(true)
       {
        //    remove
        Pair rem=qu.removeFirst();
       
        if(visited[rem.v])
        {
            continue;
        }
        visited[rem.v]=true;
        for(Edge e:graph[rem.v]){
            if(!visited[e.nbr])
            {
                 if(rem.count+1>t)
                {
                    return count;
                }
                count++;

                qu.add(new Pair(e.nbr,e.nbr+rem.psf,rem.count+1));
            }
        }
       }
       
   }

}