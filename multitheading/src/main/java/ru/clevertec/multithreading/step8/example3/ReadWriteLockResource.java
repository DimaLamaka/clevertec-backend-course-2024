package ru.clevertec.multithreading.step8.example3;


public class ReadWriteLockResource {
    private StringBuilder stringBuilder;

    public ReadWriteLockResource(StringBuilder stringBuilder) {
        this.stringBuilder = stringBuilder;
    }

    public void write(String value) {
        System.out.println(Thread.currentThread() + " <<WRITE>> to stringBuilder");
        stringBuilder.append(value)
                .append(" ");
    }

    public String read() {
        return stringBuilder.toString();
    }
}
