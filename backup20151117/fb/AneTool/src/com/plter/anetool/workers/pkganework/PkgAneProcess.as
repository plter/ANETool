package com.plter.anetool.workers.pkganework
{
	import com.plter.anetool.vo.PkgAneConfigData;
	
	import flash.desktop.NativeProcess;
	import flash.desktop.NativeProcessStartupInfo;
	import flash.events.NativeProcessExitEvent;
	import flash.events.ProgressEvent;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.utils.ByteArray;
	
	import nochump.util.zip.ZipEntry;
	import nochump.util.zip.ZipError;
	import nochump.util.zip.ZipFile;
	import nochump.util.zip.ZipOutput;
	
	public class PkgAneProcess extends NativeProcess
	{
		
		
		private static const PLATFORM_ANDROID_DIR_NAME:String="android";
		private static const PLATFORM_iOS_DIR_NAME:String="ios";
		private static const PLATFORM_WINDOWS_DIR_NAME:String="windows";
		private static const PLATFORM_MAC_DIR_NAME:String="mac";
		
		private var baseDir:File=null;
		
		public function PkgAneProcess()
		{
			super();
			
			addEventListener(NativeProcessExitEvent.EXIT,exitHandler);
			addEventListener(ProgressEvent.STANDARD_OUTPUT_DATA,stdOutHandler);
			addEventListener(ProgressEvent.STANDARD_ERROR_DATA,stdErrorHandler);
		}
		
		protected function stdOutHandler(event:ProgressEvent):void
		{
			PkgAneWorkerLog.publishInfoLog(standardError.readUTFBytes(standardError.bytesAvailable));
		}
		
		protected function stdErrorHandler(event:ProgressEvent):void
		{
			PkgAneWorkerLog.publishErrorLog(standardError.readUTFBytes(standardError.bytesAvailable));
		}
		
		protected function exitHandler(event:NativeProcessExitEvent):void
		{
			PkgAneWorkerLog.publishInfoLog("结果代码："+event.exitCode);
			
			switch(event.exitCode)
			{
				case 0:
					PkgAneWorkerLog.publishInfoLog("打包完成");
					break;
				case 100:
				{
					PkgAneWorkerLog.publishErrorLog("无法分析应用程序描述符");
					break;
				}
				case 101:
					PkgAneWorkerLog.publishErrorLog("缺少命名空间");
					break;
				case 102:
					PkgAneWorkerLog.publishErrorLog("命名空间无效");
					break;
				case 103:
					PkgAneWorkerLog.publishErrorLog("意外的元素或属性：\n1.删除引起错误的元素和属性。描述符文件中不允许使用自定义值。\n" +
						"2.检查元素和属性名称的拼写。\n" +
						"3.确保将元素放置在正确的父元素内，且使用属性时对应着正确的元素。");
					break;
				case 104:
					PkgAneWorkerLog.publishErrorLog("缺少元素或属性");
					break;
				case 105:
					PkgAneWorkerLog.publishErrorLog("元素或属性所含的某个值无效");
					break;
				case 106:
					PkgAneWorkerLog.publishErrorLog("窗口属性组合非法");
					break;
				case 107:
					PkgAneWorkerLog.publishErrorLog("窗口最小大小大于窗口最大大小");
					break;
				case 108:
					PkgAneWorkerLog.publishErrorLog("前面的元素中已使用的属性");
					break;
				case 109:
					PkgAneWorkerLog.publishErrorLog("重复元素");
					break;
				case 110:
					PkgAneWorkerLog.publishErrorLog("至少需要一个指定类型的元素");
					break;
				case 111:
					PkgAneWorkerLog.publishErrorLog("在应用程序描述符中列出的配置文件都不支持本机扩展，将配置文件添加到支持 本机扩展的 supportedProfies 列表。");
					break;
				case 112:
					PkgAneWorkerLog.publishErrorLog("AIR 目标不支持本机扩展，选择支持本机扩展的目标。");
					break;
				case 113:
					PkgAneWorkerLog.publishErrorLog("<nativeLibrary> 和 <initializer> 必须一起提供，必须为本机扩展中的每个本机库都指定初始值设定项函数。");
					break;
				case 114:
					PkgAneWorkerLog.publishErrorLog("找到不含 <nativeLibrary> 的 <finalizer>，除非平台使用本机库，否则不要指定终结器。");
					break;
				case 115:
					PkgAneWorkerLog.publishErrorLog("默认平台不得包含本机实施。");
					break;
				case 116:
					PkgAneWorkerLog.publishErrorLog("此目标不支持浏览器调用，对于指定的打包目标，<allowBrowserInvocation> 元素不能为 true。");
					break;
				case 117:
					PkgAneWorkerLog.publishErrorLog("此目标至少需要命名空间 n 打包本机扩展。");
					break;
				case 200:
					PkgAneWorkerLog.publishErrorLog("无法打开图标文件");
					break;
				case 201:
					PkgAneWorkerLog.publishErrorLog("图标大小错误");
					break;
				case 202:
					PkgAneWorkerLog.publishErrorLog("图标文件包含的某种图像格式不受支持");
					break;
				case 300:
					PkgAneWorkerLog.publishErrorLog("缺少文件，或无法打开文件");
					break;
				case 301:
					PkgAneWorkerLog.publishErrorLog("缺少或无法打开应用程序描述符文件");
					break;
				case 302:
					PkgAneWorkerLog.publishErrorLog("包中缺少根内容文件");
					break;
				case 303:
					PkgAneWorkerLog.publishErrorLog("包中缺少图标文件");
					break;
				case 304:
					PkgAneWorkerLog.publishErrorLog("初始窗口内容无效");
					break;
				case 305:
					PkgAneWorkerLog.publishErrorLog("初始窗口内容的 SWF 版本超出命名空间的版本");
					break;
				case 306:
					PkgAneWorkerLog.publishErrorLog("配置文件不受支持");
					break;
				case 307:
					PkgAneWorkerLog.publishErrorLog("命名空间必须至少为 nnn");
					break;
				case 2:
					PkgAneWorkerLog.publishErrorLog("用法错误，检查命令行参数是否存在错误");
					break;
				case 5:
					PkgAneWorkerLog.publishErrorLog("未知错误，此错误表示所发生的情况无法按常见的错误条件作出解释。可能的根源包括 ADT 与 Java 运行时环境之间不兼容、ADT 或 JRE 安装损坏以及 ADT 内有编程错误。");
					break;
				case 6:
					PkgAneWorkerLog.publishErrorLog("无法写入输出目录，确保指定的（或隐含的）输出目录可访问，并且所在驱动器有足够的磁盘空间。");
					break;
				case 7:
					PkgAneWorkerLog.publishErrorLog("无法访问证书");
					break;
				case 8:
					PkgAneWorkerLog.publishErrorLog("证书无效，证书文件格式错误、被修改、已到期或被撤消。");
					break;
				case 9:
					PkgAneWorkerLog.publishErrorLog("无法为 AIR 文件签名，验证传递给 ADT 的签名选项。");
					break;
				case 10:
					PkgAneWorkerLog.publishErrorLog("无法创建时间戳，ADT 无法与时间戳服务器建立连接。如果通过代理服务器连接到 Internet，则可能需要配置 JRE 的代理服务器设置。");
					break;
				case 11:
					PkgAneWorkerLog.publishErrorLog("创建证书时出错");
					break;
				case 12:
					PkgAneWorkerLog.publishErrorLog("输入无效，验证命令行中传递给 ADT 的文件路径和其他参数。");
					break;
				case 13:
					PkgAneWorkerLog.publishErrorLog("缺少设备 SDK，验证设备 SDK 配置。ADT 找不到执行指定命令所需的设备 SDK。");
					break;
				case 14:
					PkgAneWorkerLog.publishErrorLog("设备错误，ADT 无法执行命令，因为存在设备限制或设备问题。例如，在尝试卸载未实际安装的应用程序时会显示此退出代码。");
					break;
				case 15:
					PkgAneWorkerLog.publishErrorLog("无设备，验证设备是否已连接且已开启，或仿真器是否正在运行。");
					break;
				case 16:
					PkgAneWorkerLog.publishErrorLog("缺少 GPL 组件，当前的 AIR SDK 未包含执行请求的操作所需的所有组件。");
					break;
				case 17:
					PkgAneWorkerLog.publishErrorLog("设备打包工具失败，由于缺少预期的操作系统组件，因此无法创建包。");
					break;
				case 400:
					PkgAneWorkerLog.publishErrorLog("当前的 Android sdk 版本不支持属性，检查属性名称的拼写是否正确，以及对于在其中出现的元素是否为有效的属性。如果此属性是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
					break;
				case 401:
					PkgAneWorkerLog.publishErrorLog("当前的 Android sdk 版本不支持属性值，检查属性值的拼写是否正确，以及对于该属性是否为有效的值。如果此属性值是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
					break;
				case 402:
					PkgAneWorkerLog.publishErrorLog("当前的 Android sdk 版本不支持 XML 标签，检查 XML 标签名称的拼写是否正确，以及是否为有效的 Android 清单文档元素。如果此元素是在 Android 2.2 之后新增的，您可能需要在 ADT 命令中设置 -platformsdk 标志。");
					break;
				case 403:
					PkgAneWorkerLog.publishErrorLog("不允许覆盖 Android 标签");
					break;
				case 404:
					PkgAneWorkerLog.publishErrorLog("不允许覆盖 Android 属性");
					break;
				case 405:
					PkgAneWorkerLog.publishErrorLog("Android 标签 %1 必须是 manifestAdditions 标签中的第一个元素");
					break;
				case 406:
					PkgAneWorkerLog.publishErrorLog("Android 标签 %2 的属性 %1 具有无效值 %3。");
					break;
				case 1000:
					PkgAneWorkerLog.publishErrorLog("参数太少");
					break;
				case 1001:
					PkgAneWorkerLog.publishErrorLog("创建进程失败");
					break;
			}	
			
			if (event.exitCode!=0) 
			{
				PkgAneWorkerLog.publishInfoLog("打包失败");
			}
			
			removeBaseDir();
			
			CurrentWork.terminate();
		}
		
		private function copyZipEntries(dist:ZipOutput,src:ZipFile):void{
			
			var tmpZipEntry:ZipEntry;
			
			for each(tmpZipEntry in src.entries){
				if (tmpZipEntry.name.indexOf("META-INF")<=-1
					&&!tmpZipEntry.isDirectory()) 
				{
					try{
						dist.putNextEntry(new ZipEntry(tmpZipEntry.name));
						dist.write(src.getInput(tmpZipEntry));
						dist.closeEntry();
					}catch(zipErr:ZipError){
//						PkgAneWorkerLog.publishWarnLog(zipErr.message);
					}
				}
			}
		}
		
		
		public function startPkgAne():void{
			
			try{
				
				var data:PkgAneConfigData = PkgAneWorkerConfig.pkgAneConfigData;
				
				PkgAneWorkerLog.publishInfoLog("开始打包");
				
				var swcFile:File = new File(data.swcFilePath.replace(/\\/g,"/"));
				
				var s:FileStream = new FileStream;
				s.open(swcFile,FileMode.READ);
				var bytes:ByteArray=new ByteArray;
				s.readBytes(bytes,0,s.bytesAvailable);
				s.close();
				
				var zip:ZipFile = new ZipFile(bytes);
				var libraryZipEntry:ZipEntry = zip.getEntry("library.swf");
				if (!libraryZipEntry) 
				{
					PkgAneWorkerLog.publishErrorLog("指定的swc文件无效");
					return;
				}
				
				PkgAneWorkerLog.publishInfoLog("提取SWC中的library.swf文件");
				var libSwfFileContent:ByteArray = zip.getInput(libraryZipEntry);
				
				
				PkgAneWorkerLog.publishInfoLog("创建临时工作目录");
				baseDir = File.createTempDirectory();
				
				
				PkgAneWorkerLog.publishInfoLog("生成library.swf");
				var libSwfFile:File = baseDir.resolvePath("library.swf");
				var fs:FileStream = new FileStream();
				fs.open(libSwfFile,FileMode.WRITE);
				fs.writeBytes(libSwfFileContent);
				fs.close();
				
				
				PkgAneWorkerLog.publishInfoLog("生成extension.xml");
				var extensionFile:File = baseDir.resolvePath("extension.xml");
				fs = new FileStream();
				fs.open(extensionFile,FileMode.WRITE);
				fs.writeUTFBytes(data.aneDescFileContent);
				fs.close();
				
				var platformDir:File=null;
				var fileNativeLib:File=null;
				var fileAttachment:File=null;
				var attachmentPaths:Array=null;
				var i:int=0;
				if (data.selectAndroidPlatform) 
				{
					
					PkgAneWorkerLog.publishInfoLog("创建Android平台目录");
					platformDir = baseDir.resolvePath(PLATFORM_ANDROID_DIR_NAME);
					platformDir.createDirectory();
					
					PkgAneWorkerLog.publishInfoLog("拷贝library.swf到Android平台目录");
					libSwfFile.copyTo(platformDir.resolvePath("library.swf"),true);
					
					fileNativeLib = new File(data.androidExtNativeLibFilePath.replace(/\\/g,"/"));
					PkgAneWorkerLog.publishInfoLog("拷贝"+fileNativeLib.name+"到Android平台目录");
					fileNativeLib.copyTo(platformDir.resolvePath(fileNativeLib.name),true);
					
					if (data.androidExtSelectAttachments) 
					{
						PkgAneWorkerLog.publishInfoLog("开始拷贝扩展附件到Android平台目录");
						attachmentPaths = data.androidExtAttachments.split(";");
						for (i = 0; i < attachmentPaths.length; i++) 
						{
							try{
								fileAttachment = new File(attachmentPaths[i].replace(/\\/g,"/"));
								if (fileAttachment.exists) 
								{
									PkgAneWorkerLog.publishInfoLog("拷贝附件"+fileAttachment.name);
									
									fileAttachment.copyTo(platformDir.resolvePath(fileAttachment.name),true);
								}else{
									PkgAneWorkerLog.publishWarnLog(attachmentPaths[i]+"不存在，跳过");
								}
							}catch(e:Error){
								PkgAneWorkerLog.publishWarnLog("路径"+attachmentPaths[i]+"无效，跳过");
							}
						}
					}
					if (data.androidExtSelectDepends&&
						fileNativeLib.extension!=null&&
						fileNativeLib.extension.toLowerCase()=="jar") 
					{
						var androidDepends:Array = data.androidExtDepends.split(";");
						if (androidDepends!=null&&androidDepends.length>0) 
						{
							
							var dependFile:File;
							
							PkgAneWorkerLog.publishWarnLog("开始将本地库和依赖库打包在一起，打包过程中将忽略所有冲突");
							var allNativeLibZip:ZipOutput = new ZipOutput();
							
							//write main native lib file
							
							PkgAneWorkerLog.publishInfoLog("打包本地库"+fileNativeLib.name);
							var tmpStream:FileStream = new FileStream();
							tmpStream.open(fileNativeLib,FileMode.READ);
							var tmpZipFile:ZipFile = new ZipFile(tmpStream);
							var tmpZipEntry:ZipEntry;
							copyZipEntries(allNativeLibZip,tmpZipFile);
							tmpStream.close();
							
							for (var j:int = 0; j < androidDepends.length; j++) 
							{
								dependFile = new File(androidDepends[j].replace(/\\/g,"/"));
								if (dependFile.exists) 
								{
									if (!dependFile.isDirectory) 
									{
										if (dependFile.extension!=null&&dependFile.extension.toLowerCase()=="jar") 
										{
											PkgAneWorkerLog.publishInfoLog("打包依赖库"+dependFile.name);
											tmpStream.open(dependFile,FileMode.READ);
											tmpZipFile = new ZipFile(tmpStream);
											copyZipEntries(allNativeLibZip,tmpZipFile);
											tmpStream.close();
										}
									}else{
										var children:Array = dependFile.getDirectoryListing();
										for each (var f:File in children) 
										{
											if (f.exists&&
												!f.isDirectory&&
												f.extension!=null&&
												f.extension.toLowerCase()=="jar") 
											{
												PkgAneWorkerLog.publishInfoLog("打包依赖库"+f.name);
												tmpStream.open(f,FileMode.READ);
												tmpZipFile = new ZipFile(tmpStream);
												copyZipEntries(allNativeLibZip,tmpZipFile);
												tmpStream.close();
											}
										}
										
									}
								}else{
									PkgAneWorkerLog.publishWarnLog("依赖项"+dependFile.name+"不存在，跳过");
								}
							}
							allNativeLibZip.finish();
							
							//write the packaged libraries data to the main library file
							PkgAneWorkerLog.publishInfoLog("生成打包后的本机库文件");
							tmpStream.open(fileNativeLib,FileMode.WRITE);
							tmpStream.writeBytes(allNativeLibZip.byteArray);
							tmpStream.close();
						}
					}
				}
				if (data.selectiOSPlatform) 
				{
					
					PkgAneWorkerLog.publishInfoLog("创建iOS平台目录");
					platformDir = baseDir.resolvePath(PLATFORM_iOS_DIR_NAME);
					platformDir.createDirectory();
					
					PkgAneWorkerLog.publishInfoLog("拷贝library.swf到iOS平台目录");
					libSwfFile.copyTo(platformDir.resolvePath("library.swf"),true);
					
					fileNativeLib = new File(data.iOSExtNativeLibFilePath.replace(/\\/g,"/"));
					PkgAneWorkerLog.publishInfoLog("拷贝"+fileNativeLib.name+"到iOS平台目录");
					fileNativeLib.copyTo(platformDir.resolvePath(fileNativeLib.name),true);
					
					if (data.iOSExtSelectAttachments) 
					{
						PkgAneWorkerLog.publishInfoLog("开始拷贝扩展附件到iOS平台目录");
						attachmentPaths = data.iOSExtAttachments.split(";");
						for (i = 0; i < attachmentPaths.length; i++) 
						{
							try{
								fileAttachment = new File(attachmentPaths[i].replace(/\\/g,"/"));
								if (fileAttachment.exists) 
								{
									PkgAneWorkerLog.publishInfoLog("拷贝附件"+fileAttachment.name);
									
									fileAttachment.copyTo(platformDir.resolvePath(fileAttachment.name),true);
								}else{
									PkgAneWorkerLog.publishWarnLog(attachmentPaths[i]+"不存在，跳过");
								}
							}catch(e:Error){
								PkgAneWorkerLog.publishWarnLog("路径"+attachmentPaths[i]+"无效，跳过");
							}
						}
					}				
				}
				if (data.selectWindowsPlatform) 
				{
					
					PkgAneWorkerLog.publishInfoLog("创建Windows平台目录");
					platformDir = baseDir.resolvePath(PLATFORM_WINDOWS_DIR_NAME);
					platformDir.createDirectory();
					
					PkgAneWorkerLog.publishInfoLog("拷贝library.swf到Windows平台目录");
					libSwfFile.copyTo(platformDir.resolvePath("library.swf"),true);
					
					fileNativeLib = new File(data.windowsExtNativeLibFilePath.replace(/\\/g,"/"));
					PkgAneWorkerLog.publishInfoLog("拷贝"+fileNativeLib.name+"到Windows平台目录");
					fileNativeLib.copyTo(platformDir.resolvePath(fileNativeLib.name),true);
					
					if (data.windowsExtSelectAttachments) 
					{
						PkgAneWorkerLog.publishInfoLog("开始拷贝扩展附件到Windows平台目录");
						attachmentPaths = data.windowsExtAttachments.split(";");
						for (i = 0; i < attachmentPaths.length; i++) 
						{
							try{
								fileAttachment = new File(attachmentPaths[i].replace(/\\/g,"/"));
								if (fileAttachment.exists) 
								{
									PkgAneWorkerLog.publishInfoLog("拷贝附件"+fileAttachment.name);
									
									fileAttachment.copyTo(platformDir.resolvePath(fileAttachment.name),true);
								}else{
									PkgAneWorkerLog.publishWarnLog(attachmentPaths[i]+"不存在，跳过");
								}
							}catch(e:Error){
								PkgAneWorkerLog.publishWarnLog("路径"+attachmentPaths[i]+"无效，跳过");
							}
						}
					}				
				}
				if (data.selectMacPlatform) 
				{
					
					PkgAneWorkerLog.publishInfoLog("创建Mac平台目录");
					platformDir = baseDir.resolvePath(PLATFORM_MAC_DIR_NAME);
					platformDir.createDirectory();
					
					PkgAneWorkerLog.publishInfoLog("拷贝library.swf到Mac平台目录");
					libSwfFile.copyTo(platformDir.resolvePath("library.swf"),true);
					
					fileNativeLib = new File(data.macExtNativeLibFilePath.replace(/\\/g,"/"));
					PkgAneWorkerLog.publishInfoLog("拷贝"+fileNativeLib.name+"到Mac平台目录");
					fileNativeLib.copyTo(platformDir.resolvePath(fileNativeLib.name),true);
					
					if (data.macExtSelectAttachments) 
					{
						PkgAneWorkerLog.publishInfoLog("开始拷贝扩展附件到Mac平台目录");
						attachmentPaths = data.macExtAttachments.split(";");
						for (i = 0; i < attachmentPaths.length; i++) 
						{
							try{
								fileAttachment = new File(attachmentPaths[i].replace(/\\/g,"/"));
								if (fileAttachment.exists) 
								{
									PkgAneWorkerLog.publishInfoLog("拷贝附件"+fileAttachment.name);
									
									fileAttachment.copyTo(platformDir.resolvePath(fileAttachment.name),true);
								}else{
									PkgAneWorkerLog.publishWarnLog(attachmentPaths[i]+"不存在，跳过");
								}
							}catch(e:Error){
								PkgAneWorkerLog.publishWarnLog("路径"+attachmentPaths[i]+"无效，跳过");
							}
						}
					}				
				}
				
				
				
				//build command args
				var args:Vector.<String> = new Vector.<String>;
				args.push("-jar");
				args.push(data.adtFilePath.replace(/\\/g,"/"));
				args.push("-package");
				if (!data.useTimeStap) 
				{
					args.push("-tsa");
					args.push("none");
				}
				args.push("-storetype");
				args.push("PKCS12");
				args.push("-keystore");
				args.push(new File(data.certFilePath).nativePath.replace(/\\/g,"/"));
				args.push("-storepass");
				args.push(data.certPassword);
				args.push("-target");
				args.push("ane");
				args.push(data.aneExportPath.replace(/\\/g,"/"));
				args.push(extensionFile.nativePath.replace(/\\/g,"/"));
				args.push("-swc");
				args.push(swcFile.nativePath.replace(/\\/g,"/"));
				if (data.selectAndroidPlatform) 
				{
					args.push("-platform");
					args.push("Android-ARM");
					args.push("-C");
					args.push(baseDir.resolvePath(PLATFORM_ANDROID_DIR_NAME).nativePath.replace(/\\/g,"/"));
					args.push(".");
				}
				if (data.selectiOSPlatform) 
				{
					args.push("-platform");
					if (data.iOSExtUseRealDevice) 
					{
						args.push("iPhone-ARM");
					}else{
						args.push("iPhone-x86");
					}
					if (data.iOSExtSelectPlatformOptionsFile) 
					{
						args.push("-platformoptions");
						args.push(data.iOSExtPlatformOptionsFilePath);
					}
					args.push("-C");
					args.push(baseDir.resolvePath(PLATFORM_iOS_DIR_NAME).nativePath.replace(/\\/g,"/"));
					args.push(".");
				}
				if (data.selectWindowsPlatform) 
				{
					args.push("-platform");
					args.push("Windows-x86");
					args.push("-C");
					args.push(baseDir.resolvePath(PLATFORM_WINDOWS_DIR_NAME).nativePath.replace(/\\/g,"/"));
					args.push(".");
				}
				if (data.selectMacPlatform) 
				{
					args.push("-platform");
					args.push("MacOS-x86");
					args.push("-C");
					args.push(baseDir.resolvePath(PLATFORM_MAC_DIR_NAME).nativePath.replace(/\\/g,"/"));
					args.push(".");
				}
				
				PkgAneWorkerLog.publishInfoLog("执行命令java "+args.join(" "));
				
				var info:NativeProcessStartupInfo  = new NativeProcessStartupInfo;
				info.executable  = new File(data.javaFilePath.replace(/\\/g,"/"));
				info.arguments = args;
				
				start(info);
				
				PkgAneWorkerLog.publishInfoLog("正在执行打包操作，请稍候...");
			}catch(e:Error){
				
				trace(e.getStackTrace());
				
				PkgAneWorkerLog.publishErrorLog("打包过程中出现错误");
				
				removeBaseDir();
				
				CurrentWork.terminate();
			}
		}
		
		
		private function removeBaseDir():void{
			PkgAneWorkerLog.publishInfoLog("删除临时工作目录");
			
			if (baseDir!=null&&baseDir.exists) 
			{
				baseDir.deleteDirectory(true);
			}
		}
	}
}