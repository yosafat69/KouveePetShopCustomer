package com.example.kouveepetshopcustomer.ui.layanan;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kouveepetshopcustomer.API.Rest_API;
import com.example.kouveepetshopcustomer.MainActivity;
import com.example.kouveepetshopcustomer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LayananFragment extends Fragment {

    private Spinner spinner;
    private String[] sort = {"Nama (A-Z)", "Nama (Z-A)", "Harga Terendah", "Harga Tertinggi"};
    private LayananAdapter mAdapter;
    private ArrayList<LayananDAO> mItems;
    private String ip = MainActivity.getIp();
    private String url = MainActivity.getUrl();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_layanan, container, false);

        RecyclerView mRecyclerView = root.findViewById(R.id.recyclerView_layanan);
        RecyclerView.LayoutManager mManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mManager);
        mItems = new ArrayList<>();
        mAdapter = new LayananAdapter(root.getContext(), mItems);
        mRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(),2));
        mRecyclerView.setAdapter(mAdapter);
        spinner = root.findViewById(R.id.sorting);
        ArrayAdapter mArrayAdapter = new ArrayAdapter(root.getContext(),R.layout.spinner_item,sort);
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(mArrayAdapter);

        EditText cari = root.findViewById(R.id.cari);

        cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.getFilter().filter(s);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (String.valueOf(spinner.getSelectedItem()).equals("Nama (A-Z)")){
                    getData(root.getContext(),"nama","FALSE");
                }
                else if (String.valueOf(spinner.getSelectedItem()).equals("Nama (Z-A)")){
                    getData(root.getContext(),"nama","TRUE");
                }
                else if (String.valueOf(spinner.getSelectedItem()).equals("Harga Terendah")){
                    getData(root.getContext(),"harga","FALSE");
                }
                else if (String.valueOf(spinner.getSelectedItem()).equals("Harga Tertinggi")){
                    getData(root.getContext(),"harga","TRUE");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }



    private void getData(final Context context, String sort, String isDesc) {
        mItems.clear();
        String url = ip + this.url + "index.php/Customer/layanan?order_by="+sort+"&isDesc="+isDesc;

        JsonObjectRequest arrayRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("volley", "response : " + response.toString());

                try {
                    JSONArray massage = response.getJSONArray("message");
                    for (int i = 0; i < massage.length() ; i++){
                        JSONObject massageDetail = massage.getJSONObject(i);
                        LayananDAO layanan = new LayananDAO();
                        layanan.setNama(massageDetail.getString("nama"));
                        layanan.setHarga(massageDetail.getInt("harga"));
                        layanan.setLink_gambar(massageDetail.getString("url_gambar"));
                        mItems.add(layanan);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volley", "error : " + error.getMessage());
            }
        });
        Rest_API.getInstance(context).addToRequestQueue(arrayRequest);
    }
}
