package com.example.lookfordev;

public class CompanyHelperClass {
   String CompanyName, CompanyEmail;

    public CompanyHelperClass() {
    }

    public CompanyHelperClass(String companyName, String companyEmail) {
        CompanyName = companyName;
        CompanyEmail = companyEmail;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getCompanyEmail() {
        return CompanyEmail;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public void setCompanyEmail(String companyEmail) {
        CompanyEmail = companyEmail;
    }
}

