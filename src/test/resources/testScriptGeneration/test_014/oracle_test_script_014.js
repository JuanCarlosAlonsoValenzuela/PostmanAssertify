valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// return.data.results[0] == return.data.results[return.data.total -1]
pm.test("return.data.results[0] == return.data.results[return.data.total -1]", () => {
// Getting value of variable: return_data_results_0
return_data_results_0 = response.data;
if(return_data_results_0 != null) {
return_data_results_0 = return_data_results_0.results;
}
if(return_data_results_0 != null) {
return_data_results_0 = return_data_results_0[0];
}
// Printing value of return_data_results_0 variable
console.log("Printing value of return_data_results_0");
console.log(return_data_results_0);


// Getting value of variable: return_data_results_return_data_total_minus_1
return_data_results_return_data_total_minus_1 = response.data;
if(return_data_results_return_data_total_minus_1 != null) {
return_data_results_return_data_total_minus_1 = return_data_results_return_data_total_minus_1.results;
}
if(return_data_results_return_data_total_minus_1 != null) {
// Getting value of variable: return_data_total_minus_1
return_data_total_minus_1 = response.data;
if(return_data_total_minus_1 != null) {
return_data_total_minus_1 = return_data_total_minus_1.total;
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


if((return_data_results_0 != null) && (!valuesToConsiderAsNull.includes(return_data_results_0)) && (return_data_results_return_data_total_minus_1 != null) && (!valuesToConsiderAsNull.includes(return_data_results_return_data_total_minus_1))) {
//pm.expect(return_data_results_0).to.eql(return_data_results_return_data_total_minus_1);
}
})
