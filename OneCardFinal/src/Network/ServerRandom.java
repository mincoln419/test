package Network;

public class ServerRandom implements Runnable{
	 private int[] ran = new int[54];
	 String num;
	 
	@Override
	public void run() {
		 for(int i=0;i<ran.length;i++){
	         ran[i]=(int)(Math.random()*54);
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
		  Server.sendRandom(num);
	}
	
	
}
