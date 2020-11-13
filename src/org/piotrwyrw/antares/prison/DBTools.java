package org.piotrwyrw.antares.prison;

import org.piotrwyrw.antares.prison.utils.MessageSender;

import java.sql.*;

public class DBTools {
    private Connection connection;
    private MessageSender msd;

    public DBTools(String database) {
        this.msd = AntaresPrison.getInstance().msd;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + AntaresPrison.getInstance().getDataFolder().getPath() + "/" + database);
            if (!connection.isValid(100) || connection.isClosed() || connection == null) {
                msd.toAllAdmins("&7Database connection invalid.", true);
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection.isClosed())
                return;
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean query(String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public ResultSet result_query(String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return statement.getResultSet();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean tableExists(String name) {
        try {
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rslt = meta.getTables(null, null, name, null);
            if (!rslt.next()) {
                return false;
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void createTable(String name, String contents) {
        if (tableExists(name))
            return;
        String query = "CREATE TABLE " + name + " ( " + contents + " );";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeQuery(query);
        } catch (SQLException throwables) {}
    }

    public void insertTable(String name, String values) {
        if (!tableExists(name))
            return;
        // INSERT INTO users (uuid, balance, tickets) VALUES ("UUID", 20.0, "D | Y | Z");
        String query = "INSERT INTO " + name + " VALUES ( " + values + " );";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean ifQuery_any(String table, String condition) {
        if (!tableExists(table))
            return false;
        String query = "SELECT * FROM " + table + " " + condition + ";";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next())
                return false;
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean ifQuery_where(String table, String condition) {
        if (!tableExists(table))
            return false;
        String query = "SELECT * FROM " + table + " WHERE " + condition + ";";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next())
                return false;
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public String stringFromTable(String name, String condition, String column) {
        if (!tableExists(name))
            return null;
        String query = "SELECT * FROM " + name + " WHERE " + condition + ";";
        Statement statement = null;
        ResultSet resultSet = null;
        String string = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next())
                return null;
            string = resultSet.getString(column);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return string;
    }

    public double doubleFromTable(String name, String condition, String column) {
        if (!tableExists(name))
            return 0.0d;
        String query = "SELECT * FROM " + name + " WHERE " + condition;
        Statement statement = null;
        ResultSet resultSet = null;
        double d = 0.0d;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            d = resultSet.getDouble(column);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return d;
    }

}
