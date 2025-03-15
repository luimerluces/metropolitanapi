# Jacidi BackEnd
Hola y bienvenido a esta prueba técnica para jacidi. En el siguiente documento te explicaremos qué tipo de perfil buscamos, instrucciones de la prueba, objetivos de la prueba y los criterios de evaluación de la misma.

## Que buscamos
Esta es una prueba para desarrolladores tanto de seniors como semi-seniors en Java + SpringBoot. Las peticiones son las mismas para ambos tipos de perfiles, pero el resultado que esperamos para un senior es bastante más alto, de modo que no se trata tanto de hacerla bien o mal, sino de determinar qué nivel como desarrollador BackEnd tienes

## Instrucciones de la prueba
En el siguiente proyecto Java, tienes un esqueleto básico de una para SpringBoot más la configuración para montar una imagen docker con una bbdd postgres con la información básica de la prueba. Deberás construir la imagen de postgreSQL y levantar el docker, las tablas de dicha bbdd son:
- activity: Tabla que almacena las actividades que se realizan en un gimnasio. Cada actividad es única y referenciada por otros elementos.
- member: Tabla que almacena los miembros del gimnasio, con su información personal y un campo json que actúa como calendario para sus actividades (más adelante lo explico en detalle)
- spaces: Tabla que contiene la información sobre los espacios físicos que tiene el gimnasio. Las actividades se realizan en algún espacio
- states_schedule: Tabla con los estados para una actividad agenda.

## Objetivos de la prueba
El objetivo de la prueba es desarrollar una API para solicitudes a la BBDD, capaz de realizar la gestión de las solicitudes con un routing diferenciado por entidad, un CRUD sobre las principales entidades, mostrar información en detalle del calendario de un miembro y actualizar los registros del calendario de un miembro o insertar nuevas actividades

### Routing
Tendras que usar SprintBoot para crear un routing que distinga el tipo de entidad en cada ruta y realice las validaciones que consideres necesarias, ej:

/jacidi/activity/{member_id}

/jacidi/spaces/{member_id}/calendar?someparameters

### CRUD
Tendrás que diseñar un CRUD para las tablas members. Aquí puedes asumir que todas las acciones serán sobre elementos unicos (nada de listas a eliminar) y por ende, siempre te tendrán que mandar el id del elemento.

### Detalle del calendario
Los miembros tienen un calendario en forma de json que contiene la lista de las actividades a las que se han inscrito. Para un usuario dado, se tiene que poder LISTAR las actividades que tienen en dicho calendario y filtrarlas por el estado de la misma (reservada, realizada, etc).

### Gestionar actividades (opcional semi-senior, oblilgatorio senior)
Este punto implica el poder listar las actividades de un centro, aplicando (opcionalmente) un filtro por spaces_id. Y además poder asignar o cancelar una actividad para el miembro. No se puede asignar nos actividades qeu coindidan por fecha, ni asignar dos veces una misma actividad.


## Criterios de evaluación
Se esperan las funcionalidades:
* Request a la API
* CRUD sobre members
* Detalle del calendario
* Gestionar actividades

Ten en cuenta que esta prueba no se trata solo de cumplir todos los puntos, sino de evaluar tu talento como programador, buscamos a profesionales con inventiva y capacidad para buscar soluciones, no tanto dominar tal o cual tecnología. Por ello, aspectos como los patrones de diseño, la calidad del código, la cobertura en test, la documentación y la buena metodología para organizar el trabajo, son aspectos que valoramos tanto o más que cumplir con la checklist de objetivos de la prueba. No obstante, si decides ir más allá, implementar más funcionalidades, cambios en la bbdd o la configuración del docker, no te cortes y hazlo, todo lo que creas que es una mejora. sera bienvenido.

Ten en cuenta que esta prueba no se trata solo de cumplir todos los puntos, sino de evaluar tu talento como programador, buscamos a profesionales con inventiva y capacidad para buscar soluciones, no tanto dominar tal o cual tecnología. Por ello, aspectos como los patrones de diseño, la calidad del código, la cobertura en test, la documentación y la buena metodología para organizar el trabajo, son aspectos que valoramos tanto o más que cumplir con la checklist de objetivos de la prueba.
Ademas, sientete libre implementar cambios, si crees que modificar la bbdd, la configuración del docker, incluir otros procesos de negócio o utilizar herramientas para la securización de las llamadas pueden sumar valor a la prueba, no te cortes y hazlo, te animamos a que te luzcas y muestres lo que sabes hacer.

## Candidate notes
Cualquier instrucción sobre la ejecución o cambio que consideres relevante, indicalo aquí abajo. Eres libre de usar cualquier herramienta o recurso y cambiar lo que consideres mejor, pero comentanos el que y el por que.