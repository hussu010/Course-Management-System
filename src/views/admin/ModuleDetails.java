package views.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import models.User;
import services.admin.ManageModule;
import views.common.Login;
import views.student.StudentDashboard;
import views.teacher.TeacherDashboard;
import models.Module;

public class ModuleDetails{
	
	private JLabel messageLabel;
	private JLabel gradeLabel;

	public ModuleDetails(JFrame frame, User user, int course_id, Module module) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);

		JLabel moduleTitle = new JLabel(module.getTitle());
		moduleTitle.setFont(new Font("Futura", Font.PLAIN, 20));
		moduleTitle.setBounds(50, 50, 768, 37);
		outPanel.add(moduleTitle);

		JButton backButton = new JButton("<- Back");
		backButton.setBounds(340, 20, 150, 30);
		if (user.getRole().equals("ADMIN")) {
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new CourseDetails(frame, user, course_id);
					outPanel.setVisible(false);
			      }
				});
		} else if (user.getRole().equals("STUDENT")) {
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new StudentDashboard(frame, user);
					outPanel.setVisible(false);
			      }
				});
		} else {
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new TeacherDashboard(frame, user);
					outPanel.setVisible(false);
			      }
				});
		}
		outPanel.add(backButton);

		if (user.getRole().equals("ADMIN")) {
			JButton editButton = new JButton("Edit Module");
			editButton.setBounds(510, 20, 150, 30);
			editButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new EditModule(frame, user, course_id, module);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(editButton);

			JButton addInstructorButton = new JButton("Add Instructor");
			addInstructorButton.setBounds(680, 20, 150, 30);
			addInstructorButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new AddInstructor(frame, user, module);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(addInstructorButton);
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

        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(50, 100, 924, 25);
        headerPanel.setLayout(new GridLayout(0, 2, 5, 5));

		JPanel panel = new JPanel();
        panel.setBounds(50, 130, 924, 568);
        panel.setLayout(new GridLayout(0, 2, 5, 5));

        JLabel instructorLabel = new JLabel("<--Instructors-->");
        instructorLabel.setHorizontalAlignment(JLabel.CENTER);
        instructorLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        headerPanel.add(instructorLabel);

        JPanel instructorPanel = new JPanel();
        JPanel studentsPanel = new JPanel();
        if (user.getRole().equals("ADMIN")) {
        	instructorPanel.setLayout(new GridLayout(0, 2, 5, 5));
        	studentsPanel.setLayout(new GridLayout(0, 2, 5, 5));
        } else {
        	instructorPanel.setLayout(new GridLayout(0, 1, 5, 5));
        	studentsPanel.setLayout(new GridLayout(0, 1, 5, 5));
        }

        JLabel studentLabel = new JLabel("<--Students-->");
        studentLabel.setHorizontalAlignment(JLabel.CENTER);
        studentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        headerPanel.add(studentLabel);

        try {
        	List<User> studentList = ManageModule.getModuleUsers(module.getId(), "STUDENT");
        	List<User> instructorList = ManageModule.getModuleUsers(module.getId(), "INSTRUCTOR");

        	for (User student : studentList) {

        		JPanel cardPanel = new JPanel();
        		cardPanel.setLayout(new GridLayout(2,0));
        		cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        		int grade = ManageModule.getUserModuleGrade(student.getId(), module.getId());

        		JLabel label = new JLabel(student.getName());
        		if (grade == 0) {
            		gradeLabel = new JLabel("Grade: UNGRADED");
        		} else {
            		gradeLabel = new JLabel("Grade: " + grade);        			
        		}
                cardPanel.add(label);
                cardPanel.add(gradeLabel);
                studentsPanel.add(cardPanel);

                if (user.getRole().equals("ADMIN") || user.getRole().equals("INSTRUCTOR")) {
                	JButton showDialogButton = new JButton("Mark");
                    showDialogButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JDialog dialog = new JDialog(frame, "Mark student on module", true);
                            Container dialogContent = dialog.getContentPane();
                            dialogContent.setLayout(new GridLayout(0, 2, 20, 20));

                            JLabel marksLbl = new JLabel("Marks");

                            JTextField gradeField = new JTextField(20);
                            JButton okButton = new JButton("Submit");
                            okButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                	String grade = gradeField.getText().strip();
                                	int intGrade= Integer.parseInt(grade);
                                	try {
                                		ManageModule.addUserGrade(student.getId(), module.getId(), intGrade);
                                		JLabel label = new JLabel("User marked successfully");
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
                                                outPanel.setVisible(false);
                                            }
                                        });
                                	 } catch (Exception err) {
                                         messageLabel.setText(err.getMessage());
                                         messageLabel.setForeground(Color.RED);
                                     }
                                    dialog.dispose();
                                }
                            });

                            JButton cancelButton = new JButton("Cancel");
                            cancelButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    dialog.dispose();
                                }
                            });

                            dialogContent.add(marksLbl);
                            dialogContent.add(gradeField);
                            dialogContent.add(okButton);
                            dialogContent.add(cancelButton);
                            dialog.pack();
                            dialog.setVisible(true);
                        }
                    });
                    studentsPanel.add(showDialogButton);
                }
              }

        	for (User instructor : instructorList) {
        		JLabel label = new JLabel(instructor.getName());
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

                ActionListener removeInstructorActionListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	try {
                    		ManageModule.unenrollModule(instructor.getId(), module.getId());

                    		JLabel label = new JLabel("Instructor removed from module!");
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
                                    outPanel.setVisible(false);
                                }
                            });
                    	} catch (Exception err) {
                      	  System.out.println(err.getMessage());
                            messageLabel.setText(err.getMessage());
                            messageLabel.setForeground(Color.RED);
                        }
                    }
                };

                if (user.getRole().equals("ADMIN")) {
                	JButton removeButton = new JButton("Remove");
                    removeButton.addActionListener(removeInstructorActionListener);
                    instructorPanel.add(label);
                    instructorPanel.add(removeButton);
                } else {
                	instructorPanel.add(label);
                }
              }
        } catch (Exception err) {
          messageLabel.setText(err.getMessage());
          messageLabel.setForeground(Color.RED);
        }

        panel.add(instructorPanel);
        panel.add(studentsPanel);

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        outPanel.add(scrollPane, BorderLayout.NORTH);

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 60, 320, 60);
        outPanel.add(messageLabel);
        outPanel.add(headerPanel);
        outPanel.add(panel);

        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
