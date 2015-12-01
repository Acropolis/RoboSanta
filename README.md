#Setup Google API Credentials

1. Create a new project at https://console.developers.google.com/ for the desired email.
2. Follow this tutorial to set up OAuth for the application: https://developers.google.com/gmail/api/auth/web-server#create_a_client_id_and_client_secret
3. Download the json file for the created credentials
4. Place the credentials json file in the root and call it "client_secret.json"
5. When running the application use the commandline argument -DsourceEmail=[email] using the email tied to the credentials

When you first run the application the browser will pop up asking for permissions for the application

#Setup of the CSV file
The application requires a file called santas.csv to be present in the /main/resources/ file.
This file is a csv file that has 2 required entries per line and one optional entry:

1. The name of the Santa
2. The email of the Santa
3. An optional comma separated list of people that the santa isn't allowed to be matched with.
 * Note: if a person isn't allowed to give a gift to anyone the program will never complete. You've been warned.

#Results of running the program
Each Santa entry will be matched to another entry and an email will be sent to the associated Santa's email informing them of their match.

All emails will be sent from the value given as the sourceEmail and will appear in that email's Sent folder.

The emails will have the title "RoboSanta has matched you with your elf for Secret Santa"

The body will be "RoboSanta has matched you for the Secret Santa. You're going to get a gift for [matched santa's name]"

#Acknowledgements
MailMan code cribbed from Google's gmail quickstart tutorials.