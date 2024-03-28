valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// input.limit >= size(return.businesses[])
pm.test("input.limit >= size(return.businesses[])", () => {
	// Getting value of variable: input_limit
	// Printing value of input_limit variable
	console.log("Printing value of input_limit");
	console.log(input_limit);


	// Getting value of variable: return_businesses_size_array
	return_businesses_size_array = response.businesses;
	if(return_businesses_size_array != null) {
		return_businesses_size_array = return_businesses_size_array.length;
	}

	// Printing value of return_businesses_size_array variable
	console.log("Printing value of return_businesses_size_array");
	console.log(return_businesses_size_array);


	if((input_limit != null) && (!valuesToConsiderAsNull.includes(input_limit)) && (return_businesses_size_array != null) && (!valuesToConsiderAsNull.includes(return_businesses_size_array))) {
		//warning: method daikon.inv.binary.twoScalar.IntGreaterEqual.format(OutputFormat:Postman) needs to be implemented: input.limit >= size(return.businesses[..]);
	}
})
// return.total >= size(return.businesses[])
pm.test("return.total >= size(return.businesses[])", () => {
	// Getting value of variable: return_total
	return_total = response.total;
	// Printing value of return_total variable
	console.log("Printing value of return_total");
	console.log(return_total);


	// Getting value of variable: return_businesses_size_array
	return_businesses_size_array = response.businesses;
	if(return_businesses_size_array != null) {
		return_businesses_size_array = return_businesses_size_array.length;
	}

	// Printing value of return_businesses_size_array variable
	console.log("Printing value of return_businesses_size_array");
	console.log(return_businesses_size_array);


	if((return_total != null) && (!valuesToConsiderAsNull.includes(return_total)) && (return_businesses_size_array != null) && (!valuesToConsiderAsNull.includes(return_businesses_size_array))) {
		//warning: method daikon.inv.binary.twoScalar.IntGreaterEqual.format(OutputFormat:Postman) needs to be implemented: return.total >= size(return.businesses[..]);
	}
})

