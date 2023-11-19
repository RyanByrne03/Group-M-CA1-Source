// Password Manager CA 
//Joey Teahan - 20520316 and Josh Lamai - 21381093
//Code bug fixed by Ryan Byrne - 21503956 and Ruairi Watson - 21391571
//Manual tests performed by Ryan Byrne at 03:16 19/11/2023
//Importing the packages that we need to run the GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PasswordManagerGUI {
    private Map<String, PasswordEntry> passwordStorage = new HashMap<>();

   
    private JFrame frame; 
    private JTextArea resultTextArea;
    private JTextField siteTextField;
    private JPasswordField passwordField;

    public static void main(String[] args) { //Declaring the main method of the GUI
        SwingUtilities.invokeLater(() -> { //Invoking the ultilities at a later time
            try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //Beginning of try method to launch the GUI
            } catch (Exception e) 
            { e.printStackTrace();
            
            }new PasswordManagerGUI().initializeGUI();
            
        });            
    }

    private void initializeGUI() {//Beginning of the initialize function
       frame = new JFrame("Password Manager");//Title of window
       frame.setSize(500, 500);//Sets the height and weight of the GUI
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//If pressed, closes the GUI

        
       JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10)); //Sets the grid measurements for 
       panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); //Setting all of the borders to 15 
        
       siteTextField = new JTextField();//Declaring the site text field
       passwordField = new JPasswordField();//Declaring the password text field
       resultTextArea = new JTextArea();//Declaring the area that the text appears in 
       resultTextArea.setEditable(false);//Making sure the test area is static 
       resultTextArea.setForeground(Color.BLUE); //Setting the foreground to blue

        
       JButton storeButton = createStyledButton("Store Password", Color.BLUE, e -> storePassword()); //Creating a button that stores the users password
       JButton retrieveButton = createStyledButton("Retrieve Password", Color.BLUE, e -> retrievePassword());//Creating a button that retrieves the users entered password
       JButton showOriginalButton = createStyledButton("Show Original Password", Color.BLUE, e -> showOriginalPassword());//This button shows the original password the user entered

        
       Font buttonFont = new Font("Arial", Font.BOLD, 18); //Setting the button,text and label fonts to arial, bold and to 18
       Font textFont   = new Font("Arial", Font.BOLD, 18);
       Font labelFont  = new Font("Arial", Font.BOLD, 18);
       JLabel siteLabel = new JLabel("Site:"); //Creating the "Site" label
       siteLabel.setFont(labelFont);
       JLabel passwordLabel = new JLabel("Password:");//Creating rhe £Password" label
       passwordLabel.setFont(labelFont);
       
       //Sets the fonts of thr fields and the text area 
       siteTextField.setFont(textFont);
       passwordField.setFont(textFont);
       resultTextArea.setFont(textFont);
       
       //Sets the fonts of all of the buttons
       showOriginalButton.setFont(buttonFont);
       storeButton.setFont(buttonFont);
       retrieveButton.setFont(buttonFont);
        
        //Adds each respective item to the GUI
       panel.add(siteLabel);
       panel.add(siteTextField);
       panel.add(passwordLabel);
       panel.add(passwordField);
       panel.add(storeButton);
       panel.add(retrieveButton);
       panel.add(showOriginalButton);
       frame.add(panel);
       frame.add(resultTextArea, BorderLayout.SOUTH);
       frame.setVisible(true);//Makes everything visable
    }

    private JButton createStyledButton(String text, Color background, ActionListener actionListener) { //The class to style the buttons
       JButton button = new JButton(text);//Creating the button
       button.setBackground(background);
       button.addActionListener(actionListener);//The actions that will affect the button
       return button;
    }
    
    private void storePassword() { //Creating the store password
       String site = siteTextField.getText();
       char[] passwordChars = passwordField.getPassword();
       String originalPassword = new String(passwordChars);
       passwordField.setText("");         
       String encryptedPassword = hashPassword(originalPassword);
       passwordStorage.put(site, new PasswordEntry(encryptedPassword, originalPassword));
       resultTextArea.setText("Password stored for " + site);//The output 
    }

    private void retrievePassword() {//The retrieve password feature
       String site = siteTextField.getText();
       PasswordEntry passwordEntry = passwordStorage.get(site);
       if (passwordEntry != null) {//if statement that has the condition if the password entry is not equal to zero.
            resultTextArea.setText("Hashed Password for " + site + ": " + passwordEntry.getEncryptedPassword());
        } else {
           resultTextArea.setText("No password stored for " + site);
        }
    }

   
    private void showOriginalPassword() {//Original password feature
       String site = siteTextField.getText();//Calls the getText function we created earlier
       PasswordEntry passwordEntry = passwordStorage.get(site);
        if (passwordEntry != null) {//If the passwordEntry is not equal to 0, then print out the line below
           resultTextArea.setText("Original Password for " + site + ": " + passwordEntry.getOriginalPassword());
        } else {//If not, then print the below line
           resultTextArea.setText("No password stored for " + site);
        }
    }

    private String hashPassword(String password) {//Hash password feature
        try {//Start of the try function 
          MessageDigest md = MessageDigest.getInstance("SHA-256");//The getInstance that will be used to hash the password
           byte[] hashBytes = md.digest(password.getBytes());//Changes the password to bytes 
           StringBuilder hexStringBuilder = new StringBuilder();//Using the StringBuilder to get a hexidecimal
           for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);//converts the bytes into a hexidecimal password 
                if (hex.length() == 1) {//If the hexidecimal has more than 1 character, then it leads with a 0
                    hexStringBuilder.append('0');
                }
                hexStringBuilder.append(hex);
            }
            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {//The exception 
            
            e.printStackTrace();
            
            return null;//Returns a null value
            
        }
        
    }

    private class PasswordEntry {    
        private String encryptedPassword; //Creating the encryptedPassword variable
        private String originalPassword;//Creating the originalPassword variable
        public PasswordEntry(String encryptedPassword, String originalPassword) {
            this.encryptedPassword = encryptedPassword;
            this.originalPassword = originalPassword;
        }
        
          public String getOriginalPassword() { //Declaring the original password getter
            return originalPassword;
        }
          
           public String setOriginalPassword() { //Declaring the original password setter
            return originalPassword;
        }
           
          
        public String getEncryptedPassword() { //Declaring the encrypted password getter
            return encryptedPassword;
     }  
        
         public String setEncryptedPassword() { //Declaring the encrypted password setter
            return encryptedPassword;
        
  }
    }
}