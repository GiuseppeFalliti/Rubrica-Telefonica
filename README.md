```markdown
# Gestione Contatti - Java Swing Application

Questa applicazione Java è una semplice rubrica di contatti creata utilizzando la libreria **Swing** per la GUI. Gli utenti possono aggiungere, modificare e rimuovere contatti dalla lista, e l'applicazione salva automaticamente la posizione e le dimensioni della finestra dell'utente tra le sessioni utilizzando le **Java Preferences**.

## Funzionalità Principali

1. **Aggiunta di un Contatto**: Gli utenti possono aggiungere nuovi contatti specificando nome, cognome e numero di telefono.
2. **Modifica di un Contatto**: Gli utenti possono cercare un contatto esistente per nome e cognome e modificarne le informazioni.
3. **Rimozione di un Contatto**: Gli utenti possono selezionare un contatto dalla lista e rimuoverlo.
4. **Salvataggio delle Dimensioni e Posizione della Finestra**: L'applicazione ricorda le dimensioni e la posizione della finestra tra le sessioni utilizzando le **Java Preferences**.

## Requisiti

- **Java Development Kit (JDK)** 8 o superiore.
- **Swing**, che è parte integrante di Java SE.

## Come Eseguire

1. Clonare o scaricare il repository.
2. Compilare il programma:
   ```bash
   javac Application.java
   ```
3. Eseguire l'applicazione:
   ```bash
   java Application
   ```

## Struttura del Codice

- **Application.java**: Il file principale che gestisce l'interfaccia utente e la logica di gestione dei contatti.
- **Contatto.java**: La classe `Contatto` rappresenta un contatto con attributi come nome, cognome e numero di telefono.


## Autore

- [GiuseppeFalliti]

## Licenza

Questo progetto è sotto licenza MIT.
