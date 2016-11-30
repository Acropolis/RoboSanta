#Setup Google API Credentials

1. Create a new project at https://console.developers.google.com/ for the desired email.
2. Follow this tutorial to set up OAuth for the application: https://developers.google.com/gmail/api/auth/web-server#create_a_client_id_and_client_secret
3. Download the json file for the created credentials
4. Place the credentials json file in the root and call it "client_secret.json"
5. When running the application use the commandline argument -DsourceEmail=[email] using the email tied to the credentials

When you first run the application the browser will pop up asking for permissions for the application.

#Setup of the Json File
The application requires a json file to be present in a path defined by the System Property "santaFile" relative to the project directory. If the property isn't given the program will use the defaultSantas.json file at main/resources/defaultSantas.json

The file contains an array of Santa json objects. Example:
```javascript
	[
		{
			"name" : "Santa Claus", 
			"email" : "santa@northpole.org",
			"list" : "http://a.co/santasWishList",
			"noMatch" : ["Mrs. Claus","Rudolph"]
		}
	]
```

See included defaultSantas.json file in main/resources/

#Results of running the program
Each Santa entry will be matched to another entry and an email will be sent to the associated Santa's email informing them of their match.

The program will not match an entry with any other entry that shares a name in the former's noMatch element. 

All emails will be sent from the value given as the sourceEmail and will appear in that email's Sent folder.

The emails will have the title "RoboSanta has matched you with your elf for Secret Santa"

The body will be "{name},\nRoboSanta has made a match for you. You're going to get a gift for {match's name}!"

If the list element is present in the json a list will be added after the line "Here is their Christmas List: " otherwise the line will not appear. 

#Acknowledgements
MailMan code cribbed from Google's gmail quickstart tutorials.