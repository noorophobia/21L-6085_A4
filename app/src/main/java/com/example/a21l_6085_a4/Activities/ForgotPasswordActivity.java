package com.example.a21l_6085_a4.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a21l_6085_a4.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button btnLink;
    private TextView tvInfo;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = findViewById(R.id.etEmail);
        btnLink= findViewById(R.id.btnLink);
        mAuth = FirebaseAuth.getInstance();
        btnLink.setOnClickListener(v->{
            resetPassword();
        });
    }

    public void resetPassword( ) {
        String email = etEmail.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                       tvInfo.setText("We have sent you reset link successfully.\n Please check your email");
                      //  Toast.makeText(ForgotPasswordActivity.this, "Reset email sent.", Toast.LENGTH_SHORT).show();
                        finish();  // Close ForgotPasswordActivity
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to send reset email.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
