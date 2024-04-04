valuesToConsiderAsNull = [];
// &201
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// input.user.contact.email == return.user_data.email
pm.test("input.user.contact.email == return.user_data.email", () => {
// Getting value of variable: input_user_contact_email
input_user_contact_email = input_user;
if(input_user_contact_email != null) {
input_user_contact_email = input_user_contact_email["contact"];
if(input_user_contact_email != null) {
input_user_contact_email = input_user_contact_email["email"];
}
}
// Printing value of input_user_contact_email variable
console.log("Printing value of input_user_contact_email");
console.log(input_user_contact_email);


// Getting value of variable: return_user_data_email
return_user_data_email = response["user_data"];
if(return_user_data_email != null) {
return_user_data_email = return_user_data_email["email"];
}
// Printing value of return_user_data_email variable
console.log("Printing value of return_user_data_email");
console.log(return_user_data_email);


if((input_user_contact_email != null) && (!valuesToConsiderAsNull.includes(input_user_contact_email)) && (return_user_data_email != null) && (!valuesToConsiderAsNull.includes(return_user_data_email))) {
//TODO: IMPLEMENT;
}
})