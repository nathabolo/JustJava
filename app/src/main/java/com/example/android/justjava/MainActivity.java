package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_text_view);
        String name = nameField.getText().toString();
        CheckBox whippedCeremCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_cream_checkbox);
        boolean hasWhippedCream = whippedCeremCheckBox.isChecked();
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummery(price, name, hasWhippedCream, hasChocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "nathabolo@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private String createOrderSummery(int price, String name, boolean addWhippedCream, boolean addChocolate) {

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuntity: " + quantity;
        priceMessage += "\nTotal R" + price;
        priceMessage += "\nThank you";

        return priceMessage;
    }


    private int calculatePrice(boolean addWippedCream, boolean addChocolate) {

        int basePrice = 5;

        if (addWippedCream) {

            basePrice = basePrice + 1;
        }

        if (addChocolate) {

            basePrice = basePrice + 2;
        }

        return quantity * basePrice;

    }

    public void increment(View view) {

        if (quantity == 100) {

            //Display an error message

            Toast.makeText(this, " You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();

            //End the method because there is nothing to perform

            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {

        if (quantity == 1) {

            //Display error message

            Toast.makeText(this, "You cannot order less than one cups of coffee", Toast.LENGTH_SHORT).show();

            //End the method because there is nothing to perform

            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);

    }


}
