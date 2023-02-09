package views.common;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.util.ArrayList;

import services.common.Auth;
import services.common.FieldValidator;

public class Register {

    private JTextField emailField;
    private JTextField fullNameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JLabel errorEmailLabel;
    private JLabel errorPasswordLabel;
    private JLabel errorFullNameLabel;

    public Register(JFrame frame) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Please register your account!!");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(52, 140, 352, 54);
        panel.add(title);

        JLabel fullNameLabel = new JLabel("Full Name");
        fullNameLabel.setBounds(55, 190, 100, 45);
        panel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setColumns(10);
        fullNameField.setBounds(52, 230, 332, 43);
        fullNameField.setBorder(
            BorderFactory.createCompoundBorder(fullNameField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(fullNameField);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setBounds(55, 293, 42, 16);
        panel.add(emailLbl);

        emailField = new JTextField();
        emailField.setColumns(10);
        emailField.setBounds(52, 315, 332, 43);
        emailField.setBorder(
            BorderFactory.createCompoundBorder(emailField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(emailField);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setBounds(55, 384, 80, 16);
        panel.add(passwordLbl);

        passwordField = new JPasswordField();
        passwordField.setColumns(10);
        passwordField.setBounds(52, 401, 332, 43);
        passwordField.setBorder(
            BorderFactory.createCompoundBorder(passwordField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(passwordField);
        
        ActionListener registerActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String email = emailField.getText().strip();
                String fullName = fullNameField.getText().strip();
                String password = new String(passwordField.getPassword()).strip();

                isFormValid.add(FieldValidator.validate(email, errorEmailLabel, "email"));
                isFormValid.add(FieldValidator.validate(password, errorPasswordLabel, "password"));
                isFormValid.add(FieldValidator.validate(fullName, errorFullNameLabel, "full name"));

                if (!isFormValid.contains(false)) {
                    try {
                        Auth.createUser(fullName, email, password, "STUDENT");

                        JLabel label = new JLabel("Account created successfully");
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
                                new Login(frame);
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

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(registerActionListener);
        btnRegister.setForeground(new Color(242, 252, 255));
        btnRegister.setOpaque(true);
        btnRegister.setBorderPainted(false);
        btnRegister.setBackground(new Color(0, 0, 0));
        btnRegister.setBounds(49, 489, 315, 43);
        panel.add(btnRegister);

        JLabel signupLabel = new JLabel("Already Have Account? ");
        signupLabel.setEnabled(false);
        signupLabel.setBounds(107, 606, 200, 27);
        panel.add(signupLabel);

        JLabel loginButton = new JLabel("Login");
        loginButton.setBounds(280, 606, 42, 27);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Login(frame);
                panel.setVisible(false);
            }
        });
        panel.add(loginButton);

        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 160, 320, 60);
        panel.add(messageLabel);

        errorEmailLabel = new JLabel("");
        errorEmailLabel.setForeground(Color.RED);
        errorEmailLabel.setBounds(55, 350, 322, 27);
        panel.add(errorEmailLabel);

        errorFullNameLabel = new JLabel("");
        errorFullNameLabel.setForeground(Color.RED);
        errorFullNameLabel.setBounds(55, 270, 322, 27);
        panel.add(errorFullNameLabel);

        errorPasswordLabel = new JLabel("");
        errorPasswordLabel.setForeground(Color.RED);
        errorPasswordLabel.setBounds(55, 435, 322, 27);
        panel.add(errorPasswordLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);

    }
}
