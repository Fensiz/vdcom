#!/usr/bin/env xcrun swift
import Foundation

struct Pair: Hashable {
	init(_ a: String, _ b: String) {
		self.a = a
		self.b = b
	}
	let a: String
	let b: String
}

struct Value {
	let firstName: String
	let secondName: String
	let firstValue: Decimal
	let secondValue: Decimal?
	
	init?(_ line: String) {
		let strings: [String] = line.trimmingCharacters(in: .whitespacesAndNewlines).components(separatedBy: " ")
		guard
			strings.count >= 5,
			let fVal = Decimal(string: strings[0]),
			!strings[1].isEmpty,
			!strings[4].isEmpty,
			strings[2] == "="
		else { return nil }
		firstValue = fVal
		firstName = strings[1]
		
		secondName = strings[4]
		if strings[3] == "?" {
			secondValue = nil
		} else if let sVal = Decimal(string: strings[3]) {
			secondValue = sVal
		} else {
			return nil
		}
	}
	
	init(_ val: Value, _ secondValue: Decimal?) {
		self.firstName = val.firstName
		self.secondName = val.secondName
		self.firstValue = val.firstValue
		self.secondValue = secondValue
	}
	
	func getPair() -> Pair {
		Pair(firstName, secondName)
	}
	func getReversePair() -> Pair {
		Pair(secondName, firstName)
	}
	
	var solve: String {
		get {
			if let secondValueUnwarped = secondValue {
				return "\(firstName) \(firstValue) = \(secondName) \(secondValueUnwarped)"
			} else {
				return "Conversion not possible."
			}
		}
	}
}


class Values {
	private var dictionary: [Pair : Decimal] = [:]
	private var unknowns: [Value] = []
	
	init(_ vals: Value?...) {
		append(vals)
	}
	
	func printInfo() {
		print("Unknowns: \(unknowns.count)")
		print("Dict: \(dictionary.count)")
	}
	
	func append(_ val: Value?) {
		guard let unwarped = val else { return }
		if let unwarpedSecondValue = unwarped.secondValue {
			dictionary[unwarped.getPair()] = unwarpedSecondValue / unwarped.firstValue
			dictionary[unwarped.getReversePair()] = unwarped.firstValue / unwarpedSecondValue
		} else {
			let resultMultiply = find(first: unwarped.firstName, second: unwarped.secondName)
			var newSecondValue: Decimal?
			if let res = resultMultiply {
				
				newSecondValue = res * unwarped.firstValue
			} else {
				newSecondValue = resultMultiply
			}
			let val = Value(unwarped, newSecondValue)
			unknowns.append(val)
		}

	}
	
	func append(_ vals: Value?...) {
		append(vals)
	}
	
	func append(_ vals: [Value?]) {
		for val in vals {
			append(val)
		}
	}
	
	func append(_ line: String) {
		append(Value(line))
	}
	
	func find(first: String, second: String) -> Decimal? {
		find(first: first, second: second, index: 1, dict: dictionary)
	}
	
	func find(first: String, second: String, index: Decimal, dict: [Pair : Decimal]) -> Decimal? {
		var res: Decimal? = nil
		var dictionary = dict
		
		let temp = dict
			.filter { (key, _) in
				key.a == first
			}
			.map { (key, val) in
				(key.b, val)
			}
		
		for (key, val) in temp {
			if key == second {
				res = index * val
				break
			} else {
				dictionary[Pair(first, key)] = nil
				dictionary[Pair(key, first)] = nil
				res = find(first: key,
						   second: second,
						   index: index * val,
						   dict: dictionary)
				if res != nil {
					break
				}
			}
		}
		return res
	}
	
	func printResult() {
		unknowns.forEach { val in
			print("\(val.solve)")
		}
		
	}
}

var myClass = Values()

while let line = readLine() {
    if line == "" {
        break
    }
	myClass.append(line)
}

//myClass.printInfo()
myClass.printResult()


/*
 1024 byte = 1 kilobyte
 2 bar = 12 ring
 16.8 ring = 2 pyramid
 4 hare = 1 cat
 5 cat = 0.5 giraffe 1 byte = 8 bit
 15 ring = 2.5 bar
 1 pyramid = ? bar
 1 giraffe = ? hare
 0.5 byte = ? cat
 2 kilobyte = ? bit
 
 */


