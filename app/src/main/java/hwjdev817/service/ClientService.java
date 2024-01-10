package hwjdev817.service;

import hwjdev817.storage.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientService {
    private PreparedStatement createClient;
    private PreparedStatement getByIdClient;
    private PreparedStatement setNameClient;
    private PreparedStatement deleteByIdClient;
    private PreparedStatement listAllClient;
    private PreparedStatement selectMaxIdClient;
    private PreparedStatement existsByIdClient;
    private PreparedStatement clearClient;
    private PreparedStatement searchClient;
    public ClientService(Database database) throws SQLException {

        Connection connection =database.getConnection();
        createClient = connection.prepareStatement(
                "INSERT INTO  client (id, name)  VALUES (?, ?)"
        );
        selectMaxIdClient = connection.prepareStatement(
                "SELECT max(id) AS maxID FROM client"
        );
        getByIdClient = connection.prepareStatement(
                "SELECT name FROM client WHERE id= ? "
        );
        setNameClient = connection.prepareStatement(
                "UPDATE  client SET name= ?  WHERE id= ? "
        );
        deleteByIdClient = connection.prepareStatement(
                "DELETE   FROM client WHERE id= ? "
        );
        listAllClient = connection.prepareStatement(
                "SELECT *  FROM client "
        );
        existsByIdClient= connection.prepareStatement(
                "SELECT count(*) > 0 AS clientExists FROM client WHERE id= ?"
        );
        clearClient = connection.prepareStatement(
                "DELETE FROM client  "
        );
        searchClient = connection.prepareStatement(
                " SELECT id, name FROM client WHERE name LIKE ?"
        );
    }
    public   long create(String name) throws  IllegalArgumentException ,SQLException{

        if (name.equals("")) {
            throw new IllegalArgumentException("Name can not be empty!");
                }
        if (name.length()>1000) {
            throw new IllegalArgumentException("Length name too long!"+name);
                 }
        char[] chars = {'@', '#', '%', '&'};
        for(int j=0;j<name.length();j++) {
            for (int i = 0; i <= 3; i++) {
                if (name.charAt(j)==chars[i]) {
                    throw new IllegalArgumentException ("Invalid characters in name! " + chars[i]+"  "+name);
                  }
            }
        }
        long id = -1;
        try(ResultSet rs = selectMaxIdClient.executeQuery()){
            rs.next();
            id = rs.getLong("maxId");
        }
        id =id+1;
        createClient.setLong(1, id);
        createClient.setString(2, name);
        createClient.executeUpdate();
        return id;
    }
    public  String getById(long id) throws IllegalArgumentException ,NullPointerException, SQLException {
        if (id<0 || Long.toString(id) == ""|| Long.toString(id) == null) {
            throw new IllegalArgumentException("Id incorrect value!");
        }
        if ( Long.toString(id) == null) {
            throw new NullPointerException ("Id is null!");
        }

        getByIdClient.setLong(1,id);
        try(ResultSet rs = getByIdClient.executeQuery()){
            if(!rs.next()){
                return null;
            }
            return rs.getString("name");
        }
    }
    public  void setName(long id, String name) throws IllegalArgumentException ,NullPointerException, SQLException {
        if (id<0 || Long.toString(id) == ""|| Long.toString(id) == null) {
            throw new IllegalArgumentException("Id incorrect value!");
        }
        if ( Long.toString(id) == null) {
            throw new NullPointerException ("Id is null!");
        }

        if (name.equals("")) {
            throw new IllegalArgumentException("Name can not be empty!");
        }
        if (name.length()>1000) {
            throw new IllegalArgumentException("Length name too long!"+name);
        }
        char[] chars = {'@', '#', '%', '&'};
        for(int j=0;j<name.length();j++) {
            for (int i = 0; i <= 3; i++) {
                if (name.charAt(j)==chars[i]) {
                    throw new IllegalArgumentException ("Invalid characters in name! " + chars[i]+"  "+name);
                }
            }
        }

        setNameClient.setString(1,name);
        setNameClient.setLong(2,id);
        setNameClient.executeUpdate();

    }
    public  void deleteById(long id) throws IllegalArgumentException ,NullPointerException, SQLException {
        if (id<0 || Long.toString(id) == ""|| Long.toString(id) == null) {
            throw new IllegalArgumentException("Id incorrect value!");
        }
        if ( Long.toString(id) == null) {
            throw new NullPointerException ("Id is null!");
        }

        deleteByIdClient.setLong(1,id);
        deleteByIdClient.executeUpdate();
    }
    public  List<Client> listAll() throws SQLException{
        try (ResultSet rs = listAllClient.executeQuery()){
            List<Client> result =new ArrayList<>();
            while (rs.next()){
                Client client =new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));

                result.add(client);
            }
            return result;
        }

    }
    public boolean exists(long id ) throws SQLException{
        existsByIdClient.setLong(1,id);
        try(ResultSet rs = existsByIdClient.executeQuery()){
            rs.next();
            return  rs.getBoolean("clientExists");
        }
    }

    public long save (Client client) throws Exception,SQLException {
        if (exists(client.getId())){
            setName(client.getId(), client.getName());
            return client.getId();
        }
        return create(client.getName());
    }
    public void clear() throws SQLException {
        clearClient.executeUpdate();
    }
    public  List<Client> searchByName(String query) throws SQLException{
        searchClient.setString(1,"%"+query +"%");
        try (ResultSet rs = searchClient.executeQuery()){
            List<Client> result =new ArrayList<>();
            while (rs.next()){
                Client client =new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));

                result.add(client);
            }
            return result;
        }

    }
}

