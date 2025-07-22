import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;


public abstract class Audit {
    private static String filepath = "AUDIT.CSV";

    // CREATE
    public static void createAudit() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            LocalDate current_date = LocalDate.now();
            writer.write("INSERT, " + current_date);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Error while writing to file\n");
        }
    }

    // READ
    public static void readAudit() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            LocalDate current_date = LocalDate.now();
            writer.write("SELECT, " + current_date);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Error while writing to file\n");
        }
    }

    // UPDATE
    public static void updateAudit() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            LocalDate current_date = LocalDate.now();
            writer.write("UPDATE, " + current_date);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Error while writing to file\n");
        }
    }

    // DELETE
    public static void deleteAudit() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            LocalDate current_date = LocalDate.now();
            writer.write("DELETE, " + current_date);
            writer.newLine();
        } catch (IOException e){
            System.out.println("Error while writing to file\n");
        }
    }
}
