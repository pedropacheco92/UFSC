package owl;

import java.util.Scanner;

public class QuestionHelper {

	private static Scanner sc = new Scanner(System.in);

	private static String getQuestion(String s) {
		if (s.contains("Thing")) {
			return "Is it a Thing? (Y/N)";
		}
		return "Is it a " + s.split("#")[1].split(">")[0] + "? (Y/N)";
	}

	public static boolean handleQuestion(String s) {
		System.out.println(getQuestion(s));
		String response = sc.nextLine();

		return "Y".equals(response.toUpperCase());
	}

}
