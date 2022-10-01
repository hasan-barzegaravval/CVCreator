# CVCreator
A simple Java program to make CV easier
This is a simple java fxml program to create and manage your cvs (at least I am using it). You can store your CV content on the database (MYSQL) or you 
can store as Json file. With this software you are able to create:
<li>HTML CVS</li>
<li>Printable Pdf CVS</li>
The program has a graphical interface that make the interaction easy and smooth. The structure of program coding follows the standard Java language that 
it is familiar for anyone despite of their level of experience with the Java. Though it might not seems optimized in some cases, but it is easy to modify 
and understand. 
The program consists of data definition, UI elements definitions, Common utility functions and main app controllers. The UI elements classes are extended versions of
FXML UI elements. 
The Database structure does not follow a fully relational design. The CVs are stored in table as strings that stores the ids of the corresponding elements instead of creating relational tables. 
The Json file stores the id of data in database but as someone saves the data in json form, the relation with databse is discontinued. Hence the changes in database are not reflected in Json files and you should be careful while using the both forms together. 

