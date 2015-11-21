package com.plter.hello;

import android.widget.Toast;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

/**
 * Created by plter on 11/20/15.
 */
public class HelloFunction implements FREFunction {
    @Override
    public FREObject call(FREContext freContext, FREObject[] freObjects) {
        Toast.makeText(freContext.getActivity(), "Hello Android", Toast.LENGTH_SHORT).show();
        return null;
    }
}
