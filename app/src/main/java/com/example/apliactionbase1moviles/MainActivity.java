package com.example.apliactionbase1moviles;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView tvMostrar;
    EditText etProducto, etPrecio;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMostrar = findViewById(R.id.tvMostrar);
        etProducto = findViewById(R.id.etProducto);
        etPrecio = findViewById(R.id.etPrecio);

        tvMostrar.setText("");

        db = FirebaseFirestore.getInstance();

        db.collection("productos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot producto: Objects.requireNonNull(task.getResult())) {
                            tvMostrar.append(
                                    "id: "+producto.getId()+"\n"+
                                    "producto"+producto.getData().get("nombre") + "\n" +
                                    "precio" + producto.getData().get("precio") + "\n\n");
                        }

                    } else {
                        Log.w("App", "error de documentos", task.getException());
                    }
                });
    }


    public void guardar(View view) {
        String nombre = etProducto.getText().toString();
        int precio =Integer.parseInt(etPrecio.getText().toString());

        Map<String, Object> producto = new HashMap<>();
        producto.put("nombre", nombre);
        producto.put("precio", precio);

        db.collection("productos")
                .add(producto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Guardado con id"+documentReference.getId(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error", "Error adding document", e);
                    }
                });

    }

    public void siguiente(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }
}


