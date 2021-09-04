package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void increment(View view){
        if (quantity==100){
            Toast.makeText(this,"You cannot have more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        if (quantity==1){
            Toast.makeText(this,"You cannot have less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }
    public void submitOrder(View view) {
        final CheckBox checkBox = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean x=checkBox.isChecked();
        final CheckBox checkBox2 = (CheckBox) findViewById(R.id.notify_me_checkbox2);
        boolean y=checkBox2.isChecked();
        EditText txt = (EditText) findViewById(R.id.name_description_view);
        String val=txt.getText().toString();
        int price = calculatePrice(x,y);
        String priceMessage=createOrderSummary(price,x,y,val);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "JUST JAVA ORDER FOR "+ val);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int xy) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + xy);
    }
    private int calculatePrice(boolean x,boolean y){
        int pricePerCups=5;

        if (x) {
            pricePerCups = pricePerCups + 1;
        }
        if (y) {
            pricePerCups = pricePerCups + 2;
        }
        int price =quantity*pricePerCups;
        return price;

    }
    private String createOrderSummary (int price,boolean x,boolean y,String val){

        String priceMessage="Name: "+ val;
               priceMessage=priceMessage + "\n Add Whipped Cream?"+x;
               priceMessage=priceMessage + "\n Add Chocolate?" + y;
               priceMessage= priceMessage + "\nQunatity: "+ quantity;
               priceMessage=priceMessage + "\nTotal: $" + price;
               priceMessage=priceMessage + "\nThank You";
        return (priceMessage);

    }

}

