package com.example.lookfordev;

public class DeveloperHelperClass {
    String Name,WorkEmail,Experience,Category;

    public DeveloperHelperClass() {
    }

    public DeveloperHelperClass(String name, String workEmail, String experience, String category) {
        Name = name;
        WorkEmail = workEmail;
        Experience = experience;
        Category = category;
    }

    public String getName() {
        return Name;
    }

    public String getWorkEmail() {
        return WorkEmail;
    }

    public String getExperience() {
        return Experience;
    }

    public String getCategory() {
        return Category;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setWorkEmail(String workEmail) {
        WorkEmail = workEmail;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
