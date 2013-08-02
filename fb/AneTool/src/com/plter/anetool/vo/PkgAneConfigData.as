package com.plter.anetool.vo
{

	public class PkgAneConfigData
	{
		
		public function PkgAneConfigData(){
			
		}
		
		public var swcFilePath:String=null;
		public var javaFilePath:String=null;
		public var flexOrAirSdkFilePath:String=null;
		public var adtFilePath:String=null;
		public var certFilePath:String=null;
		public var certPassword:String=null;
		public var useTimeStap:Boolean=true;
		public var aneExportPath:String=null;
		public var aneAirVersion:String=null;
		public var aneName:String=null;
		public var aneDesc:String=null;
		public var aneCopyright:String=null;
		public var aneDescFileContent:String=null;
		public var aneId:String=null;
		public var aneVersion:String=null;
		
		public var selectAndroidPlatform:Boolean=false;
		public var androidExtNativeLibFilePath:String=null;
		public var androidExtInitializer:String=null;
		public var androidExtFinalizer:String=null;
		public var androidExtSelectAttachments:Boolean=false;
		public var androidExtAttachments:String=null;
		public var androidExtSelectDepends:Boolean=false;
		public var androidExtDepends:String=null;//Android Java 依赖项，依赖项均为Java格式的jar文件，不能为dex格式
		
		public var selectiOSPlatform:Boolean=false;
		public var iOSExtUseRealDevice:Boolean=true;
		public var iOSExtNativeLibFilePath:String=null;
		public var iOSExtInitializer:String=null;
		public var iOSExtFinalizer:String=null;
		public var iOSExtSelectAttachments:Boolean=false;
		public var iOSExtAttachments:String=null;
		public var iOSExtSelectPlatformOptionsFile:Boolean=false;
		public var iOSExtPlatformOptionsFilePath:String=null;
		
		public var selectWindowsPlatform:Boolean=false;
		public var windowsExtNativeLibFilePath:String=null;
		public var windowsExtInitializer:String=null;
		public var windowsExtFinalizer:String=null;
		public var windowsExtSelectAttachments:Boolean=false;
		public var windowsExtAttachments:String=null;
		
		public var selectMacPlatform:Boolean=false;
		public var macExtNativeLibFilePath:String=null;
		public var macExtInitializer:String=null;
		public var macExtFinalizer:String=null;
		public var macExtSelectAttachments:Boolean=false;
		public var macExtAttachments:String=null;
		
	}
}