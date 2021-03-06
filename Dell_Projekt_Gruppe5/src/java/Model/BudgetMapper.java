
package Model;

import Entities.Budget;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * @author Frederik **
 */
public class BudgetMapper {

    protected BudgetMapper() {
        try {
            Class.forName(DBDetail.DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected ArrayList<Budget> getAllPrices() {
        ArrayList<Budget> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {

            String sql = "select kno, navn, pris from partner join kampagne on partner.pno = kampagne.pno where status != 'Pending'";
            PreparedStatement statement = null;

            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Budget b = new Budget(rs.getInt(1), rs.getString(2), rs.getFloat(3));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    protected int getStartingFund() {
        int i = 0;
        try (Connection connection = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sql = "select starts_belob from budget";
            PreparedStatement statement = null;
            ResultSet rs = null;

            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            rs.next();
            i = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }


    protected int getCurrentFund() {
        int i = 0;
        try (Connection connection = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sql = "select nuvaernde_belob from budget";
            PreparedStatement statement = null;
            ResultSet rs = null;

            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();
            rs.next();
            i = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    protected boolean updateMoneyUsed(int i) {
        int j = 0;
        try (Connection connection = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sql = "update budget set nuvaernde_belob = ? where starts_belob = ?";
            PreparedStatement statement = null;

            statement = connection.prepareStatement(sql);
            statement.setInt(1, getCurrentFund() + i);
            statement.setInt(2, getStartingFund());
            j = statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return j == 1;
    }

    protected boolean newQuarterBudget(int i) {
        int j = 0;
        try (Connection connection = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sql1 = "DROP table budget cascade constraints";
            String sql2 = "Create table budget(starts_belob float, nuvaernde_belob float)";
            String sql3 = "insert into budget values (?,0)";
            PreparedStatement statement = connection.prepareStatement(sql1);
            statement.executeUpdate();
            statement = connection.prepareStatement(sql2);
            statement.executeUpdate();
            statement = connection.prepareStatement(sql3);
            statement.setInt(1, i);
            j += statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return j == 1;
    }
}
