package netprog;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

/**
 * メモのボタンクラス
 * @author ANDO
 *
 */
public class NumBreMemoBtn implements MouseListener, MouseMotionListener{
	/**
	 * ボタン内にマウスがあるかどうか
	 */
	private boolean isMouseOn = false;
	/**
	 * メモ欄で使用するボタン（要素数１０の配列）
	 */
	private JButton[] button = new JButton[10];
	/**
	 * メモアイコン
	 */
	private ImageIcon memoIcon[] = {new ImageIcon("memoBtn_image/button_0.png"), new ImageIcon("memoBtn_image/button_1.png"), new ImageIcon("memoBtn_image/button_2.png"),
																	 new ImageIcon("memoBtn_image/button_3.png"), new ImageIcon("memoBtn_image/button_4.png"), new ImageIcon("memoBtn_image/button_5.png"),
																	 new ImageIcon("memoBtn_image/button_6.png"), new ImageIcon("memoBtn_image/button_7.png"), new ImageIcon("memoBtn_image/button_8.png"),
																	 new ImageIcon("memoBtn_image/button_9.png")};
	/**
	 * メモアイコン（マウスオン時）
	 */
	private ImageIcon on_memoIcon[] = {new ImageIcon("memoBtn_image/on_image/button_0.png"), new ImageIcon("memoBtn_image/on_image/button_1.png"), new ImageIcon("memoBtn_image/on_image/button_2.png"),
																			 new ImageIcon("memoBtn_image/on_image/button_3.png"), new ImageIcon("memoBtn_image/on_image/button_4.png"), new ImageIcon("memoBtn_image/on_image/button_5.png"),
																			 new ImageIcon("memoBtn_image/on_image/button_6.png"), new ImageIcon("memoBtn_image/on_image/button_7.png"), new ImageIcon("memoBtn_image/on_image/button_8.png"),
																			 new ImageIcon("memoBtn_image/on_image/button_9.png")};
	/**
	 * 候補から削除時のアイコン
	 */
	private ImageIcon[] cancelIcon = {new ImageIcon("cancelBtn_image/button_0.png"), new ImageIcon("cancelBtn_image/button_1.png"), new ImageIcon("cancelBtn_image/button_2.png"),
																	 new ImageIcon("cancelBtn_image/button_3.png"), new ImageIcon("cancelBtn_image/button_4.png"), new ImageIcon("cancelBtn_image/button_5.png"),
																	 new ImageIcon("cancelBtn_image/button_6.png"), new ImageIcon("cancelBtn_image/button_7.png"), new ImageIcon("cancelBtn_image/button_8.png"),
																	 new ImageIcon("cancelBtn_image/button_9.png")};
	/**
	 * 候補から削除時のアイコン（マウスオン時）
	 */
	private ImageIcon[] on_cancelIcon = {new ImageIcon("cancelBtn_image/on_image/button_0.png"), new ImageIcon("cancelBtn_image/on_image/button_1.png"), new ImageIcon("cancelBtn_image/on_image/button_2.png"),
																			 new ImageIcon("cancelBtn_image/on_image/button_3.png"), new ImageIcon("cancelBtn_image/on_image/button_4.png"), new ImageIcon("cancelBtn_image/on_image/button_5.png"),
																			 new ImageIcon("cancelBtn_image/on_image/button_6.png"), new ImageIcon("cancelBtn_image/on_image/button_7.png"), new ImageIcon("cancelBtn_image/on_image/button_8.png"),
																			 new ImageIcon("cancelBtn_image/on_image/button_9.png")};
	/**
	 * デフォルトのアイコン
	 */
	private ImageIcon[] skeletonIcon = {new ImageIcon("sk_image/button_0.png"), new ImageIcon("sk_image/button_1.png"), new ImageIcon("sk_image/button_2.png"),
																		 new ImageIcon("sk_image/button_3.png"), new ImageIcon("sk_image/button_4.png"), new ImageIcon("sk_image/button_5.png"),
																		 new ImageIcon("sk_image/button_6.png"), new ImageIcon("sk_image/button_7.png"), new ImageIcon("sk_image/button_8.png"),
																		 new ImageIcon("sk_image/button_9.png")};
	/**
	 * デフォルトのアイコン（マウスオン時）
	 */
	private ImageIcon[] on_skeletonIcon = {new ImageIcon("sk_image/on_image/button_0.png"), new ImageIcon("sk_image/on_image/button_1.png"), new ImageIcon("sk_image/on_image/button_2.png"),
																				 new ImageIcon("sk_image/on_image/button_3.png"), new ImageIcon("sk_image/on_image/button_4.png"), new ImageIcon("sk_image/on_image/button_5.png"),
																				 new ImageIcon("sk_image/on_image/button_6.png"), new ImageIcon("sk_image/on_image/button_7.png"), new ImageIcon("sk_image/on_image/button_8.png"),
																				 new ImageIcon("sk_image/on_image/button_9.png")};
	/**
	 * 何度押されているかを記録
	 */
	private int[] count = new int[10];

