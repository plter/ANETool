package com.plter.anetool.ui
{
	import flash.desktop.NativeApplication;
	import flash.display.NativeMenu;
	import flash.display.NativeMenuItem;
	import flash.events.Event;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	public class ANEToolMenu
	{
		public function ANEToolMenu()
		{
		}
		
		
		private static var _aneToolMainMenu:NativeMenu=null;
		
		private static var _appMenu:NativeMenu=null;
		private static var _aboutItem:NativeMenuItem = null;
		private static var _learnAneToolItem:NativeMenuItem = null;
		private static var _closeItem:NativeMenuItem=null;
		private static var _quitItem:NativeMenuItem=null;
		
		private static var _fileMenu:NativeMenu=null;
		private static var _openConfigFileItem:NativeMenuItem=null;
		private static var _saveConfigFileItem:NativeMenuItem=null;
		
		private static var _donateMenu:NativeMenu=null;
		private static var _donateToAuthorItem:NativeMenuItem=null;
		
		public static function get aneToolMainMenu():NativeMenu{
			if (_aneToolMainMenu==null) 
			{
				_aneToolMainMenu = new NativeMenu;
				
				_appMenu=new NativeMenu;
				_aneToolMainMenu.addSubmenu(_appMenu,"ANETool");
				_aboutItem = new NativeMenuItem("关于");
				_aboutItem.addEventListener(Event.SELECT,itemSelectHandler);
				_appMenu.addItem(_aboutItem);
				_appMenu.addItem(new NativeMenuItem("",true));
				_learnAneToolItem = new NativeMenuItem("工具使用视频教程");
				_learnAneToolItem.keyEquivalent="h";
				_learnAneToolItem.addEventListener(Event.SELECT,itemSelectHandler);
				_appMenu.addItem(_learnAneToolItem);
				_appMenu.addItem(new NativeMenuItem("",true));
				_closeItem=new NativeMenuItem("关闭");
				_closeItem.keyEquivalent="w";
				_closeItem.addEventListener(Event.SELECT,itemSelectHandler);
				_appMenu.addItem(_closeItem);
				_quitItem=new NativeMenuItem("退出");
				_quitItem.keyEquivalent="q";
				_quitItem.addEventListener(Event.SELECT,itemSelectHandler);
				_appMenu.addItem(_quitItem);
				
				_fileMenu = new NativeMenu;
				_aneToolMainMenu.addSubmenu(_fileMenu,"文件");
				_openConfigFileItem = new NativeMenuItem("打开配置文件");
				_openConfigFileItem.keyEquivalent="o";
				_openConfigFileItem.addEventListener(Event.SELECT,itemSelectHandler);
				_fileMenu.addItem(_openConfigFileItem);
				_saveConfigFileItem = new NativeMenuItem("保存当前配置");
				_saveConfigFileItem.keyEquivalent="s";
				_saveConfigFileItem.addEventListener(Event.SELECT,itemSelectHandler);
				_fileMenu.addItem(_saveConfigFileItem);
				
				_donateMenu = new NativeMenu;
				_aneToolMainMenu.addSubmenu(_donateMenu,"捐助");
				_donateToAuthorItem = new NativeMenuItem("给可怜的作者捐款");
				_donateToAuthorItem.addEventListener(Event.SELECT,itemSelectHandler);
				_donateMenu.addItem(_donateToAuthorItem);
			}
			return _aneToolMainMenu;
		}
		
		protected static function itemSelectHandler(event:Event):void
		{
			switch(event.target)
			{
				case _quitItem:
				case _closeItem:
				{
					NativeApplication.nativeApplication.exit();
					break;
				}
				case _aboutItem:
					AboutWindow.show(AneTool.aneTool);
					break;
				case _learnAneToolItem:
					navigateToURL(new URLRequest("http://xtiqin.blog.163.com/blog/static/17017217920133584934827/"));
					break;
				case _openConfigFileItem:
					AneTool.aneTool.openConfigFile();
					break;
				case _saveConfigFileItem:
					AneTool.aneTool.saveCurrentConfigToFile();
					break;
				case _donateToAuthorItem:
					DonateWindow.show(AneTool.aneTool);
					break;
			}			
		}
	}
}