package views.admin;

import java.awt.Color;
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

import models.User;
import services.admin.ManageCourse;
import services.admin.ManageModule;
import views.common.Login;
import models.Course;
import models.Module;
import models.UserModule;

public class CourseDetails {
	
	private JLabel messageLabel;
	private JButton switchEnrollModuleButton;
	
	public CourseDetails(JFrame frame, User user, int course_id) {

		JPanel outPanel = new JPanel();
		outPanel.setLayout(null);

		try {
			Course course = ManageCourse.getCourse(course_id);
			JLabel courseTitle = new JLabel(course.getTitle());
			courseTitle.setFont(new Font("Futura", Font.PLAIN, 20));
			courseTitle.setBounds(50, 50, 768, 37);
			outPanel.add(courseTitle);

			JLabel pageTitle = new JLabel("Status: " + course.getStatus() + "; Modules:");
			pageTitle.setFont(new Font("Futura", Font.PLAIN, 20));
			pageTitle.setBounds(50, 75, 449, 37);
			outPanel.add(pageTitle);

			JButton backButton = new JButton("<- Back");
			backButton.setBounds(170, 20, 150, 30);
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new CourseList(frame, user);
					outPanel.setVisible(false);
			      }
				});
			outPanel.add(backButton);

			if (user.getRole().equals("ADMIN")) {
				JButton editButton = new JButton("Edit Course");
				editButton.setBounds(340, 20, 150, 30);
				editButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new EditCourse(frame, user, course);
						outPanel.setVisible(false);
				      }
					});
				outPanel.add(editButton);

				JButton deleteButton = new JButton("Delete Course");
				deleteButton.setBounds(510, 20, 150, 30);
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new DeleteCourse(frame, user, course);
						outPanel.setVisible(false);
				      }
					});
				outPanel.add(deleteButton);

				JButton addCourseButton = new JButton("Add Module");
				addCourseButton.setBounds(680, 20, 150, 30);
				addCourseButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						new AddModule(frame, user, course);
						outPanel.setVisible(false);
				      }
					});
				outPanel.add(addCourseButton);	
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

			JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(8, 2, 10, 10));
	        panel.setBounds(50, 130, 924, 568);

			List<Module> moduleList = ManageModule.listCourseModule(course.getId());
			List<UserModule> userModuleList = ManageModule.getUserModules(user.getId());
        	for (Module module : moduleList) {
            	JPanel cardPanel = new JPanel();
            	JPanel buttonPanel = new JPanel();

            	JLabel cardText = new JLabel(module.getTitle());
            	cardText.setFont(new Font("Font.SERIF", Font.PLAIN, 18));

            	JLabel levelText = new JLabel("Level: " + module.getSemesterNumber());
            	levelText.setFont(new Font("Font.SERIF", Font.BOLD, 16));

            	cardPanel.setLayout(new GridLayout(3,0));
            	buttonPanel.setLayout(new GridLayout(0,2,20,20));
            	JButton viewModuleButton = new JButton("Module Details");
            	viewModuleButton.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					new ModuleDetails(frame, user, course.getId(), module);
    					outPanel.setVisible(false);
    			      }
    				});
        		buttonPanel.add(viewModuleButton);

        		if (user.getRole().equals("ADMIN")) {
        			JButton editModuleButton = new JButton("Edit Module");    			
        			editModuleButton.addActionListener(new ActionListener() {
        				public void actionPerformed(ActionEvent e) {
        					new EditModule(frame, user, course.getId(), module);
        					outPanel.setVisible(false);
        			      }
        				});
            		buttonPanel.add(editModuleButton);

        		} else if (user.getRole().equals("STUDENT")) {
        			
        			boolean userIsEnrolled = ManageModule.isUserEnrolledToModule(userModuleList, user.getId(), module.getId());
        			        			
        			if (!userIsEnrolled) {
        				switchEnrollModuleButton = new JButton("Enroll");
            			ActionListener enrollActionListener = new ActionListener() {
            	            public void actionPerformed(ActionEvent e) {

            	            	try {
            	            		ManageModule.enrollModule(user.getId(), module.getId());
                	            	JLabel label = new JLabel("Enrolled to module successfully!");
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
                                            outPanel.setVisible(false);
                                        }
                                    });
            	            	} catch (Exception err) {
            	                    messageLabel.setText(err.getMessage());
            	                    messageLabel.setForeground(Color.RED);
            	                }
            	            }
            			};
            			switchEnrollModuleButton.addActionListener(enrollActionListener);
        			} else {
        				switchEnrollModuleButton = new JButton("Enrolled");
        			}

        			buttonPanel.add(switchEnrollModuleButton);
        		}

            	cardPanel.setBorder(BorderFactory.createTitledBorder(""));
            	cardPanel.add(cardText);
            	cardPanel.add(levelText);
        		cardPanel.add(buttonPanel);
        		panel.add(cardPanel);
            }
        	
        	outPanel.add(panel);

		} catch (Exception err) {
	          messageLabel.setText(err.getMessage());
	          messageLabel.setForeground(Color.RED);
	      }

        messageLabel = new JLabel();
        messageLabel.setBounds(50, 60, 320, 60);
        outPanel.add(messageLabel);

        outPanel.setBounds(0, 0, 1024, 768);
        frame.getContentPane().add(outPanel);
        outPanel.setVisible(true);
	}
}
