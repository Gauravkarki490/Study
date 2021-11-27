import java.io.*;
import java.util.*;
//Bipartite graph
// Graph which should not contain cycle but if it contain a cycle it should be of even length

public class BIpartite
{
    static class Edge 
    {
      int src;
      int nbr;
      int wt;

      Edge(int src, int nbr, int wt) {
         this.src = src;
         this.nbr = nbr;
         this.wt = wt;
      }
   }
   static class Pair{
       int v;
       String psf;
       int lvl;
       Pair(int v,String psf,int lvl)
       {
           this.v=v;
           this.psf=psf;
           this.lvl=lvl;
       }
   }

    public static void main(String args[])  throws Exception
    {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int vtces =Integer.parseInt(br.readLine());
         ArrayList<Edge>[] graph = new ArrayList[vtces];
         for(int i=0;i<vtces;i++)
         {
              graph[i] = new ArrayList<>();
         }
         int edges =Integer.parseInt(br.readLine());

          for (int i = 0; i < edges; i++) 
          {
                String[] parts = br.readLine().split(" ");
                int v1 = Integer.parseInt(parts[0]);
                int v2 = Integer.parseInt(parts[1]);
                int wt = Integer.parseInt(parts[2]);
                graph[v1].add(new Edge(v1, v2, wt));
                graph[v2].add(new Edge(v2, v1, wt));
          }

        
       int visited[] = new int[vtces];
       Arrays.fill(visited,-1);

       for( int i=0;i<vtces;i++)
       {
           if(visited[i] == -1)
           {
               if(!helper(graph,visited,i)){
                   System.out.println(false);
                   return ;
               }
           }
       }
       System.out.println(true);
         
        

    }
    public static boolean helper(ArrayList<Edge> graph[],int [] visited,int src)
    {
        ArrayDeque<Pair> qu=new ArrayDeque<>();
        q.add(new Pair(src,src+"",0));
        while(q.size()>0)
        {
            // remove
            Pair rem=qu.removeFirst();
            // mark *
            if(visited[rem.v] != -1)
            {
                // some work to do
                if(rem.lvl != visited[rem.v])
                {
                    return false;
                }
            }
            else
            {
                visited[rem.v] = rem.lvl;
            }
            for(Edge e: graph[rem.v])
            {
                if(visited[e.nbr] == -1)
                {
                    qu.add(new Pair(e.nbr,rem.psf+e.nbr,rem.lvl+1));
                }
            }

        }
         return true;
       
    }

}
