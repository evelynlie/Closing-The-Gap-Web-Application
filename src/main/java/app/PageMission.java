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
public class PageMission implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/mission.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Our Mission</title>";

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
                <h1>Our Mission</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        // About section
        html = html + """
            <div class='margin'>
            <br>
                <div class='about'>
                    <h1>About</h1>
                </div>
                <div class='about_content'>
                    <p style="font-size: 18"> 
                        Our website&#8217s goal is to raise awareness about a campaign called &quotClosing The Gap.&quot It will also display and
                        compare the census results from 2016 and 2021 to determine whether the Australian government has accomplished its
                        intended objectives. Everyone, from First Nations and non-First Nations Australians, to The Aboriginal Provisional and
                        The Australian Government officials are the website&#8217s target users and they are expected to read the website&#8217s content
                        to learn more about the campaign. The content of the website itself includes the overview of the campaign, the
                        development of the socioeconomic targets over the previous 5 years as recorded in the censuses of 2016 and 2021, as
                        well as the challenges in achieving the targets and potential solutions.
                        <br>
                    </p>
                </div>
            </div>
        """;

        // Use of Website
        html = html + """
            <br>
            <hr>
            <br>
            <div class='margin'>
            <h2>How Can This Website be Used?</h2>
                <ol style="font-size: 18">
                    <li>Gaining more information about the &quotClosing The Gap&quot campaign.</li>
                    <li>Comparing campaign progress across 2016 and 2021.</li>
                    <li>Researching about the campaign.</li>
                </ol>
            </div>
            <br>
            <hr>
            <br>
        """;       

        //Persona 1
        html = html + """
            <div class='margin'>
                <h2>Persona 1</h2>
                <img src="persona1.png" alt="Persona 1" height="150" style="float: left; margin-right: 15px">
            </div>
            """;         
        ArrayList<Persona> persona1 = jdbc.getPersona("Emilia");                 
        for (Persona persona : persona1) {
            html = html + "<div class ='persona_text'> Name: " + persona.getName() + "</div>";
            html = html + "<div class ='persona_text'> Age: " + persona.getAge() + " years old</div>";
            html = html + "<div class ='persona_text'> Ethnicity: " + persona.getEthnicity() + "</div>";
            html = html + "<div class ='margin persona_text'> Background: <br>" + persona.getBackground() + "</div><br>";
            html = html + "<div class='margin grid-container'> <div class='grid-item'> <h3>Needs: </h3> <ul style='font-size:18'> <li>" + persona.getNeeds1() + "</li> <li>" + persona.getNeeds2() + "</ul> </div> <div class='grid-item'> <h3>Goals: </h3> <ul style='font-size:18'> <li>" + persona.getGoals1() + "</li> <li>" + persona.getGoals2() + "</ul></div> <div class='grid-item'> <h3>Skills and Experience: </h3> <ul style='font-size:18'> <li>" + persona.getSkills1() + "</li> <li>" + persona.getSkills2() + "</ul></div></div>";
        }

        //Persona 2
        html = html + """
            <div class='margin'>
                <h2>Persona 2</h2>
                <img src="persona2.png" alt="Persona 2" height="170" style="float: left; margin-right: 15px">
            </div>
            """;         
        ArrayList<Persona> persona2 = jdbc.getPersona("Allan");        
        for (Persona persona : persona2) {
            html = html + "<div class ='persona_text'> Name: " + persona.getName() + "</div>";
            html = html + "<div class ='persona_text'> Age: " + persona.getAge() + " years old</div>";
            html = html + "<div class ='persona_text'> Ethnicity: " + persona.getEthnicity() + "</div>";
            html = html + "<div class ='margin persona_text'> Background: <br>" + persona.getBackground() + "</div>";
            html = html + "<div class='margin grid-container'> <div class='grid-item'> <h3>Needs: </h3> <ul style='font-size:18'> <li>" + persona.getNeeds1() + "</li> <li>" + persona.getNeeds2() + "</ul> </div> <div class='grid-item'> <h3>Goals: </h3> <ul style='font-size:18'> <li>" + persona.getGoals1() + "</li> <li>" + persona.getGoals2() + "</ul></div> <div class='grid-item'> <h3>Skills and Experience: </h3> <ul style='font-size:18'> <li>" + persona.getSkills1() + "</li> <li>" + persona.getSkills2() + "</ul></div></div>";
        }

        //Persona 3
        html = html + """
            <div class='margin'>
                <h2>Persona 3</h2>
                <img src="persona3.png" alt="Persona 3" height="185" style="float: left; margin-right: 15px">
            </div>
            """;         
        ArrayList<Persona> persona3 = jdbc.getPersona("Sophie");        
        for (Persona persona : persona3) {
            html = html + "<div class ='persona_text'> Name: " + persona.getName() + "</div>";
            html = html + "<div class ='persona_text'> Age: " + persona.getAge() + " years old</div>";
            html = html + "<div class ='persona_text'> Ethnicity: " + persona.getEthnicity() + "</div>";
            html = html + "<div class ='margin persona_text'> Background: <br>" + persona.getBackground() + "</div>";
            html = html + "<div class='margin grid-container'> <div class='grid-item'> <h3>Needs: </h3> <ul style='font-size:18'> <li>" + persona.getNeeds1() + "</li> <li>" + persona.getNeeds2() + "</ul> </div> <div class='grid-item'> <h3>Goals: </h3> <ul style='font-size:18'> <li>" + persona.getGoals1() + "</li> <li>" + persona.getGoals2() + "</ul></div> <div class='grid-item'> <h3>Skills and Experience: </h3> <ul style='font-size:18'> <li>" + persona.getSkills1() + "</li> <li>" + persona.getSkills2() + "</ul></div></div>";
        }

        // Team Description
        html = html + """
        <br>
        <br>
        <hr>    
        </div></div>
        <div class="margin">
            <h2 style="text-align: center; color: darkolivegreen">Team</h2>
            <h4 style="text-align: center; width: 50%; float: left; color: darkolivegreen">Edward Lim Padmajaya</h4>
            <h4 style="text-align: center; width: 50%; float: right; color: darkolivegreen">Evelyn Lie</h4>
            <h4 style="text-align: center; width: 50%; float: left; color: darkolivegreen">s3957503</h4>
            <h4 style="text-align: center; width: 50%; float: right; color: darkolivegreen">s3951140</h4>
        </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        
        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

}
