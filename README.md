# Sistema de Registro Civil

Aplicacion de escritorio en Java Swing para gestionar un registro civil.  
Permite administrar sucursales y personas a traves de una interfaz grafica sencilla e intuitiva.  

---

## Requisitos  
- Java JDK 8 o superior  

---

## Como ejecutar el proyecto  

### Opcion 1: Desde un IDE (recomendado)  
1. Abre el proyecto en Apache NetBeans, Eclipse o IntelliJ.  
2. Ejecuta la clase principal:

3. En Apache NetBeans tambien puedes simplemente presionar **F6** para correr el proyecto.  

### Opcion 2: Desde la terminal (opcional)  
Compila y ejecuta con:  

```bash
# Compilacion
javac -d bin src/sistemregistrocivil/*.java src/sistemregistrocivil/**/*.java

# Ejecucion
java -cp bin sistemregistrocivil.view.Menu

## Uso basico

1. Haz clic en "Administrar Sucursales"

2. Carga las sucursales desde un archivo CSV ("Cargar sucursales via CSV")

3. Carga las personas desde un archivo CSV ("Cargar personas via CSV")

4. Usa clic derecho en las tablas para acceder a menus contextuales

---

Notas importantes

Los archivos CSV deben estar en la carpeta:

src/datos/

Si ocurre un error con la carga de datos, verifique que los archivos.csv esten guardados en formato CSV UTF-8 (delimitado por comas)
