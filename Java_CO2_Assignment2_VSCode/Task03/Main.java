import java.util.*;

class Student {
    private final String regNo;
    private final String name;

    Student(String regNo, String name) {
        this.regNo = regNo;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return regNo.equals(other.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regNo);
    }

    @Override
    public String toString() {
        return regNo + " " + name;
    }
}

public class Main {
    public static void main(String[] args) {
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));

        System.out.println("Unique students: " + present.size());
        System.out.println("Students in the set:");
        for (Student student : present) System.out.println(student);

        System.out.println("\nAdditional equality test:");
        Student first = new Student("192311003", "Kavin");
        Student second = new Student("192311003", "Kavin Duplicate");
        System.out.println("equals(): " + first.equals(second));
        System.out.println("Same hash code: " + (first.hashCode() == second.hashCode()));
    }
}
