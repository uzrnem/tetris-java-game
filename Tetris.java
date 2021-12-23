import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Tetris extends JPanel implements KeyListener, ActionListener {
	static JFrame frame;
	static JMenuBar mb;
	static JMenu file, help;
	static JMenuItem navin, paused, resume, exit, about;
	static Timer t;
	static boolean tetris, pause;
	static int level, score, record;
	static Box newbox, oldbox;
	static int bx, by, lx, ly, x, y;
	static int ispeed, speed;
	static Random rndm;
	static Image img, omg;
	static char[][] arr = new char[10][20];
	static Container contentPane;
	public Tetris() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("tetris.bsp");
			prop.load(input);
		 	record = Integer.parseInt(prop.getProperty("c"));
		} catch (IOException c1) {
			Tetris.createNewFile();
		} catch (Exception c3) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException c4) {
					c4.printStackTrace();
				}
			}
		}
		for(int c=0;c<10;c++){
			for(int e=0;e<20;e++){
				arr[c][e]='w';
			}
		}
		newbox=Box.factoryMethod(rndm.nextInt(7));
		oldbox=Box.factoryMethod(rndm.nextInt(7));
		addKeyListener(this);
	}

	public void keyPressed(KeyEvent evt) {
		int keyCode = evt.getKeyCode();
		if (keyCode == KeyEvent.VK_LEFT && pause==false){
			Tetris.comeSide(-1);
		}
		else if (keyCode == KeyEvent.VK_RIGHT && pause==false){
			Tetris.comeSide(1);
		}
		else if (keyCode == KeyEvent.VK_UP && pause==false){
			Tetris.changeIfPossible();
		}
		else if (keyCode == KeyEvent.VK_DOWN && pause==false){
			if(speed==ispeed){
				speed=ispeed/5;
				t.setDelay(speed);
			}
			else{
				speed=ispeed;
				t.setDelay(speed);
			}
		}
		else if (keyCode == KeyEvent.VK_P){
			Tetris.pauseGame();
		}
		else if (keyCode == KeyEvent.VK_S){
			Tetris.resumeGame();
		}
		contentPane.repaint();
	}

	public void keyReleased(KeyEvent evt) {
	}
	public void keyTyped(KeyEvent evt) {
	}

	public boolean isFocusTraversable() {
		return true;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(new Font(Font.MONOSPACED,Font.BOLD,20));
		g.setColor(Color.BLACK);
		g.drawImage(img,0,0,380,440,this);
		g.drawString(""+level, 260, 260-3);
		g.drawString(""+score, 260, 320-3);
		g.drawString(""+record, 260, 380-3);
		for(int c=0;c<10;c++){
			for(int e=0;e<20;e++){
				omg=Toolkit.getDefaultToolkit().getImage(Tetris.class.getResource(arr[c][e]+".png"));
				g.drawImage(omg,20+c*20,20+e*20,20,20,this);
			}
		}
		for(int c=0;c<4;c++){
			g.drawImage(newbox.img,260+newbox.type[c][0]*20,80+newbox.type[c][1]*20,20,20,this);
		}
		for(int c=0;c<4;c++){
			g.drawImage(oldbox.img,bx+x*20+oldbox.type[c][0]*20,by+y*20+oldbox.type[c][1]*20,20,20,this);
		}
	}
	public static void main(String[] args){
		mb=new JMenuBar();
		file = new JMenu("File");
		help = new JMenu("Help");
		navin = new JMenuItem("New");
		paused =  new JMenuItem("Paused");
		resume =  new JMenuItem("Resume");
		exit =  new JMenuItem("Exit");
		about =  new JMenuItem("About");
		tetris=true;
		pause=false;
		level=1;
		score=0;
		record=0;
		bx=20;
		by=20;
		lx=3;
		ly=0;
		x=3;
		y=0;
		ispeed=1000;
		speed=ispeed;
		rndm = new Random();
		img=Toolkit.getDefaultToolkit().getImage(Tetris.class.getResource("TetrisBg.png"));
		frame = new JFrame();
		Tetris trs = new Tetris();
		frame.setTitle("Tetris");
		frame.setSize(396,502);
		WindowListener frameClose=new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				Tetris.exitGame();
			}
		};
		frame.addWindowListener(frameClose);
		file.add(navin);
		file.addSeparator();
		file.add(paused);
		file.add(resume);
		file.addSeparator();
		file.add(exit);
		help.add(about);
		navin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		paused.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
		resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		navin.addActionListener(trs);
		paused.addActionListener(trs);
		resume.addActionListener(trs);
		exit.addActionListener(trs);
		about.addActionListener(trs);
		mb.add(file);
		mb.add(help);
		frame.setJMenuBar(mb);
		contentPane = frame.getContentPane();
		contentPane.add(trs);
		frame.setIconImage(newbox.img);
		frame.setVisible(true);
		t=new Timer(speed,new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!Tetris.comeDown()){
					if(!Tetris.setBox()){
						Tetris.gameOver();
					}
					else{
						Tetris.checkLines();
					}
				}
				contentPane.repaint();
			}
		});
		t.start();
	}
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if(ae.getActionCommand().equals("New")){
			Tetris.newStart();
		}
		if(ae.getActionCommand().equals("Pause")){
		}
			Tetris.pauseGame();
		if(ae.getActionCommand().equals("Resume")){
			Tetris.resumeGame();
		}
		if(ae.getActionCommand().equals("Exit")){
			Tetris.exitGame();
		}
		if(ae.getActionCommand().equals("About")){
			Tetris.aboutMe();
		}
		if(ae.getActionCommand().equals("Stop")){
			Tetris.gameOver();
		}
	}
	public static boolean comeDown(){
		if(localLogic(oldbox.type,x,0,y,1)){
			y=y+1;
			return true;
		}
		return false;
	}
	public static void gameOver(){
		t.stop();
		tetris=false;
		pause=true;
		if(score>record){
			Properties prop = new Properties();
			OutputStream output = null;
			try {
				output = new FileOutputStream("tetris.bsp");
				prop.setProperty("c", score+"");
				prop.store(output, null);
			} catch (Exception io) {
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "Your Score is "+score, "Game Over", JOptionPane.INFORMATION_MESSAGE );
	}
	public static void changeIfPossible(){
		if(localLogic(oldbox.newBox(),x,0,y,0)){
			oldbox.change();
		}
		else if(localLogic(oldbox.newBox(),x,1,y,0)){
			x=x+1;
			oldbox.change();
		}
		else if(localLogic(oldbox.newBox(),x,2,y,0)){
			x=x+2;
			oldbox.change();
		}
		else if(localLogic(oldbox.newBox(),x,-1,y,0)){
			x=x-1;
			oldbox.change();
		}
		else if(localLogic(oldbox.newBox(),x,-2,y,0)){
			x=x-2;
			oldbox.change();
		}
	}
	public static boolean setBox(){
		speed=ispeed;
		t.setDelay(speed);
		for(int c=0;c<4;c++){
			arr[x+oldbox.type[c][0]][y+oldbox.type[c][1]]=oldbox.ch;
		}
		x=lx;
		y=ly;
		oldbox=newbox;
		newbox=Box.factoryMethod(rndm.nextInt(7));
		contentPane.repaint();
		if(localLogic(oldbox.type,x,0,y,1)){
			return true;
		}else return false;
	}
	public static void comeSide(int xi){
		if(localLogic(oldbox.type,x,xi,y,0)){
			x=x+xi;
			contentPane.repaint();
		}
	}
	public static void checkLines(){
		int ind=0;
		boolean b;
		for(int e=19;e>-1;e--){
			b=true;
			for(int c=0;c<10;c++){
				if(arr[c][e]=='w'){
					b=false;
				}
			}
			if(b==true){
				ind=ind+1;
				for(int g=e;g>0;g--){
					for(int c=0;c<10;c++){
						arr[c][g]=arr[c][g-1];
					}
				}
				for(int c=0;c<10;c++){
					arr[c][0]='w';
				}
				e++;
			}
		}
		if(ind!=0){
			if(ind==1){
				score+=5;
			}
			else if(ind==2){
				score+=11;
			}
			else if(ind==3){
				score+=18;
			}
			else if(ind==4){
				score+=25;
			}
			level=score/100+1;
			ispeed=1000-level*100+100;
		}
	}
	public static boolean localLogic(int [][]type,int lx,int cx,int ly,int cy){
		try{
			if(arr[lx+type[0][0]+cx][ly+type[0][1]+cy]=='w'){
				if(arr[lx+type[1][0]+cx][ly+type[1][1]+cy]=='w'){
					if(arr[lx+type[2][0]+cx][ly+type[2][1]+cy]=='w'){
						if(arr[lx+type[3][0]+cx][ly+type[3][1]+cy]=='w'){
							return true;
						}else return false;
					}else return false;
				}else return false;
			}else return false;
		}
		catch(ArrayIndexOutOfBoundsException aiobe){
			return false;
		}
	}
	public static void pauseGame(){
		pause=true;
		frame.setTitle("Tetris - Paused");
		t.stop();
	}
	public static void resumeGame(){
		pause=false;
		frame.setTitle("Tetris");
		t.restart();
	}
	public static void newStart(){
		frame.dispose();
		Tetris.main(null);
	}
	public static void exitGame(){
		Tetris.gameOver();
		System.exit(0);
	}
	public static void aboutMe(){
		JOptionPane.showMessageDialog(frame, "Created By Bhagyesh Sunil Patel\nEmail: gisueinc@gmail.com", "Gisue Inc...", JOptionPane.INFORMATION_MESSAGE );
	}
	public static void createNewFile() {
		File destFile = new File("tetris.bsp");
		if (!destFile.exists()) {
			try {
				destFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}