package com.cipek.protobuf;

import com.cipek.protobuf.model.proto.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonDemo {

    static final Person hakan = Person.newBuilder()
            .setAge(29)
            .setName("Hakan")
            .build();

    public static void main(String[] args) throws IOException {
        protoEqualsExample();
        protoWriteAndReadExample();

    }

    static void protoEqualsExample() {
        Person impostorHakan = Person.newBuilder()
                .setName("Hakan")
                .setAge(29)
                .build();

        Person anotherImpostorHakan = Person.newBuilder()
                .setName("hakan")
                .setAge(29)
                .build();

        System.out.println(hakan);
        System.out.println(hakan.equals(impostorHakan));
        System.out.println(hakan.equals(anotherImpostorHakan));
    }

    static void protoWriteAndReadExample() throws IOException {
        Path path = Paths.get("cipek.out");
        protoWriteExample(path);
        protoReadExample(path);
    }

    static void protoWriteExample(Path path) throws IOException {
        System.out.println("before serialized\n" + hakan);
        Files.write(path, hakan.toByteArray());
    }

    static void protoReadExample(Path path) throws IOException {
        Person deserializedPerson = Person.parseFrom(Files.readAllBytes(path));
        System.out.println("after deserialized\n" + deserializedPerson);
    }
}
