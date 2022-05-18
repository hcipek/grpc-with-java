package com.cipek.protobuf;

import com.cipek.protobuf.model.proto.Credentials;
import com.cipek.protobuf.model.proto.EmailCredentials;
import com.cipek.protobuf.model.proto.PhoneOTP;

public class OneOfDemo {

    public static void main(String[] args) {
        EmailCredentials emailCredentials = EmailCredentials.newBuilder()
                .setEmail("example@test.com")
                .setPassword("1234")
                .build();

        PhoneOTP phoneCredentials = PhoneOTP.newBuilder()
                .setNumber(1212342332)
                .setCode(555)
                .build();

        //If there is more than one mode set into object, only last one stays, previous one goes.
        Credentials credentials = Credentials.newBuilder()
                .setEmailMode(emailCredentials)
                .setPhoneMode(phoneCredentials)
                .build();

        login(credentials);
    }

    private static void login (Credentials credentials) {
        switch (credentials.getModeCase()) {
            case EMAILMODE -> System.out.println(credentials.getEmailMode());
            case PHONEMODE -> System.out.println(credentials.getPhoneMode());
            default -> System.out.println("No modes");
        }
    }
}
