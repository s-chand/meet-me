package com.iqube.app.meetme;

import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;

public class ProcessingPopup extends PopupScreen {
	public LabelField text;
	
	public ProcessingPopup(String s) {
		super(new VerticalFieldManager(), PopupScreen.DEFAULT_CLOSE);
		
		HorizontalFieldManager hmn = new HorizontalFieldManager();
		hmn.setBackground(BackgroundFactory.createSolidBackground(Color.WHITE));
		
		
		GIFEncodedImage yourImage =(GIFEncodedImage)GIFEncodedImage.getEncodedImageResource("loading3.gif");
		AnimatedGifField yourImageField =new AnimatedGifField(yourImage);
		
		
		text = new LabelField() {
			protected void paint(Graphics graphics) {
				// TODO Auto-generated method stub
				graphics.setColor(Color.BLACK);
				super.paint(graphics);
			}
		};
		text.setFont(text.getFont().derive(Font.BOLD));
		text.setText(s);
		text.setPadding(new XYEdges(5,5,5,5));
		text.setMargin(new XYEdges(0,5,0,0));
		hmn.add(text);
		
		hmn.add(yourImageField);
		
		add(hmn);
	}
	
	public ProcessingPopup(String s, Manager manager) {
		super(new VerticalFieldManager(), PopupScreen.DEFAULT_CLOSE);
		manager.setBackground(BackgroundFactory.createSolidBackground(Color.WHITE));
		
		HorizontalFieldManager hmn = new HorizontalFieldManager();
		hmn.setBackground(BackgroundFactory.createSolidBackground(Color.WHITE));
		
		
		GIFEncodedImage yourImage =(GIFEncodedImage)GIFEncodedImage.getEncodedImageResource("loading3.gif");
		AnimatedGifField yourImageField =new AnimatedGifField(yourImage);
		
		
		text = new LabelField() {
			protected void paint(Graphics graphics) {
				// TODO Auto-generated method stub
				graphics.setColor(Color.BLACK);
				super.paint(graphics);
			}
		};
		text.setFont(text.getFont().derive(Font.BOLD));
		text.setText(s);
		text.setPadding(new XYEdges(5,5,5,5));
		text.setMargin(new XYEdges(0,5,0,0));
		hmn.add(text);
		
		hmn.add(yourImageField);
		
		add(hmn);
	}
}
