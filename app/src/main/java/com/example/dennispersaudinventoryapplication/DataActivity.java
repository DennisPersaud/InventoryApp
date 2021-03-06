package com.example.dennispersaudinventoryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DataActivity extends AppCompatActivity {

    // Initialize variables
    private TextView itemName, itemPrice, itemCount;
    private Button btnAdd, btnDelete, btnUpdate, btnNotify;
    private GridView grid;
    Intent intent;
    ItemData itemData;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        // Assign variables
        databaseHelper = new DatabaseHelper(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Initialize views
        initViews();

        // Load all items from database into the grid
        showItemsOnListView();

        // Add item button listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    if(validateItemInfo(itemName, itemPrice, itemCount)){

                        verifyItemAdded(itemName, itemPrice, itemCount);
                    }else{

                        Toast.makeText(DataActivity.this, "Add item failed.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                    Toast.makeText(DataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete item button listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    if(validateItemInfo(itemName, itemPrice, itemCount)){

                        verifyItemDeleted(itemName, itemPrice, itemCount);
                    }else{

                        Toast.makeText(DataActivity.this, "Delete item failed.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                    Toast.makeText(DataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Update item count button listener
        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{

                    if(validateItemInfo(itemName, itemPrice, itemCount)){

                        verifyItemUpdated(itemName, itemPrice, itemCount);
                    }else{

                        Toast.makeText(DataActivity.this, "Update item failed.", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){

                    Toast.makeText(DataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Notification button listener
        btnNotify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try{
                    // Start MessageActivity on button click
                    intent = new Intent(DataActivity.this, MessageActivity.class);
                    startActivity(intent);
                }catch(Exception e){

                    Toast.makeText(DataActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Load all items from database into the grid
    private void showItemsOnListView() {

        GridAdapter gridAdapter = new GridAdapter(DataActivity.this, databaseHelper.getAllItems());
        grid.setAdapter(gridAdapter);
    }

    private boolean validateItemInfo(TextView itemName, TextView itemPrice, TextView itemCount){

        String itemNameInput = itemName.getText().toString();
        String itemPriceInput = itemPrice.getText().toString();
        String itemCountInput = itemCount.getText().toString();

        if(!itemNameInput.isEmpty() && !itemPriceInput.isEmpty() && !itemCountInput.isEmpty()){

            return true;
        }else{

            Toast.makeText(DataActivity.this, "Invalid item.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void verifyItemAdded(TextView itemName, TextView itemPrice, TextView itemCount){

        // Add item to database, update grid view and print message to user if successful
        itemData = new ItemData(-1, itemName.getText().toString(), Integer.parseInt(itemPrice.getText().toString()), Integer.parseInt(itemCount.getText().toString()));
        databaseHelper = new DatabaseHelper(DataActivity.this);

        boolean success = databaseHelper.addItem(itemData);

        if(success){

            showItemsOnListView();
            Toast.makeText(DataActivity.this, itemData.getItemName() + " has been added to inventory.", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(DataActivity.this, "Add item failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyItemDeleted(TextView itemName, TextView itemPrice, TextView itemCount){

        // Delete item from database, update grid view and print message to user if successful
        itemData = new ItemData(-1, itemName.getText().toString(), Integer.parseInt(itemPrice.getText().toString()), Integer.parseInt(itemCount.getText().toString()));
        databaseHelper = new DatabaseHelper(DataActivity.this);

        boolean success = databaseHelper.deleteItem(itemData);

        if(success){

            showItemsOnListView();
            Toast.makeText(DataActivity.this, itemData.getItemName() + " has been deleted from inventory", Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(DataActivity.this, "Remove item failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyItemUpdated(TextView itemName, TextView itemPrice, TextView itemCount){

        // Update count for item name, update grid view and print message to user if successful
        itemData = new ItemData(-1, itemName.getText().toString(), Integer.parseInt(itemPrice.getText().toString()), Integer.parseInt(itemCount.getText().toString()));
        databaseHelper = new DatabaseHelper(DataActivity.this);

        boolean success = databaseHelper.updateItem(itemData);

        if(success){

            showItemsOnListView();
            Toast.makeText(DataActivity.this, "Count for " + itemData.getItemName() + " has been updated to " + itemData.getItemCount(), Toast.LENGTH_SHORT).show();
        }else{

            Toast.makeText(DataActivity.this, "Update failed.", Toast.LENGTH_SHORT).show();
        }
    }

    // Initialize views
    private void initViews(){

        itemName = (TextView)findViewById(R.id.editTextItemName);
        itemPrice = (TextView)findViewById(R.id.editTextItemPrice);
        itemCount = (TextView)findViewById(R.id.editTextItemQty);
        btnAdd = (Button)findViewById(R.id.buttonAdd);
        btnDelete = (Button)findViewById(R.id.buttonDelete);
        btnUpdate = (Button)findViewById(R.id.buttonUpdate);
        btnNotify = (Button)findViewById(R.id.buttonNotification);
        grid = (GridView)findViewById(R.id.gridView);
    }
}
