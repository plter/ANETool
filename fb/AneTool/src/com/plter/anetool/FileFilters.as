package com.plter.anetool
{
	import flash.net.FileFilter;

	public class FileFilters
	{
		public static const ALL_FILE_FILTERS:Array=[new FileFilter("所有文件","*.*")];
		public static const SWC_FILE_FILTERS:Array=[new FileFilter("*.swc","*.swc")];
		public static const ANDROID_LIB_FILE_FILTERS:Array=[new FileFilter("*.jar;*.so","*.jar;*.so")];
		public static const DLL_FILE_FILTERS:Array=[new FileFilter("*.dll","*.dll")];
		public static const A_FILE_FILTERS:Array=[new FileFilter("*.a","*.a")];
		public static const P12_FILE_FILTERS:Array=[new FileFilter("*.p12","*.p12")];
		public static const XML_FILE_FILTERS:Array=[new FileFilter("*.xml","*.xml")];
		public static const ATCONF_FILE_FILTERS:Array=[new FileFilter("*.atconf","*.atconf")];
	}
}