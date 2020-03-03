An API for interfacing with a library book database.

DB:
Libraries
	ID	Primary Key
	Name
Books
	ID	Primary Key
	Name
	Author ID	Foreign Key
	Genre ID	Foreign Key
People
	ID	Primary Key
	First Name
	Middle Name
	Last Name

CheckedOut Person -> Books
	Person ID	Primary Key
	Book ID		Primary Key
	
LibraryBooks Library -> Books
	Library ID	Primary Key
	Book ID		Primary Key
	
Genre
	ID	Primary Key
	Genre
Authors
	ID	Primary Key
	First Name
	Last Name

Book has to be in checked out or in Library (How to enforce?)

API:
Private:
Add Book (Will be paired with a library by default)
Add Library
Add Person
Remove Book
Remove Library
Remove Person
Update Book
Update Library
Update Person

Public:
Get Books
Get Libraries
Get People
Get Books Checked Out by a person
Get Books in a Library
Check Out book (Remove from Library and add to checked out)
Return Book (Remove book from CheckedOut and add to Library)