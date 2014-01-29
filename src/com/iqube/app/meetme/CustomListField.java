package com.iqube.app.meetme;

import java.util.Hashtable;
import java.util.Vector;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;

public class CustomListField extends ListField implements ListFieldCallback {
	private Vector rows;
	private Vector contents;
	private UiApplication application;
	
	public CustomListField(UiApplication application, Vector c) {
		super(0, ListField.USE_ALL_WIDTH);
		
		this.contents = c;
		this.application = application;
		
		int fontHeight = Font.getDefault().getHeight();
		
		setRowHeight(fontHeight*8);
		setCallback(this);
		setEmptyString("No Events", DrawStyle.HCENTER);
		
		rows = new Vector();
		
		for (int i = 0; i < contents.size(); i++) {
			Hashtable current = (Hashtable)contents.elementAt(i);
			RowManager manager = new RowManager();
			
			LabelField eventName = new LabelField((String)current.get("event_name"));
			eventName.setPadding(new XYEdges(5,5,5,5));
			
			LabelField eventLocation = new LabelField((String)current.get("event_location"));
			eventLocation.setPadding(new XYEdges(5,5,5,5));
			
			LabelField eventDistance = new LabelField((String)current.get("distance"));
			eventDistance.setPadding(new XYEdges(5,5,5,5));
			
			
			eventName.setFont(eventName.getFont().derive(Font.BOLD,24));
			eventLocation.setFont(eventLocation.getFont().derive(Font.PLAIN,20));
			eventDistance.setFont(eventDistance.getFont().derive(Font.PLAIN, 19));
			
			
			manager.add(eventName);
			manager.add(eventLocation);
			manager.add(eventDistance);
			
			
			rows.addElement(manager);
			setSize(rows.size());
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
	
	private class RowManager extends Manager {
		public RowManager() {
			super(0);
		}
		
		public void drawRow(Graphics g, int x, int y, int width, int height, int ind) {
			layout(width, height);
			
			setPosition(x, y);
			
			g.pushRegion(getExtent());
			subpaint(g);
			
			g.setColor(0x00CACACA);
            g.drawLine(0, 0, getPreferredWidth(), 0);

            // Restore the graphics context.
            g.popContext();
		}
		
		protected void sublayout(int width, int height) {
			// TODO Auto-generated method stub
			int fontHeight = Font.getDefault().getHeight();
			int preferredWidth = getPreferredWidth();
			
			Field field = getField(0);
			layoutChild(field, preferredWidth, fontHeight*4);
			setPositionChild(field, 5, fontHeight);
			
			field = getField(1);
			layoutChild(field, preferredWidth, fontHeight*2);
			setPositionChild(field, 5, fontHeight*4);
			
			field = getField(2);
			layoutChild(field, preferredWidth, fontHeight*2);
			setPositionChild(field, 5, fontHeight*6);
			
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

	public void drawListRow(ListField listField, Graphics graphics, int index,
			int y, int width) {
		// TODO Auto-generated method stub
		CustomListField field = (CustomListField)listField;
		RowManager row = (RowManager)field.rows.elementAt(index);
		row.drawRow(graphics, 0, y, width,getRowHeight(), index);
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
		Dialog.alert(""+index);
		return super.navigationClick(status, time);
	}

}
