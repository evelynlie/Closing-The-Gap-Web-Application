package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Home</title>";

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
        <body>
        <div class = "header-image">
            <div class = "container">
                <h1><span>Closing The Gap</span></h1>
                <span class = "cen">Raising awareness about the "Closing The Gap" campaign. Help us make Aboriginal and Torres Strait Islander people achieve their full learning potential as well as live a properous and healthy life. See and compare data from two previous census (2016 & 2021).</span>
                <a class = "button" href = "page4.html">Learn More</a>
            </div>
        </div>
        </body>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";
        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        html = html + """
            <br>
            <hr>
        <div class='targets'>
            <h1>Our Focus</h1>
            <div class = 'listOutcome'>
            <h2 style='text-align: left'>For this website, we will focus on the following socioeconomic outcomes: </h2>
            <li>Outcome 1: Aboriginal and Torres Strait Islander people enjoy long and healthy lives.</li>
            <li>Outcome 5: Aboriginal and Torres Strait Islander students achieve their full learning potential. </li>
            <li>Outcome 8: Strong economic participation and development of Aboriginal and Torres Strait Islander people and communities. </li>
        </div>
        <br>
        </div>
        <hr>
        """;
        
        // About section
        html = html + """
            <div class='targets'>
            <style> h1 {text-align: center; font-size: 44;} h2 {text-align: center; font-size: 18;}</style>
            <br>
                <h1>Targets</h1>
                <h2>The National Agreement on Closing the Gap (the National Agreement) includes 17 national socioeconomic targets in areas that affect Aboriginal and Torres Strait Islander people's life outcomes. The Productivity Commission will monitor progress towards the targets, which will help all parties to the National Agreement understand how their efforts will contribute to progress over the next ten years. Each target and its outcomes are listed below.</h2>
                <br>
            </div>
        """;

        //Target 1
        //Image taken from https://www.bulletpoint.com.au/iahp/
        html = html + """
            <div class='margin'>
                <img src="target1.jpeg" alt="Target 1" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;

        ArrayList<Target> target1 = jdbc.getTarget(1);        
        for (Target target : target1) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br><br>
        <hr>
        """;
        //Target 2
        //Image taken from https://www.fprs.com.au/celebrating-national-aboriginal-and-torres-strait-islander-childrens-day/
        html = html + """
            <div class='margin'>
                <img src="target2.jpeg" alt="Target 2" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target2 = jdbc.getTarget(2);        
        for (Target target : target2) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 3
        //Image taken from https://www.youtube.com/watch?v=qEEOMToGFlk
        html = html + """
            <div class='margin'>
                <img src="target3.jpeg" alt="Target 3" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target3 = jdbc.getTarget(3);        
        for (Target target : target3) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 4
        //Image taken from https://pursuit.unimelb.edu.au/articles/boosting-early-learning-for-indigenous-children
        html = html + """
            <div class='margin'>
                <img src="target4.webp" alt="Target 4" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target4 = jdbc.getTarget(4);        
        for (Target target : target4) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br>
        <hr>
        """;
        //Target 5
        //Image taken from https://medium.com/writ-150-at-usc-fall-2020/what-is-the-best-way-to-encourage-young-aboriginal-australians-to-attend-school-a67c973530a8
        html = html + """
            <div class='margin'>
                <img src="target5.jpeg" alt="Target 5" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target5 = jdbc.getTarget(5);        
        for (Target target : target5) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 6
        //Image taken from https://www.facebook.com/cascurtin/
        html = html + """
            <div class='margin'>
                <img src="target6.jpeg" alt="Target 6" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target6 = jdbc.getTarget(6);        
        for (Target target : target6) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 7
        //Image taken from https://www.unitedwayeo.ca/news-and-stories/imagining-a-brighter-post-pandemic-future/
        html = html + """
            <div class='margin'>
                <img src="target7.jpeg" alt="Target 7" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target7 = jdbc.getTarget(7);        
        for (Target target : target7) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 8
        //Image taken from https://www.ladbible.com/news/latest-people-want-the-aboriginal-flag-to-be-on-the-sydney-harbour-bridge-20210712
        html = html + """
            <div class='margin'>
                <img src="target8.jpeg" alt="Target 8" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target8 = jdbc.getTarget(8);        
        for (Target target : target8) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 9
        //Image taken from https://www.planning.nsw.gov.au/Policy-and-Legislation/Housing/Housing-Package/Aboriginal-housing
        html = html + """
            <div class='margin'>
                <img src="target9.jpeg" alt="Target 9" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target9 = jdbc.getTarget(9);        
        for (Target target : target9) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 10
        //Image taken from https://www.sydneycriminallawyers.com.au/blog/inquest-into-death-of-david-dungay-resumes/
        html = html + """
            <div class='margin'>
                <img src="target10.jpeg" alt="Target 10" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target10 = jdbc.getTarget(10);        
        for (Target target : target10) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 11
        //Image taken from https://independentaustralia.net/australia/australia-display/suicide-of-15-year-old-aboriginal-boy-in-don-dale-a-result-of-nt-punitive-culture,9314
        html = html + """
            <div class='margin'>
                <img src="target11.jpeg" alt="Target 11" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target11 = jdbc.getTarget(11);        
        for (Target target : target11) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 12
        //Image taken from https://www.indigenous.gov.au/news-and-media/stories/national-aboriginal-and-torres-strait-islander-childrens-day-%E2%80%93-celebrating
        html = html + """
            <div class='margin'>
                <img src="target12.jpeg" alt="Target 12" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target12 = jdbc.getTarget(12);        
        for (Target target : target12) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 13
        //Image taken from https://news.flinders.edu.au/blog/2019/11/13/burns-units-need-to-cater-for-indigenous-kids/aboriginal-family-lancet-comment-piece/
        html = html + """
            <div class='margin'>
                <img src="target13.jpeg" alt="Target 13" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target13 = jdbc.getTarget(13);        
        for (Target target : target13) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 14
        //Image taken from https://www.screenhub.com.au/news/features/ten-years-in-nitv-captures-hearts-with-largest-doc-series-yet-252906-1423522/
        html = html + """
            <div class='margin'>
                <img src="target14.webp" alt="Target 14" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target14 = jdbc.getTarget(14);        
        for (Target target : target14) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 15
        //Image taken from https://www.csmonitor.com/Science/2017/0309/Hair-apparent-Study-links-modern-Australian-Aboriginals-with-the-continent-s-earliest-settlers
        html = html + """
            <div class='margin'>
                <img src="target15.jpeg" alt="Target 15" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target15 = jdbc.getTarget(15);        
        for (Target target : target15) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br>
        <hr>
        """;
        //Target 16
        //Image taken from https://www.bbc.co.uk/bbcthree/article/1e98f892-d352-4aef-9c4e-3b024579d867
        html = html + """
            <div class='margin'>
                <img src="target16.jpeg" alt="Target 16" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target16 = jdbc.getTarget(16);        
        for (Target target : target16) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;
        //Target 17
        //Image taken from https://www.theguardian.com/commentisfree/2019/may/30/we-cant-afford-to-leave-indigenous-students-behind-as-we-prepare-our-future-workforce
        html = html + """
            <div class='margin'>
                <img src="target17.webp" alt="Target 17" height="150" width= "230" style="float: left; margin-right: 15px">
            </div>
        """;
        ArrayList<Target> target17 = jdbc.getTarget(17);        
        for (Target target : target17) {
            html = html + "<div class ='target_text'><h3>" + target.getTitle() + "</h3></div>";
            html = html + "<div class ='target_text'>" + target.getDescription() + "</div>";
        }
        html = html + """
        <br><br><br><br>
        <hr>
        """;

        //Big Learn More Stuff
        html = html + """
            <br>
        <div class = 'card'>
            <div class="inner">
            <h2 class="title">The Latest Census Data</h2>
            <h4 class="subtitle">See the latest information available from the 2021 census by the Local Government Area (LGA).</h4>
            <a class = "button" href = "page3.html">Learn More</a>
            </div>
            <!--Image taken from https://lighthouse.mq.edu.au/article/august-2021/Count-me-in-What-to-expect-from-the-2021-Census<!-->
            <div class='margin'>
                <img src="australia.jpeg" alt="Australia" height="260" width= "500" style="margin-left: float; margin-right: 10px">
            </div>
        </div>
        <div class = 'card card2'>
            <div class="inner">
            <h2 class="title">Calculate The Gap</h2>
            <h4 class="subtitle">Know how big the gap is between Indigenous and non-Indigenous people across all LGAs.</h4>
            <a class = "button" href = "page4.html">Learn More</a>
            </div>
            <!--Image taken from https://www.callcentrehelper.com/how-to-calculate-schedule-efficiency-86216.html<!-->
            <div class='margin'>
                <img src="calculate.jpeg" alt="Calculate" height="260" width= "500" style="float: left; margin-right: 10px">
            </div>
        </div>
        <div class = 'card card3'>
            <div class="inner">
            <h2 class="title">Compare The Gap</h2>
            <h4 class="subtitle">See the latest information available from the 2016 & 2021 census by the Local Government Area (LGA).</h4>
            <a class = "button" href = "page5.html">Similarities</a>
            <br>
            <a class = "button" href = "page6.html">Differences </a>
            </div>
            <!--Image taken from https://developer.thingworx.com/resources/guides/perform-statistical-calculations-descriptive-analytics<!-->
            <div class='margin'>
                <img src="compare.png" alt="Compare" height="260" width= "500" style="float: left; margin-right: 10px">
            </div>
        </div>
        """;
        html = html + "<div class='footer'>"
                    + "<p style='margin-left: 20px'>Studio Project Team 10</p>"
                    + "</div>";
        
        // Close Content div
        html = html + "</div>";

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";


        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}