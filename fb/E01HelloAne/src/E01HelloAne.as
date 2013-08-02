package
{
	import com.plter.hi.Hi;
	
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	
	public class E01HelloAne extends Sprite
	{
		public function E01HelloAne()
		{
			super();
			
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			
			var h:Hi = new Hi;
			trace(h.sayHi());
			h.dispose();
		}
	}
}