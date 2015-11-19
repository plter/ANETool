/**
 * Created by plter on 11/19/15.
 */
package com.plter.hello {
import flash.external.ExtensionContext;

public class HelloNative {

    private static const NATIVE_EXTENSION_ID:String = "com.plter.hello";
    private var ec:ExtensionContext;

    public function HelloNative() {
        ec = ExtensionContext.createExtensionContext(NATIVE_EXTENSION_ID,null);
    }

    public function isAvilable():Boolean{
        return ec!=null;
    }

    public function sayHello():void{
        ec.call("hello");
    }

    public function dispose():void{
        ec.dispose();
    }
}
}
