package views.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.User;
import services.admin.ManageCourse;
import views.common.Login;
import views.student.StudentDashboard;
import models.Course;

public class CourseList {

	private JLabel messageLabel;

	public CourseList(JFrame frame, User user) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);
		
		JLabel welconeUsserTitle = new JLabel("Welcome " + user.getName());
		welconeUsserTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		welconeUsserTitle.setBounds(50, 20, 449, 37);
		outPanel.add(welconeUsserTitle);

		JLabel title = new JLabel("Courses");
		title.setFont(new Font("Futura", Font.PLAIN, 20));
		title.setBounds(50, 50, 449, 37);
		outPanel.add(title);

		if (user.getRole().equals("ADMIN")) {

			JButton addUserButton = new JButton("Add User");
			addUserButton.setBounds(510, 20, 150, 30);
			addUserButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddUser(frame, user);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(addUserButton);

			JButton addCourseButton = new JButton("Add Courses");
			addCourseButton.setBounds(680, 20, 150, 30);
			addCourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddCourse(frame, user);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(addCourseButton);

		} else if (user.getRole().equals("STUDENT")) {
			JButton dashboardButton = new JButton("Dashboard");
			dashboardButton.setBounds(680, 20, 150, 30);
			dashboardButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new StudentDashboard(frame, user);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(dashboardButton);
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
        	List<Course> courseList = ManageCourse.listAllCourses();
        	for (Course course : courseList) {
            	JPanel cardPanel = new JPanel();

            	JLabel cardText = new JLabel(course.getTitle());
            	cardText.setFont(new Font("Font.SERIF", Font.PLAIN, 18));

            	JLabel cardStatus= new JLabel(course.getStatus());
            	JButton detailsButton = new JButton("Details");
            	detailsButton.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				new CourseDetails(frame, user, course.getId());
        				outPanel.setVisible(false);
        		      }
        			});

            	cardPanel.setLayout(new GridLayout(3,0));
            	cardPanel.setBorder(BorderFactory.createTitledBorder(""));
        		cardPanel.add(cardText);
        		cardPanel.add(cardStatus);
        		cardPanel.add(detailsButton);
        		panel.add(cardPanel);
            }
        } catch (Exception err) {
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
