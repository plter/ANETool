package com.plter.anetool.ui
{
	import flash.events.MouseEvent;
	
	import spark.components.TextInput;
	
	public class PTextInput extends TextInput
	{
		public function PTextInput()
		{
			super();
			
			addEventListener(MouseEvent.MOUSE_DOWN,mouseDownHandler);
		}
		
		protected function mouseDownHandler(event:MouseEvent):void
		{
			event.stopPropagation();			
		}
	}
}