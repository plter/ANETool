package com.plter.anetool.vo
{
	import flash.net.registerClassAlias;

	public class VOManager
	{
		public function VOManager()
		{
		}
		
		public static function regVO():void{
			registerClassAlias("com.plter.anetool.vo.PrePkgAneConfigData",PkgAneConfigData);
			registerClassAlias("com.plter.anetool.vo.PkgAneProgressMessage",PkgAneProgressMessage);
		}
	}
}