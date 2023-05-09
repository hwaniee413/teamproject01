package project1.hamburger;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import javax.print.attribute.standard.Media;
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

public class ClientGame extends JFrame {
	AccessWindow accWindow;

	private ImageIcon bcklpt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bcklpt.png"));
	private ImageIcon bcllkt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bcllkt.png"));
	private ImageIcon bcpcpt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bcpcpt.png"));
	private ImageIcon bcpct = new ImageIcon(getClass().getResource("/images/burgerKeycode/bcpct.png"));
	private ImageIcon blcpct = new ImageIcon(getClass().getResource("/images/burgerKeycode/blcpct.png"));
	private ImageIcon blklkt = new ImageIcon(getClass().getResource("/images/burgerKeycode/blklkt.png"));
	private ImageIcon blkppt = new ImageIcon(getClass().getResource("/images/burgerKeycode/blkppt.png"));
	private ImageIcon blpkct = new ImageIcon(getClass().getResource("/images/burgerKeycode/blpkct.png"));
	private ImageIcon bpkclt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bpkclt.png"));
	private ImageIcon bppppt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bppppt.png"));
	private ImageIcon bcccct = new ImageIcon(getClass().getResource("/images/burgerKeycode/bcccct.png"));
	private ImageIcon bccclt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bccclt.png"));
	private ImageIcon bclkkt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bclkkt.png"));
	private ImageIcon bkccct = new ImageIcon(getClass().getResource("/images/burgerKeycode/bkccct.png"));
	private ImageIcon bkkkkt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bkkkkt.png"));
	private ImageIcon bkklkt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bkklkt.png"));
	private ImageIcon blklct = new ImageIcon(getClass().getResource("/images/burgerKeycode/blklct.png"));
	private ImageIcon bllllt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bllllt.png"));
	private ImageIcon bpkpt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bpkpt.png"));
	private ImageIcon bppllt = new ImageIcon(getClass().getResource("/images/burgerKeycode/bppllt.png"));

	Socket s;
	Thread th1, th2;
	Runnable r1, r2;

	static String keyCode = "";
	private String playerName, playerIdx, playerScore;
	private boolean gamePlay;
	int sleepTime = 10000;
	int limit = 10;  // 햄버거 랜덤 출제 갯수
	Music intromusic, playmusic, secondmusic;

	public ClientGame(AccessWindow accWindow) {
		this.accWindow = accWindow;
		new ClientGUI();

		insertHamburger();
		th1 = new Receiver();
		th1.start();
		th2 = new Sender();
		th2.start();

		intromusic = new Music("intromusic.mp3", true);
		intromusic.start();
		// playMusic.start();
	}

	class Sender extends Thread implements ActionListener, KeyListener {
		Socket s;
		DataOutputStream dos;
		String chatId;

		Sender() {
			this.s = accWindow.s;
			try {
				ClientGUI.clearButton.addActionListener(this);
				ClientGUI.readyButton.addActionListener(this);
				ClientGUI.bNormal.addActionListener(this);
				ClientGUI.bHard.addActionListener(this);
				ClientGUI.sendMsgTf.addKeyListener(this);
				dos = new DataOutputStream(s.getOutputStream());
				this.chatId = accWindow.chatId;
			} catch (IOException ie) {
			}
		}

