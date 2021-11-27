import java.io.*;
import java.util.*;

public class ConnectedComp {
   static class Edge {
      int src;
      int nbr;
      int wt;

      Edge(int src, int nbr, int wt) {
         this.src = src;
         this.nbr = nbr;
         this.wt = wt;
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
         int wt = Integer.parseInt(parts[2]);
         graph[v1].add(new Edge(v1, v2, wt));
         graph[v2].add(new Edge(v2, v1, wt));
      }

      ArrayList<ArrayList<Integer>> comps = new ArrayList<>();
      
      // write your code here
      boolean [] visted =new boolean[vtces];
      int j=0;
      for(int i=0;i<vtces;i++)
      {

          if(!visted[i])
          {    comps.add(new ArrayList<>());
              connectedComp(graph,i,visted,comps.get(j));
              j++;
          }
      }
      System.out.println(comps);
   }
   public static void connectedComp(ArrayList<Edge> graph[],int src,boolean visted[],ArrayList<Integer> list){

    visted[src]=true;
    list.add(src);
    for(Edge a:graph[src]){
        if(!visted[a.nbr])
        {
            connectedComp(graph,a.nbr,visted,list);
        }
    }
   }
}