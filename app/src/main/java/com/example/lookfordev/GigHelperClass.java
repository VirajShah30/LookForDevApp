package com.example.lookfordev;

public class GigHelperClass {
    String Title;
    String Budget;
    String Description;
    String Category;

    public GigHelperClass() {
    }

    public GigHelperClass(String title, String budget, String description, String category) {
        Title = title;
        Budget = budget;
        Description = description;
        Category = category;
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
