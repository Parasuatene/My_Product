package netprog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class NumberBreak extends JFrame implements MouseListener, MouseMotionListener{
	/**
	 * 名前
	 */
	private static String myName;
	/**
	 * IPアドレス名
	 */
	private static String ipAdress;
	/**
	 * NumBreTabクラスのインスタンス生成時に使用する
	 */
	private NumBreTab nbTab;
	/**
	 * NumBreMemoクラスのインスタンス生成時に使用する
	 */
	private NumBreMemo nbMemo;
	/**
	 * TimerLabelクラスのインスタンス生成時に使用
	 */
	private TimerLabel tLabel;
	/**
	 * NumBreTitleクラスのインスタンス生成時に使用
	 */
	private NumBreTitle nbTitle;

	//-------------------------------------------------------------------------------------------------------------------
	//使用する画像の初期化処理
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * デバッグ用の画像
	 */
	private ImageIcon[] debugIcon = {new ImageIcon("debug.png"), new ImageIcon("debug2.png"), new ImageIcon("debug3.png")};
	/**
	 * 数字のアイコン用の配列（ImageIconで初期化）
	 */
	private ImageIcon numberIcon[] = {new ImageIcon("number_image/number_0.png"), new ImageIcon("number_image/number_1.png"), new ImageIcon("number_image/number_2.png"),
																		 new ImageIcon("number_image/number_3.png"), new ImageIcon("number_image/number_4.png"), new ImageIcon("number_image/number_5.png"),
																		 new ImageIcon("number_image/number_6.png"), new ImageIcon("number_image/number_7.png"), new ImageIcon("number_image/number_8.png"),
																		 new ImageIcon("number_image/number_9.png")};
	/**
	 * 相手の数字のアイコン用の配列（ImageIconで初期化）
	 */
	private ImageIcon e_numberIcon[] = {new ImageIcon("number_image/e_number_0.png"), new ImageIcon("number_image/e_number_1.png"), new ImageIcon("number_image/e_number_2.png"),
																			 new ImageIcon("number_image/e_number_3.png"), new ImageIcon("number_image/e_number_4.png"), new ImageIcon("number_image/e_number_5.png"),
																			 new ImageIcon("number_image/e_number_6.png"), new ImageIcon("number_image/e_number_7.png"), new ImageIcon("number_image/e_number_8.png"),
																			 new ImageIcon("number_image/e_number_9.png")};
	/**
	 * ボタンのアイコン用の配列（ImageIconで初期化）
	 */
	private ImageIcon buttonIcon[][] = {
			{new ImageIcon("button_image/button_7.png"), new ImageIcon("button_image/button_4.png"), new ImageIcon("button_image/button_1.png"), new ImageIcon("button_image/button_delete.png")},
			{new ImageIcon("button_image/button_8.png"), new ImageIcon( "button_image/button_5.png"), new ImageIcon( "button_image/button_2.png"), new ImageIcon("button_image/button_0.png")},
			{new ImageIcon("button_image/button_9.png"), new ImageIcon("button_image/button_6.png"), new ImageIcon( "button_image/button_3.png"), new ImageIcon("button_image/button_call.png")}
	};
	/**
	 * ボタンのアイコン（マウスがのっているとき）用の配列
	 */
	private ImageIcon onButtonIcon[][] = {
			{new ImageIcon("on_button_image/on_button_7.png"), new ImageIcon("on_button_image/on_button_4.png"), new ImageIcon("on_button_image/on_button_1.png"), new ImageIcon("on_button_image/on_button_delete.png")},
			{new ImageIcon("on_button_image/on_button_8.png"), new ImageIcon( "on_button_image/on_button_5.png"), new ImageIcon( "on_button_image/on_button_2.png"), new ImageIcon("on_button_image/on_button_0.png")},
			{new ImageIcon("on_button_image/on_button_9.png"), new ImageIcon("on_button_image/on_button_6.png"), new ImageIcon( "on_button_image/on_button_3.png"), new ImageIcon("on_button_image/on_button_call.png")}
	};
	/**
	 * ボタンのアイコン（マウスクリック時）用の配列
	 */
	private ImageIcon clickButtonIcon[][] = {
			{new ImageIcon("click_button_image/click_button_7.png"), new ImageIcon("click_button_image/click_button_4.png"), new ImageIcon("click_button_image/click_button_1.png"), new ImageIcon("click_button_image/click_button_delete.png")},
			{new ImageIcon("click_button_image/click_button_8.png"), new ImageIcon( "click_button_image/click_button_5.png"), new ImageIcon( "click_button_image/click_button_2.png"), new ImageIcon("click_button_image/click_button_0.png")},
			{new ImageIcon("click_button_image/click_button_9.png"), new ImageIcon("click_button_image/click_button_6.png"), new ImageIcon( "click_button_image/click_button_3.png"), new ImageIcon("click_button_image/click_button_call.png")}
	};
	/**
	 * 透明の画像を用意
	 */
	private ImageIcon skeleton = new ImageIcon("skeleton.png");
	/**
	 * 入力数字のアスタリスク画像
	 */
	private ImageIcon scanIcon = new ImageIcon("scan_image/scanIcon.png");
	/**
	 * ロード中の画像
	 */
	private ImageIcon loadIcon[] = { new ImageIcon("load_image/load0.png"), new ImageIcon("load_image/load1.png"), new ImageIcon("load_image/load2.png"),
																   new ImageIcon("load_image/load3.png"), new ImageIcon("load_image/load4.png"), new ImageIcon("load_image/load5.png"),
																   new ImageIcon("load_image/load6.png"), new ImageIcon("load_image/load7.png"), new ImageIcon("load_image/load8.png"),
																   new ImageIcon("load_image/load9.png"), new ImageIcon("load_image/load10.png"), new ImageIcon("load_image/load11.png")};
	/**
	 * 敵のロード中の画像
	 */
	private ImageIcon e_loadIcon[] = { new ImageIcon("load_image/e_load0.png"), new ImageIcon("load_image/e_load1.png"), new ImageIcon("load_image/e_load2.png"),
																   new ImageIcon("load_image/e_load3.png"), new ImageIcon("load_image/e_load4.png"), new ImageIcon("load_image/e_load5.png"),
																   new ImageIcon("load_image/e_load6.png"), new ImageIcon("load_image/e_load7.png"), new ImageIcon("load_image/e_load8.png"),
																   new ImageIcon("load_image/e_load9.png"), new ImageIcon("load_image/e_load10.png"), new ImageIcon("load_image/e_load11.png")};
	/**
	 * 敵の入力時のアスタリスク画像
	 */
	private ImageIcon e_scanIcon = new ImageIcon("scan_image/e_scanIcon.png");
	/**
	 * 背景画像
	 */
	private ImageIcon backIcon = new ImageIcon("back_image/BackGround3.png");
	/**
	 * スクリーン画像
	 */
	private ImageIcon screenIcon = new ImageIcon("Screen.png");
	/**
	 * Hitの画像用の配列
	 */
	private ImageIcon[] hitIcon = {new ImageIcon("hit_image/0_Hit.png"), new ImageIcon("hit_image/1_Hit.png"), new ImageIcon("hit_image/2_Hit.png"), new ImageIcon("hit_image/3_Hit.png")};
	/**
	 * コールされた側のHitの画像用の配列
	 */
	private ImageIcon[] e_hitIcon = {new ImageIcon("hit_image/e_0_Hit.png"), new ImageIcon("hit_image/e_1_Hit.png"), new ImageIcon("hit_image/e_2_Hit.png"), new ImageIcon("hit_image/e_3_Hit.png")};
	/**
	 * Blowの画像用の配列
	 */
	private ImageIcon[] blowIcon = {new ImageIcon("blow_image/0_Blow.png"), new ImageIcon("blow_image/1_Blow.png"), new ImageIcon("blow_image/2_Blow.png"), new ImageIcon("blow_image/3_Blow.png")};
	/**
	 * Blowの画像用の配列
	 */
	private ImageIcon[] e_blowIcon = {new ImageIcon("blow_image/e_0_Blow.png"), new ImageIcon("blow_image/e_1_Blow.png"), new ImageIcon("blow_image/e_2_Blow.png"), new ImageIcon("blow_image/e_3_Blow.png")};
	/**
	 * ゲームスタート時の自分の番号を決める際に表示する画像
	 */
	private ImageIcon decideIcon = new ImageIcon("ui_image/decide.png");
	/**
	 * 自分のターン中の画像
	 */
	private ImageIcon myTurnIcon = new ImageIcon("ui_image/myTurn.png");
	/**
	 * 相手ターン中の画像
	 */
	private ImageIcon enemyTurn = new ImageIcon("ui_image/enemyTurn.png");
	/**
	 * 現在のターン数表示用の画像
	 */
	private ImageIcon turnIcon = new ImageIcon("ui_image/turn.png");
	/**
	 * 「Please Wait」の画像
	 */
	private ImageIcon stanbyIcon = new ImageIcon("stanby.png");
	/**
	 * 勝利時の画像
	 */
	private ImageIcon winIcon = new ImageIcon("result_image/win.png");
	/**
	 * 敗北時の画像
	 */
	private ImageIcon loseIcon = new ImageIcon("result_image/lose.png");

	//-------------------------------------------------------------------------------------------------------------------
	//画像表示用ボタン
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * 数字の入力ボタン用の配列
	 */
	private JButton[][] numberButton = new JButton[3][4];

	//-------------------------------------------------------------------------------------------------------------------
	//レイヤー、ラベル関係
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * 描画順を設けるためにJLayeredPaneを用意
	 */
	private JLayeredPane layPane = new JLayeredPane();;
	/**
	 * 背景画像用のラベル
	 */
	private JLabel labelBack;
	/**
	 * スクリーン画像用のラベル
	 */
	private JLabel labelScreen;
	/**
	 * 自分の数字を表示させる用のラベル
	 */
	private JLabel[] showMyNum = new JLabel[2];
	/**
	 * 数字画像表示用のラベル
	 */
	private JLabel[] labelNumber = new JLabel[3];
	/**
	 * Hit画像表示用のラベル
	 */
	private JLabel labelHit = new JLabel();
	/**
	 * Blow画像表示用のラベル
	 */
	private JLabel labelBlow = new JLabel();
	/**
	 * UI画像表示用のラベル
	 */
	private JLabel labelUI = new JLabel();
	/**
	 * ターン数表示用のラベル
	 */
	private JLabel labelTurn = new JLabel();
	/**
	 * ターン数表示の文字用
	 */
	private JLabel labelTurnNum = new JLabel();
	/**
	 * スクリーンに文字を表示する用のラベル
	 */
	private JLabel labelStr = new JLabel();
	/**
	 * スクリーンにロード画像を表示する用のラベル
	 */
	private JLabel labelLoad = new JLabel();

	//-------------------------------------------------------------------------------------------------------------------
	//使用するフィールド
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * 自分の決めた数字を格納（アイテムを使用しない限り変更することはない）
	 */
	private String[] myNumber = new String[3];
	/**
	 * コール用の数字を格納する（コールする度にnullで初期化）
	 */
	private String[] sendNum = new String[3];
	/**
	 * 現在sendNum配列に格納されている数値の個数
	 */
	private int counter = 0;
	/**
	 * 相手の現在のsendNum配列に格納されている数値の個数（画面表示の際に用いる）
	 */
	private int e_counter = 0;
	/**
	 * マウスがボタンの中にあればtrue、なければfalse
	 */
	private boolean isButtonIn;
	/**
	 * 入力ボタンのサイズ = 100
	 */
	private int buttonSize = 100;
	/**
	 * 表示される数字のサイズ:180
	 */
	private int numberSize = 180;
	/**
	 * 現在どちらのターンなのかを保存しておく
	 * 初めはどちらのターンでもないため、－１にしておく
	 */
	private int turnNum = -1;
	/**
	 * ターン数のカウント
	 */
	private int turnCount = 1;
	/**
	 * ターン数のカウントを変更するかを記録する
	 */
	private int memoryTurn = 0;
	/**
	 * どちらのターンから始めるかランダムで決める際に扱う
	 * ０、もしくは１のどちらかの値
	 */
	private int randomTurn = new Random().nextInt(2);
	/**
	 * 自分のターン番号
	 */
	private int myTurn;
	/**
	 * 自分が準備ができているかどうか（初期値:false）
	 */
	private boolean isMyReady = false;
	/**
	 * 相手がゲームを始める準備ができているかどうか（初期値:false）
	 */
	private boolean isEnemyReady = false;
	/**
	 * 処理している最中はボタン操作が効かないように、処理中かどうかを記録する
	 * trueであれば処理中
	 */
	private boolean nowProcessing = false;
	/**
	 * リトライ画面表示中かどうか
	 */
	private boolean nowRetry = false;
	/**
	 * 自分が続けるかどうか(続けるなら0 続けないなら1　初期値は-1)　
	 */
	private int myRetry = -1;
	/**
	 * 敵が続けるかどうか
	 */
	private int enemyRetry = -1;

	//-------------------------------------------------------------------------------------------------------------------
	//ここからタイトル関係
	//-------------------------------------------------------------------------------------------------------------------
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
	private ImageIcon[] searchIcon = { new ImageIcon("TitleUI_image/loading0.png"), new ImageIcon("TitleUI_image/loading1.png"), new ImageIcon("TitleUI_image/loading2.png"), new ImageIcon("TitleUI_image/loading3.png")};
	/**
	 * ゲームスタート時の画像
	 */
	private ImageIcon startIcon = new ImageIcon("gamestart.png");
	/**
	 * 説明画面を閉じるときのボタン
	 */
	private ImageIcon closeIcon = new ImageIcon("close.png");
	/**
	 * 説明画面を閉じるときのボタン
	 */
	private ImageIcon closeIcon2 = new ImageIcon("close2.png");

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
	 * ルール説明画面
	 */
	private JLabel ruleLabel = new JLabel();
	/**
	 * ルール画面クローズ時のボタン
	 */
	private JButton closeBtn = new JButton();
	/**
	 * ルール説明の画像
	 */
	private ImageIcon ruleIconLabel = new ImageIcon("setumei.png");
	/**
	 * ローディング画面のJLayeredPane
	 */
	public JLayeredPane loadLayPane = new JLayeredPane();
	/**
	 * 使用するTimerクラス
	 */
	private Timer timer;
	/**
	 * WaitTimeクラスのインスタンス生成用に用意
	 */
	private WaitTime wt;
	//-------------------------------------------------------------------------------------------------------------------
	//ここまで！！
	//-------------------------------------------------------------------------------------------------------------------


	//-------------------------------------------------------------------------------------------------------------------
	//リトライの処理
	//-------------------------------------------------------------------------------------------------------------------
	private JLayeredPane retryLayPane = new JLayeredPane();
	private JLabel retryBack = new JLabel();
	private JButton retryBtn = new JButton();
	private JButton notRetryBtn = new JButton();
	private ImageIcon retryBackIcon = new ImageIcon("Retry_image/retryBack.png");
	private ImageIcon retryBackIcon2 = new ImageIcon("Retry_image/backTitle.png");
	private ImageIcon retryBackIcon3 = new ImageIcon("Retry_image/waiting.png");
	private ImageIcon retryIcon = new ImageIcon("Retry_image/yes.png");
	private ImageIcon notRetryIcon = new ImageIcon("Retry_image/no.png");
	private ImageIcon retryIcon2 = new ImageIcon("Retry_image/onYes.png");
	private ImageIcon notRetryIcon2 = new ImageIcon("Retry_image/onNo.png");
	//-------------------------------------------------------------------------------------------------------------------
	//リトライここまで！！
	//-------------------------------------------------------------------------------------------------------------------

	/**
	 * 出力用のライター
	 */
	PrintWriter out;
	private Container c;

	public NumberBreak(int i) {

	}
	//-------------------------------------------------------------------------------------------------------------------
	//コンストラクタ
	//-------------------------------------------------------------------------------------------------------------------
	public NumberBreak(){

		//-------------------------------------------------------------------------------------------------------------------
		//ウィンドウ作成
		//-------------------------------------------------------------------------------------------------------------------
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
		this.setTitle("NumberLock");//ウィンドウのタイトルの設定
		this.setBounds(400, 100, 1000, 850/*800*/);//ウィンドウの生成座標、サイズを設定する
		this.setResizable(false);//ウィンドウのサイズ変更不可にする
		c = this.getContentPane();//フレームのペインを取得する
		c.setBackground(new Color(52, 52, 102)); //ウィンドウカラーの設定
		c.setLayout(null); //自動レイアウトをオフにする

		//-------------------------------------------------------------------------------------------------------------------
		//画像のリサイズ処理 （getScaledInstanceの引数が負の数値の場合、元のイメージ寸法が使用される）
		//-------------------------------------------------------------------------------------------------------------------
		//backIcon画像のリサイズ処理
		backIcon = new ImageIcon(backIcon.getImage().getScaledInstance(1000,850,Image.SCALE_SMOOTH));

		//ボタン画像のリサイズ処理
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++){
				buttonIcon[i][j] = new ImageIcon(buttonIcon[i][j].getImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH));
				onButtonIcon[i][j] = new ImageIcon(onButtonIcon[i][j].getImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH));
				clickButtonIcon[i][j] = new ImageIcon(clickButtonIcon[i][j].getImage().getScaledInstance(buttonSize,buttonSize,Image.SCALE_SMOOTH));
			}
		}

		//screenIcon画像のリサイズ処理
		screenIcon = new ImageIcon(screenIcon.getImage().getScaledInstance(900,-1,Image.SCALE_SMOOTH));

		//numberIcon画像のリサイズ処理
		for(int i = 0; i < numberIcon.length; i++) {
			numberIcon[i] = new ImageIcon(numberIcon[i].getImage().getScaledInstance(numberSize+20,-1,Image.SCALE_SMOOTH));
		}

		//e_numberIcon画像のリサイズ処理
		for(int i = 0; i < e_numberIcon.length; i++) {
			e_numberIcon[i] = new ImageIcon(e_numberIcon[i].getImage().getScaledInstance(numberSize+20,-1,Image.SCALE_SMOOTH));
		}

		//scanIcon画像のリサイズ処理
		scanIcon = new ImageIcon(scanIcon.getImage().getScaledInstance(numberSize+20,-1,Image.SCALE_SMOOTH));

		//e_scanIcon画像のリサイズ処理
		e_scanIcon = new ImageIcon(e_scanIcon.getImage().getScaledInstance(numberSize+20,-1,Image.SCALE_SMOOTH));

		//loadIcon画像のリサイズ処理
		for(int i = 0; i < loadIcon.length; i++) {
			loadIcon[i] = new ImageIcon(loadIcon[i].getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));
		}

		//e_loadIcon画像のリサイズ処理
		for(int i = 0; i < e_loadIcon.length; i++) {
			e_loadIcon[i] = new ImageIcon(e_loadIcon[i].getImage().getScaledInstance(120,120,Image.SCALE_SMOOTH));
		}

		//hitIcon画像のリサイズ処理
		for(int i = 0; i < hitIcon.length; i++) {
			hitIcon[i] = new ImageIcon(hitIcon[i].getImage().getScaledInstance(400, -1,Image.SCALE_SMOOTH));
		}

		//e_hitIcon画像のリサイズ処理
		for(int i = 0; i < e_hitIcon.length; i++) {
			e_hitIcon[i] = new ImageIcon(e_hitIcon[i].getImage().getScaledInstance(400, -1,Image.SCALE_SMOOTH));
		}

		//blowIcon画像のリサイズ処理
		for(int i = 0; i < blowIcon.length; i++) {
			blowIcon[i] = new ImageIcon(blowIcon[i].getImage().getScaledInstance(400, -1,Image.SCALE_SMOOTH));
		}

		//e_blowIcon画像のリサイズ処理
		for(int i = 0; i < e_blowIcon.length; i++) {
			e_blowIcon[i] = new ImageIcon(e_blowIcon[i].getImage().getScaledInstance(400, -1,Image.SCALE_SMOOTH));
		}

		//decideIcon画像のリサイズ処理
		decideIcon = new ImageIcon(decideIcon.getImage().getScaledInstance(500,-1,Image.SCALE_SMOOTH));

		//myTurnIcon画像のリサイズ処理
		myTurnIcon = new ImageIcon(myTurnIcon.getImage().getScaledInstance(500,-1,Image.SCALE_SMOOTH));

		//waitIcon画像のリサイズ処理
		enemyTurn = new ImageIcon(enemyTurn.getImage().getScaledInstance(500,-1,Image.SCALE_SMOOTH));

		//turnIcon画像のリサイズ処理
		turnIcon = new ImageIcon(turnIcon.getImage().getScaledInstance(300,60,Image.SCALE_SMOOTH));

		//stanbyIcon画像のリサイズ処理
		stanbyIcon = new ImageIcon(stanbyIcon.getImage().getScaledInstance(700,-1,Image.SCALE_SMOOTH));

		//winIcon画像のリサイズ処理
		winIcon = new ImageIcon(winIcon.getImage().getScaledInstance(700,-1,Image.SCALE_SMOOTH));

		//loseIcon画像のリサイズ処理
		loseIcon = new ImageIcon(loseIcon.getImage().getScaledInstance(700,-1,Image.SCALE_SMOOTH));

		//buttonIcon,onButtonIcon,clickButtonIcon画像のリサイズ処理
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				buttonIcon[i][j] = new ImageIcon(buttonIcon[i][j].getImage().getScaledInstance(buttonSize,-1,Image.SCALE_SMOOTH));
				onButtonIcon[i][j] = new ImageIcon(onButtonIcon[i][j].getImage().getScaledInstance(buttonSize,-1,Image.SCALE_SMOOTH));
				clickButtonIcon[i][j] = new ImageIcon(clickButtonIcon[i][j].getImage().getScaledInstance(buttonSize,-1,Image.SCALE_SMOOTH));
			}
		}

		//-------------------------------------------------------------------------------------------------------------------
		//入力ボタンの位置の初期化
		//-------------------------------------------------------------------------------------------------------------------
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 4; j++){
				int space = 15; //ボタンの間隔
				numberButton[i][j] = new JButton(buttonIcon[i][j]); //ボタンにアイコンを設定する
				numberButton[i][j].setContentAreaFilled(false); //ボタンの中を透明化にする処理
				numberButton[i][j].setBorderPainted(false); //ボタンの枠を透明化にする処理
				numberButton[i][j].addMouseListener(this); //ボタンをマウスでさわったときに反応するようにする
				//c.add(numberButton[i][j]); //ペインに貼り付ける
				layPane.add(numberButton[i][j]);
				layPane.setLayer(numberButton[i][j], 0);
				numberButton[i][j].setBounds(40+i*(buttonSize+space), 290+j*(buttonSize+space), buttonSize, buttonSize);
			}
		}

		//-------------------------------------------------------------------------------------------------------------------
		//ボタンに情報を付加する
		//-------------------------------------------------------------------------------------------------------------------
		numberButton[0][3].setActionCommand("Delete");
		numberButton[2][3].setActionCommand("Call");
		numberButton[1][3].setActionCommand(Integer.toString(0));
		numberButton[0][2].setActionCommand(Integer.toString(1));
		numberButton[1][2].setActionCommand(Integer.toString(2));
		numberButton[2][2].setActionCommand(Integer.toString(3));
		numberButton[0][1].setActionCommand(Integer.toString(4));
		numberButton[1][1].setActionCommand(Integer.toString(5));
		numberButton[2][1].setActionCommand(Integer.toString(6));
		numberButton[0][0].setActionCommand(Integer.toString(7));
		numberButton[1][0].setActionCommand(Integer.toString(8));
		numberButton[2][0].setActionCommand(Integer.toString(9));

		//-------------------------------------------------------------------------------------------------------------------
		//Layer関係
		//-------------------------------------------------------------------------------------------------------------------
		//背景画像の追加
		labelBack = new JLabel(backIcon);
		layPane.add(labelBack);
		//labelBackをlayPaneに追加(*第二引数は描画優先度)
		layPane.setLayer(labelBack, -1);
		labelBack.setBounds(0, 0, 1000, 850);

		//スクリーンに表示する数字の位置の初期化
		for(int i = 0; i < labelNumber.length; i++) {
			labelNumber[i] = new JLabel();
			layPane.add(labelNumber[i]);
			labelNumber[i].setBounds(230+i*(numberSize-20), 75, numberSize, numberSize);
		}

		//スクリーン画像の追加
		labelScreen = new JLabel(screenIcon);
		layPane.add(labelScreen);
		//labelScreenをlayPaneに追加
		layPane.setLayer(labelScreen, 0);
		labelScreen.setBounds(35, 65, 920, 200);

		//Hit画像の追加
		labelHit = new JLabel();
		layPane.add(labelHit);
		layPane.setLayer(labelHit, 1);
		labelHit.setBounds(80, 85, 400, numberSize);

		//Blow画像の追加
		labelBlow = new JLabel();
		layPane.add(labelBlow);
		layPane.setLayer(labelBlow, 1);
		labelBlow.setBounds(470, 85,  400, numberSize);

		//UI（画面右下の画像表示用）
		labelUI = new JLabel(decideIcon);
		layPane.add(labelUI);
		layPane.setLayer(labelUI, 0);
		labelUI.setBounds(500, 750,  500, 80);

		//ターン表示画像の追加
		labelTurn = new JLabel(turnIcon);
		layPane.add(labelTurn);
		layPane.setLayer(labelTurn, 0);
		labelTurn.setBounds(0, 0,  300, 60);
		labelTurn.add(labelTurnNum);
		labelTurnNum.setBounds(70, 0,  300, 60);
		labelTurnNum.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,35));
		labelTurnNum.setForeground(Color.gray);

		//スクリーンにその他の文字を表示させる用のLabel
		labelStr = new JLabel(stanbyIcon);
		layPane.add(labelStr);
		layPane.setLayer(labelStr, 1);
		labelStr.setBounds(150, 100,  700, 150);
		labelStr.setVisible(false);

		//スクリーンにロード画面を表示させる用のLabel
		labelLoad = new JLabel();
		layPane.add(labelLoad);
		layPane.setLayer(labelLoad, 2);
		labelLoad.setBounds(450, 50,  120, 230);
		labelLoad.setVisible(false);

		//Your Numberを表示
		for(int i = 0; i < showMyNum.length; i++) {
			showMyNum[i] = new JLabel();
			layPane.add(showMyNum[i]);
			layPane.setLayer(showMyNum[i], 0);
		}
		showMyNum[0].setBounds(650, 5, 250, 60);
		showMyNum[1].setBounds(900, 5, 80, 60);
		for(int i = 0; i < showMyNum.length; i++) {
			showMyNum[i].setFont(new Font("Aldrich", Font.PLAIN,35));
			showMyNum[i].setForeground(Color.GRAY);
		}
		//UIに選んだ数字を表示する
		showMyNum[0].setText("Your Number");
		showMyNum[1].setText("???");

		//-------------------------------------------------------------------------------------------------------------------
		//ここからタイトル関係
		//-------------------------------------------------------------------------------------------------------------------
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

		//ルール説明画像
		ruleIconLabel = new ImageIcon(ruleIconLabel.getImage().getScaledInstance(1000,830,Image.SCALE_SMOOTH));
		ruleLabel.setBounds(0, 0, 1000, 830);
		ruleLabel.setIcon(ruleIconLabel);
		titleLayPane.add(ruleLabel);
		titleLayPane.setLayer(ruleLabel, 1);
		ruleLabel.setVisible(false);

		closeIcon = new ImageIcon(closeIcon.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
		closeIcon2 = new ImageIcon(closeIcon2.getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH));
		closeBtn.setIcon(closeIcon);
		closeBtn.setBounds(810, 80, 30, 30);
		ruleLabel.add(closeBtn);
		closeBtn.setActionCommand("CLOSE");
		closeBtn.addMouseListener(this);//ボタンに効果を加える
		closeBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		closeBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理

		//メインクラスのContainerに追加
		c.add(titleLayPane);
		//座標、大きさ設定
		titleLayPane.setBounds(0, 0, 1000, 830);

		//-------------------------------------------------------------------------------------------------------------------------------
		//loadLayPaneに貼り付ける
		//-------------------------------------------------------------------------------------------------------------------------------
		//背景関係
		backIcon2 = new ImageIcon(backIcon2.getImage().getScaledInstance(1000,850,Image.SCALE_SMOOTH));
		backLabel2.setIcon(backIcon2);
		backLabel2.setBounds(0, 0, 1000, 850);
		loadLayPane.add(backLabel2);
		loadLayPane.setLayer(backLabel2, -1);

		for (int i = 0; i < searchIcon.length; i++) {
			searchIcon[i] = new ImageIcon(searchIcon[i].getImage().getScaledInstance(700,200,Image.SCALE_SMOOTH));
		}
		loadLabel.setBounds(250, 300, 700, 200);//200
		loadLabel.setIcon(searchIcon[0]);
		loadLayPane.add(loadLabel);
		loadLayPane.setLayer(loadLabel, 0);

		startIcon = new ImageIcon(startIcon.getImage().getScaledInstance(700,200,Image.SCALE_SMOOTH));
		startLabel.setBounds(150, 300, 700, 200);
		startLabel.setIcon(startIcon);
		loadLayPane.add(startLabel);
		loadLayPane.setLayer(startLabel, 0);
		startLabel.setVisible(false);

		//メインクラスのContainerに追加
		c.add(loadLayPane);
		//座標、大きさ設定
		loadLayPane.setBounds(0, 0, 1000, 830);
