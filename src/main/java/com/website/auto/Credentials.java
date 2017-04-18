package com.website.auto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

public class Credentials {
    private String user;
    private String pswd;

    public Credentials(String host) {
        DataSource ds = DataSourceFactory.getMySQLDataSource();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            rs = stmt.executeQuery("select username, aes_decrypt(password, 's3cr3t') as 'password' from credentials");

            while (rs.next()) {
                this.user = rs.getString("username");
                this.pswd = rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String getUser() {
        return this.user;
    }

    public String getPswd() {
        return this.pswd;
    }
}
