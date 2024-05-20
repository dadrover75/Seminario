
-- VERIVICACION DE LOS DATOS INSERTADOS
SELECT * FROM usuarios;
SELECT * FROM configuraciones;
SELECT * FROM cultivos;
SELECT * FROM dispositivos;
SELECT * FROM cultivos_dispositivos;
SELECT * FROM notificaciones;

-- CONSULTA CULTIVOS Y TODOS LOS DISPOSITIVOS ORDENADOS POR CULTIVO
SELECT 
    c.idcultivos,
    c.descripcion,
    conf.minutos_riego,
    conf.humedad_max,
    conf.humedad_min,
    d.message,
    d.estado
FROM 
    cultivos c
    JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones
    LEFT JOIN cultivos_dispositivos cd ON c.idcultivos = cd.idcultivos
    LEFT JOIN dispositivos d ON cd.iddispositivos = d.iddispositivos
WHERE 
    c.idcultivos = cd.idcultivos
    and 
    cd.iddispositivos = d.iddispositivos
ORDER BY c.idcultivos;

-- CONSULTA PARA RECUPERAR TODOS LOS DISPOSITIVOS DE UN MISMO CULTIVP
SELECT 
    c.idcultivos,
    c.descripcion as cultivo_descripcion,
    conf.minutos_riego,
    conf.humedad_max,
    conf.humedad_min,
    d.iddispositivos,
    d.message as dispositivo_mensaje,
    d.estado as dispositivo_estado
FROM 
    cultivos c
    JOIN configuraciones conf ON c.idconfiguraciones = conf.idconfiguraciones
    LEFT JOIN cultivos_dispositivos cd ON c.idcultivos = cd.idcultivos
    LEFT JOIN dispositivos d ON cd.iddispositivos = d.iddispositivos
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
