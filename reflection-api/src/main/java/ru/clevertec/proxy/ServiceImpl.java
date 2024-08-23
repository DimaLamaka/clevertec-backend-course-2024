package ru.clevertec.proxy;

public class ServiceImpl implements Service {
    @Override
    public void execute(String value) {
        System.out.println("Executing method execute with value: " + value);
        executePrivate();
    }

    private void executePrivate() {
        System.out.println("execute private method");
    }
}
