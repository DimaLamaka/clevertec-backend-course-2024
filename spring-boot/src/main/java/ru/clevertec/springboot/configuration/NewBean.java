package ru.clevertec.springboot.configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NewBeanAnnotation
public class NewBean {
    private String name = "name11";

    public NewBean(String name) {
        this.name = name;
    }
}
