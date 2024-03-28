let request_body = JSON.parse(pm.request.body.raw);
// Printing value of request_body variable
console.log("Printing value of request_body");
console.log(request_body);

// Getting value of the id property of the request body
input_id = request_body.id;
// Printing value of input_id variable
console.log("Printing value of input_id");
console.log(input_id);

// Getting value of the user property of the request body
input_user = request_body.user;
// Printing value of input_user variable
console.log("Printing value of input_user");
console.log(input_user);