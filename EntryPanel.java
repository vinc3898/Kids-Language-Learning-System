import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


import images.images;

public class EntryPanel implements ActionListener{
	final Main main;
	private JLabel background,title,submenu,submenuC,backscreen,currentuserL,adduser,fadingrectangle;
	private JButton button1,button2,button3,button4,button5,button6,button7,button8;
	private JTextField textfield1;
	private JList<String> list;
	private DefaultListModel<String> userlist;
	private String [][]data;
	
	//size of button icon
    private int original_w = 1844;
    private int original_h = 1320;
    private int width = (int)(original_w/3.8);
    private int height = (int)(original_h/3.8);
    
    //gap
    private int w_start = -20;
    private int h_start = 80;
    private int w_gap = 270;
    private int h_gap = 200;
    private Image buttonBufferedimage [] = new Image[8];
    private ImageIcon buttonicon [] = new ImageIcon[8];

	private Sound backgroundMusic;
	private Sound buttonClickedSound;
    
	public EntryPanel(final Main main) {
		this.main = main;
		this.button1 = new JButton();
		this.button2 = new JButton();
		this.button3 = new JButton();
		this.button4 = new JButton();
		this.button5 = new JButton();
		this.button6 = new JButton();
		this.button7 = new JButton();
		this.button8 = new JButton();
		Image background_image = images.background[0].getImage().getScaledInstance(1280,720,Image.SCALE_DEFAULT);
		this.background = new JLabel(new ImageIcon(background_image));
		this.title = new JLabel(images.title);
		this.backscreen = new JLabel();
		this.submenu = new JLabel(images.submenu[0]);
		this.submenuC = new JLabel(images.submenu[2]);
		this.adduser = new JLabel(images.submenu[7]);
		this.currentuserL = new JLabel();
		this.textfield1 = new JTextField();
		this.userlist = new DefaultListModel<>();
		this.fadingrectangle = new JLabel();
		for(int i=0, j=0;i<=buttonBufferedimage.length-1;i+=2,j++)
		{
			buttonBufferedimage [i] = images.Mainbuttons[j].getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
			buttonBufferedimage [i+1] = images.MainbuttonsR[j].getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		}
		for(int i=0;i<buttonicon.length;i++)
			buttonicon[i] = new ImageIcon(buttonBufferedimage[i]);

		// Initialize background music
		backgroundMusic = new Sound("sound\\background.wav");
		backgroundMusic.playWithFadeIn(10000);

		buttonClickedSound = new Sound("sound\\buttonClick.wav");
	}
	
	public void MainMenu(){
		main.setContentPane(getPanel());
		main.Repaint();
	}
	
	private void submenu(boolean open){
		
		if(open)
		{
			data = getUserdata();
			currentuserL.setText(data[0][0]);
			userlist.removeAllElements();
			userlist.addElement(data[1][0]);
			userlist.addElement(data[2][0]);
			backscreen.setVisible(true);
			button1.setEnabled(false);
			button2.setEnabled(false);
			button3.setEnabled(false);
			button4.setEnabled(false);
			button5.setEnabled(false);
		}
		else
		{
			backscreen.setVisible(false);
			button1.setEnabled(true);
			button2.setEnabled(true);
			button3.setEnabled(true);
			button4.setEnabled(true);
			button5.setEnabled(true);
			button8.setVisible(false);
			adduser.setVisible(false);
			textfield1.setVisible(false);
		}
		main.Repaint();

	}
	
	private void selectuser() {
		int selection = list.getSelectedIndex();
		if(list.getSelectedValue().equals("Default"))
			addnewuser();
		else
		{
			changeuser(selection,data);
			submenu(false);
			data = getUserdata();
			currentuserL.setText(data[0][0]);
			userlist.removeAllElements();
			userlist.addElement(data[1][0]);
			userlist.addElement(data[2][0]);
		}

	}
	
	private void addnewuser() {
		adduser.setVisible(true);
		button8.setVisible(true);
		textfield1.setVisible(true);
		main.Repaint();
	}
	
