import javax.swing.JFrame;
import images.images;

public class Numbers extends MyFrame{	

	final Main main;
	String[] level = 
		{
			"ONE",
			"TWO",
			"THREE",
			"SIX",
			"SEVEN",
			"EIGHT",
			"TWELVE",
			"TEN"
		};
	int currentlevel = 0;
	int pass = 0;
	final int category = 2;
	public Numbers(Main main) {
		this.main = main;
		int[] level = getProgress(category);
		this.currentlevel = level[0];
		this.pass = level[1];
		super.main = main;
		super.category = this.category;
		super.level = this.level;
		super.pass = this.pass;
		super.currentlevel = this.currentlevel;
		super.middleimage = images.Number;
		super.b_icon = images.background[2];
		this.GameFrame();
	}
}
