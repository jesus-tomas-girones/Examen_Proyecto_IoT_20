
# Examen_Proyecto_IoT_20
Examen Final de Practico (A)

Alumno: ____________________ Hora inicio: ______ entrega: ________   Fecha: 21/12/2020

ADVERTENCIA: Puede consultarse cualquier tipo de información propia o disponible a través de Internet. No obstante, queda terminantemente prohibido la comunicación con cualquier persona durante la realización del examen. El envío o recepción de correos electrónicos, WhatsApp, compartición de ficheros, uso de redes sociales, etc. supondrá el suspenso automático de la asignatura.

DESCRIPCIÓN: Dispones de un máximo de dos horas para realizar el mayor número de pasos y entregar el resultado en una tarea de Poliformat. Ver instrucciones de entrega al final: 

0.	Crea un nuevo proyecto con los datos … (reemplaza usuario_upv por tu usuario de correo): 

Project: Phone and Tablet / Empty Activity

Name: Examen A

Package name: com.example.usuario_upv.examentipo_a

Minimum API level: 19 Android 4.4 (KitKat)

1.	JAVA: NOTA: Ha de funcionar, aunque se cambie los array de entrada. 

  a) Añade las siguientes constantes en la clase MainActivity. La lista PROGRAMACION representa la programación diaria de una emisora de radio. El primer programa se emite de 6 a 10, el siguiente de 10 a 12 y así sucesivamente hasta el último programa, que se emite de las 23 de la noche a las 6 de la mañana. La lista TIPO representa los tipos de programas que se emiten en la radio.
static long PROGRAMACION[]={6, 10, 12, 15, 17, 20, 22, 23 };
static String TIPO[] = {"NOTICIAS", "MAGAZINE", "MUSICA", "NOTICIAS",
        "MAGAZINE", "NOTICIAS", "MAGAZINE", "MUSICA"};
  b) Crea una clase POJO con las siguientes propiedades: nombre del programa, hora inicio, hora final, tipo y url a foto de carátula. Almacena las horas en una variable de tipo long. Crea el método toString(). (5 min)

  c) Crea un método en MainActivity que reciba como parámetro la dos listas que hemos creado. El método debe crear tantos objetos de la clase POJO, como elementos haya en la lista. El nombre ha de ser “programa 1” para el primer elemento, “programa 2“ para el segundo, y así sucesivamente. La URL a la foto ha de ser http://mmoviles.upv.es/img.png  para todos los objetos. Por ejemplo, el primer programa tiene por nombre “programa 1”, se inicia a las 6, finaliza a las 10 y es de tipo “NOTICIAS”. El segundo programa tiene por nombre “programa 2”, se inicia a las 10, finaliza a las 12 y es de tipo “MAGAZINE”, y así sucesivamente hasta el último programa, que por nombre “programa 8”, se inicia a las 23, finaliza a las 6 y es de tipo “MUSICA”. El método devolverá una lista con los 8 objetos creados. Muestra el resultado en el LogCat. (10 min)

2.	ALGORITMO: Crea una variable Map<> donde, para cada tipo de programa, se calcule el nombre del programa más largo. NOTA: Recuerda que un día tiene 24 horas. Has de tenerlo en cuenta para calcular la duración del último programa del día. En el ejemplo, es de 7 horas, de 23 a 24 y de 0 a 6. Muestra esa variable en el LogCat. (20-30 min)
CONEXIÓN A FIREBASE: Conecta la aplicación creada a un proyecto Firebase. Puedes crear un nuevo proyecto o conectar la aplicación a un proyecto previamente creado. (5 min)

3.	ESCRITURA DE DATOS: Añade un EditText y un Button en la parte superior del layout principal. Al pulsar el botón almacena en Firestore un nuevo documento. Los datos a guardar corresponden con el POJO del punto 1. El nombre del programa será el valor introducido en el EditText. El inicio será el tiempo actual medido en milisegundos (currentTimeMillis()).  El final será el tiempo inicial más una hora. El tipo será ‘MUSICA’. La URL de la foto será siempre http://mmoviles.upv.es/img.png.   (15 min)

4.	RECICLERVIEW: Añade un RecyclerView en la parte inferior del layout principal. En el RecyclerView se mostrará la información creada en el punto anterior. En concreto la foto, el nombre del programa y la fecha de inicio. (20 min)

NOTA: Desde Android 9 no se permite el uso del protocolo http. En su lugar hay que utilizar https. Para poder acceder a la URL de la foto añade en el manifiesto:

<application
    android:usesCleartextTraffic="true" …
  
5.	ALMACENAMIENTO: Modifica el proceso de escritura de datos para que al pulsar en el botón se pida al usuario una foto de la galería. La foto será almacenada en Firestore y se añadirá a la base de datos la URL a la foto.  (20 min)

6.	CONSULTA: Haz que en el RecyclerView anterior se muestren solo los programas de tipo ‘MUSICA’ ordenados por nombre del programa y con un máximo de 4. Añade un nuevo documento de forma que no se muestre en el listado.  (10 min)

7.	MQTT: Conéctate al bróker MQTT mqtt.eclipseprojects.io y Suscríbete al token examen/a. Cada vez que este token cambie su valor se mostrará en el interior del EditText de la parte superior.  (15 min)

Entrega de la práctica: ¡IMPORTANTE! Imprescindible realizar todos los pasos

8.	Verifica que todo funciona correctamente antes de realizar el siguiente punto.

9.	Selecciona la opción Build / Build APK. Así generamos de nuevo el apk. 

10.	Selecciona la opción File / Export to Zip file...  ó File / Manage IDE Settings / Export to Zip file… para guardar el código del proyecto. 

11.	Para entregar usa la opción Tareas del menú de poli[Format]. Adjunta los ficheros zip y apk.

12.	Para facilitar la corrección en el cuadro de texto, que encontrarás al entrar en la entrega de la tarea, indica los ejercicios realizados de la siguiente lista introduciendo una X entre los paréntesis. Añade los comentarios oportunos:

 (_)  1.- JAVA 

 (_)	2.- ALGORITMO
 
 (_)	3.- ESCRITURA DE DATOS
 
 (_)  4.- RECICLERVIEW
 
 (_)  5.- ALMACENAMIENTO
 
 (_)  6.- CONSULTA
 
 (_)  7.- MQTT

Pega a continuación el fragmento de código realizado en el punto 2 ALGORITMO.

13.	Ejecuta el proyecto en un terminal o emulador y abre la consola de Firebase. Avisa al profesor para que lo evalúe.
