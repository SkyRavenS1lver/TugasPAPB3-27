package com.example.localetextstarter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button btnSubmit ;
    EditText harga ;
    TextView result ;
    TextView currency ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSubmit = findViewById(R.id.submit);
        harga = findViewById(R.id.inputHarga);
        result = findViewById(R.id.result);
        currency = findViewById(R.id.currency);
        Locale currentLocale = getResources().getConfiguration().locale;
        Currency currentCurrency = Currency.getInstance(currentLocale);
        currency.setText("("+currentCurrency.getDisplayName()+")");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NumberFormat format = NumberFormat.getInstance(Locale.US);
                double hasilnya = 0;
                try {
                    final Number number = format.parse(harga.getText().toString());
                    hasilnya = Double.parseDouble(number.toString())*100;
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
                String results = currencyFormatter.format(hasilnya);
                result.setText(results);
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelp();
            }
        });
        Date date = new Date();
        long expirationDate = date.getTime() + TimeUnit.DAYS.toMillis(3);
        date.setTime(expirationDate);
        String formatDate = DateFormat.getDateInstance().format(date);
        TextView expiredTextView = findViewById(R.id.date);
        expiredTextView.setText(formatDate);
    }

    /**
     * Shows the Help screen.
     */
    private void showHelp() {
        // Create the intent.
        Intent helpIntent = new Intent(this, HelpActivity.class);
        // Start the HelpActivity.
        startActivity(helpIntent);
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle options menu item clicks here.
        int id = item.getItemId();
        if (id == R.id.action_help) {
            Intent intent = new Intent(this, HelpActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_language) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}