package com.cipek.protobuf;

import com.cipek.protobuf.model.proto.*;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompositionDemo {

    public static void main(String[] args) {
        Address address = Address.newBuilder()
                .setPostbox(123)
                .setCity("Eindhoven")
                .setStreet("Main Street")
                .build();

        Car clio = Car.newBuilder()
                .setConstructor("Renault")
                .setModel("Clio")
                .setYear(2019)
                .setCarType(CarType.COUPE)
                .build();

        Car focus = Car.newBuilder()
                .setConstructor("Ford")
                .setModel("Focus")
                .setYear(1999)
                .setCarType(CarType.SPORT)
                .build();

        Map dealerMap = Stream.of(
                new AbstractMap.SimpleEntry<>(clio.getYear(), clio),
                new AbstractMap.SimpleEntry<>(focus.getYear(), focus)
        ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

        Person person = Person.newBuilder()
                .setName("Hakan")
                .setAge(29)
                .setAddress(address)
                .addAllCar(Arrays.asList(clio, focus))
                .build();

        Dealer dealer = Dealer.newBuilder()
                .putAllCars(dealerMap)
                .build();

        System.out.println(person);
        System.out.println(dealer);
        dealer.getCarsMap();
        dealer.getCarsCount();
        dealer.getCarsOrThrow(1999);

        try {
            //2000 not exists, exception will be thrown
            System.out.println(dealer.getCarsOrThrow(2000));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getClass() + " for key : 2000");
        } finally {
            //this is how we should do it, if we want to get some Car object at the end.
            System.out.println(dealer.getCarsOrDefault(2000, focus));
        }
    }
}
