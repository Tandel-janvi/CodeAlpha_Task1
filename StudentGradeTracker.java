import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker extends JFrame {

    private JTextField nameField, gradeField;
    private JTable table;
    private DefaultTableModel model;
    private ArrayList<Student> students;

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        students = new ArrayList<>();
        setLayout(new BorderLayout(10, 10));

        // Input Form
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(10);
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField(5);
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add");
        inputPanel.add(addButton);

        JButton statsButton = new JButton("Show Summary");
        inputPanel.add(statsButton);

        add(inputPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "Student Name", "Grade" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Status Label
        JLabel statusLabel = new JLabel("Enter student name and grade, then click Add.");
        add(statusLabel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addStudent(statusLabel));
        statsButton.addActionListener(e -> showSummary());

        setVisible(true);
    }

    private void addStudent(JLabel statusLabel) {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            statusLabel.setText(" Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            students.add(new Student(name, grade));
            model.addRow(new Object[] { name, grade });
            nameField.setText("");
            gradeField.setText("");
            statusLabel.setText(" Student added successfully.");
        } catch (NumberFormatException ex) {
            statusLabel.setText(" Invalid grade. Please enter a numeric value.");
        }
    }

    private void showSummary() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No student data available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = 0, highest = students.get(0).grade, lowest = students.get(0).grade;

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest)
                highest = s.grade;
            if (s.grade < lowest)
                lowest = s.grade;
        }

        double average = total / students.size();

        String summary = String.format(
                "Number of Students: %d\nAverage Grade: %.2f\nHighest Grade: %.2f\nLowest Grade: %.2f",
                students.size(), average, highest, lowest);

        JOptionPane.showMessageDialog(this, summary, "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::new);
    }
}