		public void run() {

			ClientGUI.burgerSbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerSbutton.requestFocus();
				}
			});
			ClientGUI.burgerDbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerDbutton.requestFocus();
				}
			});
			ClientGUI.burgerFbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerFbutton.requestFocus();
				}
			});
			ClientGUI.burgerJbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerJbutton.requestFocus();
				}
			});
			ClientGUI.burgerKbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerKbutton.requestFocus();
				}
			});
			ClientGUI.burgerLbutton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					ClientGUI.burgerLbutton.requestFocus();
				}
			});
			// 키이벤트
			ClientGUI.burgerSbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerLbutton.requestFocus();
						ClientGUI.burgerSbutton.setIcon(ClientGUI.bbBasic);
						ClientGUI.burgerLbutton.setIcon(ClientGUI.tbPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerDbutton.requestFocus();
						ClientGUI.burgerSbutton.setIcon(ClientGUI.bbBasic);
						ClientGUI.burgerDbutton.setIcon(ClientGUI.ptPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerSlabel.setIcon(ClientGUI.bbDark);
						keyCode = keyCode + "b";
						ClientGUI.testTA.setText(keyCode);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerSlabel.setIcon(ClientGUI.bbCenter);
					}
				}
			});
			ClientGUI.burgerDbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerSbutton.requestFocus();
						ClientGUI.burgerDbutton.setIcon(ClientGUI.ptBasic);
						ClientGUI.burgerSbutton.setIcon(ClientGUI.bbPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerFbutton.requestFocus();
						ClientGUI.burgerDbutton.setIcon(ClientGUI.ptBasic);
						ClientGUI.burgerFbutton.setIcon(ClientGUI.ltPressed);

					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyCode = keyCode + "p";
						ClientGUI.testTA.setText(keyCode);
						ClientGUI.burgerDlabel.setIcon(ClientGUI.ptDark);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerDlabel.setIcon(ClientGUI.ptCenter);
					}
				}
			});
			ClientGUI.burgerFbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerDbutton.requestFocus();
						ClientGUI.burgerFbutton.setIcon(ClientGUI.ltBasic);
						ClientGUI.burgerDbutton.setIcon(ClientGUI.ptPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerJbutton.requestFocus();
						ClientGUI.burgerFbutton.setIcon(ClientGUI.ltBasic);
						ClientGUI.burgerJbutton.setIcon(ClientGUI.kcPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyCode = keyCode + "l";
						ClientGUI.testTA.setText(keyCode);
						ClientGUI.burgerFlabel.setIcon(ClientGUI.ltDark);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerFlabel.setIcon(ClientGUI.ltCenter);
					}
				}
			});
			ClientGUI.burgerJbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerFbutton.requestFocus();
						ClientGUI.burgerJbutton.setIcon(ClientGUI.kcBasic);
						ClientGUI.burgerFbutton.setIcon(ClientGUI.ltPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerKbutton.requestFocus();
						ClientGUI.burgerJbutton.setIcon(ClientGUI.kcBasic);
						ClientGUI.burgerKbutton.setIcon(ClientGUI.ccPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyCode = keyCode + "k";
						ClientGUI.testTA.setText(keyCode);
						ClientGUI.burgerJlabel.setIcon(ClientGUI.kcDark);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerJlabel.setIcon(ClientGUI.kcCenter);
					}
				}
			});
			ClientGUI.burgerKbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerJbutton.requestFocus();
						ClientGUI.burgerKbutton.setIcon(ClientGUI.ccBasic);
						ClientGUI.burgerJbutton.setIcon(ClientGUI.kcPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerLbutton.requestFocus();
						ClientGUI.burgerKbutton.setIcon(ClientGUI.ccBasic);
						ClientGUI.burgerLbutton.setIcon(ClientGUI.tbPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyCode = keyCode + "c";
						ClientGUI.testTA.setText(keyCode);
						ClientGUI.burgerKlabel.setIcon(ClientGUI.ccDark);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerKlabel.setIcon(ClientGUI.ccCenter);
					}
				}
			});
			ClientGUI.burgerLbutton.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						ClientGUI.burgerKbutton.requestFocus();
						ClientGUI.burgerLbutton.setIcon(ClientGUI.tbBasic);
						ClientGUI.burgerKbutton.setIcon(ClientGUI.ccPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						ClientGUI.burgerSbutton.requestFocus();
						ClientGUI.burgerLbutton.setIcon(ClientGUI.tbBasic);
						ClientGUI.burgerSbutton.setIcon(ClientGUI.bbPressed);
					} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						keyCode = keyCode + "t";
						ClientGUI.testTA.setText(keyCode);
						ClientGUI.burgerLlabel.setIcon(ClientGUI.tbDark);
					} else if (e.getKeyCode() == KeyEvent.VK_UP) {
						try {
							dos.writeUTF("<Serving> " + ClientGUI.testTA.getText() + " " + chatId);
							dos.flush();
							ClientGUI.testTA.setText("");
							keyCode = "";
						} catch (IOException ie) {
						}
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_SPACE) {
						ClientGUI.burgerLlabel.setIcon(ClientGUI.tbCenter);
					}
				}
			});
			try {
				Thread.sleep(20);
			} catch (InterruptedException ire) {
			}

		}

		public void actionPerformed(ActionEvent ae) {
			Object obj = ae.getSource();
			if (obj == ClientGUI.clearButton) {
				ClientGUI.showMsgTa.setText("");
			} else if (obj == ClientGUI.readyButton) {
				try {
					keyCode = "";
					ClientGUI.timerLabel.setText(" 00 : 00 ");
					String msg = "<Chat> << " + chatId + "님 준비 완료!! >>\n";
					dos.writeUTF(msg);
					dos.flush();
					dos.writeUTF("<Ready> ");
					dos.flush();
					ClientGUI.readyButton.setEnabled(false);
					ClientGUI.bNormal.setEnabled(false);
					ClientGUI.bHard.setEnabled(false);
					
				} catch (IOException ie) {
				}
			} else if (obj == ClientGUI.bNormal) {
				try {
					String msg = "<normal> << " + chatId + "님이 게임난이도를 Normal로 설정했습니다! >>\n 이제 햄버거 주문이 10초마다 들어옵니다!";
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException ie) {
				}
			} else if (obj == ClientGUI.bHard) {
				try {
					String msg = "<hard> << " + chatId + "님이 게임난이도를 Hard로 설정했습니다! >>\n 이제 햄버거 주문이 6초마다 들어옵니다!";
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException ie) {
				}
			}
		}

		public void keyTyped(KeyEvent ke) {
		}

		public void keyPressed(KeyEvent ke) {
		}

		public void keyReleased(KeyEvent ke) {
			int code = ke.getKeyCode();
			if (code == KeyEvent.VK_ENTER) {
				String msg = "<Chat> " + chatId + ">> " + ClientGUI.sendMsgTf.getText();
				// chatMsg = chatMsg.trim();
				try {
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException ie) {
					JOptionPane.showMessageDialog(null, "메세지 전송을 실패했습니다.\n 서버 상태를 확인하세요", "메시지 전송 오류", 1);
				}
				ClientGUI.sendMsgTf.setText("");
			}
		}
	}

	// HashMap<Integer, String> idLists = new HashMap<Integer, String>();
	public class Receiver extends Thread { // 듣기
		Socket s;
		DataInputStream dis;

		Receiver() {
			this.s = accWindow.s;

			try {
				dis = new DataInputStream(s.getInputStream());
			} catch (IOException ie) {
			}
		}

		public void run() {
			while (dis != null) {
				try {
					String msg = dis.readUTF();
					if (msg.startsWith("<CList>")) {
						playerName = msg.substring(7, msg.indexOf(" "));
						playerScore = msg.substring(msg.indexOf(" ") + 1, msg.indexOf("#"));
						// playerScore = playerScore.trim();
						playerIdx = msg.substring(msg.indexOf("#") + 1);
						// playerIdx = playerIdx.trim();
						updateClientInfo();
					} else if (msg.startsWith("<Start>")) {
						keyCode = "";
						ClientGUI.testlb6.setIcon(null);
						gamePlay = true;
						ClientGUI.readyButton.setEnabled(false);
						ClientGUI.burgerFbutton.setIcon(ClientGUI.ltPressed);
						ClientGUI.burgerFbutton.requestFocus();
						ClientGUI.burgerSlabel.setVisible(true);
						ClientGUI.burgerDlabel.setVisible(true);
						ClientGUI.burgerFlabel.setVisible(true);
						ClientGUI.burgerJlabel.setVisible(true);
						ClientGUI.burgerKlabel.setVisible(true);
						ClientGUI.burgerLlabel.setVisible(true);
						ClientGUI.burgerSbutton.setFocusable(true);
						ClientGUI.burgerDbutton.setFocusable(true);
						ClientGUI.burgerFbutton.setFocusable(true);
						ClientGUI.burgerJbutton.setFocusable(true);
						ClientGUI.burgerKbutton.setFocusable(true);
						ClientGUI.burgerLbutton.setFocusable(true);

						intromusic.close();
						playmusic = new Music("playmusic.mp3", true);
						playmusic.start();

						r2 = () -> {
							showBurger();
						};
						Thread th3 = new Thread(r2);
						th3.start();

					} else if (msg.startsWith("<Success> ")) {
						String success = msg.substring(msg.indexOf(" ") + 1);
						ClientGUI.showMsgTa.append(success);
						int i = ClientGUI.showMsgTa.getText().length();
						ClientGUI.showMsgTa.setCaretPosition(i);
					} else if (msg.startsWith("<Fail> ")) {
						String fail = msg.substring(msg.indexOf(" ") + 1);
						ClientGUI.showMsgTa.append(fail);
						int i = ClientGUI.showMsgTa.getText().length();
						ClientGUI.showMsgTa.setCaretPosition(i);
					} else if (msg.startsWith("<Timer>")) {
						ClientGUI.timerLabel.setText(msg.substring(7));
					} else if (msg.startsWith("<normal>")) {
						ClientGUI.bNormal.setEnabled(false);
						ClientGUI.bHard.setEnabled(true);
						sleepTime = 10000;
					} else if (msg.startsWith("<hard>")) {
						ClientGUI.bNormal.setEnabled(true);
						ClientGUI.bHard.setEnabled(false);
						sleepTime = 6000;
					} else if (msg.startsWith("<GameEnd>")) {
						gamePlay = false;
						ClientGUI.readyButton.setEnabled(true);
						ClientGUI.timerLabel.setText(" 00 : 00 ");
						ClientGUI.testlb6.setIcon(null);

						//playmusic = new Music("playmusic.mp3", true);
						playmusic.close();
						intromusic = new Music("intromusic.mp3", true);
						intromusic.start();

						// showHamburger 쓰레드 종료?
						ClientGUI.bNormal.setEnabled(true);
						ClientGUI.bHard.setEnabled(true);
						ClientGUI.burgerSlabel.setVisible(false);
						ClientGUI.burgerDlabel.setVisible(false);
						ClientGUI.burgerFlabel.setVisible(false);
						ClientGUI.burgerJlabel.setVisible(false);
						ClientGUI.burgerKlabel.setVisible(false);
						ClientGUI.burgerLlabel.setVisible(false);
						ClientGUI.burgerSbutton.setFocusable(false);
						ClientGUI.burgerDbutton.setFocusable(false);
						ClientGUI.burgerFbutton.setFocusable(false);
						ClientGUI.burgerJbutton.setFocusable(false);
						ClientGUI.burgerKbutton.setFocusable(false);
						ClientGUI.burgerLbutton.setFocusable(false);

					} else {
						ClientGUI.showMsgTa.append(msg);
						ClientGUI.showMsgTa.append("\n");
						int i = ClientGUI.showMsgTa.getText().length();
						ClientGUI.showMsgTa.setCaretPosition(i);
					}
				} catch (IOException ie) {
					ClientGUI.showMsgTa.append("서버가 종료되었습니다. 3초 후에 종료합니다");
					try {
						Thread.sleep(3000L);
						System.exit(0);
					} catch (InterruptedException iee) {
					} finally {
						accWindow.closeAll();
					}
				}
			}
		}

		public void updateClientInfo() {
			ImageIcon icon;
			if (Integer.parseInt(playerIdx) == 0) {
				icon = new ImageIcon(getClass().getResource("/images/hamburger_s.png"));
				// icon.getImage().flush();
				ClientGUI.userAvatar1.setIcon(icon);
				ClientGUI.showIDtf1.setBackground(new Color(255, 255, 255));
				ClientGUI.showIDtf1.setText(playerName);
				ClientGUI.showScore1.setBackground(new Color(255, 255, 255));
				ClientGUI.showScore1.setText(playerScore);
				deleteClientList();
			} else if (Integer.parseInt(playerIdx) == 1) {
				icon = new ImageIcon(getClass().getResource("/images/hotdog_s.png"));
				// icon.getImage().flush();
				ClientGUI.userAvatar2.setIcon(icon);
				ClientGUI.showIDtf2.setBackground(new Color(255, 255, 255));
				ClientGUI.showIDtf2.setText(playerName);
				ClientGUI.showScore2.setBackground(new Color(255, 255, 255));
				ClientGUI.showScore2.setText(playerScore);
				deleteClientList();
			} else if (Integer.parseInt(playerIdx) == 2) {
				icon = new ImageIcon(getClass().getResource("/images/pizza_s.png"));
				// icon.getImage().flush();
				ClientGUI.userAvatar3.setIcon(icon);
				ClientGUI.showIDtf3.setBackground(new Color(255, 255, 255));
				ClientGUI.showIDtf3.setText(playerName);
				ClientGUI.showScore3.setBackground(new Color(255, 255, 255));
				ClientGUI.showScore3.setText(playerScore);
				deleteClientList();
			} else if (Integer.parseInt(playerIdx) == 3) {
				icon = new ImageIcon(getClass().getResource("/images/potato_s.png"));
				// icon.getImage().flush();
				ClientGUI.userAvatar4.setIcon(icon);
				ClientGUI.showIDtf4.setBackground(new Color(255, 255, 255));
				ClientGUI.showIDtf4.setText(playerName);
				ClientGUI.showScore4.setBackground(new Color(255, 255, 255));
				ClientGUI.showScore4.setText(playerScore);
				deleteClientList();
			}
		}

		public void deleteClientList() { // 클라이언트 목록 제거
			ImageIcon ii2 = new ImageIcon(getClass().getResource("/images/waitingBurger1.png"));
			// ii2 = new ImageIcon("image\\p0.png");
			if (Integer.parseInt(playerIdx) == 0) {
				ClientGUI.userAvatar2.setIcon(ii2);
				ClientGUI.showIDtf2.setText("");
				ClientGUI.showScore2.setText("");
				ClientGUI.userAvatar3.setIcon(ii2);
				ClientGUI.showIDtf3.setText("");
				ClientGUI.showScore3.setText("");
				ClientGUI.userAvatar4.setIcon(ii2);
				ClientGUI.showIDtf4.setText("");
				ClientGUI.showScore4.setText("");
				ClientGUI.showIDtf2.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore2.setBackground(new Color(153, 153, 153));
				ClientGUI.showIDtf3.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore3.setBackground(new Color(153, 153, 153));
				ClientGUI.showIDtf4.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore4.setBackground(new Color(153, 153, 153));
			} else if (Integer.parseInt(playerIdx) == 1) {
				ClientGUI.userAvatar3.setIcon(ii2);
				ClientGUI.showIDtf3.setText("");
				ClientGUI.showScore3.setText("");
				ClientGUI.userAvatar4.setIcon(ii2);
				ClientGUI.showIDtf4.setText("");
				ClientGUI.showScore4.setText("");
				ClientGUI.showIDtf3.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore3.setBackground(new Color(153, 153, 153));
				ClientGUI.showIDtf4.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore4.setBackground(new Color(153, 153, 153));
			} else if (Integer.parseInt(playerIdx) == 2) {
				ClientGUI.userAvatar4.setIcon(ii2);
				ClientGUI.showIDtf4.setText("");
				ClientGUI.showScore4.setText("");
				ClientGUI.showIDtf4.setBackground(new Color(153, 153, 153));
				ClientGUI.showScore4.setBackground(new Color(153, 153, 153));
			}
		}
	}

	ArrayList<ImageIcon> imgLists = new ArrayList<ImageIcon>();

	public void insertHamburger() {
		imgLists.add(bcklpt);
		imgLists.add(bcllkt);
		imgLists.add(bcpcpt);
		imgLists.add(bcpct);
		imgLists.add(blcpct);
		imgLists.add(blklkt);
		imgLists.add(blkppt);
		imgLists.add(blpkct);
		imgLists.add(bpkclt);
		imgLists.add(bppppt);
		imgLists.add(bcccct);
		imgLists.add(bccclt);
		imgLists.add(bclkkt);
		imgLists.add(bkccct);
		imgLists.add(bkkkkt);
		imgLists.add(bkklkt);
		imgLists.add(blklct);
		imgLists.add(bllllt);
		imgLists.add(bpkpt);
		imgLists.add(bppllt);

	}

	Random r = new Random();

	public void showBurger() {
		if (gamePlay) {
			for (int i = 0; i < limit; i++) {
				int idx = r.nextInt(imgLists.size());
				ImageIcon ii = imgLists.get(idx);
				ClientGUI.testlb6.setIcon(ii);
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException ie) {
				}
			}
		}
	}

}