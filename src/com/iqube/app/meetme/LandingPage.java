package com.iqube.app.meetme;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.blackberry.facebook.ApplicationSettings;
import com.blackberry.facebook.Facebook;
import com.blackberry.facebook.FacebookException;
import com.blackberry.facebook.inf.User;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class LandingPage extends MainScreen {
	private StoreManager storemanager = new StoreManager();
	
	public LandingPage() {
		super(USE_ALL_WIDTH|USE_ALL_HEIGHT|NO_VERTICAL_SCROLL|NO_HORIZONTAL_SCROLL);
		
		VerticalFieldManager manager = new VerticalFieldManager(Manager.USE_ALL_WIDTH|Manager.USE_ALL_HEIGHT|Manager.NO_VERTICAL_SCROLL|Manager.FIELD_VCENTER|Manager.FIELD_HCENTER) {
			protected void sublayout(int maxWidth, int maxHeight) {
				// TODO Auto-generated method stub
				super.sublayout(Display.getWidth(), Display.getHeight());
				setExtent(getWidth(), getHeight());
			}
		};
		
		XYEdges padding = new XYEdges(8,8,8,8);
		
		
		ButtonField signup = new ButtonField("SIGN UP WITH EMAIL ",ButtonField.CONSUME_CLICK|Field.FIELD_HCENTER);
		signup.setPadding(padding);
		
		ButtonField facebooklogin = new ButtonField("SIGN IN WITH FACEBOOK", ButtonField.CONSUME_CLICK|Field.FIELD_HCENTER);
		facebooklogin.setPadding(padding);
		
		ButtonField login = new ButtonField("Login", ButtonField.CONSUME_CLICK|Field.FIELD_HCENTER);
		login.setPadding(padding);
		
		int totalHeight = login.getHeight() + facebooklogin.getHeight()+ signup.getHeight();
		totalHeight += 48+60;
		
		int topmargin = Display.getHeight()/2 - totalHeight;
		
		XYEdges margin = new XYEdges(topmargin,0,20,0);
		
		facebooklogin.setMargin(margin);
		signup.setMargin(new XYEdges(0,0,20,0));
		login.setMargin(new XYEdges(0,0,20,0));
		
		signup.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		facebooklogin.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						String NextUrl = "http://www.facebook.com/connect/login_success.html;deviceSide=true";
		        	    String appid = "497708920313527";
		        	    String appsecret = "9b5f5dd3731a7a67716e25cf79419a69";
		        	    String permissions[] = Facebook.Permissions.USER_DATA_PERMISSIONS;
		        	    
		        	    ApplicationSettings as = new ApplicationSettings(NextUrl,appid,appsecret,permissions);
	        	        
		        	    Facebook fb = Facebook.getInstance(as);
		        	    
		        	    try {
	        	        	User user = fb.getCurrentUser();
	        	        	final String firstname = user.getFirstName();
	        	        	final String lastname = user.getLastName();
	        	        	final String email = user.getEmail();
	        	        	final String name = user.getName();
	        	        	final String gender = user.getGender();
	        	        	final String id = user.getId();
	        	        	
	        	        	storemanager.set("is_registered", "true");
	        	        	storemanager.set("fullname", name);
	        	        	storemanager.set("firstname", firstname);
	        	        	storemanager.set("lastname", lastname);
	        	        	storemanager.set("email", email);
	        	        	storemanager.set("id", id);
	        	        	storemanager.commit();
	        	        	
	        	        	//this thread sends the user's location to the server
	        	        	Thread t = new Thread() {
	        	        		public void run() {
	        	        			String url = "http://p2mu.net/meetme/meetme.pl?action=register&";
	        	        			url += "name="+ URLUTF8Encoder.encode(firstname + " " + lastname);
	        	        			url += "&email=" +email ;
	        	        			url += "&fbkid=" + id;
	        	        			url += "&type=facebook";
	        	        			
	        	        			if (DeviceInfo.isSimulator())
	        	        				url += ";deviceSide=true";
	        	        			else
	        	        				url += Utilities.getConnectionString();
	        	        			
	        	        			try {
	        	        				HttpConnection conn = (HttpConnection)Connector.open(url);
	        	        				InputStream is = conn.openInputStream();
	        	        				ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        	        				
	        	        				byte buffer[] = new byte[1024];
	        	        				int bytesRead = is.read(buffer);
	        	        				
	        	        				while (bytesRead > 0) {
	        	        					baos.write(buffer, 0, bytesRead);
	        	                    		bytesRead = is.read(buffer);
	        	        				}
	        	        				
	        	        				baos.close();
	        	        				
	        	        				String res = new String(baos.toByteArray());
	        	        				
	        	        				System.out.println("The result is: " + res);
	        	        			} catch (IOException ioe) {
	        	        				System.out.println("Error occured while registering: " + ioe.toString());
	        	        			}
	        	        			
	        	        		}
	        	        	};
	        	        	
	        	        	t.start();
	        	        	
	        	        	//users location will be fetched and nearby events will also be fetched
	        	        	
	        	        	final String simulatedJSONResult = "{\"200\": [{\"event_name\":\"MWWA\",\"distance\":\"19.2648025821982\",\"event_id\":\"4\",\"event_location\":\"4point\"},  {\"event_name\":\"Nigerian Students App Challenge\",\"distance\":\"17.1478054821982\",\"event_id\":\"5\",\"event_location\":\"Civic Center\"}, {\"event_name\":\"The Playlet\",\"distance\":\"19.489515214552151\",\"event_id\":\"6\",\"event_location\":\"National Arts Theatre\"},  {\"event_name\":\"IQube TTX\",\"distance\":\"23.2822451821982\",\"event_id\":\"7\",\"event_location\":\"UNILAG\"}, {\"event_name\":\"GNigeria\",\"distance\":\"74.9875425845982\",\"event_id\":\"8\",\"event_location\":\"4point\"}, {\"event_name\":\"Mobile Monday\",\"distance\":\"39.2648023422982\",\"event_id\":\"9\",\"event_location\":\"Victoria Island\"}, {\"event_name\":\"Developers Fest\",\"distance\":\"75.2648025452697\",\"event_id\":\"10\",\"event_location\":\"CCHUB\"}    ]}";
	        	        	storemanager.set("eventlist", simulatedJSONResult);
	        	        	storemanager.commit();
	        	        	
	        	        	UiApplication.getUiApplication().popScreen(LandingPage.this);
	        	        	UiApplication.getUiApplication().pushScreen(new EventListScreen(simulatedJSONResult));
	        	        	//UiApplication.getUiApplication().pushScreen(new BusinessCardScreen());
	        	        	
	        	      } catch (FacebookException fe) {
	        	    	  System.out.println(fe.getMessage());
	        	      } catch (Exception e) {
	        	    	  System.out.println(e.toString());
	        	      }
					}
				});
			}
			
		});
		
		login.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				UiApplication.getUiApplication().pushScreen(new LoginScreen());
			}
			
		});
		
		manager.add(facebooklogin);
		manager.add(signup);
		manager.add(login);
		
		
		manager.setBackground(BackgroundFactory.createSolidBackground(0x3F8BBC));
		add(manager);
	}
	
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

}
