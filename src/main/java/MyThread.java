import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class MyThread extends Thread {

	private final Object        mtx;
	private int                 x = 0;
	private int                 n;
	private String              fileName;

	public MyThread(Object mtx, int n, String fileName) {
		this.mtx = mtx;
		this.n = n;
		this.fileName = fileName;
	}

	private void update() throws IOException, InterruptedException {
		do {
			Thread.sleep(getRandomNumber(0, 500));
			synchronized (mtx) {

				InputStream ins = Files.newInputStream(Paths.get(fileName));
				Scanner obj = new Scanner(ins);
				if (obj.hasNextLine()) {
					x = obj.nextInt();
				}
				obj.close();
				ins.close();

				if (x < n) {
					FileWriter fileWriter = new FileWriter(fileName);
					PrintWriter printWriter = new PrintWriter(fileWriter);
					printWriter.print(x + 1);
					System.out.println("old: " + x + " new: " + (x + 1) + " " +Thread.currentThread().getName());

					printWriter.close();
					fileWriter.close();
				}
			}
		} while (x < n);
	}

	private int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	@Override
	public void run() {
		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
