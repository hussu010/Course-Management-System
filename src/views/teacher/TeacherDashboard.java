package views.teacher;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.User;
import services.admin.ManageModule;
import views.admin.CourseList;
import views.admin.ModuleDetails;
import views.common.Login;
import models.Module;

public class TeacherDashboard {

	private JLabel messageLabel;

	public TeacherDashboard(JFrame frame, User user) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);
		
		JLabel welcomeUserTitle = new JLabel("Welcome " + user.getName());
		welcomeUserTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		welcomeUserTitle.setBounds(50, 20, 449, 37);
		outPanel.add(welcomeUserTitle);

		JLabel title = new JLabel("Your Modules");
		title.setFont(new Font("Futura", Font.PLAIN, 20));
		title.setBounds(50, 50, 449, 37);
		outPanel.add(title);

		if (!user.getRole().equals("INSTRUCTOR")) {
			JButton coursesButton = new JButton("All Course");
			coursesButton.setBounds(680, 20, 150, 30);
			coursesButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new CourseList(frame, user);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(coursesButton);
		}

		JButton logoutButton = new JButton("Logout ->");
		logoutButton.setBounds(850, 20, 125, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login(frame);
				outPanel.setVisible(false);
		      }
			});
		outPanel.add(logoutButton);

		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 3, 10, 10));
        panel.setBounds(50, 100, 924, 568);

        try {
        	List<Module> moduleList = ManageModule.getUserAllModules(user.getId());
        	for (Module module : moduleList) {
            	JPanel cardPanel = new JPanel();
            	JPanel buttonPanel = new JPanel();

            	JLabel courseText = new JLabel("Course: " + module.getCourseTitle());
            	courseText.setFont(new Font("Font.SERIF", Font.PLAIN, 18));
            	JLabel cardText = new JLabel("Module Name: " + module.getTitle());
            	cardText.setFont(new Font("Font.SERIF", Font.PLAIN, 18));

            	cardPanel.setLayout(new GridLayout(3, 0));
            	buttonPanel.setLayout(new GridLayout(0,2,20,20));
            	JButton viewModuleButton = new JButton("Module Details");
            	viewModuleButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					new ModuleDetails(frame, user, module.getCourseId(), module);
    					outPanel.setVisible(false);
    			      }
    				});
        		buttonPanel.add(viewModuleButton);

        		JButton switchEnrollModuleButton = new JButton("UNENROLL");
        		ActionListener enrollActionListener = new ActionListener() {
    	            public void actionPerformed(ActionEvent e) {
    	            	try {
    	            		ManageModule.unenrollModule(user.getId(), module.getId());
        	            	JLabel label = new JLabel("unenrolled from module!");
                            label.setHorizontalAlignment(JLabel.CENTER);

                            JDialog dialog = new JDialog();
                            dialog.setAlwaysOnTop(true);
                            dialog.setSize(300, 100);
                            dialog.getContentPane().add(label);

                            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                            dialog.setVisible(true);

                            dialog.addWindowListener(new WindowAdapter() {
                                public void windowClosed(WindowEvent e) {
                                    dialog.dispose();
                                    new TeacherDashboard(frame, user);
                                    outPanel.setVisible(false);
                                }
                            });
    	            	} catch (Exception err) {
    	                    messageLabel.setText(err.getMessage());
    	                    messageLabel.setForeground(Color.RED);
    	                }
    	            }
    			};
    			switchEnrollModuleButton.addActionListener(enrollActionListener);
        		buttonPanel.add(switchEnrollModuleButton);

            	cardPanel.setBorder(BorderFactory.createTitledBorder(""));
            	cardPanel.add(courseText);
        		cardPanel.add(cardText);
        		cardPanel.add(buttonPanel);
        		panel.add(cardPanel);
            }
        	} catch (Exception err) {
        	  System.out.println(err.getMessage());
              messageLabel.setText(err.getMessage());
              messageLabel.setForeground(Color.RED);
          }

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 60, 320, 60);
        outPanel.add(messageLabel);

        outPanel.add(panel);
        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
