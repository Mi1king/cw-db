package edu.uob;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;


/**
 * This class implements the DB server.
 */
public class DBServer {

    private static final char END_OF_TRANSMISSION = 4;
    private String storageFolderPath;

    public static void main(String args[]) throws IOException {
        DBServer server = new DBServer();
        loadDate();
        server.blockingListenOn(8888);
    }

    /**
     * KEEP this signature otherwise we won't be able to mark your submission correctly.
     */
    public DBServer() {
        storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        try {
            // Create the database storage folder if it doesn't already exist !
            Files.createDirectories(Paths.get(storageFolderPath));
        } catch (IOException ioe) {
            System.out.println("Can't seem to create database storage folder " + storageFolderPath);
        }
    }

    /**
     * KEEP this signature (i.e. {@code edu.uob.DBServer.handleCommand(String)}) otherwise we won't be
     * able to mark your submission correctly.
     *
     * <p>This method handles all incoming DB commands and carries out the required actions.
     */
    public String handleCommand(String command) {
        // TODO implement your server logic here
        return "";
    }


    //  === Methods below is helper ===
    public static void loadDate() {
        String filePath = ""; // TODO: use File.separator
        List<People> people = null;
        List<Shed> sheds = null;
        try {

            filePath = "databases" + File.separator + "people.tab"; // TODO: use File.separator
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
            } else {
                people = People.readFromTabFile(filePath);
            }

            filePath = "databases" + File.separator + "sheds.tab"; // TODO: use File.separator
            file = new File(filePath);
            if (!file.exists()) {
                System.out.println("File does not exist: " + filePath);
            } else {
                sheds = Shed.readFromTabFile(filePath);
            }
            Shed.populatePurchasers(sheds, people);
            printList(people);
            printList(sheds);
            people = randomAge(people);
            printList(people);
            printList(sheds);
            writeTabFile("databases" + File.separator + "people_randomAge.tab", people);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<People> randomAge(List<People> people) {
        for (People p :
                people) {
            Random rand = new Random();
            int age = rand.nextInt(100) + 1;
            p.setAge(age);
        }
        return people;
    }

    public static <T> void printList(List<T> list) {
        for (T obj : list) {
            System.out.println(obj.toString());
        }
    }

    public static void writeTabFile(String filePath, List<People> list) throws IOException {
        File file = new File(filePath);

        try (PrintWriter writer = new PrintWriter(file)) {
            // Write header row
            writer.println("id\tName\tAge\tEmail");

            // Write data rows
            for (People p : list) {
                writer.println(p.getId() + "\t" + p.getName() + "\t" + p.getAge() + "\t" + p.getEmail());
            }
        }
    }
    //  === Methods below handle networking aspects of the project - you will not need to change these ! ===

    public void blockingListenOn(int portNumber) throws IOException {
        try (ServerSocket s = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (!Thread.interrupted()) {
                try {
                    blockingHandleConnection(s);
                } catch (IOException e) {
                    System.err.println("Server encountered a non-fatal IO error:");
                    e.printStackTrace();
                    System.err.println("Continuing...");
                }
            }
        }
    }

    private void blockingHandleConnection(ServerSocket serverSocket) throws IOException {
        try (Socket s = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {

            System.out.println("Connection established: " + serverSocket.getInetAddress());
            while (!Thread.interrupted()) {
                String incomingCommand = reader.readLine();
                System.out.println("Received message: " + incomingCommand);
                String result = handleCommand(incomingCommand);
                writer.write(result);
                writer.write("\n" + END_OF_TRANSMISSION + "\n");
                writer.flush();
            }
        }
    }
}
