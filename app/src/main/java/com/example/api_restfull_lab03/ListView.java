package com.example.api_restfull_lab03;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.api_restfull_lab03.WebServices.Asynchtask;
import com.example.api_restfull_lab03.WebServices.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListView extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AutoCompleteTextView aucomplistlug = findViewById(R.id.aucomplistlug);
        aucomplistlug.setOnItemClickListener((parent, view, position, id) -> {
            Map<String, String> datos = new HashMap<String, String>();
            WebService ws= new WebService(
                    "https://turismo.quevedoenlinea.gob.ec/lugar_turistico/json_getlistadoGridLT/" + (position+1),
                    datos, ListView.this, ListView.this);
            ws.execute("GET");
                });



    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtResp = findViewById(R.id.txtresp);
        String lstLista="";
        JSONObject resultados = new JSONObject(result);
        JSONArray JSONlista = resultados.getJSONArray("data");
        for(int i=0; i< JSONlista.length();i++){
            JSONObject banco= JSONlista.getJSONObject(i);
            lstLista = lstLista + i + ".- " +
                    banco.getString("nombre_lugar").toString()
                    + " - " +
                    banco.getString("categoria").toString() + "\n" ;
        }
        txtResp.setText(lstLista);
    }
}