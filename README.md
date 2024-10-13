README

Overview

This application is built using Jetpack Compose, following the MVVM pattern and Clean Architecture principles.

App Description

Main Map Screen

	Google Map Display: 
  	•	Shows an interactive Google Map centered on a default location upon launch.
	Current Location Button:
	•	Functionality: When clicked, the map centers and zooms into the user’s current geographical location.
	•	User Experience: Provides real-time navigation to the user’s whereabouts for easy access and interaction.
	Markers Display:
	•	Saved Locations: All saved locations are represented by markers on the map.
	•	Clustering: Markers that are in close proximity are grouped into clusters to declutter the map and enhance readability.
 
	Marker Interaction:
	•	Clicking a Marker: Navigates to the Location Detail Screen, displaying detailed information about the selected location.

Location Detail Screen

	View Location Details:
	•	Information Displayed: Shows the title, description, and associated image of the selected location.
	Edit Location:
	•	Functionality: Allows users to modify the title, description, and image of the location.
	Delete Location:
	•	Functionality: Provides an option to remove the location from the saved list.
	•	Confirmation Prompt: Asks for user confirmation before deletion to prevent accidental removals.
 
Setup Instructions

Note: This project uses secret keys that are not included in the repository. To run the application, you need to set up your own API keys.

Steps

	1.	Create a secrets.properties File:
	•	In the root directory of the project, create a file named secrets.properties.
	•	This file should not be added to version control (it’s already included in .gitignore).
	2.	Override Variables:
	•	In secrets.properties, override the variables found in secrets.defaults.properties.
	3.	Obtain Google Maps API Key:
	•	Follow the Google Maps documentation to get an API key.
	•	Enable the necessary APIs for the application:
	•	Maps SDK for Android
	4.	Sync the Project:
	•	After setting up the secrets.properties file, sync your project with Gradle to ensure the changes take effect.

Additional Information

For more details on handling secrets in Gradle, refer to the official documentation:

[Gradle Secrets Plugin Documentation](https://developers.google.com/maps/documentation/places/android-sdk/secrets-gradle-plugin)
 
