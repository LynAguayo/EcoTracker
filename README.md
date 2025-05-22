# 🌱 EcoTracker - Seguiment d'Activitats Sostenibles
## 📚 Taula de Continguts

1. [Introducció](#📖-introducció)
2. [Estructura de la Interfície](#📋-estructura-de-la-interfície)
    - [Pantalla de Registre](#👤-pantalla-de-registre)
    - [Pantalla de Visualització](#📊-pantalla-de-visualització)
3. [Sistema de Càlcul de CO2](#🔢-sistema-de-càlcul-de-CO2)
    - [Factor de Conversió](#📐-factor-de-conversió)
    - [Exemple Pràctic](#📎-exemple-pràctic)
4. [Arquitectura del Sistema](#🏗️-arquitectura-del-sistema)
    - [Components Principals](#components-principals)
    - [Funcionalitat Creativa](#🔸-funcionalitat-creativa)
5. [Persistència de Dades](#💾-persistència-de-dades)
6. [Documentació i Testing](#📑-documentació)

## 📖 Introducció

EcoTracker és una aplicació d’escriptori desenvolupada amb Java, JavaFX i MySQL, pensada per fomentar pràctiques sostenibles tant a nivell individual com corporatiu.

Funcionalitats principals: 
- Facilitar el registre d'accions ecològiques
- Calcular l'estalvi estimat de CO2
- Visualitzar i exportar les activitats
- Potenciar el pensament crític i el desenvolupament responsable de programari

## 📋 Estructura de la Interfície

### 👤 Pantalla de Registre
1. **Formulari amb els següents camps:**
   - Nom de l'activitat
   - Data
   - Categoria (Transport, Reciclatge, etc)
   - Descripció
   - Valor (km, kg, hores, etc)

2. **Botó per guardar l'activitat**

3. **Text d'ajuda (prompt) canviant segons la categoria**

### 📊 Pantalla de Visualització
1. **Taula amb les activitats registrades**

2. **Mostra el total acumulat de Co2 estalviat**

3. **Botó per exportar a CSV**

4. **Accés a recomanacions o funcionalitats extres**
   
## 🔢 Sistema de Càlcul de CO2

### 📐 Factors de Conversió
| Categoria     | Fórmula                                  |
| ------------- | ---------------------------------------- |
| Transport     | `valor * 0.24` kg CO₂                    |
| Teletreball   | `valor * 1.8` kg CO₂                     |
| Reciclatge    | `valor * 1.0` kg CO₂                     |
| Energia       | `valor * 0.15` kg CO₂                    |
| Consum local  | `valor * 0.5` kg CO₂                     |
| Reutilització | `valor * 0.4` kg CO₂                     |
| Altres        | Valor introduït per l’usuari directament |

### 📎 Exemple Pràctic
Si registrem una activitat de transport amb 5 Km:
- Formula: `5 * 0.24 = 1.2` kg CO₂ estalviat

## 🏗️ Arquitectura del Sistema
### Components Principals 
- EcoTrackerApp.java: Punt d'entrada de l'aplicació.
- Activitat.java: Model de dades de cada acció sostenible.
- ActiitatDAO.java: Gestor de persistència amb MySQL.
- RegistrarActivitatController.java: Controlador de la pantalla de registre.
- VisualitzacioController.java: Controlador de la taula de visualització.
- CO2Calculator.java: Calculadora del CO2 estalviat.
 
### 🔸 Funcionalitat Creativa - Gràfica de CO2 estalviat per mes
L’usuari pot veure una gràfica amb l’evolució mensual del CO₂ estalviat segons les activitats registrades.

Característiques:
- Agrupa les activitats per mes i any
- Calcula el total de CO₂ de cada mes
- Mostra una gràfica de barres amb el resultat
- Implementat amb JavaFX (BarChart) i XYChart.Series

Per què l’hem triada:
- Aporta valor visual
- Fàcil d’interpretar per a usuaris no tècnics
- Estimula la motivació ecològica a llarg termini

## 💾 Persistència de Dades
EcoTracker utilitza una base de dades MySQL per emmagatzemar les activitats. 
Estructura principal de la taula activitats: 
- `id` (INT, PK)
- `nom` (VARCHAR)
- `data` (DATE)
- `categoria` (VARCHAR)
- `descripcio` (TEXT)
- `valor` (DOUBLE)
- `co2_estimacio` (DOUBLE)

## 📑 Documentació i Testing
### Manual d'Usuari
1. Executar l'aplicació (EcoTrackerApp)
2. Registrar una activitat des de la pantalla inicial
3. Visualitzar activitats i exportar-les
4. Fer clic a "Veure gràfica" per analitzar l'evolució mensual

### Instruccions d'instal·lació
- Tenir instal·lat: Java 17+, Maven i MySQL
- Crear la base de dades
- Editar la configuració de connexió a DBManager.java
- Executar via IDE o teminal amb mvn javafx:run

### Testing
JUnit: validació del càlcul de CO₂ i lògica de persistència
Mockito: simulació de connexions a base de dades

### Documentació  
Pots consultar els diagrames i més informació de la pràctica en el següent enllaç:

[Memòria del Projecte]
(https://docs.google.com/document/d/1diTIuu18pzon1jrxgCvpry4fTz5WS3NRZ5Q9wrqoXG0/edit?usp=sharing)
