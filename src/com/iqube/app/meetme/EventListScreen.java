package com.iqube.app.meetme;
import java.util.Vector;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;


public class EventListScreen extends MainScreen {
	public EventListScreen(String json) {
		super(Manager.USE_ALL_HEIGHT|Manager.USE_ALL_WIDTH);//|Manager.VERTICAL_SCROLL|Manager.VERTICAL_SCROLLBAR|Manager.HORIZONTAL_SCROLL|Manager.HORIZONTAL_SCROLLBAR);
		
		HorizontalFieldManager hman = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		hman.setBackground(BackgroundFactory.createLinearGradientBackground(Color.LIGHTSKYBLUE, Color.BLUE, Color.LIGHTBLUE, Color.BLUE));
		LabelField appTit = new LabelField("Events List");
		appTit.setFont(appTit.getFont().derive(Font.BOLD));
		hman.add(appTit);
		hman.setPadding(new XYEdges(7,7,7,7));
		setTitle(hman);
		
		Vector result = MyJsonParser.eventsJson(json);
		
		CustomListField listField = new CustomListField(UiApplication.getUiApplication(), result);
		
		add(listField);
		
		SeparatorField sepField = new SeparatorField(SeparatorField.USE_ALL_WIDTH);
		sepField.setBorder(BorderFactory.createSimpleBorder(new XYEdges(6,6,6,6), new XYEdges(Color.BLUE,Color.BLUE,Color.BLUE,Color.BLUE), Border.STYLE_FILLED));
		
		add(sepField);
		
		HorizontalFieldManager buttonManager = new HorizontalFieldManager(Manager.FIELD_HCENTER);
		buttonManager.setBorder(BorderFactory.createRoundedBorder(new XYEdges(8,8,8,8), Color.BLUE, Border.STYLE_SOLID));
		buttonManager.setMargin(new XYEdges(10,0,10,0));
		buttonManager.setPadding(new XYEdges(7,7,7,7));
		
		ButtonField home = new ButtonField("Home");
		home.setPadding(new XYEdges(5,5,5,5));
		home.setMargin(new XYEdges(0,10,0,0));
		
		home.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				UiApplication.getUiApplication().pushScreen(new MenuScreen());
			}
			
		});
		
		ButtonField loadmore = new ButtonField("More");
		loadmore.setPadding(new XYEdges(5,5,5,5));
		
		
		buttonManager.add(home);
		buttonManager.add(loadmore);
		
		add(buttonManager);
	}
	
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}
}
