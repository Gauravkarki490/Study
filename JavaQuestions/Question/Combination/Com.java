import java.io.*;
import java.util.*;

public class Com{

    public static void main(String args[]) throws Exception{
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        int r=Integer.parseInt(br.readLine());

        int C[][] = new int[n+1][r+1];
        for(int i=0;i<=n;i++)
        {
            int k=Math.min(i,r);
            for(int j=0;j<=k;j++){
                if(j == 0 || j == i)
                {
                    C[i][j] = 1 ;
                }
                else{
                    C[i][j] = C[i-1][j-1] + C[i-1][j]; 
                }
            }

        }
        for(int i=0;i<=n;i++)
        {
            System.out.println(Arrays.toString(C[i]));
        }
    }

}