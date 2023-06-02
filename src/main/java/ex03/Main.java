package Ex03;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Values valuesStorage = new Values();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			if (line.isEmpty()) {
				break;
			}
			valuesStorage.append(line);
		}
		valuesStorage.printResult();
	}
}
