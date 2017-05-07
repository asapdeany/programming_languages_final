# programming_languages_final

README - Dean Sponholz

Level Attempted - Part 1

There are two files - ClientUI.java & ServerMain.java

Known Bugs - None

- The program appropriately check for errors in the IP address and Bit Length, and displays them accordingly.

- The program disables the GenPrime button while it is calculating.

- The Program uses PORT NUMBER: 13013
----------------------------------------------------------------------------------------
To run the program:

1. Run ServerMain.java to set up the Server.

	- Once you run the file, the server will wait for a connection.

2. Run the ClientUI.java to create the GUI.

	- Use the top left TEXTFIELD to submit the IP address.

	- Use the top middle TEXTFIELD to submit the Bit Length.

	- Use the top right BUTTON (titled GenPrime) to connect to the server and generate a probable prime given your ip address and bit length. 

	- The Client times out if it takes longer than 6000ms (6 Seconds) to connect.

	- Wait for the result -> It is displayed in the bottom TEXTAREA.

	- Test GUI Responsiveness by clicking the Test Button to change jPanel to a random color.
----------------------------------------------------------------------------------------


