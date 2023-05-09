package project1.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class GameServer extends JFrame implements ActionListener, KeyListener {

	Container container;

	ImagePanel backgroundImage;
	JPanel mainPanel, panel_Buttom, topPanel, buttomEastPn;
	ImagePanel westPanel;
	JLabel titleLabel, serverStatusLabel, clientLabel;
	JTextArea showMsgs, showChatId;
	JTextField sendMsgs;
	JScrollPane showMsgsScrollpn, sendMsgsScrollpn;
	JButton bStart, bDisconn, bExit;
	JButton bOption;

	public static final int WINDOW_WIDTH = 600;
	public static final int WINDOW_HEIGHT = 450;
	public final static int MAX_CLIENT = 4; // 최대 참여자수
	private int mouseX, mouseY;

	ServerSocket ss;
	Socket s;
	int port = 6676;

	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	int readyPlayer;
	int score;
	boolean running;
	static int minute = 2, second = 40; // 타이머 분 : 초

	private Image screenImage;
	private Graphics screenGraphic;
	private Image bgImage = new ImageIcon(GameServer.class.getResource("/images/bgImage.jpg")).getImage();
	ImageIcon startImg = new ImageIcon(GameServer.class.getResource("/images/startButton.png"));
	ImageIcon startImgEntered = new ImageIcon(GameServer.class.getResource("/images/startButtonEntered.png"));
	ImageIcon disconnImg = new ImageIcon(GameServer.class.getResource("/images/disconnButton.png"));
	ImageIcon disconnImgEntered = new ImageIcon(GameServer.class.getResource("/images/disconnButtonEntered.png"));
	ImageIcon exitImg = new ImageIcon(GameServer.class.getResource("/images/blueExitButton.png"));
	ImageIcon exitImgEntered = new ImageIcon(GameServer.class.getResource("/images/blueExitButtonEntered.png"));

	LinkedHashMap<String, DataOutputStream> clientLists = new LinkedHashMap<String, DataOutputStream>();
	LinkedHashMap<String, Integer> clientInfos = new LinkedHashMap<String, Integer>();

	public void initFrame() {
		container = getContentPane();
		container.setLayout(new BorderLayout());

		bStart = new JButton(startImg);
		bStart.setBounds(435, 0, 60, 50);
		bStart.setBorderPainted(false);
		bStart.setContentAreaFilled(false);
		bDisconn = new JButton(disconnImg);
		bDisconn.setBounds(490, 0, 60, 50);
		bDisconn.setBorderPainted(false);
		bDisconn.setContentAreaFilled(false);
		bDisconn.setEnabled(false);
		bExit = new JButton(exitImg);
		bExit.setBounds(540, 0, 60, 50);
		bExit.setBorderPainted(false);
		bExit.setContentAreaFilled(false);
		bStart.addActionListener(this);
		bDisconn.addActionListener(this);
		bExit.addActionListener(this);
		bStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bStart.setCursor(new Cursor(Cursor.HAND_CURSOR));
				bStart.setIcon(startImgEntered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bStart.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				bStart.setIcon(startImg);
			}
		});
		bDisconn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bDisconn.setCursor(new Cursor(Cursor.HAND_CURSOR));
				bDisconn.setIcon(disconnImgEntered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bDisconn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				bDisconn.setIcon(disconnImg);
			}
		});
		bExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				bExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
				bExit.setIcon(exitImgEntered);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				bExit.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				bExit.setIcon(exitImg);
			}
		});
		add(bStart);
		add(bDisconn);
		add(bExit);
		serverStatusLabel = new JLabel("[ Server Closed ]");
		serverStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		serverStatusLabel.setForeground(new Color(0, 0, 205));
		serverStatusLabel.setFont(new Font(null, Font.BOLD, 18));
		serverStatusLabel.setBounds(20, 370, 200, 100);
		add(serverStatusLabel);
		/*
		clientLabel = new JLabel(" [ 접속자 현황 ]");
		clientLabel.setForeground(new Color(255, 255, 204));
		showChatId = new JTextArea();
		showChatId.setFont(new Font(null, Font.BOLD, 14));
		showChatId.setEditable(false);
		clientLabel.setBounds(20, 280, 180, 100);
		showChatId.setBounds(20, 340, 180, 100);
		add(clientLabel);
		add(showChatId);
		*/
		showMsgs = new JTextArea();
		showMsgs.setBackground(new Color(0, 51, 102));
		showMsgs.setForeground(new Color(255, 204, 0));
		showMsgs.setFont(new Font(null, Font.PLAIN, 13));
		showMsgs.setEditable(false);
		showMsgs.setCaretPosition(showMsgs.getDocument().getLength());
		showMsgsScrollpn = new JScrollPane(showMsgs);
		showMsgsScrollpn.setBorder(new LineBorder(new Color(0, 0, 0), 0, true));

		sendMsgs = new JTextField();
		sendMsgs.setPreferredSize(new Dimension(380, 50));
		sendMsgs.setFont(new Font(null, Font.PLAIN, 14));
		sendMsgs.addKeyListener(this);

		panel_Buttom = new JPanel();
		panel_Buttom.setLayout(new BorderLayout());
		buttomEastPn = new JPanel();
		buttomEastPn.setLayout(new BorderLayout());
		buttomEastPn.add(showMsgsScrollpn, "Center");
		buttomEastPn.add(sendMsgs, "South");
		panel_Buttom.add(buttomEastPn, "East");

		topPanel = new JPanel();
		titleLabel = new JLabel("Hammelier Server");
		titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font(null, Font.BOLD, 20));
		topPanel.setPreferredSize(new Dimension(590, 50));
		// topPanel.setBackground(new Color(255, 255, 255));
		topPanel.add(titleLabel);
		topPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		topPanel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});

		westPanel = new ImagePanel(bgImage);
		westPanel.setPreferredSize(new Dimension(210, 400));
		container.add(topPanel, BorderLayout.NORTH);
		container.add(westPanel, BorderLayout.WEST);
		container.add(panel_Buttom, BorderLayout.CENTER);
		setFrame();
	}

	public void setFrame() {
		setUndecorated(true);
		setTitle("Hammelier Server");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0));

	}

	public void keyPressed(KeyEvent ke) {
	}

	public void keyTyped(KeyEvent ke) {
	}

	public void keyReleased(KeyEvent ke) {
		int code = ke.getKeyCode();
		// boolean b = false;
		if (code == KeyEvent.VK_ENTER) {
			String msg = sendMsgs.getText();
			msg = msg.trim();
			showMsgs.append("관리자>> " + msg + "\n");
			broadcastMsg("관리자>> " + msg);
			sendMsgs.setText("");
			sendMsgs.requestFocus();
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == bStart) {
			new Thread() {
				public void run() {
					try {
						Collections.synchronizedMap(clientLists); // HashMap의 비동기화 문제(데이터 무결성 문제) 해결
						ss = new ServerSocket(port);
						serverStatusLabel.setText("[ Server Started ]");
						serverStatusLabel.setForeground(new Color(255, 0, 0));
						showMsgs.append("서버가 시작되었습니다.\n" + "클라이언트의 접속을 기다리는 중입니다.\n\n");
						bStart.setEnabled(false);
						bDisconn.setEnabled(true);
						while (true) {
							s = ss.accept();
							if ((clientLists.size() + 1) > MAX_CLIENT || running == true) {
								s.close();
							} else {
								Thread gManager = new GameManager(s);
								gManager.start();
							}
						}
					} catch (IOException ie) {
					}
				}
			}.start();
		} else if (obj == bDisconn) {
			int select = JOptionPane.showConfirmDialog(null, "정말 연결을 끊으시겟습니까?", "Hammelier Server",
					JOptionPane.YES_NO_OPTION);
			try {
				if (select == JOptionPane.YES_OPTION) {
					// closeAll();
					ss.close();
					serverStatusLabel.setText("[ Server Closed ]");
					serverStatusLabel.setForeground(new Color(0, 0, 204));
					showMsgs.append("서버가 종료되었습니다.\n");
					bStart.setEnabled(true);
					bDisconn.setEnabled(false);
				}
			} catch (IOException ioexception) {
			}
		} else if (obj == bExit) {
			int select = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "Hammelier Server",
					JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.YES_OPTION) {
				closeAll();
				System.exit(0);
			}
		}
	}

	public void broadcastMsg(String msg) {
		Iterator<String> iterator = clientLists.keySet().iterator();
		while (iterator.hasNext()) {
			try {
				dos = clientLists.get(iterator.next()); // HashMap에 저장된 Value(DataOutputStream)를 가져온다
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException io) {
			}
		}
	}

	public class GameManager extends Thread {
		Socket s;
		DataInputStream dis;
		DataOutputStream dos;
		String clientId;
		public GameManager(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(this.s.getInputStream());
				dos = new DataOutputStream(this.s.getOutputStream());
				this.clientId = clientId;
			} catch (IOException ie) {
			}
		}

		public void run() {
			String clientId = "";
			String notice1 = "";

			int count = 0;
			try {
				clientId = dis.readUTF();
				if (!clientLists.containsKey(clientId)) { // 동일 아이디 검사
					clientLists.put(clientId, dos);
					clientInfos.put(clientId, score);
				} else if (clientLists.containsKey(clientId)) {
					s.close();
				}
				count = clientLists.size();
				;
				notice1 = clientId + "님이 입장했습니다.(현재 " + count + "명 접속 중)\n";
				broadcastMsg(notice1);
				showMsgs.append(notice1);
				/*
				 * Iterator<String> iterator = clientLists.keySet().iterator();
				 * while(iterator.hasNext()) showMsgs.append(iterator.next() + "\n");
				 */
				int i = showMsgs.getText().length();
				showMsgs.setCaretPosition(i);
				setClientInfo();
				while (dis != null) {
					String msg = dis.readUTF();
					// broadcastMsg(msg);
					filter(msg);
				}
			} catch (IOException ie) {
				clientLists.remove(clientId);
				clientInfos.remove(clientId);
				int k = clientLists.size();
				String notice2 = clientId + "님이 퇴장했습니다.(현재 " + k + "명 접속중)\n";
				broadcastMsg(notice2);
				showMsgs.append(notice2 + "\n");
				int j = showMsgs.getText().length();
				showMsgs.setCaretPosition(j);
				setClientInfo();
				readyPlayer = 0;
				running = false;
				broadcastMsg("<GameEnd>");
			}
		}

		public void setClientInfo() {
			String[] keys = new String[clientInfos.size()];
			int[] values = new int[clientInfos.size()];
			int index = 0;
			for (Map.Entry<String, Integer> entry : clientInfos.entrySet()) {
				keys[index] = entry.getKey();
				values[index] = entry.getValue();
				index++;
			}

			for (int i = 0; i < clientLists.size(); i++) {
				broadcastMsg("<CList>" + keys[i] + " " + values[i] + "#" + i);

			}
		}

		public void filter(String msg) {
			if ((msg.indexOf(" ")) != -1) {
				int k = msg.indexOf(" ");
				String line = msg.substring(0, k);
				line = line.trim();
				if (line.equals("<Chat>")) {
					String msg1 = msg.substring(k + 1).trim();
					broadcastMsg(msg1);
					showMsgs.append(msg1 + "\n");
					int j = showMsgs.getText().length();
					showMsgs.setCaretPosition(j);
				} else if (line.equals("<Ready>")) {
					readyPlayer++;
					if (readyPlayer >= 2 && readyPlayer == clientLists.size()) {
						broadcastMsg("<< 모든 유저들이 준비를 완료했습니다. >>\n");
						for (int i = 3; i > 0; i--) {
																					try {
								broadcastMsg(i + "초 후에 게임을 시작합니다.");

								Thread.sleep(1000);
							} catch (InterruptedException ire) {
							}
						}
						StopWatch sw = new StopWatch();
						sw.start();
						running = true;
						broadcastMsg("<Start>");
					}
				} else if (line.equals("<GameEnd>")) {
					broadcastMsg("<< 게임 종료 >>");
					// broadcastMsg(msg);
					readyPlayer = 0;
					running = false;
				} else if (line.equals("<Serving>")) {
					answerCheck(msg);
				} else if (line.equals("<normal>")) {
					String msg2 = msg.substring(k+1).trim();
					broadcastMsg(msg2);
					broadcastMsg("<normal>");
					showMsgs.append(msg2 + "\n");
					int j = showMsgs.getText().length();
					showMsgs.setCaretPosition(j);
					minute = 2; second = 40;
				} else if(line.equals("<hard>")) {
					String msg3 = msg.substring(k+1).trim();
					broadcastMsg(msg3);
					broadcastMsg("<hard>");
					showMsgs.append(msg3 + "\n");
					int j = showMsgs.getText().length();
					showMsgs.setCaretPosition(j);
					minute = 1; second = 60;
				} else if (line.equals("<Exit> ")) {
					clientLists.remove(clientId);
					clientInfos.remove(clientId);
					int count = clientLists.size();
					String notice2 = clientId + "님이 퇴장했습니다.(현재 " + count + "명 접속중)\n";
					broadcastMsg(notice2);
					showMsgs.append(notice2 + "\n");
					int j = showMsgs.getText().length();
					showMsgs.setCaretPosition(j);
					setClientInfo();
					readyPlayer = 0;
					running = false;
					broadcastMsg("<GameEnd>");
				}
			} else {
				// broadcastMsg(msg);
			}
		}

		public void answerCheck(String msg) { // 정답 체크
			int score = 1000;
			String code = msg.substring(10, msg.lastIndexOf(" "));
			// System.out.println(code);
			String tempId = msg.substring(msg.lastIndexOf(" ") + 1);
			// System.out.println(tempId);
			if (code.startsWith("bcklpt")) { //
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");
				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo(); // 점수 표시를 위한 클라이언트 목록 갱신
			} else if (code.startsWith("bcllkt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bcpcpt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bcpct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("blcpct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("blklkt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("blkppt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("blpkct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bpkclt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bppppt")) {
				broadcastMsg("<Success> [ " + tempId + "님 쌓기 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bcccct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bccclt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bclkkt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bkccct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bkkkkt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bkklkt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("blklct")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bllllt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bpkpt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else if (code.startsWith("bppllt")) {
				broadcastMsg("<Success> [ " + tempId + "님 서빙 성공!! ]\n");

				clientInfos.put(tempId, clientInfos.get(tempId) + score); // 정답자 점수 추가
				setClientInfo();
			} else {
				broadcastMsg("<Fail> [ " + tempId + "님 서빙 실패!! ]\n");
			}
		}

	}

	// 내부 클래스 - 타이머
	class StopWatch extends Thread {
		long startTime = System.currentTimeMillis(); // millisecond: 1/1000초

		public void run() {
			try {
				while (running == true) {
					sleep(10);
					long time = System.currentTimeMillis() - startTime;
					broadcastMsg("<Timer>" + (toTime(time)));
					// System.out.println((toTime(time)));
					if (toTime(time).equals("00 : 00")) {
						broadcastMsg("<GameEnd>"); // 시간 초과시, 게임 종료
						readyPlayer = 0;
						running = false;
						break;
					} else if (readyPlayer == 0) {
						break;
					}
				}
			} catch (Exception e) {
			}
		}

		String toTime(long time) {
			int m = (int)(minute - (time / 1000.0 / 60.0 ));
			int s = (int)(second - (time % (1000.0 * 60.0) / 1000.0));
			return String.format("%02d : %02d", m, s);
		}
	}

	public void closeAll() {
		try {
			if (dis != null)
				dis.close();
			if (dos != null)
				dos.close();
			if (is != null)
				is.close();
			if (os != null)
				os.close();
			if (s != null)
				s.close();
		} catch (IOException ie) {
		}
	}

	void pln(String str) {
		System.out.println(str);
	}

	void p(String str) {
		System.out.print(str);
	}

	public void paint(Graphics g) {
		screenImage = createImage(WINDOW_WIDTH, WINDOW_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(bgImage, 0, 0, null);
		paintComponents(g);
		this.repaint();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GameServer gs = new GameServer();
					gs.initFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}