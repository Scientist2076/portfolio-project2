import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoadSignQuizApp extends JFrame implements ActionListener {

    String[] emojiQuestions = {
        "⚠️  What does this road sign indicate?",
        "🚫  What does this sign mean?",
        "🛑  Which shape does the stop sign use?",
        "🔵  What does this blue sign indicate?",
        "⚠️  Which color is typically used for warning signs?"
    };

    String[][] options = {
        {"Stop", "Mandatory", "Warning", "Parking"},
        {"Direction", "Warning", "Prohibition", "Information"},
        {"Circle", "Triangle", "Octagon", "Square"},
        {"Mandatory instruction", "Warning", "Stop", "Prohibition"},
        {"Red", "Yellow", "Blue", "Green"}
    };

    int[] answers = {2, 2, 2, 0, 1};

    JLabel titleLabel, questionLabel, timerLabel;
    JRadioButton[] optionButtons;
    ButtonGroup group;
    JButton nextButton;

    int currentQuestion = 0;
    int score = 0;
    int timeLeft = 15;
    Timer countdownTimer;

    public RoadSignQuizApp() {
        setTitle("Road Sign Emoji Quiz");
        setSize(700, 450);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🔷 Set main background color
        getContentPane().setBackground(new Color(240, 100, 200));  // Alice Blue

        // 🔶 Title label
        titleLabel = new JLabel("🚦 ROAD SIGN QUIZ 🚦", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180)); // Steel Blue
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(700, 60));
        add(titleLabel, BorderLayout.NORTH);

        // 🟡 Center panel for question + options
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 248, 255));

        // Question label
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(questionLabel, BorderLayout.NORTH);

        // Options
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        optionsPanel.setBackground(new Color(240, 248, 255));
        optionButtons = new JRadioButton[4];
        group = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
            optionButtons[i].setBackground(new Color(240, 248, 255));
            group.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // ⏱ Bottom panel with timer + Next button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 248, 255));

        timerLabel = new JLabel("Time left: 15s", SwingConstants.LEFT);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        bottomPanel.add(timerLabel, BorderLayout.WEST);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.addActionListener(this);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 248, 255));
        buttonPanel.add(nextButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        loadQuestion(currentQuestion);
        startTimer();

        setVisible(true);
    }

    private void loadQuestion(int index) {
        questionLabel.setText("Q" + (index + 1) + ": " + emojiQuestions[index]);
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(options[index][i]);
            optionButtons[i].setSelected(false);
        }
        resetTimer();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        evaluateAnswer();
    }

    private void evaluateAnswer() {
        countdownTimer.stop();

        int selected = -1;
        for (int i = 0; i < 4; i++) {
            if (optionButtons[i].isSelected()) {
                selected = i;
                break;
            }
        }

        if (selected == answers[currentQuestion]) {
            score++;
        }

        currentQuestion++;
        if (currentQuestion < emojiQuestions.length) {
            loadQuestion(currentQuestion);
            startTimer();
        } else {
            showResult();
        }
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this,
            "🎉 Quiz Complete!\nYour Score: " + score + " / " + emojiQuestions.length,
            "Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private void startTimer() {
        timeLeft = 15;
        timerLabel.setText("Time left: " + timeLeft + "s");
        countdownTimer = new Timer(1000, e -> {
            timeLeft--;
            timerLabel.setText("Time left: " + timeLeft + "s");
            if (timeLeft <= 0) {
                countdownTimer.stop();
                JOptionPane.showMessageDialog(this, "⏰ Time's up! Moving to next question.");
                evaluateAnswer();
            }
        });
        countdownTimer.start();
    }

    private void resetTimer() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        timeLeft = 15;
        timerLabel.setText("Time left: " + timeLeft + "s");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoadSignQuizApp::new);
    }
}
