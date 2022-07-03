package com.example.rabbitmqlistener;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class SimpleMessage implements Serializable {

    private String name;
    private String description;

    public SimpleMessage() {}



}
