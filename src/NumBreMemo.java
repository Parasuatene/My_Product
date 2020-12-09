package netprog;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 * メモ関係のクラス
 * @author ANDO
 *
 */
public class NumBreMemo implements MouseListener, MouseMotionListener{
	/**
	 * メモスペースの画像
	 */
//	private ImageIcon mSpIcon = new ImageIcon("memoSpace.png");
	private ImageIcon mSpIcon = new ImageIcon("debug.png");
	/**
	 * 開くときのメモボタンの画像
	 */
	private ImageIcon[] memoBtnIcon1 = { new ImageIcon("memo_image1/memo_button1.png"), new ImageIcon("memo_image1/on_memo_button1.png"), new ImageIcon("memo_image1/click_memo_button1.png") };
	/**
	 * 閉じるときのメモボタンの画像
	 */
	private ImageIcon[] memoBtnIcon2 = { new ImageIcon("memo_image2/memo_button2.png"), new ImageIcon("memo_image2/on_memo_button2.png"), new ImageIcon("memo_image2/click_memo_button2.png") };
	/**
	 * 矢印のアイコン
	 */
	private ImageIcon[] rightIcon = { new ImageIcon("arrow_image/right_image/right.png"), new ImageIcon("arrow_image/right_image/on_right.png")};
	/**
	 * 矢印のアイコン
	 */
	private ImageIcon[] leftIcon = { new ImageIcon("arrow_image/left_image/left.png"), new ImageIcon("arrow_image/left_image/on_left.png")};
	/**
	 * 赤ランプのアイコン
	 */
	private ImageIcon[] redLamp = { new ImageIcon("lamp_image/lamp_red_on.png"), new ImageIcon("lamp_image/lamp_red_off.png")};
	/**
	 * 緑ランプのアイコン
	 */
	private ImageIcon[] greenLamp = { new ImageIcon("lamp_image/lamp_green_on.png"), new ImageIcon("lamp_image/lamp_green_off.png")};
	/**
	 * 青ランプのアイコン
	 */
	private ImageIcon[] blueLamp = { new ImageIcon("lamp_image/lamp_blue_on.png"), new ImageIcon("lamp_image/lamp_blue_off.png")};
	/**
	 * メモをするようの基盤となるラベル
	 */
	private JLabel memo = new JLabel();
	/**
	 * メモするスペース
	 */
	private JLabel memoSpace = new JLabel();
	/**
	 * メモするスペース上に貼り付けるJLayedPane
	 */
	public JLayeredPane spLayPane = new JLayeredPane();
	/**
	 * NumBreMemoBtnクラスの配列
	 */
	private NumBreMemoBtn[] memoBtns = new NumBreMemoBtn[3];
	/**
	 * メモ欄を表示する用のボタン
	 */
	private JButton openBtn = new JButton();
	/**
	 * 矢印ボタン（右）
	 */
	private JButton rightBtn = new JButton();
	/**
	 * 矢印ボタン（左）
	 */
	private JButton leftBtn = new JButton();
	/**
	 * ランプのラベル
	 */
	private JLabel[] lampLabel = new JLabel[3];
	/**
	 * メモボタンをオンにしているか、オフにしているか
	 */
	public boolean isMemoOn = false;
	/**
	 * メモボタン内に入っているのかどうか
	 */
	private boolean isMemoBtnIn = false;
	/**
	 * 現在表示されているクラス配列の番号（初期値は０）
	 */
	private int classNum = 0;
	/**
	 * x座標
	 */
	private int pos_x = -385;
	/**
	 * y座標
	 */
	private int pos_y = 275;
	/**
	 * 幅
	 */
	private int width = 425;//410
	/**
	 * 高さ
	 */
	private int height = 470;

