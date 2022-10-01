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
<h2>Why CVcreator</h2>
There is no specific reason it is only easier for me to make my CV using this method. Hence I thought that it might be useful to others as well. In addition, you may critize the way I program and think. 
<h2>Why using this:</h2>
I personally enjoyed it beacuse I can create a CV in HTML form and at the same time print it as pdf with different structure. I can choose the structure style and concept and develope them as program, I can use this to make a personal website or simply print my CV as pdf. It is much easier for me to handle my info in this way. 
You may find it strange but hope not useless. 
<h2>A Note for developers:</h2>
I can realize how much you will suffer reading these codes as you may not find the comments on all lines. But I believe that it should not be hard to understand what I did. I hope you find some positive points in my programing style but if you found any point worth noting please do. 
<h6>If you are going to use this program over the remote connection, please take care of security</h6>
<h6>The dependencies are as shown in the maven file</h6>
<h6>If by any chance, you find this code useful, please review and adopt the database. The current structure is for single user.</h6>
<h2>How to use the project:</h2>
Simple: I recommend using an IDE and you need JAVA FXML with MAVEN to use this codes. In addition I used Scene Builder to do the main Tab design. You may find it useful.
Exectable,Docker and standalone: This project does not contain any of those however you can build as you like.
<h2>The goal of this project has never been creating a framework or an application, but to share a way of doing an easy task (CV making) in a harder but more fun way.</h2>
