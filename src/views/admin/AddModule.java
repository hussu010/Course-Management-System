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
import models.Course;

public class AddModule {

    private JTextField titleField;
    private JLabel messageLabel;
    private JLabel errorTitleLabel;
    private JTextField semesterNumberField;
    private JLabel errorSemesterNumberlLabel;

    public AddModule(JFrame frame, User user, Course course) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Add new module to " + course.getTitle() + " !");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 100, 500, 54);
        panel.add(title);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(50, 180, 42, 16);
        panel.add(titleLabel);

        titleField = new JTextField();
        titleField.setColumns(10);
        titleField.setBounds(50, 210, 332, 40);
        titleField.setBorder(
            BorderFactory.createCompoundBorder(titleField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(titleField);

        JLabel semesterNumberLabel = new JLabel("Semester Number");
        semesterNumberLabel.setBounds(50, 270, 160, 20);
        panel.add(semesterNumberLabel);

        semesterNumberField = new JTextField();
        semesterNumberField.setColumns(10);
        semesterNumberField.setBounds(50, 290, 332, 40);
        semesterNumberField.setBorder(
            BorderFactory.createCompoundBorder(semesterNumberField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(semesterNumberField);

        ActionListener addModuleActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String title = titleField.getText().strip();
                String semesterNumber = semesterNumberField.getText().strip();

                isFormValid.add(FieldValidator.validate(title, errorTitleLabel, "course_title"));
                isFormValid.add(FieldValidator.validate(semesterNumber, errorSemesterNumberlLabel, "id"));

                if (!isFormValid.contains(false)) {
                  try {

                	  int intSemesterNumber = Integer.parseInt(semesterNumber);
                	  ManageModule.createModule(course.getId(), title, intSemesterNumber);

                	  JLabel label = new JLabel("Module created successfully");
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
                              new CourseDetails(frame, user, course.getId());
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

        JButton buttonAddCourse = new JButton("Add Module");
        buttonAddCourse.addActionListener(addModuleActionListener);
        buttonAddCourse.setForeground(new Color(242, 252, 255));
        buttonAddCourse.setOpaque(true);
        buttonAddCourse.setBorderPainted(false);
        buttonAddCourse.setBackground(new Color(0, 0, 0));
        buttonAddCourse.setBounds(50, 360, 315, 43);
        panel.add(buttonAddCourse);

        JButton buttonBack= new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				new CourseDetails(frame, user, course.getId());
				panel.setVisible(false);
		      }
			});
        buttonBack.setForeground(new Color(242, 252, 255));
        buttonBack.setOpaque(true);
        buttonBack.setBorderPainted(false);
        buttonBack.setBackground(Color.gray);
        buttonBack.setBounds(50, 410, 315, 43);
        panel.add(buttonBack);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 130, 320, 60);
        panel.add(messageLabel);

        errorTitleLabel = new JLabel();
        errorTitleLabel.setForeground(Color.RED);
        errorTitleLabel.setBounds(50, 245, 322, 27);
        panel.add(errorTitleLabel);

        errorSemesterNumberlLabel = new JLabel();
        errorSemesterNumberlLabel.setForeground(Color.RED);
        errorSemesterNumberlLabel.setBounds(50, 330, 322, 27);
        panel.add(errorSemesterNumberlLabel);

        panel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(panel);
        panel.setVisible(true);
    }
}
