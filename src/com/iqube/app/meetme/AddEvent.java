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
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class AddEvent extends MainScreen {
	private EditField eventName, location, duration;
	private StoreManager stManager = new StoreManager();
	
	public AddEvent() {
		super(USE_ALL_WIDTH|USE_ALL_HEIGHT|Manager.VERTICAL_SCROLL|Manager.VERTICAL_SCROLLBAR);
		
		HorizontalFieldManager hman = new HorizontalFieldManager(Manager.USE_ALL_WIDTH|Manager.FIELD_HCENTER);
		hman.setBackground(BackgroundFactory.createLinearGradientBackground(Color.LIGHTSKYBLUE, Color.BLUE, Color.LIGHTBLUE, Color.BLUE));
		LabelField appTit = new LabelField("Add Event");
		appTit.setFont(appTit.getFont().derive(Font.BOLD));
		
		hman.add(appTit);
		hman.setPadding(new XYEdges(7,7,7,7));
		setTitle(hman);
		
		Border border1 = BorderFactory.createRoundedBorder(new XYEdges(2,2,2,2), Border.STYLE_SOLID);
		XYEdges padding = new XYEdges(5,5,5,5);
		
		XYEdges padding2 = new XYEdges(3,3,3,3);
		
		LabelField eventname = new LabelField("Event Name");
		eventname.setPadding(padding2);
		eventname.setMargin(new XYEdges(7,0,3,7));
		eventname.setFont(eventname.getFont().derive(Font.BOLD));
		add(eventname);
		
		eventName = new EditField(EditField.FILTER_DEFAULT);
		eventName.setBorder(border1);
		eventName.setPadding(padding);
		eventName.setMargin(new XYEdges(0,7,0,7));
		add(eventName);
		
		LabelField loc = new LabelField("Event Location");
		loc.setPadding(padding2);
		loc.setMargin(new XYEdges(7,0,3,7));
		loc.setFont(loc.getFont().derive(Font.BOLD));
		add(loc);
		
		location = new EditField(EditField.FILTER_DEFAULT);
		location.setBorder(border1);
		location.setPadding(padding);
		location.setMargin(new XYEdges(0,7,0,7));
		add(location);
		
		LabelField duratn = new LabelField("Event duration");
		duratn.setPadding(padding2);
		duratn.setMargin(new XYEdges(7,0,3,7));
		duratn.setFont(duratn.getFont().derive(Font.BOLD));
		add(duratn);
		
		duration = new EditField(EditField.FILTER_NUMERIC);
		duration.setBorder(border1);
		duration.setPadding(padding);
		duration.setMargin(new XYEdges(0,7,0,7));
		add(duration);
		
		
		HorizontalFieldManager hrm = new HorizontalFieldManager(Manager.FIELD_HCENTER);
		hrm.setPadding(new XYEdges(8,5,5,5));
		hrm.setMargin(new XYEdges(10,0,0,0));
		
		
		ButtonField addEvent = new ButtonField("Add Event");
		addEvent.setFont(addEvent.getFont().derive(Font.BOLD));
		
		addEvent.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				//String url = "http://p2mu.net/meetme/meetme.pl?action=add_event&uid=1&event_name=gnigeria&event_location=lagos&duration=2&lat=6.538&long=3.665";
				boolean check = false;
				
				if (eventName.getText().trim().equals("")) {
					check = true;
					eventName.setFocus();
				} else if (location.getText().trim().equals("")) {
					check = true;
					location.setFocus();
				} else if (duration.getText().trim().equals("")) {
					duration.setFocus();
					check = true;
				}
				
				if (check) {
					UiApplication.getUiApplication().invokeAndWait(new Runnable() {
						public void run() {
							Dialog.alert("Some fields are empty. Fill correctly and try again.");
						};
					});
					return;
				}
				
				//won't display getting location for now
				
				final ProcessingPopup pop = new ProcessingPopup("Getting Location...");
				UiApplication.getUiApplication().pushScreen(pop);
				
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
							
							final double lat = qc.getLatitude();
							final double lon = qc.getLongitude();
							
							UiApplication.getUiApplication().invokeAndWait(new Runnable() {
								public void run() {
									//ProcessingPopup pop = new ProcessingPopup("Adding Event...", getMainManager());
									//UiApplication.getUiApplication().pushScreen(pop);
									
									pop.text.setText("Adding Event...");
									
									//start a new thread to add the event
									
									new Thread() {
										public void run() {
											String eventname = eventName.getText();
											String locat = location.getText();
											String dur = duration.getText();
											String uid = (String)stManager.get("id");
											
											eventname = URLUTF8Encoder.encode(eventname);
											locat = URLUTF8Encoder.encode(locat);
											
											String url = "http://p2mu.net/meetme/meetme.pl?action=add_event&uid="+uid;
											url += "&event_name="+eventname+"&event_location=" + locat+ "&duration=" + dur;
											url += "&lat=" + lat +  "&long=" + lon;
											//lat=6.538&long=3.665
											url += Utilities.getConnectionString();
											
											try {
												ConnectionFactory conFactory = new ConnectionFactory();
												conFactory.setTimeLimit(15000);
												
												HttpConnection c = (HttpConnection) conFactory.getConnection(url).getConnection();
												InputStream is = c.openInputStream();
			        	        				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        	        				
			        	        				byte buffer[] = new byte[1024];
			        	        				int bytesRead = is.read(buffer);
			        	        				
			        	        				while (bytesRead > 0) {
			        	        					baos.write(buffer, 0, bytesRead);
			        	                    		bytesRead = is.read(buffer);
			        	        				}
			        	        				
			        	        				baos.close();
			        	        				
			        	        				final String res = new String(baos.toByteArray());
			        	        				
			        	        				UiApplication.getUiApplication().invokeLater(new Runnable() {
			        	        					public void run() {
			        	        						UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
			        	        						Dialog.alert("result is: " + res);
			        	        					}
			        	        				});
											} catch (IOException ioe) {
												System.out.println(ioe.toString());
											}
										}
									}.start();
								}
							});
						} catch (LocationException le) {
							System.out.println(le.toString());
						} catch (InterruptedException ie) {
							System.out.println(ie.toString());
						}
					}
				}.start();
			}
			
		});
		
		hrm.add(addEvent);
		
		add(hrm);
	}
	
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}
}
