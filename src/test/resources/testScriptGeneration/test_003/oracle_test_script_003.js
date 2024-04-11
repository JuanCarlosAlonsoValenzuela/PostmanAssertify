valuesToConsiderAsNull = [];
// &200
response = pm.response.json();
// Printing value of response variable
console.log("Printing value of response");
console.log(response);

// Invariants of this nesting level:
// return.total >= 0
pm.test("return.total >= 0", () => {
// Getting value of variable: return_total
return_total = response["total"];
// Printing value of return_total variable
console.log("Printing value of return_total");
console.log(return_total);


if((return_total != null) && (!valuesToConsiderAsNull.includes(return_total))) {
TODO: Implement Postman assertion;
}
})
// return.page >= 1
pm.test("return.page >= 1", () => {
// Getting value of variable: return_page
return_page = response["page"];
// Printing value of return_page variable
console.log("Printing value of return_page");
console.log(return_page);


if((return_page != null) && (!valuesToConsiderAsNull.includes(return_page))) {
TODO: Implement Postman assertion;
}
})

// 200&data
response_data = response["data"]
if(response_data != null) {
// Printing value of response_data variable
console.log("Printing value of response_data");
console.log(response_data);

for(response_data_index in response_data) {
response_data_element = response_data[response_data_index]
// Printing value of response_data_element variable
console.log("Printing value of response_data_element");
console.log(response_data_element);

// Invariants of this nesting level:
// return.edit_session.is_music_licensed == true
pm.test("return.edit_session.is_music_licensed == true", () => {
// Getting value of variable: return_edit_session_is_music_licensed
return_edit_session_is_music_licensed = response_data_element["edit_session"];
if(return_edit_session_is_music_licensed != null) {
return_edit_session_is_music_licensed = return_edit_session_is_music_licensed["is_music_licensed"];
}
// Printing value of return_edit_session_is_music_licensed variable
console.log("Printing value of return_edit_session_is_music_licensed");
console.log(return_edit_session_is_music_licensed);


if((return_edit_session_is_music_licensed != null) && (!valuesToConsiderAsNull.includes(return_edit_session_is_music_licensed))) {
TODO: Implement Postman assertion;
}
})

// 200&data&user
response_data_element_user = response_data_element["user"]
if(response_data_element_user != null) {
// Printing value of response_data_element_user variable
console.log("Printing value of response_data_element_user");
console.log(response_data_element_user);

// This nesting level has no invariants

// 200&data&user&websites
response_data_element_user_websites = response_data_element_user["websites"]
if(response_data_element_user_websites != null) {
// Printing value of response_data_element_user_websites variable
console.log("Printing value of response_data_element_user_websites");
console.log(response_data_element_user_websites);

for(response_data_element_user_websites_index in response_data_element_user_websites) {
response_data_element_user_websites_element = response_data_element_user_websites[response_data_element_user_websites_index]
// Printing value of response_data_element_user_websites_element variable
console.log("Printing value of response_data_element_user_websites_element");
console.log(response_data_element_user_websites_element);

// Invariants of this nesting level:
// size(input.uris[]) >= 1
pm.test("size(input.uris[]) >= 1", () => {
// Getting value of variable: input_uris_size_array
input_uris_size_array = input_uris_array;
if(input_uris_size_array != null) {
input_uris_size_array = input_uris_size_array.length;
}

// Printing value of input_uris_size_array variable
console.log("Printing value of input_uris_size_array");
console.log(input_uris_size_array);


if((input_uris_size_array != null) && (!valuesToConsiderAsNull.includes(input_uris_size_array))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_user
} // Closing if response_data_element_user

// 200&data&user&skills
response_data_element_user_skills = response_data_element_user["skills"]
if(response_data_element_user_skills != null) {
// Printing value of response_data_element_user_skills variable
console.log("Printing value of response_data_element_user_skills");
console.log(response_data_element_user_skills);

for(response_data_element_user_skills_index in response_data_element_user_skills) {
response_data_element_user_skills_element = response_data_element_user_skills[response_data_element_user_skills_index]
// Printing value of response_data_element_user_skills_element variable
console.log("Printing value of response_data_element_user_skills_element");
console.log(response_data_element_user_skills_element);

// Invariants of this nesting level:
// size(input.uris[]) >= 1
pm.test("size(input.uris[]) >= 1", () => {
// Getting value of variable: input_uris_size_array
input_uris_size_array = input_uris_array;
if(input_uris_size_array != null) {
input_uris_size_array = input_uris_size_array.length;
}

// Printing value of input_uris_size_array variable
console.log("Printing value of input_uris_size_array");
console.log(input_uris_size_array);


if((input_uris_size_array != null) && (!valuesToConsiderAsNull.includes(input_uris_size_array))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_user
} // Closing if response_data_element_user

// 200&data&user&pictures
response_data_element_user_pictures = response_data_element_user["pictures"]
if(response_data_element_user_pictures != null) {
// Printing value of response_data_element_user_pictures variable
console.log("Printing value of response_data_element_user_pictures");
console.log(response_data_element_user_pictures);

// This nesting level has no invariants

// 200&data&user&pictures&sizes
response_data_element_user_pictures_sizes = response_data_element_user_pictures["sizes"]
if(response_data_element_user_pictures_sizes != null) {
// Printing value of response_data_element_user_pictures_sizes variable
console.log("Printing value of response_data_element_user_pictures_sizes");
console.log(response_data_element_user_pictures_sizes);

for(response_data_element_user_pictures_sizes_index in response_data_element_user_pictures_sizes) {
response_data_element_user_pictures_sizes_element = response_data_element_user_pictures_sizes[response_data_element_user_pictures_sizes_index]
// Printing value of response_data_element_user_pictures_sizes_element variable
console.log("Printing value of response_data_element_user_pictures_sizes_element");
console.log(response_data_element_user_pictures_sizes_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_user_pictures_sizes_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_user_pictures
} // Closing if response_data_element_user_pictures

} // Closing if response_data_element_user

} // Closing if response_data_element

// 200&data&categories
response_data_element_categories = response_data_element["categories"]
if(response_data_element_categories != null) {
// Printing value of response_data_element_categories variable
console.log("Printing value of response_data_element_categories");
console.log(response_data_element_categories);

for(response_data_element_categories_index in response_data_element_categories) {
response_data_element_categories_element = response_data_element_categories[response_data_element_categories_index]
// Printing value of response_data_element_categories_element variable
console.log("Printing value of response_data_element_categories_element");
console.log(response_data_element_categories_element);

// Invariants of this nesting level:
// size(return.pictures.sizes[]) >= size(return.subcategories[])
pm.test("size(return.pictures.sizes[]) >= size(return.subcategories[])", () => {
// Getting value of variable: return_pictures_sizes_size_array
return_pictures_sizes_size_array = response_data_element_categories_element["pictures"];
if(return_pictures_sizes_size_array != null) {
return_pictures_sizes_size_array = return_pictures_sizes_size_array["sizes"];
}
if(return_pictures_sizes_size_array != null) {
return_pictures_sizes_size_array = return_pictures_sizes_size_array.length;
}

// Printing value of return_pictures_sizes_size_array variable
console.log("Printing value of return_pictures_sizes_size_array");
console.log(return_pictures_sizes_size_array);


// Getting value of variable: return_subcategories_size_array
return_subcategories_size_array = response_data_element_categories_element["subcategories"];
if(return_subcategories_size_array != null) {
return_subcategories_size_array = return_subcategories_size_array.length;
}

// Printing value of return_subcategories_size_array variable
console.log("Printing value of return_subcategories_size_array");
console.log(return_subcategories_size_array);


if((return_pictures_sizes_size_array != null) && (!valuesToConsiderAsNull.includes(return_pictures_sizes_size_array)) && (return_subcategories_size_array != null) && (!valuesToConsiderAsNull.includes(return_subcategories_size_array))) {
TODO: Implement Postman assertion;
}
})

// 200&data&categories&pictures
response_data_element_categories_element_pictures = response_data_element_categories_element["pictures"]
if(response_data_element_categories_element_pictures != null) {
// Printing value of response_data_element_categories_element_pictures variable
console.log("Printing value of response_data_element_categories_element_pictures");
console.log(response_data_element_categories_element_pictures);

// This nesting level has no invariants

// 200&data&categories&pictures&sizes
response_data_element_categories_element_pictures_sizes = response_data_element_categories_element_pictures["sizes"]
if(response_data_element_categories_element_pictures_sizes != null) {
// Printing value of response_data_element_categories_element_pictures_sizes variable
console.log("Printing value of response_data_element_categories_element_pictures_sizes");
console.log(response_data_element_categories_element_pictures_sizes);

for(response_data_element_categories_element_pictures_sizes_index in response_data_element_categories_element_pictures_sizes) {
response_data_element_categories_element_pictures_sizes_element = response_data_element_categories_element_pictures_sizes[response_data_element_categories_element_pictures_sizes_index]
// Printing value of response_data_element_categories_element_pictures_sizes_element variable
console.log("Printing value of response_data_element_categories_element_pictures_sizes_element");
console.log(response_data_element_categories_element_pictures_sizes_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_categories_element_pictures_sizes_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})
// return.height < return.width
pm.test("return.height < return.width", () => {
// Getting value of variable: return_height
return_height = response_data_element_categories_element_pictures_sizes_element["height"];
// Printing value of return_height variable
console.log("Printing value of return_height");
console.log(return_height);


// Getting value of variable: return_width
return_width = response_data_element_categories_element_pictures_sizes_element["width"];
// Printing value of return_width variable
console.log("Printing value of return_width");
console.log(return_width);


if((return_height != null) && (!valuesToConsiderAsNull.includes(return_height)) && (return_width != null) && (!valuesToConsiderAsNull.includes(return_width))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_categories_element_pictures
} // Closing if response_data_element_categories_element_pictures

} // Closing if response_data_element_categories_element

// 200&data&categories&subcategories
response_data_element_categories_element_subcategories = response_data_element_categories_element["subcategories"]
if(response_data_element_categories_element_subcategories != null) {
// Printing value of response_data_element_categories_element_subcategories variable
console.log("Printing value of response_data_element_categories_element_subcategories");
console.log(response_data_element_categories_element_subcategories);

for(response_data_element_categories_element_subcategories_index in response_data_element_categories_element_subcategories) {
response_data_element_categories_element_subcategories_element = response_data_element_categories_element_subcategories[response_data_element_categories_element_subcategories_index]
// Printing value of response_data_element_categories_element_subcategories_element variable
console.log("Printing value of response_data_element_categories_element_subcategories_element");
console.log(response_data_element_categories_element_subcategories_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_categories_element_subcategories_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_categories_element
} // Closing if response_data_element_categories_element

// 200&data&categories&icon
response_data_element_categories_element_icon = response_data_element_categories_element["icon"]
if(response_data_element_categories_element_icon != null) {
// Printing value of response_data_element_categories_element_icon variable
console.log("Printing value of response_data_element_categories_element_icon");
console.log(response_data_element_categories_element_icon);

// This nesting level has no invariants

// 200&data&categories&icon&sizes
response_data_element_categories_element_icon_sizes = response_data_element_categories_element_icon["sizes"]
if(response_data_element_categories_element_icon_sizes != null) {
// Printing value of response_data_element_categories_element_icon_sizes variable
console.log("Printing value of response_data_element_categories_element_icon_sizes");
console.log(response_data_element_categories_element_icon_sizes);

for(response_data_element_categories_element_icon_sizes_index in response_data_element_categories_element_icon_sizes) {
response_data_element_categories_element_icon_sizes_element = response_data_element_categories_element_icon_sizes[response_data_element_categories_element_icon_sizes_index]
// Printing value of response_data_element_categories_element_icon_sizes_element variable
console.log("Printing value of response_data_element_categories_element_icon_sizes_element");
console.log(response_data_element_categories_element_icon_sizes_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_categories_element_icon_sizes_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})
// size(input.uris[]) >= 1
pm.test("size(input.uris[]) >= 1", () => {
// Getting value of variable: input_uris_size_array
input_uris_size_array = input_uris_array;
if(input_uris_size_array != null) {
input_uris_size_array = input_uris_size_array.length;
}

// Printing value of input_uris_size_array variable
console.log("Printing value of input_uris_size_array");
console.log(input_uris_size_array);


if((input_uris_size_array != null) && (!valuesToConsiderAsNull.includes(input_uris_size_array))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_categories_element_icon
} // Closing if response_data_element_categories_element_icon

} // Closing if response_data_element_categories_element

} // Closing for response_data_element
} // Closing if response_data_element

// 200&data&pictures
response_data_element_pictures = response_data_element["pictures"]
if(response_data_element_pictures != null) {
// Printing value of response_data_element_pictures variable
console.log("Printing value of response_data_element_pictures");
console.log(response_data_element_pictures);

// This nesting level has no invariants

// 200&data&pictures&sizes
response_data_element_pictures_sizes = response_data_element_pictures["sizes"]
if(response_data_element_pictures_sizes != null) {
// Printing value of response_data_element_pictures_sizes variable
console.log("Printing value of response_data_element_pictures_sizes");
console.log(response_data_element_pictures_sizes);

for(response_data_element_pictures_sizes_index in response_data_element_pictures_sizes) {
response_data_element_pictures_sizes_element = response_data_element_pictures_sizes[response_data_element_pictures_sizes_index]
// Printing value of response_data_element_pictures_sizes_element variable
console.log("Printing value of response_data_element_pictures_sizes_element");
console.log(response_data_element_pictures_sizes_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_pictures_sizes_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})
// return.link_with_play_button is Url
pm.test("return.link_with_play_button is Url", () => {
// Getting value of variable: return_link_with_play_button
return_link_with_play_button = response_data_element_pictures_sizes_element["link_with_play_button"];
// Printing value of return_link_with_play_button variable
console.log("Printing value of return_link_with_play_button");
console.log(return_link_with_play_button);


if((return_link_with_play_button != null) && (!valuesToConsiderAsNull.includes(return_link_with_play_button))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_pictures
} // Closing if response_data_element_pictures

} // Closing if response_data_element

// 200&data&tags
response_data_element_tags = response_data_element["tags"]
if(response_data_element_tags != null) {
// Printing value of response_data_element_tags variable
console.log("Printing value of response_data_element_tags");
console.log(response_data_element_tags);

for(response_data_element_tags_index in response_data_element_tags) {
response_data_element_tags_element = response_data_element_tags[response_data_element_tags_index]
// Printing value of response_data_element_tags_element variable
console.log("Printing value of response_data_element_tags_element");
console.log(response_data_element_tags_element);

// Invariants of this nesting level:
// LENGTH(return.resource_key)==40
pm.test("LENGTH(return.resource_key)==40", () => {
// Getting value of variable: return_resource_key
return_resource_key = response_data_element_tags_element["resource_key"];
// Printing value of return_resource_key variable
console.log("Printing value of return_resource_key");
console.log(return_resource_key);


if((return_resource_key != null) && (!valuesToConsiderAsNull.includes(return_resource_key))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element
} // Closing if response_data_element

// 200&data&uploader
response_data_element_uploader = response_data_element["uploader"]
if(response_data_element_uploader != null) {
// Printing value of response_data_element_uploader variable
console.log("Printing value of response_data_element_uploader");
console.log(response_data_element_uploader);

// This nesting level has no invariants

// 200&data&uploader&pictures
response_data_element_uploader_pictures = response_data_element_uploader["pictures"]
if(response_data_element_uploader_pictures != null) {
// Printing value of response_data_element_uploader_pictures variable
console.log("Printing value of response_data_element_uploader_pictures");
console.log(response_data_element_uploader_pictures);

// This nesting level has no invariants

// 200&data&uploader&pictures&sizes
response_data_element_uploader_pictures_sizes = response_data_element_uploader_pictures["sizes"]
if(response_data_element_uploader_pictures_sizes != null) {
// Printing value of response_data_element_uploader_pictures_sizes variable
console.log("Printing value of response_data_element_uploader_pictures_sizes");
console.log(response_data_element_uploader_pictures_sizes);

for(response_data_element_uploader_pictures_sizes_index in response_data_element_uploader_pictures_sizes) {
response_data_element_uploader_pictures_sizes_element = response_data_element_uploader_pictures_sizes[response_data_element_uploader_pictures_sizes_index]
// Printing value of response_data_element_uploader_pictures_sizes_element variable
console.log("Printing value of response_data_element_uploader_pictures_sizes_element");
console.log(response_data_element_uploader_pictures_sizes_element);

// Invariants of this nesting level:
// return.link is Url
pm.test("return.link is Url", () => {
// Getting value of variable: return_link
return_link = response_data_element_uploader_pictures_sizes_element["link"];
// Printing value of return_link variable
console.log("Printing value of return_link");
console.log(return_link);


if((return_link != null) && (!valuesToConsiderAsNull.includes(return_link))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_uploader_pictures
} // Closing if response_data_element_uploader_pictures

} // Closing if response_data_element_uploader

} // Closing if response_data_element

// 200&data&spatial
response_data_element_spatial = response_data_element["spatial"]
if(response_data_element_spatial != null) {
// Printing value of response_data_element_spatial variable
console.log("Printing value of response_data_element_spatial");
console.log(response_data_element_spatial);

// This nesting level has no invariants

// 200&data&spatial&director_timeline
response_data_element_spatial_director_timeline = response_data_element_spatial["director_timeline"]
if(response_data_element_spatial_director_timeline != null) {
// Printing value of response_data_element_spatial_director_timeline variable
console.log("Printing value of response_data_element_spatial_director_timeline");
console.log(response_data_element_spatial_director_timeline);

for(response_data_element_spatial_director_timeline_index in response_data_element_spatial_director_timeline) {
response_data_element_spatial_director_timeline_element = response_data_element_spatial_director_timeline[response_data_element_spatial_director_timeline_index]
// Printing value of response_data_element_spatial_director_timeline_element variable
console.log("Printing value of response_data_element_spatial_director_timeline_element");
console.log(response_data_element_spatial_director_timeline_element);

// Invariants of this nesting level:
// return.roll == 0.0
pm.test("return.roll == 0.0", () => {
// Getting value of variable: return_roll
return_roll = response_data_element_spatial_director_timeline_element["roll"];
// Printing value of return_roll variable
console.log("Printing value of return_roll");
console.log(return_roll);


if((return_roll != null) && (!valuesToConsiderAsNull.includes(return_roll))) {
TODO: Implement Postman assertion;
}
})

} // Closing for response_data_element_spatial
} // Closing if response_data_element_spatial

} // Closing if response_data_element

} // Closing for response
} // Closing if response