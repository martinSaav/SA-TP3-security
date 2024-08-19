![logo-sistemasactivos](./docs/imgs/sa.png)
# Documento de definición de arquitectura

## 1. Pautas para la entrega del trabajo práctico final

### a) Se entrega:
#### a.	Proyecto completo de Netbeans comprimido en .zip (incluye Thymeleaf, WebClient, dos microservicios y el Actuator).
#### b.	Archivos compilados .jar y .war para ejecutar.
#### c.	Proyecto Postman con los servicios configurados para probar funcionamiento del BFF.
####    La entrega debe estar en una carpeta llamada nombreAlumno_sistemasactivos.zip y luego debe ser cargada a la tarea asignada en Classroom.
### b) El plazo de entrega es hasta el domingo 3/03/24 23:59 hrs.
### c) Los microservicios deben ser construidos en Java Springboot.
### d) Se debe crear el diagrama de arquitectura de la solución marcada en el punto 2).
### e) Las peticiones tienen que ser bajo dominio https (archivo correspondiente debe estar en cada proyecto)
### f) Los esquemas de la bdd deben ser: ms1 para el ms con alguna información ya sea account u otro y ms_security para el ms de seguridad.
### g) La solución debe ser gestionada con Maven.
### h) El producto debe ser entregado para un entorno productivo.
### i) La API debe ser del tipo RESTful estándar OPEN API OAS2 o 3.
### j) Deberá usar la especificación JPA en Hibernate para la integración con la capa de datos desde la solución SpringBoot (por rendimiento también se permite utilización de JDBC para algún método específico).
### k) Se deberá usar la versión de Java 17, SpringBoot 3.0.2 o 3.0.3 y Spring Framework 6.0.5.

## 2. Diagrama de arquitectura de la solución
![diagrama-secuencia](./docs/diagram/diagrama_secuencia.png)

## Descripción de flujo:

#### El proceso inicia cuando una solicitud de inicio de sesión es enviada desde el frontend hacia el BFF (Backend For Frontend). El BFF canaliza esta solicitud hacia el microservicio de seguridad, el cual se encarga de validar la información de inicio de sesión y emitir un token de acceso junto con el rol correspondiente. Este token con su respectivo rol es almacenado en la caché del BFF para su posterior uso.
#### Una vez completado este flujo, el frontend tiene la capacidad de enviar solicitudesadicionales hacia los recursos del microservicio deseado, incluyendo el token obtenidocomo parte de la autenticación.
#### Es responsabilidad del BFF verificar la validez del token, caducidad, así también asegurarse de que el rol asociado al mismo sea válido.
#### <strong>NOTA</strong>: Destacar que el register visto en la clase del 27/02 no está en el esquema, el mismo debe estar implementado
