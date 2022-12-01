package app;

public class Persona {
    public String name;

    public int age;

    public String ethnicity;

    public String background;

    public String needs_1;

    public String needs_2;

    public String goals_1;

    public String goals_2;

    public String skills_1;

    public String skills_2;

    public Persona() {
    }

    public Persona(String name, String image, int age, String ethnicity, String background, String needs_1, String needs_2, String goals_1, String goals_2, String skills_1, String skills_2) {
        this.name = name;
        this.age = age;
        this.ethnicity = ethnicity;
        this.background = background;
        this.needs_1 = needs_1;
        this.needs_2 = needs_2;
        this.goals_1 = goals_1;
        this.goals_2 = goals_2;
        this.skills_1 = skills_1;
        this.skills_2 = skills_2;
     }
    
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public String getBackground() {
        return background;
    }

    public String getNeeds1() {
        return needs_1;
    }

    public String getNeeds2() {
        return needs_2;
    }

    public String getGoals1() {
        return goals_1;
    }

    public String getGoals2() {
        return goals_2;
    }

    public String getSkills1() {
        return skills_1;
    }

    public String getSkills2() {
        return skills_2;
    }
}
