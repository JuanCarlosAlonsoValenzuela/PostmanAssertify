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

// 200&users
response_element_users = response_element["users"]
if(response_element_users != null) {
// Printing value of response_element_users variable
console.log("Printing value of response_element_users");
console.log(response_element_users);

// Invariants of this nesting level:
// size(return.array[]) >= 1
pm.test("size(return.array[]) >= 1", () => {
// Getting value of variable: return_array_size_array
return_array_size_array = response_element_users;
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
for(response_element_users_index in response_element_users) {
response_element_users_element = response_element_users[response_element_users_index]
// Printing value of response_element_users_element variable
console.log("Printing value of response_element_users_element");
console.log(response_element_users_element);

for(response_element_users_element_index in response_element_users_element) {
response_element_users_element_element = response_element_users_element[response_element_users_element_index]
// Printing value of response_element_users_element_element variable
console.log("Printing value of response_element_users_element_element");
console.log(response_element_users_element_element);

// Invariants of this nesting level:
// return.age >= 1
pm.test("return.age >= 1", () => {
// Getting value of variable: return_age
return_age = response_element_users_element_element["age"];
// Printing value of return_age variable
console.log("Printing value of return_age");
console.log(return_age);


if((return_age != null) && (!valuesToConsiderAsNull.includes(return_age))) {
//TODO: Postman Assertion;
}
})

} // Closing for response_element
} // Closing if response_element

} // Closing for array nesting level 1
} // Closing for array nesting level 1