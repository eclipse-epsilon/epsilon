This spreadsheet test project includes tests for 
1) abstract spreadsheet driver
2) Google Spreadsheet implementation
3) Microsoft Excel implementation

The abstract spreadsheet driver and Excel driver tests are enabled by default
and will be executed every time the project is run.

As the Google spreadsheet driver depends on running tests over the network
these tests are disabled by default. In order to enable them update the
"google_tests_enabled" property in resources/properties.txt to "yes". You will 
also need to add your Google account username and Google account password 
in the same file, and also recreate the spreadsheet files used by the common tests
(including the initial contents of the files). 