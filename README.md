# TALLER DE TRABAJO INDIVIDUAL EN PATRONES ARQUITECTURALES
## Descripción del Programa

El programa es una aplicación de registro de mensajes en un chat implementada utilizando Spark Java y MongoDB como base de datos. Consta de varias partes funcionales que incluyen un servicio de registro de mensajes (LogService), una capa de acceso a datos (MessageDAO), una utilidad para interactuar con la base de datos MongoDB (MongoUtil), y un balanceador de carga de tipo Round Robin (Weblogroundrobin).

## Prerrequisitos

Necesitarás tener instaladas las siguientes herramientas y dependencias:

* **Java Development Kit (JDK) 17:** Asegúrate de tener instalado el JDK 17 en tu sistema. Puedes descargarlo e instalarlo desde el sitio web oficial de Oracle o utilizar una distribución OpenJDK.

* **Apache Maven:** Maven es una herramienta de gestión de proyectos que se utiliza para construir y gestionar proyectos Java. Asegúrate de tener instalado Maven en tu sistema. Puedes descargarlo e instalarlo desde el sitio web oficial de Apache Maven.

* **Git:** Git es un sistema de control de versiones distribuido que se utiliza para gestionar el código fuente del proyecto. Asegúrate de tener instalado Git en tu sistema si deseas clonar el repositorio del proyecto desde GitHub.

## Arquitectura de la Aplicación

![](/media/Arquitectura.png)

La arquitectura de la aplicación se compone de los siguientes componentes:
* **LogService:** Este componente es el núcleo de la aplicación y proporciona un servicio web para registrar mensajes en el chat. Utiliza Spark Java para manejar las solicitudes HTTP y comunica con la base de datos MongoDB a través de la capa de acceso a datos (MessageDAO).

* **MessageDAO:** Es la capa de acceso a datos de la aplicación que se encarga de interactuar con la base de datos MongoDB. Proporciona métodos para agregar, listar y eliminar mensajes en la base de datos.

* **MongoUtil:** Es una utilidad que facilita la conexión con la base de datos MongoDB. Proporciona un método para obtener una instancia de la base de datos MongoDB utilizando la biblioteca oficial de MongoDB para Java.

* **Weblogroundrobin:** Este componente actúa como un balanceador de carga de tipo Round Robin (APP-LB-RoundRobin). Distribuye las solicitudes entre varios servicios de registro de mensajes (LogServices) que se ejecutan en puertos diferentes (35001 al 35003). Cada LogService se comunica con la base de datos MongoDB a través del puerto 27017.

## Funcionamiento

<video width="640" height="360" controls>
  <source src="media/DesplieguesFuncionando.mp4" type="video/mp4">
  Your browser does not support the video tag.
</video>

1. Cuando un cliente realiza una solicitud HTTP al balanceador de carga (Weblogroundrobin), éste distribuye la solicitud a uno de los servicios de registro de mensajes (LogServices) disponibles utilizando el algoritmo Round Robin.

2. El servicio de registro de mensajes (LogService) recibe la solicitud y procesa el mensaje, que puede ser para registrar un nuevo mensaje en el chat o para listar mensajes existentes.

3. Si la solicitud es para registrar un nuevo mensaje, el servicio de registro de mensajes (LogService) utiliza la capa de acceso a datos (MessageDAO) para agregar el mensaje a la base de datos MongoDB.

4. Si la solicitud es para listar mensajes existentes, el servicio de registro de mensajes (LogService) utiliza la capa de acceso a datos (MessageDAO) para obtener los mensajes de la base de datos MongoDB y los devuelve al cliente.

5. La base de datos MongoDB almacena los mensajes registrados en el chat.

## Testing

1. **testSendMessageWithoutMessage()**
    - **Descripción:** Esta prueba verifica el comportamiento del método `sendMessage()` cuando se llama con un parámetro de mensaje nulo.
    - **Pasos de la prueba:**
        1. Llamar a `LogService.sendMessage(null)`.
        2. Verificar que la lista de mensajes devuelta no sea nula.
        3. Verificar que la lista de mensajes devuelta no esté vacía.

