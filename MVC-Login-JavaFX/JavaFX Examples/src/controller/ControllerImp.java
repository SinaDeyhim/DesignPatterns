package controller;

import java.util.Observer;

import model.Access;

public class ControllerImp implements Controller{

	private Access access;
	
	public void setModel( Access access){
		this.access = access;
	}
	
	@Override
	public void login(String user, String pass) {
		access.login(user, pass);
	}

	@Override
	public void addObserver(Observer o) {
		access.addObserver(o);
	}
}
