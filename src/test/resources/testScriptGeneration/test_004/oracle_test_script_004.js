valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// size(input.codes[]) >= size(return.array[])
pm.test("size(input.codes[]) >= size(return.array[])", () => {
// Getting value of variable: input_codes_size_array
input_codes_size_array = input_codes_array;
if(input_codes_size_array != null) {
input_codes_size_array = input_codes_size_array.length;
}

// Printing value of input_codes_size_array variable
console.log("Printing value of input_codes_size_array");
console.log(input_codes_size_array);


// Getting value of variable: return_array_size_array
return_array_size_array = response;
if(return_array_size_array != null) {
return_array_size_array = return_array_size_array.length;
}

// Printing value of return_array_size_array variable
console.log("Printing value of return_array_size_array");
console.log(return_array_size_array);


if((input_codes_size_array != null) && (!valuesToConsiderAsNull.includes(input_codes_size_array)) && (return_array_size_array != null) && (!valuesToConsiderAsNull.includes(return_array_size_array))) {
//TODO: Postman Assertion;
}
})

// Access to the next nesting level
for(response_index in response) {
response_element = response[response_index]
// Printing value of response_element variable
console.log("Printing value of response_element");
console.log(response_element);

// Invariants of this nesting level:
// LENGTH(return.cca3)==3
pm.test("LENGTH(return.cca3)==3", () => {
// Getting value of variable: return_cca3
return_cca3 = response_element["cca3"];
// Printing value of return_cca3 variable
console.log("Printing value of return_cca3");
console.log(return_cca3);


if((return_cca3 != null) && (!valuesToConsiderAsNull.includes(return_cca3))) {
//TODO: Postman Assertion;
}
})
// return.maps.googleMaps is Url
pm.test("return.maps.googleMaps is Url", () => {
// Getting value of variable: return_maps_googleMaps
return_maps_googleMaps = response_element["maps"];
if(return_maps_googleMaps != null) {
return_maps_googleMaps = return_maps_googleMaps["googleMaps"];
}
// Printing value of return_maps_googleMaps variable
console.log("Printing value of return_maps_googleMaps");
console.log(return_maps_googleMaps);


if((return_maps_googleMaps != null) && (!valuesToConsiderAsNull.includes(return_maps_googleMaps))) {
//TODO: Postman Assertion;
}
})

} // Closing for array nesting level 1