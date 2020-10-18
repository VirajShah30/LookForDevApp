package com.example.lookfordev;

public class GigHelperClass {
    String Title;
    String Budget;
    String Description;
    String Category;
    String Email;

    public GigHelperClass() {
    }

    public GigHelperClass(String title, String budget, String description, String category, String email) {
        Title = title;
        Budget = budget;
        Description = description;
        Category = category;
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTitle() {
        return Title;
    }

    public String getBudget() {
        return Budget;
    }

    public String getDescription() {
        return Description;
    }

    public String getCategory() {
        return Category;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
