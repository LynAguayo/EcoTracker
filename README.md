# 🌱 EcoTracker - Seguiment d'Activitats Sostenibles

## 📚 Taula de Continguts

1. [Document Tècnic del Projecte](#-document-tècnic-del-projecte)
2. [Manual d'Usuari](#-manual-dusuari)
3. [Pla de Proves](#-pla-de-proves)

## 📖 Document Tècnic del Projecte

### Descripció General
EcoTracker és una aplicació d'escriptori desenvolupada amb Java, JavaFX i MySQL que permet registrar i monitoritzar activitats sostenibles. La nostra aplicació té com a objectiu fomentar pràctiques ecològiques tant a nivell individual com corporatiu, calculant l'estalvi estimat de CO₂ per cada activitat registrada.

### Funcionalitat Creativa
Hem implementat una gràfica interactiva que mostra l'evolució mensual del CO₂ estalviat. Aquesta funcionalitat permet:
- Visualitzar l'evolució temporal de l'impacte ambiental
- Motivar els usuaris a mantenir hàbits sostenibles
- Analitzar tendències en el temps
- Exportar les dades per a anàlisis posteriors

### Estructura del Projecte
El projecte segueix el patró MVC (Model-Vista-Controlador):

#### Components Principals
- **Model**: `Activitat.java` - Representa una activitat sostenible
- **Vista**: Fitxers FXML i CSS per la interfície d'usuari
- **Controlador**: 
  - `RegistrarActivitatController.java`
  - `VisualitzarActivitatsController.java`
  - `GraficaController.java`
- **Utilitats**:
  - `CO2Calculator.java` - Càlculs d'estalvi de CO₂
  - `DBConnector.java` - Gestió de la connexió a la base de dades
  - `ActivitatDAO.java` - Accés a dades

### Diagrama de Classes
```
[Espai reservat per al diagrama de classes]
```

### Base de Dades
Utilitzem MySQL com a sistema de gestió de base de dades. L'estructura principal és:

```sql
CREATE TABLE sustainable_activities (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    co2_saved DOUBLE NOT NULL
);
```

La base de dades es crea automàticament si no existeix gràcies a la configuració `createDatabaseIfNotExist=true` en la URL de connexió. A més, el sistema inclou un `DataInitializer` que:
- Crea la taula si no existeix
- Insereix 10 activitats d'exemple amb dades variades
- Inclou exemples de totes les categories disponibles
- Serveix com a dades de prova per verificar el funcionament de l'aplicació

### Càlcul de CO₂
El sistema calcula l'estalvi de CO₂ segons la categoria de l'activitat. Quan registres una activitat, primer has de seleccionar la categoria, i després introduir el valor específic que es demana per a cada una:

| Categoria     | Valor que demana                    | Fórmula                                  | Exemple                                  |
| ------------- | ----------------------------------- | ---------------------------------------- | ---------------------------------------- |
| Transport     | Quants km has recorregut?           | `valor * 0.24` kg CO₂                    | 10 km = 2.4 kg CO₂ estalviat             |
| Teletreball   | Quants dies has teletreballat?      | `valor * 1.8` kg CO₂                     | 2 dies = 3.6 kg CO₂ estalviat            |
| Reciclatge    | Quants kg has reciclat?             | `valor * 1.0` kg CO₂                     | 5 kg = 5.0 kg CO₂ estalviat              |
| Energia       | Quantes hores ho has mantenit apagat? | `valor * 0.15` kg CO₂                 | 4 hores = 0.6 kg CO₂ estalviat           |
| Consum local  | Quants productes locals has comprat? | `valor * 0.5` kg CO₂                    | 3 productes = 1.5 kg CO₂ estalviat       |
| Reutilització | Quantes vegades has reutilitzat?    | `valor * 0.4` kg CO₂                     | 2 vegades = 0.8 kg CO₂ estalviat         |
| Altres        | Valor directe de CO₂ estalviat (kg) | Valor introduït directament              | 1.5 kg CO₂ estalviat                     |

El sistema mostra automàticament el text d'ajuda adequat segons la categoria seleccionada, facilitant la introducció del valor correcte. A més, realitza validacions per assegurar que:
- El valor introduït és un número vàlid
- No es permeten valors negatius
- El text d'ajuda canvia dinàmicament segons la categoria seleccionada

## 👤 Manual d'Usuari

### Requisits del Sistema
- Java 17 o superior
- XAMPP (MySQL)
- Maven
- Git

### Instal·lació
1. **Preparació del Entorn**:
   - Instal·lar XAMPP i iniciar el servei MySQL
   - Verificar que MySQL està actiu a http://localhost:8080/
   - No cal crear la base de dades manualment, es crea automàticament

2. **Configuració del Projecte**:
   ```bash
   git clone [URL del repositori]
   cd ecotracker
   ```

3. **Configuració de la Base de Dades**:
   - Les credencials per defecte són:
     - Usuari: root
     - Contrasenya: root
   - Si la teva instal·lació de MySQL utilitza una contrasenya buida, modifica el fitxer `src/main/java/com/example/ecotracker/util/DBConnector.java`:
     ```java
     private static final String PASSWORD = ""; // Canvia "root" per "" si és necessari
     ```

4. **Verificació de Dependències**:
   Assegura't que el teu `pom.xml` inclou les següents dependències:
   ```xml
   <dependencies>
       <!-- MySQL Connector -->
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>8.0.27</version>
       </dependency>
       
       <!-- JUnit -->
       <dependency>
           <groupId>org.junit.jupiter</groupId>
           <artifactId>junit-jupiter</artifactId>
           <version>5.8.2</version>
           <scope>test</scope>
       </dependency>
       
       <!-- Mockito -->
       <dependency>
           <groupId>org.mockito</groupId>
           <artifactId>mockito-core</artifactId>
           <version>4.5.1</version>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

5. **Execució de l'Aplicació**:
   ```bash
   mvn javafx:run
   ```
   O executa directament la classe `EcoTrackerApp` des del teu IDE.

### Ús de l'Aplicació
1. **Registrar una Activitat**:
   - Seleccionar "Registrar Activitat" al menú principal
   - Omplir el formulari amb:
     - Nom de l'activitat
     - Data
     - Categoria
     - Descripció
     - Valor (segons la categoria)
   - Clic a "Afegir Activitat"

2. **Visualitzar Activitats**:
   - Seleccionar "Visualitzar Activitats"
   - Veure la llista d'activitats registrades
   - Exportar a CSV si es desitja

3. **Gràfica de CO₂**:
   - Seleccionar "Veure Gràfica"
   - Analitzar l'evolució mensual
   - Interactuar amb la gràfica per més detalls

## 🧪Pla de Proves

### Eines de Testing
Per implementar les nostres proves, hem utilitzat:
- **JUnit**: Framework de proves unitàries que ens permet verificar el comportament individual de cada component de l'aplicació. Utilitzem anotacions com `@Test` per marcar els mètodes de prova i `assertEquals` per verificar els resultats.
- **Mockito**: Framework que ens permet crear objectes simulats (mocks) per aïllar els components durant les proves. És especialment útil per simular la connexió a la base de dades i verificar les interaccions entre components.

### Master Test Plan
Hem implementat un pla de proves complet que combina:
- **Proves Unitàries**: Utilitzant JUnit per verificar el funcionament individual de cada component
- **Proves Funcionals**: Verificant la integració entre components i el flux complet de l'aplicació
- **Proves d'Integració**: Comprovant la correcta interacció amb la base de dades

### Llistat de Proves Realitzades

#### Què es Prova
1. **Validació de Dades**
   - Registre d'activitats amb camps buits
   - Introducció de valors no vàlids
   - Validació de dates futures
   - Comprovació de valors negatius

2. **Persistència de Dades**
   - Creació i inicialització de la base de dades
   - Inserció d'activitats
   - Recuperació i llistat d'activitats
   - Eliminació d'activitats
   - Càlcul de totals

3. **Funcionalitats de la Interfície**
   - Visualització de la taula d'activitats
   - Exportació a CSV
   - Generació i actualització de la gràfica
   - Navegació entre pantalles

#### Mètodes i Classes Testejades

##### ActivitatDAOTest
```java
- testCreateTable() // Verifica la creació de la taula
- testInsert() // Comprova la inserció d'activitats
- testFindAll() // Verifica la recuperació de totes les activitats
- testDelete() // Comprova l'eliminació d'activitats
- testGetTotalCo2Saved() // Verifica el càlcul del total de CO₂
```

##### GraficaControllerTest
```java
- testCalculoCO2PorMes() // Verifica el càlcul mensual
- testUpdateChart() // Comprova l'actualització del gràfic
- testHandleBack() // Verifica la navegació
```

#### Resultats Esperats
- **Validació de Dades**:
  - Rebuig de camps buits
  - Rebuig de valors no vàlids
  - Rebuig de dates futures
  - Rebuig de valors negatius

- **Persistència**:
  - Creació correcta de la taula
  - Inserció exitosa d'activitats
  - Recuperació completa de dades
  - Eliminació correcta
  - Càlculs precisos de CO₂

- **Interfície**:
  - Visualització correcta de dades
  - Exportació completa a CSV
  - Gràfica actualitzada i precisa
  - Navegació fluida

### Fitxers de Test Inclosos
- `src/test/java/com/example/ecotracker/ActivitatDAOTest.java`
- `src/test/java/com/example/ecotracker/GraficaControllerTest.java`

### Master Test Plan Excel
[Enllaç al Master Test Plan en Excel](https://docs.google.com/spreadsheets/d/1diTIuu18pzon1jrxgCvpry4fTz5WS3NRZ5Q9wrqoXG0/edit?usp=sharing)

## 👥 Treball en Equip

### Gestió del Projecte
Per a una gestió eficient del projecte, hem utilitzat:
- **GitHub Projects**: Per organitzar i repartir les tasques entre els membres de l'equip
- **Issues**: Per gestionar dubtes, bugs i millores del projecte
- **Pull Requests**: Per revisar i integrar els canvis de manera controlada

![GitHub Projects](https://github.com/your-repo/ecotracker/blob/main/docs/github-projects.png)

### Organització
- Cada membre s'ha encarregat de desenvolupar diferents components de l'aplicació
- Hem fet reunions diàries per sincronitzar l'evolució del projecte
- Hem utilitzat el sistema d'issues per documentar i resoldre problemes

## 🖼️ Vistes de l'Aplicació

L'aplicació consta de quatre vistes principals:

1. **Menú Principal**
   ![Menú Principal](https://github.com/your-repo/ecotracker/blob/main/docs/menu-principal.png)
   - Accés a totes les funcionalitats
   - Resum de l'estalvi total de CO₂

2. **Registre d'Activitats**
   ![Registre](https://github.com/your-repo/ecotracker/blob/main/docs/registre-activitats.png)
   - Formulari per introduir noves activitats
   - Validació en temps real dels camps

3. **Visualització d'Activitats**
   ![Visualització](https://github.com/your-repo/ecotracker/blob/main/docs/visualitzacio-activitats.png)
   - Taula amb totes les activitats registrades
   - Opció d'exportar a CSV

4. **Gràfica de CO₂**
   ![Gràfica](https://github.com/your-repo/ecotracker/blob/main/docs/grafica-co2.png)
   - Evolució mensual de l'estalvi de CO₂
   - Visualització interactiva