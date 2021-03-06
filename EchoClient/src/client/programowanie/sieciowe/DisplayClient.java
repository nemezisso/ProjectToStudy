package client.programowanie.sieciowe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class DisplayClient extends javax.swing.JFrame {
    BufferedReader in = null;
    PrintWriter out = null;
   
    public DisplayClient() {
        try {
            Socket socket = new Socket("localhost", 7);
            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("Nawiazano polaczenie z: "
                    + socket.getInetAddress());
        }catch (IOException e) {
            System.err.println(e);
        }
        initComponents();
    }

    private void initComponents() {

        showAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        searchISBN = new javax.swing.JButton();
        sort = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        addBook = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        showAll.setText("Pokaż");
        showAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllActionPerformed(evt);
            }
        });

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        searchISBN.setText("Wyszukaj");
        searchISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchISBNActionPerformed(evt);
            }
        });

        sort.setText("Sortuj");
        sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortActionPerformed(evt);
            }
        });

        exit.setText("Zamknij");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });

        addBook.setText("Dodaj książkę");
        addBook.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(showAll)
                        .addGap(18, 18, 18)
                        .addComponent(searchISBN)
                        .addGap(18, 18, 18)
                        .addComponent(sort)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(addBook)
                        .addGap(18, 18, 18)
                        .addComponent(exit)
                        .addGap(0, 232, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showAll)
                    .addComponent(searchISBN)
                    .addComponent(sort)
                    .addComponent(exit)
                    .addComponent(addBook))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }
    
    private void showAllActionPerformed(java.awt.event.ActionEvent evt) {
       String str=null;
       String[] show={"Wszystkie","Klasyczne","Ebooki","Audiobooki"};
       String choose=(String)JOptionPane.showInputDialog(null,"Wybierz jakie książki pokazać:","Wyświetl książki",
               JOptionPane.QUESTION_MESSAGE,null,show,0);
        
       switch(choose){
            case "Wszystkie":
                 out.println("show: all");
                 out.flush();
                 break;
            case "Klasyczne":
                out.println("show: classic");
                out.flush();
                break;
            case "Ebooki":
                out.println("show: ebook");
                out.flush();                
                break;
            case "Audiobooki":
                out.println("show: audiobook");
                out.flush();
                break;
            default:
                out.println("ERROR!");
                out.flush();
                break;
        }
        textArea.setText(null);
        textArea.append(DisplayClient.readText(in));
    }
    
    private void searchISBNActionPerformed(java.awt.event.ActionEvent evt) {
        String text=null;
        String numberISBN;
        numberISBN=GenerateBook.ISBNstring();
        
        out.println("search ISBN: "+numberISBN);
        out.flush();

        textArea.setText(null);
        textArea.append(DisplayClient.readText(in));

    }
    
    private void sortActionPerformed(java.awt.event.ActionEvent evt) {
        out.println("sort");
        out.flush();
        
        textArea.setText(null);
        textArea.append(DisplayClient.readText(in));
    }
    
    private void exitActionPerformed(java.awt.event.ActionEvent evt) {
        out.println("#");
        out.flush();
        
        try {
            in.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(DisplayClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0);
    }
    
    private void addBookActionPerformed(java.awt.event.ActionEvent evt) {
        String[] s={"Książka klasyczna","Ebook","Audiobook"};
        String choose=(String)JOptionPane.showInputDialog(null,"Wybierz jaką książkę chcesz dodać","Dodaj książkę",
               JOptionPane.QUESTION_MESSAGE,null,s,0);
        switch(choose){
            case "Książka klasyczna":
                out.println("add classic: "+GenerateBook.GenerateClassicBook());
                out.flush();
                break;
            case "Ebook":
                out.println("add ebook: "+GenerateBook.GenerateEbook());
                out.flush();
                break;
            case "Audiobook":
                out.println("add audio: "+GenerateBook.GenerateAudioBook());
                out.flush();
                break;
            default:
                out.println("ERROR");
                break;
        }
    }
    
    private static String readText(BufferedReader in){
        String readText=null;
        try {
            readText = in.readLine().replace("@","\n");
        } catch (IOException ex) {
            Logger.getLogger(DisplayClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return readText;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DisplayClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayClient().setVisible(true);
            }
        });
    }

    private javax.swing.JButton addBook;
    private javax.swing.JButton exit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchISBN;
    private javax.swing.JButton showAll;
    private javax.swing.JButton sort;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
