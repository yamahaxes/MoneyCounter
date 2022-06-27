package org.moneycounter.event;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private final String name;
    private final List<Expense> expenses;
    private final List<Person> personList;

    public Event(String name){
        this.name = name;
        expenses = new ArrayList<>();
        personList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPerson(Person person){
        personList.add(person);
    }

    public List<Person> getPersonList(){
        return personList;
    }

    public Person getPerson(int index){
        return personList.get(index);
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
