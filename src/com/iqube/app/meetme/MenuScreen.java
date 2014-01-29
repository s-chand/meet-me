package com.iqube.app.meetme;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class MenuScreen extends MainScreen {
	public MenuScreen() {
		super(Manager.USE_ALL_HEIGHT|Manager.USE_ALL_WIDTH);
		
		HorizontalFieldManager hman = new HorizontalFieldManager(Manager.USE_ALL_WIDTH);
		hman.setBackground(BackgroundFactory.createLinearGradientBackground(Color.LIGHTSKYBLUE, Color.BLUE, Color.LIGHTBLUE, Color.BLUE));
		LabelField appTit = new LabelField("Home Menu");
		appTit.setFont(appTit.getFont().derive(Font.BOLD));
		hman.add(appTit);
		hman.setPadding(new XYEdges(7,7,7,7));
		setTitle(hman);
		
		add(new MyCustomListField());
	}
	
	class MyCustomListField extends ListField implements ListFieldCallback {
		private Vector rows;
		Bitmap bitmap1, bitmap2, bitmap3, bitmap4;
		
		
		String array[] = {"business card", "events", "add events","request"}; 
		
		public MyCustomListField() {
			super(0,ListField.USE_ALL_HEIGHT|ListField.USE_ALL_WIDTH);
			
			int fontHeight = Font.getDefault().getHeight();
			
			setRowHeight(fontHeight*4);
			setCallback(this);
			
			rows = new Vector();
			
			bitmap1 = Bitmap.getBitmapResource("card_in_use.png");
			bitmap2 = Bitmap.getBitmapResource("events.png");
			bitmap3 = Bitmap.getBitmapResource("group_full_add.png");
			bitmap4 = Bitmap.getBitmapResource("user_add.png");
			
			for (int x = 0; x < 4; x++) {
				TableRowManager manager = new TableRowManager();
				
				LabelField label = new LabelField() {
					protected void paint(Graphics graphics) {
						// TODO Auto-generated method stub
						graphics.setColor(Color.BLACK);
					    super.paint(graphics);
					}
				};
				
				label.setPadding(new XYEdges(5,5,5,5));
				label.setMargin(new XYEdges(0,0,0,15));
				label.setFont(label.getFont().derive(Font.BOLD));
				
				switch (x) {
				case 0:
					manager.add(new BitmapField(bitmap1));
					break;
				case 1:
					manager.add(new BitmapField(bitmap2));
					break;
				case 2:
					manager.add(new BitmapField(bitmap3));
					break;
				case 3:
					manager.add(new BitmapField(bitmap4));
					break;
				}
				
				label.setText(array[x]);
				
				manager.add(label);
				rows.addElement(manager);
			}
			
			setSize(rows.size());
			
		}
		
		class TableRowManager extends Manager {
			public TableRowManager() {
				super(0);
			}
			
			public void drawRow(Graphics g, int x, int y, int width, int height) {
				layout(width, height);
				
				setPosition(x, y);
				g.pushRegion(getExtent());
				subpaint(g);
				
				g.setColor(0x00CACACA);
				g.drawLine(0, 0, getPreferredWidth(), 0);
				g.popContext();
			}
			
			protected void sublayout(int width, int height) {
				// TODO Auto-generated method stub
				int fontHeight = Font.getDefault().getHeight();
				int preferredWidth = getPreferredWidth();
				
				Field field = getField(0);
				layoutChild(field,48, 48);
				setPositionChild(field,10, 10);
				
				field = getField(1);
				layoutChild(field, preferredWidth-10, fontHeight*2);
				setPositionChild(field, 60, fontHeight);
				
				setExtent(preferredWidth, getPreferredHeight());
			}
			
			public int getPreferredHeight() {
				// TODO Auto-generated method stub
				return getRowHeight();
			}
			
			public int getPreferredWidth() {
				// TODO Auto-generated method stub
				return Display.getWidth();
			}
		}
		
		protected void drawFocus(Graphics graphics, boolean on) {
			// TODO Auto-generated method stub
			
			XYRect focusRect = new XYRect();
	        getFocusRect(focusRect);
	        
	        boolean oldDrawStyleFocus = graphics
	                .isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS);
	        
	        try {
	        	if (on) {
	        		graphics.setDrawingStyle(Graphics.DRAWSTYLE_FOCUS, true);
	        		int oldColour = graphics.getColor();
	        		try {
	        			graphics.setColor(Color.LIGHTGRAY);
	                    graphics.fillRect(focusRect.x, focusRect.y,
	                            focusRect.width, focusRect.height);
	        		} finally {
	        			graphics.setColor(oldColour);
	        		}
	        		
	        		//to redraw the rows after drawing focus. This simulates focus transparency
	    			drawListRow(this, graphics, getSelectedIndex(),
	    			focusRect.y, focusRect.width);
	        	}
	        } finally {
	        	graphics.setDrawingStyle(Graphics.DRAWSTYLE_FOCUS,
	                    oldDrawStyleFocus);
	        }
		}

		public void drawListRow(ListField listField, Graphics graphics,
				int index, int y, int width) {
			// TODO Auto-generated method stub
			MyCustomListField listfield = (MyCustomListField)listField;
			TableRowManager rowManager = (TableRowManager)listfield.rows.elementAt(index);
			rowManager.drawRow(graphics, 0, y, width, listfield.getRowHeight());
		}

		public Object get(ListField listField, int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public int getPreferredWidth(ListField listField) {
			// TODO Auto-generated method stub
			return 0;
		}

		public int indexOfList(ListField listField, String prefix, int start) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		protected boolean navigationClick(int status, int time) {
			// TODO Auto-generated method stub
			int index = getSelectedIndex();
			switch (index) {
			case 0:
				UiApplication.getUiApplication().pushScreen(new BusinessCardScreen());
				break;
			case 2:
				UiApplication.getUiApplication().pushScreen(new AddEvent());
				break;
			case 1:
				StoreManager smanager = new StoreManager();
				String json = (String)smanager.get("eventlist");
				UiApplication.getUiApplication().pushScreen(new EventListScreen(json));
				break;
			case 3:
				Dialog.alert("request");
				break;
			}
			return super.navigationClick(status, time);
		}
	}
}
