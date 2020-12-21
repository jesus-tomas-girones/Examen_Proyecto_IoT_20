package com.example.jtomas.examen_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class MainActivity extends AppCompatActivity implements MqttCallback {

   public static final String TAG = "EXAMEN";
   public static final String topicRoot = "examen/";
   public static final int qos = 1;
   public static final String broker = "tcp://mqtt.eclipseprojects.io:1883";
   public static final String clientId = "Test13459876";

   private MqttClient client;
   private Adaptador adaptador;

   // 1. JAVA
   static long PROGRAMACION[] = {6, 10, 12, 15, 17, 20, 22, 23};
   static String TIPO[] = {"NOTICIAS", "MAGAZINE", "MUSICA", "NOTICIAS",
           "MAGAZINE", "NOTICIAS", "MAGAZINE", "MUSICA"};

   List<Programa> obtenerProgramas(long[] horas, String[] tipos) {
      List<Programa> lista = new ArrayList<>();
      for (int n = 0; n < TIPO.length; n++) {
         long hFin;
         if (n < TIPO.length - 1) hFin = horas[n + 1];
         else hFin = horas[0];
         lista.add(new Programa("programa " + (n + 1), horas[n], hFin, tipos[n],
                 "http://mmoviles.upv.es/img.png"));
      }
      return lista;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      // 1. JAVA
      List<Programa> programas = obtenerProgramas(PROGRAMACION, TIPO);
      Log.e(TAG, programas.toString());

      Set<String> tipos = new HashSet<>();
      for (Programa programa : programas) {
         tipos.add(programa.getTipo());
      }

      // 2.	ALGORITMO
      Map<String, String> mapa = new HashMap<>();
      for (String tipo : tipos) {
         long masLargo = 0;
         String nombreMasLargo = "";
         for (Programa p : programas) {
            if (tipo.equals(p.getTipo())) {
               long duracion;
               if (p.gethFinal() >= p.gethInicio())
                  duracion = p.gethFinal() - p.gethInicio();
               else duracion = 24 - p.gethFinal() //duración hasta el final del día
                               + p.gethInicio();  //duración al principio del siguiente día
               if (masLargo < duracion) {
                  masLargo = duracion;
                  nombreMasLargo = p.getNombre();
               }
            }
         }
         mapa.put(tipo, nombreMasLargo);
      }
      Log.e(TAG, mapa.toString());

      // 4.	RECICLERVIEW
      RecyclerView recyclerView = findViewById(R.id.recyclerView);
      Query query = FirebaseFirestore.getInstance()
              .collection("programas")
              .whereEqualTo("tipo", "MUSICA") // 6. CONSULTA
              .orderBy("nombre")
              .limit(5);
      FirestoreRecyclerOptions<Programa> opciones = new FirestoreRecyclerOptions
              .Builder<Programa>().setQuery(query, Programa.class).build();
      adaptador = new Adaptador(this, opciones);
      recyclerView.setAdapter(adaptador);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));

      // 7.	MQTT
      try {
         Log.i(TAG, "Conectando al broker " + broker);
         client = new MqttClient(broker, clientId, new MemoryPersistence());
         MqttConnectOptions connOpts = new MqttConnectOptions();
         connOpts.setCleanSession(true);
         connOpts.setKeepAliveInterval(60);
         connOpts.setWill(topicRoot + "WillTopic", "App desconectada".getBytes(),
                 qos, false);
         client.connect(connOpts);
      } catch (MqttException e) {
         Log.e(TAG, "Error al conectar.", e);
      }
      try {
         Log.i(TAG, "Suscrito a " + "examen/a");
         client.subscribe("examen/a");
         client.setCallback(this);
      } catch (MqttException e) {
         Log.e(TAG, "Error al suscribir.", e);
      }
   }

   @Override
   public void messageArrived(String topic, MqttMessage message) throws Exception {
      String payload = new String(message.getPayload());
      Log.d(TAG, "Recibiendo: " + topic + "->" + payload);
      TextView textView = findViewById(R.id.editText);
      textView.setText(payload);

   }

   @Override
   public void connectionLost(Throwable cause) {
      Log.d(TAG, "Conexión perdida");
   }

   @Override
   public void deliveryComplete(IMqttDeliveryToken token) {
      Log.d(TAG, "Entrega completa");
   }

   @Override
   public void onDestroy() {
      try {
         Log.i(TAG, "Desconectado");
         client.disconnect();
      } catch (MqttException e) {
         Log.e(TAG, "Error al desconectar.", e);
      }
      super.onDestroy();
   }

   @Override
   public void onStart() {
      super.onStart();
      adaptador.startListening();
   }

   @Override
   public void onStop() {
      super.onStop();
      adaptador.stopListening();
   }

   // 3. ESCRITURA DE DATOS:
   public void escribir(android.view.View view) {
      Intent i = new Intent(Intent.ACTION_PICK);
      i.setType("image/*");
      startActivityForResult(i, 1234);
   }

   @Override
   protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == Activity.RESULT_OK) {
         if (requestCode == 1234) {
            String nombreFichero = UUID.randomUUID().toString();
            subirFichero(data.getData(), "programas/" + nombreFichero);
         }
      }

   }

   // 5. ALMACENAMIENTO
   private void subirFichero(Uri fichero, String referencia) {
      final StorageReference ref = FirebaseStorage.getInstance().getReference().child(referencia);
      UploadTask uploadTask = ref.putFile(fichero);
      Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
         @Override
         public Task<Uri> then(@NonNull
                                       Task<UploadTask.TaskSnapshot> task) throws Exception {
            if (!task.isSuccessful())
               throw task.getException();
            return ref.getDownloadUrl();
         }
      }).addOnCompleteListener(new OnCompleteListener<Uri>() {
         @Override
         public void onComplete(@NonNull Task<Uri> task) {
            if (task.isSuccessful()) {
               Uri downloadUri = task.getResult();
               Log.e("Almacenamiento", "URL: " + downloadUri.toString());
               registrarPrograma(downloadUri.toString());
            } else {
               Log.e("Almacenamiento", "ERROR: subiendo fichero");
            }
         }
      });
   }

   private void registrarPrograma(String url) {
      TextView textView = findViewById(R.id.editText);
      Programa programa = new Programa(textView.getText().toString(), currentTimeMillis(),
              currentTimeMillis() + 1000 * 60 * 60, "MUSICA", url);
      FirebaseFirestore db = FirebaseFirestore.getInstance();
      CollectionReference programas = db.collection("programas");
      programas.add(programa);
   }
}