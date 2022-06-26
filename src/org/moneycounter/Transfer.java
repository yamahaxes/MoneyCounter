package org.moneycounter;

import org.moneycounter.event.Person;

public class Transfer {

    private final Person fromPerson;
    private final Person toPerson;
    private final Double sum;

    public Transfer(Person fromPerson, Person toPerson, Double sum){
        this.fromPerson = fromPerson;
        this.toPerson = toPerson;
        this.sum = sum;
    }


    public Person getFromPerson() {
        return fromPerson;
    }

    public Person getToPerson() {
        return toPerson;
    }

    public Double getSum() {
        return sum;
    }
}
