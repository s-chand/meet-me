 package com.iqube.app.meetme;

import javax.microedition.location.Criteria;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

import net.rim.device.api.gps.BlackBerryCriteria;
import net.rim.device.api.gps.BlackBerryLocation;
import net.rim.device.api.gps.BlackBerryLocationProvider;
import net.rim.device.api.gps.GPSInfo;
import net.rim.device.api.gps.LocationInfo;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;
import net.rim.device.api.util.StringProvider;

public class BusinessCardScreen extends MainScreen {
	private StoreManager store = new StoreManager();
	
	private BlackBerryCriteria blackberryCriteria = null;
	private BlackBerryLocation blackberryLocation = null;
	private BlackBerryLocationProvider blackBerryLocationProvider = null;
	
	private EditField lname, fname, title, houseaddress, telephone, email, facebookname, twitterhandle, skypeid;
	
	public BusinessCardScreen() {
		super(Manager.USE_ALL_HEIGHT|Manager.USE_ALL_WIDTH);//|Manager.VERTICAL_SCROLL|Manager.VERTICAL_SCROLLBAR|Manager.HORIZONTAL_SCROLL|Manager.HORIZONTAL_SCROLLBAR);
		
		HorizontalFieldManager hman = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		hman.setBackground(BackgroundFactory.createLinearGradientBackground(Color.LIGHTSKYBLUE, Color.BLUE, Color.LIGHTBLUE, Color.BLUE));
		LabelField appTit = new LabelField("Business Card");
		appTit.setFont(appTit.getFont().derive(Font.BOLD));
		hman.add(appTit);
		hman.setPadding(new XYEdges(7,7,7,7));
		setTitle(hman);
		
		//Ui.getUiEngineInstance().setAcceptableDirections(Display.DIRECTION_LANDSCAPE);
		
		//getMainManager().setBackground(BackgroundFactory.createBitmapBackground(Bitmap.getBitmapResource("buscard_bg.png")));
		
		VerticalFieldManager manager = new VerticalFieldManager(Manager.USE_ALL_WIDTH|Manager.VERTICAL_SCROLL|Manager.VERTICAL_SCROLLBAR);
		
		
		Border border = BorderFactory.createRoundedBorder(new XYEdges(2,2,2,2),Color.LIGHTSTEELBLUE, Border.STYLE_SOLID);
		XYEdges padding = new XYEdges(8,8,8,8);
		XYEdges margin = new XYEdges(10,5,0,5);
		
		fname = new EditField("Firstname: ", (String)store.get("firstname"));
		fname.setBorder(border);
		fname.setMargin(margin);
		fname.setPadding(padding);
		
		lname = new EditField("Lastname: ", (String)store.get("lastname"));
		lname.setBorder(border);
		lname.setPadding(padding);
		lname.setMargin(margin);
		
		/**store.set("title", _title);
		store.set("houseaddress", _houseaddress);
		store.set("telephone", _tel);
		store.set("email", _email);
		store.set("fbname", _fbname);
		store.set("twitter", _handle);
		store.set("skype", _skype);**/
		
		title = new EditField("Title: ", (String)store.get("title"));
		title.setBorder(border);
		title.setMargin(margin);
		title.setPadding(padding);
		
		houseaddress = new EditField(EditField.FILTER_DEFAULT);
		houseaddress.setLabel("House Address: ");
		houseaddress.setPadding(padding);
		houseaddress.setMargin(margin);
		houseaddress.setBorder(border);
		houseaddress.setText((String)store.get("houseaddress"));
		
		telephone = new EditField(EditField.FILTER_PHONE);
		telephone.setLabel("Telephone:  ");
		telephone.setPadding(padding);
		telephone.setMargin(margin);
		telephone.setBorder(border);
		telephone.setText((String)store.get("telephone"));
		
		email = new EditField(EditField.FILTER_EMAIL);
		email.setLabel("Email: ");
		email.setText( (String)store.get("email"));
		email.setBorder(border);
		email.setPadding(padding);
		email.setMargin(margin);
		
		facebookname = new EditField(EditField.FILTER_DEFAULT);
		facebookname.setLabel("Facebook name: ");
		facebookname.setText((String)store.get("fullname"));
		facebookname.setPadding(padding);
		facebookname.setMargin(margin);
		facebookname.setBorder(border);
		
		twitterhandle = new EditField(EditField.FILTER_DEFAULT);
		twitterhandle.setLabel("Twitter handle: ");
		twitterhandle.setPadding(padding);
		twitterhandle.setMargin(margin);
		twitterhandle.setBorder(border);
		twitterhandle.setText((String)store.get("twitter"));
		
		skypeid = new EditField(EditField.FILTER_DEFAULT);
		skypeid.setLabel("Skype Id: ");
		skypeid.setBorder(border);
		skypeid.setMargin(margin);
		skypeid.setPadding(padding);
		skypeid.setText((String)store.get("skype"));
		
		
		
		
		manager.add(fname);
		manager.add(lname);
		manager.add(title);
		manager.add(houseaddress);
		manager.add(email);
		manager.add(facebookname);
		manager.add(twitterhandle);
		manager.add(skypeid);
		
		//manager.add(new ButtonField("Button 1"));
		//manager.add(new ButtonField("Button 2"));
		
		add(manager);
	}
	
