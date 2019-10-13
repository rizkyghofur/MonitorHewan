package com.rizkyghofur.monitorhewan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private ArrayList<DatabaseHelper> listData;
    SwipeRefreshLayout mSwipeRefreshLayout;

    JSONParser jParser = new JSONParser();
    String url_all_products = "http://ghwibowo.net/monitoring_hewan/get_hewan.php";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATA = "data";
    private static final String TAG_DATA_TERBARU = "data_terbaru";

    private static final String TAG_ID_HEWAN = "id_hewan";
    private static final String TAG_TANGGAL = "tanggal";
    private static final String TAG_TIME = "time";
    private static final String TAG_LAT = "lat";
    private static final String TAG_LOG = "log";
    private static final String TAG_SUHU = "suhu";
    private static final String TAG_DETAK_JANTUNG = "detak_jantung";


    // products JSONArray
    JSONArray products = null;
    ArrayList<HashMap<String, String>> data_hewan;
    ArrayList<HashMap<String, String>> data_hewan_terbaru = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        recyclerView = findViewById(R.id.datalist);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                    new get_data_hewan();
                }
            },1000);
        }
    });

        MyRecyclerView();
//        GetData();
        data_hewan = new ArrayList<>();
        listData = new ArrayList<>();
        new get_data_hewan().execute();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

//        runOnUiThread(new Runnable() {
//            public void run() {
//                /**
//                 * Updating parsed JSON data into ListView
//                 * */
//
//                adapter = new RecyclerViewAdapter(listData);
//                recyclerView.setAdapter(adapter);
//
//            }
//        });
    }

    private void GetData(){
        Toast.makeText(getApplicationContext(),"Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("monitorhewan")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        listData = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            DatabaseHelper data = snapshot.getValue(DatabaseHelper.class);
                            listData.add(data);
                        }
                        adapter = new RecyclerViewAdapter(listData);
                        recyclerView.setAdapter(adapter);
                        Toast.makeText(getApplicationContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }

                });
    }

    public boolean doubleTap = false;
    @Override
    public void onBackPressed(){
        if(doubleTap){
            super.onBackPressed();
            return;
        }

        this.doubleTap = true;
        Toast.makeText(this, "Ketuk kembali 2 kali untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleTap = false;
            }
        }, 2000);
    }

    //Methode yang berisi kumpulan baris kode untuk mengatur RecyclerView
    private void MyRecyclerView(){
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        //Membuat Underline pada Setiap Item Didalam List
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    private class get_data_hewan extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MonitorActivity.this, "Sedang mengunduh data...", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> params = new ArrayList<>();

            JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
            DatabaseHelper data_helper = null;
            listData = new ArrayList<>();

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
                int jumlah_hewan = Integer.valueOf(json.getString("jumlah_hewan"));

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    products = json.getJSONArray(TAG_DATA);

                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);

                        // Storing each json item in variable
                        String id_hewan = c.getString(TAG_ID_HEWAN);
                        String tanggal = c.getString(TAG_TANGGAL);
                        String time = c.getString(TAG_TIME);
                        String suhu = c.getString(TAG_SUHU);
                        String detak_jantung = c.getString(TAG_DETAK_JANTUNG);
                        String lat = c.getString(TAG_LAT);
                        String log = c.getString(TAG_LOG);


                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID_HEWAN, id_hewan);
                        map.put(TAG_TANGGAL, tanggal);
                        map.put(TAG_TIME, time);
                        map.put(TAG_SUHU, suhu);
                        map.put(TAG_DETAK_JANTUNG, detak_jantung);
                        map.put(TAG_LAT, lat);
                        map.put(TAG_LOG, log);

                        if (i < jumlah_hewan) {
                            // adding HashList to ArrayList
//                            data_hewan_terbaru.add(map);
                            data_helper = new DatabaseHelper();
                            data_helper.id_hewan = Integer.valueOf(id_hewan);
                            data_helper.tanggal = tanggal;
                            data_helper.time = time;
                            data_helper.suhu = Double.valueOf(suhu);
                            data_helper.detakjantung = Integer.valueOf(detak_jantung);
                            data_helper.lat = Double.valueOf(lat);
                            data_helper.log = Double.valueOf(log);

                            listData.add(data_helper);
                        } else {
                            data_hewan.add(map);
                        }
                    }

                    products = json.getJSONArray(TAG_DATA);

                } else {
                    Toast.makeText(MonitorActivity.this, "T-T", Toast.LENGTH_LONG).show();
                    // no products found
                    // Launch Add New product Activity
//                    Intent i = new Intent(getApplicationContext(),
//                            NewProductActivity.class);
//                    // Closing all previous activities
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */

//                    listData = new ArrayList<>();


//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                        DatabaseHelper data = snapshot.getValue(DatabaseHelper.class);
//                        listData.add(data);
//                    }
                    adapter = new RecyclerViewAdapter(listData);
                    recyclerView.setAdapter(adapter);

                }
            });
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
//            pDialog.dismiss();
//            // updating UI from Background Thread


        }

    }

}