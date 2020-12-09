package netprog;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTabbedPane;

/**
 * タブ関係のクラス
 * @author ANDO
 *
 */
public class NumBreTab {
	/**
	 * 使用するJTabbedPane
	 */
	public JTabbedPane tabbedpane = new JTabbedPane();
	/**
	 * NumBreTableクラスのインスタンス作成用
	 */
	public NumBreTable nbTable;
	/**
	 * 履歴（自分用）
	 */
	public JLabel histTab = new JLabel();
	/**
	 * 履歴（相手用）
	 */
	public JLabel e_histTab = new JLabel();
	/**
	 * チャット用
	 */
	public JLabel chatTab = new JLabel(); //chatのタブ
	/**
	 * メモ用
	 */
	public JLabel memoTab = new JLabel(); //memoのタブ
	/**
	 * フォントの初期化
	 */
	Font font = new Font(Font.SANS_SERIF,Font.PLAIN,20);

	//コンストラクタ
	public NumBreTab(JLayeredPane layPane) {
		//tabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		//tabbedpaneに各タブを加えていく
		tabbedpane.addTab("  History  ", histTab);
//		tabbedpane.addTab(null, new ImageIcon(new ImageIcon("history.png").getImage().getScaledInstance(100,30,Image.SCALE_SMOOTH)), histTab);
		tabbedpane.addTab("Enemy History", e_histTab);
//		tabbedpane.addTab("  Chat  ", chatTab);
//		chatTab.setText("testだよ～");
//		chatTab.setFont(font);
//		tabbedpane.addTab("  Memo  ", memoTab);
		tabbedpane.setFont(font);

		//NumBreTableのインスタンス作成
		nbTable = new NumBreTable(histTab, e_histTab);

		//layPaneに追加
		layPane.add(tabbedpane);
		layPane.setLayer(tabbedpane, 0);
		tabbedpane.setBounds(420, 270, 530, 465);
	}

	/**
	 * タブに追加されている各機能のリセット処理
	 */
	public void Reset(int myCount, int enemyCount) {
		nbTable.Reset(myCount, enemyCount);
	}
}
