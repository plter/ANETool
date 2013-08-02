package com.plter.hi;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class Hi implements FREExtension {
	
	
	private HiContext hiContext=null;

	@Override
	public FREContext createContext(String arg0) {
		if (hiContext==null) {
			hiContext = new HiContext();
		}
		return hiContext;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}
}
