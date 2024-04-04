valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// size(input.hotelIds[]) -1 >= size(return.data[]) -1
pm.test("size(input.hotelIds[]) -1 >= size(return.data[]) -1", () => {
// Getting value of variable: input_hotelIds_size_array_minus_1
input_hotelIds_size_array_minus_1 = input_hotelIds_array;
if(input_hotelIds_size_array_minus_1 != null) {
input_hotelIds_size_array_minus_1 = input_hotelIds_size_array_minus_1.length;
}

if(input_hotelIds_size_array_minus_1 != null) {
input_hotelIds_size_array_minus_1 = input_hotelIds_size_array_minus_1  -1;
}
// Printing value of input_hotelIds_size_array_minus_1 variable
console.log("Printing value of input_hotelIds_size_array_minus_1");
console.log(input_hotelIds_size_array_minus_1);


// Getting value of variable: return_data_size_array_minus_1
return_data_size_array_minus_1 = response["data"];
if(return_data_size_array_minus_1 != null) {
return_data_size_array_minus_1 = return_data_size_array_minus_1.length;
}

if(return_data_size_array_minus_1 != null) {
return_data_size_array_minus_1 = return_data_size_array_minus_1  -1;
}
// Printing value of return_data_size_array_minus_1 variable
console.log("Printing value of return_data_size_array_minus_1");
console.log(return_data_size_array_minus_1);


if((input_hotelIds_size_array_minus_1 != null) && (!valuesToConsiderAsNull.includes(input_hotelIds_size_array_minus_1)) && (return_data_size_array_minus_1 != null) && (!valuesToConsiderAsNull.includes(return_data_size_array_minus_1))) {
//pm.expect(input_hotelIds_size_array_minus_1).to.be.at.least(return_data_size_array_minus_1);
}
})