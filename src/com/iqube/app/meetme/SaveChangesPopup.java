package com.iqube.app.meetme;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class SaveChangesPopup extends PopupScreen {
	private ButtonField saveChanges;
	private ButtonField discardChanges;
	private ButtonField cancel;
	private LabelField changes;
	
	static int res = 0;
	
	public SaveChangesPopup(final Screen sc) {
		super(new VerticalFieldManager(), PopupScreen.DEFAULT_CLOSE);
		setPadding(new XYEdges(15,15,15,15));
		
		changes = new LabelField("Changes made!", Field.FIELD_HCENTER);
		changes.setPadding(new XYEdges(8,8,8,8));
		changes.setMargin(new XYEdges(5,0,5,0));
		
		saveChanges = new ButtonField("Save", Field.FIELD_HCENTER);
		saveChanges.setPadding(new XYEdges(4,4,4,4));
		saveChanges.setMargin(new XYEdges(0,0,8,0));
		saveChanges.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				
				//call method to save all changes made here
			}
			
		});
		
		discardChanges = new ButtonField("Discard", Field.FIELD_HCENTER);
		discardChanges.setPadding(new XYEdges(4,4,4,4));
		discardChanges.setMargin(new XYEdges(0,0,8,0));
		discardChanges.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				res = 1;
				UiApplication app = UiApplication.getUiApplication();
				app.popScreen(app.getActiveScreen());
			}
			
		});
		
		cancel = new ButtonField("Cancel", Field.FIELD_HCENTER);
		cancel.setPadding(new XYEdges(4,4,4,4));
		cancel.setMargin(new XYEdges(0,0,8,0));
		cancel.setChangeListener(new FieldChangeListener() {

			public void fieldChanged(Field field, int context) {
				// TODO Auto-generated method stub
				UiApplication.getUiApplication().popScreen(UiApplication.getUiApplication().getActiveScreen());
			}
			
		});
		
		add(changes);
		add(saveChanges);
		add(discardChanges);
		add(cancel);
	}
}
