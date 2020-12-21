package com.example.jtomas.examen_a;

public class Programa {
   private String nombre;
   private long hInicio;
   private long hFinal;
   private String tipo;
   private String foto;

   public Programa() {
   }

   public Programa(String nombre, long hInicio, long hFinal, String tipo, String foto) {
      this.nombre = nombre;
      this.hInicio = hInicio;
      this.hFinal = hFinal;
      this.tipo = tipo;
      this.foto = foto;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public long gethInicio() {
      return hInicio;
   }

   public void sethInicio(long hInicio) {
      this.hInicio = hInicio;
   }

   public long gethFinal() {
      return hFinal;
   }

   public void sethFinal(long hFinal) {
      this.hFinal = hFinal;
   }

   public String getTipo() {
      return tipo;
   }

   public void setTipo(String tipo) {
      this.tipo = tipo;
   }

   public String getFoto() {
      return foto;
   }

   public void setFoto(String foto) {
      this.foto = foto;
   }

   @Override
   public String toString() {
      return "Programa{" +
              "nombre='" + nombre + '\'' +
              ", hInicio=" + hInicio +
              ", hFinal=" + hFinal +
              ", tipo='" + tipo + '\'' +
              ", foto='" + foto + '\'' +
              '}';
   }
}
