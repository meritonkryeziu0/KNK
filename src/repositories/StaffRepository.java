package repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import database.DBConnection;
import database.InsertQueryBuilder;
import helpers.Staff;

public class StaffRepository {

    private static DBConnection connection = DBConnection.getConnection();


    public static ArrayList<Staff> filterPosition(String position) throws Exception {
        String query = "select * from staff where position = ?";
        ArrayList<Staff> staffMembers = new ArrayList<Staff>();

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, position);
        ResultSet result = stmt.executeQuery();

        while (result.next()) {
            staffMembers.add(fromResultSet(result));
        }

        if (staffMembers != null) return staffMembers;

        return null;
    }


    public static Staff fromResultSet(ResultSet res) throws Exception {
        int id = res.getInt("id");
        String first_name = res.getString("first_name");
        String last_name = res.getString("last_name");
        String personalNumber = res.getString("personal_number");
        String position = res.getString("position");
        String birthdate = res.getString("birthdate");
        String phone_number = res.getString("phone_number");
        double salary = res.getDouble("salary");
        String password = res.getString("password");
        String gender = res.getString("gender");

        Staff staff = new Staff(id, first_name, last_name, personalNumber, phone_number, gender, birthdate, position,
                salary, password);
        return staff;
    }

    public ArrayList<Staff> findAll() throws Exception {
        String query = "select * from staff";
        ResultSet res = this.connection.executeQuery(query);
        ArrayList<Staff> staff = new ArrayList<>();
        while (res.next()) {
            System.out.println("Name: " + res.getString("last_name"));
            staff.add(fromResultSet(res));
        }
        return staff;
    }


    public static Staff find(int id) throws Exception {
        String query = "select * from staff where id = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);

        ResultSet result = stmt.executeQuery();
        if (!result.next()) {
            return null;
        }
        return fromResultSet(result);
    }

    public static Staff create(Staff model) throws Exception {
        InsertQueryBuilder query = (InsertQueryBuilder) InsertQueryBuilder.create("staff")
                .add("id", model.getId(), "i")
                .add("first_name", model.getFirst_name(), "s")
                .add("last_name", model.getLast_name(), "s")
                .add("personal_number", model.getPersonal_number(), "s")
                .add("position", model.getPosition(), "s")
                .add("birthdate", model.getBirthdate(), "s")
                .add("phone_number", model.getPhone_number(), "s")
                .add("salary", (float) model.getSalary(), "f")
                .add("password", model.getPassword(), "s")
                .add("gender", model.getGender(), "s");


        int lastInsertedId = connection.execute(query);

        Staff newStaff = find(lastInsertedId);

        if (newStaff != null) {
            return newStaff;
        }
        return null;
    }


    public static Staff update(Staff model) throws Exception {
        String query =  "update staff set first_name = ? , last_name = ? ,personal_number = ?, " +
                "position = ? ,birthdate = ? , phone_number = ? , salary = ? , password = ? , " +
                "gender = ?  where id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, model.getFirst_name());
        stmt.setString(2 , model.getLast_name());
        stmt.setString(3, model.getPersonal_number());
        stmt.setString(4, model.getPosition());
        stmt.setString(5 , model.getBirthdate());
        stmt.setString(6 , model.getPhone_number());
        stmt.setDouble( 7 , model.getSalary());
        stmt.setString( 8 , model.getPassword());
        stmt.setString(9 , model.getGender());
        stmt.setInt(10 , model.getId());

        int affectedRows = stmt.executeUpdate();
        if(affectedRows != 1 ) {
            throw new Exception("ERR_NO_ROW_CHANGE");
        }

        return find(model.getId());
    }


    public static boolean remove(int id) throws Exception {
        String query = "delete from staff where id = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, id);

        return stmt.executeUpdate() == 1;
    }


}
