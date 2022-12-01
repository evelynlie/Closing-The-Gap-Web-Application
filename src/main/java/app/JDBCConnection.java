package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs() {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code16");
                String name16  = results.getString("lga_name16");
                int code21     = results.getInt("lga_code21");
                String name21  = results.getString("lga_name21");

                // Create a LGA Object
                LGA lga = new LGA(code16, name16, code21, name21);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    // TODO: Add your required methods here
    public ArrayList<Persona> getPersona(String p_name) {
        ArrayList<Persona> personas = new ArrayList<Persona>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM PERSONA WHERE Name LIKE '" + p_name + "%'";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Persona persona = new Persona();
                persona.name = results.getString("Name");
                
                persona.age = results.getInt("Age");
                persona.ethnicity = results.getString("Ethnicity");
                persona.background = results.getString("Background");
                persona.needs_1 = results.getString("Needs_1");
                persona.needs_2 = results.getString("Needs_2");
                persona.goals_1 = results.getString("Goals_1");
                persona.goals_2 = results.getString("Goals_2");
                persona.skills_1 = results.getString("Skills_and_Experience_1");
                persona.skills_2 = results.getString("Skills_and_Experience_2");

                personas.add(persona);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return personas;
    }

    public ArrayList<Target> getTarget(int t_id) {
        ArrayList<Target> targets = new ArrayList<Target>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM SocialOutcomes WHERE id = '" + t_id + "'";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Target target = new Target();
                target.id = results.getInt("id");
                target.title = results.getString("title");
                target.description = results.getString("description");
                targets.add(target);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return targets;
    }

    public ArrayList<Population> getPopulation() {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT status, SUM(count21) as total_people FROM POPULATIONSTATISTICS GROUP BY status";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.status = results.getString("status");
                pop.Total_People = results.getInt("total_people");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }

    public ArrayList<Health> getHealth() {
        ArrayList<Health> health = new ArrayList<Health>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT status, condition, SUM(count21) as total_people21 FROM LongTermHealth GROUP BY status, condition ORDER BY status";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Health heal = new Health();
                heal.status = results.getString("status");
                heal.condition = results.getString("condition");
                heal.Total_People = results.getInt("total_people21");
                health.add(heal);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return health;
    }

    public ArrayList<Education> getEducation() {
        ArrayList<Education> education = new ArrayList<Education>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT status, education, SUM(count21) as total_people21 FROM HighestSchoolYear GROUP BY status, education ORDER BY status, level";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Education edu = new Education();
                edu.status = results.getString("status");
                edu.education = results.getString("education");
                edu.Total_People = results.getInt("total_people21");
                education.add(edu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return education;
    }

    public ArrayList<Income> getIncome() {
        ArrayList<Income> income = new ArrayList<Income>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT status, income, SUM(count21) as total_people21 FROM WeeklyIncome WHERE status IN ('At least 1 indigenous member', 'Non-indigenous household') GROUP BY status, income ORDER BY status";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Income inc = new Income();
                inc.status = results.getString("status");
                inc.income_range = results.getString("income");
                inc.Total_People = results.getInt("total_people21");
                income.add(inc);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return income;
    }

    public ArrayList<Status> getStatusByOrderAndCategory(String order, String sort) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            // No order
            if (sort.equals("NothingSelected") && (order.equals("NothingSelected"))){
                query = "SELECT *, count21 AS total_people FROM POPULATIONSTATISTICS";
            }
            else if (order.equals("NothingSelected")){
                query = "SELECT *, count21 AS total_people FROM POPULATIONSTATISTICS order by " + sort + ", lga_code21";
            }
            else {
                query = "SELECT *, count21 AS total_people FROM PopulationStatistics ORDER BY " + sort + " " + order + ", lga_code21 ";

            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // IDK HOW TO GET PERCENTAGE BASED ON ROW STATUS 

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status status = new Status();
                status.lga_code21 = results.getInt("lga_code21");
                status.status = results.getString("status");
                status.sex = results.getString("sex");
                status.age_range = results.getString("age_range");
                status.age_min = results.getInt("min_age");
                status.age_max = results.getInt("max_age");
                status.count21 = results.getInt("count21");
                status.count16 = results.getInt("count16");
                status.Total_People = results.getInt("total_people");
                statuses.add(status);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }

    public ArrayList<Health> getLTHCByOrderAndCategory(String order, String sort) {
        ArrayList<Health> healths = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") && (order.equals("NothingSelected"))){
                query = "SELECT *, count21 AS total_people FROM LongTermHealth";
            }
            else if (order.equals("NothingSelected")){
                query = "SELECT *, count21 AS total_people FROM LongTermHealth order by " + sort + ", lga_code21";
            }
            else {
                query = "SELECT *, count21 AS total_people FROM LongTermHealth ORDER BY " + sort + " " + order + ", lga_code21 ";

            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health health = new Health();
                health.lga_code21 = results.getInt("lga_code21");
                health.status = results.getString("status");
                health.sex = results.getString("sex");
                health.condition = results.getString("condition");
                health.count21 = results.getInt("count21");
                health.Total_People = results.getInt("total_people");
                healths.add(health);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healths;
    }

    public ArrayList<Education> getEducationByOrderAndCategory(String order, String sort) {
        ArrayList<Education> edu = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") && (order.equals("NothingSelected"))){
                query = "SELECT *, count21 AS total_people FROM HighestSchoolYear";
            }
            else if (order.equals("NothingSelected")){
                query = "SELECT *, count21 AS total_people FROM HighestSchoolYear ORDER BY " + sort + ", lga_code21";
            }
            else if (order.equals("education")){
                query = "SELECT *, count21 AS total_people FROM HighestSchoolYear ORDER BY level " + order + ", lga_code21 ";
            }
            else {
                query = "SELECT *, count21 AS total_people FROM HighestSchoolYear ORDER BY " + sort + " " + order + ", lga_code21 ";

            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education education = new Education();
                education.lga_code21 = results.getInt("lga_code21");
                education.status = results.getString("status");
                education.sex = results.getString("sex");
                education.education = results.getString("education");
                education.count21 = results.getInt("count21");
                education.count16 = results.getInt("count16");
                education.Total_People = results.getInt("total_people");
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return edu;
    }

    public ArrayList<Income> getLatestIncome(String order, String sort) {
        ArrayList<Income> weekly_income = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") && (order.equals("NothingSelected"))){
                query = "SELECT *, count21 AS total_people FROM WeeklyIncome";
            }
            else if (order.equals("NothingSelected")){
                query = "SELECT *, count21 AS total_people FROM WeeklyIncome WHERE status IN ('At least 1 indigenous member', 'Non-indigenous household') ORDER BY " + sort + ", lga_code21";
            }
            else {
                query = "SELECT *, count21 AS total_people FROM WeeklyIncome WHERE status IN ('At least 1 indigenous member', 'Non-indigenous household') ORDER BY " + sort + " " + order + ", lga_code21 ";

            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.income_min = results.getInt("min_income");
                income.income_max = results.getInt("max_income");
                income.count21 = results.getInt("count21");
                income.Total_People = results.getInt("total_people");
                weekly_income.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return weekly_income;
    }

    public ArrayList<Income> getIncomeScoreByPopulationAndAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<Income> incomeScore = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, area_sqkm21, total_lgapop21, total_lgapop16 " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = lga.lga_code21 AND p.lga_code21 = w1.lga_code21 " +
                "WHERE w1.status = 'At 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop; 
            }
            else {
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, area_sqkm21, total_lgapop21, total_lgapop16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21, 2) AS gapScore21, ROUND(CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS gapScore16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21 - CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS GapChange " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = lga.lga_code21 AND p.lga_code21 = w1.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort; 

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count21 = results.getInt("count21");
                income.count16 = results.getInt("count16");
                income.count21_1 = results.getInt("other_count21");
                income.count16_1 = results.getInt("other_count16");
                income.Total_People = results.getInt("total_lgapop21");
                incomeScore.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return incomeScore;
    }

    public ArrayList<Income> getIncomeScoreByPopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<Income> incomeScore = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, total_lgapop21 , total_lgapop16 " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN totalpop p " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = p.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop;
            }
            else{
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, total_lgapop21 , total_lgapop16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21, 2) AS gapScore21, ROUND(CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS gapScore16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21 - CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS GapChange " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN totalpop p " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = p.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count21 = results.getInt("count21");
                income.count16 = results.getInt("count16");
                income.count21_1 = results.getInt("other_count21");
                income.count16_1 = results.getInt("other_count16");
                income.Total_People = results.getInt("total_lgapop21");
                incomeScore.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return incomeScore;
    }

    public ArrayList<Income> getIncomeScoreByAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<Income> incomeScore = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, area_sqkm21 " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN LGA " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = lga.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + ";";
            }
            else{
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, area_sqkm21, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21, 2) AS gapScore21, ROUND(CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS gapScore16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21 - CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS GapChange " + 
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "JOIN LGA " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 AND w1.lga_code21 = lga.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " + 
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }

            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count21 = results.getInt("count21");
                income.count16 = results.getInt("count16");
                income.count21_1 = results.getInt("other_count21");
                income.count16_1 = results.getInt("other_count16");
                income.area_sqkm21 = results.getInt("area_sqkm21");
                incomeScore.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return incomeScore;
    }

    public ArrayList<Income> getIncomeScoreSorted(String sort, String order) {
        ArrayList<Income> incomeScore = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16  " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL " +
                "LIMIT 200";
            }
            else{
                query = "SELECT w1.lga_code21, w1.status, w1.income, w1.count21, w1.count16, w2.status AS other_status, w2.count21 AS other_count21, w2.count16 as other_count16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21, 2) AS gapScore21, ROUND(CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS gapScore16, ROUND(CAST(w1.count21 AS FLOAT)/w2.count21 - CAST(w1.count16 AS FLOAT)/w2.count16, 2) AS GapChange " +
                "FROM WeeklyIncome w1 " +
                "JOIN WeeklyIncome w2 " +
                "ON w1.income = w2.income AND w1.lga_code21 = w2.lga_code21 " +
                "WHERE w1.status = 'At least 1 indigenous member' AND w2.status = 'Non-indigenous household' AND w1.count21 <> w2.count21 AND w2.count16 IS NOT NULL AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }

            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count21 = results.getInt("count21");
                income.count16 = results.getInt("count16");
                income.count21_1 = results.getInt("other_count21");
                income.count16_1 = results.getInt("other_count16");
                incomeScore.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return incomeScore;
    }

    public ArrayList<Education> getEduScoreSorted(String sort, String order) {
        ArrayList<Education> eduScore = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16 " +
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL " +
                "LIMIT 200";
            }
            else{
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21, ROUND(CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS gapScore16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21 - CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS GapChange " + 
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education edu = new Education();
                edu.lga_code21 = results.getInt("lga_code21");
                edu.status = results.getString("status");
                edu.sex = results.getString("sex");
                edu.education = results.getString("education");
                edu.count21 = results.getInt("count21");
                edu.count16 = results.getInt("count16");
                edu.count21_1 = results.getInt("other_count21");
                edu.count16_1 = results.getInt("other_count16");
                eduScore.add(edu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduScore;
    }

    public ArrayList<Education> getEduScoreByPopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<Education> eduScore = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16 " +
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN totalpop p " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex AND hsy1.lga_code21 = p.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop;
            }
            else{
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21, ROUND(CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS gapScore16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21 - CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS GapChange " + 
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN totalpop p " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex AND hsy1.lga_code21 = p.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education edu = new Education();
                edu.lga_code21 = results.getInt("lga_code21");
                edu.status = results.getString("status");
                edu.sex = results.getString("sex");
                edu.education = results.getString("education");
                edu.count21 = results.getInt("count21");
                edu.count16 = results.getInt("count16");
                edu.count21_1 = results.getInt("other_count21");
                edu.count16_1 = results.getInt("other_count16");
                eduScore.add(edu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduScore;
    }

    public ArrayList<Education> getEduScoreByAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<Education> eduScore = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16, area_sqkm21 " +
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN LGA " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex AND hsy1.lga_code21 = lga.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea;
            }
            else{
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 AS other_count16, area_sqkm21, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21, ROUND(CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS gapScore16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21 - CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS GapChange " + 
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN LGA " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.sex = hsy2.sex AND hsy1.lga_code21 = lga.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }

            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education edu = new Education();
                edu.lga_code21 = results.getInt("lga_code21");
                edu.status = results.getString("status");
                edu.sex = results.getString("sex");
                edu.education = results.getString("education");
                edu.count21 = results.getInt("count21");
                edu.count16 = results.getInt("count16");
                edu.count21_1 = results.getInt("other_count21");
                edu.count16_1 = results.getInt("other_count16");
                eduScore.add(edu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduScore;
    }

    public ArrayList<Education> getEduScoreByPopulationAndAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<Education> eduScore = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 as other_count16, area_sqkm21, total_lgapop21, total_lgapop16 " +
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.lga_code21 = lga.lga_code21 AND p.lga_code21 = hsy1.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop; 
            }
            else {
                query = "SELECT hsy1.lga_code21, hsy1.status, hsy1.sex, hsy1.education, hsy1.count21, hsy1.count16, hsy2.status AS other_status, hsy2.count21 AS other_count21, hsy2.count16 as other_count16, area_sqkm21, total_lgapop21, total_lgapop16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21, ROUND(CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS gapScore16, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21 - CAST(hsy1.count16 AS FLOAT)/hsy2.count16, 2) AS GapChange " +
                "FROM HIGHESTSCHOOLYEAR hsy1 " +
                "JOIN HIGHESTSCHOOLYEAR hsy2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON hsy1.education = hsy2.education AND hsy1.lga_code21 = hsy2.lga_code21 AND hsy1.lga_code21 = lga.lga_code21 AND p.lga_code21 = hsy1.lga_code21 " +
                "WHERE hsy1.status = 'indigenous' AND hsy2.status = 'non-indigenous' AND hsy1.count21 <> hsy2.count21 AND hsy2.count16 IS NOT NULL " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND total_lgapop16 >= " + minPop + " AND total_lgapop16 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort; 

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education edu = new Education();
                edu.lga_code21 = results.getInt("lga_code21");
                edu.status = results.getString("status");
                edu.sex = results.getString("sex");
                edu.education = results.getString("education");
                edu.count21 = results.getInt("count21");
                edu.count16 = results.getInt("count16");
                edu.count21_1 = results.getInt("other_count21");
                edu.count16_1 = results.getInt("other_count16");
                eduScore.add(edu);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduScore;
    }

    public ArrayList<HealthandInc> getLTHCIncomeScoreSorted(String sort, String order) {
        ArrayList<HealthandInc> lthcIncomescore = new ArrayList<HealthandInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "ON h.lga_code21 = w.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL " +
                "LIMIT 200";
            }
            else{
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "ON h.lga_code21 = w.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealthandInc LTHCIncome = new HealthandInc();
                LTHCIncome.lga_code21 = results.getInt("lga_code21");
                LTHCIncome.sex = results.getString("sex");
                LTHCIncome.condition = results.getString("condition");
                LTHCIncome.income = results.getString("income");
                LTHCIncome.HealthindiCount21 = results.getInt("hCount21");
                LTHCIncome.HealthnonCount21 = results.getInt("hNonCount21");
                LTHCIncome.IncomeindiCount21 = results.getInt("wCount21");
                LTHCIncome.IncomenonCount21 = results.getInt("wNonCount21");
                LTHCIncome.totalIndi21 = results.getInt("total_indi21");
                LTHCIncome.totalNon21 = results.getInt("total_non21");
                LTHCIncome.score21 = results.getDouble("gapScore21");
                lthcIncomescore.add(LTHCIncome);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return lthcIncomescore;
    }

    public ArrayList<HealthandInc> getLTHCIncomeScorePopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<HealthandInc> lthcIncomescore = new ArrayList<HealthandInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL ";
            }
            else{
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealthandInc LTHCIncome = new HealthandInc();
                LTHCIncome.lga_code21 = results.getInt("lga_code21");
                LTHCIncome.sex = results.getString("sex");
                LTHCIncome.condition = results.getString("condition");
                LTHCIncome.income = results.getString("income");
                LTHCIncome.HealthindiCount21 = results.getInt("hCount21");
                LTHCIncome.HealthnonCount21 = results.getInt("hNonCount21");
                LTHCIncome.IncomeindiCount21 = results.getInt("wCount21");
                LTHCIncome.IncomenonCount21 = results.getInt("wNonCount21");
                LTHCIncome.totalIndi21 = results.getInt("total_indi21");
                LTHCIncome.totalNon21 = results.getInt("total_non21");
                LTHCIncome.score21 = results.getDouble("gapScore21");
                lthcIncomescore.add(LTHCIncome);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return lthcIncomescore;
    }

    public ArrayList<HealthandInc> getLTHCIncomeScoreAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<HealthandInc> lthcIncomescore = new ArrayList<HealthandInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL ";
            }
            else{
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealthandInc LTHCIncome = new HealthandInc();
                LTHCIncome.lga_code21 = results.getInt("lga_code21");
                LTHCIncome.sex = results.getString("sex");
                LTHCIncome.condition = results.getString("condition");
                LTHCIncome.income = results.getString("income");
                LTHCIncome.HealthindiCount21 = results.getInt("hCount21");
                LTHCIncome.HealthnonCount21 = results.getInt("hNonCount21");
                LTHCIncome.IncomeindiCount21 = results.getInt("wCount21");
                LTHCIncome.IncomenonCount21 = results.getInt("wNonCount21");
                LTHCIncome.totalIndi21 = results.getInt("total_indi21");
                LTHCIncome.totalNon21 = results.getInt("total_non21");
                LTHCIncome.score21 = results.getDouble("gapScore21");
                lthcIncomescore.add(LTHCIncome);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return lthcIncomescore;
    }

    public ArrayList<HealthandInc> getLTHCIncomeScoreAreaPopulationSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<HealthandInc> lthcIncomescore = new ArrayList<HealthandInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " + 
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = lga.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL ";
            }
            else{
                query = "SELECT h.lga_code21, h.sex, h.condition, w.income, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, w.indiCount21 AS wCount21, " +
                "w.nonCount21 AS wNonCount21, h.indiCount21 + w.indiCount21 AS total_indi21, h.nonCount21 + w.nonCount21 AS total_non21, " +
                "ROUND(CAST((h.indiCount21 + w.indiCount21) AS FLOAT)/(h.nonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM HealthGS h " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " + 
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = lga.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealthandInc LTHCIncome = new HealthandInc();
                LTHCIncome.lga_code21 = results.getInt("lga_code21");
                LTHCIncome.sex = results.getString("sex");
                LTHCIncome.condition = results.getString("condition");
                LTHCIncome.income = results.getString("income");
                LTHCIncome.HealthindiCount21 = results.getInt("hCount21");
                LTHCIncome.HealthnonCount21 = results.getInt("hNonCount21");
                LTHCIncome.IncomeindiCount21 = results.getInt("wCount21");
                LTHCIncome.IncomenonCount21 = results.getInt("wNonCount21");
                LTHCIncome.totalIndi21 = results.getInt("total_indi21");
                LTHCIncome.totalNon21 = results.getInt("total_non21");
                LTHCIncome.score21 = results.getDouble("gapScore21");
                lthcIncomescore.add(LTHCIncome);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return lthcIncomescore;
    }

    public ArrayList<EduandIncome> getEduIncomeScoreSorted(String sort, String order) {
        ArrayList<EduandIncome> eduIncomescore = new ArrayList<EduandIncome>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "ON e.lga_code21 = w.lga_code21 " +
                "WHERE gapScore16 IS NOT NULL AND gapScore21 IS NOT NULL " +
                "LIMIT 200";
            }
            else{
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "ON e.lga_code21 = w.lga_code21 " +
                "WHERE gapScore16 IS NOT NULL AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandIncome eduIncome = new EduandIncome();
                eduIncome.lga_code21 = results.getInt("lga_code21");
                eduIncome.sex = results.getString("sex");
                eduIncome.education = results.getString("education");
                eduIncome.income = results.getString("income");
                eduIncome.EduindiCount21 = results.getInt("eduCount21");
                eduIncome.EdunonCount21 = results.getInt("eduNonCount21");
                eduIncome.IncomeindiCount21 = results.getInt("wCount21");
                eduIncome.IncomenonCount21 = results.getInt("wNonCount21");
                eduIncome.totalIndi21 = results.getInt("total_indi21");
                eduIncome.totalNon21 = results.getInt("total_non21");
                eduIncome.totalIndi16 = results.getInt("total_indi16");
                eduIncome.totalNon16 = results.getInt("total_non16");
                eduIncome.score21 = results.getDouble("gapScore21");
                eduIncome.score16 = results.getDouble("gapScore16");
                eduIncome.scoreDiff = results.getDouble("gapChange");
                eduIncomescore.add(eduIncome);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduIncomescore;
    }

    public ArrayList<EduandIncome> getEduIncomeScorePopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<EduandIncome> eduIncomes = new ArrayList<EduandIncome>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL";
            }
            else{
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandIncome eduIncome = new EduandIncome();
                eduIncome.lga_code21 = results.getInt("lga_code21");
                eduIncome.sex = results.getString("sex");
                eduIncome.education = results.getString("education");
                eduIncome.income = results.getString("income");
                eduIncome.EduindiCount21 = results.getInt("eduCount21");
                eduIncome.EdunonCount21 = results.getInt("eduNonCount21");
                eduIncome.IncomeindiCount21 = results.getInt("wCount21");
                eduIncome.IncomenonCount21 = results.getInt("wNonCount21");
                eduIncome.totalIndi21 = results.getInt("total_indi21");
                eduIncome.totalNon21 = results.getInt("total_non21");
                eduIncome.totalIndi16 = results.getInt("total_indi16");
                eduIncome.totalNon16 = results.getInt("total_non16");
                eduIncome.score21 = results.getDouble("gapScore21");
                eduIncome.score16 = results.getDouble("gapScore16");
                eduIncome.scoreDiff = results.getDouble("gapChange");
                eduIncomes.add(eduIncome);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduIncomes;
    }

    public ArrayList<EduandIncome> getEduIncomeScorePopulationAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<EduandIncome> eduIncomes = new ArrayList<EduandIncome>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = p.lga_code21 AND e.lga_code21 = lga.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea;
            }
            else{
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = p.lga_code21 AND e.lga_code21 = lga.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandIncome eduIncome = new EduandIncome();
                eduIncome.lga_code21 = results.getInt("lga_code21");
                eduIncome.sex = results.getString("sex");
                eduIncome.education = results.getString("education");
                eduIncome.income = results.getString("income");
                eduIncome.EduindiCount21 = results.getInt("eduCount21");
                eduIncome.EdunonCount21 = results.getInt("eduNonCount21");
                eduIncome.IncomeindiCount21 = results.getInt("wCount21");
                eduIncome.IncomenonCount21 = results.getInt("wNonCount21");
                eduIncome.totalIndi21 = results.getInt("total_indi21");
                eduIncome.totalNon21 = results.getInt("total_non21");
                eduIncome.totalIndi16 = results.getInt("total_indi16");
                eduIncome.totalNon16 = results.getInt("total_non16");
                eduIncome.score21 = results.getDouble("gapScore21");
                eduIncome.score16 = results.getDouble("gapScore16");
                eduIncome.scoreDiff = results.getDouble("gapChange");
                eduIncomes.add(eduIncome);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduIncomes;
    }

    public ArrayList<EduandIncome> getEduIncomeScoreAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<EduandIncome> eduIncomes = new ArrayList<EduandIncome>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN LGA " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL";
            }
            else{
                query = "SELECT e.lga_code21, e.sex, e.education, w.income, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, e.indiCount16 AS eduCount16, e.nonCount16 AS eduNonCount16," +
                "w.indiCount21 AS wCount21, w.nonCount21 AS wNonCount21, w.indiCount16 AS wCount16, w.nonCount16 AS wNonCount16, e.indiCount21 + w.indiCount21 AS total_indi21, " + 
                "e.nonCount21 + w.nonCount21 AS total_non21, e.indiCount16 + w.indiCount16 AS total_indi16, e.nonCount16 + w.nonCount16 AS total_non16, " +
                "ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) AS gapScore21, " +
                "ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4) AS gapScore16, " +
                "ROUND((ROUND(CAST((e.indiCount21 + w.indiCount21) AS FLOAT)/(e.nonCount21 + w.nonCount21), 4) - (ROUND(CAST((e.indiCount16 + w.indiCount16) AS FLOAT)/(e.nonCount16 + w.nonCount16), 4))), 4) AS gapChange " +
                "FROM EducationGS e " +
                "JOIN WeeklyIncomeGS w " +
                "JOIN LGA " +
                "ON e.lga_code21 = w.lga_code21 AND e.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL AND gapScore16 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandIncome eduIncome = new EduandIncome();
                eduIncome.lga_code21 = results.getInt("lga_code21");
                eduIncome.sex = results.getString("sex");
                eduIncome.education = results.getString("education");
                eduIncome.income = results.getString("income");
                eduIncome.EduindiCount21 = results.getInt("eduCount21");
                eduIncome.EdunonCount21 = results.getInt("eduNonCount21");
                eduIncome.IncomeindiCount21 = results.getInt("wCount21");
                eduIncome.IncomenonCount21 = results.getInt("wNonCount21");
                eduIncome.totalIndi21 = results.getInt("total_indi21");
                eduIncome.totalNon21 = results.getInt("total_non21");
                eduIncome.totalIndi16 = results.getInt("total_indi16");
                eduIncome.totalNon16 = results.getInt("total_non16");
                eduIncome.score21 = results.getDouble("gapScore21");
                eduIncome.score16 = results.getDouble("gapScore16");
                eduIncome.scoreDiff = results.getDouble("gapChange");
                eduIncomes.add(eduIncome);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduIncomes;
    }

    public ArrayList<HealEduInc> getHealEduIncScoreSorted(String sort, String order) {
        ArrayList<HealEduInc> healeduinc = new ArrayList<HealEduInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL " +
                "LIMIT 200";
            }
            else{
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL " +
                "ORDER BY " + sort;
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealEduInc score = new HealEduInc();
                score.lga_code21 = results.getInt("lga_code21");
                score.condition = results.getString("condition");
                score.income = results.getString("income");
                score.education = results.getString("education");
                score.totalIndi = results.getInt("total_indi");
                score.totalNon = results.getInt("total_non");
                score.score = results.getDouble("gapScore21");
                healeduinc.add(score);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healeduinc;
    }

    public ArrayList<HealEduInc> getHealEduIncScorePopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<HealEduInc> healeduinc = new ArrayList<HealEduInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop;
            }
            else{
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " " +
                "ORDER BY " + sort;
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealEduInc score = new HealEduInc();
                score.lga_code21 = results.getInt("lga_code21");
                score.condition = results.getString("condition");
                score.income = results.getString("income");
                score.education = results.getString("education");
                score.totalIndi = results.getInt("total_indi");
                score.totalNon = results.getInt("total_non");
                score.score = results.getDouble("gapScore21");
                healeduinc.add(score);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healeduinc;
    }

    public ArrayList<HealEduInc> getHealEduIncScoreAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<HealEduInc> healeduinc = new ArrayList<HealEduInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = lga.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea;
            }
            else{
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN LGA " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = lga.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " " +
                "ORDER BY " + sort;
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealEduInc score = new HealEduInc();
                score.lga_code21 = results.getInt("lga_code21");
                score.condition = results.getString("condition");
                score.income = results.getString("income");
                score.education = results.getString("education");
                score.totalIndi = results.getInt("total_indi");
                score.totalNon = results.getInt("total_non");
                score.score = results.getDouble("gapScore21");
                healeduinc.add(score);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healeduinc;
    }

    public ArrayList<HealEduInc> getHealEduIncScorePopulationAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<HealEduInc> healeduinc = new ArrayList<HealEduInc>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN LGA " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = lga.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop;
            }
            else{
                query = "SELECT h.lga_code21, h.condition, w.income, e.education, h.newindiCount21 + e.newindiCount21 + w.indiCount21 AS total_indi, h.newNonCount21 + e.newNonCount21 + w.nonCount21 AS total_non, ROUND(CAST((h.newindiCount21 + e.newindiCount21 + w.indiCount21) AS FLOAT)/ (h.newNonCount21 + e.newNonCount21 + w.nonCount21), 4) AS gapScore21 " +
                "FROM eduNoGender e " +
                "JOIN WeeklyIncomeGS w " + 
                "JOIN lthcNoGender h " +
                "JOIN LGA " +
                "JOIN totalpop p " +
                "ON h.lga_code21 = w.lga_code21 AND h.lga_code21 = e.lga_code21 AND h.lga_code21 = lga.lga_code21 AND h.lga_code21 = p.lga_code21 " +
                "WHERE gapScore21 IS NOT NULL AND area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " " +
                "ORDER BY " + sort;
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                HealEduInc score = new HealEduInc();
                score.lga_code21 = results.getInt("lga_code21");
                score.condition = results.getString("condition");
                score.income = results.getString("income");
                score.education = results.getString("education");
                score.totalIndi = results.getInt("total_indi");
                score.totalNon = results.getInt("total_non");
                score.score = results.getDouble("gapScore21");
                healeduinc.add(score);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healeduinc;
    }

    public ArrayList<EduandHealth> getEduLTHCScoreSorted(String sort, String order) {
        ArrayList<EduandHealth> eduLTHCscore = new ArrayList<EduandHealth>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21 " +
                "FROM EducationGS e " +
                "JOIN HealthGS h " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status " +
                "LIMIT 200";
            }
            else{
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21 " +
                "FROM EducationGS e " +
                "JOIN HealthGS h " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status " +
                "WHERE gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandHealth eduHealth = new EduandHealth();
                eduHealth.lga_code21 = results.getInt("lga_code21");
                eduHealth.sex = results.getString("sex");
                eduHealth.condition = results.getString("condition");
                eduHealth.education = results.getString("education");
                eduHealth.EduindiCount21 = results.getInt("eduCount21");
                eduHealth.EdunonCount21 = results.getInt("eduNonCount21");
                eduHealth.HealthindiCount21 = results.getInt("hCount21");
                eduHealth.HealthnonCount21 = results.getInt("hNonCount21");
                eduHealth.totalIndi = results.getInt("total_indi");
                eduHealth.totalNon = results.getInt("total_non");
                eduHealth.score = results.getDouble("gapScore21");
                eduLTHCscore.add(eduHealth);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduLTHCscore;
    }

    public ArrayList<EduandHealth> getEduLTHCScorePopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<EduandHealth> eduLTHC = new ArrayList<EduandHealth>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN totalpop p " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL";
            }
            else{
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN totalpop p " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = p.lga_code21 " +
                "WHERE total_lgapop21 >=  " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandHealth eduHealth = new EduandHealth();
                eduHealth.lga_code21 = results.getInt("lga_code21");
                eduHealth.sex = results.getString("sex");
                eduHealth.condition = results.getString("condition");
                eduHealth.education = results.getString("education");
                eduHealth.EduindiCount21 = results.getInt("eduCount21");
                eduHealth.EdunonCount21 = results.getInt("eduNonCount21");
                eduHealth.HealthindiCount21 = results.getInt("hCount21");
                eduHealth.HealthnonCount21 = results.getInt("hNonCount21");
                eduHealth.totalIndi = results.getInt("total_indi");
                eduHealth.totalNon = results.getInt("total_non");
                eduHealth.score = results.getDouble("gapScore21");
                eduLTHC.add(eduHealth);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduLTHC;
    }

    public ArrayList<EduandHealth> getEduLTHCScoreAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<EduandHealth> eduLTHC = new ArrayList<EduandHealth>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21, area_sqkm21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN LGA " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL";
            }
            else{
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21, area_sqkm21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN LGA " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = lga.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandHealth eduHealth = new EduandHealth();
                eduHealth.lga_code21 = results.getInt("lga_code21");
                eduHealth.sex = results.getString("sex");
                eduHealth.condition = results.getString("condition");
                eduHealth.education = results.getString("education");
                eduHealth.EduindiCount21 = results.getInt("eduCount21");
                eduHealth.EdunonCount21 = results.getInt("eduNonCount21");
                eduHealth.HealthindiCount21 = results.getInt("hCount21");
                eduHealth.HealthnonCount21 = results.getInt("hNonCount21");
                eduHealth.totalIndi = results.getInt("total_indi");
                eduHealth.totalNon = results.getInt("total_non");
                eduHealth.score = results.getDouble("gapScore21");
                eduLTHC.add(eduHealth);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduLTHC;
    }

    public ArrayList<EduandHealth> getEduLTHCScorePopulationAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<EduandHealth> eduLTHC = new ArrayList<EduandHealth>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21, area_sqkm21, total_lgapop21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN totalpop p " + 
                "JOIN LGA " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = lga.lga_code21 AND e.lga_code21 = p.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop;
            }
            else{
                query = "SELECT e.lga_code21, e.status, e.sex, e.education, e.indiCount21 AS eduCount21, e.nonCount21 AS eduNonCount21, h.condition, h.indiCount21 AS hCount21, h.nonCount21 AS hNonCount21, e.indiCount21 + h.indiCount21 AS total_indi, e.nonCount21 + h.nonCount21 AS total_non, ROUND(CAST((e.indiCount21 + h.indiCount21) AS FLOAT)/(e.nonCount21 + h.nonCount21), 4) AS gapScore21, area_sqkm21, total_lgapop21 " +
                "FROM EducationGS e " + 
                "JOIN HealthGS h " + 
                "JOIN totalpop p " + 
                "JOIN LGA " +
                "ON e.lga_code21 = h.lga_code21 AND e.status = h.status AND e.sex = h.sex AND e.other_status = h.other_status AND e.lga_code21 = lga.lga_code21 AND e.lga_code21 = p.lga_code21 " +
                "WHERE area_sqkm21 >=  " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                EduandHealth eduHealth = new EduandHealth();
                eduHealth.lga_code21 = results.getInt("lga_code21");
                eduHealth.sex = results.getString("sex");
                eduHealth.condition = results.getString("condition");
                eduHealth.education = results.getString("education");
                eduHealth.EduindiCount21 = results.getInt("eduCount21");
                eduHealth.EdunonCount21 = results.getInt("eduNonCount21");
                eduHealth.HealthindiCount21 = results.getInt("hCount21");
                eduHealth.HealthnonCount21 = results.getInt("hNonCount21");
                eduHealth.totalIndi = results.getInt("total_indi");
                eduHealth.totalNon = results.getInt("total_non");
                eduHealth.score = results.getDouble("gapScore21");
                eduLTHC.add(eduHealth);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return eduLTHC;
    }

    public ArrayList<Health> getLTHCScoreSorted(String sort, String order) {
        ArrayList<Health> healthScore = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.lga_code21, lth2.status AS other_status, lth2.sex, lth2.condition, lth2.count21 AS other_count " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 " +
                "LIMIT 200";
            }
            else{
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.status AS other_status, lth2.count21 AS other_count, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21 " + 
                "FROM LONGTERMHEALTH lth1 " +
            	"JOIN LONGTERMHEALTH lth2 " +
                "ON lth1.condition = lth1.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort + " ";
                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
                query = query + " LIMIT 200";
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health health = new Health();
                health.lga_code21 = results.getInt("lga_code21");
                health.sex = results.getString("sex");
                health.condition = results.getString("condition");
                health.count21 = results.getInt("count21");
                health.count21_1 = results.getInt("other_count");
                healthScore.add(health);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healthScore;
    }

    public ArrayList<Health> getLTHCScoreByPopulationSorted(String minPop, String maxPop, String sort, String order) {
        ArrayList<Health> healthScore = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.lga_code21, lth2.status AS other_status, lth2.sex, lth2.condition, lth2.count21 AS other_count " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN totalpop p " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex AND lth1.lga_code21 = p.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop;
            }
            else{
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.lga_code21, lth2.status AS other_status, lth2.sex, lth2.condition, lth2.count21 AS other_count, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21 " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN totalpop p " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex AND lth1.lga_code21 = p.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health health = new Health();
                health.lga_code21 = results.getInt("lga_code21");
                health.sex = results.getString("sex");
                health.condition = results.getString("condition");
                health.count21 = results.getInt("count21");
                health.count21_1 = results.getInt("other_count");
                healthScore.add(health);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healthScore;
    }

    public ArrayList<Health> getLTHCScoreByAreaSorted(String minArea, String maxArea, String sort, String order) {
        ArrayList<Health> healthScore = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.lga_code21, lth2.status AS other_status, lth2.count21 AS other_count, area_sqkm21 " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN LGA " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex AND lth1.lga_code21 = lga.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea;
            }
            else{
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.status AS other_status, lth2.count21 AS other_count, area_sqkm21, ROUND(CAST(hsy1.count21 AS FLOAT)/hsy2.count21, 2) AS gapScore21 " + 
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN LGA " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.sex = lth2.sex AND lth1.lga_code21 = lga.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort;

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }

            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health health = new Health();
                health.lga_code21 = results.getInt("lga_code21");
                health.sex = results.getString("sex");
                health.condition = results.getString("condition");
                health.count21 = results.getInt("count21");
                health.count21_1 = results.getInt("other_count");
                healthScore.add(health);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healthScore;
    }

    public ArrayList<Health> getLTHCScoreByPopulationAndAreaSorted(String minPop, String maxPop, String minArea, String maxArea, String sort, String order) {
        ArrayList<Health> healthScore = new ArrayList<Health>();
        String query;	
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // IF NO ORDER BY GAP SCORE
            if (sort.equals("NothingSelected") || order.equals("NothingSelected")){
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.status AS other_status, lth2.count21 AS other_count, area_sqkm21, total_lgapop21 " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.lga_code21 = lga.lga_code21 AND p.lga_code21 = lth1.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop; 
            }
            else {
                query = "SELECT lth1.lga_code21, lth1.status, lth1.sex, lth1.condition, lth1.count21, lth2.status AS other_status, lth2.count21 AS other_count, area_sqkm21, total_lgapop21, ROUND(CAST(lth1.count21 AS FLOAT)/lth2.count21, 2) AS gapScore21 " +
                "FROM LONGTERMHEALTH lth1 " +
                "JOIN LONGTERMHEALTH lth2 " +
                "JOIN totalpop p " +
                "JOIN LGA " +
                "ON lth1.condition = lth2.condition AND lth1.lga_code21 = lth2.lga_code21 AND lth1.lga_code21 = lga.lga_code21 AND p.lga_code21 = lth1.lga_code21 " +
                "WHERE lth1.status = 'indigenous' AND lth2.status = 'non-indigenous' AND lth1.count21 <> lth2.count21 " +
                "AND area_sqkm21 >= " + minArea + " AND area_sqkm21 <= " + maxArea + " AND total_lgapop21 >= " + minPop + " AND total_lgapop21 <= " + maxPop + " AND gapScore21 IS NOT NULL " +
                "ORDER BY " + sort; 

                if (order.equals("ASC") || order.equals("DESC")){
                    query = query + " " + order;
                }
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health health = new Health();
                health.lga_code21 = results.getInt("lga_code21");
                health.sex = results.getString("sex");
                health.condition = results.getString("condition");
                health.count21 = results.getInt("count21");
                health.count21_1 = results.getInt("other_count");
                healthScore.add(health);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return healthScore;
    }

    public ArrayList<getState> getStateList() {
        ArrayList<getState> statuses = new ArrayList<getState>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // Get Result
            query = "SELECT lga_name21, lga_state21 FROM LGA LIMIT 10";
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                getState status = new getState();
                status.name = results.getString("lga_name21");
                status.state = results.getString("lga_state21");
                statuses.add(status);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<getLGA> getLGAList() {
        ArrayList<getLGA> statuses = new ArrayList<getLGA>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // Get Result
            query = "SELECT lga_code21, lga_name21, lga_state21, lga_type21, area_sqkm21 FROM LGA lIMIT 10";
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                getLGA status = new getLGA();
                status.lga_code21 = results.getInt("lga_code21");
                status.name = results.getString("lga_name21");
                status.state = results.getString("lga_state21");
                status.type = results.getString("lga_type21");
                status.area = results.getString("area_sqkm21");
                statuses.add(status);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    //For 2021 Difference Page
    public ArrayList<Population> getPopulation21(String dataset, String order, String sort, String filter, String status) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            String dataset_text;
            if(dataset.equals("Dataset1")) {
                dataset_text = "PopulationStatistics";
            }
            else if (dataset.equals("Dataset3")) {
                dataset_text = "HighestSchoolYear";
            }
            else {
                dataset_text = "WeeklyIncome";
                if(status.equals("indigenous")) {
                    status = "At least 1 indigenous member";
                }
                else {
                    status = "Non-indigenous household";
                }
            }
            
            query = "SELECT ABS(SUM(count21)) AS total_people FROM " + dataset_text + " JOIN LGA WHERE count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", LGA.lga_code21";
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("total_people");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }

    public ArrayList<Status> getStatusByOrderAndCategory21(String order, String sort, String filter, String status, String rank) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            // No order
            if (sort.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0' AND order by " + sort + ", count21, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count21, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *,PopulationStatistics.count21 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // IDK HOW TO GET PERCENTAGE BASED ON ROW STATUS 

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.lga_type21 = results.getString("lga_type21");
                sta.area_sqkm21 = results.getInt("area_sqkm21");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.age_range = results.getString("age_range");
                sta.age_min = results.getInt("min_age");
                sta.age_max = results.getInt("max_age");
                sta.count21 = results.getInt("count21");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }

    public ArrayList<Education> getEducationByOrderAndCategory21(String order, String sort, String filter, String status, String rank) {
        ArrayList<Education> edu = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if (sort.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND order by " + sort + ", count21, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count21, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education education = new Education();
                education.lga_code21 = results.getInt("lga_code21");
                education.lga_name21 = results.getString("lga_name21");
                education.lga_state21 = results.getString("lga_state21");
                education.lga_type21 = results.getString("lga_type21");
                education.area_sqkm21 = results.getInt("area_sqkm21");
                education.status = results.getString("status");
                education.sex = results.getString("sex");
                education.education = results.getString("education");
                education.count21 = results.getInt("count21");
                education.Total_People = results.getInt("total_people");
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return edu;
    }

    public ArrayList<Income> getLatestIncome21(String order, String sort, String filter, String status, String rank) {
        ArrayList<Income> weekly_income = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        if(status.equals("indigenous")) {
            status = "At least 1 indigenous member";
        }
        else {
            status = "Non-indigenous household";
        }
        
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            query = "SELECT *, WeeklyIncome.count21 AS Total_People FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND WeeklyIncome.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.lga_name21 = results.getString("lga_name21");
                income.lga_state21 = results.getString("lga_state21");
                income.lga_type21 = results.getString("lga_type21");
                income.area_sqkm21 = results.getInt("area_sqkm21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count21 = results.getInt("count21");
                income.Total_People = results.getInt("Total_People");
                weekly_income.add(income);
            }
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return weekly_income;
    }
    //FOR DIFFERENCE PAGE 2016
    public ArrayList<Population> getPopulation16(String dataset, String order, String sort, String filter, String status) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            String dataset_text;
            if(dataset.equals("Dataset1")) {
                dataset_text = "PopulationStatistics";
            }
            else if (dataset.equals("Dataset3")) {
                dataset_text = "HighestSchoolYear";
            }
            else {
                dataset_text = "WeeklyIncome";
                if(status.equals("indigenous")) {
                    status = "at least 1 indigenous member";
                }
                else {
                    status = "non-indigenous household";
                }
            }
            
                query = "SELECT ABS(SUM(count16)) AS total_people FROM " + dataset_text + " JOIN LGA WHERE count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, LGA.lga_code21";
            
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("total_people");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }
    public ArrayList<Population> getPopulationLI16(String dataset, String order, String sort, String filter, String status) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            query = "SELECT SUM(count16) AS Total_People FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, LGA.lga_code21";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("Total_People");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }
    public ArrayList<Population> getPopulationLI21(String dataset, String order, String sort, String filter, String status) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            query = "SELECT SUM(count21) AS Total_People FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, LGA.lga_code21";
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("Total_People");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }
    public ArrayList<Status> getStatusByOrderAndCategory16(String order, String sort, String filter, String status, String rank) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            // No order
            if (sort.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0' AND order by " + sort + ", count16, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count16, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *,PopulationStatistics.count16 AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND PopulationStatistics.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16 " + order + ", lga_code21 LIMIT " + rank;
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // IDK HOW TO GET PERCENTAGE BASED ON ROW STATUS 

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name16 = results.getString("lga_name16");
                sta.lga_state16 = results.getString("lga_state16");
                sta.lga_type16 = results.getString("lga_type16");
                sta.area_sqkm16 = results.getInt("area_sqkm16");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.age_range = results.getString("age_range");
                sta.age_min = results.getInt("min_age");
                sta.age_max = results.getInt("max_age");
                sta.count16 = results.getInt("count16");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }

    public ArrayList<Education> getEducationByOrderAndCategory16(String order, String sort, String filter, String status, String rank) {
        ArrayList<Education> edu = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND order by " + sort + ", count16, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count16, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16 " + order + ", lga_code21 LIMIT " + rank;
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education education = new Education();
                education.lga_code21 = results.getInt("lga_code21");
                education.lga_name16 = results.getString("lga_name16");
                education.lga_state16 = results.getString("lga_state16");
                education.lga_type16 = results.getString("lga_type16");
                education.area_sqkm16 = results.getInt("area_sqkm16");
                education.status = results.getString("status");
                education.sex = results.getString("sex");
                education.education = results.getString("education");
                education.count16 = results.getInt("count16");
                education.Total_People = results.getInt("total_people");
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return edu;
    }

    public ArrayList<Income> getLatestIncome16(String order, String sort, String filter, String status, String rank) {
        ArrayList<Income> weekly_income = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        if(status.equals("indigenous")) {
            status = "At least 1 indigenous member";
        }
        else if(status.equals("non-indigenous")) {
            status = "Non-indigenous household";
        }
        else {
            status = "total number of household";
        }
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected")){
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND WeeklyIncome.count16 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND WeeklyIncome.count16 != '0' AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND order by " + sort + ", count16, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND WeeklyIncome.count16 != '0' AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count16, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND WeeklyIncome.count16 != '0' AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND WeeklyIncome.count16 != '0' AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND WeeklyIncome.count21 != '0' AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16 " + order + ", lga_code21 LIMIT " + rank;
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code16");
                income.lga_name16 = results.getString("lga_name16");
                income.lga_state16 = results.getString("lga_state16");
                income.lga_type16 = results.getString("lga_type16");
                income.area_sqkm16 = results.getInt("area_sqkm16");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count16 = results.getInt("count16");
                income.Total_People = results.getInt("total_people");
                weekly_income.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return weekly_income;
    }
    
    //FOR STATE
    //For 2021 Difference Page
    public ArrayList<Population> getPopulationState21(String dataset, String order, String sort, String filter, String status, String state) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            String dataset_text;
            if(dataset.equals("Dataset1")) {
                dataset_text = "PopulationStatistics";
            }
            else if (dataset.equals("Dataset3")) {
                dataset_text = "HighestSchoolYear";
            }
            else {
                dataset_text = "WeeklyIncome";
            }
            query = "SELECT SUM(count21) AS total_people FROM " + dataset_text + " JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", LGA.lga_code21";
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("total_people");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }

    public ArrayList<Status> getStatusByOrderAndCategoryState21(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            // No order
            query = "SELECT LGA.lga_code21 AS lga_code21, lga_name21, status, sex, age_range, count21 FROM PopulationStatistics JOIN LGA WHERE LGA.lga_state21 = '" + state + "' AND PopulationStatistics.lga_code21 = LGA.lga_code21 AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // IDK HOW TO GET PERCENTAGE BASED ON ROW STATUS 

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.age_range = results.getString("age_range");
                sta.count21 = results.getInt("count21");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }

    public ArrayList<Education> getEducationByOrderAndCategoryState21(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Education> edu = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if (sort.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND order by " + sort + ", count21, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count21, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *, HighestSchoolYear.count21 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            }
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education education = new Education();
                education.lga_code21 = results.getInt("lga_code21");
                education.lga_name21 = results.getString("lga_name21");
                education.lga_state21 = results.getString("lga_state21");
                education.lga_type21 = results.getString("lga_type21");
                education.area_sqkm21 = results.getInt("area_sqkm21");
                education.status = results.getString("status");
                education.sex = results.getString("sex");
                education.education = results.getString("education");
                education.count21 = results.getInt("count21");
                education.Total_People = results.getInt("total_people");
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return edu;
    }

    public ArrayList<Income> getLatestIncomeState21(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Income> weekly_income = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        if(status.equals("indigenous")) {
            status = "At least 1 indigenous member";
        }
        else if(status.equals("non-indigenous")) {
            status = "Non-indigenous household";
        }
        else {
            status = "Total number of household";
        }
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            query = "SELECT *, WeeklyIncome.count21 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND WeeklyIncome.count21 != '0' AND " + sort + " = '" + filter + "' AND lga_state21 = '" + state + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code21");
                income.lga_name21 = results.getString("lga_name21");
                income.lga_state21 = results.getString("lga_state21");
                income.lga_type21 = results.getString("lga_type21");
                income.area_sqkm21 = results.getInt("area_sqkm21");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.income_min = results.getInt("min_income");
                income.income_max = results.getInt("max_income");
                income.count21 = results.getInt("count21");
                income.Total_People = results.getInt("total_people");
                weekly_income.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return weekly_income;
    }
    //FOR DIFFERENCE PAGE 2016
    public ArrayList<Population> getPopulationState16(String dataset, String order, String sort, String filter, String status, String state) {
        ArrayList<Population> population = new ArrayList<Population>();
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query;
            // The Query
            String dataset_text;
            if(dataset.equals("Dataset1")) {
                dataset_text = "PopulationStatistics";
            }
            else if (dataset.equals("Dataset3")) {
                dataset_text = "HighestSchoolYear";
            }
            else {
                dataset_text = "WeeklyIncome";
            }
            query = "SELECT SUM(count16) AS total_people FROM " + dataset_text + " JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, LGA.lga_code21";
            
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Persona Object
                Population pop = new Population();
                pop.Total_People = results.getInt("total_people");
                population.add(pop);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return population;
    }

    public ArrayList<Status> getStatusByOrderAndCategoryState16(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            // No order
            
            query = "SELECT LGA.lga_code21 AS lga_code21, lga_name16, status, sex, age_range, count16 FROM PopulationStatistics JOIN LGA WHERE LGA.lga_state21 = '" + state + "' AND PopulationStatistics.lga_code21 = LGA.lga_code21 AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count21 " + order + ", lga_code21 LIMIT " + rank;
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // IDK HOW TO GET PERCENTAGE BASED ON ROW STATUS 

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name16 = results.getString("lga_name16");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.age_range = results.getString("age_range");
                sta.count16 = results.getInt("count16");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }

    public ArrayList<Education> getEducationByOrderAndCategoryState16(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Education> edu = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            if (sort.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0'";
            }
            else if (filter.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND order by " + sort + ", count16, lga_code21";
            }
            else if (status.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' ORDER BY " + sort + ", count16, lga_code21 ";
            }
            else if(rank.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21";
            }
            else if(order.equals("NothingSelected")){
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count16 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16, lga_code21 LIMIT " + rank;
            }
            else {
                query = "SELECT *, HighestSchoolYear.count16 AS total_people FROM HighestSchoolYear JOIN LGA WHERE LGA.lga_state16 = '" + state + "' AND LGA.lga_state21 = '" + state + "' AND HighestSchoolYear.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND HighestSchoolYear.count21 != '0' AND " + sort + " = '" + filter + "' AND status = '" + status + "' ORDER BY " + sort + ", count16 " + order + ", lga_code21 LIMIT " + rank;
            }
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education education = new Education();
                education.lga_code21 = results.getInt("lga_code21");
                education.lga_name16 = results.getString("lga_name16");
                education.status = results.getString("status");
                education.sex = results.getString("sex");
                education.education = results.getString("education");
                education.count16 = results.getInt("count16");
                education.Total_People = results.getInt("total_people");
                edu.add(education);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return edu;
    }

    public ArrayList<Income> getLatestIncomeState16(String order, String sort, String filter, String status, String rank, String state) {
        ArrayList<Income> weekly_income = new ArrayList<Income>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        if(status.equals("indigenous")) {
            status = "At least 1 indigenous member";
        }
        else if(status.equals("non-indigenous")) {
            status = "Non-indigenous household";
        }
        else {
            status = "Total number of household";
        }
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            query = "SELECT *, WeeklyIncome.count16 AS total_people FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND LGA.lga_type21 IS NOT NULL AND LGA.lga_type16 IS NOT NULL AND LGA.area_sqkm16 != '0' AND LGA.area_sqkm21 != '0' AND WeeklyIncome.count21 != '0' AND " + sort + " = '" + filter + "' AND lga_state21 = '" + state + "' AND status = '" + status + "' ORDER BY " + sort + ", count16 " + order + ", lga_code21 LIMIT " + rank;
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income income = new Income();
                income.lga_code21 = results.getInt("lga_code16");
                income.lga_name16 = results.getString("lga_name16");
                income.status = results.getString("status");
                income.income_range = results.getString("income");
                income.count16 = results.getInt("count16");
                income.Total_People = results.getInt("total_people");
                weekly_income.add(income);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return weekly_income;
    }
    public ArrayList<Status> getLGAStatusByOrderAndCategory21(String lgaName, String min, String max, String status, String sex) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if(max.equals("above 65")) {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(PopulationStatistics.count21) AS Total_People " +
                "FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                "min_age >= " + min + " AND " + 
                "max_age <= 120 AND " + 
                "sex = '" + sex + "' AND " +
                "LGA.lga_name21 = '" + lgaName + "' AND " + 
                "status = '" + status + "'";
            }
            else {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(PopulationStatistics.count21) AS Total_People " +
                "FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                "min_age >= " + min + " AND " + 
                "max_age <= " + max + " AND " + 
                "sex = '" + sex + "' AND " +
                "LGA.lga_name21 = '" + lgaName + "' AND " + 
                "status = '" + status + "'";
            }
               
                
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Status> getSimiStatusByOrderAndCategory21(String lgaName, String min, String max, String status, String sex, String order) {
        ArrayList<Status> statuses = new ArrayList<Status>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if(max.equals("above 65")) {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, PopulationStatistics.status, PopulationStatistics.sex, SUM(PopulationStatistics.count21) AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                    "min_age >= " + min + " AND " +
                    "max_age <= 120 AND " +
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_type21 IS NOT NULL AND " + 
                    "LGA.area_sqkm21 != '0' AND " +
                    "LGA.lga_name21 != '" + lgaName + "' AND " +
                    "PopulationStatistics.count21 != '0' AND " +
                    "status = '" + status + "' " +
                    "GROUP BY count21 " +
                    "ORDER BY ABS(Total_People - (SELECT SUM(PopulationStatistics.count21) AS Total_People " +
                    "FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                    "min_age >= " + min + " AND " + 
                    "max_age <= 120 AND " + 
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_name21 = '" + lgaName + "' AND " +
                    "status = '" + status + "')) " + order + " LIMIT 5";
            }
            else {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, PopulationStatistics.status, PopulationStatistics.sex, SUM(PopulationStatistics.count21) AS total_people FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                    "min_age >= " + min + " AND " +
                    "max_age <= " + max + " AND " +
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_type21 IS NOT NULL AND " + 
                    "LGA.area_sqkm21 != '0' AND " +
                    "LGA.lga_name21 != '" + lgaName + "' AND " +
                    "PopulationStatistics.count21 != '0' AND " +
                    "status = '" + status + "' " +
                    "GROUP BY count21 " +
                    "ORDER BY ABS(Total_People - (SELECT SUM(PopulationStatistics.count21) AS Total_People " +
                    "FROM PopulationStatistics JOIN LGA WHERE PopulationStatistics.lga_code21 = LGA.lga_code21 AND " +
                    "min_age >= " + min + " AND " + 
                    "max_age <= " + max + " AND " + 
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_name21 = '" + lgaName + "' AND " +
                    "status = '" + status + "')) " + order + " LIMIT 5";
            }

                    
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Status sta = new Status();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Health> getLGALTH21(String lgaName, String condition, String status, String sex) {
        ArrayList<Health> statuses = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

                    query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(LongTermHealth.count21) AS Total_People " +
                    "FROM LongTermHealth JOIN LGA WHERE LongTermHealth.lga_code21 = LGA.lga_code21 AND " +
                    "condition = '" + condition + "' AND " +
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_name21 = '" + lgaName + "' AND " +
                    "status = '" + status + "'";
             
                
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health sta = new Health();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Health> getSimiLTH21(String lgaName, String condition, String status, String sex, String order) {
        ArrayList<Health> statuses = new ArrayList<Health>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            
                    query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, LongTermHealth.status, LongTermHealth.sex, LongTermHealth.condition, SUM(LongTermHealth.count21) AS total_people FROM LongTermHealth JOIN LGA WHERE LongTermHealth.lga_code21 = LGA.lga_code21 AND " +
                        "LGA.lga_type21 IS NOT NULL AND " + 
                        "LGA.area_sqkm21 != '0' AND " +
                        "LongTermHealth.count21 != '0' AND " +
                        "condition = '" + condition + "' AND " +
                        "sex = '" + sex + "' AND " +
                        "LGA.lga_name21 != '" + lgaName + "' AND " +
                        "status = '" + status + "' " +
                        "GROUP BY count21 " +
                        "ORDER BY ABS(Total_People - (SELECT SUM(LongTermHealth.count21) AS Total_People " +
                        "FROM LongTermHealth JOIN LGA WHERE LongTermHealth.lga_code21 = LGA.lga_code21 AND " +
                        "condition = '" + condition + "' AND " +
                        "sex = '" + sex + "' AND " +
                        "LGA.lga_name21 = '" + lgaName + "' AND " +
                        "status = '" + status + "')) " + order + " LIMIT 5";
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Health sta = new Health();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.condition = results.getString("condition");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Education> getLGAEducationByOrderAndCategory21(String lgaName, String min, String max, String status, String sex) {
        ArrayList<Education> statuses = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

                    query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(HighestSchoolYear.count21) AS Total_People " +
                    "FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND " +
                    "sex = '" + sex + "' AND " +
                    "level >= " + min + " AND " + 
                    "level <= " + max + " AND " + 
                    "LGA.lga_name21 = '" + lgaName + "' AND " +
                    "status = '" + status + "'";
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Education sta = new Education();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Education> getSimiEducationByOrderAndCategory21(String lgaName, String minEdu, String maxEdu, String status, String sex, String order) {
        ArrayList<Education> statuses = new ArrayList<Education>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

                    query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, HighestSchoolYear.status, HighestSchoolYear.sex, SUM(HighestSchoolYear.count21) AS Total_People FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND " +
                    "level >= " + minEdu + " AND " +
                    "level <= " + maxEdu + " AND " +
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_type21 IS NOT NULL AND " + 
                    "LGA.area_sqkm21 != '0' AND " +
                    "LGA.lga_name21 != '" + lgaName + "' AND " +
                    "HighestSchoolYear.count21 != '0' AND " +
                    "status = '" + status + "' " +
                    "GROUP BY count21 " +
                    "ORDER BY ABS(Total_People - (SELECT SUM(HighestSchoolYear.count21) AS Total_People " +
                    "FROM HighestSchoolYear JOIN LGA WHERE HighestSchoolYear.lga_code21 = LGA.lga_code21 AND " +
                    "level >= " + minEdu + " AND " + 
                    "level <= " + maxEdu + " AND " + 
                    "sex = '" + sex + "' AND " +
                    "LGA.lga_name21 = '" + lgaName + "' AND " +
                    "LGA.lga_type21 IS NOT NULL AND " +  
                    "LGA.area_sqkm21 != '0' AND " + 
                    "HighestSchoolYear.count21 != '0' AND " + 
                    "status = '" + status + "')) " + order + " LIMIT 5";
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                Education sta = new Education();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.status = results.getString("status");
                sta.sex = results.getString("sex");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Income2> getLGALatestIncome21(String lgaName, String minIncome, String maxIncome, String status) {
        ArrayList<Income2> statuses = new ArrayList<Income2>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if(maxIncome.equals("more than 3000")) {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(WeeklyIncome.count21) AS Total_People " +
                "FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " + 
                "max_income <= 100000 AND " + 
                "LGA.lga_name21 = '" + lgaName + "' AND " +
                "status = '" + status + "'";
            }
            else {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, SUM(WeeklyIncome.count21) AS Total_People " +
                "FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " + 
                "max_income <= " + maxIncome + " AND " + 
                "LGA.lga_name21 = '" + lgaName + "' AND " +
                "status = '" + status + "'";
            }
                
            
                
            
                
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                // Create a Status Object
                Income2 sta = new Income2();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<Income2> getSimiLatestIncome21(String lgaName, String minIncome, String maxIncome, String status, String order) {
        ArrayList<Income2> statuses = new ArrayList<Income2>();
        String query;
        // Setup the variable for the JDBC connection
        Connection connection = null;
        if(status.equals("indigenous")) {
            status = "At least 1 indigenous member";
        }
        else if(status.equals("non-indigenous")) {
            status = "Non-indigenous household";
        }
        else {
            status = "total number of household";
        }
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if(maxIncome.equals("more than 3000")) {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, WeeklyIncome.status, SUM(WeeklyIncome.count21) AS Total_People FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " +
                "max_income <= 100000 AND " +
                "LGA.lga_type21 IS NOT NULL AND " + 
                "LGA.area_sqkm21 != '0' AND " +
                "WeeklyIncome.count21 != '0' AND " +
                "LGA.lga_name21 != '" + lgaName + "' AND " +
                "status = '" + status + "' " +
                "GROUP BY count21 " +
                "ORDER BY ABS(Total_People - (SELECT SUM(WeeklyIncome.count21) AS Total_People " +
                "FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " + 
                "max_income <= 100000 AND " + 
                "LGA.lga_name21 = '" + lgaName + "' AND " +
                "status = '" + status + "')) " + order + " LIMIT 5";
            }
            else {
                query = "SELECT LGA.lga_code21, LGA.lga_name21, LGA.lga_state21, WeeklyIncome.status, SUM(WeeklyIncome.count21) AS Total_People FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " +
                "max_income <= " + maxIncome + " AND " +
                "LGA.lga_type21 IS NOT NULL AND " + 
                "LGA.area_sqkm21 != '0' AND " +
                "WeeklyIncome.count21 != '0' AND " +
                "LGA.lga_name21 != '" + lgaName + "' AND " +
                "status = '" + status + "' " +
                "GROUP BY count21 " +
                "ORDER BY ABS(Total_People - (SELECT SUM(WeeklyIncome.count21) AS Total_People " +
                "FROM WeeklyIncome JOIN LGA WHERE WeeklyIncome.lga_code21 = LGA.lga_code21 AND " +
                "min_income >= " + minIncome + " AND " + 
                "max_income <= " + maxIncome + " AND " + 
                "LGA.lga_name21 = '" + lgaName + "' AND " +
                "status = '" + status + "')) " + order + " LIMIT 5";
            }   
            
            
            
            System.out.println(query);
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            // Process all of the results
            while (results.next()) {
                Income2 sta = new Income2();
                sta.lga_code21 = results.getInt("lga_code21");
                sta.lga_name21 = results.getString("lga_name21");
                sta.lga_state21 = results.getString("lga_state21");
                sta.Total_People = results.getInt("Total_People");
                statuses.add(sta);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return statuses;
    }
    public ArrayList<String> getLGANames() {
        // Create the ArrayList of LGA objects to return
        ArrayList<String> lgaNames = new ArrayList<String>();
    
        // Setup the variable for the JDBC connection
        Connection connection = null;
    
        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);
    
            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
    
            // The Query
            String query = "SELECT lga_name21 FROM LGA where lga_name21 != '' ORDER BY LGA.lga_name21 ASC";
    
            // Get Result
            ResultSet results = statement.executeQuery(query);
    
            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String lgaName  = results.getString("lga_name21");
    
                // Add the lga object to the array
                lgaNames.add(lgaName);
            }
    
            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    
        // Finally we return all of the lga
        return lgaNames;
    }
}
