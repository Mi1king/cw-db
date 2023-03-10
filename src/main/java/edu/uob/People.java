package edu.uob;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class People {
    private final int id;
    private String name;
    private int age;
    private String email;

    public People(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static List<People> readFromTabFile(String filePath) throws IOException {
        List<People> people = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Trim whitespace from the line
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] data = line.split("\t");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String email = data[3];
                People person = new People(id, name, age, email);
                people.add(person);
            }
        }
        return people;
    }

    @Override
    public String toString() {
        return "People{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
