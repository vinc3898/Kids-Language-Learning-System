import javax.swing.*;
import images.images;

public class Main extends JFrame{
	
	public Main(){
		super("Kids Language Learning System");
		setIconImage(images.programIcon.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public void Repaint()
    { 
	    pack();
	    setVisible(true);
	    setLocationRelativeTo(null);
	    revalidate();
	    repaint();
    }
	


	public void setcurrentState(int currentState){
		switch(currentState) {
			case 0:
				EntryPanel panel = new EntryPanel(this);
				panel.MainMenu();
				break;
			case 1:
				Animals a = new Animals(this);
				break;
			case 2:
				Numbers b = new Numbers(this);
				break;
			case 3:
				Colours c = new Colours(this);
				break;
			case 4:
				Fruits d = new Fruits(this);
				break;
			case 5:
				Repaint();
				break;
		}
	}
    
	public static void main(String[] args) 
	{
		Main main = new Main();
		EntryPanel panel = new EntryPanel(main);
		panel.MainMenu();
	}
	
}

