import java.util.*;

public class Demo{

    
    
    
    
    
    



    public static void main(String[] args)
    {
                int n=45;
                char ch[]= Integer.toBinaryString(n).toCharArray();
               
                
                String str="";
               
               int i=0,j=ch.length-1;
               while(i<j)
               {
                   char tem=ch[i];
                   ch[i]=ch[j];
                   ch[j]=tem;
                   i++;
                   j--;
               }
               
               for(i=1;i<=ch.length;i++)
               {
                   if(i%2==0)
                   {
                       ch[i-1]='0';
                   }
                  
               }
               i=0;
               j=ch.length-1;
                while(i<j)
               {
                   char tem=ch[i];
                   ch[i]=ch[j];
                   ch[j]=tem;
                   i++;
                   j--;
               }
                for(i=0;i<ch.length;i++)
               {
                   str+=ch[i];
                  
               }
                System.out.println(Arrays.toString(ch));
                int no=Integer.parseInt(str,2);
                System.out.println(no);


    }




}