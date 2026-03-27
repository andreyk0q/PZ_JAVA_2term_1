import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DistanceCalculatorGUI extends JFrame {

    private JTextField lat1Field;
    private JTextField lon1Field;
    private JTextField lat2Field;
    private JTextField lon2Field;
    private JTextField resultField;

    public DistanceCalculatorGUI() {
        setTitle("Обчислення відстані між двома точками");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        panel.add(new JLabel("Широта 1 (lat1, град):"));
        lat1Field = new JTextField();
        panel.add(lat1Field);

        panel.add(new JLabel("Довгота 1 (lon1, град):"));
        lon1Field = new JTextField();
        panel.add(lon1Field);

        panel.add(new JLabel("Широта 2 (lat2, град):"));
        lat2Field = new JTextField();
        panel.add(lat2Field);

        panel.add(new JLabel("Довгота 2 (lon2, град):"));
        lon2Field = new JTextField();
        panel.add(lon2Field);

        panel.add(new JLabel("Відстань (м):"));
        resultField = new JTextField();
        resultField.setEditable(false);
        panel.add(resultField);

        JButton solveButton = new JButton("Solve");
        JButton clearButton = new JButton("Clear");

        panel.add(solveButton);
        panel.add(clearButton);

        add(panel);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateDistance();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lat1Field.setText("");
                lon1Field.setText("");
                lat2Field.setText("");
                lon2Field.setText("");
                resultField.setText("");
            }
        });
    }

    private void calculateDistance() {
        try {
            double lat1 = Double.parseDouble(lat1Field.getText());
            double lon1 = Double.parseDouble(lon1Field.getText());
            double lat2 = Double.parseDouble(lat2Field.getText());
            double lon2 = Double.parseDouble(lon2Field.getText());

            double R = 6371e3; // радіус Землі в метрах

            double phi1 = Math.toRadians(lat1);
            double phi2 = Math.toRadians(lat2);
            double deltaPhi = Math.toRadians(lat2 - lat1);
            double deltaLambda = Math.toRadians(lon2 - lon1);

            double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2)
                    + Math.cos(phi1) * Math.cos(phi2)
                    * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            double distance = R * c;

            resultField.setText(String.format("%.2f", distance));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Будь ласка, введіть коректні числові значення!",
                    "Помилка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DistanceCalculatorGUI().setVisible(true);
        });
    }
}