import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

class Hw4Button extends JButton{
	public Hw4Button(String string) {
		super(string);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		int w = size.width;
		int h = size.height;
		Graphics2D g2 = (Graphics2D) g;

		if((this.getText()).length()>0) {
			g2.setColor(new Color(230,220,255));
			g2.fillRoundRect(10, 10, w-20, h-20, 20, 20);
		}


		Font font = new Font("Arial",Font.BOLD, (int)(h*0.6));
		g.setFont(font);
		font = g.getFont();
        FontMetrics metrics = g.getFontMetrics(font);
        int font_width = metrics.stringWidth(this.getText());
        int font_height = metrics.getAscent();
        g.setColor(new Color(30,30,70));
        if((this.getText()).equals("C")) {
        	g.setColor(new Color(200,100,100));
        }

		g.drawString(this.getText(),(w-font_width)/2,(h+font_height)/2);
		repaint();
	}
}

class Hw4ResultPanel extends JPanel{
	JLabel la;
	Hw4ResultPanel(String str){
		la = new JLabel(str);
		la.setVisible(false);
		la.setFocusable(false);//key입력 받지 않음 
		add(la);
	}
	public void setText(String str){
		la.setText(str);
		
	}
	public String getText() {
		return la.getText();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		int w = size.width;
		int h = size.height;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.WHITE);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.40F));
		g2.fillRoundRect(15, 15, w-30, h-30, 20, 20);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		Font font = new Font("Arial", Font.BOLD, h/2);
		g.setFont(font);
		g.setColor(new Color(30,30,70));

		font = g.getFont();
        FontMetrics metrics = g.getFontMetrics(font);
        int font_width = metrics.stringWidth(la.getText());

        g.drawString(la.getText(),w-30-font_width,h-30);
		repaint();
	}
}


class Hw4Panel extends JPanel {
	
	Hw4ResultPanel result;
	Hw4Button buttons[] = new Hw4Button[16];
	String [] str = {"7","8","9","C","4","5","6","+","1","2","3","-","0","","","="};
	int op=0; // 연산자 
	int temp=0; // 이전 값 
	int	flag=0; //입력받을 때
	
	Hw4Panel(){
		result = new Hw4ResultPanel("0");
		add(result); // 패널에 결과값을 출력하는 패널 추가 
		for(int i =0; i<16;i++) {
			buttons[i]=new Hw4Button(str[i]);
			add(buttons[i]); //버튼 추가 
			buttons[i].addActionListener(new Hw4Listener());
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension size = this.getSize();
		int w =size.width; 
		int h =size.height;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, size.width, size.height);
		// JFRame 검정색으로 색칠 
		int startx=0;
		int starty=0;
		if(w>h) {
			w=h/4*3;
			startx = (size.width - w)/2;
			starty = 0;
		}
		else {
			h=w/3*4;
			startx = 0;
			starty = (size.height - h)/2;
		}
		
		for(int i = 0; i<=31; i++)
		{ //패널에 그라데이션 
			g.setColor(new Color(160+(i*3),160+(i*2),255));
			g.fillRect(startx, starty+i*(h/30), w, h/30);
		}
		
		float buttonw = w/4.0F; //버튼 하나의 넓이 
		float buttonh = h/6.0F; //버튼 하나의 높이 
		result.setOpaque(false);
		result.setBackground(new Color(255,255,255,255));
		result.setBounds(startx, starty, w, (int)(buttonh*2));
		result.repaint();
		
		int buttonx = startx;
		int buttony = starty+(int)(buttonh*2);
		int x=0,y=0,index=0;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				x=buttonx+(int)(buttonw*j);
				y=buttony+(int)(buttonh*i);
				buttons[index].setBounds(x+2, y+2, (int)buttonw-2, (int)(buttonh)-2);
				buttons[index].setOpaque(false);
				buttons[index].setBackground(new Color(255,255,255,255));
				buttons[index].repaint();
				index++;
			}
		}
		
	}
	
	private class Hw4Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String eText = e.getActionCommand();//버튼값을 string으로 받음 
			if(eText.equals("C")) {
				result.setText("0");
				op=0;
				temp=0;
				flag=0;
			}
			else if(eText.equals("+")) {
				if(op==1) {
					result.setText(""+(temp + Integer.parseInt(result.getText())));
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=1;
					flag=0;
				}
				else if(op==2) {
					result.setText(""+(temp - Integer.parseInt(result.getText())));
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=1;
					flag=0;
				}
				else {
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=1;
					flag=0;
				}
			}
			else if(eText.equals("-")) {
				if(op==1) {
					result.setText(""+(temp + Integer.parseInt(result.getText())));
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=2;
					flag=0;
				}
				else if(op==2) {
					result.setText(""+(temp - Integer.parseInt(result.getText())));
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=2;
					flag=0;
				}
				else {
					temp = Integer.parseInt(result.getText()); // 현재 값을 저장 
					op=2;
					flag=0;
				}
			}
			else if(eText.equals("=")) {
				if(op==1) {
					result.setText(""+(temp + Integer.parseInt(result.getText())));
					flag=0;
					op=0;
				}
				else if(op==2) {
					result.setText(""+(temp - Integer.parseInt(result.getText())));
					flag=0;
					op=0;
				}
			}
			else { // 숫자를 입력 받으면 
				if(flag==0) {
					result.setText(eText);
					flag=1;
				}
				else {
					result.setText(result.getText()+eText);
				}
				
			}
		}
	}

	
}


public class Hw4 extends JFrame{
	
	public static void main(String[] args) {
		//LookAndFeel변경 
		try {
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		 } catch (Exception e) {
		            e.printStackTrace();
		 }
		
		new Hw4();
		
	}
	Hw4(){
		setTitle("Homework2");
		setSize(500,500);
		Hw4Panel hPanel = new Hw4Panel();
		add(hPanel);	
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
}
