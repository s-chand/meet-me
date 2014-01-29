package com.iqube.app.meetme;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class LoginScreen extends MainScreen {
	private EditField emailfield;
	private PasswordEditField passwordfield;
	
	public LoginScreen() {
		super(USE_ALL_WIDTH|USE_ALL_HEIGHT|NO_HORIZONTAL_SCROLL);
		
		HorizontalFieldManager hman = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		hman.setBackground(BackgroundFactory.createLinearGradientBackground(Color.LIGHTSKYBLUE, Color.BLUE, Color.LIGHTBLUE, Color.BLUE));
		LabelField appTit = new LabelField("LOG IN");
		appTit.setFont(appTit.getFont().derive(Font.BOLD));
		hman.add(appTit);
		hman.setPadding(new XYEdges(7,7,7,7));
		setTitle(hman);
		
		Border border = BorderFactory.createRoundedBorder(new XYEdges(3,3,3,3), Border.STYLE_DASHED);
		XYEdges padding = new XYEdges(5,5,5,5);
		
		LabelField email = new LabelField("Email address");
		email.setPadding(new XYEdges(3,3,3,3));
		email.setFont(email.getFont().derive(Font.PLAIN, 24));
		
		emailfield = new EditField(EditField.FILTER_EMAIL);
		emailfield.setPadding(padding);
		emailfield.setBorder(border);
		emailfield.setMargin(new XYEdges(0,5,10,5));
		
		email.setMargin(new XYEdges(emailfield.getFont().getHeight()*2,5,5,5));
		
		LabelField password = new LabelField("Password: ");
		password.setPadding(new XYEdges(3,3,3,3));
		password.setMargin(new XYEdges(20,5,5,5));
		password.setFont(password.getFont().derive(Font.PLAIN, 24));
		
		passwordfield = new PasswordEditField();
		passwordfield.setPadding(padding);
		passwordfield.setBorder(border);
		passwordfield.setMargin(new XYEdges(0,5,0,5));
		
		add(email);
		add(emailfield);
		add(password);
		add(passwordfield);
		
		ButtonField login = new ButtonField("Log in");
		login.setPadding(new XYEdges(5,5,5,5));
		login.setMargin(new XYEdges(0,5,0,3));
		login.setFont(login.getFont().derive(Font.BOLD));
		
		ButtonField clear = new ButtonField("Clear");
		clear.setPadding(new XYEdges(5,5,5,5));
		clear.setFont(clear.getFont().derive(Font.BOLD));
		
		HorizontalFieldManager mana = new HorizontalFieldManager(Manager.FIELD_HCENTER);
		mana.add(login);
		mana.add(clear);
		
		mana.setPadding(new XYEdges(8,8,8,8));
		mana.setMargin(new XYEdges(8,0,0,0));
		add(mana);
		
		
	}
	
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}
}
