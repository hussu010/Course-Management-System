package views.admin;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;

import models.User;
import services.common.Auth;
import services.common.FieldValidator;
import views.common.Login;

public class AddUser {

	private JTextField emailField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JLabel errorEmailLabel;
    private JLabel errorPasswordLabel;
    private JLabel errorFullNameLabel;

	public AddUser(JFrame frame, User user) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JButton backButton = new JButton("<- Back");
		backButton.setBounds(340, 20, 150, 30);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CourseList(frame, user);
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

        JLabel title = new JLabel("Create a new user!!");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 140, 352, 54);
        panel.add(title);

        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setBounds(50, 190, 100, 45);
        panel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setColumns(10);
        fullNameField.setBounds(50, 230, 332, 43);
        fullNameField.setBorder(
            BorderFactory.createCompoundBorder(fullNameField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(fullNameField);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setBounds(50, 300, 42, 16);
        panel.add(emailLbl);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(50, 320, 332, 40);
        emailField.setBorder(
            BorderFactory.createCompoundBorder(emailField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(emailField);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setBounds(50, 380, 80, 16);
        panel.add(passwordLbl);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(50, 400, 332, 40);
        passwordField.setBorder(
            BorderFactory.createCompoundBorder(passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(passwordField);

        JLabel roleLbl = new JLabel("Role");
        roleLbl.setBounds(50, 460, 80, 16);
        panel.add(roleLbl);

        String[] roles = {"STUDENT", "INSTRUCTOR", "ADMIN"};
        JComboBox<String> rolesBox = new JComboBox<>(roles);
        rolesBox.setBounds(50, 480, 332, 43);
        panel.add(rolesBox);

        ActionListener registerActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String email = emailField.getText().strip();
                String fullName = fullNameField.getText().strip();
                String password = new String(passwordField.getPassword()).strip();
                String selectedRole = (String) rolesBox.getSelectedItem();

                isFormValid.add(FieldValidator.validate(email, errorEmailLabel, "email"));
                isFormValid.add(FieldValidator.validate(password, errorPasswordLabel, "password"));
                isFormValid.add(FieldValidator.validate(fullName, errorFullNameLabel, "full name"));

                if (!isFormValid.contains(false)) {
                    try {
                        Auth.createUser(fullName, email, password, selectedRole);

                        JLabel label = new JLabel("User created successfully");
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

        JButton btnRegister = new JButton("Create User");
        btnRegister.addActionListener(registerActionListener);
        btnRegister.setForeground(new Color(242, 252, 255));
        btnRegister.setOpaque(true);
        btnRegister.setBorderPainted(false);
        btnRegister.setBackground(new Color(0, 0, 0));
        btnRegister.setBounds(50, 530, 315, 43);
        panel.add(btnRegister);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 160, 320, 60);
        panel.add(messageLabel);

        errorEmailLabel = new JLabel();
        errorEmailLabel.setForeground(Color.RED);
        errorEmailLabel.setBounds(50, 360, 322, 27);
        panel.add(errorEmailLabel);

        errorFullNameLabel = new JLabel();
        errorFullNameLabel.setForeground(Color.RED);
        errorFullNameLabel.setBounds(50, 270, 322, 27);
        panel.add(errorFullNameLabel);

        errorPasswordLabel = new JLabel();
        errorPasswordLabel.setForeground(Color.RED);
        errorPasswordLabel.setBounds(50, 440, 322, 27);
        panel.add(errorPasswordLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);

	}
}
