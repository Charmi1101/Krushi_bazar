package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Sell_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edt_cropName, edt_preferredArea, edt_quantity, edt_price;
    private String SpinnerText = "", S_edt_cropName = "", S_edt_preferredArea = "", S_edt_quantity = "", S_edt_price = "";
    private String[] w_value = {"0.250 kg", "0.50 kg", "1.000 kg", "2.000 kg", "3.000 kg", "4.000 kg", "5.000 kg", "10.000 kg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        final Spinner spinner = findViewById(R.id.spinner_weight);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, w_value);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        Button btn_proceedToSell = findViewById(R.id.btn_proceedSell);
        edt_cropName = findViewById(R.id.edt_cropName);
        edt_preferredArea = findViewById(R.id.edt_PreferredArea);
        edt_quantity = findViewById(R.id.edt_quantityValue);
        edt_price = findViewById(R.id.edt_price);

        if (getIntent().getBooleanExtra("ifcondition", false)) {

            edt_cropName.append(getIntent().getStringExtra("CropValue").toString());
            edt_preferredArea.append(getIntent().getStringExtra("PreferredArea").toString());
            SpinnerText = (getIntent().getStringExtra("WeightPerQuantity"));
            edt_quantity.append(getIntent().getStringExtra("Quantity").toString());
            edt_price.append(getIntent().getStringExtra("PricePerQuantity").toString());
            Log.d("Tag",SpinnerText+":"+edt_cropName.getText().toString()+":"+
            edt_preferredArea.getText().toString()+":"+
            edt_quantity.getText().toString()+":"+
            edt_price.getText().toString());


            for (int i = 0; i < w_value.length; i++) {
                Log.d("Tag", SpinnerText + " " + w_value[i].substring(0,4));

                if (SpinnerText.compareTo(w_value[i].substring(0,4)) == 0) {
                    spinner.setSelection(i);
                    break;
                }
            }
        }


        btn_proceedToSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSetText();
                Intent intent = new Intent(Sell_Activity.this, Sell_Confirm.class);
                intent.putExtra("CropValue", S_edt_cropName);
                intent.putExtra("PreferredArea", S_edt_preferredArea);
                intent.putExtra("WeightPerQuantity", SpinnerText);
                intent.putExtra("Quantity", S_edt_quantity);
                intent.putExtra("PricePerQuantity", S_edt_price);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(this);
    }

    private void edtSetText() {
        S_edt_cropName = edt_cropName.getText().toString();
        S_edt_preferredArea = edt_preferredArea.getText().toString();
        S_edt_quantity = edt_quantity.getText().toString();
        S_edt_price = edt_price.getText().toString();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SpinnerText = w_value[i].substring(0, 5);

        /*for( int j=0; j<SpinnerText.length(); j++){
            if(SpinnerText.charAt(j) == ' '){
                SpinnerText = w_value[i].substring(0,j);
            }
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}