package Ex03;

import javafx.util.Pair;

import java.text.DecimalFormat;

public class Value {
	final String firstName;
	final String secondName;
	final Double firstValue;
	final Double secondValue;

	private Value(String firstName, String secondName, Double firstValue, Double secondValue) {
		this.firstName = firstName;
		this.secondName = secondName;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	public Value(Value val, Double secondValue) {
		this.firstName = val.firstName;
		this.secondName = val.secondName;
		this.firstValue = val.firstValue;
		this.secondValue = secondValue;
	}

	public static Value getInstance(String line) {
		final String[] strings = line.trim().split("\\s+");
		if (strings.length != 5 ||
			strings[1].isEmpty() ||
			strings[4].isEmpty() ||
			!strings[2].equals("=")) {
			return null;
		}
		Double firstValue = null;
		Double secondValue = null;
		try {
			firstValue = Double.valueOf(strings[0]);
			secondValue = Double.valueOf(strings[3]);
		} catch (Exception ignored) {}
		if (firstValue == null) {
			return null;
		}
		if (strings[3].equals("?")) {
			secondValue = null;
		} else if (secondValue == null) {
			return null;
		}
		return new Value(
				strings[1], strings[4], firstValue, secondValue);
	}

	public Pair<String, String> getPair() {
		return new Pair<>(firstName, secondName);
	}

	public Pair<String, String> getReversePair() {
		return new Pair<>(secondName, firstName);
	}

	public String toString() {
		DecimalFormat format = new DecimalFormat("0.#");
		if (secondValue != null) {
			return (format.format(firstValue) + " " + firstName + " = " + format.format(secondValue) + " " + secondName);
		} else {
			return "Conversion not possible.";
		}
	}
}
