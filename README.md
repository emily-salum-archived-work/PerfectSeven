
# PERFECT SEVEN

## Video Demo [Here](https://youtu.be/CWss5f941eA)

#### Introduction:
At the time I was looking for an idea for a final project, I heard my mother talk about her job and how she
had to use the calculator to do this calculation and, although not extremely complex, she had
to memorize it to use for every client. I then proposed I could make a program for her, because if she would
only have to insert the necessary inputs it would take less time to do so and less memorizing to make.
That lead to a conversation where I learned about some of the processes the employees would take in their day-to-day,
and I made Perfect Seven with the intention of bringing some better solutions to some of those processes.
Two programs will be made, Administrator-Mode and Employee-Mode, with the main purpose of facilitating the passage and usage of client-data to the employees.

## Requirements upon planning:

- Read an excel file and save information inside a database.
Reasoning:
Currently to get client information their boss manually divides an excel file and sends to each
employee, which is unneficient.
Specifications:
The adm can choose an excel file from their computer and the client information should then be
provided to the employees (spread evenly among them) through the database connection.


- Manage Employees
	Specifications:
	A "Remove" Button next to each employee in a table, and an "add" button next to the table.



- Registration
Reasoning:
Different employees will have acess to different information, so they should have some sort of account.
Specifications:
Only the administrator-side can add names, and only the Employee-Side can add a password to that account
(The first attempted password for a named account that doesn't yet have a password
will be considered the new password). The administrator can also reset the password of an account by clicking
on a button in the table of employees.


- Client State and Records of Sellings:
	Reasoning:
	Now that we have provided true connection between the employee and client, we can use
	it for a number of different useful things. These things were already done differently,
	where  the employees would usually keep note of who didn't accept their calls through the excel
	file, those that  were in the process of being sold their products, etc. But this way is a lot more
	practical as it actually removes them from the visible list of who to call.
	Specifications:
	The table that shows all of the clients will be separated by their state of purchuse, "in wait",
	"in process", "not answered", "sold", and any client can be sent to any other state at any given
	time. Once the client is sent to "sold" the employee will be requested to insert a value to
	represent the selling value which is then saved in the database. For the administrator, they will
	be capable of visualizing the sold products from 3 different ranges: "day", "month" and "total".



- A simple "Refim Calculation" Interface
Reasoning:
	Although it would be a lot better if I could make the calculation completely automatic, the values
	used on it are not provided by the excel file described earlier, and rather by a separate program
	based on the customer's identifications, so the best I can make without acess to such resource is a
	quicker solution for calculation which is already better than the currently used system.
	Specifications:
	The calculation receives two inputs, "saldo" e "prestacao", both changing from client to client.
	It also uses a value called "fator", that can change over time but isn't decided from client to
	client, it will be saved on the database to be used in the calculation by all employees, and allow
	the administrator to change it when needed. it should also have a nice "erase contents" button.


Some Extra Implementation Information:

Programming Language: Java.
Database Hosting: MongoDB.

Relevant plugins used:
1. MongoDB: To manipulate the database hosted in Mongo.
2. POI: To read excel files.

# List of different Classes and most relevant relations between them:

## DataProvider, TableDataProvider and DataTableModel:
I made these as an attempt to simplify the way to pass data from the database to interface and logical purposes,
A DataTableModel would have control over TableDataProviders of a same type, each having many DataProviders that would commonly
be associated with a specific data from the TableDataProvider.
For example, once I decided to encrypt the information, all I had to do to make sure things were being decrypted
was add a boolean to the DataProvider, telling it to decrypt its data or not.

## EncryptionConversion:
Contains two methods that I used to encrypt and decrypt data stored inside the database.

## ConfirmationScreen and Action:
I made the confirmation screen class to allow for processes to be canceled in case the user didn't intend to do them.
It does so by accepting Action objects that are separated into "confirm" and "cancel" options.
Although later I saw that there was a class called "Action" in java.awt with the same purpose,
I decided to keep my own, as its implementation is an extremely simple thing and it does what it had to do.

## DefaultInterface:
This class has some static methods to create JTextFields and JButtons, specially useful to minimize repeated code.
It was also conventional once I decided to change the look of the interface in some aspects.

## DatabaseConnection, ClientBehaviour and WorkerBehaviour:
These are the classes that make the database manipulation possible, with the main one being "DatabaseConnection", inicializing
the connection to it. I have made the others because as time passed it was starting to get filled with methods and everything
was harder to find, by separating them in types it gets much easier to look for stuff.

# Interface, Absolute-Positioning-Grid and ControlledPanel:
Interface Controls the window of the application, and its currently opened Main Controlled Panel.
I made ControlledPanel to share behaviour between all the interface levels, it would be used inside an absolute positioning
grid and the idea was that panels would be put inside other panels and serve as "building blocks"
using scaling so you could change the overall layout and keep the same funcionalities no matter what.
However, after working with it for a while, I discovered there are a few problems with putting
Panels inside other Panels that make it completely unusable, the main difference now
is that there are two ways a controlled panel can be used:
1 - Main Current Interface, added as a panel
2 - Building Block, is not added as a panel and simply serves to contain elements of the interface in its constructor,
and serve its useful standard methods for scaling.

# JTableButtonMouseListener and JTableButtonRenderer:
Taken from this: https://stackoverflow.com/questions/13833688/adding-jbutton-to-jtable
To add buttons to JTables and make them clickable.

### Testing Specifications:

1. For simplicity I have sent the whole thing for the final project, but as said previously it's actually two programs,
so to test the whole thing I made so that you can click on "]" in your keyboard to switch from adm mode to worker mode.

2. The project comes with an excel file with a simple random example of a file my program would read
to fill the database with new clients.

#### PS: The true data currently used by the real program is private, so I made a different database with "placeholder values" instead.
