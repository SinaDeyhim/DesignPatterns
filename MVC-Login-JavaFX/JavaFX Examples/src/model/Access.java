package model;

import java.util.Observable;

public class Access extends Observable{

	private String username, pass;
	
	public Access( String user, String pass) {
		this.username = user;
		this.pass = pass;
	}
	
	public void login( String user, String pass){
		String[] message;
		if( user.equals(username)&& pass.equals(this.pass)){
			message = new String[]{user,"has access"};
		}else{
			message = new String[]{user,"access denied"};
		}
		setChanged();
		notifyObservers( message);
	}
}
