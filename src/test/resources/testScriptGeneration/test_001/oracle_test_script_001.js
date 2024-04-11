valuesToConsiderAsNull = ["N/A"];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// input.i == return.imdbID
pm.test("input.i == return.imdbID", () => {
// Getting value of variable: input_i
// Printing value of input_i variable
console.log("Printing value of input_i");
console.log(input_i);


// Getting value of variable: return_imdbID
return_imdbID = response["imdbID"];
// Printing value of return_imdbID variable
console.log("Printing value of return_imdbID");
console.log(return_imdbID);


if((input_i != null) && (!valuesToConsiderAsNull.includes(input_i)) && (return_imdbID != null) && (!valuesToConsiderAsNull.includes(return_imdbID))) {
pm.expect(input_i).to.eql(return_imdbID);
}
})
// return.Response == "True"
pm.test("return.Response == \"True\"", () => {
// Getting value of variable: return_Response
return_Response = response["Response"];
// Printing value of return_Response variable
console.log("Printing value of return_Response");
console.log(return_Response);


if((return_Response != null) && (!valuesToConsiderAsNull.includes(return_Response))) {
pm.expect(["True"].includes(return_Response)).to.be.true;
}
})

// 200&Ratings
response_Ratings = response["Ratings"]
if(response_Ratings != null) {
// Printing value of response_Ratings variable
console.log("Printing value of response_Ratings");
console.log(response_Ratings);

for(response_Ratings_index in response_Ratings) {
response_Ratings_element = response_Ratings[response_Ratings_index]
// Printing value of response_Ratings_element variable
console.log("Printing value of response_Ratings_element");
console.log(response_Ratings_element);

// Invariants of this nesting level:
// return.Source one of { "Internet Movie Database", "Metacritic", "Rotten Tomatoes" }
pm.test("return.Source one of { \"Internet Movie Database\", \"Metacritic\", \"Rotten Tomatoes\" }", () => {
// Getting value of variable: return_Source
return_Source = response_Ratings_element["Source"];
// Printing value of return_Source variable
console.log("Printing value of return_Source");
console.log(return_Source);


if((return_Source != null) && (!valuesToConsiderAsNull.includes(return_Source))) {
pm.expect(["Internet Movie Database", "Metacritic", "Rotten Tomatoes"].includes(return_Source)).to.be.true;
}
})

} // Closing for response
} // Closing if response