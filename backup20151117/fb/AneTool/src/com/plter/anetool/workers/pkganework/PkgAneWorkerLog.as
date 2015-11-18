package com.plter.anetool.workers.pkganework
{
	import com.plter.anetool.vo.PkgAneProgressMessage;

	public class PkgAneWorkerLog
	{
		public function PkgAneWorkerLog()
		{
		}
		
		public static function createMessage(type:String,message:String):PkgAneProgressMessage{
			var m:PkgAneProgressMessage = new PkgAneProgressMessage;
			m.type = type;
			m.message = message;
			return m;
		}
		
		public static function publishErrorLog(log:String):void{
			if (PkgAneWorkerConfig.progressMessageChannel!=null) 
			{
				PkgAneWorkerConfig.progressMessageChannel.send(createMessage("error",log));
			}
		}
		
		public static function publishWarnLog(log:String):void{
			if (PkgAneWorkerConfig.progressMessageChannel!=null) 
			{
				PkgAneWorkerConfig.progressMessageChannel.send(createMessage("warn",log));
			}
		}
		
		public static function publishInfoLog(log:String):void{
			if (PkgAneWorkerConfig.progressMessageChannel!=null) 
			{
				PkgAneWorkerConfig.progressMessageChannel.send(createMessage("info",log));
			}
		}
	}
}