package com.workintech.dependencyproject.rest;

import com.workintech.dependencyproject.mapping.DeveloperResponse;
import com.workintech.dependencyproject.model.Developer;
import com.workintech.dependencyproject.model.JuniorDeveloper;
import com.workintech.dependencyproject.model.MidDeveloper;
import com.workintech.dependencyproject.model.SeniorDeveloper;
import com.workintech.dependencyproject.tax.DeveloperTax;
import com.workintech.dependencyproject.tax.Taxable;
import com.workintech.dependencyproject.validation.DeveloperValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers;
    private Taxable taxable;
    @PostConstruct
    public void init() {
        developers = new HashMap<>();
    }
@Autowired
    public DeveloperController(@Qualifier("developerTax") Taxable taxable) {
        this.taxable = taxable;
    }
    @GetMapping("/")
    public List<Developer> get(){
        return developers.values().stream().toList();
    }
    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable int id) {
        if(DeveloperValidation.isIdValid(id)){
            return new DeveloperResponse(null,"Success",200);
        }else{
            return new DeveloperResponse(null,"Id is not valid",400);
        }

    }
    @PostMapping("/")
    public Developer save(@RequestBody Developer developer){
        Developer savedDeveloper = createDeveloper(developer);
        if(savedDeveloper == null){

        }
        developers.put(developer.getId(),savedDeveloper);
        return developers.get(developer.getId());
    }

    @PutMapping("/{id}")
    public Developer update(@PathVariable int id, @RequestBody Developer developer) {
        if(!developers.containsKey(id)) {

        }
        developer.setId(id);
        Developer updatedDeveloper = createDeveloper(developer);
        developers.put(id,updatedDeveloper);
        return developers.get(id);
    }

    @DeleteMapping("/{id}")
    public Developer delete(@PathVariable int id) {
        if(!developers.containsKey(id)){

        }
        Developer developer = developers.get(id);
        developers.remove(id);
        return developer;
    }

    private Developer createDeveloper(Developer developer) {
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
