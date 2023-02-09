package views.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.User;
import services.admin.ManageCourse;
import views.common.Login;
import models.Course;

public class DeleteCourse {
	
	private JLabel messageLabel;
	
	public DeleteCourse(JFrame frame, User user, Course course) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);
		
		JLabel courseTitle = new JLabel("Confirm delete " + course.getTitle());
		courseTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		courseTitle.setBounds(50, 50, 768, 37);
		outPanel.add(courseTitle);

		JLabel pageTitle = new JLabel("Status: " + course.getStatus());
		pageTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		pageTitle.setBounds(50, 75, 449, 37);
		outPanel.add(pageTitle);

		JButton backButton = new JButton("<- Back");
		backButton.setBounds(680, 20, 150, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CourseList(frame, user);
				outPanel.setVisible(false);
		      }
			});
		outPanel.add(backButton);
		
		JButton logoutButton = new JButton("Logout ->");
		logoutButton.setBounds(850, 20, 125, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login(frame);
				outPanel.setVisible(false);
		      }
			});
		outPanel.add(logoutButton);
		
		ActionListener deleteActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
	                ManageCourse.deleteCourse(course.getId());

	                JLabel label = new JLabel("Course deleted successfully");
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
                            new CourseList(frame, user);
                            outPanel.setVisible(false);
                        }
                    });
                } catch (Exception err) {
                  messageLabel.setText(err.getMessage());
                  messageLabel.setForeground(Color.RED);
              }
            }
        };

		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(50, 120, 150, 30);
		deleteButton.addActionListener(deleteActionListener);
		outPanel.add(deleteButton);

		JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));
        panel.setBounds(50, 130, 924, 568);
        
        messageLabel = new JLabel();
        messageLabel.setBounds(50, 60, 320, 60);
        outPanel.add(messageLabel);

        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
