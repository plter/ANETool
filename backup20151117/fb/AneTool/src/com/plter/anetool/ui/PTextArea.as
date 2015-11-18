package com.plter.anetool.ui
{
	import flash.events.MouseEvent;
	
	import spark.components.TextArea;
	
	public class PTextArea extends TextArea
	{
		public function PTextArea()
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