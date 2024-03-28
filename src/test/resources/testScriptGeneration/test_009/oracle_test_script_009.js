valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// return.limit >= size(return.items[])
pm.test("return.limit >= size(return.items[])", () => {
	// Getting value of variable: return_limit
	return_limit = response.limit;
	// Printing value of return_limit variable
	console.log("Printing value of return_limit");
	console.log(return_limit);


	// Getting value of variable: return_items_size_array
	return_items_size_array = response.items;
	if(return_items_size_array != null) {
		return_items_size_array = return_items_size_array.length;
	}

	// Printing value of return_items_size_array variable
	console.log("Printing value of return_items_size_array");
	console.log(return_items_size_array);


	if((return_limit != null) && (!valuesToConsiderAsNull.includes(return_limit)) && (return_items_size_array != null) && (!valuesToConsiderAsNull.includes(return_items_size_array))) {
		//TODO: Implement;
	}
})
// return.total >= size(return.items[])
pm.test("return.total >= size(return.items[])", () => {
	// Getting value of variable: return_total
	return_total = response.total;
	// Printing value of return_total variable
	console.log("Printing value of return_total");
	console.log(return_total);


	// Getting value of variable: return_items_size_array
	return_items_size_array = response.items;
	if(return_items_size_array != null) {
		return_items_size_array = return_items_size_array.length;
	}

	// Printing value of return_items_size_array variable
	console.log("Printing value of return_items_size_array");
	console.log(return_items_size_array);


	if((return_total != null) && (!valuesToConsiderAsNull.includes(return_total)) && (return_items_size_array != null) && (!valuesToConsiderAsNull.includes(return_items_size_array))) {
		//TODO: Implement;
	}
})