	public NumBreMemoBtn(JLayeredPane spLayPane) {
		//カウント数を０で初期化
		for (int c : count) {
			c = 0;
		}

		//ボタン画像のリサイズ処理
		for(int i = 0; i < memoIcon.length; i++) {
			memoIcon[i] = new ImageIcon(memoIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
			on_memoIcon[i] = new ImageIcon(on_memoIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
			cancelIcon[i] = new ImageIcon(cancelIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
			on_cancelIcon[i] = new ImageIcon(on_cancelIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
			skeletonIcon[i] = new ImageIcon(skeletonIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
			on_skeletonIcon[i] = new ImageIcon(on_skeletonIcon[i].getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
		}

		//ボタンの設定
		for(int i = 0; i < button.length; i++) {
			int space = 15; //ボタンの間隔
			button[i] = new JButton(); //ボタンにアイコンを設定する
			button[i].setIcon(skeletonIcon[i]);
			button[i].setContentAreaFilled(false); //ボタンの中を透明化にする処理
			button[i].setBorderPainted(false); //ボタンの枠を透明化にする処理
			button[i].setActionCommand(Integer.toString(i)); //ボタンに役割を付与
			button[i].addMouseListener(this); //ボタンをマウスでさわったときに反応するようにする
			spLayPane.add(button[i]);
			spLayPane.setLayer(button[i], 1);
		}

		//ボタンの座標指定
		int x1, x2, x3;
		x1 = 40; x2 = 155; x3 = 270;
		int y1, y2, y3, y4;
		y1 = 15; y2 = 130; y3 = 245; y4 = 360;
		int btnSize = 100;
		button[0].setBounds(x2, y4, btnSize, btnSize);
		button[1].setBounds(x1, y3, btnSize, btnSize);
		button[2].setBounds(x2, y3, btnSize, btnSize);
		button[3].setBounds(x3, y3, btnSize, btnSize);
		button[4].setBounds(x1, y2, btnSize, btnSize);
		button[5].setBounds(x2, y2, btnSize, btnSize);
		button[6].setBounds(x3, y2, btnSize, btnSize);
		button[7].setBounds(x1, y1, btnSize, btnSize);
		button[8].setBounds(x2, y1, btnSize, btnSize);
		button[9].setBounds(x3, y1, btnSize, btnSize);

		//初めは全て見えない状態にしておく
		SetVisibleFalse();
	}

	/**
	 * リセット処理
	 */
	public void Reset() {
		for(int i = 0; i < button.length; i++) {
			button[i].setIcon(skeletonIcon[i]);//アイコンをスケルトンに初期化
			count[i] = 0; //カウントを初期化
		}
	}

	/**
	 * 桁ごとのボタン切り替え時に使用（全てのボタンのsetVisibleをfalseに変更する）
	 */
	public void SetVisibleTrue() {
		for (JButton btn : button) {
			btn.setVisible(true);
		}
	}

	/**
	 * 桁ごとのボタン切り替え時に使用（全てのボタンのsetVisibleをfalseに変更する）
	 */
	public void SetVisibleFalse() {
		for (JButton btn : button) {
			btn.setVisible(false);
		}
	}

	/**
	 *受け取ったボタンの数字を判断するメソッド
	 * @param catchButton
	 * @param action ０ならば通常、１ならばマウスオンの画像を使用
	 */
	private void  CheckBtn(JButton theButton, String catchButton, int action) {
		switch (catchButton) {
			case "0": ChangeImage(theButton, 0, action); break;
			case "1": ChangeImage(theButton, 1, action); break;
			case "2": ChangeImage(theButton, 2, action); break;
			case "3": ChangeImage(theButton, 3, action); break;
			case "4": ChangeImage(theButton, 4, action); break;
			case "5": ChangeImage(theButton, 5, action); break;
			case "6": ChangeImage(theButton, 6, action); break;
			case "7": ChangeImage(theButton, 7, action); break;
			case "8": ChangeImage(theButton, 8, action); break;
			case "9": ChangeImage(theButton, 9, action); break;
			default: break;
		}
	}

	/**
	 * 画像を適切なものに変更するメソッド
	 * @param theButton
	 * @param number
	 * @param action
	 */
	private void ChangeImage(JButton theButton, int number, int action) {
		switch (count[number]) {
		case 0:
			if(action == 0) {
				theButton.setIcon(skeletonIcon[number]);
			}else {
				theButton.setIcon(on_skeletonIcon[number]);
			}
			break;
		case 1:
			if(action == 0) {
				theButton.setIcon(memoIcon[number]);
			}else {
				theButton.setIcon(on_memoIcon[number]);
			}
			break;
		case 2:
			if(action == 0) {
				theButton.setIcon(cancelIcon[number]);
			}else {
				theButton.setIcon(on_cancelIcon[number]);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String catchButton = theButton.getActionCommand();

		int buttonNum = Integer.parseInt(catchButton);
		count[buttonNum]++;
		count[buttonNum] %= 3;
		CheckBtn(theButton, catchButton, 1);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String catchButton = theButton.getActionCommand();

		if(isMouseOn == false) {
			CheckBtn(theButton, catchButton, 1);
			isMouseOn = true;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton theButton = (JButton)e.getComponent();
		String catchButton = theButton.getActionCommand();

		if(isMouseOn == true) {
			CheckBtn(theButton, catchButton, 0);
			isMouseOn = false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
