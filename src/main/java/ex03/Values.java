package Ex03;

import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Values {
	private final HashMap<Pair<String, String>, Double> dictionary = new HashMap<>();
	private final List<Value> unknown = new ArrayList<>();

	private Double find(String first, String second, Double index, Map<Pair<String, String>, Double> dict) {
		Double res = null;
		Map<Pair<String, String>, Double> dictForChild = new HashMap<>(dict);
		Map<String, Double> dictForIterate = dict.entrySet().stream()
				.filter(x -> x.getKey().getKey().equals(first))
				.collect(Collectors.toMap(p -> p.getKey().getValue(), Map.Entry::getValue));
		for (Map.Entry<String, Double> entry : dictForIterate.entrySet()) {
			if (entry.getKey().equals(second)) {
				res = index * entry.getValue();
				break;
			} else {
				dictForChild.remove(new Pair<>(first, entry.getKey()));
				dictForChild.remove(new Pair<>(entry.getKey(), first));
				res = find(entry.getKey(), second, index * entry.getValue(), dictForChild);
				if (res != null) {
					break;
				}
			}
		}
		return res;
	}
	private Double find(String first, String second) {
		return find(first, second, 1.0, dictionary);
	}

	private void append(Value val) {
		if (val == null) {
			return;
		}
		if (val.secondValue != null) {
			dictionary.put(val.getPair(), val.secondValue / val.firstValue);
			dictionary.put(val.getReversePair(), val.firstValue / val.secondValue);
		} else {
			Double resultMultiply = find(val.firstName, val.secondName);
			if (resultMultiply != null) {
				resultMultiply *= val.firstValue;
			}
			Value newVal = new Value(val, resultMultiply);
			unknown.add(newVal);
		}
	}

	public void append(String line) {
		append(Value.getInstance(line));
	}

	public void printResult() {
		unknown.forEach(System.out::println);
	}
}
