package netprog;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class NumBreTitle implements MouseListener, MouseMotionListener{
	/**
	 * 背景画像
	 */
	private ImageIcon backIcon1 = new ImageIcon("back_image/BackGround4.png");
	/**
	 * タイトルロゴ
	 */
	private ImageIcon titleIcon = new ImageIcon("logo_image/game_logo.png");
	/**
	 * ボタン画像
	 */
	private ImageIcon[] playIcon = { new ImageIcon("TitleUI_image/button_play0.png"), new ImageIcon("TitleUI_image/button_play1.png") };
	/**
	 * ルール説明画像
	 */
	private ImageIcon[] ruleIcon = { new ImageIcon("TitleUI_image/button_rule0.png"), new ImageIcon("TitleUI_image/button_rule1.png") };
	/**
	 * 背景画像用のラベル
	 */
	private JLabel backLabel1 = new JLabel();
	/**
	 * タイトル画像用のラベル
	 */
	private JLabel titleLabel = new JLabel();
	/**
	 * 選択ボタン(1. Play 2. ゲーム説明)
	 */
	private JButton[] buttons = new JButton[2];
	/**
	 * タイトル画面のJLayeredPane（ここにロゴやボタンを貼り付けていく）
	 */
	public JLayeredPane titleLayPane = new JLayeredPane();
	/**
	 * 背景画像
	 */
	private ImageIcon backIcon2 = new ImageIcon("back_image/BackGround3.png");
	/**
	 * 対戦相手待ちの画像
	 */
	private ImageIcon[] searchIcon = { new ImageIcon("TitleUI_image/searching0.png"), new ImageIcon("TitleUI_image/searching1.png"), new ImageIcon("TitleUI_image/searching2.png"), new ImageIcon("TitleUI_image/searching3.png")};
	/**
	 * ゲームスタート時の画像
	 */
	private ImageIcon startIcon = new ImageIcon("gamestart.png");
	/**
	 * 背景画像用のラベル
	 */
	private JLabel backLabel2 = new JLabel();
	/**
	 * Searching画像を貼り付けるJLabel
	 */
	private JLabel loadLabel = new JLabel();
	/**
	 * GameStart画像を貼り付けるJLabel
	 */
	private JLabel startLabel = new JLabel();
	/**
	 * ローディング画面のJLayeredPane
	 */
	public JLayeredPane loadLayPane = new JLayeredPane();
	/**
	 * ゲームスタートしているかを判別
	 */
	public boolean gameStart = false;
	/**
	 * 使用するTimerクラス
	 */
	private Timer timer;
	/**
	 * WaitTimeクラスのインスタンス生成用に用意
	 */
//	private WaitTime wt;

	/**
	 * コンストラクタ
	 * @param c
	 */
	public NumBreTitle(Container c) {
		//-------------------------------------------------------------------------------------------------------------------------------
		//titleLayPaneに貼り付ける
		//-------------------------------------------------------------------------------------------------------------------------------
		//背景関係
		backIcon1 = new ImageIcon(backIcon1.getImage().getScaledInstance(1000,850,Image.SCALE_SMOOTH));
		backLabel1.setIcon(backIcon1);
		backLabel1.setBounds(0, 0, 1000, 850);
		titleLayPane.add(backLabel1);
		titleLayPane.setLayer(backLabel1, -1);

		//タイトルロゴ関係
		titleIcon = new ImageIcon(titleIcon.getImage().getScaledInstance(920,400,Image.SCALE_SMOOTH));
		titleLabel.setIcon(titleIcon);
		titleLabel.setBounds(35, 40, 920, 400);
		titleLayPane.add(titleLabel);
		titleLayPane.setLayer(titleLabel, 0);

		//ボタン関係
		for (int i = 0; i < 2; i++) {
			playIcon[i] = new ImageIcon(playIcon[i].getImage().getScaledInstance(300,-1,Image.SCALE_SMOOTH));
			ruleIcon[i] = new ImageIcon(ruleIcon[i].getImage().getScaledInstance(300,-1,Image.SCALE_SMOOTH));
		}
		buttons[0] = new JButton();//初期化処理
		buttons[0].setIcon(playIcon[0]);
		buttons[0].setActionCommand("PLAY");
		buttons[0].setBounds(350, 530, 300, 65);
		buttons[1] = new JButton();//初期化処理
		buttons[1].setIcon(ruleIcon[0]);
		buttons[1].setActionCommand("RULE");
		buttons[1].setBounds(350, 630, 300, 65);
		for(JButton button : buttons) {
			button.setContentAreaFilled(false); //ボタンの中を透明化にする処理
			button.setBorderPainted(false); //ボタンの枠を透明化にする処理
			button.addMouseListener(this);//ボタンに効果を加える
			titleLayPane.add(button);
			titleLayPane.setLayer(button, 0);
		}

		//メインクラスのContainerに追加
		c.add(titleLayPane);
		//座標、大きさ設定
		titleLayPane.setBounds(0, 0, 1000, 830);

		//-------------------------------------------------------------------------------------------------------------------------------
		//loadLayPaneに貼り付ける
		//-------------------------------------------------------------------------------------------------------------------------------
//		//背景関係
//		backIcon2 = new ImageIcon(backIcon2.getImage().getScaledInstance(1000,850,Image.SCALE_SMOOTH));
//		backLabel2.setIcon(backIcon2);
//		backLabel2.setBounds(0, 0, 1000, 850);
//		loadLayPane.add(backLabel2);
//		loadLayPane.setLayer(backLabel2, -1);
//
//		for (int i = 0; i < searchIcon.length; i++) {
//			searchIcon[i] = new ImageIcon(searchIcon[i].getImage().getScaledInstance(700,200,Image.SCALE_SMOOTH));
//		}
//		loadLabel.setBounds(200, 300, 700, 200);
//		loadLabel.setIcon(searchIcon[0]);
//		loadLayPane.add(loadLabel);
//		loadLayPane.setLayer(loadLabel, 0);
//
//		startIcon = new ImageIcon(startIcon.getImage().getScaledInstance(700,200,Image.SCALE_SMOOTH));
//		startLabel.setBounds(150, 300, 700, 200);
//		startLabel.setIcon(startIcon);
//		loadLayPane.add(startLabel);
//		loadLayPane.setLayer(startLabel, 0);
//		startLabel.setVisible(false);
//
//		//メインクラスのContainerに追加
//		c.add(loadLayPane);
//		//座標、大きさ設定
//		loadLayPane.setBounds(0, 0, 1000, 830);
//		loadLayPane.setVisible(false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		//プレイボタンに侵入したとき
			if(catchButton.equals("PLAY")) {
				gameStart = true;
//				if(!MyServer2.isReady) {
//					System.out.println("isReady = " + MyServer2.isReady);
////					System.out.println("最新　member =" + new MyServer2().Member());
//					titleLayPane.setVisible(false);
//					loadLayPane.setVisible(true);
//					timer = new Timer();
//					timer.schedule(new WaitTime(), 1000, 1000);
//				}else {
////					NumberBreak nb = new NumberBreak();//メインクラスのインスタンス生成
//					gameStart = true;
////					titleLayPane.setVisible(false);
//				}
			}

			//ルール説明ボタンに侵入したとき
			if(catchButton.equals("RULE")) {
				theButton.setIcon(ruleIcon[1]);
			}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		//プレイボタンに侵入したとき
		if(catchButton.equals("PLAY")) {
			theButton.setIcon(playIcon[1]);
		}

		//ルール説明ボタンに侵入したとき
		if(catchButton.equals("RULE")) {
			theButton.setIcon(ruleIcon[1]);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		//プレイボタンから出たとき
		if(catchButton.equals("PLAY")) {
			theButton.setIcon(playIcon[0]);
		}

		//ルール説明ボタンから出たとき
		if(catchButton.equals("RULE")) {
			theButton.setIcon(ruleIcon[0]);
		}
	}

//	public class WaitTime extends TimerTask{
//		int count = 1;
//		@Override
//		public void run() {
//			if(MyServer2.isReady) {
////				NumberBreak nb = new NumberBreak();
//				titleLayPane.setVisible(false);
//			}else {
//				loadLabel.setIcon(searchIcon[(count % searchIcon.length)]);
//				count++;
//			}
//		}
//	}

}
