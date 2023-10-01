import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.awt.event.KeyAdapter;
import images.images;

public class MyFrame{
	
	Main main;
	int currentlevel = 0;
	int category;
	int pass = 0;
	String level[];
	ImageIcon middleimage[];
	ImageIcon b_icon;
	Sound backgroundMusic = new Sound("sound\\game.wav");
	Sound typeSound = new Sound("sound\\type.wav");
	Sound deleteSound = new Sound("sound\\delete.wav");
	Sound correctSound = new Sound("sound\\correct.wav"); 
	Sound wrongSound = new Sound("sound\\wrong.wav"); 

	public MyFrame(){
		backgroundMusic.playWithFadeIn(500);
	}
	
	public JPanel getPanel() {
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1280, 720));
		//background
        JLabel background = new JLabel(b_icon);
        background.setPreferredSize(new Dimension(1280,720));
        background.setLayout(new BorderLayout());
        
        //Middle image
        JLabel middle = new JLabel(middleimage[currentlevel]);
        middle.setPreferredSize(new Dimension(350,350));
        
        //length of the words
        int length = level[currentlevel].length();
        
        //Label of level
        JPanel levellabel = new JPanel(new FlowLayout(FlowLayout.LEFT,-50,0));
        //smaller font
        final int width = 82;//(int)(images.font[0].getIconWidth()*0.5);
        final int height = 67;//(int)(images.font[0].getIconHeight()*0.5);
        levellabel.setPreferredSize(new Dimension(1280,height));
        Image adjustment [] = new Image[15];
        
        for(int i=0;i<10;i++)
        	adjustment[i] = images.Numbers[i].getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        adjustment[10] = images.font[11].getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        adjustment[11] = images.font[4].getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        adjustment[12] = images.font[21].getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        adjustment[13] = images.colon_icon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        adjustment[14] = images.slash.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH);
        
        ImageIcon smallerfont[] = new ImageIcon[15];
        for(int i=0;i<adjustment.length;i++)
        	smallerfont[i] = new ImageIcon(adjustment[i]);
        
        JLabel [] levels = {
        		new JLabel(),
        		new JLabel(smallerfont[10]),
        		new JLabel(smallerfont[11]),
        		new JLabel(smallerfont[12]),
        		new JLabel(smallerfont[11]),
        		new JLabel(smallerfont[10]),
        		new JLabel(),
        		new JLabel(smallerfont[13]),
        		new JLabel()
        };
        EmptyBorder emptyborder1 = new EmptyBorder(0,60,0,60);
        EmptyBorder emptyborder2 = new EmptyBorder(0,40,0,40);
        levels[0].setBorder(emptyborder1);
        levels[6].setBorder(emptyborder2);
        levels[8].setBorder(emptyborder2);
        for(int i = 0;i<levels.length;i++)
        	levellabel.add(levels[i]);
        levellabel.add(new JLabel(smallerfont[currentlevel+1]));
        levellabel.add(new JLabel(smallerfont[14]));
        levellabel.add(new JLabel(smallerfont[8]));
        
        //panel for user input
        JPanel input = new JPanel(new GridBagLayout());
        input.setPreferredSize(new Dimension(1280,266));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel line = new JPanel(new FlowLayout());
        Image line_icon = images.line_icon.getImage().getScaledInstance(length<6 ? 163 : 1280/(length+1), 53,Image.SCALE_SMOOTH);
        for(int i = 0;i<length;i++)
        	line.add(new JLabel (new ImageIcon(line_icon)));
        JPanel word = new JPanel(new FlowLayout(FlowLayout.LEFT));
        word.setPreferredSize(new Dimension(length*(length<6  ? 163 : 1280/(length+1)),133));
    	
        //KeyListener to get user's input
        panel.addKeyListener(new KeyAdapter() {
  			int counter = 0;
			String ans = "";
    		public void keyTyped(KeyEvent e) {
    			// TODO Auto-generated method stub
    			int length = level[currentlevel].length();
    			char keyChar = Character.toUpperCase(e.getKeyChar());
    			if(Character.isAlphabetic(keyChar) && counter<length)
    			{
    				ans += keyChar;
    				Image word_icon = images.font[keyChar-65].getImage().getScaledInstance(length<6  ? 163 : 1280/(length+1),133,Image.SCALE_SMOOTH);
    				word.add(new JLabel (new ImageIcon(word_icon)));
    				counter++;
    				word.revalidate();
    				word.repaint();
					typeSound.play();
    			}
    			else if(keyChar == '\n')
    			{
    				if(ans.equals(level[currentlevel]))
    				{
    					if(currentlevel != level.length-1)
    					{
    						currentlevel++;
							correctSound.play();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
    						GameFrame();
    					}
    					else
    					{
    						main.setcurrentState(0);
    						currentlevel = 0;
    						pass++;
    						saveProgress(category,true);
							backgroundMusic.stop();
    					}
    				}
    				else
    				{
    		  			counter = 0;
    					ans = "";
						wrongSound.play();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
    					word.removeAll();
    					word.revalidate();
    					word.repaint();
    					main.getContentPane().setFocusable(true);
    					main.getContentPane().requestFocus();
    				}
    			}
    			else if(keyChar == '\b')
    			{
    				Component[] componentList = word.getComponents();
    				try {
    					word.remove(componentList[componentList.length-1]);
    					ans = ans.substring(0, ans.length()-1);
    					counter--;
    					word.revalidate();
    					word.repaint();
    				}catch(Exception arg) {
    					System.out.println("Nothing to delete");
    				}
					deleteSound.play();
    			}

    		}
    		public void keyPressed(KeyEvent e) {
    			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
    			{
    				saveProgress(category,false);
					backgroundMusic.stop();
    				main.setcurrentState(0);
    			}
        		}});
    			
    	levellabel.setOpaque(false);
        line.setOpaque(false);
        word.setOpaque(false);
        input.setOpaque(false);
        
        panel.removeAll();
        panel.add(background);
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	input.add(word,gbc);
    	gbc.gridx = 0;
    	gbc.gridy = 1;
    	input.add(line,gbc);
    	background.add(middle,BorderLayout.CENTER);
    	background.add(input,BorderLayout.SOUTH);
    	background.add(levellabel,BorderLayout.NORTH);
    	return panel;
	}
	
	public void GameFrame(){
		main.setContentPane(getPanel());
		main.getContentPane().setFocusable(true);
		main.getContentPane().requestFocus();
		main.Repaint();
	}
	public void saveProgress(int category,boolean passAll) {
		String data[][] = new String [3][9];
		int level = currentlevel+1;
		try {
			BufferedReader user = new BufferedReader(new FileReader("Users.txt"));
			String str=null;
			String line[]=null;
			
			for(int i = 0;(str = user.readLine()) != null;i++)
			{
				line = str.split(",");
				for(int j = 0;j<line.length;j++)
					data[i][j] = line[j];
			}
			data[0][category] = String.valueOf(level);
			if(passAll)
				data[0][category+4] = String.valueOf(pass);
			user.close();
			BufferedWriter output = new BufferedWriter(new FileWriter("Users.txt"));
			for(int i = 0;i<data.length;i++)
			{
				for(int j = 0;j<data[0].length;j++)
				{
					output.write(data[i][j]);
					output.write(",");
				}
				output.newLine();
			}
			output.close();
			
		}catch(Exception e) {
			System.out.println("Cannot Read User's file");
		}
	}
	
	public int[] getProgress(int category) {
		int[] level = new int[2];
		try {
			BufferedReader user = new BufferedReader(new FileReader("Users.txt"));
			String str=null;
			str = user.readLine();
			String line[] = str.split(",");
			level[0] = Integer.parseInt(line[category]) - 1;
			level[1] = Integer.parseInt(line[category+4]);
			user.close();
		}catch(Exception e) {
			System.out.println("Cannot Read User's file");
		}
		return level;
	}
}
