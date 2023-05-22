import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class Ex01 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		ex01func03(sc.nextInt());
	}

	private static void ex01func01(int n) {
		for (int i = 1; i <= n; ++i) {
			if (i % 3 == 0 && i % 5 == 0) {
				System.out.println("FooBar");
			} else if (i % 3 == 0) {
				System.out.println("Foo");
			} else if (i % 5 == 0) {
				System.out.println("Bar");
			} else {
				System.out.println(i);
			}
		}
	}

	private static void ex01func02(int n) {
		ex01func02(1, n);
	}
	private static void ex01func02(int i, int n) {
		if (i <= n) {
			String text = (i % 3 == 0 ? "Foo" : "") + (i % 5 == 0 ? "Bar" : "");
			System.out.println(
					text + (text.isEmpty() ? i : "")
			);
			ex01func02(i + 1, n);
		}
	}

	private static void ex01func03(int n) {
		Integer[] mask = new Integer[n];
		Arrays.fill(mask, 0);
		IntStream.range(0, n)
				.peek(x -> {
					int y = x + 1;
					if (y % 3 == 0) mask[x] += 1;
					if (y % 5 == 0) mask[x] += 2;
				})
				.forEach(x -> {
					String res;
					switch (mask[x]) {
						case 1:
							res = "Foo";
							break;
						case 2:
							res = "Bar";
							break;
						case 3:
							res = "FooBar";
							break;
						default:
							res = String.valueOf(x + 1);
					}
					System.out.println(res);
				});
	}

}
