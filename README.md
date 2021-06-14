# bg-backend-assignment
Project created with Spring Intializr @ https://start.spring.io/

POST /register (registers a new BgUser)
	colonistId string
	pass string
	mail string
	cpass string
	
POST /login (logins into an existing BgUser profile, recieve the JWT token)
	colonistId string
	pass string
	
GET /testauth (JWT poc)

POST /units/create
	title string
	region integer
	desc string
	cancelpolicy integer
	price decimal
	image binnary
	
POST /review (Requires JWT authentication) (requirement)
	unitUUID string
	newScore decimal
	desc string
	
GET units/getscore
	unitUUID string	
	
GET units/getinfo
	unitUUID string
	
GET units/retrieve (requirement)
	orderBy string
	limit integer
	offset integer
	cancelpolicyId integer
	regionId integer
	title string

Requirement 4,5
/login login in the API using password/username combination to obtain the JWT token
	Client Request:
		colonistId: the unique username
		pass: the password/username
	Server Answer:
		jwtToken: the token that the user has to send into JWT secured api calls
		reply: a message from the server

Requirement 3,5
/review a user sends a review for a specific unit
	Client Request:
		unitUUID: the ID of the unit that is reviewed
		newScore: the score given by the user
		desc: an optional text
		HEADER:
			Authorization: the user must have this header set to the JWT token obtained by /users/login
	Server Answer:
		totalReviews: the total reviews on this unique including this
		averageScore: the newly calculated average score for the reviewed unit
		unitUUID: the ID of the reviewed unit

Requirement 1,2
units/retrieve handles the sorting, filtering and indexing of Units simutaniously (ascending by default)
	Client Request:
		orderBy: ordering of the units based on EUnitComparator.java ordinal value
			0 BY_TITLE, 1 BY_PRICE, 2 BY_SCORE, 3 BY_REGION, 4 BY_CANCELPOLICY
			the above can be combined using csv style string splitted by "," example: 3,1,2 orders by: region then price and finally by the score
			you can add a prefix to each csv to make it descending example: 3,v1,v2,0 orders by ascending region the descending price then descending scire and finally by ascending title
		regionId (optional)
			show only units belonging to this region id
		cancelpolicyId (optional)
			show only units belonging to this cancelpolicy id
		title (optional)
			show only units that contain this string in their title (case sensitive)
		limit (configurable can be server side or client side)
			how many bgUnits should be sent from the API
		offset
			at which offset should the API send bgUnits 
			(this is used when sending bgUnits in batches for example a offset 6 will skip the first 6 unit results)
	Server Answer: a list of Units and all their information
		description, title, price, imageUrl, cancelPolicy, regionName, averageScore, totalReviews, unitUUID
		
Sort technical summary:
H2 Database is used for persisting the data. It is recommended to implement object caching using Apache Ignite to greatly boost
the performance of "units/retrieve" which is extremely heavy with the current implementention, although still preffered for the benefits it offers