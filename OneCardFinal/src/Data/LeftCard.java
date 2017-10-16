package Data;

import Main.PlayScreen;
import Network.Client;

public class LeftCard implements Runnable{
	int id;
	int cs;
	
	public LeftCard(int id,int cs){
		this.id=id;
		this.cs=cs;
	}
	
	public void run() {		
		
		switch(id){
		case 0:
			if(Client.idNum==0){
					PlayScreen.ccnt4.setText(cs+"장");
				}else {
					PlayScreen.ccnt1.setText(cs+"장");
				}	
			break;
		case 1:
			if(Client.idNum==1){
				PlayScreen.ccnt4.setText(cs+"장");
			}else{
				PlayScreen.ccnt2.setText(cs+"장");
			}	
			break;
		case 2:
			if(Client.idNum==2){
				PlayScreen.ccnt4.setText(cs+"장");
			}else {
				PlayScreen.ccnt3.setText(cs+"장");
			}	
			break;
		case 3:
			if(Client.idNum==3){
				PlayScreen.ccnt4.setText(cs+"장");
			}else{
				PlayScreen.ccnt3.setText(cs+"장");
			}	
			break;
			
		default:
			break;
		}	
		
	}


}
