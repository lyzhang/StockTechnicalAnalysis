package edu.brown.cs32.lyzhang.crassus.gui.dialogs;

public class ExtensionUtils {

	static String getExtension(String s){

		String ext = "";
		
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        
        return ext;
	}
	
	static String setExtension(String string, String path) {
		int i = path.lastIndexOf('.');
		
        if (i > 0 &&  i < path.length() - 1) {
            return path.substring(0,i) + "." + string;
        }
        
        
        return path + "." + string;

	}
	
}
