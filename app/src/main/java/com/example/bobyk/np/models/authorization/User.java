package com.example.bobyk.np.models.authorization;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/5/17.
 */


@IgnoreExtraProperties
public class User extends BaseAuthModel{

    public User() {

    }

    public User(String firstName, String surname, String middleName, String email,  String role,
                String phoneNumber, String photoUrl) {
       super(firstName, surname, middleName, email, role, phoneNumber, photoUrl);
    }

}
