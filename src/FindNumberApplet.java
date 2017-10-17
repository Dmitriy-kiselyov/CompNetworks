import javax.swing.*;
import java.io.IOException;

public class FindNumberApplet extends JApplet {
    private JPanel panel1;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JButton connectButton;
    private JTextArea consoleArea;
    private JButton clearButton;

    //Called when this applet is loaded into the browser.
    public void init() {
        //Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(() -> add(panel1));

            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }

            addEventListeners();
        } catch (Exception e) {
            System.err.println("createGUI didn't complete successfully");
        }
    }

    private void addEventListeners() {
        connectButton.addActionListener(event -> {
            String ip = ipTextField.getText();
            String port = portTextField.getText();

            if (consoleArea.getText().length() > 0)
                log("-------------------------------------");

            try {
                FindNumberClient.logger = this::log;
                FindNumberClient.main(new String[]{ip, port});
            } catch (Exception e) {
                log("Произошла ошибка: " + e.getMessage());
            }
        });

        clearButton.addActionListener(e -> clear());
    }

    private void log(String s) {
        consoleArea.append(s + "\n");
    }

    private void clear() {
        consoleArea.setText("");
    }

}
