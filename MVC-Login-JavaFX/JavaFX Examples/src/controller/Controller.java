package controller;

import java.util.Observer;

import model.Access;

public interface Controller {
	
	public void login( String user, String pass);
	
	public void setModel(Access a);
	
	public void addObserver(Observer o);
}
