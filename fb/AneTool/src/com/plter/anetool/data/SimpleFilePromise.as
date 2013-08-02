package com.plter.anetool.data
{
	import flash.desktop.IFilePromise;
	import flash.events.ErrorEvent;
	import flash.utils.ByteArray;
	import flash.utils.IDataInput;
	
	public class SimpleFilePromise implements IFilePromise
	{
		
		public function SimpleFilePromise(bytes:ByteArray)
		{
			this.bytes=bytes;
		}
		
		public function get relativePath():String
		{
			return "build_ane.atconf";
		}
		
		public function get isAsync():Boolean
		{
			return false;
		}
		
		public function open():IDataInput
		{
			return bytes;
		}
		
		public function close():void
		{
		}
		
		public function reportError(e:ErrorEvent):void
		{
		}
		
		private var bytes:ByteArray=null;
	}
}