	/**
	 * コンストラクタ
	 * @param layeredPane
	 */
	public NumBreMemo(JLayeredPane layPane) {

		//メモボタン1のリサイズ処理
		for(int i = 0; i < memoBtnIcon1.length; i++){
			memoBtnIcon1[i] = new ImageIcon(memoBtnIcon1[i].getImage().getScaledInstance(30, 100,Image.SCALE_SMOOTH));
		}

		//メモボタン2のリサイズ処理
		for(int i = 0; i < memoBtnIcon2.length; i++){
			memoBtnIcon2[i] = new ImageIcon(memoBtnIcon2[i].getImage().getScaledInstance(30, 100,Image.SCALE_SMOOTH));
		}

		//メモを統括するラベル
		memo.setBounds(pos_x, pos_y, width, height);//(-370, 290, 400, 445)
		layPane.add(memo);
		layPane.setLayer(memo, 3);

		//メモするスペース
		mSpIcon = new ImageIcon(mSpIcon.getImage().getScaledInstance(385, 475,Image.SCALE_SMOOTH));
		memoSpace.setBounds(0, 0, 385, 475);//(0, 0, 370, 445);
		memoSpace.setIcon(mSpIcon);
		memo.add(memoSpace);

		//メモボタンの設定
		openBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		openBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理
		openBtn.addMouseListener(this);//ボタンに効果を加える
		openBtn.setActionCommand("MEMO");
		openBtn.setIcon(memoBtnIcon1[0]);
		openBtn.setBounds(385, 185,  30, 100);//(370, 170,  30, 100);
		memo.add(openBtn);

		//レイヤードペインの設定
		spLayPane.setBounds(0, 0, width, height);//メモスペースと一致させる
		memoSpace.add(spLayPane);

		//NumBreMemoBtnのインスタンス生成
		for(int i = 0; i < memoBtns.length; i++) {
			memoBtns[i] = new NumBreMemoBtn(spLayPane);
		}
		//配列番号0だけsetVisibleをtrueにする
		memoBtns[classNum].SetVisibleTrue();

		//矢印ボタンのリサイズ処理
		for(int i = 0; i < 2; i++){
			rightIcon[i] = new ImageIcon(rightIcon[i].getImage().getScaledInstance(100, 100,Image.SCALE_SMOOTH));
			leftIcon[i] = new ImageIcon(leftIcon[i].getImage().getScaledInstance(100, 100,Image.SCALE_SMOOTH));
		}

		//矢印（右）のボタンの設定
		rightBtn.setIcon(rightIcon[0]);
		rightBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		rightBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理
		rightBtn.setActionCommand("RIGHT"); //ボタンに役割を付与
		rightBtn.addMouseListener(this); //ボタンをマウスでさわったときに反応するようにする
		spLayPane.add(rightBtn);
		spLayPane.setLayer(rightBtn, 3);
		rightBtn.setBounds(270, 360, 100, 100);

		//矢印（左）のボタンの設定
		leftBtn.setIcon(leftIcon[0]);
		leftBtn.setContentAreaFilled(false); //ボタンの中を透明化にする処理
		leftBtn.setBorderPainted(false); //ボタンの枠を透明化にする処理
		leftBtn.setActionCommand("LEFT"); //ボタンに役割を付与
		leftBtn.addMouseListener(this); //ボタンをマウスでさわったときに反応するようにする
		spLayPane.add(leftBtn);
		spLayPane.setLayer(leftBtn, 3);
		leftBtn.setBounds(40, 360, 100, 100);

		//ランプラベルの設定
		for (int i = 0; i < lampLabel.length; i++) {
			lampLabel[i] = new JLabel();
			spLayPane.add(lampLabel[i]);
			spLayPane.setLayer(lampLabel[i], 1);
		}
		int size = 35;
		for(int i = 0; i < 2; i++) {
			redLamp[i] = new ImageIcon(redLamp[i].getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
			greenLamp[i] = new ImageIcon(greenLamp[i].getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
			blueLamp[i] = new ImageIcon(blueLamp[i].getImage().getScaledInstance(size,size,Image.SCALE_SMOOTH));
		}
		lampLabel[0].setIcon(redLamp[0]);
		lampLabel[0].setBounds(8, 107, size, size);
		lampLabel[1].setIcon(greenLamp[1]);
		lampLabel[1].setBounds(8, 222, size, size);
		lampLabel[2].setIcon(greenLamp[1]);
		lampLabel[2].setBounds(8, 337, size, size);
	}

	/**
	 * マウスの挙動によってメモボタンの画像変更を行うメソッド
	 * @param i
	 * 画像配列の何番目か
	 */
	private void ChangeMemoImage(int i) {
		if(isMemoOn) {
			openBtn.setIcon(memoBtnIcon2[i]);
		}else {
			openBtn.setIcon(memoBtnIcon1[i]);
		}
	}

	public void SwitchLamp() {
		if(classNum == 0) {
			lampLabel[0].setIcon(redLamp[0]);
			lampLabel[1].setIcon(greenLamp[1]);
			lampLabel[2].setIcon(blueLamp[1]);
		}else if(classNum == 1) {
			lampLabel[0].setIcon(redLamp[1]);
			lampLabel[1].setIcon(greenLamp[0]);
			lampLabel[2].setIcon(blueLamp[1]);
		}else {
			lampLabel[0].setIcon(redLamp[1]);
			lampLabel[1].setIcon(greenLamp[1]);
			lampLabel[2].setIcon(blueLamp[0]);
		}
	}

	/**
	 * メモボタンをリセットする
	 */
	public void Reset() {
		for (NumBreMemoBtn btn : memoBtns) {
			btn.Reset();
		}
		classNum = 0; //現在のクラス番号を0で初期化
		SwitchLamp();
		//配列番号0だけsetVisibleをtrueにする
		memoBtns[classNum].SetVisibleTrue();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("MEMO")) {
			if(!isMemoOn) {
				memo.setBounds(0, pos_y, width, height);
				isMemoOn = true;
				ChangeMemoImage(0);
			}else {
				memo.setBounds(pos_x, pos_y, width, height);
				isMemoOn = false;
				ChangeMemoImage(0);
			}
		}

		if(catchButton.equals("RIGHT")) {
			memoBtns[classNum].SetVisibleFalse();
			classNum++;
			classNum %= 3;
			memoBtns[classNum].SetVisibleTrue();
			SwitchLamp();
		}

		if(catchButton.equals("LEFT")) {
			memoBtns[classNum].SetVisibleFalse();
			classNum--;
			if(classNum < 0) {
				classNum = 2;
			}else {
				classNum %= 3;
			}
			memoBtns[classNum].SetVisibleTrue();
			SwitchLamp();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("MEMO")) {
			ChangeMemoImage(2);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("MEMO")) {
			if(isMemoBtnIn) {
				ChangeMemoImage(1);
			}else {
				ChangeMemoImage(0);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("MEMO")) {
			ChangeMemoImage(1);
		}

		if(catchButton.equals("RIGHT")) {
			rightBtn.setIcon(rightIcon[1]);
		}

		if(catchButton.equals("LEFT")) {
			leftBtn.setIcon(leftIcon[1]);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String catchButton = theButton.getActionCommand();

		if(catchButton.equals("MEMO")) {
			ChangeMemoImage(0);
		}

		if(catchButton.equals("RIGHT")) {
			rightBtn.setIcon(rightIcon[0]);
		}

		if(catchButton.equals("LEFT")) {
			leftBtn.setIcon(leftIcon[0]);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) { }
}
