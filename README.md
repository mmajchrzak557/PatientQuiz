The goal of the project was to create a tool for patients which would allow them to get information on the disease that they were diagnosed with.
The app would also give them a quiz to solve, which would determine if they understand the disease, treatment and other related information.
App was supposed to run on a tablet in an oculist office.

Main activity of the app is a list of diseases created from JSON file which would be downloaded
from certain URL - that way the app doesn`t have to be updated everytime there are some changes in just the substantive content. Another advantage is that doctors, 
given the right tool could update app contents themselves and to their liking. 

After selecting the right list item, next activity would present the user with information on the chosen disease. The data is also downloaded in JSON format.
The data itself, however, is in a HTML format which makes it possible to edit the part visible to the user like a webpage. 

The last and most important part of the app is the quiz itself. The user can initiate the test from previous activity once he is sure he understands the problem.
Then he is presented with questions, which have one or more possible answers. User inputs his answers with for buttons and once he thinks the answer is correct,
he proceeds to next question. After every question there is a message telling the user whether his answer was correct or not. Number of the questions, their content 
and correct answers are dictated by yet another JSON file.

One missing functionality of the app is an end screen displaying user`s final score. The project stopped however and will likely not be finished.
