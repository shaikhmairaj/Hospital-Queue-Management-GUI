

public class Patient implements Comparable<Patient> {
    String name;
    int age;
    String disease;
    int severity;

    public Patient(String name, int age, String disease, int severity) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.severity = severity;
    }

    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.severity, other.severity);
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Disease: " + disease + ", Severity: " + severity;
    }
}
