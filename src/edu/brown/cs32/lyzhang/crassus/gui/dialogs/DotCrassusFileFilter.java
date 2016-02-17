package edu.brown.cs32.lyzhang.crassus.gui.dialogs;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class DotCrassusFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		
		if("crassus".equals(ExtensionUtils.getExtension(f.getName())))
			return true;
		
		return false;
	}

	@Override
	public String getDescription() {
		return ".crassus files";
	}

}
