Hello! Thank you for supporting MyTravel, the one-stop shop for all you flight booking needs as provided by CSC207's very own group_0758.
Here is a step by step guide to getting your app running smooth.

Before we begin, always remember: LOG OUT FROM THE MAIN MENU WHEN FINISHED USING THE APP OR YOUR DATA WILL NOT BE SAVED/.

Chapter One: "Let's Get This Party Started"
I. Placing the text files in the correct directory.
You can run the app with no data files, but you won't be able to do anything but gaze upon that login screen and wish you could log in. The CSV files should be places in the application install file directory, which will be specific to the phone of choice. The getApplication.getFilesDir.toString() method will provide the location of the files on said phone. Be sure they are named flights.txt, clients.txt, trips.txt and passwords.txt respectively. Additional CSV files containing flights or clients may be named as you wish, and placed in this location to be added to the app by Administrators later should you desire to.

If using an emulator: open the Android Device Monitor, select your emulator of choice, and add the source files as well as any additional files to be uploaded to data/data/com.csc207.group0758.mytravel.files

II. Logging in
So you've got your files in place, eh? Good stuff. Just put in your username (email) and password and you're golden. But what if you're a new Client? Well good news for you, after that administrator added you to the system you officially exist as a Client, and when you try logging in you'll be setting your password for future use.


Chapter 2: "Clients and Administrators"

I. Navigating the Menu (as a Client)
So now you're looking at the Client menu or Administrator menu depending on who you logged in as. If you're a client, 
- searching flights and viewing such search results is available from the "Search Flights" button. 
- Viewing and editing your info is available from the "Account Information" button. Under this menu, change whatever information you’d like to modify and click save changes.
- Searching and booking itineraries will also be possible, and are accessible from the "Search/Book Itineraries" button.
- You can view booked itineraries using the "View Booked Itineraries" button. 
When you're done, remember: YOU MUST LOG OUT WHEN FINISHED OR YOUR DATA WILL NOT BE SAVED.

II. Navigating the Menu (as a Admin)
So you're an Admin eh? Don't get carried away, but you can access:
- Search, Edit, or Upload Flights: search existing flights, edit existing flights (looking them up using an existing flight number), or upload flights from a given flights filePath (eg. “flights1.txt”, if such a file was placed in the emulator/device).
- Search, Edit, or Upload Clients: look up and edit existing clients (given an existing email), view a client’s booked itineraries (again, using an existing email), or upload new clients from a given clients filePath (eg. “clients1.txt”, if such a file was placed in the emulator/device).
- Search/Book Itineraries: search possible itineraries based on existing flights and book them for an existing client (using an existing email).
How does it feel to be all-powerful? Pretty good right? Well don't let it go to your head because YOU MUST LOG OUT WHEN FINISHED OR YOUR CHANGES WILL NOT BE SAVED.


Thanks for reading!
- group_0758 xoxo
