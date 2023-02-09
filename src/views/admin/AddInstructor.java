package views.admin;

import javax.swing.*;

import services.admin.ManageModule;
import services.common.FieldValidator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

import models.User;
import models.Module;

import views.common.Login;

public class AddInstructor {

    private JTextField instructorIdField;
    private JLabel messageLabel;
    private JLabel errorInstructorIdlLabel;

    public AddInstructor(JFrame frame, User user, Module module) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton backButton = new JButton("<- Back");
		backButton.setBounds(340, 20, 150, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ModuleDetails(frame, user, module.getCourseId(), module);
				panel.setVisible(false);
		      }
			});
		panel.add(backButton);

		JButton logoutButton = new JButton("Logout ->");
		logoutButton.setBounds(850, 20, 125, 30);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Login(frame);
				panel.setVisible(false);
		      }
			});
		panel.add(logoutButton);

        JLabel title = new JLabel("Add Instructor!!");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 100, 352, 30);
        panel.add(title);

        JLabel instructorIdLbl = new JLabel("Instructor ID");
        instructorIdLbl.setBounds(50, 150, 150, 16);
        panel.add(instructorIdLbl);

        instructorIdField = new JTextField();
        instructorIdField.setColumns(10);
        instructorIdField.setBounds(50, 180, 300, 43);
        instructorIdField.setBorder(
            BorderFactory.createCompoundBorder(instructorIdField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(instructorIdField);

        ActionListener addInstructorActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String instructorId = instructorIdField.getText().strip();

                isFormValid.add(FieldValidator.validate(instructorId, errorInstructorIdlLabel, "id"));

                if (!isFormValid.contains(false)) {
                	try {
                		int intInstructorId = Integer.parseInt(instructorId);
                    	ManageModule.enrollModule(intInstructorId, module.getId());

                    	JLabel label = new JLabel("Instructor added to module!!");
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
                                new ModuleDetails(frame, user, module.getCourseId(), module);
                                panel.setVisible(false);
                            }
                        });

                    } catch (Exception err) {
                        messageLabel.setText(err.getMessage());
                        messageLabel.setForeground(Color.RED);
                    }
                }

            }
        };

        JButton btnAddInstructor= new JButton("Add Instructor");
        btnAddInstructor.addActionListener(addInstructorActionListener);
        btnAddInstructor.setForeground(new Color(242, 252, 255));
        btnAddInstructor.setOpaque(true);
        btnAddInstructor.setBorderPainted(false);
        btnAddInstructor.setBackground(new Color(0, 0, 0));
        btnAddInstructor.setBounds(50, 250, 300, 40);
        panel.add(btnAddInstructor);

        try {
        	List<User> instructorList = ManageModule.getAllUserOfRole("INSTRUCTOR");
        	
        	String[] columnNames = {"id", "name", "email"};
        	Object[][] data = new Object[instructorList.size()][3];

        	for (int i = 0; i < instructorList.size(); i++) {
        	  User item = instructorList.get(i);
        	  data[i][0] = item.getId();
        	  data[i][1] = item.getName();
        	  data[i][2] = item.getEmail();
        	}

        	JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(50, 300, 924, 418);
            panel.add(scrollPane);
        } catch (Exception err) {
            messageLabel.setText(err.getMessage());
            messageLabel.setForeground(Color.RED);
        }

        errorInstructorIdlLabel = new JLabel();
        errorInstructorIdlLabel.setForeground(Color.RED);
        errorInstructorIdlLabel.setBounds(50, 220, 322, 27);
        panel.add(errorInstructorIdlLabel);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 200, 320, 60);
        panel.add(messageLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);
    }
}
