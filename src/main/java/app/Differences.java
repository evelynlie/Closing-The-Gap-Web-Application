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
public class Differences implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page6.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Differences</title>";

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
                <h1>Compare The Differences Between Censuses</h1>
            </div>
        """;

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
            </script>
        """;
    
        // Add Div for page Content
        html = html + "<div class='content'>";
        
        html = html + """
            <div class="margin">
            <br>
            <div class="grid-container">
            <div class="grid-item">
            <form action="/page6.html" method="post">
               <label for="lga">Select LGA: </label>
               <select name="lga" id="lga">
               <option value="NothingSelected">Nothing Selected</option> 
               <option value="AllLga">All LGAs</option>
               </select>
               </div>
                <p>OR</p>
                  <div class="grid-item">
                  <form action="/page6.html" method="post">
                  <label for="state">Select State: </label>
                  <select name="state" id="state">
                  <option value="NothingSelected">Nothing Selected</option>
                  <option value="ACT">Australia Capital Territory</option>
                  <option value="NSW">New South Wales</option>
                  <option value="Northern Territory">Northern Territory</option>
                  <option value="Other Australian Territories">Other Australian Territories</option>
                  <option value="QLD">Queensland</option>
                  <option value="South Australia">South Australia</option>
                  <option value="Tasmania">Tasmania</option>
                  <option value="Victoria">Victoria</option>
                  <option value="Western Australia">Western Australia</option>
                  </select>
                  </div>
                </div>
                <div class="grid-container">
                    <div class="grid-item">
                    <form action="/page6.html" method="post">
                    <label for="dataset">Select a dataset<span style='color: red'>*</span>: </label>
                    <select name="dataset" id="dataset" data-dependent-selector="#sort">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="Dataset1" data-dependent-opt="Dataset1">Dataset 1: Population</option>
                            <option value="Dataset3" data-dependent-opt="Dataset3">Dataset 3: Highest Completed School Year</option>
                            <option value="Dataset4" data-dependent-opt="Dataset4">Dataset 4: Total Household Weekly Income</option>
                        </select>
                    </div>
                    
                    <div class="grid-item">
                        <label for="sort">Sort by Category<span style='color: red'>*</span>: </label>
                        <select name="sort" id="sort" data-dependent-selector="#filter">
                            // category for dataset 1
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="sex" data-from-dependent="Dataset1" data-dependent-opt="sex">Sex</option>
                            <option value="age_range" data-from-dependent="Dataset1" data-dependent-opt="age_range" >Age Range</option>
                            // category for dataset 3
                            <option value="sex" data-dependent-opt="sex" data-from-dependent="Dataset3">Sex</option>
                            <option value="education" data-dependent-opt="education" data-from-dependent="Dataset3">Education Level</option>
                            // category for dataset 4
                            <option value="income" data-dependent-opt="income" data-from-dependent="Dataset4">Income Range</option>
                        </select>
                    </div>
                    
                  <div class="grid-item">
                  <label for="filter">Filter for Category<span style='color: red'>*</span>: </label>
                  <select name="filter" id="filter" data-dependent-selector="#status">
                  <option value="NothingSelected">Nothing Selected</option>
                    <option value="female" data-from-dependent="sex">Female</option>
                    <option value="male" data-from-dependent="sex">Male</option>
                    <option value="1-4" data-from-dependent="age_range">1-4</option>
                    <option value="5-9" data-from-dependent="age_range">5-9</option>
                    <option value="10-14" data-from-dependent="age_range">10-14</option>
                    <option value="15-19" data-from-dependent="age_range">15-19</option>
                    <option value="20-24" data-from-dependent="age_range">20-24</option>
                    <option value="25-29" data-from-dependent="age_range">25-29</option>
                    <option value="30-34" data-from-dependent="age_range">30-34</option>
                    <option value="35-39" data-from-dependent="age_range">35-39</option>
                    <option value="40-44" data-from-dependent="age_range">40-44</option>
                    <option value="45-49" data-from-dependent="age_range">45-49</option>
                    <option value="50-54" data-from-dependent="age_range">50-54</option>
                    <option value="55-59" data-from-dependent="age_range">55-59</option>
                    <option value="60-64" data-from-dependent="age_range">60-64</option>
                    <option value="Above 65" data-from-dependent="age_range">Above 65</option>
                    <option value="Did not attend School" data-from-dependent="education">Did not attend School</option>
                    <option value="Year 8 or Below" data-from-dependent="education">Year 8 or Below</option>
                    <option value="Year 9 or Equivalent" data-from-dependent="education">Year 9 or Equivalent</option>
                    <option value="Year 10 or Equivalent" data-from-dependent="education">Year 10 or Equivalent</option>
                    <option value="Year 11 or Equivalent" data-from-dependent="education">Year 11 or Equivalent</option>
                    <option value="Year 12 or Equivalent" data-from-dependent="education">Year 12 or Equivalent</option>
                    <option value="1-149" data-from-dependent="income">1-149</option>
                    <option value="150-299" data-from-dependent="income">150-299</option>
                    <option value="300-399" data-from-dependent="income">300-399</option>
                    <option value="400-499" data-from-dependent="income">400-499</option>
                    <option value="500-649" data-from-dependent="income">500-649</option>
                    <option value="650-799" data-from-dependent="income">650-799</option>
                    <option value="800-999" data-from-dependent="income">800-999</option>
                    <option value="1000-1249" data-from-dependent="income">1000-1249</option>
                    <option value="1250-1499" data-from-dependent="income">1250-1499</option>
                    <option value="1500-1999" data-from-dependent="income">1500-1999</option>
                    <option value="2000-2499" data-from-dependent="income">2000-2499</option>
                    <option value="2500-2999" data-from-dependent="income">2500-2999</option>
                    <option value="3000 or more" data-from-dependent="income">Above 3000</option>
                  </select>
                  </div>
                  </div>
                  <div class="grid-container">
                  <div class="grid-item">
                  <label for="status">Sort by Status<span style='color: red'>*</span>: </label>
                  <select name="status" id="status">
                      <option value="NothingSelected">Nothing Selected</option>
                      <option value="indigenous">Indigenous</option>
                      <option value="non-indigenous">Non-Indigenous</option>
                  </select>
                  </div>
                  <div class="grid-item">
                  <label for="rank">Select Top Rank Limit<span style='color: red'>*</span>: </label>
                  <select name="rank" id="rank">
                  <option value="NothingSelected">Nothing Selected</option>
                      <option value="5">5</option>
                      <option value="10">10</option>
                      <option value="20">20</option>
                  </select>
                  </div>
                  <div class="grid-item">
                  <label for="order">Order of Category<span style='color: red'>*</span>: </label>
                  <select name="order" id="order">
                      <option value="NothingSelected">Nothing Selected</option>
                      <option value="ASC">Ascending</option>
                      <option value="DESC">Descending</option>
                  </select>
                </div>
                </div>
                <br>
                    <button type="submit" class="btn btn-primary">Sort & Display</button>
                    <button type="reset" class="btn btn-primary">Reset</button>
                    </form>
                <h3 style='color:#f0a04b'>Note: Data may take some time to load and a loading sign will appear on the browser tab to indicate that the selection is in process.</h3>
            </div>
        """;
        
        String lga = context.formParam("lga");
        String state = context.formParam("state");
        String datasetForm = "Error";
        System.out.println(lga);
        System.out.println(state);
        
        String dataset = context.formParam("dataset");
        String order = context.formParam("order");        
        String filter = context.formParam("filter");
        String status = context.formParam("status");
        String rank = context.formParam("rank");
        String sort = context.formParam("sort");
        System.out.println(rank);
        if (dataset == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<div class='margin'><h2><i>No Results to Show</i></h2></div>"
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        } else {
            if(!lga.equals("NothingSelected") && state.equals("NothingSelected")) {
                datasetForm = "LGA";
            }
            else if(!state.equals("NothingSelected") && lga.equals("NothingSelected")) {
                datasetForm = "State";
            }
            else {
                html = html + "<div class='margin'><h2><i>Please Select Either a LGA or State Name!</i></h2></div>";
            }
            if(dataset.equals("NothingSelected")|| order.equals("NothingSelected") || filter.equals("NothingSelected") || status.equals("NothingSelected") || rank.equals("NothingSelected") || sort.equals("NothingSelected")) {
                html = html + "<div class='margin'><h2><i>Please Fill All the Filters!</i></h2></div>"
                            + "<div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            else {
                System.out.println(datasetForm);
                System.out.println(dataset);
                if(datasetForm.equals("LGA")) {
                    html = html + outputLGADataset2016(dataset, order, filter, status, rank, sort);
                    html = html + outputLGADataset2021(dataset, order, filter, status, rank, sort);
                }
                else if(datasetForm.equals("State")) {
                    html = html + outputStateDataset2016(dataset, order, filter, status, rank, sort, state);
                    html = html + outputStateDataset2021(dataset, order, filter, status, rank, sort, state);
                }
                else {
                    html = html + "<div class='margin'><h2><i>No Results to Show</i></h2></div>";
                }
            }
            
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
    
    //2016
    public String outputLGADataset2016(String dataset, String order, String filter, String status, String rank, String sort) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        String sort_text;

        // Capitalise First Character in Category
        if (sort.equals("age_range")){
            sort_text = "age range";
        }
        else if (sort.equals("total_people")){
            sort_text = "Total People";
        }
        else{
            sort_text = sort;
        }
        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Lowest";
        }
        else{
            order_text = "Highest";
        }
        

        if (dataset.equals("Dataset1")){
            ArrayList<Status> status1 = jdbc.getStatusByOrderAndCategory16(order, sort, filter, status, rank);
            html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulation16(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 100%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Age Range</th>" +
                    "<th>Total Number of People</th>" +
                    
                "</tr>";
                for (Status sta: status1) {
                    html = html + "<tr>"
                    + "<td>" + sta.getLGACode21() + "</td>" 
                    + "<td>" + sta.getName16() + "</td>"
                    + "<td>" + sta.getLGAState16() + "</td>"
                    + "<td>" + sta.getLGAType16() + "</td>"
                    + "<td>" + sta.getArea16() + "</td>"
                    + "<td>" + sta.getStatus() + "</td>" 
                    + "<td>" + sta.getSex() + "</td>" 
                    + "<td>" + sta.getAgeRange() + "</td>" 
                    + "<td>" + sta.getCount16() + "</td></tr>"; 
                }
            html = html + "</table></div>";
        }
        else if (dataset.equals("Dataset3")){
            ArrayList<Education> edu = jdbc.getEducationByOrderAndCategory16(order, sort, filter, status, rank);
            html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
    
            ArrayList<Population> population = jdbc.getPopulation16(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";

            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";

            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Education Level</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Education education : edu) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName16() + "</td>" 
                    + "<td>" + education.getLGAState16() + "</td>"
                    + "<td>" + education.getLGAType16() + "</td>"
                    + "<td>" + education.getArea16() + "</td>"
                    + "<td>" + education.getStatus() + "</td>" 
                    + "<td>" + education.getSex() + "</td>" 
                    + "<td>" + education.getEducation() + "</td>" 
                    + "<td>" + education.getCount16() + "</td></tr>";
                }
            html = html + "</table></div>";
        }
        else {
            ArrayList<Income> income = jdbc.getLatestIncome16(order, sort, filter, status, rank);
            // User does not sort by category
            if(status.equals("indigenous")) {
                status = "At least 1 indigenous member";
            }
            else if(status.equals("non-indigenous")) {
                status = "Non-indigenous household";
            }
            else {
                status = "Total number of household";
            }
            if (sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>List of Total Population in the 2016 Census</h2></div>";
            }
            else if (filter.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Total Population Sorted by " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if(order.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Total Population Sorted by the " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if (status.equals("NothingSelected")){
                order_text = order.substring(0, 1).toUpperCase() + order.substring(1);
                html = html + "<div class='margin'><h2>" + order_text + " Population Sorted by " + filter + " " + sort_text +  " in the 2016 Census</h2></div>";
            }
            else {
                html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            ArrayList<Population> population = jdbc.getPopulationLI16(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";

            if (sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>List of Population in the 2016 Census</h2></div>";
            }
            else if (filter.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Population Sorted by " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if(order.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Population Sorted by the " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if (status.equals("NothingSelected")){
                order_text = order_text.substring(0, 1).toUpperCase() + order_text.substring(1);
                html = html + "<div class='margin'><h2>" + order_text + " Population Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) +  " in the 2016 Census</h2></div>";
            }
            else if (rank.equals("NothingSelected")){
                order_text = order_text.substring(0, 1).toUpperCase() + order_text.substring(1);
                html = html + "<div class='margin'><h2>" + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) +  " in the 2016 Census</h2></div>";
            }
            else {
                html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Income Range</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName16() + "</td>"
                    + "<td>" + incomes.getLGAState16() + "</td>"
                    + "<td>" + incomes.getLGAType16() + "</td>"
                    + "<td>" + incomes.getArea16() + "</td>"
                    + "<td>" + incomes.getStatus() + "</td>" 
                    + "<td>" + incomes.getIncomeRange() + "</td>" 
                    + "<td>" + incomes.getCount16() + "</td></tr>";
                }
            html = html + "</table></div>";        }
        return html;
    }

    //2021
    public String outputLGADataset2021(String dataset, String order, String filter, String status, String rank, String sort) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        String sort_text;

        // Capitalise First Character in Category
        if (sort.equals("age_range")){
            sort_text = "age range";
        }
        else if (sort.equals("total_people")){
            sort_text = "Total People";
        }
        else{
            sort_text = sort;
        }
        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Lowest";
        }
        else{
            order_text = "Highest";
        }
        if (dataset.equals("Dataset1")){
        html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
                ArrayList<Population> population = jdbc.getPopulation21(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 100%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            ArrayList<Status> status1 = jdbc.getStatusByOrderAndCategory21(order, sort, filter, status, rank);
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Age Range</th>" +
                    "<th>Total Number of People</th>" +
                    
                "</tr>";
                for (Status sta: status1) {
                    html = html + "<tr>"
                    + "<td>" + sta.getLGACode21() + "</td>" 
                    + "<td>" + sta.getName21() + "</td>"
                    + "<td>" + sta.getLGAState21() + "</td>"
                    + "<td>" + sta.getLGAType21() + "</td>"
                    + "<td>" + sta.getArea21() + "</td>"
                    + "<td>" + sta.getStatus() + "</td>" 
                    + "<td>" + sta.getSex() + "</td>" 
                    + "<td>" + sta.getAgeRange() + "</td>" 
                    + "<td>" + sta.getCount21() + "</td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else if (dataset.equals("Dataset3")){
            ArrayList<Education> edu = jdbc.getEducationByOrderAndCategory21(order, sort, filter, status, rank);
            // User does not sort by category
            html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulation21(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Education Level</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Education education : edu) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName21() + "</td>"
                    + "<td>" + education.getLGAState21() + "</td>"
                    + "<td>" + education.getLGAType21() + "</td>"
                    + "<td>" + education.getArea21() + "</td>"
                    + "<td>" + education.getStatus() + "</td>" 
                    + "<td>" + education.getSex() + "</td>" 
                    + "<td>" + education.getEducation() + "</td>" 
                    + "<td>" + education.getCount21() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else{
            ArrayList<Income> income = jdbc.getLatestIncome21(order, sort, filter, status, rank);
            // User does not sort by category
            if(status.equals("indigenous")) {
                status = "At least 1 indigenous member";
            }
            else if(status.equals("non-indigenous")) {
                status = "Non-indigenous household";
            }
            else {
                status = "total number of household";
            }
            if (sort.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>List of Total population in the 2016 Census</h2></div>";
            }
            else if (filter.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Total Population Sorted by " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if(order.equals("NothingSelected")){
                html = html + "<div class='margin'><h2>Total Population Sorted by the " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            }
            else if (status.equals("NothingSelected")){
                order_text = order.substring(0, 1).toUpperCase() + order.substring(1);
                html = html + "<div class='margin'><h2>Total Population Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text +  " in the 2016 Census</h2></div>";
            }
            else {
                html = html + "<div class='margin'><h2>Total Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            }
            ArrayList<Population> population = jdbc.getPopulationLI21(dataset, order, sort, filter, status);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";

            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th>State</th>" +
                    "<th>LGA Type</th>" +
                    "<th>Area in Square Kilometres</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Income Range</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName21() + "</td>"
                    + "<td>" + incomes.getLGAState21() + "</td>"
                    + "<td>" + incomes.getLGAType21() + "</td>"
                    + "<td>" + incomes.getArea21() + "</td>"
                    + "<td>" + incomes.getStatus() + "</td>" 
                    + "<td>" + incomes.getIncomeRange() + "</td>" 
                    + "<td>" + incomes.getCount21() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";  
        }
        return html;
    }
    
    //2016
    public String outputStateDataset2016(String dataset, String order, String filter, String status, String rank, String sort, String state) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        String sort_text;

        // Capitalise First Character in Category
        if (sort.equals("age_range")){
            sort_text = "age range";
        }
        else if (sort.equals("total_people")){
            sort_text = "Total People";
        }
        else{
            sort_text = sort;
        }
        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Lowest";
        }
        else{
            order_text = "Highest";
        }
        

        // User does not select any dataset
        if (dataset.equals("Dataset1")){
            
            ArrayList<Status> status1 = jdbc.getStatusByOrderAndCategoryState16(order, sort, filter, status, rank, state);
            // User does not sort by category
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status + " Sorted by " + filter + " " + sort_text + " in the 2016 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulationState16(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 100%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status + " Sorted by " + filter + " " + sort_text + " in the 2016 Census</h2></div>";
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Age Range</th>" +
                    "<th>Total Number of People</th>" +
                    
                "</tr>";
                for (Status sta: status1) {
                    html = html + "<tr>"
                    + "<td>" + sta.getLGACode21() + "</td>" 
                    + "<td>" + sta.getName16() + "</td>"
                    + "<td>" + sta.getStatus() + "</td>" 
                    + "<td>" + sta.getSex() + "</td>" 
                    + "<td>" + sta.getAgeRange() + "</td>" 
                    + "<td>" + sta.getCount16() + "</td></tr>"; 
                }
            html = html + "</table></div>";
        }
        else if (dataset.equals("Dataset3")){
            ArrayList<Education> edu = jdbc.getEducationByOrderAndCategoryState16(order, sort, filter, status, rank, state);
            // User does not sort by category
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status + " Sorted by " + filter + " " + sort_text + " in the 2016 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulationState16(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status + " Sorted by " + filter + " " + sort_text + " in the 2016 Census</h2></div>";
            
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Education Level</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Education education : edu) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName16() + "</td>" 
                    + "<td>" + education.getStatus() + "</td>" 
                    + "<td>" + education.getSex() + "</td>" 
                    + "<td>" + education.getEducation() + "</td>" 
                    + "<td>" + education.getCount16() + "</td></tr>";
                }
            html = html + "</table></div>";
        }
        else {
            ArrayList<Income> income = jdbc.getLatestIncomeState16(order, sort, filter, status, rank, state);
            // User does not sort by category
            if(status.equals("indigenous")) {
                status = "At least 1 indigenous member";
            }
            else {
                status = "Non-indigenous household";
            }
            
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulationState16(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2016 Census</h2></div>";
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Income Range</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName16() + "</td>"
                    + "<td>" + incomes.getStatus() + "</td>" 
                    + "<td>" + incomes.getIncomeRange() + "</td>" 
                    + "<td>" + incomes.getCount16() + "</td></tr>";
                }
            html = html + "</table></div>";        }
        return html;
    }

    //2021
    public String outputStateDataset2021(String dataset, String order, String filter, String status, String rank, String sort, String state) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        String sort_text;

        // Capitalise First Character in Category
        if (sort.equals("age_range")){
            sort_text = "age range";
        }
        else if (sort.equals("total_people")){
            sort_text = "Total People";
        }
        else{
            sort_text = sort;
        }
        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Lowest";
        }
        else{
            order_text = "Highest";
        }

        if (dataset.equals("Dataset1")){
            ArrayList<Status> status1 = jdbc.getStatusByOrderAndCategoryState21(order, sort, filter, status, rank, state);
            // User does not sort by category
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
                ArrayList<Population> population = jdbc.getPopulationState21(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Age Range</th>" +
                    "<th>Total Number of People</th>" +
                    
                "</tr>";
                for (Status sta: status1) {
                    html = html + "<tr>"
                    + "<td>" + sta.getLGACode21() + "</td>" 
                    + "<td>" + sta.getName21() + "</td>"
                    + "<td>" + sta.getStatus() + "</td>" 
                    + "<td>" + sta.getSex() + "</td>" 
                    + "<td>" + sta.getAgeRange() + "</td>" 
                    + "<td>" + sta.getCount21() + "</td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else if (dataset.equals("Dataset3")){
            ArrayList<Education> edu = jdbc.getEducationByOrderAndCategoryState21(order, sort, filter, status, rank, state);
            // User does not sort by category
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulationState21(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";

            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Sex</th>" +
                    "<th>Education Level</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Education education : edu) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName21() + "</td>"
                    + "<td>" + education.getStatus() + "</td>" 
                    + "<td>" + education.getSex() + "</td>" 
                    + "<td>" + education.getEducation() + "</td>" 
                    + "<td>" + education.getCount21() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else{
            ArrayList<Income> income = jdbc.getLatestIncomeState21(order, sort, filter, status, rank, state);
            // User does not sort by category
            if(status.equals("indigenous")) {
                status = "At least 1 indigenous member";
            }
            else if(status.equals("non-indigenous")) {
                status = "Non-indigenous household";
            }
            else {
                status = "total number of household";
            }
            html = html + "<div class='margin'><h2>Total Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            
            ArrayList<Population> population = jdbc.getPopulationState21(dataset, order, sort, filter, status, state);
                html = html + "<div class='margin'><table style='width: 30%'>" + 
                "<tr>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                "</tr>";
                for (Population populations : population) {
                    html = html + "<tr>"
                    + "<td>" + populations.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";

            html = html + "<div class='margin'><h2>Top " + rank + " " + order_text + " Population of " + state + " of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People, Sorted by " + filter.substring(0, 1).toUpperCase() + filter.substring(1) + " " + sort_text.substring(0, 1).toUpperCase() + sort_text.substring(1) + " in the 2021 Census</h2></div>";
            html = html + "<div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 10%'>LGA Code</th>" +
                    "<th>LGA Name</th>" +
                    "<th style='width: 20%'>Status</th>" +
                    "<th style='width: 10%'>Income Range</th>" +
                    "<th>Total Number of People</th>" +
                "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName21() + "</td>"
                    + "<td>" + incomes.getStatus() + "</td>" 
                    + "<td>" + incomes.getIncomeRange() + "</td>" 
                    + "<td>" + incomes.getCount21() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        return html;
    }
}   
