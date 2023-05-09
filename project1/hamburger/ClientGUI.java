package project1.hamburger;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ClientGUI extends JFrame {
	
	private Image mainBackground = new ImageIcon(Main.class.getResource("/images/background.png")).getImage();
	private ImageIcon exitImg = new ImageIcon(Main.class.getResource("/images/exit.png"));
	private ImageIcon showBurger = new ImageIcon(Main.class.getResource("/images/showBurger.png"));
	private ImageIcon contro_intro = new ImageIcon(Main.class.getResource("/images/control.png"));
	private ImageIcon exitImgEntered = new ImageIcon(Main.class.getResource("/images/exitEntered.png"));
	
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("/images/menuBar1.jpg")));
	private ImageIcon clientBG = new ImageIcon(Main.class.getResource("/images/userFrame.png"));
	private ImageIcon scoreImage = new ImageIcon(Main.class.getResource("/images/scoreButton.png"));

	private ImageIcon readyButtonBasic = new ImageIcon(Main.class.getResource("/images/readyButtonBasic.png"));
	private ImageIcon readyButtonEntered = new ImageIcon(Main.class.getResource("/images/readyButtonEntered.png"));
	public static ImageIcon hard = new ImageIcon(Main.class.getResource("/images/hard.png"));
	public static ImageIcon hardClicked = new ImageIcon(Main.class.getResource("/images/hardClicked.png"));
	public static ImageIcon normal = new ImageIcon(Main.class.getResource("/images/normal.png"));
	public static ImageIcon normalClicked = new ImageIcon(Main.class.getResource("/images/normalClicked.png"));
	public static ImageIcon waitingBurger = new ImageIcon(Main.class.getResource("/images/waitingBurger1.png"));
	
	public static ImageIcon bbCenter = new ImageIcon(Main.class.getResource("/images/bbCenter.png"));
	public static ImageIcon ptCenter = new ImageIcon(Main.class.getResource("/images/ptCenter.png"));
	public static ImageIcon ltCenter = new ImageIcon(Main.class.getResource("/images/ltCenter.png"));
	public static ImageIcon kcCenter = new ImageIcon(Main.class.getResource("/images/kcCenter.png"));
	public static ImageIcon ccCenter = new ImageIcon(Main.class.getResource("/images/ccCenter.png"));
	public static ImageIcon tbCenter = new ImageIcon(Main.class.getResource("/images/tbCenter.png"));
	
	public static ImageIcon bbBasic = new ImageIcon(Main.class.getResource("/images/bb.png"));
	public static ImageIcon ptBasic = new ImageIcon(Main.class.getResource("/images/pt.png"));
	public static ImageIcon ltBasic = new ImageIcon(Main.class.getResource("/images/lt.png"));
	public static ImageIcon kcBasic = new ImageIcon(Main.class.getResource("/images/kc.png"));
	public static ImageIcon ccBasic = new ImageIcon(Main.class.getResource("/images/cc.png"));
	public static ImageIcon tbBasic = new ImageIcon(Main.class.getResource("/images/tb.png"));

	public static  ImageIcon bbPressed = new ImageIcon(Main.class.getResource("/images/bbPressed.png"));
	public static  ImageIcon ptPressed = new ImageIcon(Main.class.getResource("/images/ptPressed.png"));
	public static  ImageIcon ltPressed = new ImageIcon(Main.class.getResource("/images/ltPressed.png"));
	public static  ImageIcon kcPressed = new ImageIcon(Main.class.getResource("/images/kcPressed.png"));
	public static  ImageIcon ccPressed = new ImageIcon(Main.class.getResource("/images/ccPressed.png"));
	public static  ImageIcon tbPressed = new ImageIcon(Main.class.getResource("/images/tbPressed.png"));

	public static  ImageIcon bbDark = new ImageIcon(Main.class.getResource("/images/bbDark.png"));
	public static  ImageIcon ptDark = new ImageIcon(Main.class.getResource("/images/ptDark.png"));
	public static  ImageIcon ltDark = new ImageIcon(Main.class.getResource("/images/ltDark.png"));
	public static  ImageIcon kcDark = new ImageIcon(Main.class.getResource("/images/kcDark.png"));
	public static  ImageIcon ccDark = new ImageIcon(Main.class.getResource("/images/ccDark.png"));
	public static  ImageIcon tbDark = new ImageIcon(Main.class.getResource("/images/tbDark.png"));
	
	private JButton exitButton = new JButton(exitImg);
	public static JButton clearButton, musicStartButton, musicStopButton, readyButton;
	public static JButton bEasy, bNormal, bHard;
	public static JLabel timerLabel, clientBG1, clientBG2, clientBG3, clientBG4;
	public static JLabel userAvatar1, userAvatar2, userAvatar3, userAvatar4;
	public static JLabel showHamberger, testlb1, testlb2, testlb3, testlb4, testlb5, testlb6;
	public static JLabel userID1, userID2, userID3, userID4, score1, score2, score3, score4;
	public static JTextArea showMsgTa;
	public static JTextField sendMsgTf, testTA;
	public static JTextField showIDtf1, showIDtf2, showIDtf3, showIDtf4;
	public static JTextField showScore1, showScore2, showScore3, showScore4;
	public static JScrollPane scrollPane;

	public static JLabel control;
	public static JLabel burgerSlabel, burgerDlabel, burgerFlabel;
	public static JLabel burgerJlabel, burgerKlabel, burgerLlabel;
	public static JButton burgerSbutton, burgerDbutton, burgerFbutton;
	public static JButton burgerJbutton, burgerKbutton, burgerLbutton;
		
	private Image screenImage;
	private Graphics screenGraphic;

	private int mouseX, mouseY;
	
	ClientGUI(){
		setUndecorated(true);
		setSize(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		exitButton.setBounds(1250, 0, 30, 30);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				exitButton.setIcon(exitImgEntered);
			}

			@Override
			public void mouseExited(MouseEvent me) {
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				exitButton.setIcon(exitImg);
			}

			@Override
			public void mousePressed(MouseEvent me) {
				int select = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "Exit", JOptionPane.YES_NO_OPTION);
				if (select == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}

		});
		add(exitButton);

		menuBar.setBounds(0, 0, 1280, 28);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				mouseX = me.getX();
				mouseY = me.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				int x = me.getXOnScreen();
				int y = me.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
		
		initPad();
	}
	
	private void initPad() {

		// 중앙
		burgerSlabel = new JLabel(bbCenter);
		burgerSlabel.setBounds(320, 250, 206, 70);
		burgerDlabel = new JLabel(ptCenter);
		burgerDlabel.setBounds(540, 250, 206, 70);
		burgerFlabel = new JLabel(ltCenter);
		burgerFlabel.setBounds(750, 250, 206, 70);
		burgerJlabel = new JLabel(kcCenter);
		burgerJlabel.setBounds(320, 430, 206, 70);
		burgerKlabel = new JLabel(ccCenter);
		burgerKlabel.setBounds(540, 430, 206, 70);
		burgerLlabel = new JLabel(tbCenter);
		burgerLlabel.setBounds(750, 430, 206, 70);
		burgerSlabel.setVisible(false);
		burgerDlabel.setVisible(false);
		burgerFlabel.setVisible(false);
		burgerJlabel.setVisible(false);
		burgerKlabel.setVisible(false);
		burgerLlabel.setVisible(false);
		add(burgerSlabel);
		add(burgerDlabel);
		add(burgerFlabel);
		add(burgerJlabel);
		add(burgerKlabel);
		add(burgerLlabel);

		// 문제 출제 레이블
		showHamberger = new JLabel(showBurger);
		showHamberger.setOpaque(false);
		showHamberger.setBounds(1010, 120, 250, 240);
		showHamberger.setBorder(new LineBorder(new Color(51, 0, 0), 0, true));

		testlb6 = new JLabel();
		testlb6.setVisible(true);
		testlb6.setBounds(1055, 180, 150, 150);
		add(testlb6);
		add(showHamberger);

		// 상단 사용법 레이블
		control = new JLabel(contro_intro);
		control.setBounds(0, 30, 563, 70);
		control.setVisible(true);
		add(control);

		JLabel lb = new JLabel(new ImageIcon(Main.class.getResource("/images/chatBG1.jpg")));
		lb.setBounds(1010, 350, 250, 290);

		// 채팅 영역
		showMsgTa = new JTextArea();
		showMsgTa.setBorder(new LineBorder(new Color(255, 153, 102), 3, true));
		showMsgTa.setEditable(false);
		showMsgTa.setOpaque(false);
		// showMsgTa.setBackground(new Color(255, 255, 153));

		scrollPane = new JScrollPane(showMsgTa);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(new LineBorder(null, 0, false));
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(1010, 376, 250, 195);

		sendMsgTf = new JTextField(20);
		sendMsgTf.setBounds(1010, 570, 165, 30);
		sendMsgTf.setBorder(new LineBorder(new Color(255, 153, 102), 3, true));
		sendMsgTf.setFocusable(true);
		add(sendMsgTf);

		clearButton = new JButton(new ImageIcon(Main.class.getResource("/images/clear1.jpg")));
		clearButton.setSelectedIcon(new ImageIcon(Main.class.getResource("/images/clear2.jpg")));
		clearButton.setPressedIcon(new ImageIcon(Main.class.getResource("/images/clear1.jpg")));
		clearButton.setContentAreaFilled(false);
		clearButton.setBorderPainted(false);
		clearButton.setBounds(1175, 570, 85, 30);
		add(clearButton);
		add(scrollPane);
		add(lb);

		// 클리이언트 정보 영역
		clientBG1 = new JLabel(clientBG);
		clientBG1.setBounds(30, 110, 240, 125);
		clientBG2 = new JLabel(clientBG);
		clientBG2.setBounds(30, 240, 240, 125);
		clientBG3 = new JLabel(clientBG);
		clientBG3.setBounds(30, 370, 240, 125);
		clientBG4 = new JLabel(clientBG);
		clientBG4.setBounds(30, 500, 240, 125);

		// 클라이언트 아바타
		userAvatar1 = new JLabel(waitingBurger);
		userAvatar1.setBounds(105, 127, 100, 75);
		userAvatar2 = new JLabel(waitingBurger);
		userAvatar2.setBounds(105, 257, 100, 75);
		userAvatar3 = new JLabel(waitingBurger);
		userAvatar3.setBounds(105, 387, 100, 75);
		userAvatar4 = new JLabel(waitingBurger);
		userAvatar4.setBounds(105, 517, 100, 75);

		// 클라이언트1 닉네임 + 스코어
		userID1 = new JLabel("ID");
		showIDtf1 = new JTextField();
		showIDtf1.setHorizontalAlignment(SwingConstants.CENTER);
		showIDtf1.setEditable(false);
		showIDtf1.setBackground(new Color(153, 153, 153));
		score1 = new JLabel(scoreImage);
		showScore1 = new JTextField();
		showScore1.setHorizontalAlignment(SwingConstants.CENTER);
		showScore1.setEditable(false);
		showScore1.setBackground(new Color(153, 153, 153));
		userID1.setBounds(45, 202, 40, 25);
		showIDtf1.setBounds(60, 202, 90, 25);
		score1.setBounds(140, 202, 60, 25);
		showScore1.setBounds(190, 202, 60, 25);
		add(userAvatar1);
		add(userID1);
		add(showIDtf1);
		add(score1);
		add(showScore1);
		add(clientBG1);

		userID2 = new JLabel("ID");
		showIDtf2 = new JTextField();
		showIDtf2.setHorizontalAlignment(SwingConstants.CENTER);
		showIDtf2.setEditable(false);
		showIDtf2.setBackground(new Color(153, 153, 153));
		score2 = new JLabel(scoreImage);
		showScore2 = new JTextField();
		showScore2.setHorizontalAlignment(SwingConstants.CENTER);
		showScore2.setEditable(false);
		showScore2.setBackground(new Color(153, 153, 153));
		userID2.setBounds(45, 332, 40, 25);
		showIDtf2.setBounds(60, 332, 90, 25);
		score2.setBounds(140, 332, 60, 25);
		showScore2.setBounds(190, 332, 60, 25);
		add(userAvatar2);
		add(userID2);
		add(showIDtf2);
		add(score2);
		add(showScore2);
		add(clientBG2);

		userID3 = new JLabel("ID");
		showIDtf3 = new JTextField();
		showIDtf3.setHorizontalAlignment(SwingConstants.CENTER);
		showIDtf3.setEditable(false);
		showIDtf3.setBackground(new Color(153, 153, 153));
		score3 = new JLabel(scoreImage);
		showScore3 = new JTextField();
		showScore3.setHorizontalAlignment(SwingConstants.CENTER);
		showScore3.setEditable(false);
		showScore3.setBackground(new Color(153, 153, 153));
		userID3.setBounds(45, 462, 40, 25);
		showIDtf3.setBounds(60, 462, 90, 25);
		score3.setBounds(140, 462, 60, 25);
		showScore3.setBounds(190, 462, 60, 25);
		add(userAvatar3);
		add(userID3);
		add(showIDtf3);
		add(score3);
		add(showScore3);
		add(clientBG3);

		userID4 = new JLabel("ID");
		showIDtf4 = new JTextField();
		showIDtf4.setHorizontalAlignment(SwingConstants.CENTER);
		showIDtf4.setEditable(false);
		showIDtf4.setBackground(new Color(153, 153, 153));
		score4 = new JLabel(scoreImage);
		showScore4 = new JTextField();
		showScore4.setHorizontalAlignment(SwingConstants.CENTER);
		showScore4.setEditable(false);
		showScore4.setBackground(new Color(153, 153, 153));
		userID4.setBounds(45, 592, 40, 25);
		showIDtf4.setBounds(60, 592, 90, 25);
		score4.setBounds(140, 592, 60, 25);
		showScore4.setBounds(190, 592, 60, 25);
		add(userAvatar4);
		add(userID4);
		add(showIDtf4);
		add(score4);
		add(showScore4);
		add(clientBG4);

		// 하단부 버거버튼
		burgerSbutton = new JButton(bbBasic);
		burgerDbutton = new JButton(ptBasic);
		burgerFbutton = new JButton(ltBasic);
		burgerJbutton = new JButton(kcBasic);
		burgerKbutton = new JButton(ccBasic);
		burgerLbutton = new JButton(tbBasic);
		burgerSbutton.setBorderPainted(false);
		burgerSbutton.setContentAreaFilled(false);
		burgerSbutton.setFocusPainted(false);
		burgerDbutton.setBorderPainted(false);
		burgerDbutton.setContentAreaFilled(false);
		burgerDbutton.setFocusPainted(false);
		burgerFbutton.setBorderPainted(false);
		burgerFbutton.setContentAreaFilled(false);
		burgerFbutton.setFocusPainted(false);
		burgerJbutton.setBorderPainted(false);
		burgerJbutton.setContentAreaFilled(false);
		burgerJbutton.setFocusPainted(false);
		burgerKbutton.setBorderPainted(false);
		burgerKbutton.setContentAreaFilled(false);
		burgerKbutton.setFocusPainted(false);
		burgerLbutton.setBorderPainted(false);
		burgerLbutton.setContentAreaFilled(false);
		burgerLbutton.setFocusPainted(false);
		burgerSbutton.setBounds(130, 625, 160, 80);
		burgerDbutton.setBounds(310, 625, 160, 80);
		burgerFbutton.setBounds(490, 625, 160, 80);
		burgerJbutton.setBounds(670, 625, 160, 80);
		burgerKbutton.setBounds(850, 625, 160, 80);
		burgerLbutton.setBounds(1030, 625, 160, 80);
		add(burgerSbutton);
		add(burgerDbutton);
		add(burgerFbutton);
		add(burgerJbutton);
		add(burgerKbutton);
		add(burgerLbutton);

		// 상단 타이머
		JLabel timerBG = new JLabel(new ImageIcon(Main.class.getResource("/images/timer2.png")));
		timerBG.setOpaque(true);
		timerLabel = new JLabel(" 00 : 00 ");
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setFont(new Font("나눔고딕", Font.BOLD, 30));
		timerLabel.setBounds(585, 35, 120, 76);
		timerBG.setBounds(585, 35, 120, 76);
		add(timerLabel);
		add(timerBG);

		// readyButton
		readyButton = new JButton(readyButtonBasic);
		readyButton.setBounds(940, 45, 100, 60);
		readyButton.setBorderPainted(false);
		readyButton.setContentAreaFilled(false);
		readyButton.setFocusPainted(false);
		readyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				readyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				readyButton.setIcon(readyButtonEntered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				readyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				readyButton.setIcon(readyButtonBasic);
			}
		});
		add(readyButton);

		bNormal = new JButton(normal);
		bNormal.setBounds(1050, 45, 100, 47);
		bNormal.setBorderPainted(false);
		bNormal.setContentAreaFilled(false);
		bNormal.setFocusPainted(false);
		bNormal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bNormal.setCursor(new Cursor(Cursor.HAND_CURSOR));
				bNormal.setIcon(normalClicked);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bNormal.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				bNormal.setIcon(normal);
			}
		});
		
		bHard = new JButton(hard);
		bHard.setBounds(1160, 45, 100, 47);
		bHard.setBorderPainted(false);
		bHard.setContentAreaFilled(false);
		bHard.setFocusPainted(false);
		bHard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bHard.setCursor(new Cursor(Cursor.HAND_CURSOR));
				bHard.setIcon(hardClicked);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bHard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				bHard.setIcon(hard);
			}

		});
		
		add(bHard);
		add(bNormal);
		
		// keyCode test
		testTA = new JTextField();
		testTA.setBounds(500, 450, 300, 100);
		// add(testTA);

	}
	public void paint(Graphics g) {
		screenImage = createImage(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(mainBackground, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}
}
