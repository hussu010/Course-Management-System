package views.common;

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
import services.admin.ManageModule;
import models.Module;

public class ResultDetail{
	
	private JLabel messageLabel;
	private JLabel gradeLabel;
	private int fullModuleGrade;

	public ResultDetail(JFrame frame, User user, int semester) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);

		JLabel userNameTitle = new JLabel("Result of " + user.getName() + " | Semester: " + semester);
		userNameTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		userNameTitle.setBounds(50, 60, 768, 30);
		outPanel.add(userNameTitle);

		JButton backButton = new JButton("<- Back");
		backButton.setBounds(340, 20, 150, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ResultList(frame, user);
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

        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(50, 100, 924, 25);
        headerPanel.setLayout(new GridLayout(0, 2, 5, 5));

		JPanel panel = new JPanel();
        panel.setBounds(50, 130, 924, 20);

        JLabel modulesLabel = new JLabel("<--Modules-->");
        modulesLabel.setHorizontalAlignment(JLabel.CENTER);
        modulesLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        headerPanel.add(modulesLabel);

        JLabel marksLabel = new JLabel("<--Marks-->");
        marksLabel.setHorizontalAlignment(JLabel.CENTER);
        marksLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        headerPanel.add(marksLabel);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setBounds(50, 130, 924, 460);
    	resultsPanel.setLayout(new GridLayout(6, 0, 5, 5));
    	resultsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

    	JPanel resultsSummaryPanel = new JPanel();
    	resultsSummaryPanel.setBounds(50, 600, 924, 100);
    	resultsSummaryPanel.setLayout(new GridLayout(3, 0, 5, 5));
    	resultsSummaryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        try {
			List<Module> moduleList = ManageModule.getUserSemesterModules(23, semester);

        	for (Module module : moduleList) {

        		JPanel cardPanel = new JPanel();
        		cardPanel.setLayout(new GridLayout(0,2));

        		int moduleGrade = ManageModule.getUserModuleGrade(23, module.getId());

        		JLabel label = new JLabel(module.getTitle());
        		label.setHorizontalAlignment(JLabel.CENTER);

        		if (moduleGrade == 0) {
            		gradeLabel = new JLabel("UNGRADED");
            		gradeLabel.setHorizontalAlignment(JLabel.CENTER);
        		} else {
            		gradeLabel = new JLabel(moduleGrade + "/ 100");
            		gradeLabel.setHorizontalAlignment(JLabel.CENTER);      			
        		}
                cardPanel.add(label);
                cardPanel.add(gradeLabel);
                resultsPanel.add(cardPanel);
                fullModuleGrade += 100;
            }

        	int finalGrade = ManageModule.getUserSemesterGrade(23, semester);

            JLabel finalGradeLabel = new JLabel("Final Grade: " + finalGrade + "/ " + fullModuleGrade);
            finalGradeLabel.setHorizontalAlignment(JLabel.CENTER);
            resultsSummaryPanel.add(finalGradeLabel);

            float resutPercentage = finalGrade * 100 / fullModuleGrade;

            JLabel resutPercentageLabel = new JLabel("Percentage: " + resutPercentage);
            resutPercentageLabel.setHorizontalAlignment(JLabel.CENTER);
            
            JLabel resutSummaryLabel;
            
            if (resutPercentage > 40) {
            	resutSummaryLabel = new JLabel("Summary: PASSED");
            } else {
            	resutSummaryLabel = new JLabel("Summary: FAILED");
            }

            resutSummaryLabel.setHorizontalAlignment(JLabel.CENTER);

            resultsSummaryPanel.add(resutPercentageLabel);
            resultsSummaryPanel.add(resutSummaryLabel);
        } catch (Exception err) {
          messageLabel.setText(err.getMessage());
          messageLabel.setForeground(Color.RED);
        }

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 60, 320, 60);
        outPanel.add(messageLabel);
        outPanel.add(headerPanel);
        outPanel.add(resultsPanel);
        outPanel.add(resultsSummaryPanel);
        outPanel.add(panel);

        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
