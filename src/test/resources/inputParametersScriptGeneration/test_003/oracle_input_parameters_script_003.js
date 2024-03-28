// Getting value of the name header parameter
input_name = pm.request.headers.find(header => !header.disabled && header.key=="name");
if(input_name != null){
input_name = input_name.value;
}
if (input_name != null) {
	input_name = decodeURIComponent(input_name);
}
// Printing value of input_name variable
console.log("Printing value of input_name");
console.log(input_name);

// Getting value of the age header parameter
input_age = pm.request.headers.find(header => !header.disabled && header.key=="age");
if(input_age != null){
input_age = input_age.value;
}
if (input_age != null) {
	input_age = decodeURIComponent(input_age);
	input_age = parseInt(input_age);
}
// Printing value of input_age variable
console.log("Printing value of input_age");
console.log(input_age);

// Getting value of the score header parameter
input_score = pm.request.headers.find(header => !header.disabled && header.key=="score");
if(input_score != null){
input_score = input_score.value;
}
if (input_score != null) {
	input_score = decodeURIComponent(input_score);
	input_score = Number(input_score);
}
// Printing value of input_score variable
console.log("Printing value of input_score");
console.log(input_score);

// Getting value of the admin header parameter
input_admin = pm.request.headers.find(header => !header.disabled && header.key=="admin");
if(input_admin != null){
input_admin = input_admin.value;
}
if (input_admin != null) {
	input_admin = decodeURIComponent(input_admin);
	input_admin = (input_admin == "true");
}
// Printing value of input_admin variable
console.log("Printing value of input_admin");
console.log(input_admin);