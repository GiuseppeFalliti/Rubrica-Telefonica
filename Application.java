//fare salva e carica.
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

public class Application extends JFrame {
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String POS_X = "x";
    private static final String POS_Y = "y";
    private Container cp;
    private JPanel mainPanel;
    private JPanel buttonpPanel;
    private DefaultListModel<Contatto> contattiListModel;
    private JList<Contatto> contattiList;

    private Preferences preferences;

    public Application() {
        super();
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        this.setTitle("Application");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        preferences = Preferences.userNodeForPackage(Application.class);
        int width = preferences.getInt(WIDTH_KEY, 300);
        int height = preferences.getInt(HEIGHT_KEY, 400);
        int posx = preferences.getInt(POS_X, 100);
        int posy = preferences.getInt(POS_Y, 100);

        this.setSize(width, height);
        this.setLocation(posx, posy);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveUserDimensions();
                System.exit(0);
            }
        });
        this.setupApp();
    }

    private void setupApp() {
        setTitle("Gestione contatti");
        setPreferredSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // pannello principale
        mainPanel = new JPanel(new BorderLayout());

        // Creare un modello per la lista di contatti
        contattiListModel = new DefaultListModel<>(); // inizializzazione modello dei dati per una lista.
        contattiList = new JList<>(contattiListModel); // inizializzazione lista che contiene gli oggetti Contatto.

        // pannello per i pulsanti
        buttonpPanel = new JPanel();

        // bottoni
        JButton aggiungi = new JButton("Add");
        JButton elimina = new JButton("Delete");
        JButton modifica = new JButton("Edit");
        JButton salva = new JButton("Save");
        JButton apri = new JButton("Open");

        // actionListener bottoni
        aggiungi.addActionListener(e -> {
            addContatto();
        });
        elimina.addActionListener(e2 -> {
            rimuoviContatto();
        });
        modifica.addActionListener(e3 -> {
            modificaContatto();
        });
        salva.addActionListener(e4 -> {
            save();
        });
        apri.addActionListener(e5->{
            open();
        });

        buttonpPanel.add(aggiungi);
        buttonpPanel.add(elimina);
        buttonpPanel.add(modifica);
        buttonpPanel.add(salva);
        buttonpPanel.add(apri);

        // Aggiungere la lista e il pannello dei pulsanti al pannello principale
        mainPanel.add(new JScrollPane(contattiList), BorderLayout.CENTER);
        mainPanel.add(buttonpPanel, BorderLayout.SOUTH);

        // titolo
        JLabel menuLabel = new JLabel("Menu Rubrica", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cp.add(menuLabel, BorderLayout.NORTH);

        cp.add(mainPanel);

    }

    private void addContatto() {
        // finestra di dialogo per l aggiunta del contatto alla rubrica
        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        JTextField numeroField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Cognome:"));
        panel.add(cognomeField);
        panel.add(new JLabel("Telefono:"));
        panel.add(numeroField);

        int risultato = JOptionPane.showConfirmDialog(cp, panel, "Inserisci le informazioni del contatto",
                JOptionPane.OK_CANCEL_OPTION);

        // Verifica se l'utente ha premuto OK
        if (risultato == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();
            String numero = numeroField.getText();

            if (checkContatto(cognome) == true) {
                JOptionPane.showMessageDialog(cp, "Cognome del Contatto gia presente nella rubrica");
            } else {

                Contatto nuovoContatto = new Contatto(nome, cognome, numero);
                contattiListModel.addElement(nuovoContatto);
            }

        }

    }

    private void rimuoviContatto() {
        int indiceSelezionato = contattiList.getSelectedIndex(); // recupera l indice dell elemento selezionato
        if (indiceSelezionato != -1) { // verifica se l'utente ha selezionato un elemento dalla lista
            contattiListModel.removeElementAt(indiceSelezionato); // rimozione del contatto
        }
    }

    private void modificaContatto() {
        // Pannello di input per cercare il contatto esistente
        JPanel paneldialog = new JPanel(new GridLayout(0, 1));
        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        paneldialog.add(new JLabel("Nome contatto esistente:"));
        paneldialog.add(nomeField);
        paneldialog.add(new JLabel("Cognome contatto esistente:"));
        paneldialog.add(cognomeField);

        // Mostra il primo dialogo per cercare il contatto
        int risultato = JOptionPane.showConfirmDialog(cp, paneldialog, "Cerca contatto", JOptionPane.OK_CANCEL_OPTION);
        if (risultato == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText();
            String cognome = cognomeField.getText();

            // Cerca il contatto nella lista
            for (int i = 0; i < contattiListModel.size(); i++) {
                Contatto contatto = contattiListModel.get(i);
                if (contatto.getNome().equals(nome) && contatto.getCognome().equals(cognome)) {
                    //mostra un altro dialogo per modificare i dati
                    JTextField nomeField2 = new JTextField(contatto.getNome());
                    JTextField cognomeField2 = new JTextField(contatto.getCognome());
                    JTextField numeroField2 = new JTextField(contatto.getNumero());

                    JPanel paneldialog2 = new JPanel(new GridLayout(0, 1));
                    paneldialog2.add(new JLabel("Nuovo Nome:"));
                    paneldialog2.add(nomeField2);
                    paneldialog2.add(new JLabel("Nuovo Cognome:"));
                    paneldialog2.add(cognomeField2);
                    paneldialog2.add(new JLabel("Nuovo Numero:"));
                    paneldialog2.add(numeroField2);

                    int risultato2 = JOptionPane.showConfirmDialog(cp, paneldialog2, "Modifica contatto",
                            JOptionPane.OK_CANCEL_OPTION);

                    if (risultato2 == JOptionPane.OK_OPTION) {
                        // Modifica i dati del contatto
                        contatto.setNome(nomeField2.getText());
                        contatto.setCognome(cognomeField2.getText());
                        contatto.setNumero(numeroField2.getText());
                        contattiList.repaint(); // Aggiorna la visualizzazione della lista
                    }
                    return; 
                }
            }
            JOptionPane.showMessageDialog(cp, "Contatto non trovato");
        }
    }

    private boolean checkContatto(String cognome) {
        // controllo se il contatto esiste gia nella rubrica
        for (int i = 0; i < contattiListModel.size(); i++) {
            Contatto contatto = contattiListModel.get(i);
            if (contatto.getCognome().equals(cognome)) {
                return true;
            }

        }
        return false;
    }
    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                FileWriter fileWriter = new FileWriter(file);

                for (int i = 0; i < contattiListModel.size(); i++) {
                    fileWriter.write(contattiListModel.get(i).getNome() + "#");
                    fileWriter.write(contattiListModel.get(i).getCognome() + "#");
                    fileWriter.write(contattiListModel.get(i).getNumero() + "#");
                    fileWriter.write("\n");
                }
                fileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] contatto = line.split("#");
                    if (contatto.length >= 3) {
                        String nome = contatto[0];
                        String cognome = contatto[1];
                        String numero = contatto[2];

                        Contatto newcontatto = new Contatto(nome, cognome, numero);
                        contattiListModel.addElement(newcontatto);

                    } else {
                        JOptionPane.showMessageDialog(this, "Errore lettura file: formato non valido");
                    }

                }
                
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore lettura file");
            }
        }
    }

    public void saveUserDimensions() {
        preferences.putInt(WIDTH_KEY, getWidth());
        preferences.putInt(HEIGHT_KEY, getHeight());
        preferences.putInt(POS_X, getX());
        preferences.putInt(POS_Y, getY());
    }

    public void startApp(boolean packElements) {
        if (packElements)
            this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Application app = new Application();
            app.startApp(true);
        });
    }
}
