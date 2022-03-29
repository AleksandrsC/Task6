package com.accenture.bootcamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * based on example from https://github.com/xerial/sqlite-jdbc
 */
public class Main {
    private static final Logger log= LogManager.getLogger(Main.class);

    public static void main (String[] args) {
        try{
            Class.forName("org.sqlite.JDBC");
        }catch(Exception x){
            log.error("failed to load JDBC driver", x);
        }

        try(Connection connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = connection.createStatement();){

            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");
            ResultSet rs = statement.executeQuery("select * from person");
            while(rs.next()){
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(SQLException e){
            // if the error message is "out of memory",
            // it probably means no database file is found
            log.error("JDBC example error", e);
        }
    }
}


