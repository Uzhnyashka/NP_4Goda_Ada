package com.example.bobyk.np.models.authorization;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by bobyk on 6/9/17.
 */

@IgnoreExtraProperties
public class Driver extends BaseAuthModel{


    public Driver() {

    }

    public Driver(String firstName, String surname, String middleName,
                  String email, String role, String phoneNumber,
                  String photoUrl) {
          super(firstName, surname, middleName, email, role, phoneNumber, photoUrl);
    }


}