// 200&items
response_items = response.items
if(response_items != null) {
// Printing value of response_items variable
console.log("Printing value of response_items");
console.log(response_items);

	for(response_items_index in response_items) {
		response_items_element = response_items[response_items_index]
// Printing value of response_items_element variable
console.log("Printing value of response_items_element");
console.log(response_items_element);

// Invariants of this nesting level:
// LENGTH(return.id)==22
pm.test("LENGTH(return.id)==22", () => {
	// Getting value of variable: return_id
	return_id = response_items_element.id;
	// Printing value of return_id variable
	console.log("Printing value of return_id");
	console.log(return_id);


	if((return_id != null) && (!valuesToConsiderAsNull.includes(return_id))) {
		//TODO: Implement;
	}
})
// size(return.artists[]) >= 1
pm.test("size(return.artists[]) >= 1", () => {
	// Getting value of variable: return_artists_size_array
	return_artists_size_array = response_items_element.artists;
	if(return_artists_size_array != null) {
		return_artists_size_array = return_artists_size_array.length;
	}

	// Printing value of return_artists_size_array variable
	console.log("Printing value of return_artists_size_array");
	console.log(return_artists_size_array);


	if((return_artists_size_array != null) && (!valuesToConsiderAsNull.includes(return_artists_size_array))) {
		//TODO: Implement;
	}
})

	// 200&items&artists
	response_items_element_artists = response_items_element.artists
	if(response_items_element_artists != null) {
	// Printing value of response_items_element_artists variable
	console.log("Printing value of response_items_element_artists");
	console.log(response_items_element_artists);

	// Invariants of this nesting level:
	// size(return.array[]) >= 1
	pm.test("size(return.array[]) >= 1", () => {
		// Getting value of variable: return_array_size_array
		return_array_size_array = response_items_element_artists;
		if(return_array_size_array != null) {
			return_array_size_array = return_array_size_array.length;
		}

		// Printing value of return_array_size_array variable
		console.log("Printing value of return_array_size_array");
		console.log(return_array_size_array);


		if((return_array_size_array != null) && (!valuesToConsiderAsNull.includes(return_array_size_array))) {
			//TODO: Implement;
		}
	})

// Access to the next nesting level
		for(response_items_element_artists_index in response_items_element_artists) {
			response_items_element_artists_element = response_items_element_artists[response_items_element_artists_index]
// Printing value of response_items_element_artists_element variable
console.log("Printing value of response_items_element_artists_element");
console.log(response_items_element_artists_element);

if(response_items_element_artists_element != null) {
	// Invariants of this nesting level:
	// size(return.array[]) >= 1
	pm.test("size(return.array[]) >= 1", () => {
		// Getting value of variable: return_array_size_array
		return_array_size_array = response_items_element_artists_element;
		if(return_array_size_array != null) {
			return_array_size_array = return_array_size_array.length;
		}

		// Printing value of return_array_size_array variable
		console.log("Printing value of return_array_size_array");
		console.log(return_array_size_array);


		if((return_array_size_array != null) && (!valuesToConsiderAsNull.includes(return_array_size_array))) {
			//TODO: Implement;
		}
	})

// Access to the next nesting level
		for(response_items_element_artists_element_index in response_items_element_artists_element) {
			response_items_element_artists_element_element = response_items_element_artists_element[response_items_element_artists_element_index]
// Printing value of response_items_element_artists_element_element variable
console.log("Printing value of response_items_element_artists_element_element");
console.log(response_items_element_artists_element_element);

		for(response_items_element_artists_element_element_index in response_items_element_artists_element_element) {
			response_items_element_artists_element_element_element = response_items_element_artists_element_element[response_items_element_artists_element_element_index]
	// Printing value of response_items_element_artists_element_element_element variable
	console.log("Printing value of response_items_element_artists_element_element_element");
	console.log(response_items_element_artists_element_element_element);

	// Invariants of this nesting level:
	// return.type == "artist"
	pm.test("return.type == \"artist\"", () => {
		// Getting value of variable: return_type
		return_type = response_items_element_artists_element_element_element.type;
		// Printing value of return_type variable
		console.log("Printing value of return_type");
		console.log(return_type);


		if((return_type != null) && (!valuesToConsiderAsNull.includes(return_type))) {
			//TODO: Implement;
		}
	})
	// size(return.links[]) >= 1
	pm.test("size(return.links[]) >= 1", () => {
		// Getting value of variable: return_links_size_array
		return_links_size_array = response_items_element_artists_element_element_element.links;
		if(return_links_size_array != null) {
			return_links_size_array = return_links_size_array.length;
		}

		// Printing value of return_links_size_array variable
		console.log("Printing value of return_links_size_array");
		console.log(return_links_size_array);


		if((return_links_size_array != null) && (!valuesToConsiderAsNull.includes(return_links_size_array))) {
			//TODO: Implement;
		}
	})

		// 200&items&artists&links
		response_items_element_artists_element_element_element_links = response_items_element_artists_element_element_element.links
		if(response_items_element_artists_element_element_element_links != null) {
		// Printing value of response_items_element_artists_element_element_element_links variable
		console.log("Printing value of response_items_element_artists_element_element_element_links");
		console.log(response_items_element_artists_element_element_element_links);

			for(response_items_element_artists_element_element_element_links_index in response_items_element_artists_element_element_element_links) {
				response_items_element_artists_element_element_element_links_element = response_items_element_artists_element_element_element_links[response_items_element_artists_element_element_element_links_index]
		// Printing value of response_items_element_artists_element_element_element_links_element variable
		console.log("Printing value of response_items_element_artists_element_element_element_links_element");
		console.log(response_items_element_artists_element_element_element_links_element);

		// Invariants of this nesting level:
		// return.available == true
		pm.test("return.available == true", () => {
			// Getting value of variable: return_available
			return_available = response_items_element_artists_element_element_element_links_element.available;
			// Printing value of return_available variable
			console.log("Printing value of return_available");
			console.log(return_available);


			if((return_available != null) && (!valuesToConsiderAsNull.includes(return_available))) {
				//TODO: Implement;
			}
		})

			} // Closing for response_items_element_artists_element_element_element
		} // Closing if response_items_element_artists_element_element_element

		} // Closing for response_items_element
	} // Closing if response_items_element

} // Closing if array nesting level 2
} // Closing for array nesting level 2
} // Closing for array nesting level 1
	} // Closing for response
} // Closing if response