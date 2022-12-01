package app;

import java.util.ArrayList;

import org.thymeleaf.standard.inline.OutputExpressionInlinePreProcessorHandler;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class LatestData implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page3.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Latest Data</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='common.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        // Logo taken from https://www.google.com/url?sa=i&url=https%3A%2F%2Fcommons.wikimedia.org%2Fwiki%2FFile%3ARMIT_University_Logo.svg&psig=AOvVaw2KvbnD2q6dDKABpYa1qFY_&ust=1667801176792000&source=images&cd=vfe&ved=0CA0QjhxqFwoTCMj0-NfxmPsCFQAAAAAdAAAAABAD
        html = html + """
            <div class="logo">
                <a href='/'>
                    <img src="logo.png" alt="logo" width="75">
                </a>
            </div>
            <div class='topnav'>
                <a href='/'>Home</a>
                <a href='page3.html'>Latest Data</a>
                <a href='page4.html'>Calculate</a>
                <div class='dropdown'>
                    <button class='dropbtn'>Compare
                        <i class="fa fa-caret-down"></i>
                    </button>
                    <div class='dropdown-content'>
                        <a href='page5.html'>Similarities</a>
                        <a href='page6.html'>Differences</a>
                    </div>
                </div>
                <a href='mission.html'>Our Mission</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Latest Data Based on the 2021 Census</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // JavaScript for dependent selection in sort by category
        html = html + """
            <script>
            document.addEventListener("change", checkSelect);

            function checkSelect(evt) {
                const origin = evt.target;
                
                if (origin.dataset.dependentSelector) {
                    const selectedOptFrom = origin.querySelector("option:checked")
                    .dataset.dependentOpt || "n/a";
                    const addRemove = optData => (optData || "") === selectedOptFrom 
                    ? "add" : "remove";
                    document.querySelectorAll(`${origin.dataset.dependentSelector} option`)
                    .forEach( opt => 
                        opt.classList[addRemove(opt.dataset.fromDependent)]("display") );
                }
            }

            $(document).ready(function () {
                $('#dtBasicExample').DataTable();
                $('.dataTables_length').addClass('bs-select');
              });
            </script>
        """;
                
        // Finally we can print out all of the LGAs
        html = html + """
            <div class="margin">
            <br>
                <div class="grid-container">
                    <div class="grid-item">
                    <form action="/page3.html" method="post">
                        <label for="dataset">Select a dataset: </label>
                        <select name="dataset" id="dataset" data-dependent-selector="#sort">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="Dataset1" data-dependent-opt="Dataset1">Dataset 1: Population</option>
                            <option value="Dataset2" data-dependent-opt="Dataset2">Dataset 2: Long Term Health Conditions</option>
                            <option value="Dataset3" data-dependent-opt="Dataset3">Dataset 3: Highest Completed School Year</option>
                            <option value="Dataset4" data-dependent-opt="Dataset4">Dataset 4: Total Household Weekly Income</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="sort">Sort by Category: </label>
                        <select name="sort" id="sort">
                            // category for dataset 1
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="status" data-from-dependent="Dataset1">Status</option>
                            <option value="sex" data-from-dependent="Dataset1">Sex</option>
                            <option value="min_age" data-from-dependent="Dataset1">Age Range</option>
                            <option value="total_people" data-from-dependent="Dataset1">Total Number of People</option>
                            // category for dataset 2
                            <option value="status" data-from-dependent="Dataset2">Status</option>
                            <option value="sex" data-from-dependent="Dataset2">Sex</option>
                            <option value="condition" data-from-dependent="Dataset2">Condition</option>
                            <option value="total_people" data-from-dependent="Dataset2">Total Number of People</option>
                            // category for dataset 3
                            <option value="status" data-from-dependent="Dataset3">Status</option>
                            <option value="sex" data-from-dependent="Dataset3">Sex</option>
                            <option value="education" data-from-dependent="Dataset3">Education Level</option>
                            <option value="total_people" data-from-dependent="Dataset3">Total Number of People</option>
                            // category for dataset 4
                            <option value="status" data-from-dependent="Dataset4">Status</option>
                            <option value="income" data-from-dependent="Dataset4">Income Range</option>
                            <option value="total_people" data-from-dependent="Dataset4">Total Number of People</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="order">Order of Category: </label>
                        <select name="order" id="order">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="ASC">Ascending</option>
                            <option value="DESC">Descending</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <button type="submit" class="btn btn-primary">Sort & Display</button>
                        <button type="reset" class="btn btn-primary">Reset</button>
                    </div>
                    </form>
                </div>
                <h3 style='color:#f0a04b'>Note: Data may take some time to load and a loading sign will appear on the browser tab to indicate that the selection is in process.</h3>
                <hr>
                </div>
        """;

        String dataset = context.formParam("dataset");
        String order = context.formParam("order");        
        String sort = context.formParam("sort");
        if (dataset == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<div class='margin'><h2><i>No Results to Show</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        } else {
            // If NOT NULL, then lookup the movie by type!
            html = html + outputDataset(dataset, order,sort);
        }

        // Finish the List HTML
        html = html + "</ul>";

        // Close Content div
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputDataset(String dataset, String order, String sort) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        String sort_text;

        // Capitalise First Character in Category
        if (sort.equals("min_age")){
            sort_text = "Age Range";
        }
        else if (sort.equals("total_people")){
            sort_text = "Total People";
        }
        else{
            sort_text = sort.substring(0, 1).toUpperCase() + sort.substring(1);
        }

        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Ascending";
        }
        else{
            order_text = "Descending";
        }

        // User does not select any dataset
        if (dataset.equals("NothingSelected")){
            html = html + "<div class='margin'><h2><i>Please Select A Dataset</i></h2></div>" 
            + "<div class='footer'>"
            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
            + "</div>";           }
        // User select dataset 1
        else if (dataset.equals("Dataset1")){
            ArrayList<Status> status1 = jdbc.getStatusByOrderAndCategory(order, sort);
            html = html + "<div class='margin'><h2>Population</h2></div>";
            html = html + "<div class='margin'><h3>Overall View</h3></div>";
            ArrayList<Population> population = jdbc.getPopulation();
            html = html + "<div class='margin'><table style='width: 40%'>" + 
                "<tr>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 20%'>Total Number of People</th>" +
                "</tr>";
            for (Population populations : population) {
                html = html + "<tr>"
                    + "<td>" + populations.getStatus() + "</td>" 
                    + "<td style='text-align: center'>" + populations.getTotalPeople() + "</td></tr>"; 
            }
            html = html + "</table></div>";
            // User Sort by Category
            html = html + "<div class='margin'><hr><h3>Detailed View</h3></div>";
            if (!sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in  " + order_text + " Order</h3></div>";
            }
            html = html + "<div class='margin'><table style='width: 100%' id='dtBasicExample'" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Age Range</th>" +
                    "<th>Total Number of People</th>" +
                    "<th>Percentage of Population Based on Status</th>" +
                "</tr>";
                for (Status status : status1) {
                    html = html + "<tr>"
                    + "<td style='text-align: center'>" + status.getLGACode21() + "</td>" 
                    + "<td>" + status.getStatus() + "</td>" 
                    + "<td>" + status.getSex() + "</td>" 
                    + "<td>" + status.getAgeRange() + "</td>" 
                    + "<td style='text-align: center'>" + status.getTotalPeople() + "</td>" 
                    + "<td style='text-align: center'><b>" + status.getPercentage21() + "%</b></td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        // User select dataset 2
        else if (dataset.equals(("Dataset2"))){
            ArrayList<Health> health1 = jdbc.getLTHCByOrderAndCategory(order, sort);
            // User does not sort by categoryx
            ArrayList<Health> heal = jdbc.getHealth();
            html = html + "<div class='margin'><h2>Long Term Health Conditions</h2></div>";
            html = html + "<div class='margin'><h3>Overall View</h3></div>";
            html = html + "<div class='margin'><table style='width: 60%'>" + 
                "<tr>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 20%'>Condition Level</th>" +
                    "<th style='width: 20%'>Total Number of People</th>" +
                "</tr>";
            for (Health heals : heal) {
                html = html + "<tr>"
                + "<td>" + heals.getStatus() + "</td>" 
                + "<td>" + heals.getCondition() + "</td>" 
                + "<td style='text-align: center'>" + heals.getTotalPeople() + "</td></tr>"; 
            }
            html = html + "</table></div>";
            // User Sort by Sex, Condition, Status, Total People
            html = html + "<div class='margin'><hr><h3>Detailed View</h3></div>";
            if (!sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in  " + order_text + " Order</h3></div>";
            }
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Condition</th>" +
                    "<th>Total Number of People</th>" +
                    "<th>Percentage of Population Based on Status</th>" +
                "</tr>";
                for (Health health : health1) {
                    html = html + "<tr>"
                    + "<td style='text-align: center'>" + health.getLGACode21() + "</td>" 
                    + "<td>" + health.getStatus() + "</td>" 
                    + "<td>" + health.getSex() + "</td>" 
                    + "<td>" + health.getCondition() + "</td>" 
                    + "<td style='text-align: center'>" + health.getCount21() + "</td>" 
                    + "<td style='text-align: center'><b>" + health.getPercentage21() + "%</b></td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else if (dataset.equals("Dataset3")){
            ArrayList<Education> edu = jdbc.getEducationByOrderAndCategory(order, sort);
            ArrayList<Education> education = jdbc.getEducation();
            html = html + "<div class='margin'><h2>Highest Completed School Year</h2></div>";
            html = html + "<div class='margin'><h3>Overall View</h3></div>";
            html = html + "<div class='margin'><table style='width: 60%'>" + 
                "<tr>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 20%'>Education Level</th>" +
                    "<th style='width: 20%'>Total Number of People</th>" +
                "</tr>";
            for (Education educations : education) {
                html = html + "<tr>"
                    + "<td>" + educations.getStatus() + "</td>" 
                    + "<td>" + educations.getEducation() + "</td>" 
                    + "<td style='text-align: center'>" + educations.getTotalPeople() + "</td></tr>"; 
            }
            html = html + "</table></div>";
            html = html + "<div class='margin'><hr><h3>Detailed View</h3></div>";
            // User does sort by category
            if (!sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in  " + order_text + " Order</h3></div>";
            }
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Education Level</th>" +
                    "<th>Total Number of People</th>" +
                    "<th>Percentage of Population Based on Status</th>" +
                "</tr>";
                for (Education edus : edu) {
                    html = html + "<tr>"
                    + "<td style='text-align: center'>" + edus.getLGACode21() + "</td>" 
                    + "<td>" + edus.getStatus() + "</td>" 
                    + "<td>" + edus.getSex() + "</td>" 
                    + "<td>" + edus.getEducation() + "</td>" 
                    + "<td style='text-align: center'>" + edus.getCount21() + "</td>" 
                    + "<td style='text-align: center'><b>" + edus.getPercentage21() + "%</b></td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else{
            ArrayList<Income> income = jdbc.getLatestIncome(order, sort);
            ArrayList<Income> inc = jdbc.getIncome();
            html = html + "<div class='margin'><h2>Household Weekly Income</h2></div>";
            html = html + "<div class='margin'><h3>Overall View</h3></div>";
            html = html + "<div class='margin'><table style='width: 60%'>" + 
                "<tr>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 20%'>Income Range</th>" +
                    "<th style='width: 20%'>Total Number of People</th>" +
                "</tr>";
            for (Income incs : inc) {
                html = html + "<tr>"
                    + "<td>" + incs.getStatus() + "</td>" 
                    + "<td>AU$" + incs.getIncomeRange() + "</td>" 
                    + "<td style='text-align: center'>" + incs.getTotalPeople() + "</td></tr>"; 
            }
            html = html + "</table></div>";
            html = html + "<div class='margin'><hr><h3>Detailed View</h3></div>";
            // User does sort by category
            if (!sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in  " + order_text + " Order</h3></div>";
            }
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Income Range</th>" +
                    "<th>Total Number of People</th>" +
                    "<th>Percentage of Population Based on Status</th>" +
                "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                    + "<td style='text-align: center'>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getStatus() + "</td>" 
                    + "<td>AU$" + incomes.getIncomeRange() + "</td>" 
                    + "<td style='text-align: center'>" + incomes.getCount21() + "</td>" 
                    + "<td style='text-align: center'><b>" + incomes.getPercentage21() + "%</b></td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";      
        }
        return html;
    }
}