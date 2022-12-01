This is a website that is created to help indigenous people in Australia to get information and data about "Closing the Gap" campaign. However, only several campaign outcomes of the year 2016 and 2021 are targeted on the website as there will be too much data if we accounted all outcomes. 

This is a project that is done in pairs and it started on the beginning of October 2022 and finished developing at November 2022. The website itself uses Javalin to convert Java code into HTML and uses CSS to style the website as well as SQL to retrieve the data that user selects, from the database.

The project starts with a simple layout that is provided as a starter code. 
This starter code provides:

* A Java class for the Index page (index.html).
* 6x Java classes for 6 pages. Additional pages can be added by adding additional classes.
* JDBCConnection Java class, that uses the CTG Database. This class contains one method to return all LGAs contained in the Database.
* Examples CSS (```common.css```) file in the resources directory.
* Example image (```logo.png```) file in the resources directory with where to locate any images on the website
* Starting database:
    * ```ctg.db``` - contains a starting database based on the example CTG ER Model.
* Optional helper program (``CTGProcessCSV.java``) that shows an example of how to load the SQLite database by using Java to read the CSV files and JDBC insert statements to update the CTG SQLite database.
* Optional helper SQL files (```ctg_create_tables.sql```) that creates 2 tables (```LGA``` and ```PopulationStatistics```) based on the example CTG ER Model.

The final result of the project was marked as high distinction by the judge of the assignment.
