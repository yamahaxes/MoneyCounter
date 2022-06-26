package org.moneycounter;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class App {

    public static void main(String[] args) {
        MoneyCounterManager manager = new MoneyCounterManager();
        ClassLoader classLoader = App.class.getClassLoader();

        File inputFile = new File(Objects.requireNonNull(classLoader.getResource("input.csv")).getFile());
        String pathInputFile = inputFile.getParent();

        try {
            manager.loadEvent(inputFile);
        } catch (Exception e){
            System.out.println("Не удалось загрузить данные!");
            return;
        }

        manager.printResult();

        try {
            manager.uploadResult(new File(pathInputFile + "/output.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
