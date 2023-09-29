import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class StudentGradeCalculator {
    private JFrame frame;
    private JTextField[] subjectFields;
    private JTextField totalMarksField;
    private JTextField averagePercentageField;
    private JTextField gradeField;
    private int currentSubject = 0; // Index of the currently focused subject field

    public StudentGradeCalculator() {
        frame = new JFrame("Grade Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 400)); // Set preferred window size

        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JPanel resultPanel = new JPanel(new GridLayout(0, 2));

        int numSubjects = 5; // You can change the number of subjects as needed

        subjectFields = new JTextField[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            JLabel label = new JLabel("Subject " + (i + 1) + " Marks:");
            inputPanel.add(label);
            subjectFields[i] = new JTextField(5);
            inputPanel.add(subjectFields[i]);

            // Add a key listener to move to the next subject when the user presses Enter
            subjectFields[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        moveToNextSubject();
                    }
                }
            });
        }

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplay();
            }
        });

        inputPanel.add(calculateButton);

        inputPanel.add(new JLabel("Total Marks:"));
        totalMarksField = new JTextField(10);
        totalMarksField.setEditable(false);
        inputPanel.add(totalMarksField);

        inputPanel.add(new JLabel("Average Percentage:"));
        averagePercentageField = new JTextField(10);
        averagePercentageField.setEditable(false);
        inputPanel.add(averagePercentageField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField(5);
        gradeField.setEditable(false);
        inputPanel.add(gradeField);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.setVisible(true);
    }

    private void calculateAndDisplay() {
        // Calculate total marks and average percentage
        int numSubjects = subjectFields.length;
        int[] marks = new int[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            try {
                marks[i] = Integer.parseInt(subjectFields[i].getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input for subject " + (i + 1), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int totalMarks = 0;
        for (int mark : marks) {
            totalMarks += mark;
        }

        double averagePercentage = (double) totalMarks / (numSubjects * 100) * 100;

        // Assign grade
        String grade;
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        totalMarksField.setText(Integer.toString(totalMarks));
        averagePercentageField.setText(String.format("%.2f%%", averagePercentage));
        gradeField.setText(grade);
    }

    private void moveToNextSubject() {
        currentSubject++;
        if (currentSubject < subjectFields.length) {
            subjectFields[currentSubject].requestFocus();
        } else {
            calculateAndDisplay(); // If all subjects are entered, calculate and display results
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeCalculator();
            }
        });
    }
}
