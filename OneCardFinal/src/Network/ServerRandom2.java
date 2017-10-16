package Network;

public class ServerRandom2 implements Runnable{
	private int[] ran = new int[4];
	 String num;
	 
	@Override
	public void run() {
		 for(int i=0;i<ran.length;i++){
	         ran[i]=(int)(Math.random()*4);
	         for(int j=0;j<i;j++){
	            if(ran[j]==ran[i]){
	               i--;
	            }
	         }         
	      }	
		 num=ran[0]+" ";
	  for(int i=1;i<ran.length;i++){
		  num=num+ran[i]+" ";
	  }		  	
		  Server.sendFirstNum(num);
	}
	
}
