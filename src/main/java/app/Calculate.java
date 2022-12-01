package app;

import java.util.ArrayList;

import javax.print.DocFlavor.STRING;
import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import javassist.CodeConverter.ArrayAccessReplacementMethodNames;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Calculate implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page4.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Calculate</title>";

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
                <h1>Calculate Gap Score</h1>
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

            $(document).ready(function () {
                $('#dtBasicExample').DataTable();
                $('.dataTables_length').addClass('bs-select');
              });
            </script>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // Next we will ask this *class* for the LGAs
        // ArrayList<LGA21> lgas = jdbc.getLGA21();

        // 
        html = html + """
            <div class="margin">
                <form action="/page4.html" method="post">
                    <br>
                    <label style="margin-left: 20px" for="checkbox_dataset">Select dataset(s) to be calculated: </label>
                    <fieldset style="margin-top: 5px; background-color: #E6E3D3;">
                        <input id="dataset2" name="Dataset2" type="checkbox" value="Dataset2">
                        <label for="dataset2">Dataset 2: Long Term Health Conditions</label><br>
                        <input id="dataset3" name="Dataset3" type="checkbox" value="Dataset3">
                        <label for="dataset3">Dataset 3: Highest Completed School Year</label><br>
                        <input id="dataset4" name="Dataset4" type="checkbox" value="Dataset4">
                        <label for="dataset4">Dataset 4: Total Household Weekly Income</label><br>
                    </fieldset>
                    <br>
                    <div class="grid-container">
                        <div class="grid-item">
                            <label for="population_range">Range of the LGA population: </label>
                                <input id="min_pop" name="min_pop" type="number" value="min_pop" placeholder="Minimum" min="0"> - 
                                <input id="max_pop" name="max_pop" type="number" value="max_pop" placeholder="Maximum">
                            <br>
                        </div>                       
                        <div class="grid-item">
                            <label for="area_range">Range of the total LGA area: </label>
                                <input id="min_area" name="min_area" type="number" value="min_area" placeholder="Minimum" min="0"> - 
                                <input id="max_area" name="max_area" type="number" value="max_area" placeholder="Maximum"> square kilometers
                        </div>
                    </div>
                    <br>
                    <div class="grid-container">
                        <div class="grid-item">
                            <label for="sort">Sort by Gap Score: </label>
                            <select name="sort" id="sort">
                            // gapscore for dataset 2
                                <option value="NothingSelected">Nothing Selected</option>
                                <option value="gapScore16">2016</option>
                                <option value="gapScore21">2021</option>
                                <option value="GapChange">Difference</option>
                            </select>
                        </div>
                        <div class="grid-item">
                            <label for="order">Order of Sorted Gap Score: </label>
                            <select name="order" id="order">
                                <option value="NothingSelected">Nothing Selected</option>
                                <option value="ASC">Ascending</option>
                                <option value="DESC">Descending</option>
                            </select>
                        </div>
                    </div>
                    <h3>If <span style='color: darkolivegreen'>'Dataset 2'</span> is selected, it can only be sorted by the '2021 Gap Score' because it does not have any 2016 data.</h3>
                    <br>
                    <button type="submit" class="btn btn-primary">Sort & Display</button>
                    <button type="reset" class="btn btn-primary">Reset</button>
                </form>
                <h3 style='color:#f0a04b'>Note: Data may take some time to load and a loading sign will appear on the browser tab to indicate that the selection is in process.</h3>
                <h3 style='color:#f0a04b'>Gap Score is calculated by the dividing the total number of indigenous people with the total number of non-indigenous people based on its category (LGA, sex, education, health, or income range).</h3>
                <hr>
            </div>
        """;

        String dataset2 = context.formParam("Dataset2");
        String dataset3 = context.formParam("Dataset3");
        String dataset4 = context.formParam("Dataset4");
        String min_pop = context.formParam("min_pop");  
        String max_pop = context.formParam("max_pop");     
        String min_area = context.formParam("min_area");  
        String max_area = context.formParam("max_area");     
        String sort = context.formParam("sort");     
        String order = context.formParam("order");     
        if (dataset2 == null && dataset3 == null && dataset4 == null) {
            // FIRST LANDING ON PAGE
            html = html + "<div class='margin'><h2><i>No Results to Show</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
        } 
        else {
            html = html + outputDataset(dataset2, dataset3, dataset4, min_area, max_area, min_pop, max_pop, sort, order);
        }

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputDataset(String dataset2, String dataset3, String dataset4, String min_area, String max_area, String min_pop, String max_pop, String sort, String order){
        // Look up movies from JDBC
        JDBCConnection jdbc = new JDBCConnection();
        String html = "";
        // DATASET 2, 3, AND 4
        if ((dataset2 != null && dataset2.equals("Dataset2")) && (dataset3 != null && dataset3.equals("Dataset3")) && (dataset4 != null && dataset4.equals("Dataset4"))){
            if (sort.equals("gapScore16") || sort.equals("GapChange")){
                html = html + "<div class='margin'><h2><i>Dataset 2 can only be sorted by '2021' Gap Score!</i></h2></div>" 
                + "<div class='footer'>"
                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                + "</div>";
            }
            // IF AREA AND POPULATION IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<HealEduInc> score = jdbc.getHealEduIncScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 10%'>Condition</th>" +
                            "<th style='width: 10%'>Education Level</th>" +
                            "<th style='width: 10%'>Income Range</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                for (HealEduInc result : score) {
                    html = html + "<tr>"
                                + "<td>" + result.getLGACode21() + "</td>" 
                                + "<td>" + result.getCondition() + "</td>"
                                + "<td>" + result.getEducation() + "</td>"
                                + "<td>" + result.getIncome() + "</td>"; 
                    if (result.getCondition().equals("other") && result.getEducation().equals("Did not attend School")){
                        html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                    }
                    else if (result.getEducation().equals("Did not attend School")){
                        html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                    }
                    else if (result.getCondition().equals("other")){
                        html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                    }
                    else{
                        html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>" + result.getCondition() + "</b> and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                    }   
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            // POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<HealEduInc> score = jdbc.getHealEduIncScorePopulationSorted(min_pop, max_pop, sort, order);
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 10%'>Education Level</th>" +
                                "<th style='width: 10%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealEduInc result : score) {
                        html = html + "<tr>"
                                    + "<td>" + result.getLGACode21() + "</td>" 
                                    + "<td>" + result.getCondition() + "</td>"
                                    + "<td>" + result.getEducation() + "</td>"
                                    + "<td>" + result.getIncome() + "</td>"; 
                        if (result.getCondition().equals("other") && result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getCondition().equals("other")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>" + result.getCondition() + "</b> and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){ 
                ArrayList<HealEduInc> score = jdbc.getHealEduIncScoreAreaSorted(min_area, max_area, sort, order);
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 10%'>Education Level</th>" +
                                "<th style='width: 10%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealEduInc result : score) {
                        html = html + "<tr>"
                                    + "<td>" + result.getLGACode21() + "</td>" 
                                    + "<td>" + result.getCondition() + "</td>"
                                    + "<td>" + result.getEducation() + "</td>"
                                    + "<td>" + result.getIncome() + "</td>"; 
                        if (result.getCondition().equals("other") && result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getCondition().equals("other")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>" + result.getCondition() + "</b> and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            else{
                ArrayList<HealEduInc> score = jdbc.getHealEduIncScorePopulationAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed School Year and Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 10%'>Education Level</th>" +
                                "<th style='width: 10%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealEduInc result : score) {
                        html = html + "<tr>"
                                    + "<td>" + result.getLGACode21() + "</td>" 
                                    + "<td>" + result.getCondition() + "</td>"
                                    + "<td>" + result.getEducation() + "</td>"
                                    + "<td>" + result.getIncome() + "</td>"; 
                        if (result.getCondition().equals("other") && result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to <b>not attend school</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else if (result.getCondition().equals("other")){
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>other conditions</b>, and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous people are <b>" + result.getScore21() + " times </b> more likely to complete <b>" + result.getEducation() + "</b>, suffer from <b>" + result.getCondition() + "</b> and make <b>AU$" + result.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        //DATASET 2 AND 4
        else if ((dataset2 != null && dataset2.equals("Dataset2")) && (dataset4 != null && dataset4.equals("Dataset4"))){
            if (sort.equals("gapScore16") || sort.equals("GapChange")){
                html = html + "<div class='margin'><h2><i>Dataset 2 can only be sorted by '2021' Gap Score!</i></h2></div>" 
                + "<div class='footer'>"
                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                + "</div>";
            }
            // IF AREA AND POPULATION IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<HealthandInc> lthcIncome = jdbc.getLTHCIncomeScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 10%'>Condition</th>" +
                            "<th style='width: 15%'>Income Range</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                for (HealthandInc score : lthcIncome) {
                    html = html + "<tr>"
                                + "<td>" + score.getLGACode21() + "</td>" 
                                + "<td>" + score.getSex() + "</td>" 
                                + "<td>" + score.getCondition() + "</td>"
                                + "<td>" + score.getIncome() + "</td>"; 
                    if (score.getCondition().equals("other")){
                        html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>other conditions</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                    }
                    else{
                        html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>" + score.getCondition() + "</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                    }   
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            // POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<HealthandInc> lthcIncome = jdbc.getLTHCIncomeScorePopulationSorted(min_pop, max_pop, sort, order);
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 7%'>Sex</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 15%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealthandInc score : lthcIncome) {
                        html = html + "<tr>"
                                    + "<td>" + score.getLGACode21() + "</td>" 
                                    + "<td>" + score.getSex() + "</td>" 
                                    + "<td>" + score.getCondition() + "</td>"
                                    + "<td>" + score.getIncome() + "</td>"; 
                        if (score.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>other conditions</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>" + score.getCondition() + "</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){     
                ArrayList<HealthandInc> lthcIncome = jdbc.getLTHCIncomeScorePopulationSorted(min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 7%'>Sex</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 15%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealthandInc score : lthcIncome) {
                        html = html + "<tr>"
                                    + "<td>" + score.getLGACode21() + "</td>" 
                                    + "<td>" + score.getSex() + "</td>" 
                                    + "<td>" + score.getCondition() + "</td>"
                                    + "<td>" + score.getIncome() + "</td>"; 
                        if (score.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>other conditions</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>" + score.getCondition() + "</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // BOTH AREA AND POPULATION IS GIVEN
            else{
                ArrayList<HealthandInc> lthcIncome = jdbc.getLTHCIncomeScoreAreaPopulationSorted(min_pop, max_pop, min_area, max_area, sort, order);
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 7%'>LGA Code</th>" +
                                "<th style='width: 7%'>Sex</th>" +
                                "<th style='width: 10%'>Condition</th>" +
                                "<th style='width: 15%'>Income Range</th>" +
                                "<th>Gap Score (2021)</th>" +
                            "</tr>";
                    for (HealthandInc score : lthcIncome) {
                        html = html + "<tr>"
                                    + "<td>" + score.getLGACode21() + "</td>" 
                                    + "<td>" + score.getSex() + "</td>" 
                                    + "<td>" + score.getCondition() + "</td>"
                                    + "<td>" + score.getIncome() + "</td>"; 
                        if (score.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>other conditions</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore21() + " times </b> more likely to suffer from <b>" + score.getCondition() + "</b> and make <b>AU$" + score.getIncome() + "</b></td></tr>";
                        }   
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        //DATASET 3 AND 4
        else if ((dataset3 != null && dataset3.equals("Dataset3")) && (dataset4 != null && dataset4.equals("Dataset4"))){
            // IF AREA AND POPULATION IS NOT GIVEN
            if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<EduandIncome> eduIncome = jdbc.getEduIncomeScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if(sort.equals("gapScore16")){
                    sort_text = "2016 Gap Score";
                }
                else if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else if (sort.equals("GapChange")){
                    sort_text = "2021 and 2016 Gap Difference";
                }
                else{
                    sort_text = "";
                }
                
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
                
                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                                "<th style='width: 5%'>LGA Code</th>" +
                                "<th style='width: 5%'>Sex</th>" +
                                "<th style='width: 7%'>Education Level</th>" +
                                "<th style='width: 7%'>Income Range</th>" +
                                "<th style='width: 30%'>Gap Score (2016)</th>" +
                                "<th style='width: 30%'>Gap Score (2021)</th>" +
                                "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                            "</tr>";
                for (EduandIncome eduInc : eduIncome) {
                    html = html + "<tr>"
                                + "<td>" + eduInc.getLGACode21() + "</td>" 
                                + "<td>" + eduInc.getSex() + "</td>" 
                                + "<td>" + eduInc.getEducation() + "</td>"
                                + "<td>" + eduInc.getIncome() + "</td>";
                    if (eduInc.getEducation().equals("Did not attend School")){
                        html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                    }
                    else{
                        html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                    }
                    if ((eduInc.getScoreDiff() >= 0)){
                        html = html + "<td><b> increased by <span style='color: red'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                    }
                    else{
                        html = html + "<td><b> decreased by <span style='color: green'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                    }
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            // IF POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<EduandIncome> eduIncome = jdbc.getEduIncomeScorePopulationSorted(min_pop, max_pop, sort, order);
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }
                    
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
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 5%'>Sex</th>" +
                                    "<th style='width: 7%'>Education Level</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (EduandIncome eduInc : eduIncome) {
                        html = html + "<tr>"
                                    + "<td>" + eduInc.getLGACode21() + "</td>" 
                                    + "<td>" + eduInc.getSex() + "</td>" 
                                    + "<td>" + eduInc.getEducation() + "</td>"
                                    + "<td>" + eduInc.getIncome() + "</td>";
                        if (eduInc.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        if ((eduInc.getScoreDiff() >= 0)){
                            html = html + "<td><b> increased by <span style='color: red'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // IF AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<EduandIncome> eduIncome = jdbc.getEduIncomeScoreAreaSorted(min_area, max_area, sort, order);
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }
                    
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
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 5%'>Sex</th>" +
                                    "<th style='width: 7%'>Education Level</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (EduandIncome eduInc : eduIncome) {
                        html = html + "<tr>"
                                    + "<td>" + eduInc.getLGACode21() + "</td>" 
                                    + "<td>" + eduInc.getSex() + "</td>" 
                                    + "<td>" + eduInc.getEducation() + "</td>"
                                    + "<td>" + eduInc.getIncome() + "</td>";
                        if (eduInc.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        if ((eduInc.getScoreDiff() >= 0)){
                            html = html + "<td><b> increased by <span style='color: red'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + eduInc.getScoreDiff() + "<span> times</b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            else{
                ArrayList<EduandIncome> eduIncome = jdbc.getEduIncomeScorePopulationAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for Both the Population and Area!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }
                    
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
                    html = html + "<div class='margin'><h2>Education Level Combined with Household Weekly Income of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 5%'>Sex</th>" +
                                    "<th style='width: 7%'>Education Level</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (EduandIncome eduInc : eduIncome) {
                        html = html + "<tr>"
                                    + "<td>" + eduInc.getLGACode21() + "</td>" 
                                    + "<td>" + eduInc.getSex() + "</td>" 
                                    + "<td>" + eduInc.getEducation() + "</td>"
                                    + "<td>" + eduInc.getIncome() + "</td>";
                        if (eduInc.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>not attend school</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore16() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + eduInc.getSex() + "s</b> are <b>" + eduInc.getScore21() + " times </b> more likely to <b>" + eduInc.education + "</b> and make <b>AU$" + eduInc.getIncome() + "</b></td>";
                        }
                        if ((eduInc.getScoreDiff() >= 0)){
                            html = html + "<td><b> increased by <span style='color: red'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + eduInc.getScoreDiff() + "</span> times</b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        // DATASET 2 AND 3
        else if ((dataset2 != null && dataset2.equals("Dataset2")) && (dataset3 != null && dataset3.equals("Dataset3"))){
            if (sort.equals("gapScore16") || sort.equals("GapChange")){
                html = html + "<div class='margin'><h2><i>Dataset 2 can only be sorted by '2021' Gap Score!</i></h2></div>" 
                + "<div class='footer'>"
                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                + "</div>";
            }
            // IF AREA AND POPULATION IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<EduandHealth> eduLTHC = jdbc.getEduLTHCScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 7%'>LGA Code</th>" +
                        "<th style='width: 7%'>Sex</th>" +
                        "<th style='width: 10%'>Condition</th>" +
                        "<th style='width: 15%'>Education</th>" +
                        "<th>Gap Score (2021)</th>" +
                    "</tr>";
                for (EduandHealth score : eduLTHC) {
                    html = html + "<tr>"
                    + "<td>" + score.getLGACode21() + "</td>" 
                    + "<td>" + score.getSex() + "</td>" 
                    + "<td>" + score.getCondition() + "</td>"
                    + "<td>" + score.getEducation() + "</td>"; 
                    if(score.getScore() == 2.147483647E9){
                        html = html + "<td>There are <b>" + (score.getTotalNonIndi() - score.getTotalIndi()) + "</b> more non-indigenous people than indigenous</td></tr>";
                    }
                    else{
                        if (score.getEducation().equals("Did not attend School") && score.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>other conditions</b></td></tr>";
                        }
                        else if (score.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                        }
                        else if (score.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>other conditions</b></td></tr>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                        }   
                    }
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            //IF POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<EduandHealth> eduLTHC = jdbc.getEduLTHCScorePopulationSorted(min_pop, max_pop, sort, order);
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 10%'>Condition</th>" +
                            "<th style='width: 15%'>Education</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (EduandHealth score : eduLTHC) {
                        html = html + "<tr>"
                        + "<td>" + score.getLGACode21() + "</td>" 
                        + "<td>" + score.getSex() + "</td>" 
                        + "<td>" + score.getCondition() + "</td>"
                        + "<td>" + score.getEducation() + "</td>"; 
                        if(score.getScore() == 2.147483647E9){
                            html = html + "<td>There are <b>" + (score.getTotalNonIndi() - score.getTotalIndi()) + "</b> more non-indigenous people than indigenous</td></tr>";
                        }
                        else{
                            if (score.getEducation().equals("Did not attend School") && score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else if (score.getEducation().equals("Did not attend School")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }
                            else if (score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }   
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            //IF AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){     
                ArrayList<EduandHealth> eduLTHC = jdbc.getEduLTHCScoreAreaSorted(min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 10%'>Condition</th>" +
                            "<th style='width: 15%'>Education</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (EduandHealth score : eduLTHC) {
                        html = html + "<tr>"
                        + "<td>" + score.getLGACode21() + "</td>" 
                        + "<td>" + score.getSex() + "</td>" 
                        + "<td>" + score.getCondition() + "</td>"
                        + "<td>" + score.getEducation() + "</td>"; 
                        if(score.getScore() == 2.147483647E9){
                            html = html + "<td>There are <b>" + (score.getTotalNonIndi() - score.getTotalIndi()) + "</b> more non-indigenous people than indigenous</td></tr>";
                        }
                        else{
                            if (score.getEducation().equals("Did not attend School") && score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else if (score.getEducation().equals("Did not attend School")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }
                            else if (score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }   
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            else{
                ArrayList<EduandHealth> eduLTHC = jdbc.getEduLTHCScorePopulationAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM AREA OR POPULATION IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else{
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions Combined with Highest Completed of School Year of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 10%'>Condition</th>" +
                            "<th style='width: 15%'>Education</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (EduandHealth score : eduLTHC) {
                        html = html + "<tr>"
                        + "<td>" + score.getLGACode21() + "</td>" 
                        + "<td>" + score.getSex() + "</td>" 
                        + "<td>" + score.getCondition() + "</td>"
                        + "<td>" + score.getEducation() + "</td>"; 
                        if(score.getScore() == 2.147483647E9){
                            html = html + "<td>There are <b>" + (score.getTotalNonIndi() - score.getTotalIndi()) + "</b> more non-indigenous people than indigenous</td></tr>";
                        }
                        else{
                            if (score.getEducation().equals("Did not attend School") && score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else if (score.getEducation().equals("Did not attend School")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to <b>not attend school</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }
                            else if (score.getCondition().equals("other")){
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>Indigenous <b>" + score.getSex() + "s</b> are <b>" + score.getScore() + " times </b> more likely to complete <b>" + score.getEducation() + "</b> and suffer from <b>" + score.getCondition() + "</b></td></tr>";
                            }   
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        // DATASET 2
        else if (dataset2 != null && dataset2.equals("Dataset2")){
            if (sort.equals("gapScore16") || sort.equals("GapChange")){
                html = html + "<div class='margin'><h2><i>Dataset 2 can only be sorted by '2021' Gap Score!</i></h2></div>" 
                + "<div class='footer'>"
                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                + "</div>";
            }
            // IF AREA AND POPULATION IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<Health> lthc = jdbc.getLTHCScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 15%'>Condition</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                for (Health health : lthc) {
                    html = html + "<tr>"
                    + "<td>" + health.getLGACode21() + "</td>" 
                    + "<td>" + health.getSex() + "</td>" 
                    + "<td>" + health.getCondition() + "</td>"; 
                    if (health.getCondition().equals("other") && health.getScore() == 2.147483647E9){
                        if ((health.getCount21() - health.getCount21_1()) < 0){
                            html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less indigenous <b>" + health.getSex() + "s</b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                        }
                        else{
                            html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more indigenous <b>" + health.getSex() + "<s/b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                        }
                    }
                    else if (health.getCondition().equals("other")){
                        html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>other conditions</b></td></tr>";
                    }
                    else if(health.getScore() == 2.147483647E9){
                        if ((health.getCount21() - health.getCount21_1()) < 0){
                            html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                        }
                        else{
                            html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                        }
                    }
                    else{
                        html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>" + health.getCondition() + "</b></td></tr>";
                    }
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            //IF POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<Health> lthc = jdbc.getLTHCScoreByPopulationSorted(min_pop, max_pop, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                    + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
                }
                else{
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text +" in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 15%'>Condition</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (Health health : lthc) {
                        html = html + "<tr>"
                        + "<td>" + health.getLGACode21() + "</td>" 
                        + "<td>" + health.getSex() + "</td>" 
                        + "<td>" + health.getCondition() + "</td>"; 
                        if (health.getCondition().equals("other") && health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less indigenous <b>" + health.getSex() + "s</b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more indigenous <b>" + health.getSex() + "<s/b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                        }
                        else if (health.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>other conditions</b></td></tr>";
                        }
                        else if(health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>" + health.getCondition() + "</b></td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            //IF AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){     
                ArrayList<Health> lthc = jdbc.getLTHCScoreByAreaSorted(min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                            "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 15%'>Condition</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (Health health : lthc) {
                        html = html + "<tr>"
                        + "<td>" + health.getLGACode21() + "</td>" 
                        + "<td>" + health.getSex() + "</td>" 
                        + "<td>" + health.getCondition() + "</td>"; 
                        if (health.getCondition().equals("other") && health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less indigenous <b>" + health.getSex() + "s</b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more indigenous <b>" + health.getSex() + "<s/b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                        }
                        else if (health.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>other conditions</b></td></tr>";
                        }
                        else if(health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>" + health.getCondition() + "</b></td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            else{
                ArrayList<Health> lthc = jdbc.getLTHCScoreByPopulationAndAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM AREA OR POPULATION IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else{
                    String sort_text;
                    if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Long Term Health Conditions of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population</h3></div>";
                    }
                    else if (sort.equals("gapScore21") && order.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h2>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h2></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                        "<tr>" +
                            "<th style='width: 7%'>LGA Code</th>" +
                            "<th style='width: 7%'>Sex</th>" +
                            "<th style='width: 15%'>Condition</th>" +
                            "<th>Gap Score (2021)</th>" +
                        "</tr>";
                    for (Health health : lthc) {
                        html = html + "<tr>"
                        + "<td>" + health.getLGACode21() + "</td>" 
                        + "<td>" + health.getSex() + "</td>" 
                        + "<td>" + health.getCondition() + "</td>"; 
                        if (health.getCondition().equals("other") && health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less indigenous <b>" + health.getSex() + "s</b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more indigenous <b>" + health.getSex() + "<s/b> than non-indigenous who suffer from <b>other conditions</b></td></tr>";
                            }
                        }
                        else if (health.getCondition().equals("other")){
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>other conditions</b></td></tr>";
                        }
                        else if(health.getScore() == 2.147483647E9){
                            if ((health.getCount21() - health.getCount21_1()) < 0){
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> less non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                            else{
                                html = html + "<td>There are <b>" + (health.getCount21() - health.getCount21_1()) + "</b> more non-indigenous <b>" + health.getSex() + "s</b> than indigenous who suffer from <b>" + health.getCondition() + "</b></td></tr>";
                            }
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + health.getSex() + "s</b> are <b>" + health.getScore() + " times </b> more likely to suffer from <b>" + health.getCondition() + "</b></td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        //DATASET 3
        else if (dataset3 != null && dataset3.equals("Dataset3")){
            if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<Education> edu = jdbc.getEduScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if(sort.equals("gapScore16")){
                    sort_text = "2016 Gap Score";
                }
                else if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else if (sort.equals("GapChange")){
                    sort_text = "2021 and 2016 Gap Difference";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                }
                else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 5%'>LGA Code</th>" +
                        "<th style='width: 5%'>Sex</th>" +
                        "<th style='width: 7%'>Education Level</th>" +
                        "<th style='width: 30%'>Gap Score (2016)</th>" +
                        "<th style='width: 30%'>Gap Score (2021)</th>" +
                        "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                    "</tr>";
                    for (Education education : edu) {
                        html = html + "<tr>"
                            + "<td>" + education.getLGACode21() + "</td>" 
                            + "<td>" + education.getSex() + "</td>" 
                            + "<td>" + education.getEducation() + "</td>";
                        if (education.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to <b>not attend school</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to <b>not attend school</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                        }
                        // IF GAP SCORE CHANGE IS POSTIVE
                        if ((education.getScore21() - education.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            //IF POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<Education> edu = jdbc.getEduScoreByPopulationSorted(min_pop, max_pop, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 5% '>LGA Code</th>" +
                        "<th style='width: 5% '>Sex</th>" +
                        "<th style='width: 7%'>Education Level</th>" +
                        "<th style='width: 30%'>Gap Score (2016)</th>" +
                        "<th style='width: 30%'>Gap Score (2021)</th>" +
                        "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                    "</tr>";
                    for (Education education : edu) {
                        html = html + "<tr>"
                            + "<td>" + education.getLGACode21() + "</td>" 
                            + "<td>" + education.getSex() + "</td>" 
                            + "<td>" + education.getEducation() + "</td>";
                        if (education.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to <b>not attend school</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to <b>not attend school</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                        }
                        // IF GAP SCORE CHANGE IS POSTIVE
                        if ((education.getScore21() - education.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            //IF AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<Education> edu = jdbc.getEduScoreByAreaSorted(min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 5% '>LGA Code</th>" +
                        "<th style='width: 5% '>Sex</th>" +
                        "<th style='width: 7%'>Education Level</th>" +
                        "<th style='width: 30%'>Gap Score (2016)</th>" +
                        "<th style='width: 30%'>Gap Score (2021)</th>" +
                        "<th style='width: 30%'>Gap Score Difference< <br>(2021 - 2016)/th>" +
                    "</tr>";
                    for (Education education : edu) {
                        html = html + "<tr>"
                            + "<td>" + education.getLGACode21() + "</td>" 
                            + "<td>" + education.getSex() + "</td>" 
                            + "<td>" + education.getEducation() + "</td>";
                        if (education.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to <b>not attend school</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to <b>not attend school</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                        }
                        // IF GAP SCORE CHANGE IS POSTIVE
                        if ((education.getScore21() - education.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            else{
                ArrayList<Education> edu = jdbc.getEduScoreByPopulationAndAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM AREA OR POPULATION IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else{
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Education Level of Indigenous Compared to Non-Indigenous</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population</h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 5%'>LGA Code</th>" +
                        "<th style='width: 5%'>Sex</th>" +
                        "<th style='width: 7%'>Education Level</th>" +
                        "<th style='width: 30%'>Gap Score (2016)</th>" +
                        "<th style='width: 30%'>Gap Score (2021)</th>" +                            
                        "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                    "</tr>";
                    for (Education education : edu) {
                        html = html + "<tr>"
                            + "<td>" + education.getLGACode21() + "</td>" 
                            + "<td>" + education.getSex() + "</td>" 
                            + "<td>" + education.getEducation() + "</td>";
                        if (education.getEducation().equals("Did not attend School")){
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to <b>not attend school</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to <b>not attend school</b></td>";
                        }
                        else{
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore16() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                            html = html + "<td>Indigenous <b>" + education.getSex() + "s</b> are <b>" + education.getScore21() + " times </b> more likely to complete <b>" + education.getEducation() + "</b></td>";
                        }
                        // IF GAP SCORE CHANGE IS POSTIVE
                        if ((education.getScore21() - education.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + education.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
        }
        //DATASET 4
        else if (dataset4 != null && dataset4.equals("Dataset4")){
            // IF AREA AND POPULATION IS NOT GIVEN
            if ((min_area.equals("") &&  max_area.equals("")) && (min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<Income> income = jdbc.getIncomeScoreSorted(sort, order);
                // INITIALISE SORT TEXT FOR HTML
                String sort_text;
                if(sort.equals("gapScore16")){
                    sort_text = "2016 Gap Score";
                }
                else if (sort.equals("gapScore21")){
                    sort_text = "2021 Gap Score";
                }
                else if (sort.equals("GapChange")){
                    sort_text = "2021 and 2016 Gap Difference";
                }
                else{
                    sort_text = "";
                }

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

                // NOT SORTED
                if (sort.equals("NothingSelected")){
                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                }
                else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in Normal Order</h3></div>";
                }
                else{
                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                    html = html + "<div class='margin'><h3>Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                }
                html = html + "<div class='margin'><p>Only the first <b>200</b> rows are displayed. To view more rows, please provide a range of population or area.</p></div>";
                html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                    "<tr>" +
                        "<th style='width: 5%'>LGA Code</th>" +
                        "<th style='width: 7%'>Income Range</th>" +
                        "<th style='width: 30%'>Gap Score (2016)</th>" +
                        "<th style='width: 30%'>Gap Score (2021)</th>" +
                        "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                    "</tr>";
                for (Income incomes : income) {
                    html = html + "<tr>"
                                + "<td>" + incomes.getLGACode21() + "</td>" 
                                + "<td>" + incomes.getIncomeRange() + "</td>";
                    html = html + "<td>Indigenous people are <b>" + incomes.getScore16() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                    html = html + "<td>Indigenous people are <b>" + incomes.getScore21() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                    if ((incomes.getScore21() - incomes.getScore16()) >= 0){
                        html = html + "<td><b> increased by <span style='color: red'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                    }
                    else{
                        html = html + "<td><b> decreased by <span style='color: green'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                    }
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
            //IF POPULATION IS GIVEN BUT AREA IS NOT GIVEN
            else if ((min_area.equals("") &&  max_area.equals(""))){
                ArrayList<Income> income = jdbc.getIncomeScoreByPopulationSorted(min_pop, max_pop, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h3>Filtered By LGA Population </h3></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Popualation and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Population and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (Income incomes : income) {
                        html = html + "<tr>"
                                    + "<td>" + incomes.getLGACode21() + "</td>" 
                                    + "<td>" + incomes.getIncomeRange() + "</td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore16() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore21() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        if ((incomes.getScore21() - incomes.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // IF AREA IS GIVEN BUT POPULATION IS NOT GIVEN
            else if ((min_pop.equals("") &&  max_pop.equals(""))){
                ArrayList<Income> income = jdbc.getIncomeScoreByAreaSorted(min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM NUMBER IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h2>Filtered By LGA Area </h2></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (Income incomes : income) {
                        html = html + "<tr>"
                                    + "<td>" + incomes.getLGACode21() + "</td>" 
                                    + "<td>" + incomes.getIncomeRange() + "</td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore16() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore21() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        if ((incomes.getScore21() - incomes.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                    html = html + "</table></div>"
                                + "<br><div class='footer'>"
                                + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                                + "</div>";
                }
            }
            // BOTH POPULATION AND AREA
            else{
                ArrayList<Income> income = jdbc.getIncomeScoreByPopulationAndAreaSorted(min_pop, max_pop, min_area, max_area, sort, order);
                // EITHER MINIMUM OR MAXIMUM AREA OR POPULATION IS NOT INPUTED
                if (min_area.equals("") || max_area.equals("") || min_pop.equals("") || max_pop.equals("")){
                    html = html + "<div class='margin'><h2><i>Please Input Both the Minimum and Maximum Number for the Area and Population!</i></h2></div>" 
                        + "<div class='footer'>"
                        + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                        + "</div>";
                }
                else {
                    // INITIALISE SORT TEXT FOR HTML
                    String sort_text;
                    if(sort.equals("gapScore16")){
                        sort_text = "2016 Gap Score";
                    }
                    else if (sort.equals("gapScore21")){
                        sort_text = "2021 Gap Score";
                    }
                    else if (sort.equals("GapChange")){
                        sort_text = "2021 and 2016 Gap Difference";
                    }
                    else{
                        sort_text = "";
                    }

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

                    html = html + "<div class='margin'><h2>Weekly Income of Indigenous Compared to Non-Indigenous Household</h2></div>";
                    // NOT SORTED
                    if (sort.equals("NothingSelected")){
                        html = html + "<div class='margin'><h2>Filtered By LGA Area and Population</h2></div>";
                    }
                    else if ((sort.equals("gapScore16") || sort.equals("gapScore21") || sort.equals("GapChange")) && (order.equals("NothingSelected"))){
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in Normal Order</h3></div>";
                    }
                    else{
                        html = html + "<div class='margin'><h3>Filtered by LGA Area and Population, and Sorted by " + sort_text + " in " + order_text + " Order</h3></div>";
                    }
                    html = html + "<div class='margin'><h4>LGA Population: " + min_pop + " - " + max_pop + "</h4></div>";
                    html = html + "<div class='margin'><h4>LGA Area: " + min_area + " - " + max_area + " square kilometers</h4></div>";
                    html = html + "<br><div class='margin'><table style='width: 100%'>" + 
                                "<tr>" +
                                    "<th style='width: 5%'>LGA Code</th>" +
                                    "<th style='width: 7%'>Income Range</th>" +
                                    "<th style='width: 30%'>Gap Score (2016)</th>" +
                                    "<th style='width: 30%'>Gap Score (2021)</th>" +
                                    "<th style='width: 30%'>Gap Score Difference <br>(2021 - 2016)</th>" +
                                "</tr>";
                    for (Income incomes : income) {
                        html = html + "<tr>"
                                    + "<td>" + incomes.getLGACode21() + "</td>" 
                                    + "<td>" + incomes.getIncomeRange() + "</td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore16() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        html = html + "<td>Indigenous people are <b>" + incomes.getScore21() + " times </b> more likely to make <b>AU$" + incomes.getIncomeRange() + "</b></td>";
                        if ((incomes.getScore21() - incomes.getScore16()) >= 0){
                            html = html + "<td><b> increased by <span style='color: red'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                        else{
                            html = html + "<td><b> decreased by <span style='color: green'>" + incomes.getScoreDiff() + "</span> times </b> in 2021</td></tr>";
                        }
                    }
                }
                html = html + "</table></div>"
                            + "<br><div class='footer'>"
                            + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                            + "</div>";
            }
        }
        return html;
    }
}