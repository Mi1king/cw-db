package edu.uob;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Shed {
    private final int id;
    private String name;
    private int height;
    private int purchaserId;
    private People purchaser;


    public Shed(int id, String name, int height, int purchaserId) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.purchaserId = purchaserId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getPurchaserId() {
        return purchaserId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPurchaserId(int purchaserId) {
        this.purchaserId = purchaserId;
    }

    public void setPurchaser(People purchaser) {
        this.purchaser = purchaser;
    }

    public People getPurchaser() {
        return purchaser;
    }

    public static List<Shed> readFromTabFile(String filePath) {
        List<Shed> sheds = new ArrayList<>();
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
                int height = Integer.parseInt(data[2]);
                int purchaserId = Integer.parseInt(data[3]);
                Shed shed = new Shed(id, name, height, purchaserId);
                sheds.add(shed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sheds;
    }

    public static void populatePurchasers(List<Shed> sheds, List<People> people) {
        for (Shed shed : sheds) {
            for (People person : people) {
                if (person.getId() == shed.getPurchaserId()) {
                    shed.setPurchaser(person);
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Shed{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", height=" + height +
                ", purchaserId=" + purchaserId +
                '}';
    }
}
