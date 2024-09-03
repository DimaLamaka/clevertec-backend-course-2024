package ru.clevertec.multithreading.step6;

import java.util.stream.IntStream;

public class Resource {
    private final StringBuilder firstResource;
    private final StringBuilder secondResource;

    public Resource(StringBuilder firstResource, StringBuilder secondResource) {
        this.firstResource = firstResource;
        this.secondResource = secondResource;
    }

    public void appendToFirstResource(String value) {
        synchronized (firstResource) {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        String appendValue = i + ":" + value + " ";
                        System.out.println("Append to first resource: " + appendValue);
                        firstResource.append(appendValue);
                    });
            firstResource.append("\n");
        }
    }

    public void appendToSecondResource(String value) {
        synchronized (secondResource) {
            IntStream.range(0, 100)
                    .forEach(i -> {
                        String appendValue = i + ":" + value + " ";
                        System.out.println("Append to second resource: " + appendValue);
                        secondResource.append(appendValue);
                    });
            secondResource.append("\n");
        }
    }
}
