<h1> Closing The Gap Web Application (Programming Studio 1 Project)</h1>
<h2>Table of Contents</h2>

- [Introduction](#introduction)
- [Features](#features)
- [Build Process](#build-process)
- [Contributors](#contributors)

## Introduction
This web-based application was created to help indigenous people in Australia to get information and data about "Closing the Gap" campaign. "Closing the Gap" is a campaign that is made by all Australian governments to achieve a better life quality for Aboriginal and Torres Strait Islanders in Australia by the year 2030. There are a total of 17 targets that the Australian governments wants to achieve, ranging from health to education. However, only a few campaign outcomes from the years 2016 and 2021 are focused on the web application because accounting for all of them would generate an excessive amount of data.

This project, which is an assignment that is completed in pairs, began at the beginning of October 2022 and was completed in November 2022. The website itself uses Javalin to convert Java code into HTML and uses CSS to style the website as well as SQL to retrieve the data that user selects, from the database. The final result of the project was marked as ***High Distinction*** by the judge of the assignment.

## Features

## Build Process
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

Classes backing Web pages:
```bash
├── Index.java                    - Homepages/index page. Provides a directory to all other pages
├── Page1.java                    - Sets of 6 other Java class files backing 6 other pages.
├── ...
└── Page6.java                        
```

Other Classes:
```bash
├── java/app                                - Package location for all Java files for the webserver
│         ├── App.java                      - Main Application entrypoint for Javalin
│         └── JDBCConnection.java           - Example JDBC Connection class based on Studio Project Workshop content
├── java/helper                             - Location of the helper file for loading SQLite with JDBC
│         └── CTGProcessCSV.java               - Helper Java program to load SQLite database from the provided CSVs
```

Folders:
```bash
├── /src/main                    - Location of all files as required by build configuration
│         ├── java               - Java Source location
│         │    ├── app           - Package location for all Java files for the webserver
│         │    └── helper        - Location of the helper file for loading SQLite with JDBC
│         └── resources          - Web resources (html templates / style sheets)
│               ├── css          - CSS Style-sheets. Base example style sheet (common.css) provided
│               └── images       - Image files. Base example image (RMIT Logo) provided
│ 
├── /target                      - build directory (DO NOT MODIFY)
├── /database                    - The folder to store sqlite database files (*.db files), SQL script (*.sql), and other files related to the database
├── pom.xml                      - Configure Build (DO NOT MODIFY)
└── README.md                    - This file ;)
```

Current Libraries:
* org.xerial.sqlite-jdbc (SQLite JDBC library)
* javalin (lightweight Java Webserver)
* thymeleaf (HTML template) - https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html

Libraries required as dependencies:
* By javalin
   * slf4j-simple (lightweight logging)
* By xerial/jdbc
   * sqlite-jdbc

You can run the main webserver program similar to the project workshop activities
1. Open this project within VSCode
2. Allow VSCode to read the pom.xml file
 - Allow the popups to run and "say yes" to VSCode configuring the build
 - Allow VSCode to download the required Java libraries
3. To Build & Run
 - Open the ``src/main/java/app/App.java`` source file, and select "Run" from the pop-up above the main function
4. Go to: http://localhost:7001

## Contributors
* Edward Lim Padmajaya, Bachelor of Computer Science, RMIT University.
