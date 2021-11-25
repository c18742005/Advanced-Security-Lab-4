import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Advanced Security Lab 4 - Part D
 * Program that implements the RSA encryption and decryption
 * The program follows the 5 steps of the RSA algorithm: 
 *      1. Choose 2 prime numbers p and q
 *      2. Calculate the modulus (n = p * q)
 *      3. Calculate the totient (phi = (p -1 )*(q - 1))
 *      4. Choose number e coprime to phi where 1 < e < phi
 *      5. Calculate the private key d as e^-1 (mod phi)
 * To encrypt the following calculation is used m^e mod n
 * To decrypt the following calculation is used c^d mod n
 * @author steven
 */
public class RSA_Algorithm extends javax.swing.JFrame {

    /**
     * Creates new RSA_Algorithm application
     */
    public RSA_Algorithm() {
        initComponents();
        
        // Step 1: Choose 2 prime numbers p and q
        p = getPrimeInt();
        q = getPrimeInt();
        
        // Step 2: Calculate the modulus
        n = p * q;
        
        // Step 3: Calculate the totient
        phi = (p - 1) * (q - 1);
        
        // Step 4: Choose number e coprime to phi where 1 < e < phi
        for(e = 2; e < phi; e++) {
            if (gcd(e, phi) == 1) {
                break;
            }
        }
        
        // Step 5: calculate the private key d as e^-1 (mod phi)
        for (int i = 0; i <= 9; i++) {
            int x = 1 + (i * phi);
 
            // d is for private key exponent
            if(x % e == 0) {
                d = x / e;
                break;
            }
        }
    }
    
    /**
     * Function that creates a random prime integer
     * @return num a prime integer
     */
    private int getPrimeInt() {
        int num = 0;
        
        // Generate a random number
        Random rand = new Random();

        do {          
            num = rand.nextInt(1000) + 1;
        } while(!isPrime(num));
        
        return num;
    }
    
    /**
     * Function to check if an integer is prime or not
     * @param n integer to be checked
     * @return true if int is prime else false
     */
    private boolean isPrime(int n) {
        // If number is 2 or 3 return true
        // If it is less than or equal to one return false
        if (n <= 3 || n % 2 == 0) {
            return n == 2 || n == 3;
        }

        int div = 3;
        
        // iterate through all possible divisors
        while ((div <= Math.sqrt(n)) && (n % div != 0)) {
            div += 2; 
        }
        
        return n % div != 0;
    }
    
    /**
     * Function to check the GCD of two integers
     * @param n1 
     * @param n2
     * @return the GCD of the numbers
     */
    private int gcd(int n1, int n2) {
        if (n1 == 0) {
            return n2;
        } else {
            return gcd(n2 % n1, n1);
        }
    }
    
    /**
     * Function to encrypt message using RSA
     * @param m message to be encrypted
     * @return encrypted message
     */
    private BigInteger encryptMessage(int m) {
        BigInteger M = BigInteger.valueOf(m);
        BigInteger E = BigDecimal.valueOf(e).toBigInteger();
        BigInteger N = BigInteger.valueOf(n);
        
        // m^e mod n
        return M.modPow(E, N);
    }
    
    /**
     * Function to decrypt the encrypted message using RSA
     * @param c message to decrypt
     * @return decrypted message
     */
    public BigInteger decryptMessage(BigInteger C) {
        // c^d mod n
        BigInteger N = BigInteger.valueOf(n);
        
        return (C.pow(d)).mod(N);
    }
    
    /**
     * Function to initialise components of the app
     */                       
    private void initComponents() {

        label = new javax.swing.JLabel();
        button = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultArea = new javax.swing.JTextArea();
        outputLabel = new javax.swing.JLabel();
        message = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        label.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        label.setText("Message:");

        button.setText("Submit");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        resultArea.setColumns(20);
        resultArea.setLineWrap(true);
        resultArea.setRows(5);
        jScrollPane2.setViewportView(resultArea);

        outputLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        outputLabel.setText("Output:");

        message.setModel(new javax.swing.SpinnerNumberModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputLabel)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(outputLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }                      

    /**
     * Function that controls what happens when the Submit button is clicked
     * Function gets input of user starts the RSA encryption and decryption 
     * then prints results to UI
     * @param evt
     */
    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {                                       
        int input = (Integer) message.getValue();
        
        // Create string builder and print message to encrypt
        StringBuilder sb = new StringBuilder("Message to encrypt: ");
        sb.append(Integer.toString(input));
        sb.append("\n");
        
        // Print p, q, n, phi and e
        sb.append("\np = ");
        sb.append(Integer.toString(p));
        sb.append(", q = ");
        sb.append(Integer.toString(q));
        sb.append("\n");
        sb.append("n = ");
        sb.append(Integer.toString(n));
        sb.append("\n");
        sb.append("phi = ");
        sb.append(Integer.toString(phi));
        sb.append("\n");
        sb.append("e = ");
        sb.append(Integer.toString(e));
        sb.append("\n");
        
        // Print public string to user
        sb.append("\nPublic key is: ");
        sb.append(Integer.toString(n));
        sb.append(", ");
        sb.append(Integer.toString((int) e));
        sb.append("\n");
        
        // Print private if you need it
        // sb.append("\nPrivate key is: ");
        // sb.append(Integer.toString((int) d));
        // sb.append("\n");
        
        // Encrypt the plaintext input
        BigInteger ciphertext = encryptMessage(input);
        sb.append("\nEncrypted message: ");
        sb.append(ciphertext.toString());
        sb.append("\n");
        
        // Decrypt the ciphertext input and display it
        BigInteger plaintext = decryptMessage(ciphertext);
        sb.append("\nDecrypted message: ");
        sb.append(plaintext.toString());
        sb.append("\n");
        
        // Display results to user
        resultArea.setText(sb.toString());
    }                                      

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch(ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RSA_Algorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(InstantiationException ex) {
            java.util.logging.Logger.getLogger(RSA_Algorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RSA_Algorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch(javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RSA_Algorithm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the app */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RSA_Algorithm().setVisible(true);
            }
        });
    }

    // Variables declaration                    
    private javax.swing.JButton button;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label;
    private javax.swing.JSpinner message;
    private javax.swing.JLabel outputLabel;
    private javax.swing.JTextArea resultArea;
    private int p, q, n, phi, e, d;      
}
