package views.common;

import javax.swing.*;

import services.common.Auth;
import services.common.FieldValidator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;

import models.User;
import views.admin.CourseList;
import views.student.*;
import views.teacher.TeacherDashboard;

public class Login {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JLabel errorEmailLabel;
    private JLabel errorPasswordLabel;

    public Login(JFrame frame) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Login!!");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 200, 352, 54);
        panel.add(title);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setBounds(50, 293, 42, 16);
        panel.add(emailLbl);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(50, 315, 330, 40);
        emailField.setBorder(
            BorderFactory.createCompoundBorder(emailField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(emailField);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setBounds(50, 384, 80, 16);
        panel.add(passwordLbl);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(50, 401, 330, 40);
        passwordField.setBorder(
            BorderFactory.createCompoundBorder(passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(passwordField);
        
        ActionListener loginActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String email = emailField.getText().strip();
                String password = new String(passwordField.getPassword()).strip();

                isFormValid.add(FieldValidator.validate(email, errorEmailLabel, "email"));
                isFormValid.add(FieldValidator.validate(password, errorPasswordLabel, "default"));

                if (!isFormValid.contains(false)) {
                    try {
                        User user = Auth.getUser(email, password);
                        if (user.getRole().equals("STUDENT")) {
                        	new StudentDashboard(frame, user);
                        } else if (user.getRole().equals("INSTRUCTOR")) {
                        	new TeacherDashboard(frame, user);
                        } else {
                        	new CourseList(frame, user);
                        };
                        panel.setVisible(false);
                    } catch (Exception err) {
                        messageLabel.setText(err.getMessage());
                        messageLabel.setForeground(Color.RED);
                    }
                }
            }
        };

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(loginActionListener);
        btnLogin.setForeground(new Color(242, 252, 255));
        btnLogin.setOpaque(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setBackground(new Color(0, 0, 0));
        btnLogin.setBounds(50, 489, 330, 40);
        panel.add(btnLogin);

        JLabel signupLabel = new JLabel("Don't have account?");
        signupLabel.setEnabled(false);
        signupLabel.setBounds(50, 550, 150, 27);
        panel.add(signupLabel);

        JLabel loginButton = new JLabel("Register");
        loginButton.setBounds(200, 550, 60, 27);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register(frame);
                panel.setVisible(false);
            }
        });
        panel.add(loginButton);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 235, 320, 60);
        panel.add(messageLabel);

        errorEmailLabel = new JLabel();
        errorEmailLabel.setForeground(Color.RED);
        errorEmailLabel.setBounds(55, 355, 322, 27);
        panel.add(errorEmailLabel);

        errorPasswordLabel = new JLabel();
        errorPasswordLabel.setForeground(Color.RED);
        errorPasswordLabel.setBounds(55, 440, 322, 27);
        panel.add(errorPasswordLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);
    }
}
