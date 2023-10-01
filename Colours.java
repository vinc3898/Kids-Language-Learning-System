import javax.swing.JFrame;
import images.images;

public class Colours extends MyFrame{	

	final Main main;
	String[] level = 
		{
			"PINK",
			"BLUE",
			"PURPLE",
			"RED",
			"WHITE",
			"BLACK",
			"GREEN",
			"YELLOW"
		};
	int currentlevel = 0;
	int pass = 0;
	final int category = 3;
	public Colours(Main main) {
		this.main = main;
		int[] level = getProgress(category);
		this.currentlevel = level[0];
		this.pass = level[1];
		super.main = main;
		super.category = this.category;
		super.level = this.level;
		super.pass = this.pass;
		super.currentlevel = this.currentlevel;
		super.middleimage = images.Colour;
		super.b_icon = images.background[3];
		this.GameFrame();
	}
}
