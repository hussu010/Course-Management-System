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
import java.awt.Color;

import models.User;
import models.Module;

public class EditModule {

    private JTextField titleField;
    private JLabel messageLabel;
    private JLabel errorTitleLabel;

    public EditModule(JFrame frame, User user, int course_id, Module module) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Edit Module");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 100, 500, 54);
        panel.add(title);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(50, 180, 42, 16);
        panel.add(titleLabel);

        titleField = new JTextField(module.getTitle());
        titleField.setColumns(10);
        titleField.setBounds(50, 210, 332, 43);
        titleField.setBorder(
            BorderFactory.createCompoundBorder(titleField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(titleField);

        ActionListener editModuleActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String title = titleField.getText().strip();
                
                isFormValid.add(FieldValidator.validate(title, errorTitleLabel, "course_title"));

                if (!isFormValid.contains(false)) {
                  try {
                	  ManageModule.editModule(module.getId(), title);

                	  JLabel label = new JLabel("Module updated successfully");
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
                              new CourseDetails(frame, user, course_id);
                              panel.setVisible(false);
                          }
                      });

                  } catch (Exception err) {
                	  System.out.println(err.getMessage());
                      messageLabel.setText(err.getMessage());
                      messageLabel.setForeground(Color.RED);
                  }
              }
            }
        };

        JButton buttonEditCourse = new JButton("Edit Module");
        buttonEditCourse.addActionListener(editModuleActionListener);
        buttonEditCourse.setForeground(new Color(242, 252, 255));
        buttonEditCourse.setOpaque(true);
        buttonEditCourse.setBorderPainted(false);
        buttonEditCourse.setBackground(new Color(0, 0, 0));
        buttonEditCourse.setBounds(50, 350, 315, 43);
        panel.add(buttonEditCourse);

        JButton buttonBack= new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new ModuleDetails(frame, user, course_id, module);
				panel.setVisible(false);
		      }
			});
        buttonBack.setForeground(new Color(242, 252, 255));
        buttonBack.setOpaque(true);
        buttonBack.setBorderPainted(false);
        buttonBack.setBackground(Color.gray);
        buttonBack.setBounds(50, 400, 315, 43);
        panel.add(buttonBack);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 130, 320, 60);
        panel.add(messageLabel);

        errorTitleLabel = new JLabel();
        errorTitleLabel.setForeground(Color.RED);
        errorTitleLabel.setBounds(50, 250, 322, 27);
        panel.add(errorTitleLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);
    }
}
