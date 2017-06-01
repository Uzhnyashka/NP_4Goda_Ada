package com.example.bobyk.np.views.authorization.signIn;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bobyk.np.R;
import com.example.bobyk.np.event.EventAuthChangeFragment;
import com.example.bobyk.np.utils.Role;
import com.example.bobyk.np.views.authorization.signUp.SignUpFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by bobyk on 3/25/17.
 */

public class SignInFragment extends Fragment implements SignInView {

    @Bind(R.id.tv_sign_in_label)
    TextView signInLabelTextView;
    @Bind(R.id.btn_sign_up_member)
    TextView signUpMemberTextView;
    @Bind(R.id.et_email)
    EditText mEmailEditText;
    @Bind(R.id.et_password)
    EditText mPasswordEditText;

    private Role mRole;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

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
        mAuth = FirebaseAuth.getInstance();
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
        signInLabelTextView.setText("Sign in as " + mRole.toString());
        if (mRole == Role.USER) {
            signUpMemberTextView.setVisibility(View.VISIBLE);
        } else {
            signUpMemberTextView.setVisibility(View.GONE);
        }
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
        signIn(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
