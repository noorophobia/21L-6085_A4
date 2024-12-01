package com.example.a21l_6085_a4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
 ;
import com.example.a21l_6085_a4.Model.ShoppingItem;
import com.example.a21l_6085_a4.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
 import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class ShoppingItemAdapter extends FirebaseRecyclerAdapter<ShoppingItem, ShoppingItemAdapter.ShoppingItemViewHolder> {

    private Context context;

    public ShoppingItemAdapter(@NonNull FirebaseRecyclerOptions<ShoppingItem> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ShoppingItemViewHolder holder, int position, @NonNull ShoppingItem model) {
        holder.name.setText(model.getName());
        holder.quantity.setText(model.getQuantity());
        holder.price.setText(model.getPrice());

        // Set onClickListener for the Delete button
        holder.deleteButton.setOnClickListener(v -> {
            String itemId = getRef(position).getKey();
            deleteItem(itemId);
            updateItemIds();
        });
    }

    @NonNull
    @Override
    public ShoppingItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_shopping_item, parent, false);
        return new ShoppingItemViewHolder(v);
    }

    // Delete the item from Firebase Realtime Database
    private void deleteItem(String itemId) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("shopping_items");
        databaseRef.child(itemId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error deleting item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Method to update the IDs after deletion
    private void updateItemIds() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("shopping_items");
        databaseRef.get().addOnSuccessListener(dataSnapshot -> {
            int i = 0;
            for (var itemSnapshot : dataSnapshot.getChildren()) {
                String itemId = itemSnapshot.getKey();
                if (itemId != null) {
                    databaseRef.child(itemId).child("id").setValue(i);
                    i++;
                }
            }
        });
    }

    // ViewHolder to hold each item view
    public static class ShoppingItemViewHolder extends RecyclerView.ViewHolder {

        TextView name, quantity, price;
        Button deleteButton;

        public ShoppingItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            quantity = itemView.findViewById(R.id.itemQuantity);
            price = itemView.findViewById(R.id.itemPrice);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
