# TestSegundaMano
Examen técnico de segunda mano

Descripción de la aplicación:
Se crea una app en Kotlin que permita poder realizar un seguimiento de la diferencia entre dos divisas por medio de la api https://fixer.io/

Características del proyecto:
Se creó el proyecto con Clean Architecture, MPV y dagger 2 para inyección de dependencias.
Se utiliza Couritnes para el manejo de múltiples llamadas a la api.
Se realizan unit test con la librería mockito.
Se agregan pequeñas animaciones sobre el recyclerview.

Nota.-
Tanto la api de fixer.io como otras que permiten ver el historial de las divisas en un rango de tiempo con un solo llamado a sus respectivas apis, solicitaban algún tipo de membresía o pago, por lo cual ajuste la manera de solicitar los datos uno a uno.

