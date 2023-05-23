import UIKit

struct Value {
	let firstName: String
	let secondName: String
	let firstValue: Decimal
	let secondValue: Decimal
	
	init?(_ line: String) {
		var strings: [String] = line.components(separatedBy: " ")
		print(strings)
		guard
			strings.count >= 5,
			let fVal = Decimal(string: strings[0]),
			let sVal = Decimal(string: strings[3]),
			!strings[1].isEmpty,
			!strings[4].isEmpty,
			strings[2] == "="
		else { return nil }
		firstValue = fVal
		firstName = strings[1]
		secondValue = sVal
		secondName = strings[4]
	}
	
	func found(first: String, second: String) -> Bool {
		first == firstName && second == secondName ||
			second == firstName && first == secondName
	}
	
}

struct Pair: Hashable {
	init(_ a: String, _ b: String) {
		self.a = a
		self.b = b
	}
	let a: String
	let b: String
}

class Values {
	private var arrayOfVals: [Value] = []
	private var dictionary: [Pair : Decimal] = [:]
	
	init(_ vals: Value?...) {
		append(vals)
	}
	
	func append(_ val: Value?) {
		guard let unwarped = val else { return }
		arrayOfVals.append(unwarped)
		dictionary[Pair(unwarped.firstName, unwarped.secondName)] = unwarped.secondValue / unwarped.firstValue
		dictionary[Pair(unwarped.secondName, unwarped.firstName)] = unwarped.firstValue / unwarped.secondValue
	}
	
	func find(first: String, second: String) -> Decimal? {
		find(first: first, second: second, index: 1, dict: dictionary)
	}
	
	func find(first: String, second: String, index: Decimal, dict: [Pair : Decimal]) -> Decimal? {
		var res: Decimal? = nil
		var dictionary = dict
		
		var tempDict: [String : Decimal] = [:]
		dictionary
			.filter {
				$0.key.a == first
			}
			.map { (key, val) in
				tempDict[key.b] = val
			}
		guard !tempDict.isEmpty else { return nil }
		tempDict
			.forEach { (key, val) in
				if key == second {
					res = index * val
					return
				} else {
					dictionary[Pair(first, key)] = nil
					res = find(first: key, second: second, index: index * val, dict: dictionary)
				}
			}
		return res
	}
	
	func append(_ vals: Value?...) {
		append(vals)
	}
	
	func append(_ vals: [Value?]) {
		for val in vals {
			append(val)
		}
	}
	
	func printCount() {
		dictionary.forEach { val in
			print("\(val)")
		}
		
	}
}

var myClass = Values()
myClass.append(
	Value("1024 byte = 1 kilobyte"),
	Value("1024 byte = 1 kilobyte"),
	Value("2 bar = 12 ring"),
	Value("16.8 ring = 2 pyramid"),
	Value("4 hare = 1 cat"),
	Value("5 cat = 0.5 giraffe"),
	Value("1 byte = 8 bit"),
	Value("15 ring = 2.5 bar")
)
let unknownValue: Value = Value("1024 byte = 1 kilobyte")!

myClass.printCount()
print(myClass.find(first: "pyramid", second: "ring"))


