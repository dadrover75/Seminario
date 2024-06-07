/*
INSERT INTO `configuraciones` VALUES (1,1,30,80,40),(2,2,20,75,35),(3,3,25,85,45),(4,1,30,80,40),(5,2,20,75,35),(6,3,25,85,45);
INSERT INTO `cultivos` VALUES (1,'Blue One',1),(2,'Roxy Blue',2),(3,'Calipso',3),(4,'Blue One',1),(5,'Roxy Blue',2),(6,'Calipso',3);
INSERT INTO `cultivos_dispositivos` VALUES (1,7),(1,8),(2,10),(3,11),(3,12),(4,13),(1,14),(3,15);
INSERT INTO mensajes (topic, payload) VALUES
('/sensor/humidity/1', '75'),
('/sensor/humidity/2', '55'),
('/actuator/waterpump/1', 'on'),
('/sensor/humidity/3', '60'),
('/sensor/humidity/4', '70'),
('/sensor/humidity/5', '65'),
('/actuator/waterpump/2', 'on'),
('/actuator/waterpump/3', 'off'),
('/sensor/humidity/6', '60');
INSERT INTO dispositivos (mensaje_id, estado) VALUES
(7, 0),
(8, 0),
(9, 1),
(10, 0),
(11, 0),
(12, 0),
(13, 1),
(14, 0),
(15, 0);
INSERT INTO `notificaciones` VALUES (1,'info','Inicio de riego para Blue One',NULL),(2,'info','Fin de riego para Blue One',NULL),(3,'warning','Sensor de humedad 2 fuera de línea',NULL),(4,'info','Bomba de agua 1 activada',NULL);
INSERT INTO `usuarios` VALUES (1,'Juan Perez','Administrador'),(2,'Maria Lopez','Usuario'),(3,'Carlos Gonzalez','Usuario'),(4,'Juan Perez','Administrador'),(5,'Maria Lopez','Usuario'),(6,'Carlos Gonzalez','Usuario');
*/
-- VERIFICACION DE LOS DATOS INSERTADOS
SELECT * FROM usuarios;
SELECT * FROM configuraciones;
SELECT * FROM cultivos;
SELECT * FROM dispositivos;
SELECT * FROM cultivos_dispositivos;
SELECT * FROM notificaciones;
SELECT * FROM mensajes;

-- CONSULTA CULTIVOS Y TODOS LOS DISPOSITIVOS ORDENADOS POR CULTIVO
SELECT 
    c.idcultivos,
    c.descripcion,
    conf.minutos_riego,
    conf.humedad_max,
    conf.humedad_min,
    m.topic,
    m.payload,
    d.estado
FROM 
    cultivos c
    JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones
    LEFT JOIN cultivos_dispositivos cd ON c.idcultivos = cd.idcultivos
    LEFT JOIN dispositivos d ON cd.iddispositivos = d.iddispositivos
    LEFT JOIN mensajes m ON d.mensaje_id = m.idmensajes
ORDER BY c.idcultivos;

-- CONSULTA PARA RECUPERAR TODOS LOS DISPOSITIVOS DE UN MISMO CULTIVO
SELECT 
    c.idcultivos,
    c.descripcion AS cultivo_descripcion,
    conf.minutos_riego,
    conf.humedad_max,
    conf.humedad_min,
    d.iddispositivos,
    m.topic AS mensaje_topic,
    m.payload AS mensaje_payload,
    d.estado AS dispositivo_estado
FROM 
    cultivos c
    JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones
    LEFT JOIN cultivos_dispositivos cd ON c.idcultivos = cd.idcultivos
    LEFT JOIN dispositivos d ON cd.iddispositivos = d.iddispositivos
    LEFT JOIN mensajes m ON d.mensaje_id = m.idmensajes
WHERE 
    c.idcultivos = 1;

-- CONSULTA EL VALOR ASIGNADO A LA CONFIGURACION DE UN CULTIVO
SELECT 
    conf.humedad_min
FROM 
    cultivos c
    JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones
WHERE 
    c.idcultivos = 2;
