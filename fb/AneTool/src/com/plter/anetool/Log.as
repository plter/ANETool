package com.plter.anetool
{
	import spark.components.TextArea;

	public class Log
	{
		public static function info(msg:String):void
		{
			if (outTextArea==null) 
			{
				return;
			}
			
			outTextArea.appendText("信息："+msg+"\n");
		}
		
		public static function warn(msg:String):void{
			if (outTextArea==null) 
			{
				return;
			}
			
			outTextArea.appendText("警告："+msg+"\n");
		}
		
		public static function error(msg:String):void{
			if (outTextArea==null) 
			{
				return;
			}
			
			outTextArea.appendText("错误："+msg+"\n");
		}
		
		
		private static var _outTextArea:TextArea=null;

		public static function get outTextArea():TextArea
		{
			return _outTextArea;
		}

		public static function set outTextArea(value:TextArea):void
		{
			_outTextArea = value;
		}

	}
}