import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.*;

public class PingApp {
    private static final Logger LOGGER = Logger.getLogger(PingApp.class.getName());

    private JFrame frame;
    private JLabel statusLabel;
    private JTextArea logTextArea;
    private JButton pingButton;

    public PingApp() {
        frame = new JFrame("Ping App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        statusLabel = new JLabel();
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        pingButton = new JButton("Pingar");
        pingButton.setPreferredSize(new Dimension(100, 30)); // Define o tamanho preferencial do botão
        pingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performPing();
            }
        });

        JPanel contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); // Define um espaçamento inferior de 10 pixels
        contentPane.add(statusLabel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH; // Ocupa todo o espaço horizontal e vertical
        gbc.weightx = 1.0; // Componente se expande horizontalmente
        gbc.weighty = 1.0; // Componente se expande verticalmente
        contentPane.add(new JScrollPane(logTextArea), gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE; // Não se expande
        gbc.weighty = 0.0; // Não se expande verticalmente
        contentPane.add(pingButton, gbc);

        frame.setContentPane(contentPane);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
    }

    public void start() {
        frame.setVisible(true);
        performPingPeriodically();
    }

    private void performPing() {
        try {
            String url = "https://n8n.*********.com.br/"; //substitua pelo seu link
            int timeout = 5000; // Tempo limite de 5 segundos

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);

            int responseCode = connection.getResponseCode();

            // Atualiza a interface gráfica com o resultado do teste de ping
            SwingUtilities.invokeLater(() -> {
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    statusLabel.setText("O host está respondendo corretamente.");
                } else {
                    statusLabel.setText("O host retornou o status de resposta HTTP: " + responseCode);
                    playSound();
                }
                logPingResult(responseCode);
            });
        } catch (SocketTimeoutException e) {
            SwingUtilities.invokeLater(() -> {
                statusLabel.setText("Ocorreu um erro de tempo limite: " + e.getMessage());
                logPingResult(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
                playSound();
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao executar a verificação de status de resposta HTTP.", e);
        }
    }

    private void performPingPeriodically() {
        Timer timer = new Timer();
        long interval = 20 * 60 * 1000; // Intervalo de meia hora em milissegundos

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                performPing();
            }
        }, 0, interval);
    }

    private void logPingResult(int responseCode) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String timestamp = dateFormat.format(new Date());
        String logMessage = String.format("[%s] Resultado do ping: %d\n", timestamp, responseCode);
        logTextArea.append(logMessage);
    }

    private void playSound() {
        try {
            String soundFile = "src/231277__steel2008__race-start-ready-go.wav";

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFile));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PingApp pingApp = new PingApp();
            pingApp.start();
        });
    }
}
