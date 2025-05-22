# 🌱 EcoTracker - Seguiment d'Activitats Sostenibles

## 📚 Taula de Continguts

1. [Document Tècnic del Projecte](#-document-tècnic-del-projecte)
2. [Manual d'Usuari](#-manual-dusuari)
3. [Pla de Proves](#-pla-de-proves)

## 📖 Document Tècnic del Projecte

### Descripció General
EcoTracker és una aplicació d'escriptori desenvolupada amb Java, JavaFX i MySQL que permet registrar i monitoritzar activitats sostenibles. La nostra aplicació té com a objectiu fomentar pràctiques ecològiques a nivell individual, calculant l'estalvi estimat de CO2 per cada activitat registrada.

### Funcionalitat Creativa
Hem implementat una gràfica interactiva que mostra l'evolució mensual del CO2 estalviat. Aquesta funcionalitat permet:
- Visualitzar l'evolució temporal de l'impacte ambiental
- Analitzar tendències en el temps
- Exportar les dades en format CSV

### Estructura del Projecte
El projecte segueix el patró MVC (Model-Vista-Controlador):

#### Components Principals
- **Model**: `Activitat.java` - Representa una activitat sostenible
- **Vista**: Fitxers FXML i CSS per la interfície d'usuari
- **Controlador**: 
  - `MenuInicialController.java`
  - `RegistrarActivitatController.java`
  - `VisualitzarActivitatsController.java`
  - `GraficaController.java`
- **Utilitats**:
  - `CO2Calculator.java` - Càlculs d'estalvi de CO2
  - `DBConnector.java` - Gestió de la connexió a la base de dades
  - `DataInitializer.java` - Per insertar 10 activitats d'exemple a la taula sustainable_activities
- **Dao**: `ActivitatDAO.java` - Accés a dades

### Diagrama de Classes
![Diagrama de Classes](/docs/diagrama-clases.png)

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

El sistema mostra automàticament el text d'ajuda adequat segons la categoria seleccionada. A més, fa validacions per assegurar que:
- El valor introduït és un número vàlid
- No es permeten valors negatius
- La data ha d'estar compresa entre fa 30 dies i avui (no s'admeten dates futures ni massa antigues)
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
   git clone https://github.com/LaSalleGracia-Programacio/24-25-pr-ctica-iii-evelyn_mariona_alexandra.git
   cd ecotracker
   ```

3. **Configuració de la base de dades**:
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
           <version>8.0.30</version>
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
           <version>5.18.0</version>
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

## 🧪 Pla de Proves

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
- testInsert() // Comprova la inserció d'activitats
- testFindAll() // Verifica la recuperació de totes les activitats
- testDelete() // Comprova l'eliminació d'activitats
- testGetTotalCo2Saved() // Verifica el càlcul del total de CO₂
```

##### GraficaControllerTest
```java
- testCalculoCO2PorMes() // Verifica el càlcul mensual
```

#### Resultats Esperats
- **Validació de Dades**:
  - Rebuig de camps buits
  - Rebuig de valors no vàlids
  - Rebuig de dates futures
  - Rebuig de valors negatius

- **Persistència**:
  - Creació correcta de la taula
  - Inserció correcta d'activitats
  - Recuperació completa de dades
  - Eliminació correcta
  - Càlculs precisos de CO2

- **Interfície**:
  - Visualització correcta de dades
  - Exportació completa a CSV
  - Gràfica actualitzada i precisa

### Fitxers de Test Inclosos
- `src/test/java/com/example/ecotracker/ActivitatDAOTest.java`
- `src/test/java/com/example/ecotracker/GraficaControllerTest.java`

### Master Test Plan Excel
[Enllaç al Master Test Plan en Excel](https://docs.google.com/spreadsheets/d/1yxbmQ9fcvrkNs2QZWF8OLe201ef_YaRf/edit?usp=sharing&ouid=118312948817311622634&rtpof=true&sd=true)
[Enllaç a la presentació Canva](https://www.canva.com/design/DAGoM_tb3pI/5VqHAiA2XA8l46BN_MP5sg/view?utm_content=DAGoM_tb3pI&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h3e0eb9188f)

## 👥 Treball en Equip

### Gestió del Projecte
Per a una gestió eficient del projecte, hem utilitzat:
- **GitHub Projects**: Per organitzar i repartir les tasques entre els membres de l'equip
- **Issues**: Per gestionar dubtes i millores del projecte

![GitHub Projects](/docs/github-projects.png)
![Issues](/docs/issues.png)

## 🖼️ Vistes de l'Aplicació

L'aplicació consta de quatre vistes principals:

1. **Menú Principal**
   ![Menú Principal](/docs/menu-inicial.png)
   - Accés a totes les funcionalitats

2. **Registre d'Activitats**
   ![Registre](/docs/registrar-actvitat.png)
   - Formulari per introduir noves activitats
   - Validació en temps real dels camps

3. **Visualització d'Activitats**
   ![Visualització](/docs/visualitzar-activitats.png)
   - Taula amb totes les activitats registrades
   - Opció d'exportar a CSV

4. **Gràfica de CO2**
   ![Gràfica](/docs/grafica-co2-estalviat.png)
   - Evolució mensual de l'estalvi de CO₂
