package com.example.jtomas.examen_a;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adaptador extends
        FirestoreRecyclerAdapter<Programa, Adaptador.ViewHolder> {

   private Context context;
   protected View.OnClickListener onClickListener;

   public Adaptador(Context context,
                            @NonNull FirestoreRecyclerOptions<Programa> options) {
      super(options);
      this.context = context.getApplicationContext();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      public final ImageView imagen;
      public final TextView titulo;
      public final TextView tiempo;
      public ViewHolder(View itemView) {
         super(itemView);
         this.imagen = (ImageView) itemView.findViewById(R.id.imageView1);
         this.titulo = (TextView) itemView.findViewById(R.id.textView1);
         this.tiempo = (TextView) itemView.findViewById(R.id.textView2);
      }
   }

   @Override public Adaptador.ViewHolder onCreateViewHolder(
           ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
              .inflate(R.layout.elemento_lista, parent, false);
      return new Adaptador.ViewHolder(view);
   }

   @Override protected void onBindViewHolder(@NonNull Adaptador
           .ViewHolder holder, int position, @NonNull Programa imagen) {
      holder.titulo.setText(imagen.getNombre());
      CharSequence prettyTime = DateUtils.getRelativeDateTimeString(
              context, imagen.gethInicio(), DateUtils.SECOND_IN_MILLIS,
              DateUtils.WEEK_IN_MILLIS, 0);
      holder.tiempo.setText(prettyTime);
      Glide.with(context)
              .load(imagen.getFoto())
              .placeholder(R.drawable.ic_launcher_foreground)
              .into(holder.imagen);
      holder.itemView.setOnClickListener(onClickListener);
   }

   public void setOnItemClickListener(View.OnClickListener onClick) {
      onClickListener = onClick;
   }
}
