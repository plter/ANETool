package com.plter.anetool.workers.pkganework
{
	import com.plter.anetool.vo.VOManager;
	
	import flash.display.Sprite;
	import flash.system.Worker;
	
	public class PkgAneWorker extends Sprite
	{
		
		public function PkgAneWorker()
		{
			super();
			
			VOManager.regVO();
			
			PkgAneWorkerConfig.progressMessageChannel = Worker.current.getSharedProperty("progressMC");
			PkgAneWorkerConfig.pkgAneConfigData = Worker.current.getSharedProperty("pkgAneConfigData");
			
			new PkgAneProcess().startPkgAne();
		}
	}
}