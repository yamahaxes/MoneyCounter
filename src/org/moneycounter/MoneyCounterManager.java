package org.moneycounter;

import org.moneycounter.event.Event;
import org.moneycounter.event.Expense;
import org.moneycounter.event.Person;
import org.moneycounter.event.Transfer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.zip.DataFormatException;

public class MoneyCounterManager {

    private Event event;

    public MoneyCounterManager(){

    }

    public void loadEvent(File file) throws DataFormatException, IOException {

        String content = readFileContents(file.getAbsolutePath());

        event = new Event("");
        String[] lines = content.split("\\r?\\n");

        if (lines.length > 0){
            // first line is a header
            String[] columns = lines[0].split(",");
            // initialization of all members
            Map<String, Person> personMap = new HashMap<>(); // to search by name

            for (int columnCounter = 2; columnCounter < columns.length; columnCounter++) {
                Person person = new Person(columns[columnCounter]);
                event.addPerson(person);
                personMap.put(columns[columnCounter], person);
            }

            for (int lineCounter = 1; lineCounter < lines.length; lineCounter++) {
                String[] data = lines[lineCounter].split(",", -1);

                if (data.length != columns.length){
                    throw new DataFormatException("Incorrect input file format");
                }

                // filling shares costs
                Map<Person, Double> sharedCosts = new HashMap<>();
                for (int columnCounter = 2; columnCounter < columns.length; columnCounter++) {
                    double sum;
                    try {
                        sum = Double.parseDouble(data[columnCounter]);
                    }catch (NumberFormatException formatException){
                        sum = 0.0d;
                    }
                    sharedCosts.put(event.getPerson(columnCounter - 2), sum);
                }

                // add expense
                Expense expense = new Expense(data[1], personMap.get(data[0]), sharedCosts);
                event.addExpense(expense);
            }
        }
    }

    public void uploadResult(File file) throws IOException {
        if (event == null){
            return;
        }

        FileWriter writer = new FileWriter(file);
        List<Transfer> transfers = event.getTransactions();

        StringBuilder str = new StringBuilder(",");
        List<Person> people = event.getPersonList();

        for (Person person: people) {
            str.append(person.getName()).append(",");
        }
        str.deleteCharAt(str.length() - 1);
        writer.write(str.toString());

        for (Person personRow: people) {
            str = new StringBuilder();
            str.append("\n").append(personRow.getName()).append(",");

            for (Person personColumn: people) {
                for (Transfer transfer: transfers) {
                    if (transfer.getFromPerson() == personRow && transfer.getToPerson() == personColumn){
                        str.append(transfer.getSum());
                    }
                }
                str.append(",");
            }
            str.deleteCharAt(str.length() - 1);
            writer.write(str.toString());
        }

        writer.close();
    }

    public void printResult(){
        if (event == null){
            return;
        }

        List<Transfer> transfers = event.getTransactions();
        for (Transfer transfer: transfers) {
            System.out.println(transfer.getFromPerson() + " to " + transfer.getToPerson() + ": " + transfer.getSum());
        }
    }

    private String readFileContents(String path) throws IOException {
        return Files.readString(Path.of(path));
    }

}
