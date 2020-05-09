package hu.alkfejl.dao;

import hu.alkfejl.model.Contact;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContactDAOImpl implements ContactDAO {
    private static final String CONN_STR = "jdbc:sqlite:contact.db";
    private static final String SELECT_ALL_CONTACT = "SELECT * FROM Contact;";
    private static final String DELETE_CONTACT = "DELETE FROM Contact WHERE id = ?;";
    private static final String INSERT_CONTACT = "INSERT INTO Contact(name, email, workEmail, birth, position, " +
            "organization, address, workAddress, phone, workPhone, modifiedOn, picture) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_CONTACT = "UPDATE Contact SET name=?, email=?, workEmail=?, birth=?, position=?, " +
            "organization=?, address=?, workAddress=?, phone=?, workPhone=?, modifiedOn=?, picture=? WHERE id=?;";



    /*
    * Table in DB :
    * Contact(id, name, email, workEmail, birth, position, organization, address, workAddress, phone, workPhone, modifiedOn)
    *
    * */

    @Override
    public List<Contact> listAll() {
        List<Contact> result = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(CONN_STR);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(SELECT_ALL_CONTACT);


        ){
            while(rs.next()){
                Contact c = new Contact();
                InputStream input = rs.getBinaryStream(13);
                if(input != null){
                    InputStreamReader inputReader = new InputStreamReader(input);
                    if(inputReader.ready())
                    {
                        File tempFile = new File("tempFile.jpg");

                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] buffer = new byte[1024];
                        while(input.read(buffer) > 0){
                            fos.write(buffer);
                        }
                        Image image = new Image(tempFile.toURI().toURL().toString());
                        c.setProfilePicture(image);
                    }
                }

                c.setId(rs.getInt(1)); //id
                c.setName(rs.getString(2)); //name
                c.setEmail(rs.getString(3)); //email
                c.setWorkEmail(rs.getString(4)); //workEmail
                c.setBirth(rs.getString(5)); // birth
                c.setPosition(rs.getString(6)); //position
                c.setOrganization(rs.getString(7)); // organization
                c.setAddress(rs.getString(8)); // address
                c.setWorkAddress(rs.getString(9)); //workAddress
                c.setPhone(rs.getString(10)); //phone
                c.setWorkPhone(rs.getString(11)); //workPhone
                c.setModifiedOn(rs.getString(12));

                result.add(c);
            }

        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean add(Contact c) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement st = conn.prepareStatement(INSERT_CONTACT)) {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            st.setString(1, c.getName());
            st.setString(2, c.getEmail());
            st.setString(3, c.getWorkEmail());
            st.setString(4, c.getBirth());
            st.setString(5, c.getPosition());
            st.setString(6, c.getOrganization());
            st.setString(7, c.getAddress());
            st.setString(8, c.getWorkAddress());
            st.setString(9, c.getPhone());
            st.setString(10, c.getWorkPhone());
            st.setString(11, time.toString());
            st.setBytes(12, readFile(c.getProfilePictureString()));
            int res = st.executeUpdate();
            TimeUnit.SECONDS.sleep(5);
            if (res == 1) {
                return true;
            }
        } catch (SQLException |InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(Contact c) {
        try (Connection conn = DriverManager.getConnection(CONN_STR);
             PreparedStatement st = conn.prepareStatement(DELETE_CONTACT)) {
            st.setInt(1, c.getId());

            TimeUnit.SECONDS.sleep(5);
            int res = st.executeUpdate();

            return res == 1;

        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Contact c) {
        try (Connection conn = DriverManager.getConnection(CONN_STR); PreparedStatement st = conn.prepareStatement(UPDATE_CONTACT)) {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            st.setString(1, c.getName());
            st.setString(2, c.getEmail());
            st.setString(3, c.getWorkEmail());
            st.setString(4, c.getBirth());
            st.setString(5, c.getPosition());
            st.setString(6, c.getOrganization());
            st.setString(7, c.getAddress());
            st.setString(8, c.getWorkAddress());
            st.setString(9, c.getPhone());
            st.setString(10, c.getWorkPhone());
            st.setString(11, time.toString());
            st.setBytes(12, readFile(c.getProfilePictureString()));
            st.setInt(13, c.getId());
            TimeUnit.SECONDS.sleep(5);
            int res = st.executeUpdate();
            return res == 1;
        } catch (SQLException |InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ContactDAOImpl() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }

}
