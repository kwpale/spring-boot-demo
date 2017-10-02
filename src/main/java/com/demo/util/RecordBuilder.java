package com.demo.util;

import com.demo.entity.User;

public class RecordBuilder {

    public static User buildUser(String email){
        User user = new User();
        user.setEmail(email);
        String[] namePieces = email.split("@");
        user.setUsername(namePieces[0].replace(".","").toLowerCase());
        if (namePieces[0].contains(".")) {
            // fn and ln can be inferred
            String[] names = namePieces[0].split("\\.");
            user.setLastName(names[1]);
            user.setFirstName(names[0]);
        }
        user.setActive(true);
        return user;
    }
}