//		loadLayPane.setVisible(false);

		//-------------------------------------------------------------------------------------------------------------------
		//ここまで！！
		//-------------------------------------------------------------------------------------------------------------------

		//-------------------------------------------------------------------------------------------------------------------
		//リトライの処理
		//-------------------------------------------------------------------------------------------------------------------
		//レイヤーペイン
		retryLayPane.setBounds(0, 0, 1000, 830);
		layPane.add(retryLayPane);
		layPane.setLayer(retryLayPane, 5);

		//retryBack
		retryBack.setBounds(0, 0, 1000, 830);
		retryBackIcon = new ImageIcon(retryBackIcon.getImage().getScaledInstance(1000,830,Image.SCALE_SMOOTH));
		retryBackIcon2 = new ImageIcon(retryBackIcon2.getImage().getScaledInstance(1000,830,Image.SCALE_SMOOTH));
		retryBackIcon3 = new ImageIcon(retryBackIcon3.getImage().getScaledInstance(1000,830,Image.SCALE_SMOOTH));
		retryBack.setIcon(retryBackIcon);
		retryLayPane.add(retryBack);
		retryLayPane.setLayer(retryBack, -1);

		int btn_x = 200; int btn_y = 200;
		retryBtn.setBounds(400, 450, btn_x, 110);
		retryIcon = new ImageIcon(retryIcon.getImage().getScaledInstance(btn_x,btn_y,Image.SCALE_SMOOTH));
		retryIcon2 = new ImageIcon(retryIcon2.getImage().getScaledInstance(btn_x,btn_y,Image.SCALE_SMOOTH));
		retryBtn.setIcon(retryIcon);
		retryBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		retryBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理
		retryBtn.addMouseListener(this);//ボタンに効果を加える
		retryBtn.setActionCommand("RETRY");
		retryLayPane.add(retryBtn);
		retryLayPane.setLayer(retryBtn, 0);

		notRetryBtn.setBounds(400, 600, btn_x, 110);
		notRetryIcon = new ImageIcon(notRetryIcon.getImage().getScaledInstance(btn_x,btn_y,Image.SCALE_SMOOTH));
		notRetryIcon2 = new ImageIcon(notRetryIcon2.getImage().getScaledInstance(btn_x,btn_y,Image.SCALE_SMOOTH));
		notRetryBtn.setIcon(notRetryIcon);
		notRetryBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		notRetryBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理
		notRetryBtn.addMouseListener(this);//ボタンに効果を加える
		notRetryBtn.setActionCommand("NOT_RETRY");
		retryLayPane.add(notRetryBtn);
		retryLayPane.setLayer(notRetryBtn, 0);
		//-------------------------------------------------------------------------------------------------------------------
		//リトライここまで！！
		//-------------------------------------------------------------------------------------------------------------------

		//-------------------------------------------------------------------------------------------------------------------
		//インスタンス生成関係
		//-------------------------------------------------------------------------------------------------------------------
		//NumBreTabクラスのインスタンス生成
		nbTab = new NumBreTab(layPane);
		//NumBreMemoクラスのインスタンス生成
		nbMemo = new NumBreMemo(layPane);

		//layPaneをContainerに追加
		c.add(layPane);
		layPane.setBounds(0, 0, 1000, 830);

		layPane.setVisible(false);//最初はsetVisibleをfalseにしておく
		loadLayPane.setVisible(false);
		retryLayPane.setVisible(false);


		//-------------------------------------------------------------------------------------------------------------------
		//サーバーに接続する
		//-------------------------------------------------------------------------------------------------------------------
		Socket socket = null;
		try {
			//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			socket = new Socket("localhost", 10000);
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
			 System.err.println("エラーが発生しました: " + e);
		}

		//スレッドのインスタンス作成
		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
	}

	//-------------------------------------------------------------------------------------------------------------------
	//メッセージ受信のためのスレッド
	//-------------------------------------------------------------------------------------------------------------------
	public class MesgRecvThread extends Thread {

		Socket socket;
		String myName;

		public MesgRecvThread(Socket s, String n){
			socket = s;
			myName = n;
		}

		//通信状況を監視し，受信データによって動作する
		public void run() {
			//-------------------------------------------------------------------------------------------------------------------
			//初回のみ呼ばれる処理
			//-------------------------------------------------------------------------------------------------------------------
			try{
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println();//接続の最初に名前を送る
				myTurn = Integer.parseInt(br.readLine());//サーバーが送った番号を受信する

				if(myTurn % 2 == 0){//偶数ならターン番号を０とする
					myTurn = 0;
				}else {//奇数ならターン番号を１とする
					myTurn = 1;
				}
				SetScanIcon(scanIcon);
				//TestLoadingEffect();

				//-------------------------------------------------------------------------------------------------------------------
				//ループ処理
				//-------------------------------------------------------------------------------------------------------------------
				while(true) {
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる

					if(inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする
						System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
						String[] inputTokens = inputLine.split(" ");	//入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し、1つ目の要素を取り出す
						int hit = 0; //hit数
						int blow = 0; //blow数
						String[] callNumber = new String[3]; //コールされた番号を格納する
						int catchTurn;//どちらのターンだったかを受け取る

						//ゲーム開始の準備ができたときに受け取る
						if(cmd.equals("STANBY")) {
							catchTurn = Integer.parseInt(inputTokens[1]);
							//送られてきたターンの番号が自分の番号と異なっていればisEnemyTurnをtrueにする
							if(catchTurn != myTurn) {
								isEnemyReady = true;
							}
							System.out.println("スタンバイ");
						}

						//ゲームスタートの処理
						if(cmd.equals("START")) {
							turnNum = Integer.parseInt(inputTokens[1]);//どちらのターンから始めるかを確定
//							labelTurn.setText(String.format("Turn-%2s" , turnCount));
							labelStr.setVisible(false);
							showMyNum[1].setText(myNumber[0] + myNumber[1] + myNumber[2]);
							labelTurnNum.setText(String.format("Turn-%02d" , turnCount));
							tLabel = new TimerLabel(layPane);
							if(turnNum == myTurn) {
								System.out.println("先攻です");
								SetScanIcon(scanIcon);
								labelUI.setIcon(myTurnIcon);
							}else {
								System.out.println("後攻です");
								SetScanIcon(e_scanIcon);
								labelUI.setIcon(enemyTurn);
							}
							System.out.println("スタート");
						}

						//アイテム選択時に受け取る
						if(cmd.equals("CONTINUE")) {
							turnNum = Integer.parseInt(inputTokens[1]);
							String answer = inputTokens[2];
							if(turnNum != myTurn) {//自分のターン番号と同じでないとき
								if(answer.equals("Yes")) {
									enemyRetry = 0;
								}else {
									enemyRetry = 1;
								}
							}else {
								if(answer.equals("Yes")) {
									myRetry = 0;
								}else {
									myRetry = 1;
								}
							}

							if(myRetry == -1 && enemyRetry == -1) {//未入力の場合
								System.out.println("何もしない");
							}else if(myRetry == 0 && enemyRetry == 0) {//どちらも"Yes"を選択した場合
								Reset();//初期化
								retryLayPane.setVisible(false);
							}else if(myRetry == 1) {
								//タイトルに戻る処理
								retryLayPane.setVisible(false);
								layPane.setVisible(false);
								titleLayPane.setVisible(true);
								Reset();
							}else if(enemyRetry == 1) {
								retryBtn.setVisible(false);
								notRetryBtn.setVisible(false);
								retryBack.setIcon(retryBackIcon2);
								DelayMethod(2000);
								//タイトルに戻る処理
								Reset();
								retryLayPane.setVisible(false);
								layPane.setVisible(false);
								titleLayPane.setVisible(true);
							}else if(myRetry == 0) {
								retryBack.setIcon(retryBackIcon3);
								retryBtn.setVisible(false);
								notRetryBtn.setVisible(false);
							}
						}

						//数字のボタン入力時に受け取る
						if(cmd.equals("INPUT")) {
							turnNum = Integer.parseInt(inputTokens[1]);
							if(turnNum != myTurn) {//自分のターンの時は青色画像で表示
								labelNumber[e_counter].setIcon(e_numberIcon[Integer.parseInt(inputTokens[2])]);
								e_counter++;
							}
						}

						//デリート時に受け取る
						if(cmd.equals("DELETE")) {
							turnNum = Integer.parseInt(inputTokens[1]);
							if(turnNum != myTurn) {//自分のターンでないときは赤色の画像で表示
								e_counter--;
								labelNumber[e_counter].setIcon(e_scanIcon);
							}
						}

						//コール時に受け取る
						if(cmd.equals("CALL")) {
							nowProcessing = true; //処理開始
							turnNum = Integer.parseInt(inputTokens[1]);
							for(int i = 0; i < 3; i++) {//コールされた番号を取得
								callNumber[i] = inputTokens[i+2];
							}
							String number = callNumber[0] + callNumber[1] + callNumber[2];//送信する数字
							if(turnNum != myTurn) {//コールされた側のみが行う処理
								//HitとBlowの個数を調べる
								for(int i = 0; i < 3; i++) {
									for(int j = 0; j < 3; j++) {
										if(callNumber[i].equals(myNumber[j])) {//コールされた番号の中に自分と同じ番号があるとき
											if(i == j) {//数字の場所も同じであるとき
												System.out.println("hit++");
												hit ++;
											}else {
												System.out.println("blow++");
												blow++;
											}
										}
									}
								}
								System.out.println("コール");
								System.out.println("Hit ：" + hit + " / Blow : " + blow);
								//サーバへHit,Blowの情報を送信する
								String msg = "HIT&BLOW" + " " + myTurn + " " + number + " "+ hit + " " + blow;
								SendServer(msg);
							}else {
								e_counter = 0;
							}
						}

						//Hit&Blowの画像表示を行う
						if(cmd.equals("HIT&BLOW")) {
							int nextTurn = Integer.parseInt(inputTokens[1]);//次のターンの人の番号を受け取る
							hit = Integer.parseInt(inputTokens[3]); //Hitの個数を受け取る
							blow = Integer.parseInt(inputTokens[4]); ////Blowの個数を受け取る
							String[] rowData = {inputTokens[2], inputTokens[3], inputTokens[4]};
							SetOffIcon(); //一度labelNumberの画像表示をオフにする
							if(turnNum == myTurn) { //コールした側の処理
								LoadingEffect();
								SetHitBlow(hitIcon[hit], blowIcon[blow]);
								nbTab.nbTable.tableModel.insertRow(nbTab.nbTable.curRowCount, rowData);
								nbTab.nbTable.curRowCount++;
							}else { //コールされた側の処理
								E_LoadingEffect();
								SetHitBlow(e_hitIcon[hit], e_blowIcon[blow]);
								nbTab.nbTable.e_tableModel.insertRow(nbTab.nbTable.e_curRowCount, rowData);
								nbTab.nbTable.e_curRowCount++;
							}
							if(hit == 3) { //3Hitだったとき
								//ゲーム終了処理
								if(turnNum == myTurn) {
									GameResult(winIcon);
								}else {
									GameResult(loseIcon);
								}
							}else {
								//ここで初めてターンが変わる
								turnNum = nextTurn;
								memoryTurn++;

								if(memoryTurn == 2) {
									//ターンカウントを+1する
									turnCount++;
									labelTurnNum.setText(String.format("Turn-%02d" , turnCount));
									memoryTurn = 0;
								}

								tLabel = new TimerLabel(layPane);

								if(turnNum == myTurn) {
									SetScanIcon(scanIcon);
									labelUI.setIcon(myTurnIcon);
								}else {
									SetScanIcon(e_scanIcon);
									labelUI.setIcon(enemyTurn);
								}
								nowProcessing = false;
							}
						}
					}else{
						break;
					}
				}
				socket.close();
			} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}

	//-------------------------------------------------------------------------------------------------------------------
	//メイン関数
	//-------------------------------------------------------------------------------------------------------------------
	public static void main(String[] args){
		//名前の入力ダイアログを開く
		myName = JOptionPane.showInputDialog(null,"名前を入力してください","名前の入力",JOptionPane.QUESTION_MESSAGE);
		if(myName.equals("")){
			myName = "No name";//名前がないときは，"No name"とする
		}
		//IPアドレスの入力ダイアログを開く
		ipAdress = JOptionPane.showInputDialog(null,"接続するIPアドレスを入力してください","IPアドレスの入力",JOptionPane.QUESTION_MESSAGE);
		if(ipAdress.equals("")){
			ipAdress = "localhost";//名前がないときは,"localhost"とする
		}
		NumberBreak nb = new NumberBreak();
		nb.setVisible(true);
	}

	//-------------------------------------------------------------------------------------------------------------------
	//ゲーム内で使用する処理のメソッド
	//-------------------------------------------------------------------------------------------------------------------

	/**
	 * ゲームの結果処理
	 * @param icon
	 */
	public void GameResult(ImageIcon icon) {
		labelStr.setIcon(icon);
		int time = 0;
		while(time < 5) {
			DelayMethod(500);
			labelStr.setVisible(true);
			DelayMethod(500);
			labelStr.setVisible(false);
			time++;
		}
		SetOffIcon();
		labelStr.setVisible(true);
		retryLayPane.setVisible(true);
		nowRetry = true;
	}

	/**
	 * ゲームのリセット処理
	 */
	public void Reset() {
		//ターンの初期化
		turnCount = 1;
//		labelTurn.setText(String.format("Turn-%2s" , turnCount));
		labelTurnNum.setText(String.format("Turn-%02d" , turnCount));
		turnNum = -1;
		//操作中でないようにする
		nowProcessing = false;
		//ゲーム準備完了でない状態にする
		isMyReady = false;
		isEnemyReady = false;
		//リトライ画面の初期化
		retryBack.setIcon(retryBackIcon);
		retryBtn.setVisible(true);
		notRetryBtn.setVisible(true);
		//UIの初期化
		labelUI.setIcon(decideIcon);
		labelStr.setIcon(stanbyIcon);
		labelStr.setVisible(false);
		SetScanIcon(scanIcon);
		nowRetry = false;
		//マイナンバーの初期化
		for(int i = 0; i < myNumber.length; i++) {
			myNumber[i] = null;
		}
		//カウントの初期化
		counter = 0;
		e_counter = 0;
		showMyNum[1].setText("???");
		nbMemo.Reset(); //メモ関係のリセット処理
		nbTab.Reset(nbTab.nbTable.curRowCount, nbTab.nbTable.e_curRowCount); //タブ関係のリセット処理
	}

	/**
	 * 数字が重複しているかを確認するメソッド
	 * @param catchButton
	 * @param sendNum
	 * @return
	 */
	public boolean isDuplication(String catchButton, String[] sendNum) {
		for(String s : sendNum) {
			if(s == catchButton) {
				return true;
			}
		}
		return false;
	}

	/**
	 * マウスの挙動によってボタンの画像変更を行うメソッド
	 * @param theButton
	 * @param catchButton
	 * @param
	 */
	public void ChangeImage(JButton theButton, String catchButton, ImageIcon[][] icon) {
		switch (catchButton) {
			case "Delete": theButton.setIcon(icon[0][3]); break;
			case "Call": theButton.setIcon(icon[2][3]); break;
			case "0": theButton.setIcon(icon[1][3]); break;
			case "1": theButton.setIcon(icon[0][2]); break;
			case "2": theButton.setIcon(icon[1][2]); break;
			case "3": theButton.setIcon(icon[2][2]); break;
			case "4": theButton.setIcon(icon[0][1]); break;
			case "5": theButton.setIcon(icon[1][1]); break;
			case "6": theButton.setIcon(icon[2][1]); break;
			case "7": theButton.setIcon(icon[0][0]); break;
			case "8": theButton.setIcon(icon[1][0]); break;
			case "9": theButton.setIcon(icon[2][0]); break;
			default: break;
		}
	}

	/**
	 * 自分のターン時のロードのアニメーション
	 */
	public void LoadingEffect() {
		labelLoad.setVisible(true);
		for(ImageIcon icon : loadIcon) {
			labelLoad.setIcon(icon);
			DelayMethod(30);
		}
		labelLoad.setVisible(false);
	}

	/**
	 * 自分のターンでないときのロードのアニメーション
	 */
	public void E_LoadingEffect() {
		labelLoad.setVisible(true);
		for(ImageIcon icon : e_loadIcon) {
			labelLoad.setIcon(icon);
			DelayMethod(30);
		}
		labelLoad.setVisible(false);
	}

	/**
	 * labelNumberの画像表示をオフにするメソッド
	 */
	public void SetOffIcon() {
		for(JLabel label : labelNumber) {
			label.setVisible(false);
		}
	}

	/**
	 * スクリーンにアスタリスクの画像を表示するメソッド
	 * @param icon（表示したい画像）
	 */
	public void SetScanIcon(ImageIcon icon) {
		for(JLabel label : labelNumber) {
			label.setVisible(true);
			label.setIcon(icon);
		}
	}

	/**
	 * HitとBlowの表示
	 * 2秒待ったあとに画像表示をオフにする
	 * @param hitIcon
	 * @param blowIcon
	 */
	public void SetHitBlow(ImageIcon hitIcon, ImageIcon blowIcon) {
		labelHit.setIcon(hitIcon);
		labelBlow.setIcon(blowIcon);
		labelHit.setVisible(true);
		labelBlow.setVisible(true);
		DelayMethod(2500);
		labelHit.setVisible(false);
		labelBlow.setVisible(false);
	}

	/**
	 * 処理を遅らせるメソッド
	 * @param time
	 * 遅延させたい時間（1/1000秒）
	 */
	public void DelayMethod(int time) {
		try {
			TimeUnit.MILLISECONDS.sleep(time);
		} catch (InterruptedException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * サーバーに情報を送信するメソッド
	 * @param msg
	 */
	public void SendServer(String msg) {
		//サーバに情報を送る
		out.println(msg);//送信データをバッファに書き出す
		out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
	}

	/**
	 * ゲームスタート時に自分の数字を決めるメソッド
	 */
	public void DecideMyNumber(String catchButton) {
		if(catchButton.equals("Call")) { //Callボタンが押されたときの処理
			if(counter == 3) { //3桁選ばれている場合
				String msg;
				isMyReady = true;
				//相手が準備できているかで処理を分岐
				if(isEnemyReady) {
						msg = "START" + " " + randomTurn;//相手の準備が既に出来ていた場合、ゲームスタート
				}else {
						labelStr.setVisible(true);
						msg = "STANBY" + " " + myTurn;//相手側にこちらの準備が整ったことを知らせる（自分のターン番号を送信）
				}
				counter = 0;
				SetOffIcon();
				SendServer(msg); //サーバーに情報を送信
			}
		}else if(catchButton.equals("Delete")) { //Deleteボタンが押されたときの処理
			if(counter > 0) {
				counter--;
				myNumber[counter] = null;
				labelNumber[counter].setIcon(scanIcon);
			}
		}else { //数字が押されたときの処理
			if(counter < myNumber.length) {
				if(!isDuplication(catchButton, myNumber)) {
					myNumber[counter] = catchButton;
					labelNumber[counter].setIcon(numberIcon[Integer.parseInt(catchButton)]);
					counter++;
				}
			}
		}
	}

	/**
	 * ローディング時のタイマークラス
	 * @author ANDO
	 *
	 */
	public class WaitTime extends TimerTask{
		int count = 1;
		int secCount = 0;
		@Override
		public void run() {
				loadLabel.setIcon(searchIcon[(count % searchIcon.length)]);
				count++;
				secCount++;
				if(secCount == 7) {
					loadLayPane.setVisible(false);
					layPane.setVisible(true);
					timer.cancel();
				}
		}
	}

	//-------------------------------------------------------------------------------------------------------------------
	//以降、マウスイベントの処理
	//-------------------------------------------------------------------------------------------------------------------
	/**
	 * ボタンをクリックしたときの処理
	 * @param マウスイベント
	 */
	public void mouseClicked(MouseEvent e) {
			JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
			String catchButton = theButton.getActionCommand();

			//プレイボタンをクリックしたとき
			if(catchButton.equals("PLAY")) {
				System.out.println("プレイボタンクリック");
				titleLayPane.setVisible(false);
				loadLayPane.setVisible(true);
				timer = new Timer();
				timer.schedule(new WaitTime(), 500, 500);
			}else if(catchButton.equals("RULE")) {//ルール説明ボタンに侵入したとき
				ruleLabel.setVisible(true);
				buttons[0].setVisible(false);
				buttons[1].setVisible(false);
			}else if(catchButton.equals("CLOSE")) {
				ruleLabel.setVisible(false);
				buttons[0].setVisible(true);
				buttons[1].setVisible(true);
			}else if(catchButton.equals("RETRY")){//YESボタンを押したときの処理
				String msg = "CONTINUE" + " " + myTurn + " " + "Yes";
				SendServer(msg);
			}else if(catchButton.equals("NOT_RETRY")){//NOボタンを押したときの処理
				String msg = "CONTINUE" + " " + myTurn + " " + "No";
				SendServer(msg);
			}else {
				if(!nbMemo.isMemoOn) {//メモ起動中は操作不可能
					//最初の自分の数字を決める時にのみ扱う
					if(!isMyReady) {
						DecideMyNumber(catchButton);
					}

					//処理の最中の場合は反応しない
					if(!nowProcessing || !nowRetry) {
						//自分のターンでなければボタンは反応しない
						if(turnNum == myTurn) {
							String msg;
							System.out.println("enter1");
							if(catchButton.equals("Call")) { //Callボタンが押されたときの処理
								System.out.println("enter2");
								System.out.println(counter);
								if(counter == 3) { //3桁選ばれている場合
//									if(!tLabel.isRun) {
										System.out.println("enter3");
										msg = "CALL" + " " + myTurn + " " + sendNum[0] + " " + sendNum[1] + " " + sendNum[2];
										counter = 0;
										SendServer(msg);
										System.out.println("enter3");
										for(int i = 0; i < sendNum.length; i++) { //sendNum配列をnullで初期化
											sendNum[i] = null;
										}
//									}
								}
							}else if(catchButton.equals("Delete")) { //Deleteボタンが押されたときの処理
								if(counter > 0) {
									counter--;
									sendNum[counter] = null;
									labelNumber[counter].setIcon(scanIcon);
									msg = "DELETE" + " " + myTurn;
									SendServer(msg);//サーバーに送信
								}
							}else { //数字が押されたときの処理
								if(counter < sendNum.length) {
									if(!isDuplication(catchButton, sendNum)) {
										sendNum[counter] = catchButton;//sendNum配列に加える
										labelNumber[counter].setIcon(numberIcon[Integer.parseInt(catchButton)]);
										msg = "INPUT" + " " + myTurn + " " + sendNum[counter];
										counter++;
										SendServer(msg);//サーバーに送信
									}
								}
							}
						}
					}
				}
			}
	}

	/**
	 * ボタンにマウスが侵入時、画像をonButtonIcon画像に切り替える
	 * isButtonInをtrueに変更
	 * @param マウスイベント
	 */
	public void mouseEntered(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String catchButton = theButton.getActionCommand();

		//プレイボタンに侵入したとき
		if(catchButton.equals("PLAY")) {
			theButton.setIcon(playIcon[1]);
		}else if(catchButton.equals("RULE")) {//ルール説明ボタンに侵入したとき
			theButton.setIcon(ruleIcon[1]);
		}else if(catchButton.equals("CLOSE")){
			theButton.setIcon(closeIcon2);
		}else if(catchButton.equals("RETRY")){//YESボタンを押したときの処理
			theButton.setIcon(retryIcon2);
		}else if(catchButton.equals("NOT_RETRY")){//NOボタンを押したときの処理
			theButton.setIcon(notRetryIcon2);
		}else {
			if(!nowRetry) {
				isButtonIn = true;
				ChangeImage(theButton, catchButton, onButtonIcon);
			}
		}
	}

	/**
	 * ボタンからマウスが出た時に、画像をbuttonIcon画像に切り替える
	 * isButtonInをfalseに変更
	 * @param マウスイベント
	 */
	public void mouseExited(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		//プレイボタンから出たとき
		if(catchButton.equals("PLAY")) {
			theButton.setIcon(playIcon[0]);
		}else if(catchButton.equals("RULE")) {//ルール説明ボタンから出たとき
			theButton.setIcon(ruleIcon[0]);
		}else if(catchButton.equals("CLOSE")){
			theButton.setIcon(closeIcon);
		}else if(catchButton.equals("RETRY")){//YESボタンを押したときの処理
			theButton.setIcon(retryIcon);
		}else if(catchButton.equals("NOT_RETRY")){//NOボタンを押したときの処理
			theButton.setIcon(notRetryIcon);
		}else {
			if(!nowRetry) {
				isButtonIn = false;
				ChangeImage(theButton, catchButton, buttonIcon);
			}
		}
	}

	/**
	 * マウスボタンクリック中、ボタンの画像を変更
	 */
	public void mousePressed(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("PLAY")) {
			//何もしない
		}else if(catchButton.equals("RULE")) {
			//何もしない
		}else {
			if(!nowRetry) {
				ChangeImage(theButton, catchButton, clickButtonIcon);
			}
		}
	}

	/**
	 *マウスボタンを離した際、ボタンの画像を適切なものに変更（isButtonInによって変わる）
	 */
	public void mouseReleased(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("PLAY")) {
			//何もしない
		}else if(catchButton.equals("RULE")) {
			//何もしない
		}else {
			if(!nowRetry) {
				if(isButtonIn) {
					ChangeImage(theButton, catchButton, onButtonIcon);
				}else {
					ChangeImage(theButton, catchButton, buttonIcon);
				}
			}
		}


		for(int i = panel.length-1; i > 0; i--){
			int index = rnd.nextInt(i + 1);
			String tmp = panel[index];
			panel[index] = panel[i];
			panel[i] = tmp;
		}
	}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
}
