package com.example.bobyk.np.models.facebook;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;

/**
 * Created by bobyk on 14.09.16.
 */
public class FacebookModel implements IFacebookModel{

    @Override
    public GraphRequest getFacebookUser(AccessToken accessToken, GraphRequest.GraphJSONObjectCallback callback) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, callback);
        Bundle params = new Bundle();
        params.putString("fields", "id,name,email,picture,gender");
        request.setParameters(params);
        request.executeAsync();
        return request;
    }
}
