para probar
instalar base de datos pto 3306
correr la aplicacion
[ probar cliente en
  https://testclient-cloud.mqtt.cool/
  conexion a tcp://broker.hivemq.com:1883
  publicar en el topico sensor/humidity/1 al 5 un valor por ejemplo de 10 si queremos ver que se prenda la bomba
  ver en la ventana de la aplicacion como cambia el estado de la bomba y actualiza lectura de humedad ]

  **ACTUALIZACION 16/6**
  Se creo un script llamado TestDispositivos que genera mensajes con humedad ramdon a intervalos de 5 seg en 
  cada dispositivo, para simular las lecturas.
  En este momento para probar solo se necesita generar la base de datos con el 
  script sql de la carpeta resources, en una conexion localhost:3306
