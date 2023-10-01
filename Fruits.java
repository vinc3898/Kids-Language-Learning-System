import javax.swing.JFrame;
import images.images;

public class Fruits extends MyFrame{	

	final Main main;
	String[] level = 
		{
			"WATERMELON",
			"BANANA",
			"STRAWBERRY",
			"LEMON",
			"PINEAPPLE",
			"AVOCADO",
			"ORANGE",
			"KIWI"
		};
	int currentlevel = 0;
	int pass = 0;
	final int category = 2;
	public Fruits(Main main) {
		this.main = main;
		int[] level = getProgress(category);
		this.currentlevel = level[0];
		this.pass = level[1];
		super.main = main;
		super.category = this.category;
		super.level = this.level;
		super.pass = this.pass;
		super.currentlevel = this.currentlevel;
		super.middleimage = images.Fruit;
		super.b_icon = images.background[4];
		this.GameFrame();
	}
}
