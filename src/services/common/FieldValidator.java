package services.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;

public class FieldValidator {

	public static boolean validate(String text, JLabel label, String field) {
		switch (field) {
			case "email":
				String emailRegex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
				Pattern emailPattern = Pattern.compile(emailRegex);
				Matcher emailMatcher = emailPattern.matcher(text);
				if (!emailMatcher.matches()) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
			case "password":
				String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
				Pattern passwordPattern = Pattern.compile(passwordRegex);
				Matcher passwordMatcher = passwordPattern.matcher(text);

				if (!passwordMatcher.matches()) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
			case "course_title":
				if (text.length() < 6) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
			case "id":
				String integerRegex = "^[-+]?\\d+$";
				Pattern integerPattern = Pattern.compile(integerRegex);
				Matcher integerMatcher = integerPattern.matcher(text);

				if (!integerMatcher.matches()) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
			default:
				if (text.length() < 3) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
		}
	}

}
