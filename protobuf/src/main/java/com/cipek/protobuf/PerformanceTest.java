package com.cipek.protobuf;

import com.cipek.json.JPerson;
import com.cipek.protobuf.model.proto.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;

public class PerformanceTest {

    public static void main(String[] args) {
        //json serialize deserialize
        JPerson jPerson = JPerson.builder()
                .age(29)
                .name("Hakan")
                .build();
        ObjectMapper mapper = new ObjectMapper();

        Runnable runnableForJson = () -> {
            try {
                byte[] bytes = mapper.writeValueAsBytes(jPerson);
                JPerson newJPerson = mapper.readValue(bytes, JPerson.class);
            } catch (JsonProcessingException e) {
                System.out.println("write failed");
//                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("read failed");
//                e.printStackTrace();
            }
        };

        //proto serialize deserialize
        Person person = Person.newBuilder()
                .setAge(29)
                .setName("Hakan")
                .build();

        Runnable runnableForProto = () -> {
            try {
                byte[] bytes = person.toByteArray();
                Person newPerson = Person.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                System.out.println("parse failed for proto");
                e.printStackTrace();
            }
        };

        for (int i = 0; i<5; i++) {
            performanceTest(runnableForJson, "JSON");
            performanceTest(runnableForProto, "Proto");
        }
    }

    static void performanceTest(Runnable runnable, String method) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i<5_000_000; i++) runnable.run();
        long endTime = System.currentTimeMillis();
        System.out.println(method + " takes " + (endTime-startTime) + "ms");
    }
}
