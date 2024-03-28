// Getting value of the name_array header parameter
input_name_array_array = pm.request.headers.find(header => !header.disabled && header.key=="name_array");
if(input_name_array_array != null){
input_name_array_array = input_name_array_array.value;
}
if (input_name_array_array != null) {
	input_name_array_array = decodeURIComponent(input_name_array_array);
	input_name_array_array = input_name_array_array.split(",").map(item => item.trim());
}
// Printing value of input_name_array_array variable
console.log("Printing value of input_name_array_array");
console.log(input_name_array_array);

// Getting value of the age_array header parameter
input_age_array_array = pm.request.headers.find(header => !header.disabled && header.key=="age_array");
if(input_age_array_array != null){
input_age_array_array = input_age_array_array.value;
}
if (input_age_array_array != null) {
	input_age_array_array = decodeURIComponent(input_age_array_array);
	input_age_array_array = input_age_array_array.split(",").map(item => parseInt(item.trim()));
}
// Printing value of input_age_array_array variable
console.log("Printing value of input_age_array_array");
console.log(input_age_array_array);

// Getting value of the score_array header parameter
input_score_array_array = pm.request.headers.find(header => !header.disabled && header.key=="score_array");
if(input_score_array_array != null){
input_score_array_array = input_score_array_array.value;
}
if (input_score_array_array != null) {
	input_score_array_array = decodeURIComponent(input_score_array_array);
	input_score_array_array = input_score_array_array.split(",").map(item => Number(item.trim()));
}
// Printing value of input_score_array_array variable
console.log("Printing value of input_score_array_array");
console.log(input_score_array_array);

// Getting value of the admin_array header parameter
input_admin_array_array = pm.request.headers.find(header => !header.disabled && header.key=="admin_array");
if(input_admin_array_array != null){
input_admin_array_array = input_admin_array_array.value;
}
if (input_admin_array_array != null) {
	input_admin_array_array = decodeURIComponent(input_admin_array_array);
	input_admin_array_array = input_admin_array_array.split(",").map(item => item.trim() == "true");
}
// Printing value of input_admin_array_array variable
console.log("Printing value of input_admin_array_array");
console.log(input_admin_array_array);