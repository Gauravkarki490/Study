import java.io.*;
import java.util.*;

public class island {
   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

      int m = Integer.parseInt(br.readLine());
      int n = Integer.parseInt(br.readLine());
      int[][] arr = new int[m][n];

      for (int i = 0; i < arr.length; i++) {
         String parts = br.readLine();
         for (int j = 0; j < arr[0].length; j++) {
            arr[i][j] = Integer.parseInt(parts.split(" ")[j]);
         }
      }

      // write your code here
      int row[]={0,0,-1,1};
      int col[]={1,-1,0,0};
      int count=0;
      for(int i=0;i<m;i++)
      {
          for(int j=0;j<n;j++)
          {
              if(arr[i][j]!=2&&arr[i][j]!=1)
              {
                  islands(arr,i,j,row,col);
                  count++;
              }
          }
      }
      System.out.println(count);
   }
   
   public static void islands(int[][] arr,int i,int j,int row[],int col[]){
       
     arr[i][j]=2;
       
        for(int k=0;k<4;k++){
            if(i<arr.length&&j<arr[0].length&&i+row[k]<arr.length&&i+row[k]>=0&&j+col[k]<arr[0].length&&j+col[k]>=0&&arr[i+row[k]][j+col[k]]!=1&&arr[i+row[k]][j+col[k]]!=2)
            {
                islands(arr,i+row[k],j+col[k],row,col);
            }
        }
   }

}