	private void getnewuser() {
		int selection = list.getSelectedIndex();
		String name = textfield1.getText();
		textfield1.setText("");
		data = getUserdata();
		if(selection == 0){
			data[1][0] = name;
			changeuser(0,data);
		}
		else {
			data[2][0] = name;
			changeuser(1,data);
		}
		userlist.removeAllElements();
		userlist.addElement(data[1][0]);
		userlist.addElement(data[2][0]);
		submenu(false);

	}
	
	
	private void changeuser(int selection,String data[][]) {
		try {
			BufferedWriter user = new BufferedWriter(new FileWriter("Users.txt"));
			if(selection == 0) {
				for(int i = 1;i<data.length;i++)
				{
					for(int j = 0;j<data[0].length;j++)
					{
						user.write(data[i][j]);
						user.write(",");
					}
					user.newLine();
				}
				for(int i = 0;i<data[0].length;i++)
				{
					user.write(data[0][i]);
					user.write(",");
				}
				
			}
			else if(selection == 1){
				for(int i = 2;i>0;i--)
				{
					for(int j = 0;j<data[0].length;j++)
					{
						user.write(data[i][j]);
						user.write(",");
					}
					user.newLine();
				}
				for(int i = 0;i<data[0].length;i++)
				{
					user.write(data[0][i]);
					user.write(",");
				}
			}	
			user.close();
		}catch(Exception e) {
			System.out.println("Cannot Read User's file");
		}
	}
	
