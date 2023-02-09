package views.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SplashScreen extends JPanel {

	private static final long serialVersionUID = 7320444829026068755L;

	public SplashScreen(JFrame frame) {
		JPanel splashFrame = new JPanel();
		splashFrame.setBackground(new Color(255, 255, 255));
		splashFrame.setBounds(0, 0, 1024, 768);
		frame.getContentPane().add(splashFrame);
		splashFrame.setVisible(true);
		splashFrame.setLayout(null);

		JLabel logoImg = new JLabel(new ImageIcon(getClass().getResource("../../public/logo.png")));
		logoImg.setBounds(487, 250, 100, 100);
		splashFrame.add(logoImg);
		
		JLabel loadingLabel = new JLabel("Loading!!!");
		loadingLabel.setBounds(450, 350, 200, 100);
		splashFrame.add(loadingLabel);
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingLabel.setFont(new Font("Font.SERIF", Font.PLAIN, 32));
		
		int delay = 2000;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				splashFrame.setVisible(false);
				}
			};
			new javax.swing.Timer(delay, taskPerformer).start();
	}
}
