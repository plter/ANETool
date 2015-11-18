package com.plter.hi
{
	import flash.events.EventDispatcher;
	import flash.external.ExtensionContext;

	public class Hi extends EventDispatcher
	{
		
		
		private var ex:ExtensionContext=null;
		
		public function Hi()
		{
			ex = ExtensionContext.createExtensionContext("com.plter.hi",null);
		}
		
		
		public function sayHi():String{
			return ex.call("sayHi") as String;
		}
		
		
		public function dispose():void{
			if (ex!=null) 
			{
				ex.dispose();
			}
		}
	}
}