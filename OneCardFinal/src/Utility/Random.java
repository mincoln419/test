package Utility;

import java.util.StringTokenizer;

import Network.Client;

public class Random implements Runnable{	
	
	
	
	public static String getNum;
	   String num;
	   public Random(String num){
	      this.num=num;
	   }
	
	public void run() { ///////////severRandom���� client�� ���� ���� �������迭
		   
		  if(num!=null){
		         StringTokenizer st=new StringTokenizer(num);
		         int k=0;
		         while(st.hasMoreTokens()){
		         Client.num[k]=Integer.parseInt(st.nextToken());
		        
		         k++;         
		         }
		         }  
		  
		   }               
		   
	
	
	
		   //////////////////////////////�ϴ� �ӽ÷� �̰ɷ� ������.
		   
//		   for(int i=0;i<num.length;i++){
//		         num[i]=(int)(Math.random()*54);
//		         for(int j=0;j<i;j++){
//		            if(num[j]==num[i]){
//		               i--;
//		            }
//		         }         
//		      }	
		   
	     }	   	