	protected void makeMenu(Menu menu, int instance) {
		// TODO Auto-generated method stub
		super.makeMenu(menu, instance);
		//menu.add(new NearByEvents());
		//menu.add(new AddEvents());
		menu.add(new LogOut());
	}
	
	class  LogOut extends MenuItem {
		public LogOut() {
			super (new StringProvider("Logout"),30,30);
		}
		
		public void run() {
			StoreManager manager = new StoreManager();
			manager.set("is_registered", "false");
			manager.commit();
			
			System.out.println("Complete");
		}
	}
	
	class NearByEvents extends MenuItem {
		public NearByEvents() {
			super(new StringProvider("Nearby Events"), 10, 10);
		}
		
		private void showError(final Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					Dialog.alert(e.toString());
				}
				
			});
		}
		
		public void run() {
			/**new Thread() {
				public void run() {
					blackberryCriteria = new BlackBerryCriteria();
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
						blackBerryLocationProvider = (BlackBerryLocationProvider)BlackBerryLocationProvider.getInstance(blackberryCriteria);
						blackberryLocation = (BlackBerryLocation)blackBerryLocationProvider.getLocation(80);
						
						QualifiedCoordinates qc = blackberryLocation.getQualifiedCoordinates();
						
						final double lat = qc.getLatitude();
						final double lon = qc.getLongitude();**/
						
						final String simulatedJSONResult = "{\"200\": [{\"event_name\":\"MWWA\",\"distance\":\"19.2648025821982\",\"event_id\":\"4\",\"event_location\":\"4point\"},  {\"event_name\":\"Nigerian Students App Challenge\",\"distance\":\"17.1478054821982\",\"event_id\":\"5\",\"event_location\":\"Civic Center\"}, {\"event_name\":\"The Playlet\",\"distance\":\"19.489515214552151\",\"event_id\":\"6\",\"event_location\":\"National Arts Theatre\"},  {\"event_name\":\"IQube TTX\",\"distance\":\"23.2822451821982\",\"event_id\":\"7\",\"event_location\":\"UNILAG\"}, {\"event_name\":\"GNigeria\",\"distance\":\"74.9875425845982\",\"event_id\":\"8\",\"event_location\":\"4point\"}, {\"event_name\":\"Mobile Monday\",\"distance\":\"39.2648023422982\",\"event_id\":\"9\",\"event_location\":\"Victoria Island\"}, {\"event_name\":\"Developers Fest\",\"distance\":\"75.2648025452697\",\"event_id\":\"10\",\"event_location\":\"CCHUB\"}    ]}";
								
						
						UiApplication.getUiApplication().invokeLater(new Runnable() {
							public void run() {
								//Dialog.alert("Lat: " + lat + "\nLong: " + lon);
								
								UiApplication.getUiApplication().pushScreen(new EventListScreen(simulatedJSONResult));
							}
						});
					/***} catch (LocationException le) {
						showError(le);
					} catch (InterruptedException ie) {
						showError(ie);
					}
				}
			}.start();**/
			System.out.println("Fetching the location on the background");
			
			//Dialog.alert("Here i will use the location data to fecth events close to the user's location");
		}
	}
	
	protected boolean onSave() {
		// TODO Auto-generated method stub
		if (isDirty()) {
			String _fname = fname.getText();
			String _lname = lname.getText();
			String _title = title.getText();
			String _houseaddress = houseaddress.getText();
			String _tel = telephone.getText();
			String _email = email.getText();
			String _fbname = facebookname.getText();
			String _handle = twitterhandle.getText();
			String _skype = skypeid.getText();
			
			store.set("firstname", _fname);
			store.set("lastname", _lname);
			store.set("title", _title);
			store.set("houseaddress", _houseaddress);
			store.set("telephone", _tel);
			store.set("email", _email);
			store.set("fbname", _fbname);
			store.set("twitter", _handle);
			store.set("skype", _skype);
			
			store.commit();
		}
		return super.onSave();
	}
	
}
