valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// This nesting level has no invariants

// Access to the next nesting level
for(response_index in response) {
response_element = response[response_index]
// Printing value of response_element variable
console.log("Printing value of response_element");
console.log(response_element);

// Invariants of this nesting level:
// LENGTH(return.cca2)==2
pm.test("LENGTH(return.cca2)==2", () => {
// Getting value of variable: return_cca2
return_cca2 = response_element["cca2"];
// Printing value of return_cca2 variable
console.log("Printing value of return_cca2");
console.log(return_cca2);


if((return_cca2 != null) && (!valuesToConsiderAsNull.includes(return_cca2))) {
pm.expect(return_cca2).to.have.length(2);
}
})

} // Closing for array nesting level 1