# Closing The Gap Web Application (Programming Studio 1 Project)
This web-based application was created to help indigenous people in Australia to get information and data about "Closing the Gap" campaign. "Closing the Gap" is a campaign that is made by all Australian governments to achieve a better life quality for Aboriginal and Torres Strait Islanders in Australia by the year 2030. There are a total of 17 targets that the Australian governments wants to achieve, ranging from health to education. However, only a few campaign outcomes from the years 2016 and 2021 are focused on the web application because accounting for all of them would generate an excessive amount of data.

This project, which is an assignment that is completed in pairs, began at the beginning of October 2022 and was completed in November 2022. The website itself uses Javalin to convert Java code into HTML and uses CSS to style the website as well as SQL to retrieve the data that user selects, from the database.

The project starts with a simple layout that is provided as a starter code. 
This starter code includes:
* A Java class for the Index page (index.html).
* 6x Java classes for 6 pages. Additional pages can be added by adding additional classes.
* JDBCConnection Java class, that uses the CTG Database. This class contains one method to return all LGAs contained in the Database.
* Examples CSS (```common.css```) file in the resources directory.
* Example image (```logo.png```) file in the resources directory with where to locate any images on the website
* Starting database:
    * ```ctg.db``` - contains a starting database based on the example CTG ER Model.
* Optional helper program (``CTGProcessCSV.java``) that shows an example of how to load the SQLite database by using Java to read the CSV files and JDBC insert statements to update the CTG SQLite database.
* Optional helper SQL files (```ctg_create_tables.sql```) that creates 2 tables (```LGA``` and ```PopulationStatistics```) based on the example CTG ER Model.

The final result of the project was marked as ***High Distinction*** by the judge of the assignment.

## How to Run the Website
Debug the ```App.java``` file and copy and paste ```http://localhost:7001/``` into the browser's address bar. 
