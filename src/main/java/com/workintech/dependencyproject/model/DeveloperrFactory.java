package com.workintech.dependencyproject.model;

import com.workintech.dependencyproject.tax.Taxable;

public class DeveloperrFactory {
    public static Developer createDeveloper(Developer developer, Taxable taxable) {
        Developer savedDeveloper;
        if(developer.getExperience().name().equalsIgnoreCase("junior")) {
            savedDeveloper = new JuniorDeveloper(developer.getId(),developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getSimpleTaxRate(),
                    developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("mid")) {
            savedDeveloper = new MidDeveloper(developer.getId(),developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getMiddleTaxRate(),
                    developer.getExperience());
        } else if (developer.getExperience().name().equalsIgnoreCase("senior")) {
            savedDeveloper = new SeniorDeveloper(developer.getId(),developer.getName(),
                    developer.getSalary() - developer.getSalary() * taxable.getUpperTaxRate(),
                    developer.getExperience());
        }else{
            savedDeveloper = null;
        }
        return savedDeveloper;

    }
}