	private String[][] getUserdata() {
		String[][] data = new String[3][9];
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
			user.close();
		}catch(Exception e) {
			System.out.println("Cannot Read User's file");
		}
		return data;
	}
	
	public JPanel getPanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(1280, 720));
		
		//background
		background.setBounds(0,0,1280,720);
		
		//title
		title.setBounds(290,40,731,51);
		
		//button
		button1.setIcon(buttonicon[0]);
    	button1.setDisabledIcon(buttonicon[0]);
    	button1.setRolloverEnabled(true);
    	button1.setRolloverIcon(buttonicon[1]);
    	button1.setBorderPainted(false);
    	button1.setFocusPainted(false);
    	button1.setContentAreaFilled(false);
    	button1.setBounds(w_start, h_start, width, height);
    	button1.addActionListener(this);

    	button2.setIcon(buttonicon[2]);
    	button2.setDisabledIcon(buttonicon[2]);
    	button2.setRolloverEnabled(true);
    	button2.setRolloverIcon(buttonicon[3]);
    	button2.setBorderPainted(false);
    	button2.setFocusPainted(false);
    	button2.setContentAreaFilled(false);
    	button2.setBounds(w_start + w_gap, h_start + h_gap, width, height);
    	button2.addActionListener(this);
    	
    	button3.setIcon(buttonicon[4]);
    	button3.setDisabledIcon(buttonicon[4]);
    	button3.setRolloverEnabled(true);
    	button3.setRolloverIcon(buttonicon[5]);
    	button3.setBorderPainted(false);
    	button3.setFocusPainted(false);
    	button3.setContentAreaFilled(false);
    	button3.setBounds(w_start + w_gap * 2, h_start, width, height);
    	button3.addActionListener(this);
    	
    	button4.setIcon(buttonicon[6]);
    	button4.setDisabledIcon(buttonicon[6]);
    	button4.setRolloverEnabled(true);
    	button4.setRolloverIcon(buttonicon[7]);
    	button4.setBorderPainted(false);
    	button4.setFocusPainted(false);
    	button4.setContentAreaFilled(false);
    	button4.setBounds(w_start + w_gap * 3, h_start + h_gap, width, height);
    	button4.addActionListener(this);
    	
    	button5.setIcon(images.submenu[3]);
    	button5.setDisabledIcon(images.submenu[3]);
    	button5.setRolloverEnabled(true);
    	button5.setRolloverIcon(images.submenu[4]);
    	button5.setBorderPainted(false);
    	button5.setFocusPainted(false);
    	button5.setContentAreaFilled(false);
    	button5.setBounds(1180,5,111,111);
    	button5.addActionListener(this);
    	
    	//Sub-Menu
		//A black screen let user focus on Sub-Menu
		backscreen.setBackground(new Color(0,0,0,100));
		backscreen.setOpaque(true);
		backscreen.setBounds(0,0,1280,720);
		backscreen.setVisible(false);
		
		//Sub-Menu Image
		submenu.setBounds(336,128,607,465);
		
		//Sub-Menu button
		button6.setIcon(images.submenu[1]);
		button6.setRolloverEnabled(false);
		button6.setBorderPainted(false);
    	button6.setFocusPainted(false);
    	button6.setContentAreaFilled(false);
    	button6.setBounds(850,130,121,121);
    	button6.addActionListener(this);
    	
		button7.setIcon(images.submenu[5]);
		button7.setRolloverEnabled(false);
		button7.setBorderPainted(false);
    	button7.setFocusPainted(false);
    	button7.setContentAreaFilled(false);
    	button7.setBounds(512,450,256,109);
    	button7.addActionListener(this);
    	
		button8.setIcon(images.submenu[6]);
		button8.setRolloverEnabled(false);
		button8.setBorderPainted(false);
    	button8.setFocusPainted(false);
    	button8.setContentAreaFilled(false);
    	button8.setBounds(600,400,141,81);
    	button8.addActionListener(this);
    	button8.setVisible(false);
    	
    	//Sub-Menu Component
    	submenuC.setBounds(500,150,270,76);
    	adduser.setBounds(336,180,658,327);
    	adduser.setVisible(false);
    	textfield1.setBounds(525,250,270,76);
    	textfield1.setBackground(new Color(0,0,0,100));
    	textfield1.setForeground(new Color(255,255,255));
    	textfield1.setBorder(null);
    	textfield1.setFont(new Font("Monospaced",Font.BOLD,30));
    	textfield1.setVisible(false);
    	
    	//Current User
    	currentuserL.setBounds(500,150,270,76);
    	currentuserL.setFont(new Font("Monospaced",Font.BOLD,30));
    	currentuserL.setForeground(new Color(71,14,4));
    	currentuserL.setHorizontalAlignment(JLabel.CENTER);
    	
    	//Other User
    	list = new JList<>(userlist);
    	list.setBounds(500,250,270,142);
    	list.setBackground(null);
    	list.setBorder(null);
    	list.setFont(new Font("Monospaced",Font.BOLD,30));
    	list.setForeground(new Color(252,171,2));
    	
    	backscreen.add(button8);
    	backscreen.add(textfield1);
    	backscreen.add(adduser);
    	backscreen.add(button6);
    	backscreen.add(button7);
    	backscreen.add(currentuserL);
    	backscreen.add(list);
    	backscreen.add(submenuC);
    	backscreen.add(submenu);
    	
    	panel.add(backscreen);
    	panel.add(background);
		background.add(title);
		background.add(button1);
		background.add(button2);
		background.add(button3);
		background.add(button4);
		background.add(button5);

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() != null) {
			buttonClickedSound.play();
			if(e.getSource() == button1){
				main.setcurrentState(1);
				backgroundMusic.stop();
			}
			else if(e.getSource() == button2){
				main.setcurrentState(2);
				backgroundMusic.stop();
			}
			else if(e.getSource() == button3){
				main.setcurrentState(3);
				backgroundMusic.stop();
			}
			else if(e.getSource() == button4){
				main.setcurrentState(4);
				backgroundMusic.stop();
			}
			else if(e.getSource() == button5)
				submenu(true);
			else if(e.getSource() == button6)
				submenu(false);
			else if(e.getSource() == button7)
			{
				if(list.getSelectedValue() != null)
					selectuser();
			}
			else if(e.getSource() == button8)
				getnewuser();
		}
	}
}
