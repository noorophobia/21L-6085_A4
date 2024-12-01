package com.example.a21l_6085_a4.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a21l_6085_a4.Adapter.ShoppingItemAdapter;
import com.example.a21l_6085_a4.Model.ShoppingItem;
import com.example.a21l_6085_a4.R;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageButton logout;
    private ShoppingItemAdapter adapter;
    private DatabaseReference databaseReference;
    private ProgressBar progress;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logout=findViewById(R.id.btnlogout);
 progress= findViewById(R.id.progressBar);
 progress.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance("https://l-6085-a4-default-rtdb.firebaseio.com").getReference("shopping_items");

        // Firebase query for the adapter
        FirebaseRecyclerOptions<ShoppingItem> options = new FirebaseRecyclerOptions.Builder<ShoppingItem>()
                .setQuery(databaseReference, ShoppingItem.class)
                .build();

        // Initialize adapter
        progress.setVisibility(View.GONE);
        adapter = new ShoppingItemAdapter(options, this);
        recyclerView.setAdapter(adapter);

        // FAB to add items
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> showAddItemDialog());
        logout.setOnClickListener(v->{
            logout();
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        // Redirect to login screen after logout
        startActivity(new Intent(this, SignInActivity.class));
    }
    private void showAddItemDialog() {
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the dialog layout
        View dialogView = LayoutInflater.from(this).inflate(R.layout.add_dialog, null);
        builder.setView(dialogView);

        EditText etName = dialogView.findViewById(R.id.etName);
        EditText etQuantity = dialogView.findViewById(R.id.etQuantity);
        EditText etPrice = dialogView.findViewById(R.id.etPrice);

        builder.setPositiveButton("Add", (dialogInterface, i) -> {
            String name = etName.getText().toString().trim();
            String quantity = etQuantity.getText().toString().trim();
            String price = etPrice.getText().toString().trim();

            if (name.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a new item
            ShoppingItem item = new ShoppingItem(name, quantity, price);

            // Add to Firebase
            databaseReference.push().setValue(item)
                    .addOnSuccessListener(aVoid -> Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Failed to add item", Toast.LENGTH_SHORT).show());
        });

        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());

        builder.show();
    }
}