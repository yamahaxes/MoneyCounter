package org.moneycounter.event;

import java.util.ArrayList;
import java.util.List;

public class Event {

    private final String name;
    private final List<Expense> expenses;

    public Event(String name){
        this.name = name;
        expenses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addExpense(Expense expense){
        expenses.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}