2. **testSendMessageWithEmptyMessage()**
    - **Descripción:** Esta prueba verifica el comportamiento del método `sendMessage()` cuando se llama con un parámetro de mensaje vacío.
    - **Pasos de la prueba:**
        1. Llamar a `LogService.sendMessage("")`.
        2. Verificar que la lista de mensajes devuelta no sea nula.
        3. Verificar que la lista de mensajes devuelta no esté vacía.

3. **testSendMessageWithNullStringMessage()**
    - **Descripción:** Esta prueba verifica el comportamiento del método `sendMessage()` cuando se llama con la cadena "null" como parámetro de mensaje.
    - **Pasos de la prueba:**
        1. Llamar a `LogService.sendMessage("null")`.
        2. Verificar que la lista de mensajes devuelta no sea nula.
        3. Verificar que la lista de mensajes devuelta no esté vacía.
## Ejecución de las pruebas

Puedes probar los test con el siguiente comando:

```
    mvn test
```

Se visualizará lo siguiente:

![](/media/test.png)

## ¿Cómo se realizó la instalación?
Se creó una instancia con Amazon EC2 para ejecutarla en la nube de AWS. Toda la configuración se dejó por defecto.
Se usó como sistema operativo el Amazon Linux

A la instancia le establecemos las siguientes reglas de entrada:

![](/media/reglasEntrada.png)

Nos conectamos a la máquina por medio del cliente SSH usando la clave privada.
Una vez hecho esto, ejecutamos los siguientes comandos en el orden respectivo:

```
    sudo yum update -y
```

Este comando se utiliza en sistemas operativos basados en Red Hat (como CentOS, Amazon Linux, etc.) y se utiliza para actualizar todos los paquetes del sistema. El parámetro -y se usa para aceptar automáticamente todas las actualizaciones sin necesidad de confirmación del usuario.

```
    sudo yum install docker
```

Este comando se utiliza para instalar Docker en un sistema operativo basado en Red Hat utilizando el administrador de paquetes YUM. Descarga e instala los paquetes necesarios para ejecutar Docker en el sistema.

```
    sudo service docker start
```

Después de instalar Docker, este comando se utiliza para iniciar el servicio Docker en el sistema. Esto permite que Docker comience a ejecutar contenedores y manejar imágenes.

```
    sudo usermod -a -G docker ec2-user
```

Este comando se utiliza para agregar el usuario actual (ec2-user en este caso) al grupo docker. Esto permite al usuario ejecutar comandos de Docker sin necesidad de utilizar sudo. Después de ejecutar este comando, el usuario puede administrar contenedores Docker sin privilegios de root.

Ejecutamos lo siguiente:

```
    docker run -d -p 27017:27017 --name mongocontainer mongo
```

* docker run: Comando principal de Docker para crear y ejecutar contenedores.
* -d: Bandera que indica a Docker que el contenedor debe ejecutarse en segundo plano.
* -p 27017:27017: Bandera que mapea el puerto 27017 del contenedor al puerto 27017 del host. Esto es necesario para acceder a los servicios que se ejecutan dentro del contenedor desde fuera del mismo. En este caso, el puerto 27017 es el puerto predeterminado utilizado por MongoDB.
* --name mongocontainer: Define el nombre del contenedor como "mongocontainer". Esto proporciona un nombre de identificación único para el contenedor.
* mongo: Especifica el nombre de la imagen del contenedor que se utilizará, en este caso, la imagen oficial de MongoDB. Si la imagen no está presente en el sistema, Docker la descargará automáticamente antes de crear el contenedor.

Ahora creamos un Dockerfile y colocamos lo siguiente:

![](/media/Dockerfile.png)

Aquí especificamos la ejecución de la clase LogService

Ahora ejecutamos desde el local:

```
   docker build --tag dockerlogservice .
```

* docker build: Este comando se utiliza para construir una nueva imagen de Docker a partir de un Dockerfile.

* --tag dockerlogservice: La opción --tag (o -t) se utiliza para asignar un nombre y una etiqueta a la imagen que se está construyendo. En este caso, el nombre de la imagen se establece como dockerlogservice.

