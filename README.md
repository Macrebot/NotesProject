# Notes Project
**Notes Project** es una aplicación que combina un microservicio en Java con un bot en Python para gestionar notas. Los usuarios interactúan con el sistema a través de un bot de Telegram que permite crear, modificar, borrar y visualizar notas. El bot se comunica con un servidor Java que maneja las operaciones y se conecta a una base de datos MongoDB.

## Estructura del Proyecto
* **Microservicio en Java:** Gestiona las operaciones CRUD para las notas y expone una API RESTful.
* **Bot en Python:** Actúa como la interfaz de usuario y realiza solicitudes HTTP al microservicio para manipular la base de datos.

## Configuración

### Configuración del Microservicio Java

1. **Configuración de la Base de Datos:** La conexión a la base de datos MongoDB se realiza mediante la constante `MONGODB_URI`. Asegúrate de definir esta constante en el archivo de configuración de tu aplicación.
2. ** Endpoints del Microservicio:**
El microservicio expone los siguientes endpoints a través del controlador `NoteController`:
* **Obtener todas las notas:**
```http
GET /notes
```
* **Obtener una nota por ID:**
```http
GET /notes/{id}
```
* **Crear una nueva nota:**
```http
POST /notes
```
* **Actualizar una nota existente:**
```http
PATCH /notes/{id}
```
* ** Eliminar una nota por ID:**
```http
DELETE /notes/{id}
```
### Configuración del Bot en Python
1. **Dependencias:** Asegúrate de instalar las dependencias necesarias para el bot Python usando el archivo `requirements.txt`.
Para instalar las dependencias, ejecuta dentro del directorio notes_bot:
```bash
pip install -r requirements.txt
```
2. **Configuración del Bot**: El token del bot de Telegram se almacena en un archivo separado llamado `secrets.py`. En el archivo del bot, importa el token de la siguiente manera:
**secrets.py**:
```python
TOKEN = 'tu-token-del-bot-telegram'
```
**notes_bot.py**:
```python
from secrets import TOKEN
NOTES_ENDPOINT = 'http:/localhost:8085/notes'
```
3. **Comandos del Bot**: El bot soporta los siguientes comandos para interactuar con el microservicio:
* **/start**: Inicia la interacción con el bot.
* **/get_all_notes**: Obtiene todas las notas.
* **/get_note [id]**: Obtiene una nota específica por ID.
* **/post_note [name]. [content]**: Crear una nueva nota con el nombre y contenido proporcionandos.
* **/delete_note [id]**: Elimina una nota específica por ID.
* **/patch_name_note [id] [new_name]**: Actualiza el nombre de una nota existente por ID.
* **/patch_content_note [id] [new_content]**: Actualiza el contenido de una nota existente por ID.
* **/patch_all_note [id] [new_name]. [new_content]**: Actualiza el nombre y contenido de una nota existente por ID.

## Dependencia Adicional
Para crear notas, el proyecto NotesProject depende de otro microservicio que se encarga de la asignación automática de IDs. Este microservicio se encuentra en otro repositorio. Asegúrate de que el microservicio de asignación de IDs esté en funcionamiento antes de intentar crear nuevas notas, ya que es necesario para proporcionar IDs únicos a las notas.

## Ejecución del Proyecto
1. **Inicia el Microservicio en Java**: Ejecuta el microservicio Java utilziando el comando Maven.
```bash
mvn spring-boot:run
```
2. **Inicia el Bot en Python**: Ejecuta el bot en Python.
```bash
python3 bot.py
```
3. **Interacción con el Bot**: Envía comandos al bot de Telegram para gestioanr tus notas.
