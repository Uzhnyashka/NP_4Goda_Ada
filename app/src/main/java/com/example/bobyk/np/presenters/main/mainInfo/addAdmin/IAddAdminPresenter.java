package com.example.bobyk.np.presenters.main.mainInfo.addAdmin;

import com.example.bobyk.np.models.authorization.Admin;

/**
 * Created by bobyk on 6/9/17.
 */

public interface IAddAdminPresenter {
    void registerAdmin(Admin admin, String password);
}
