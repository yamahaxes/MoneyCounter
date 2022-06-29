package org.moneycounter.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    public List<Transfer> getTransactions(){
        calculateBalance();

        List<Person> creditors = new ArrayList<>();
        List<Person> debtors = new ArrayList<>();
        List<Transfer> transfers = new ArrayList<>();

        for (Person person: personList) {
            if (person.getBalance() < 0){
                creditors.add(person);
            }else if (person.getBalance() > 0){
                debtors.add(person);
            }
        }

        creditors.sort(Comparator.comparingDouble(Person::getBalance));
        debtors.sort((o1, o2) -> Double.compare(o2.getBalance(), o1.getBalance()));

        for (Person debtor: debtors) {

            if (debtor.getBalance() <= 0){
                continue;
            }

            for (Person creditor: creditors) {

                if (debtor.getBalance() <= 0){
                    break;
                }

                if (creditor.getBalance() >= 0) {
                    continue;
                }
                double minSum = Math.min(debtor.getBalance(), Math.abs(creditor.getBalance()));

                debtor.addBalance(-minSum);
                creditor.addBalance(minSum);

                Transfer transfer = new Transfer(debtor, creditor, minSum);
                transfers.add(transfer);
            }

        }

        return transfers;
    }

    public void calculateBalance(){

        for (Person person: personList) {
            person.setBalance(0.0);
        }

        for (Expense expense:
                expenses) {

            Person whoPayPerson = expense.getWhoPayPerson();
            double totalExpense = 0.0;

            for (Map.Entry<Person, Double> entry:
                    expense.getSharedCosts().entrySet()) {

                Person debtor = entry.getKey();

                if (!debtor.equals(whoPayPerson)){
                    debtor.addBalance(entry.getValue());
                    totalExpense += entry.getValue();
                }
            }

            whoPayPerson.addBalance(- totalExpense);
        }

    }
}
