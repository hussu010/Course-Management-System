package views.admin;

import javax.swing.*;

import services.admin.ManageCourse;
import services.common.FieldValidator;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.Color;

import models.Course;
import models.User;

public class EditCourse {

    private JTextField titleField;
    private JLabel messageLabel;
    private JLabel errorTitleLabel;

    public EditCourse(JFrame frame, User user, Course course) {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Edit Course!!");
        title.setFont(new Font("Font.SERIF", Font.PLAIN, 20));
        title.setBounds(50, 100, 352, 54);
        panel.add(title);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(50, 180, 42, 16);
        panel.add(titleLabel);

        titleField = new JTextField(course.getTitle());
        titleField.setColumns(10);
        titleField.setBounds(50, 210, 332, 43);
        titleField.setBorder(
            BorderFactory.createCompoundBorder(titleField.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
        panel.add(titleField);
        
        JCheckBox isActiveCheckBox = new JCheckBox("Is Active");
        isActiveCheckBox.setBounds(50, 280, 332, 43);
        panel.add(isActiveCheckBox);
        
        ActionListener addCourseActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList < Boolean > isFormValid = new ArrayList < Boolean > ();
                String title = titleField.getText().strip();
                boolean status = isActiveCheckBox.isSelected();
                
                isFormValid.add(FieldValidator.validate(title, errorTitleLabel, "course_title"));

                if (!isFormValid.contains(false)) {
                  try {
                	  ManageCourse.editCourse(course.getId(), title, status);

                      JLabel label = new JLabel("Course updated successfully");
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
                	  System.out.println(err.getMessage());
                      messageLabel.setText(err.getMessage());
                      messageLabel.setForeground(Color.RED);
                  }
              }
            }
        };

        JButton buttonAddCourse = new JButton("Edit Course");
        buttonAddCourse.addActionListener(addCourseActionListener);
        buttonAddCourse.setForeground(new Color(242, 252, 255));
        buttonAddCourse.setOpaque(true);
        buttonAddCourse.setBorderPainted(false);
        buttonAddCourse.setBackground(new Color(0, 0, 0));
        buttonAddCourse.setBounds(50, 350, 315, 43);
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
