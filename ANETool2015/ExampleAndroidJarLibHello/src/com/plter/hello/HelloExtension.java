package com.plter.hello;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/**
 * Created by plter on 11/20/15.
 */
public class HelloExtension implements FREExtension {
    @Override
    public void initialize() {

    }

    @Override
    public FREContext createContext(String s) {
        return new HelloContext();
    }

    @Override
    public void dispose() {

    }
}
