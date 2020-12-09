package netprog;

import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class TimerLabel{
	private JLabel label = new JLabel();
	private JLabel label2 = new JLabel();
	private ImageIcon[] timeIcons = { new ImageIcon("time_image/time0.png"), new ImageIcon("time_image/time1.png"), new ImageIcon("time_image/time2.png"), new ImageIcon("time_image/time3.png"), new ImageIcon("time_image/time4.png"),
														new ImageIcon("time_image/time5.png"), new ImageIcon("time_image/time6.png"), new ImageIcon("time_image/time7.png"), new ImageIcon("time_image/time8.png"), new ImageIcon("time_image/time9.png"),
														new ImageIcon("time_image/time10.png"), new ImageIcon("time_image/time11.png"), new ImageIcon("time_image/time12.png"), new ImageIcon("time_image/time13.png"), new ImageIcon("time_image/time14.png"),
														new ImageIcon("time_image/time15.png"), new ImageIcon("time_image/time16.png"), new ImageIcon("time_image/time17.png"), new ImageIcon("time_image/time18.png"), new ImageIcon("time_image/time19.png") };
	private ImageIcon lockIcon = new ImageIcon("time_image/lock.png");
	private int secCount;
	public boolean isRun = true;
	public Timer timer;

	/**
	 * コンストラクタ
	 * @param layPane
	 */
	public TimerLabel(JLayeredPane layPane) {
		for (ImageIcon icon : timeIcons) {
			icon = new ImageIcon(icon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
		}
		lockIcon = new ImageIcon(lockIcon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
		label.setBounds((40+2*115), (290+3*115), 100, 100);
		label2.setBounds((40+2*115), (290+3*115), 100, 100);
		label2.setIcon(lockIcon);
		secCount = 0;
		layPane.add(label);
		layPane.setLayer(label, 2);
		layPane.add(label2);
		layPane.setLayer(label2, 1);
		timer = new Timer();
		timer.schedule(new TimeLabelTask(), 0, 500); //schedule(タスク, 繰り返しを開始するまでのDelay, 繰り返し処理の周期)
	}

	public class TimeLabelTask extends TimerTask{
		@Override
		public void run() {
			if(secCount == 20) {
				label.setVisible(false);
				label2.setVisible(false);
				isRun = false;
			}
			if(isRun) {
				label.setIcon(timeIcons[secCount]);
//				label.setIcon(new ImageIcon("debug.png"));
				secCount++;
			}
		}
	}
}
