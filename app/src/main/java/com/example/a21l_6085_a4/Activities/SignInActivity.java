package com.example.a21l_6085_a4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a21l_6085_a4.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnlogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
        btnlogin= findViewById(R.id.buttonLogin);
        btnlogin.setOnClickListener(v->{
            loginUser();
        });
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

    }

    public void loginUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
         btnlogin.setEnabled(false); // Disable login button to prevent multiple clicks

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        btnlogin.setVisibility(View.VISIBLE);

                        btnlogin.setEnabled(true);

                        // Redirect to the shopping list or main activity
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();  // Close LoginActivity
                    } else {
                        progressBar.setVisibility(View.GONE);
                        btnlogin.setVisibility(View.VISIBLE);

                        btnlogin.setEnabled(true);
                        Toast.makeText(SignInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void goToRegister(View view) {
        // Navigate to Registration activity
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword(View view) {
        // Navigate to Forgot Password activity
        Intent intent = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