// 200&businesses
response_businesses = response.businesses
if(response_businesses != null) {
// Printing value of response_businesses variable
console.log("Printing value of response_businesses");
console.log(response_businesses);

	for(response_businesses_index in response_businesses) {
		response_businesses_element = response_businesses[response_businesses_index]
// Printing value of response_businesses_element variable
console.log("Printing value of response_businesses_element");
console.log(response_businesses_element);

// Invariants of this nesting level:
// return.rating >= 1.0
pm.test("return.rating >= 1.0", () => {
	// Getting value of variable: return_rating
	return_rating = response_businesses_element.rating;
	// Printing value of return_rating variable
	console.log("Printing value of return_rating");
	console.log(return_rating);


	if((return_rating != null) && (!valuesToConsiderAsNull.includes(return_rating))) {
		//warning: method daikon.inv.unary.scalar.LowerBoundFloat.format(OutputFormat:Postman) needs to be implemented: return.rating >= 1.0;
	}
})
// return.review_count >= 1
pm.test("return.review_count >= 1", () => {
	// Getting value of variable: return_review_count
	return_review_count = response_businesses_element.review_count;
	// Printing value of return_review_count variable
	console.log("Printing value of return_review_count");
	console.log(return_review_count);


	if((return_review_count != null) && (!valuesToConsiderAsNull.includes(return_review_count))) {
		//warning: method daikon.inv.unary.scalar.LowerBound.format(OutputFormat:Postman) needs to be implemented: return.review_count >= 1;
	}
})
// return.transactions[] elements one of { "delivery", "pickup", "restaurant_reservation" }
pm.test("return.transactions[] elements one of { \"delivery\", \"pickup\", \"restaurant_reservation\" }", () => {
	// Getting value of variable: return_transactions_array
	return_transactions_array = response_businesses_element.transactions;
	// Printing value of return_transactions_array variable
	console.log("Printing value of return_transactions_array");
	console.log(return_transactions_array);


	if((return_transactions_array != null) && (!valuesToConsiderAsNull.includes(return_transactions_array))) {
		//warning: method daikon.inv.unary.stringsequence.EltOneOfString.format(OutputFormat:Postman) needs to be implemented: return.transactions[..] elements one of { "delivery", "pickup", "restaurant_reservation" };
	}
})
// LENGTH(return.location.country)==2
pm.test("LENGTH(return.location.country)==2", () => {
	// Getting value of variable: return_location_country
	return_location_country = response_businesses_element.location;
	if(return_location_country != null) {
		return_location_country = return_location_country.country;
	}
	// Printing value of return_location_country variable
	console.log("Printing value of return_location_country");
	console.log(return_location_country);


	if((return_location_country != null) && (!valuesToConsiderAsNull.includes(return_location_country))) {
		//pm.expect(return_location_country).to.have.length(2);
	}
})
// size(input.price[]) >= 1
pm.test("size(input.price[]) >= 1", () => {
	// Getting value of variable: input_price_size_array
	input_price_size_array = input_price_array;
	if(input_price_size_array != null) {
		input_price_size_array = input_price_size_array.length;
	}

	// Printing value of input_price_size_array variable
	console.log("Printing value of input_price_size_array");
	console.log(input_price_size_array);


	if((input_price_size_array != null) && (!valuesToConsiderAsNull.includes(input_price_size_array))) {
		//warning: method daikon.inv.unary.scalar.LowerBound.format(OutputFormat:Postman) needs to be implemented: size(input.price[..]) >= 1;
	}
})
// size(return.location.display_address[]) >= 1
pm.test("size(return.location.display_address[]) >= 1", () => {
	// Getting value of variable: return_location_display_address_size_array
	return_location_display_address_size_array = response_businesses_element.location;
	if(return_location_display_address_size_array != null) {
		return_location_display_address_size_array = return_location_display_address_size_array.display_address;
	}
	if(return_location_display_address_size_array != null) {
		return_location_display_address_size_array = return_location_display_address_size_array.length;
	}

	// Printing value of return_location_display_address_size_array variable
	console.log("Printing value of return_location_display_address_size_array");
	console.log(return_location_display_address_size_array);


	if((return_location_display_address_size_array != null) && (!valuesToConsiderAsNull.includes(return_location_display_address_size_array))) {
		//warning: method daikon.inv.unary.scalar.LowerBound.format(OutputFormat:Postman) needs to be implemented: size(return.location.display_address[..]) >= 1;
	}
})

	// 200&businesses&categories
	response_businesses_element_categories = response_businesses_element.categories
	if(response_businesses_element_categories != null) {
	// Printing value of response_businesses_element_categories variable
	console.log("Printing value of response_businesses_element_categories");
	console.log(response_businesses_element_categories);

		for(response_businesses_element_categories_index in response_businesses_element_categories) {
			response_businesses_element_categories_element = response_businesses_element_categories[response_businesses_element_categories_index]
	// Printing value of response_businesses_element_categories_element variable
	console.log("Printing value of response_businesses_element_categories_element");
	console.log(response_businesses_element_categories_element);

	// Invariants of this nesting level:
	// size(input.categories[]) >= 1
	pm.test("size(input.categories[]) >= 1", () => {
		// Getting value of variable: input_categories_size_array
		input_categories_size_array = input_categories_array;
		if(input_categories_size_array != null) {
			input_categories_size_array = input_categories_size_array.length;
		}

		// Printing value of input_categories_size_array variable
		console.log("Printing value of input_categories_size_array");
		console.log(input_categories_size_array);


		if((input_categories_size_array != null) && (!valuesToConsiderAsNull.includes(input_categories_size_array))) {
			//warning: method daikon.inv.unary.scalar.LowerBound.format(OutputFormat:Postman) needs to be implemented: size(input.categories[..]) >= 1;
		}
	})

		} // Closing for response_businesses_element
	} // Closing if response_businesses_element

	} // Closing for response
} // Closing if response