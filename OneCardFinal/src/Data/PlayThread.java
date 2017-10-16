package Data;


public class PlayThread implements Runnable{	
	String idname;
	PlayThread(String idname){
		this.idname=idname;
	}
	
	@Override
	public void run() {
	new CardMove(idname);
			
	}	
}