import java.sql.*;
import java.util.Scanner;

public class LostAndFoundSystem {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Lost and Found System =====");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View Lost Items");
            System.out.println("4. View Found Items");
            System.out.println("5. Match Lost and Found Items");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: reportLostItem(); break;
                case 2: reportFoundItem(); break;
                case 3: viewLostItems(); break;
                case 4: viewFoundItems(); break;
                case 5: matchItems(); break;
                case 6: System.out.println("Thanks for using the system, Dharani 👑!"); break;
                default: System.out.println("❌ Invalid choice.");
            }

        } while (choice != 6);
    }

    static void reportLostItem() {
        try {
            System.out.print("Enter lost item name: ");
            String name = sc.nextLine();
            System.out.print("Enter description: ");
            String desc = sc.nextLine();
            System.out.print("Enter date (yyyy-mm-dd): ");
            String date = sc.nextLine();

            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO lost_items (item_name, description, date_lost) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
            System.out.println("✅ Lost item reported.");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void reportFoundItem() {
        try {
            System.out.print("Enter found item name: ");
            String name = sc.nextLine();
            System.out.print("Enter description: ");
            String desc = sc.nextLine();
            System.out.print("Enter date (yyyy-mm-dd): ");
            String date = sc.nextLine();

            Connection con = DBConnection.getConnection();
            String sql = "INSERT INTO found_items (item_name, description, date_found) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setDate(3, Date.valueOf(date));
            ps.executeUpdate();
            System.out.println("✅ Found item reported.");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void viewLostItems() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM lost_items");

            System.out.println("\n📌 Lost Items:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Item: " + rs.getString("item_name")
                        + ", Description: " + rs.getString("description") + ", Date: " + rs.getDate("date_lost"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void viewFoundItems() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM found_items");

            System.out.println("\n📌 Found Items:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Item: " + rs.getString("item_name")
                        + ", Description: " + rs.getString("description") + ", Date: " + rs.getDate("date_found"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    static void matchItems() {
        try {
            Connection con = DBConnection.getConnection();
            Statement stLost = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            Statement stFound = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet lost = stLost.executeQuery("SELECT * FROM lost_items");
            ResultSet found = stFound.executeQuery("SELECT * FROM found_items");

            boolean matchFound = false;
            System.out.println("\n🔍 Matching Items:");

            while (lost.next()) {
                String lostItem = lost.getString("item_name").toLowerCase();

                found.beforeFirst(); // reset found result set
                while (found.next()) {
                    String foundItem = found.getString("item_name").toLowerCase();
                    if (lostItem.equals(foundItem)) {
                        System.out.println("\n🔗 Possible Match:");
                        System.out.println("Lost → " + lost.getString("item_name") + ": " + lost.getString("description"));
                        System.out.println("Found → " + found.getString("item_name") + ": " + found.getString("description"));
                        matchFound = true;
                    }
                }
            }

            if (!matchFound) {
                System.out.println("❌ No matches found.");
            }

            con.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}