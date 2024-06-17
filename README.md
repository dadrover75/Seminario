para probar
instalar base de datos pto 3306
correr la aplicacion
[ probar cliente en
  https://testclient-cloud.mqtt.cool/
  conexion a tcp://broker.hivemq.com:1883
  publicar en el topico sensor/humidity/1 al 5 un valor por ejemplo de 10 si queremos ver que se prenda la bomba
  ver en la ventana de la aplicacion como cambia el estado de la bomba y actualiza lectura de humedad ]

  #**ACTUALIZACION 16/6**
  
  Se creo un script llamado TestDispositivos que genera mensajes con humedad ramdon a intervalos de 5 seg en cada dispositivo, para simular las lecturas.
  En este momento para probar solo se necesita generar la base de datos con el script sql de la carpeta resources, en una conexion localhost:3306

  #**NOTA PARA CAROLINA**

Para prescindir de crear la base de datos localmente se puede usar para pruebas una conexion en la nube cuya configuracion se encuentra comentada en la clase de connexion, para poder elegir cual usar. La unica contemplacion es que debido a las demoras en conectarse, la pantalla de vista tarda unos segundos hasta aparecer, y la actualizacion de los datos, tiende a dar errores por las tardanzas en actualizar los datos en la base de datos de la bomba de riego, generando conflictos con los mensajes mqtt, debido al qos. En una instalacion en ambiente real, son ajustes en las configuraciones que habria que proyectar hacer.
