package com.plter.hello;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by plter on 11/20/15.
 */
public class HelloContext extends FREContext {

    private Map<String,FREFunction> funcs = new HashMap<String, FREFunction>();

    public HelloContext() {
        funcs.put("hello",new HelloFunction());
    }

    @Override
    public Map<String, FREFunction> getFunctions() {
        return funcs;
    }

    @Override
    public void dispose() {

    }
}
