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
public class Similarities implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page5.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Similarities</title>";

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
                <h1>Compare The Similarities Between LGAs</h1>
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
            </script>
        """;
            
        html = html + """
            <div class="margin">
            <br>
                <div class="grid-container">
                    <div class="grid-item">
                        <form action="/page5.html" method="post">
                            <label for="dataset">Select a dataset: </label>
                            <select name="dataset" id="dataset">
                                <option value="NothingSelected">Nothing Selected</option>
                                <option value="Dataset1">Dataset 1: Population</option>
                                <option value="Dataset2">Dataset 2: Long Term Health Conditions</option>
                                <option value="Dataset3">Dataset 3: Highest Completed School Year</option>
                                <option value="Dataset4">Dataset 4: Total Household Weekly Income</option>
                            </select>
                    </div>
                    <div class="grid-item">
                        <form action="/page6.html" method="post">
                            <label for="lgaName">Select a LGA Name to find the similarities: </label>
                            <select name="lgaName" id="lgaName">
                                <option value="NothingSelected">Nothing Selected</option> 
                                """;
                                JDBCConnection jdbc = new JDBCConnection();
                                ArrayList<String> lgaNames = jdbc.getLGANames();
                                for (String lga_name : lgaNames) {
                                    html += "<option value = " + lga_name + ">" + lga_name + "</option>";
                                }
                            html+= "</select>";
                            html +="""
                    </div>
                    <br>
                </div>   
                <div class="grid-container">
                    <div class="grid-item">
                        <label for="sex">Sort by Gender (Dataset 1): </label>
                        <select name="sex" id="sex">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="female">Female</option>
                            <option value="male">Male</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="min">Minimum Age (Dataset 1): </label>
                        <select name="min" id="min">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="1">1</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                            <option value="25">25</option>
                            <option value="30">30</option>
                            <option value="35">35</option>
                            <option value="40">40</option>
                            <option value="45">45</option>
                            <option value="50">50</option>
                            <option value="55">55</option>
                            <option value="60">60</option>
                            <option value="65">Above 65</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="max">Maximum Age (Dataset 1): </label>
                        <select name="max" id="max">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="1">1</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="20">20</option>
                            <option value="25">25</option>
                            <option value="30">30</option>
                            <option value="35">35</option>
                            <option value="40">40</option>
                            <option value="45">45</option>
                            <option value="50">50</option>
                            <option value="55">55</option>
                            <option value="60">60</option>
                            <option value="120">Above 65</option>
                        </select>
                    </div>
                </div>
                <div class="grid-container">
                    <div class="grid-item">
                        <label for="sexCon">Sort by Gender (Dataset 2): </label>
                        <select name="sexCon" id="sexCon">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="female">Female</option>
                            <option value="male">Male</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="condition">Sort by Disease (Dataset 2): </label>
                        <select name="condition" id="condition">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="arthritis">Arthritis</option>
                            <option value="asthma">Asthma</option>
                            <option value="cancer">Cancer</option>
                            <option value="dementia">Dementia</option>
                            <option value="diabetes">Diabetes</option>
                            <option value="heart disease">Heart Disease</option>
                            <option value="kidney disease">Kidney Disease</option>
                            <option value="lung condition">Lung Condition</option>
                            <option value="mental health">Mental Health</option>
                            <option value="stroke">Stroke</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                </div>
                <div class="grid-container">
                    <div class="grid-item">
                        <label for="sexEdu">Sort by Gender (Dataset 3): </label>
                        <select name="sexEdu" id="sexEdu">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="female">Female</option>
                            <option value="male">Male</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="minEdu">Minimum School Year Range (Dataset 3): </label>
                        <select name="minEdu" id="minEdu">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="7">Did not attend School</option>
                            <option value="8">Year 8 or Below</option>
                            <option value="9">Year 9 or Equivalent</option>
                            <option value="10">Year 10 or Equivalent</option>
                            <option value="11">Year 11 or Equivalent</option>
                            <option value="12">Year 12 or Equivalent</option>
                        </select>
                    </div>
                    <div class="grid-item">
                        <label for="maxEdu">Maximum School Year Range (Dataset 3): </label>
                        <select name="maxEdu" id="maxEdu">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="7">Did not attend School</option>
                            <option value="8">Year 8 or Below</option>
                            <option value="9">Year 9 or Equivalent</option>
                            <option value="10">Year 10 or Equivalent</option>
                            <option value="11">Year 11 or Equivalent</option>
                            <option value="12">Year 12 or Equivalent</option>
                        </select>
                    </div>
                </div>
                <div class="grid-container">
                    <div class="grid-item">
                        <label for="minIncome">Minimum Income (Dataset 4): </label>
                        <select name="minIncome" id="minIncome">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="1">1</option>
                            <option value="150">150</option>
                            <option value="300">300</option>
                            <option value="400">400</option>
                            <option value="500">500</option>
                            <option value="650">650</option>
                            <option value="800">800</option>
                            <option value="1000">1000</option>
                            <option value="1250">1250</option>
                            <option value="1500">1500</option>
                            <option value="2000">2000</option>
                            <option value="2500">2500</option>
                            <option value="3000">Above 3000</option>
                        </select>
                    </div>  
                    <div class="grid-item">
                        <label for="maxIncome">Maximum Income (Dataset 4): </label>
                        <select name="maxIncome" id="maxIncome">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="1">1</option>
                            <option value="150">150</option>
                            <option value="300">300</option>
                            <option value="400">400</option>
                            <option value="500">500</option>
                            <option value="650">650</option>
                            <option value="800">800</option>
                            <option value="1000">1000</option>
                            <option value="1250">1250</option>
                            <option value="1500">1500</option>
                            <option value="2000">2000</option>
                            <option value="2500">2500</option>
                            <option value="more than 3000">Above 3000</option>
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
                        <label for="order">Order by Most / Least Similarity<span style='color: red'>*</span>: </label>
                        <select name="order" id="order">
                            <option value="NothingSelected">Nothing Selected</option>
                            <option value="ASC">Most Similar</option>
                            <option value="DESC">Least Similar</option>
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

        String dataset = context.formParam("dataset");        
        String lgaName = context.formParam("lgaName");
        String min = context.formParam("min");
        String max = context.formParam("max");
        String minEdu = context.formParam("minEdu");
        String maxEdu = context.formParam("maxEdu");
        String minIncome = context.formParam("minIncome");
        String maxIncome = context.formParam("maxIncome");
        String condition = context.formParam("condition");
        String status = context.formParam("status");
        String sex = "null";
        if (dataset == null) {
            
        }
        else if((dataset.equals("Dataset1"))) {
            sex = context.formParam("sex");
        }
        else if((dataset.equals("Dataset2")) ){
            String sexCon = context.formParam("sexCon");
            sex = sexCon;
        }
        else if((dataset.equals("Dataset3"))) {
            String sexEdu = context.formParam("sexEdu");
            sex = sexEdu;
        }
        else {
            sex = context.formParam("sex");
        }
        System.out.println(sex);
        String order = context.formParam("order");
        if (dataset == null) {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<div class='margin'><h2><i>No Results to Show</i></h2></div>"
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        } else {
            if (dataset.equals("NothingSelected")){
                html = html + "<div class='margin'><h2><i>Please Select a Dataset!</i></h2></div>"
                            + "<div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            else if(lgaName.equals("NothingSelected")){
                html = html + "<div class='margin'><h2><i>Please Select the LGA Name!</i></h2></div>"
                            + "<div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            else if(dataset.equals("Dataset1")){
                if(((sex.equals("NothingSelected")) || (min.equals("NothingSelected")) || (max.equals("NothingSelected")))) {
                    html = html + "<div class='margin'><h2><i>Please Fill All the Filters Depending on the Dataset!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((status.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2><i>Please Select a Status to Sort the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Please Select the Order to Display the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (!minEdu.equals("NothingSelected") || !maxEdu.equals("NothingSelected") || !condition.equals("NothingSelected") || !minIncome.equals("NothingSelected") || !maxIncome.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Wrong Filter Selected! Please ONLY Select the Filters Depending on the Dataset</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((!sex.equals("NothingSelected")) && (!min.equals("NothingSelected")) && (!max.equals("NothingSelected")) && (!status.equals("NothingSelected")) && (!order.equals("NothingSelected"))){
                    html = html + outputSimiDataset2021(dataset,lgaName, min, max, minEdu, maxEdu, minIncome, maxIncome, condition, status, sex, order);
                }
            }
            else if(dataset.equals("Dataset2")){
                if((sex.equals("NothingSelected")) || (condition.equals("NothingSelected"))) {
                    html = html + "<div class='margin'><h2><i>Please Fill All the Filters Depending on the Dataset!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((status.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2><i>Please Select a Status to Sort the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Please Select the Order to Display the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (!minEdu.equals("NothingSelected") || !maxEdu.equals("NothingSelected") || !min.equals("NothingSelected") || !max.equals("NothingSelected")|| !minIncome.equals("NothingSelected") || !maxIncome.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Wrong Filter Selected! Please ONLY Select the Filters Depending on the Dataset</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((!sex.equals("NothingSelected")) && (!condition.equals("NothingSelected"))){
                    html = html + outputSimiDataset2021(dataset,lgaName, min, max, minEdu, maxEdu, minIncome, maxIncome, condition, status, sex, order);
                }
            }
            else if(dataset.equals("Dataset3")){
                if((sex.equals("NothingSelected")) || (minEdu.equals("NothingSelected")) || (maxEdu.equals("NothingSelected"))) {
                    html = html + "<div class='margin'><h2><i>Please Fill All the Filters Depending on the Dataset!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((status.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2><i>Please Select a Status to Sort the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Please Select the Order to Display the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (!min.equals("NothingSelected") || !max.equals("NothingSelected")|| !minIncome.equals("NothingSelected") || !maxIncome.equals("NothingSelected") || !condition.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Wrong Filter Selected! Please ONLY Select the Filters Depending on the Dataset</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((!sex.equals("NothingSelected")) && (!minEdu.equals("NothingSelected")) && (!maxEdu.equals("NothingSelected"))){
                    html = html + outputSimiDataset2021(dataset,lgaName, min, max, minEdu, maxEdu, minIncome, maxIncome, condition, status, sex, order);
                }
            }
            else {
                if((minIncome.equals("NothingSelected")) || (maxIncome.equals("NothingSelected"))) {
                    html = html + "<div class='margin'><h2><i>Please fill all the filters depending on the dataset!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((status.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2><i>Please Select a Status to Sort the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Please Select the Order to Display the Result!</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if (!min.equals("NothingSelected") || !max.equals("NothingSelected")|| !minEdu.equals("NothingSelected") || !maxEdu.equals("NothingSelected") || !condition.equals("NothingSelected") || !sex.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2><i>Wrong Filter Selected! Please ONLY Select the Filters Depending on the Dataset</i></h2></div>"
                                + "<div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
                else if ((!minIncome.equals("NothingSelected")) || (!maxIncome.equals("NothingSelected"))){
                    html = html + outputSimiDataset2021(dataset,lgaName, min, max, minEdu, maxEdu, minIncome, maxIncome, condition, status, sex, order);
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
    
    //2021
    public String outputSimiDataset2021(String dataset, String lgaName, String min, String max, String minEdu, String maxEdu, String minIncome, String maxIncome, String condition, String status, String sex, String order) {
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";

        // Capitalise First Character in Category
        
        // Assign Proper Word for Order
        String order_text;
        if (order.equals("NothingSelected")){
            order_text = "Normal";
        }
        else if (order.equals("ASC")){
            order_text = "Most";
        }
        else{
            order_text = "Least";
        }

        // User does not select any dataset
        if (dataset.equals("NothingSelected")){
            html = html + "<div class='margin'><h2><i>No Results to Show<br>Please Select a Dataset</i></h2></div>";
        }
        // User select dataset 1
        else if (dataset.equals("Dataset1")){
            ArrayList<Status> status1 = jdbc.getSimiStatusByOrderAndCategory21(lgaName, min, max, status, sex, order);
            html = html + "<div class='margin'><h2>Total Number of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People Aged Between " + min + "-" + max + ", Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex in " + lgaName + "</h2></div>";
            ArrayList<Status> status2 = jdbc.getLGAStatusByOrderAndCategory21(lgaName, min, max, status, sex);
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                "<tr>" +
                    "<th style='width: 20%' text-align:center>LGA Code</th>" +
                    "<th style='width: 30%'>LGA Name</th>" +
                    "<th style='width: 20%'>State</th>" +
                    "<th style='width: 30%'>Total Number of People</th>" +
                    
                "</tr>";
                for (Status st : status2) {
                    html = html + "<tr>"
                    + "<td>" + st.getLGACode21() + "</td>" 
                    + "<td>" + st.getName21() + "</td>"
                    + "<td>" + st.getLGAState21() + "</td>" 
                    + "<td>" + st.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top 5 " + order_text + " Similar LGA to " + lgaName + " Based on " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People Aged Between " + min + "-" + max + ", Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex</h2></div>";
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +
            "</tr>";
                for (Status sta: status1) {
                    html = html + "<tr>"
                    + "<td>" + sta.getLGACode21() + "</td>" 
                    + "<td>" + sta.getName21() + "</td>"
                    + "<td>" + sta.getLGAState21() + "</td>" 
                    + "<td>" + sta.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else if (dataset.equals("Dataset2")) {
            ArrayList<Health> health1 = jdbc.getSimiLTH21(lgaName, condition, status, sex, order);
            // User does not sort by category
            
            html = html + "<div class='margin'><h2>Total Number of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People that have " + condition.substring(0, 1).toUpperCase() + condition.substring(1) + " Health Condition, Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex in " + lgaName + "</h2></div>";
            ArrayList<Health> health2 = jdbc.getLGALTH21(lgaName, condition, status, sex);
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
            "<th style='width: 20%'>LGA Code</th>" +
            "<th style='width: 30%'>LGA Name</th>" +
            "<th style='width: 20%'>State</th>" +
            "<th style='width: 30%'>Total Number of People</th>" +
            
        "</tr>";
                for (Health lt : health2) {
                    html = html + "<tr>"
                    + "<td>" + lt.getLGACode21() + "</td>" 
                    + "<td>" + lt.getName21() + "</td>"
                    + "<td>" + lt.getLGAState21() + "</td>"
                    + "<td>" + lt.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top 5 " + order_text + " Similar LGA to " + lgaName + " Based on " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People that have " + condition.substring(0, 1).toUpperCase() + condition.substring(1) + " Health Condition, Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex</h2></div>";
            html = html + "<div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +
            "</tr>";
                for (Health lth : health1) {
                    html = html + "<tr>"
                    + "<td>" + lth.getLGACode21() + "</td>" 
                    + "<td>" + lth.getName21() + "</td>"
                    + "<td>" + lth.getLGAState21() + "</td>"
                    + "<td>" + lth.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>"; 
        }
        else if (dataset.equals("Dataset3")) {
            ArrayList<Education> edu1 = jdbc.getSimiEducationByOrderAndCategory21(lgaName, minEdu, maxEdu, status, sex, order);
            String min_text;
            String max_text;
            min = minEdu;
            max = maxEdu;
            if(min.equals("7")) {
                min_text = "Did Not Attend School";
            }
            else if(min.equals("8")) {
                min_text = "Year 8 or Below";
            }
            else if(min.equals("9")) {
                min_text = "Year 9 or Equilavent";
            }
            else if(min.equals("10")) {
                min_text = "Year 10 or Equilavent";
            }
            else if(min.equals("11")) {
                min_text = "Year 11 or Equilavent";
            }
            else {
                min_text = "Year 12 or Equilavent";
            }
            if(max.equals("7")) {
                max_text = "Did Not Attend School";
            }
            else if(max.equals("8")) {
                max_text = "Year 8 or Below";
            }
            else if(max.equals("9")) {
                max_text = "Year 9 or Equilavent";
            }
            else if(max.equals("10")) {
                max_text = "Year 10 or Equilavent";
            }
            else if(max.equals("11")) {
                max_text = "Year 11 or Equilavent";
            }
            else {
                max_text = "Year 12 or Equilavent";
            }
            html = html + "<div class='margin'><h2>Total Number of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People Who are Educated Between " + min_text + " - " + max_text + ", Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex in " + lgaName + "</h2></div>";
            ArrayList<Education> edu2 = jdbc.getLGAEducationByOrderAndCategory21(lgaName, minEdu, maxEdu, status, sex);
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +    
            "</tr>";
                for (Education education : edu2) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName21() + "</td>"
                    + "<td>" + education.getLGAState21() + "</td>"
                    + "<td>" + education.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top 5 " + order_text + " Similar LGA to " + lgaName + " Based on " + status.substring(0, 1).toUpperCase() + status.substring(1) + " People Who are Educated Between " + min_text + " - " + max_text + ", Sorted by " + sex.substring(0, 1).toUpperCase() + sex.substring(1) + " Sex</h2></div>";
            html = html + "<div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +     
            "</tr>";
                for (Education education : edu1) {
                    html = html + "<tr>"
                    + "<td>" + education.getLGACode21() + "</td>" 
                    + "<td>" + education.getName21() + "</td>"
                    + "<td>" + education.getLGAState21() + "</td>" 
                    + "<td>" + education.getTotalPeople() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        }
        else {
            ArrayList<Income2> income1 = jdbc.getSimiLatestIncome21(lgaName, minIncome, maxIncome, status, order);
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
            html = html + "<div class='margin'><h2>Total Number of " + status.substring(0, 1).toUpperCase() + status.substring(1) + " Household that have an Income Between AU$" + minIncome + "-" + maxIncome + " in " + lgaName + "</h2></div>";
            ArrayList<Income2> income2 = jdbc.getLGALatestIncome21(lgaName, minIncome, maxIncome, status);
            html = html + "<br><div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +
            "</tr>";
                for (Income2 incomes : income2) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName21() + "</td>"
                    + "<td>" + incomes.getLGAState21() + "</td>"
                    + "<td>" + incomes.getTotalPeople() + "</td></tr>"; 
                }
            html = html + "</table></div>";
            html = html + "<div class='margin'><h2>Top 5 " + order_text + " Similar LGA to " + lgaName + " Based on " + status.substring(0, 1).toUpperCase() + status.substring(1) + " household that have an Income Between AU$" + minIncome + "-" + maxIncome + "</h2></div>";
            html = html + "<div class='margin'><table style='width: 100%'>" + 
            "<tr>" +
                "<th style='width: 20%'>LGA Code</th>" +
                "<th style='width: 30%'>LGA Name</th>" +
                "<th style='width: 20%'>State</th>" +
                "<th style='width: 30%'>Total Number of People</th>" +
            "</tr>";
                for (Income2 incomes : income1) {
                    html = html + "<tr>"
                    + "<td>" + incomes.getLGACode21() + "</td>" 
                    + "<td>" + incomes.getName21() + "</td>"
                    + "<td>" + incomes.getLGAState21() + "</td>" 
                    + "<td>" + incomes.getTotalPeople() + "</td></tr>";
                }
            html = html + "</table></div>"
                        + "<br><div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";    
        }
        return html;
    }
}