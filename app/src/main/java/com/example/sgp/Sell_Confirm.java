package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sgp.Adapters.Database_Class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Sell_Confirm extends AppCompatActivity {
    String Name_Value = getName(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
    int orderno = 0;

    TextView txt_crop, txt_preferredArea, txt_weightPerQua, txt_quantity, txt_TotalWeight, txt_pricePerQua, txt_TotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_confirm);
        txt_crop = findViewById(R.id.txt_cropValue);
        txt_preferredArea = findViewById(R.id.txt_areaSellValue);
        txt_weightPerQua = findViewById(R.id.txt_weightSellValue);
        txt_quantity = findViewById(R.id.txt_QuantitySellValue);
        txt_TotalWeight = findViewById(R.id.txt_TotalWeightSellValue);
        txt_pricePerQua = findViewById(R.id.txt_priceSellValue);
        txt_TotalPrice = findViewById(R.id.txt_TotalPriceSellValue);

        double Weight_i = parseDouble((getIntent().getStringExtra("WeightPerQuantity")));
        int Quantity_i = parseInt((getIntent().getStringExtra("Quantity")));
        double temp = Weight_i * (double) Quantity_i;
        String TotalWeight_i = String.format("%-10.3f kg", temp);
        double PricePerQuantity = parseDouble((getIntent().getStringExtra("PricePerQuantity")));
        temp = temp * PricePerQuantity;
        String TotalPrice_i = "Rs." + String.format("%.2f", temp);


        txt_crop.setText(getIntent().getStringExtra("CropValue"));
        txt_preferredArea.setText(getIntent().getStringExtra("PreferredArea"));
        txt_weightPerQua.setText(getIntent().getStringExtra("WeightPerQuantity"));
        txt_quantity.setText(getIntent().getStringExtra("Quantity"));
        txt_TotalWeight.setText(TotalWeight_i);
        txt_pricePerQua.setText(getIntent().getStringExtra("PricePerQuantity"));
        txt_TotalPrice.setText(TotalPrice_i);

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sell_Confirm.this, Sell_Activity.class);
                intent.putExtra("ifcondition", true);
                intent.putExtra("CropValue", txt_crop.getText().toString());
                intent.putExtra("PreferredArea", txt_preferredArea.getText().toString());
                intent.putExtra("WeightPerQuantity", txt_weightPerQua.getText().toString().substring(0, 4));
                intent.putExtra("Quantity", txt_quantity.getText().toString());
                intent.putExtra("PricePerQuantity", txt_pricePerQua.getText().toString());
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_reorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updating_RealtimeDatabase();
                Toast.makeText(Sell_Confirm.this, "Confirm selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Sell_Confirm.this, Sell_Activity.class));

            }
        });

        findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Updating_RealtimeDatabase();
                Intent intent = new Intent(Sell_Confirm.this, Selller_Dashboard.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(Sell_Confirm.this, "Confirm selected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Updating_RealtimeDatabase() {
        int n = getOrderNumber();
        String mPhnoValue = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        String mNameValue = Name_Value;
        String mCropNameValue = txt_crop.getText().toString();
        String mPriceValue = txt_pricePerQua.getText().toString();
        String mQuantityValue = txt_quantity.getText().toString();
        String mWeightValue = txt_weightPerQua.getText().toString();
        String mAreaValue = txt_preferredArea.getText().toString();
        String mDateValue = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        Database_Class D = new Database_Class(mNameValue, mPhnoValue, mCropNameValue, mPriceValue, mQuantityValue, mWeightValue, mAreaValue, mDateValue);

        FirebaseDatabase.getInstance().getReference("Main Stock/Orders/" + n).setValue(D);
        FirebaseDatabase.getInstance().getReference("Main Stock/nextOrderCounter").setValue(n + 1);


    }

    private String getName(String phno) {

        FirebaseFirestore.getInstance().document("DATA/" + phno)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        Name_Value = value.get("name").toString();
                    }
                });
        return Name_Value;
    }

    private int getOrderNumber() {
        FirebaseDatabase.getInstance().getReference("Main Stock/nextOrderCounter")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        orderno = Integer.parseInt(snapshot.getValue() + "");
                        Log.d("Tag", orderno + "Order");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return orderno;
    }
}