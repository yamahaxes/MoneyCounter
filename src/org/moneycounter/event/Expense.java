package org.moneycounter.event;

import java.util.Map;

public class Expense {

    private final Person whoPayPerson;
    private final String name;
    private final Map<Person, Double> sharedCosts;

    public Expense(String name, Person whoPayPerson, Map<Person, Double> sharedCosts){
        this.name = name;
        this.whoPayPerson = whoPayPerson;
        this.sharedCosts = sharedCosts;
    }

    public Person getWhoPayPerson() {
        return whoPayPerson;
    }

    public String getName() {
        return name;
    }

    public Map<Person, Double> getSharedCosts() {
        return sharedCosts;
    }
}
