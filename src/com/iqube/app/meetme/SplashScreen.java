package com.iqube.app.meetme;

import com.blackberry.facebook.ApplicationSettings;
import com.blackberry.facebook.Facebook;
import com.blackberry.facebook.FacebookException;
import com.blackberry.facebook.inf.User;

import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.container.MainScreen;

/**
 * A class extending the MainScreen class, which provides default standard
 * behavior for BlackBerry GUI applications.
 */
public final class SplashScreen extends MainScreen
{
    /**
     * Creates a new SplashScreen object
     */
	private String firstname, lastname, email, website, religion, name, gender;
	
    public SplashScreen()
    {        
        // Set the displayed title of the screen       
        setTitle("Splash Screen");
        
        UiApplication.getUiApplication().invokeLater(new Runnable() {
        	public void run() {
        		 String NextUrl = "http://www.facebook.com/connect/login_success.html";
        	     String appid = "497708920313527";
        	     String appsecret = "9b5f5dd3731a7a67716e25cf79419a69";
        	     String permissions[] = Facebook.Permissions.USER_DATA_PERMISSIONS;
        	     
        	     ApplicationSettings as = new ApplicationSettings(NextUrl,appid,appsecret,permissions);
        	        
        	     Facebook fb = Facebook.getInstance(as);
        	     
        	     try {
        	        	User user = fb.getCurrentUser();
        	        	firstname = user.getFirstName();
        	        	lastname = user.getLastName();
        	        	email = user.getEmail();
        	        	website = user.getWebsite();
        	        	religion = user.getReligion();
        	        	name = user.getName();
        	        	gender = user.getGender();
        	        	//user.get
        	        	
        	        	EditField field = new EditField();
        	            String text = "firstname: " + firstname + "\n";
        	            text += "lastname: " + lastname + "\n";
        	            text += "email: " + email + "\n";
        	            
        	            field.setText(text + "\ntoString: " + user.toString());
        	            
        	            add(field);
        	        	
        	        	//System.out.println(user);
        	      } catch (FacebookException fe) {
        	    	  System.out.println(fe.getMessage());
        	      } catch (Exception e) {
        	    	  System.out.println(e.toString());
        	      }

        	}
        }); 
        
        
    }
}