* El punto (.) al final del comando indica que Docker debe buscar el Dockerfile en el directorio actual para construir la imagen.

Ejecutaremos tres contenedores basados en la imagen dockerlogservice, cada uno con una configuración diferente de puerto y nombre.
```
   docker run -d -p 35001:46000 --name firstdockercontainer dockerlogservice
   docker run -d -p 35002:46000 --name secondockercontainer dockerlogservice
   docker run -d -p 35003:46000 --name thirdockercontainer dockerlogservice
```

* docker run: Comando principal de Docker para crear y ejecutar contenedores.
* -d: Bandera que indica a Docker que el contenedor debe ejecutarse en segundo plano (modo demonio).
* -p <host_port>:<container_port>: Bandera que mapea un puerto del host al puerto especificado dentro del contenedor. En este caso, se está mapeando el puerto 46000 del contenedor al puerto 35001, 35002 y 35003 del host para los tres contenedores respectivamente.
* --name <container_name>: Bandera que asigna un nombre específico al contenedor. En este caso, se están asignando los nombres firstdockercontainer, secondockercontainer y thirdockercontainer a los tres contenedores respectivamente.
* dockerlogservice: Especifica el nombre de la imagen del contenedor que se utilizará, en este caso, la imagen llamada dockerlogservice.

Asignamos una nueva etiqueta a la imagen dockerlogservice

```
   docker tag dockerlogservice jaidergonzalez/chataws
```

Subimos la imagen etiquetada

```
   docker push jaidergonzalez/chataws:latest
```

Volvemos al servidor y ejecutamos lo siguiente:

```
   docker run -d -p 35001:46000 --name firstdockercontainer jaidergonzalez/chataws
   docker run -d -p 35002:46000 --name secondockercontainer jaidergonzalez/chataws
   docker run -d -p 35003:46000 --name thirdockercontainer jaidergonzalez/chataws
```

* docker run: Comando principal de Docker para crear y ejecutar contenedores.
* -d: Bandera que indica a Docker que el contenedor debe ejecutarse en segundo plano (modo demonio).
* -p <host_port>:<container_port>: Bandera que mapea un puerto del host al puerto especificado dentro del contenedor. En este caso, se está mapeando el puerto 46000 del contenedor al puerto 35001, 35002 y 35003 del host para los tres contenedores respectivamente.
* --name <container_name>: Bandera que asigna un nombre específico al contenedor. En este caso, se están asignando los nombres firstdockercontainer, secondockercontainer y thirdockercontainer a los tres contenedores respectivamente.
* jaidergonzalez/chataws: Especifica el nombre de la imagen del contenedor que se utilizará, en este caso, la imagen llamada jaidergonzalez/chataws.

Ahora creamos otro Dockerfile pero con la siguiente información:

![](/media/Dockerfile2.png)

Seguimos un proceso similar. Ejecutamos:

```
   docker build --tag dockeroundrobin .
```

```
   docker run -d -p 8080:45000 --name firstrrdockercontainer dockeroundrobin
```

```
   docker tag dockeroundrobin jaidergonzalez/chataws
```
```
   docker push jaidergonzalez/chataws:latest
```

En el servidor ejecutamos lo siguiente:

```
   docker run -d -p 8080:45000 --name firstrrdockercontainer jaidergonzalez/chataws
```
## Consideraciones

- Las clases RRInvoker y MongoUtil dependen demasiado de la dirección de IPv4 pública o dns asignado del AWS. Como la instancia con la cual se trabajó va a estar apagada para ahorrar créditos, muchas funcionalidades no estarán activas, sin embargo se muestra el funcionamiento del sistema con un video e imágenes.
- La comunicación entre el balanceador de carga y los servicios de registro de mensajes se realiza a través de HTTP.
- La comunicación entre los servicios de registro de mensajes y la base de datos MongoDB se realiza a través del protocolo de MongoDB.
- La aplicación es escalable y puede manejar un alto volumen de solicitudes distribuyendo la carga entre varios servicios de registro de mensajes.

## Autor
- Jaider Arley Gonzalez Arias

