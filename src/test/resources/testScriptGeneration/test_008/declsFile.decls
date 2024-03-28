decl-version 2.0
var-comparability implicit

ppt /artists/{id}/albums:::CLASS
ppt-type class

ppt /artists/{id}/albums&getArtistAlbums&200():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists&arrayProperty():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists%array():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String

ppt /artists/{id}/albums&getArtistAlbums&200&items():::ENTER
ppt-type enter
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String

ppt /artists/{id}/albums&getArtistAlbums&200():::EXIT1
ppt-type subexit
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type getArtistAlbums&Output&200
	rep-type hashcode
variable return.items
	var-kind field items
	enclosing-var return
	dec-type items[]
	rep-type hashcode
variable return.items[..]
	var-kind array
	enclosing-var return.items
	array 1
	dec-type items[]
	rep-type hashcode[]
variable return.limit
	var-kind field limit
	enclosing-var return
	dec-type int
	rep-type int
variable return.total
	var-kind field total
	enclosing-var return
	dec-type int
	rep-type int

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists&arrayProperty():::EXIT2
ppt-type subexit
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type getArtistAlbums&Output&200&items&artists&arrayProperty
	rep-type hashcode
variable return.url
	var-kind field url
	enclosing-var return
	dec-type java.lang.String
	rep-type java.lang.String
variable return.available
	var-kind field available
	enclosing-var return
	dec-type boolean
	rep-type boolean

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists%array():::EXIT3
ppt-type subexit
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type getArtistAlbums&Output&200&items&artists%array
	rep-type hashcode
variable return.array
	var-kind field array
	enclosing-var return
	dec-type array[]
	rep-type hashcode
variable return.array[..]
	var-kind array
	enclosing-var return.array
	array 1
	dec-type array[]
	rep-type hashcode[]

ppt /artists/{id}/albums&getArtistAlbums&200&items&artists():::EXIT4
ppt-type subexit
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type getArtistAlbums&Output&200&items&artists
	rep-type hashcode
variable return.name
	var-kind field name
	enclosing-var return
	dec-type java.lang.String
	rep-type java.lang.String
variable return.type
	var-kind field type
	enclosing-var return
	dec-type java.lang.String
	rep-type java.lang.String
variable return.arrayProperty
	var-kind field arrayProperty
	enclosing-var return
	dec-type arrayProperty[]
	rep-type hashcode
variable return.arrayProperty[..]
	var-kind array
	enclosing-var return.arrayProperty
	array 1
	dec-type arrayProperty[]
	rep-type hashcode[]

ppt /artists/{id}/albums&getArtistAlbums&200&items():::EXIT5
ppt-type subexit
variable input
	var-kind variable
	dec-type getArtistAlbums&Input
	rep-type hashcode
variable input.id
	var-kind field id
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable input.limit
	var-kind field limit
	enclosing-var input
	dec-type int
	rep-type int
variable input.offset
	var-kind field offset
	enclosing-var input
	dec-type int
	rep-type int
variable input.include_groups
	var-kind field include_groups
	enclosing-var input
	dec-type java.lang.String[]
	rep-type hashcode
variable input.include_groups[..]
	var-kind array
	enclosing-var input.include_groups
	array 1
	dec-type java.lang.String[]
	rep-type java.lang.String[]
variable input.market
	var-kind field market
	enclosing-var input
	dec-type java.lang.String
	rep-type java.lang.String
variable return
	var-kind return
	dec-type getArtistAlbums&Output&200&items
	rep-type hashcode
variable return.id
	var-kind field id
	enclosing-var return
	dec-type java.lang.String
	rep-type java.lang.String
variable return.artists
	var-kind field artists
	enclosing-var return
	dec-type artists[]
	rep-type hashcode
variable return.artists[..]
	var-kind array
	enclosing-var return.artists
	array 1
	dec-type artists[]
	rep-type hashcode[]

