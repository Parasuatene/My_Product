package netprog;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class NumBreUI {
	private JLabel logo = new JLabel();
	ImageIcon icon = new ImageIcon("logo_image/game_logo.png");

	/**
	 * コンストラクタ
	 * @param layPane
	 */
	public NumBreUI(JLayeredPane layPane){
		int width = 150; int height = 90;
		icon = new ImageIcon(icon.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
		logo.setIcon(icon);
		logo.setBounds(10, 730, width, height);
		layPane.add(logo);
		layPane.setLayer(logo, 0);
	}
}
