package ru.clevertec;

import ru.clevertec.generator.PersonGenerator;
import ru.clevertec.stream.AdvancedExample;

public class Main {
    public static void main(String[] args) {
        AdvancedExample advancedExample = new AdvancedExample(new PersonGenerator());
        advancedExample.showAdvancedExamples();
    }
}