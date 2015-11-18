package com.plter.hi;

import java.util.HashMap;
import java.util.Map;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class HiContext extends FREContext {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		
		if (functionsMap==null) {
			functionsMap = new HashMap<String, FREFunction>();
			functionsMap.put("sayHi", new SayHi());
		}
		
		return functionsMap;
	}
	
	private Map<String, FREFunction> functionsMap = null;

}
