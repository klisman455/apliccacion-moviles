package com.example.apliactionbase1moviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.chromium.base.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity2 extends AppCompatActivity {
FirebaseFirestore db;
TextView tvId;
EditText etMostrarNombre,etMostrarPrecio;
String nombre, precio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        db= FirebaseFirestore.getInstance();
        tvId= findViewById(R.id.etId);

        etMostrarNombre = findViewById(R.id.etNombre);
        etMostrarPrecio = findViewById(R.id.etprecio);

    }

    public void eliminar(View view) {
        String id= tvId.getText().toString();
        db.collection("productos")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("Exito","dccument successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Error","Error deleting document",e);
                    }
                });
    }

    public void cargar(View view) {
        String id= tvId.getText().toString();

        db.collection("productos")
                .document(id)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        nombre = documentSnapshot.getData().get("nombre").toString();
                        precio = documentSnapshot.getData().get("precio").toString();
                        etMostrarNombre.setText(nombre);
                        etMostrarPrecio.setText(precio);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("app","error al traer document",e);
                    }
                });
    }

    public void actualizar(View view) {
     String id= tvId.getText().toString();
     String nombre =etMostrarNombre.getText().toString();
  int precioInt =Integer.parseInt(etMostrarPrecio.getText().toString());

        Map<String, Object> producto = new HashMap<>();
        producto.put("nombre",nombre);
        producto.put("precio",precioInt);

        db.collection("productos")
                .document(id)
                .set(producto)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.w("Exito","todo bien");
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Error","todo mal",e);
            }
        })
        ;


    }
}