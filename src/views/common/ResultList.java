package views.common;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import models.User;
import views.student.StudentDashboard;

public class ResultList{

	public ResultList(JFrame frame, User user) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);

		JLabel moduleTitle = new JLabel("Select the semester you want to view the result of: ");
		moduleTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		moduleTitle.setBounds(50, 50, 768, 37);
		outPanel.add(moduleTitle);

		JButton backButton = new JButton("<- Back");
		backButton.setBounds(340, 20, 150, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StudentDashboard(frame, user);
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

		JPanel panel = new JPanel();
        panel.setBounds(50, 130, 924, 568);
        panel.setLayout(new GridLayout(0, 2, 50, 50));
        
        for (int i = 1; i <= 6; i++) {
        	JButton resultButton = new JButton("Semester " + i);
        	
        	final Integer semester = i;

        	resultButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				new ResultDetail(frame, user, semester);
    				outPanel.setVisible(false);
    		      }
    			});

        	panel.add(resultButton);
        }
        outPanel.add(panel);

        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
