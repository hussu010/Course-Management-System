import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import views.common.*;
import services.common.*;
import services.admin.*;

public class App {

	private JFrame appFrame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.appFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public App() {
		initialize();
	}

	private void initialize() {
		appFrame = new JFrame();
		appFrame.setTitle("Dexter - Your CMS");
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.getContentPane().setLayout(null);
		appFrame.setSize(1024, 768);
		appFrame.setResizable(false);

		try {
			new SplashScreen(appFrame);
			ManageDatabase db = new ManageDatabase("jdbc:mysql://localhost:3306", "hello", "password");
			new Auth(db);
			new ManageCourse(db);
			new ManageModule(db);
			new Login(appFrame);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,
					e,
					"Error 500: Server Communication Failed", JOptionPane.ERROR_MESSAGE);
			System.exit(500);
		}
	}
}
