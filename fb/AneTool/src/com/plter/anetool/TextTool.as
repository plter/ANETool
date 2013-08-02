package com.plter.anetool
{
	import spark.components.TextInput;

	public class TextTool
	{
		public static function isTextEmpty(ti:TextInput):Boolean
		{
			return ti.text==null||ti.text=="";
		}
		
		
		public static function isStringEmpty(s:String):Boolean{
			return s==null||s=="";
		}
	}
}