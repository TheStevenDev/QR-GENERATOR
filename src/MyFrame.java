import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class MyFrame extends JFrame implements ActionListener {
    private Color backGroundColor = new Color(36, 41, 46);
    private Color whiteColor = new Color(250, 251, 252);
    private Color greenColor = new Color(45, 186, 78);
    private Font font = new Font("Arial", Font.PLAIN, 15);

    //Labels
    private JLabel titleLabel = new JLabel("TheStevenDev - QR Code Generator");
    private JLabel dataLabel = new JLabel("ENTER DATA TO ENCODE:");
    private JLabel pathLabel = new JLabel("SELECT THE PATH:");

    //TextField & Buttons

    private JTextField dataField = new JTextField();
    private JButton selectButton = new JButton();
    private JButton confirmButton = new JButton();

    //Files
    JFileChooser fileChooser = new JFileChooser();

    public MyFrame() {
        this.setTitle("TheStevenDev - QR Code Generator");
        this.setVisible(true);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1100, 700);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(backGroundColor);

        titleLabel.setBounds(323, 18, 453, 86);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setOpaque(false);
        titleLabel.setFont(font);

        dataLabel.setBounds(100, 235, 415, 26);
        dataLabel.setForeground(Color.WHITE);
        dataLabel.setHorizontalAlignment(JLabel.RIGHT);
        dataLabel.setOpaque(false);
        dataLabel.setFont(font);


        dataField.setBounds(534, 228, 466, 45);
        dataField.setBackground(whiteColor);
        dataField.setForeground(Color.BLACK);
        dataField.setCaretColor(Color.BLACK);
        dataField.setOpaque(true);
        dataField.setFont(font);


        confirmButton.setBounds(432, 426, 237, 45);
        confirmButton.setText("SAVE");
        confirmButton.setForeground(whiteColor);
        confirmButton.setBackground(greenColor);
        confirmButton.setOpaque(true);
        confirmButton.setFocusable(false);
        confirmButton.setBorder(null);
        confirmButton.addActionListener(this);


        this.add(titleLabel);
        this.add(dataLabel);
        this.add(pathLabel);
        this.add(dataField);
        this.add(selectButton);
        this.add(confirmButton);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String userHome = System.getProperty("user.home");
            File desktop = new File(userHome, "Desktop");
            fileChooser.setCurrentDirectory(desktop);

            FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("PNG", "png");
            fileChooser.setFileFilter(pngFilter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.APPROVE_OPTION && dataField.getText() != null) {
                File selectedFile = fileChooser.getSelectedFile();
                String path = selectedFile.getAbsolutePath();

                if (!path.toLowerCase().endsWith(".png")) {
                    path += ".png";
                }

                try {
                    generateQRCode(dataField.getText(), path);
                } catch (Exception ex) {throw new RuntimeException(ex);}

            } else {
                dataField.setText("INSERT VALID OPTION!");
            }


        }


    }

    private void generateQRCode(String data, String filePath) throws WriterException, IOException {
        final int WIDTH = 300;
        final int HEIGHT = 300;

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // generate matrix
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, hints);

        File qrFile = new File(filePath);
        MatrixToImageWriter.writeToFile(bitMatrix, "PNG",qrFile);

    }


}
