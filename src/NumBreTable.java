package netprog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 * 履歴表示関係のクラス
 * @author ANDO
 *
 */
public class NumBreTable {
		/**
		 * 履歴を表示する用（これがないと行への追加などができない）
		 */
		public DefaultTableModel tableModel = new DefaultTableModel();
		/**
		 * 敵の履歴を表示する用（これがないと行への追加などができない）
		 */
		public DefaultTableModel e_tableModel = new DefaultTableModel();
		/**
		 * 中央揃えする際に使う
		 */
		public DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		/**
		 * 履歴を表示する用
		 */
		public JTable table = new JTable();
		/**
		 *敵の履歴を表示する用
		 */
		public JTable e_table = new JTable();
		/**
		 * カラムの属性名
		 */
		String[] arrayColumn = {"Number", "Hit", "Blow"};
		/**
		 * 行数（自分用）
		 */
		public int MAX_ROW_COUNT = 10;
		/**
		 * 現在の行の位置を記録
		 */
		public int curRowCount = 0;
		/**
		 * 行数（相手用）
		 */
		public int E_MAX_ROW_COUNT = 10;
		/**
		 * 相手の現在の行の位置を記録
		 */
		public int e_curRowCount = 0;
		/**
		 * フォントの初期化
		 */
		Font font = new Font(Font.SANS_SERIF,Font.PLAIN,20);
		/**
		 * 履歴（自分用）スクロール画面
		 */
		public JScrollPane sp = new JScrollPane();
		/**
		 * 履歴（相手用）のスクロール画面
		 */
		public JScrollPane e_sp = new JScrollPane();

		/**
		 * コンストラクタ
		 */
		public NumBreTable(JLabel histTab, JLabel e_histTab) {

			tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
			TableColumn[] col = new TableColumn[3];
			tableModel = new DefaultTableModel(arrayColumn, MAX_ROW_COUNT);
			table = new JTable(tableModel); //（行, 列）を初期化
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//自動サイズ処理をオフにする
			table.setRowHeight(35);//行の高さ
			table.setEnabled(false);//セルの編集を不可にする
			table.setGridColor(Color.white);//枠線の色指定
			table.setFont(font);//文字のフォント指定
			table.setForeground(Color.white);//文字の色
			table.setBackground(new Color(0, 32, 96));//背景色
			//中央揃えの設定
			for(int i = 0; i < arrayColumn.length; i++) {
				col[i] = table.getColumnModel().getColumn(i);
			    col[i].setCellRenderer(tableCellRenderer);
			}

			//カラムの行高変更
			JTableHeader tableHead = table.getTableHeader();
			tableHead.setEnabled(false);//セルの編集を不可にする
			Dimension dimHead = tableHead.getPreferredSize();
			dimHead.height = 60;
			tableHead.setPreferredSize(dimHead);
			tableHead.setResizingAllowed(false);//カラムの自動レイアウトオフ
			tableHead.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,23));//フォントのセット
			tableHead.setForeground(Color.white);//文字の色
			tableHead.setBackground(new Color(5, 5, 255));//背景色
			tableHead.setOpaque(true);//透過をオフにする

			// カラムの列幅、最小列幅、最大列幅を設定
			DefaultTableColumnModel tcm = (DefaultTableColumnModel)table.getColumnModel();
			TableColumn[] tableColumn = new TableColumn[3]; //幅500で考える
			//numberのセル
			tableColumn[0] = tcm.getColumn(0);
			tableColumn[0].setPreferredWidth(292);
			//hitのセル
			tableColumn[1] = tcm.getColumn(1);
			tableColumn[1].setPreferredWidth(100);
			//blowのセル
			tableColumn[2] = tcm.getColumn(2);
			tableColumn[2].setPreferredWidth(100);

			//スクロールバーに追加
			sp = new JScrollPane(table);
			sp.setPreferredSize(new Dimension(500, 400));
			//JScrollPaneをlayPaneに追加する
			histTab.add(sp);
			sp.setBounds(10, 10, 510, 410); //(420, 320, 510, 440);

			//-------------------------------------------------------------------------------------------------------------------
			//JTable関係（敵用）
			//-------------------------------------------------------------------------------------------------------------------
			e_tableModel = new DefaultTableModel(arrayColumn, E_MAX_ROW_COUNT);
			e_table = new JTable(e_tableModel); //（行, 列）を初期化
			e_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//自動サイズ処理をオフにする
			e_table.setRowHeight(35);//行の高さ
			e_table.setEnabled(false);//セルの編集を不可にする
			e_table.setGridColor(Color.white);//枠線の色指定
			e_table.setFont(font);//文字のフォント指定
			e_table.setForeground(Color.white);//文字の色
			e_table.setBackground(new Color(128, 0, 0));//背景色
			//中央揃えの設定
			for(int i = 0; i < arrayColumn.length; i++) {
				col[i] = e_table.getColumnModel().getColumn(i);
			    col[i].setCellRenderer(tableCellRenderer);
			}

			//カラムの行高変更
			JTableHeader e_tableHead = e_table.getTableHeader();
			e_tableHead.setEnabled(false);//セルの編集を不可にする
			Dimension e_dimHead = e_tableHead.getPreferredSize();
			e_dimHead.height = 60;
			e_tableHead.setPreferredSize(e_dimHead);
			e_tableHead.setPreferredSize(dimHead);
			e_tableHead.setResizingAllowed(false);//カラムの自動レイアウトオフ
			e_tableHead.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,23));//フォントのセット
			e_tableHead.setForeground(Color.white);//文字の色
			e_tableHead.setBackground(new Color(240, 0, 0));//背景色
			e_tableHead.setOpaque(true);//透過をオフにする

			// カラムの列幅、最小列幅、最大列幅を設定
			DefaultTableColumnModel e_tcm = (DefaultTableColumnModel)e_table.getColumnModel();
			TableColumn[] e_tableColumn = new TableColumn[3]; //幅500で考える
			//numberのセル
			e_tableColumn[0] = e_tcm.getColumn(0);
			e_tableColumn[0].setPreferredWidth(292);
			//hitのセル
			e_tableColumn[1] = e_tcm.getColumn(1);
			e_tableColumn[1].setPreferredWidth(100);
			//blowのセル
			e_tableColumn[2] = e_tcm.getColumn(2);
			e_tableColumn[2].setPreferredWidth(100);

			//スクロールバーに追加
			e_sp = new JScrollPane(e_table);
			e_sp.setPreferredSize(new Dimension(500, 400));
			//JScrollPaneをlayPaneに追加する
			e_histTab.add(e_sp);
			e_sp.setBounds(10, 10, 510, 410); //(420, 320, 510, 440);
		}

		/**
		 * リセット処理
		 * 追加された行を取り除いていく
		 * @param myCount
		 * @param enemyCount
		 */
		public void Reset(int myCount, int enemyCount) {
			//自分の履歴の初期化
			for(int i = myCount - 1; i >= 0; i--) {
				this.tableModel.removeRow(i);
			}
			this.curRowCount = 0; //行番号の初期化

			//相手の履歴の初期化
			for(int i = enemyCount - 1; i >= 0; i--) {
				this.e_tableModel.removeRow(i);
			}
			this.e_curRowCount = 0; //行番号の初期化
		}
}
