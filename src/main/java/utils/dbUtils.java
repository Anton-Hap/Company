package utils;

import constants.Const;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class dbUtils {

    private static final String URL = "jdbc:mysql://127.0.0.1:8080/Company";
    private static final String USER = "root";
    private static final String PASSWORD = "Йцукен123";

    private static Connection connection;

    private static void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> crossRequest() {
        String query = "select заказчики.наименование,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0101' and '0131') as январь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0201' and '0228') as февраль,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0301' and '0331') as март,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0401' and '0430') as апрель,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0501' and '0531') as май,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0601' and '0630') as июнь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0701' and '0731') as июль,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0801' and '0831') as август,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '0901' and '0930') as сентябрь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '1001' and '1031') as октябрь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '1101' and '1130') as ноябрь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID and DATE_FORMAT(`Дата начала`, '%m%d') between '1201' and '1231') as декабрь,\n" +
                "                (select COUNT(*) from `учет работ` where `ID Заказчика` = заказчики.ID) as всего\n" +
                "                from заказчики left join `учет работ` on заказчики.ID = `учет работ`.`ID Заказчика` GROUP BY заказчики.наименование";

        ResultSet resultSet = null;
        List<List<String>> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToTableList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<List<String>> unionRequest() {
        String query = "select Наименование from техника\n" +
                "union\n" +
                "select ФИО from рабочие";
        ResultSet resultSet = null;
        List<List<String>> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToTableList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String finalRequest() {
        String query = "SELECT COUNT(`ID`) FROM `Поставщики`";

        ResultSet resultSet = null;
        String result = "";
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            resultSet.next();
            result = resultSet.getString(1);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<List<String>> parameterRequest(String parameter, String parameterField) {
        String query = "SELECT * FROM `Рабочие` WHERE `ЗП` " + parameter + " '" + parameterField + "'";

        ResultSet resultSet = null;
        List<List<String>> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToTableList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<List<String>> conditionalRequest() {
        String query = "SELECT * FROM `Учет работ` WHERE `СТАТУС` = '" + Const.STATUS_COMPLETED + "'";
;
        ResultSet resultSet = null;
        List<List<String>> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToTableList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static String getNextID(String tableName) {
        String query = " SELECT MAX(`ID`) FROM `" + tableName + "`";

        ResultSet resultSet = null;
        int request = 0;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            resultSet.next();
            if (resultSet.getString(1) != null) {
                request = Integer.parseInt(resultSet.getString(1)) + 1;
            } else {
                request = 1;
            }

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return String.valueOf(request);
    }

    public static List<String> getRow(String tableName, String id) {
        String query = "SELECT * FROM `" + tableName + "` WHERE id = '" + id + "'";

        ResultSet resultSet = null;
        List<String> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToRowList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getColumn(String tableName, String columnName) {
        String query = "SELECT `" + columnName + "` FROM `" + tableName + "`";

        ResultSet resultSet = null;
        List<String> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToRowList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void updateRow(String tableName, List<String> headNames, List<String> params, String id) throws SQLException {
        String query = "UPDATE `" + tableName + "` SET " + createSetString(headNames, params) + " WHERE `" + headNames.get(0) + "` = " + id;

        connect();

        Statement statement = connection.createStatement();
        statement.execute(query);

        close();
    }

    public static void removeColumn(String tableName, String columnName) {
        String query = "ALTER TABLE `" + tableName + "` DROP COLUMN `" + columnName + "` ";

        try {
            connect();

            Statement statement = connection.createStatement();
            statement.execute(query);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addColumn(String tableName, String columnName, String typeData) throws SQLException {
        String query = "ALTER TABLE `" + tableName + "` ADD COLUMN `" + columnName + "` " + typeData;

        connect();

        Statement statement = connection.createStatement();
        statement.execute(query);

        close();
    }

    public static void addRow(String tableName, List<String> headNames, List<String> params) throws SQLException {
        String query = "INSERT INTO `" + tableName + "`" + createInsertString(headNames, params);

        connect();

        Statement statement = connection.createStatement();
        statement.execute(query);

        close();
    }

    public static void removeRow(String tableName, String namePrimaryKey, String primaryKey) {
        String query = "DELETE FROM `" + tableName + "` WHERE `" + namePrimaryKey + "` = " + primaryKey;

        try {
            connect();

            Statement statement = connection.createStatement();
            statement.execute(query);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<List<String>> getTable(String tableName) {
        String query = "SELECT * FROM `" + tableName + "`";

        ResultSet resultSet = null;
        List<List<String>> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToTableList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getDataFromColumns(String tableName, List<String> columnsName, String id) {
        String query = "SELECT " + fromListToString(columnsName) + " FROM `" + tableName + "` WHERE `ID` = " + id;

        ResultSet resultSet = null;
        List<String> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToRowList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> getForeignKeyName(String tableName) {
        String query = "DESCRIBE `" + tableName + "`";

        ResultSet resultSet = null;
        List<List<String>> listTable = null;
        List<String> listColumns = new ArrayList<>();
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            listTable = fromResultToTableList(resultSet);

            for (List<String> strings : listTable) {
                String nameColumn = "";
                for (int j = 0; j < strings.size(); j++) {
                    if (j == 0) {
                        nameColumn = strings.get(j);
                    }

                    if (strings.get(j) != null) {
                        if (strings.get(j).equals("MUL")) {
                            listColumns.add(nameColumn);
                        }
                    }
                }
            }

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listColumns;
    }


    public static List<String> getNameColumns(String tableName) {
        String query = "DESCRIBE `" + tableName + "`";

        ResultSet resultSet = null;
        List<String> list = null;
        try {
            connect();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            list = fromResultToList(resultSet);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String createSetString(List<String> headNames, List<String> params) {
        String query = "";

        for (int i = 1; i < headNames.size(); i++) {
            if (i != headNames.size() - 1) {
                query += "`" + headNames.get(i) + "` = \'" + params.get(i) + "\', ";
            } else {
                query += "`" + headNames.get(i) + "` = \'" + params.get(i) + "\'";
            }
        }

        return query;
    }

    private static String fromListToString(List<String> list) {
        String s = "";

        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                s += "`" + list.get(i) + "`, ";
            } else {
                s += "`" + list.get(i) + "`";
            }
        }

        return s;
    }

    private static List<List<String>> fromResultToTableList(ResultSet resultSet) {
        List<List<String>> tableList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                List<String> rowList = new ArrayList<>();

                for (int  i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    rowList.add(resultSet.getString(i));
                }

                tableList.add(rowList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableList;
    }

    private static List<String> fromResultToRowList(ResultSet resultSet) {
        List<String> rowList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                for (int  i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    rowList.add(resultSet.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowList;
    }

    private static List<String> fromResultToList(ResultSet resultSet) {
        List<String> list = new ArrayList<>();

        int index = 1;
        try {
            while (resultSet.next()) {
                list.add(resultSet.getString(index));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String createInsertString(List<String> headNames, List<String> params) {
        String query = " (";
        for (int i = 1; i < headNames.size(); i++) {
            if (i != headNames.size() - 1) {
                query += "`" + headNames.get(i) + "`, ";
            } else {
                query += "`" + headNames.get(i) + "`";
            }
        }
        query += ")";

        query += " VALUES ";

        query += "(";
        for (int i = 1; i < params.size(); i++) {
            if (i != params.size() - 1) {
                query += "\'" + params.get(i) + "\', ";
            } else {
                query += "\'" + params.get(i) + "\'";
            }
        }
        query += ")";

        return query;
    }
}
