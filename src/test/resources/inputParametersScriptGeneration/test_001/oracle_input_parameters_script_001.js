// Getting value of the name query parameter
input_name = pm.request.url.query.get("name");
if (input_name != null) {
	input_name = decodeURIComponent(input_name);
}
// Printing value of input_name variable
console.log("Printing value of input_name");
console.log(input_name);

// Getting value of the age query parameter
input_age = pm.request.url.query.get("age");
if (input_age != null) {
	input_age = decodeURIComponent(input_age);
	input_age = parseInt(input_age);
}
// Printing value of input_age variable
console.log("Printing value of input_age");
console.log(input_age);

// Getting value of the score query parameter
input_score = pm.request.url.query.get("score");
if (input_score != null) {
	input_score = decodeURIComponent(input_score);
	input_score = Number(input_score);
}
// Printing value of input_score variable
console.log("Printing value of input_score");
console.log(input_score);

// Getting value of the admin query parameter
input_admin = pm.request.url.query.get("admin");
if (input_admin != null) {
	input_admin = decodeURIComponent(input_admin);
	input_admin = (input_admin == "true");
}
// Printing value of input_admin variable
console.log("Printing value of input_admin");
console.log(input_admin);