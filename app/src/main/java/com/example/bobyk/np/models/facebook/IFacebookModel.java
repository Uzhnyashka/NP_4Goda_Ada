package com.example.bobyk.np.models.facebook;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

/**
 * Created by bobyk on 14.09.16.
 */
public interface IFacebookModel {
    GraphRequest getFacebookUser(AccessToken accessToken, GraphRequest.GraphJSONObjectCallback callback);
}
