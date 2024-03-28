decl-version 2.0
var-comparability implicit

ppt /users/playlists:::CLASS
ppt-type class

ppt /users/playlists&createPlaylist&201():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type createPlaylist&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.user
	var-kind field user
	enclosing-var input
	dec-type createPlaylist&Input&user
	rep-type hashcode
variable input.user.name
	var-kind field name
	enclosing-var input.user
	dec-type java.lang.String
	rep-type java.lang.String
variable input.user.contact
	var-kind field contact
	enclosing-var input.user
	dec-type createPlaylist&Input&contact
	rep-type hashcode
variable input.user.contact.email
	var-kind field email
	enclosing-var input.user.contact
	dec-type java.lang.String
	rep-type java.lang.String

ppt /users/playlists&createPlaylist&201():::EXIT1
ppt-type subexit
variable input
	var-kind variable
	dec-type createPlaylist&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.user
	var-kind field user
	enclosing-var input
	dec-type createPlaylist&Input&user
	rep-type hashcode
variable input.user.name
	var-kind field name
	enclosing-var input.user
	dec-type java.lang.String
	rep-type java.lang.String
variable input.user.contact
	var-kind field contact
	enclosing-var input.user
	dec-type createPlaylist&Input&contact
	rep-type hashcode
variable input.user.contact.email
	var-kind field email
	enclosing-var input.user.contact
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type createPlaylist&Output&201
	rep-type hashcode
variable return.id
	var-kind field id
	enclosing-var return
	dec-type java.lang.String
	rep-type java.lang.String
variable return.user_data
	var-kind field user_data
	enclosing-var return
	dec-type createPlaylist&Output&201&user_data
	rep-type hashcode
variable return.user_data.name
	var-kind field name
	enclosing-var return.user_data
	dec-type java.lang.String
	rep-type java.lang.String
variable return.user_data.email
	var-kind field email
	enclosing-var return.user_data
	dec-type java.lang.String
	rep-type java.lang.String

