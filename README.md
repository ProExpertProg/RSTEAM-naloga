This app was created as a solution for the following task:

Create a Java Android application, which dowloads data from the following public JSON source: https://api.wordpress.org/plugins/info/1.1/?action=query_plugins&request%5Bpage%5D=1&request%5Bper_page%5D=100&request%5Bbrowse%5D=new

Use Retrofit 2 to download the data. Parse the data and display plugin entries in the RecylerView component. Each entry should display one of the screenshots (download the picture using Glide) on the left and name of the plugin on the right.
When the user clicks on an item, a new activity should open, which should download the ZIP of the plugin files and display the number of files for every extension in a ListView. If there exists a link to the homepage, a button, which opens the link in the browser via an intent, should be displayed, otherwise the button should not be displayed.

On first startup, a dialog should pop up to ask the user to choose the number of results, which should then be saved in SharedPreferences and used as the request[per_page] query parameter for the JSON request. If the user does not enter a number, use 100 by default. This number should then be used in the following app start ups.
