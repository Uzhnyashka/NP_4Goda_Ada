package com.example.bobyk.np.views.authorization.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.global.Constants;
import com.example.bobyk.np.presenters.authorization.signIn.SignInPresenter;
import com.example.bobyk.np.utils.Role;
import com.example.bobyk.np.utils.Utils;
import com.example.bobyk.np.views.authorization.additionalData.AdditionalDataFragment;
import com.example.bobyk.np.views.authorization.signUp.SignUpFragment;
import com.example.bobyk.np.views.main.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

/**
 * Created by bobyk on 3/25/17.
 */

public class SignInFragment extends Fragment implements SignInView, GoogleApiClient.OnConnectionFailedListener {

    private String TAG = getClass().getSimpleName();
    
    @Bind(R.id.tv_sign_in_label)
    TextView signInLabelTextView;
    @Bind(R.id.btn_sign_up_member)
    TextView signUpMemberTextView;
    @Bind(R.id.et_email)
    EditText mEmailEditText;
    @Bind(R.id.et_password)
    EditText mPasswordEditText;
    @Bind(R.id.rl_or)
    RelativeLayout orRelativeLayout;
    @Bind(R.id.rl_social)
    RelativeLayout socialRelativeLayout;
    @Bind(R.id.btn_login_fb)
    LoginButton mLoginFBButton;

    private Role mRole;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SignInPresenter mPresenter;
    private CallbackManager mCallbackManager;
    private String fbEmail = "";
    private GoogleApiClient mGoogleApiClient;

    public static SignInFragment newInstance(Role role) {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setRole(role);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        mPresenter = new SignInPresenter(getActivity(), this, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_in, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    private void init() {
        LoginManager.getInstance().registerCallback(mPresenter.getCallbackManager(), mPresenter);
        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        signInLabelTextView.setText("Sign in as " + mRole.toString());
        if (mRole == Role.USER) {
            signUpMemberTextView.setVisibility(View.VISIBLE);
            socialRelativeLayout.setVisibility(View.VISIBLE);
            orRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            signUpMemberTextView.setVisibility(View.GONE);
            socialRelativeLayout.setVisibility(View.GONE);
            orRelativeLayout.setVisibility(View.GONE);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void setRole(Role role) {
        mRole = role;
    }

    @OnClick(R.id.btn_sign_up_member)
    public void onSignUpMemberClick() {
        EventBus.getDefault().post(new EventAuthChangeFragment(SignUpFragment.newInstance(), true));
    }

    @OnClick(R.id.btn_sign_in)
    public void onSignInClick() {
        mPresenter.signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void onSuccessSingIn() {
        mPresenter.checkRoleWithEmail(mEmailEditText.getText().toString(), mRole.toString());
    }

    @Override
    public void onFailSignIn(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void roleConfirmed() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("role", mRole.toString());
        startActivity(intent);

        getActivity().finish();
    }

    @OnClick(R.id.btn_fb)
    public void onFbClick() {
        mPresenter.loginUserWithFacebook();
    }

    @Override
    public void roleFailed() {
        Utils.showToastMessage(getActivity(), "Account is not " + mRole.toString());
    }

    @Override
    public void onSuccessLoginFB(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("WWW success");
                            if (!fbEmail.equals("")) {
                                mPresenter.isRegistred(fbEmail);
                            }

                        } else {
                            System.out.println("WWW " + task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void setFacebookEmail(String email) {
        System.out.println("WWW email " + email);
        fbEmail = email;
    }

    @Override
    public void userExist() {
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        intent.putExtra("role", mRole.toString());
        startActivity(intent);

        getActivity().finish();
    }

    @Override
    public void userIsNotExist() {
        EventBus.getDefault().post(new EventAuthChangeFragment(AdditionalDataFragment.newInstance(),true));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            System.out.println("WWW res " + result.getStatus());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                successGoogleSignIn(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                failedGoogleSignIn();
                // [END_EXCLUDE]
            }
        } else {
            mPresenter.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.btn_google)
    public void onGoogleClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    private void successGoogleSignIn(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(),
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mPresenter.isRegistred(account.getEmail());
                } else {
                    Utils.showToastMessage(getActivity(), "Error google auth");
                }
            }
        });
    }

    private void failedGoogleSignIn() {
        Utils.showToastMessage(getActivity(), "Error google auth1");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Utils.showToastMessage(getActivity(), "Can not connect to Google Play Services");
    }
}
