package project1.hamburger;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AccessWindow extends JFrame implements ActionListener, KeyListener {

	private Image background = new ImageIcon(getClass().getResource("/images/loginBG.png")).getImage();
	private JLabel menubar = new JLabel(new ImageIcon(getClass().getResource("/images/coke_menubar.png")));
	JLabel ipLabel, idLabel, bgLabel;
	JTextField ipTf, idTf;
	JPanel pN, pS, pInputN, pInputS, centerPanel, cnPanel, inputPanel, csPanel;
	JButton bW, bE;

	RoundedButton bAccess, disCon;

	Socket s;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	private Image screenImage;
	private Graphics screenGraphic;
	static final int WIDTH = 500, HEIGHT = 400;
	private int mouseX, mouseY;

	String ip;
	String chatId;
	int port = 6676;

	AccessWindow() {
		setUndecorated(true);

		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);

		menubar.setBounds(0, 0, 500, 30);
		menubar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				mouseX = me.getX();
				mouseY = me.getY();
			}
		});
		menubar.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				int x = me.getXOnScreen();
				int y = me.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});

		add(menubar);
		init();
	}

	private void init() {

		ipLabel = new JLabel(" IP ");
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		// ipLabel.setOpaque(true);
		// ipLabel.setBackground(new Color(255, 153, 0));
		ipLabel.setFont(new Font(null, Font.BOLD, 18));
		ipLabel.setForeground(new Color(0, 0, 0));

		ipLabel.setBounds(120, 110, 50, 30);
		ipTf = new JTextField(20);
		ipTf.setText("");

		ipTf.setBounds(170, 110, 200, 30);
		ipTf.addKeyListener(this);
		// ipTf.requestFocus();
		ipTf.setFocusable(true);
		add(ipLabel);
		add(ipTf);

		idLabel = new JLabel(" ID ");
		idLabel.setHorizontalAlignment(JLabel.CENTER);
		// idLabel.setOpaque(true);
		// idLabel.setBackground(new Color(255, 153, 0));
		idLabel.setFont(new Font(null, Font.BOLD, 18));
		idLabel.setForeground(new Color(0, 51, 102));
		idLabel.setBounds(120, 140, 50, 30);
		idTf = new JTextField(20);
		idTf.setText("");
		// idTf.setPreferredSize(new Dimension(200, 30)); // ID textfield 너비, 높이
		idTf.setBounds(170, 140, 200, 30);
		idTf.addKeyListener(this);
		add(idLabel);
		add(idTf);

		bAccess = new RoundedButton("로그인");
		bAccess.setHorizontalAlignment(RoundedButton.CENTER);
		bAccess.addActionListener(this);
		disCon = new RoundedButton("종료");
		disCon.setHorizontalAlignment(RoundedButton.CENTER);
		disCon.addActionListener(this);
		bAccess.setBounds(140, 360, 100, 30);
		disCon.setBounds(250, 360, 100, 30);
		add(bAccess);
		add(disCon);
	}

	public void paint(Graphics g) {
		screenImage = createImage(WIDTH, HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

	public void connect() { // 서버에 접속
		try {
			s = new Socket(ip, port);
			is = s.getInputStream();
			os = s.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);

			new ClientGame(this);
			ClientGUI.showMsgTa.append("서버와 연결 성공!!\n");

			this.ipTf.setEditable(false);
			this.idTf.setEditable(false);
			this.bAccess.setEnabled(false);
		} catch (UnknownHostException ue) {
			String note = ip + "가 일치하는 서버가 없습니다. 다시 입력해주세요.";
			JOptionPane.showMessageDialog(null, note, "ERROR", JOptionPane.ERROR_MESSAGE);
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(null, "서버를 찾을 수 없습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	void closeAll() {
		try {
			if (this.dis != null)
				dis.close();
			if (this.dos != null)
				dos.close();
			if (this.is != null)
				is.close();
			if (this.os != null)
				os.close();
			if (this.s != null)
				s.close();
		} catch (IOException ie) {
			ipTf.setEditable(true);
			idTf.setEditable(true);
			bAccess.setEnabled(true);
		}
	}

	public void actionPerformed(ActionEvent ae) {
		Object object = ae.getSource();
		if (object == bAccess) {
			ip = ipTf.getText();
			ip = ip.trim();
			chatId = idTf.getText();
			chatId = chatId.trim();
			if (ip.length() == 0) {
				JOptionPane.showMessageDialog(this, "접속할 서버의 IP를 꼭 입력해주세요.");
			} else if (chatId.length() == 0) {
				JOptionPane.showMessageDialog(this, "사용할 ID를 꼭 입력해주세요.");
			} else if (chatId.length() > 7) {
				JOptionPane.showMessageDialog(this, "ID는 여섯자리까지만 입력할 수 있어요.");
			} else if (ip.length() == 0 && chatId.length() == 0) {
				JOptionPane.showMessageDialog(this, "IP와 ID를 모두 입력해주세요.");
			} else {
				connect();
				try {
					dos.writeUTF(chatId);
					dos.flush();
				} catch (IOException ioexcepion) {
					JOptionPane.showMessageDialog(null, "서버에 ID 전송을 실패했습니다.");
				}
			}

		}
		if (object == disCon) {
			int select = JOptionPane.showConfirmDialog(this, "종료하시겠습니까?", "프로그램 종료", JOptionPane.OK_CANCEL_OPTION);
			if (select == JOptionPane.OK_OPTION) {
				closeAll();
				ipTf.setEditable(true);
				// ipTf.setText("");
				idTf.setEditable(true);
				// idTf.setText("");
				bAccess.setEnabled(true);
				System.exit(0);
			}
		}
	}

	public void keyPressed(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}

	public void keyReleased(KeyEvent ke) {
		String str1 = ipTf.getText();
		String str2 = idTf.getText();
		int code = ke.getKeyCode();
		// boolean b = false;
		if ((str1.length() != 0) && (code == KeyEvent.VK_ENTER)) {
			idTf.requestFocus();
		}
		if ((str2.length() != 0) && (code == KeyEvent.VK_ENTER)) {
			// getRootPane().setDefaultButton(bAccess);
			bAccess.setFocusable(true);
		}
	}
}
