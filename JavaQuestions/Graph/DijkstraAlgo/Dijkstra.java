import java.io.*;
import java.util.*;

class Dijkstra{

   static class Edge {
      int src;
      int nbr;
      int wt;
      Edge(int src, int nbr,int wt) {
         this.src = src;
         this.nbr = nbr;
         this.wt=wt;
      }
   }
    static class Pair implements Comparable<Pair>{
        int v;
        String psf;
        int wsf;
        Pair(int v,String psf,int wsf){
            this.v=v;
            this.psf=psf;
            this.wsf=wsf;
        }
        public int compareTo(Pair a)
        {
            return this.wsf - a.wsf;
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
         int v3 = Integer.parseInt(parts[2]);
         graph[v1].add(new Edge(v1, v2,v3));
         graph[v2].add(new Edge(v2, v1,v3));
      }

      int src = Integer.parseInt(br.readLine());
   
      boolean visited[]=new boolean[vtces];
    //   int count=helper(graph,visited,src,t);

    //   when function was void then
    //   int count=0;
    //   for(int i=0;i<vtces;i++)
    //   {
    //       if(visited[i])
    //       count++;
    //   }
    //   System.out.println(count);
    PriorityQueue<Pair> pq = new PriorityQueue<>();
    pq.add(new Pair(src,src+"",0));

    while(pq.size()>0)
    {
        Pair rem= pq.remove();
        if(visited[rem.v])
        {
            continue;
        }
        visited[rem.v]=true;
        System.out.println(rem.v+" via "+rem.psf+" @ "+rem.wsf);
        for(Edge e:graph[rem.v])
        {
            if(!visited[e.nbr])
            {
                pq.add(new Pair(e.nbr,rem.psf+e.nbr,rem.wsf+e.wt));
            }
        }
    }
    
   }

  

}