#### PROJECT SPECIFICATION

### News App

#### Layout

CRITERIA | MEETS SPECIFICATIONS
--- | ---
Main Screen | App contains a main screen which displays multiple news stories
List Item Contents | Each list item on the main screen displays relevant text and information about the story.<br/><br/>The title of the article and the name of the section that it belongs to are required field.<br/><br/>If available, author name and date published should be included. Please note not all responses will contain these pieces of data, but it is required to include them if they are present.<br/><br/>Images are not required.
Layout Best Practices | The code adheres to all of the following best practices:<br/><br/><ul><li>Text sizes are defined in sp</li><li>Lengths are defined in dp</li><li>Padding and margin is used appropriately, such that the views are not crammed up against each other.</li></ul>

#### Functionality

CRITERIA | MEETS SPECIFICATIONS
--- | ---
Main Screen Updates | Stories shown on the main screen update properly whenever new news data is fetched from the API.
Errors | The code runs without errors.
Story Intents | Clicking on a story uses an intent to open the story in the user’s browser.
API Query | App queries the content.guardianapis.com api to fetch news stories related to the topic chosen by the student, using either the ‘test’ api key or the student’s key.
JSON Parsing | The JSON response is parsed correctly, and relevant information is stored in the app.
No Data Message | When there is no data to display, the app shows a default TextView that informs the user.
Response Validation | The app checks whether the device is connected to the internet and responds appropriately. The result of the request is validated to account for a bad server response or lack of server response.
Use of Loaders | Networking operations are done using a Loader rather than an AsyncTask.
External Libraries and Packages | The intent of this project is to give you practice writing raw Java code using the necessary classes provided by the Android framework; therefore, the use of external libraries for the core functionality will not be permitted to complete this project.
Use of Uri Builder | The student must use a Uri builder to construct the URL string to pass into the Loader constructor.

#### Code Readability

CRITERIA | MEETS SPECIFICATIONS
--- | ---
Readability | Code is easily readable so that a fellow programmer can understand the purpose of the app.
Naming Conventions | 	All variables, methods, and resource IDs are descriptively named so that another developer reading the code can easily understand their function.
Formatting | The code is properly formatted i.e. there are no unnecessary blank lines; there are no unused variables or methods; there is no commented out code.
