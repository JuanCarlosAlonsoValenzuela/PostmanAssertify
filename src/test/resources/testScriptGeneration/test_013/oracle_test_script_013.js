valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// return.data.results[return.data.offset] == return.data.results[return.data.total -1]
pm.test("return.data.results[return.data.offset] == return.data.results[return.data.total -1]", () => {
// Getting value of variable: return_data_results_return_data_offset
return_data_results_return_data_offset = response["data"];
if(return_data_results_return_data_offset != null) {
return_data_results_return_data_offset = return_data_results_return_data_offset["results"];
}
if(return_data_results_return_data_offset != null) {
// Getting value of variable: return_data_offset
return_data_offset = response["data"];
if(return_data_offset != null) {
return_data_offset = return_data_offset["offset"];
}
// Printing value of return_data_offset variable
console.log("Printing value of return_data_offset");
console.log(return_data_offset);


if(return_data_offset != null) {
return_data_results_return_data_offset = return_data_results_return_data_offset[return_data_offset];
}
}
// Printing value of return_data_results_return_data_offset variable
console.log("Printing value of return_data_results_return_data_offset");
console.log(return_data_results_return_data_offset);


// Getting value of variable: return_data_results_return_data_total_minus_1
return_data_results_return_data_total_minus_1 = response["data"];
if(return_data_results_return_data_total_minus_1 != null) {
return_data_results_return_data_total_minus_1 = return_data_results_return_data_total_minus_1["results"];
}
if(return_data_results_return_data_total_minus_1 != null) {
// Getting value of variable: return_data_total_minus_1
return_data_total_minus_1 = response["data"];
if(return_data_total_minus_1 != null) {
return_data_total_minus_1 = return_data_total_minus_1["total"];
}
if(return_data_total_minus_1 != null) {
return_data_total_minus_1 = return_data_total_minus_1  -1;
}
// Printing value of return_data_total_minus_1 variable
console.log("Printing value of return_data_total_minus_1");
console.log(return_data_total_minus_1);


if(return_data_total_minus_1 != null) {
return_data_results_return_data_total_minus_1 = return_data_results_return_data_total_minus_1[return_data_total_minus_1];
}
}
// Printing value of return_data_results_return_data_total_minus_1 variable
console.log("Printing value of return_data_results_return_data_total_minus_1");
console.log(return_data_results_return_data_total_minus_1);


if((return_data_results_return_data_offset != null) && (!valuesToConsiderAsNull.includes(return_data_results_return_data_offset)) && (return_data_results_return_data_total_minus_1 != null) && (!valuesToConsiderAsNull.includes(return_data_results_return_data_total_minus_1))) {
pm.expect(return_data_results_return_data_offset).to.eql(return_data_results_return_data_total_minus_1);
}
})