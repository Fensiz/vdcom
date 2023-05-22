import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Ex02 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		ex02func01(n);
	}

	private static void ex02func01(int n) {

		String fileName = "file";
		File file = new File(fileName);
			try {
			if (!file.exists()) {
				file.createNewFile();
			} else {
				PrintWriter writer = new PrintWriter(fileName);
				writer.print("");
				writer.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Object mtx = new Object();
		Thread thread1 = new MyThread(mtx, n, fileName);
		Thread thread2 = new MyThread(mtx, n, fileName);

		thread1.start();
		thread2.start();

		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		try {
			System.out.println(new String(Files.readAllBytes(Paths.get(fileName))));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
