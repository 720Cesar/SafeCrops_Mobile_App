# SafeCrops

SafeCrops es una aplicación móvil desarrollado utilizando Kotlin y Java. La aplicación tiene como objetivo la gestión de usuarios mediante una plataforma web conectada a una base de datos
MySQL a través de un dominio. Dentro de la aplicación el usuario puede crearse una cuenta con dos diferentes tipos de usuario a los que se les permitirá hacer uso de un modelo de predicción
de clases de enfermedades, el modelo utilizado es TensorFlow Lite y puede ser ajustado a cualquier tipo de detección de objetos. 

Las características principales del proyecto son:

- Clasificador de objetos usando un modelo de TensorFlow Lite.
- Gestión de información conectado a Internet mediante un host gratuito y bases de datos MySQL.

# Dev
Uso de la aplicación con un modelo de TensorFlow Lite:
- El repositorio no cuenta con un modelo de TFL, por lo que para cargarlo se debe realizar en la carpeta "app/src/main/ml/"
- La adaptación al modelo se realiza en los archivos Java "choose_gallery_photo" y "take_photo"

NOTA 10/10/2024: 
El servicio de hosting gratuito utilizado por la aplicación ha cesado operaciones, se evalua la manera de cambiar el servicio de hosting para mantener las operaciones activas de la base de datos.
