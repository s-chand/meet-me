package com.iqube.app.meetme;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.HttpConnection;
import javax.microedition.location.Criteria;
import javax.microedition.location.LocationException;
import javax.microedition.location.QualifiedCoordinates;

import net.rim.device.api.gps.BlackBerryCriteria;
import net.rim.device.api.gps.BlackBerryLocation;
import net.rim.device.api.gps.BlackBerryLocationProvider;
import net.rim.device.api.gps.GPSInfo;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

/**
 * This class extends the UiApplication class, providing a
 * graphical user interface.
 */
public class MeetMe extends UiApplication
{
    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
	
	StoreManager manager = new StoreManager();
	double lon=-0.0, lat=-0.0;
	
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MeetMe theApp = new MeetMe();       
        theApp.enterEventDispatcher();
    }
    

    /**
     * Creates a new MeetMe object
     */
    public MeetMe()
    {      
    	
    	if (manager.get("is_registered") != null && ((String)manager.get("is_registered")).equals("true")) {
    		//Load the events list stored while getting the users location
    		//in background so as to fetch latest events list  based on the user's location
    		
    		//get the users location
    		final ProcessingPopup pop1 = new ProcessingPopup("Getting Location...");
			UiApplication.getUiApplication().pushScreen(pop1);
			
			new Thread() {
				public void run() {
					BlackBerryCriteria blackberryCriteria = new BlackBerryCriteria();
					if (GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_CELLSITE))
						blackberryCriteria.setMode(GPSInfo.GPS_MODE_CELLSITE);
					else if (GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_ASSIST))
						blackberryCriteria.setMode(GPSInfo.GPS_MODE_ASSIST);
					else if (GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_AUTONOMOUS))
						blackberryCriteria.setMode(GPSInfo.GPS_MODE_AUTONOMOUS);
					else {
						blackberryCriteria.setCostAllowed(true);
						blackberryCriteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);
					}
					
					try {
						BlackBerryLocationProvider blackBerryLocationProvider = (BlackBerryLocationProvider)BlackBerryLocationProvider.getInstance(blackberryCriteria);
						BlackBerryLocation blackberryLocation = (BlackBerryLocation)blackBerryLocationProvider.getLocation(80);
					
						QualifiedCoordinates qc = blackberryLocation.getQualifiedCoordinates();
					
						lat = qc.getLatitude();
						lon = qc.getLongitude();
					} catch (LocationException le) {
						System.out.println(le);
					} catch (InterruptedException ine) {
						System.out.println(ine);
					}
				}
			}.start();
			
			//return if the app was unable to fetch user's location
			if (lat == -0.0 && lon ==-0.0) {
				Dialog.alert("Cannot fetch location at this time");
				return;
			}
			
    		final StringBuffer url = new StringBuffer("http://p2mu.net/meetme/meetme.pl?action=location");
    		String userid = (String)manager.get("id"); //gets the userid from the storemanager
    		url.append("&uid="+userid);
    		url.append("&lat="+lat);
    		url.append("&long="+lon);
    		
    		new Thread() {
    			public void run() {
    				try {
    					
    					//display the popup screen to show that events are being fetched
    					getUiApplication().invokeAndWait(new Runnable() {
    						public void run() {
    							final ProcessingPopup pop = new ProcessingPopup("Fetching nearby events...");
    							getUiApplication().popScreen(pop1); //pops the first popupscreen that was added
    	    					getUiApplication().pushScreen(pop); //pushes the new popup screen
    						}
    					});
    					
    					
    					ConnectionFactory conFactory = new ConnectionFactory();
    					conFactory.setTimeLimit(15000);
					
    					HttpConnection c = (HttpConnection) conFactory.getConnection(url.toString()).getConnection();
    					InputStream is = c.openInputStream();
    					ByteArrayOutputStream baos = new ByteArrayOutputStream();
    					
    					byte buffer[] = new byte[1024];
        				int bytesRead = is.read(buffer);
        				
        				while (bytesRead > 0) {
        					baos.write(buffer, 0, bytesRead);
                    		bytesRead = is.read(buffer);
        				}
        				
        				baos.close();
        				
        				String res = new String(baos.toByteArray());
        				final String res2 = "{" + res + "}"; //converts the result gotten to a proper json by appending the opening and closing curly braces
        				
        				manager.set("eventlist", res2);
        	        	manager.commit();
        				
        				getUiApplication().invokeAndWait(new Runnable() {
        					public void run() {
        						getUiApplication().popScreen(getUiApplication().getActiveScreen());
        						getUiApplication().pushScreen(new EventListScreen(res2));
        					}
        				});
        				
    				} catch (IOException ioe) {
    					System.out.println(ioe.toString());
    				}
    			}
    		}.start();
    		
    		//String jsonevent = (String)manager.get("eventlist");
    		//pushScreen(new EventListScreen(jsonevent));
    		
    		//pushScreen(new BusinessCardScreen());
    	}
    	else  
    		pushScreen(new LandingPage());
    }    
}
