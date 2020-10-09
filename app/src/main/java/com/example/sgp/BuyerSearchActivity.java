package com.example.sgp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sgp.Adapters.BuyerSearch_adapter;

import com.example.sgp.Adapters.Database_Class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyerSearchActivity extends AppCompatActivity {

    private ArrayList<Database_Class> mainCardList;
    private BuyerSearch_adapter data_adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_search);
        fillCardList();
        setupRecyclerView();

        ImageButton ArrowButton = findViewById(R.id.arrowButton);
       /* ArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BuyerSearchActivity.this, "Arrow selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyerSearchActivity.this, BuyActivity.class));
            }
        });*/
    }

    private void setupRecyclerView() {

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        data_adapter = new BuyerSearch_adapter(mainCardList);
        layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(data_adapter);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void fillCardList() {
        mainCardList = new ArrayList<Database_Class>(0);
       /* for (int i = 0; i < 15; i++) {
            mainCardList.add(new Database_Class("Seller "+i,"987654321","Lemon","30.00","3","5","Maninagar","28/09/2020"));

        }*/
        FirebaseDatabase.getInstance().getReference("Main Stock/Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsnap:snapshot.getChildren()) {
                            Database_Class S=dsnap.getValue(Database_Class.class);
                            Log.d("Tag",S.mNameValue);
                            mainCardList.add(S);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new BuyerSearch_adapter(mainCardList));
        recyclerView.setLayoutManager(new LinearLayoutManager(BuyerSearchActivity.this));
   }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seller_menu_item:
                Toast.makeText(this, "Seller Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Selller_Dashboard.class));
                break;
            case R.id.buyer_menu_item:
                Toast.makeText(this, "Buyer Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, buyer_dashboard.class));
                break;
            case R.id.home_menu_item:
                Toast.makeText(this, "home Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Dashboard.class));
                break;
            case R.id.account_menu_item:
                Toast.makeText(this, "account Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout_menu_item:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(BuyerSearchActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search.....");



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                customFilter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                customFilter(s);
                //data_adapter.getFilter().filter(s);
                return true;
            }

        });

        return true;
    }
    void customFilter(String s){
        ArrayList<Database_Class> temp=new ArrayList<>();
        for (Database_Class data:mainCardList) {
            if(data.mCropNameValue.toLowerCase().contains(s.toLowerCase())){
                temp.add(data);
                Log.d("Tag",data.mCropNameValue +" : "+s);
            }
        }
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        data_adapter = new BuyerSearch_adapter(temp);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(data_adapter);
        recyclerView.setLayoutManager(layoutManager);

    }
}




