# Cover-Op-Painters
Our goal was to create a community based application for cleaning up graffiti throughout 
the city of Vancouver, and grant volunteers proof of documentation that they have worked
for the community good in a fun and helpful way. We, as an organization, provide the materials and 
equipment for volunteers' use. The application shows all the locations of 
known graffiti on a google maps api, and allows volunteers to create or join existing
graffiti cleaning events.

Volunteers have the option to create a personal account to signup and create a 
clean up event. The creator of the event will need to pick up the materials and 
equipment for the event. Proof that the graffiti cleaning was completed will be 
requested by email after the cleaning is complete.

Once the proof has been assessed and verified by a moderator on the team, 
all registered volunteers to the specified event would be provided 
with a peer evaluation. The peer evaluation's purpose is to give credit for the volunteers who 
actually showed up to the event and dedicated their time and efforts to the clean up. 
The application would provide a proof of volunteering for anyone who 
participated and received a peer evaluation.

## Current Features
At the moment, we provide login possibilities through multiple platforms including
gmail, reddit, twitter, and guest user logins. Once a user is logged in, they'll be
able to access a simple UI to either go to the google map and create an event, or 
join an event.

If they would like to create an event, they merely have to select a
specific marker on the map to create an event. The android geocoder api 
would provide a specific address to the marker. However, 
if the api is not available, the user should not be able
to create an event, as the address would not have been specified.

Once the event is created, other users would be able to access the "current events"
list and register for any of the existed listed events.

## Planned Features
Originally the scope of this project would have been to allow for users to take photos
of the event before and after, and submit them to the application for moderation review.
Once the documents were reviewed, they would be granted official documents 
granting proof of their volunteer time to help in covering up graffiti with paint that 
had been provided. 

They would have also had access to any past events that they had attended, and thus
if they had been granted documentation proof, it would be easily accessible through the
application.

However, the scope of this project was much too large for the amount of time granted. So 
our minimum viable product was a product that could allow for users to create graffiti events 
and register for events with specified time and location.

### Further Unplanned Possible Improvements
It would be a huge improvement to develop a chat functionality for any groups and registered
users to specified events. People would be able to arrange meetings and travel together to 
increase the efficiency and also get to know each other.

