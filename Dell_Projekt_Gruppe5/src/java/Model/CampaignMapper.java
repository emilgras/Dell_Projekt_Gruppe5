package Model;

import Entities.Campaign;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * @author Frederik **
 */
public class CampaignMapper {

    protected CampaignMapper() {
        try {
            Class.forName(DBDetail.DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean insertCampaign(Campaign camp) {
        int rowsInserted = 0;
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "insert into kampagne values (?,?,?,?,?,?,?,?)";
            String primary = "select kno_increment.nextval from dual";
            PreparedStatement statement = null;

            PreparedStatement stmt = con.prepareStatement(primary);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                camp.setKno(rs.getInt(1));
            }

            statement = con.prepareStatement(sqlString);

            statement.setInt(1, camp.getKno());
            statement.setString(2, camp.getDescription());
            statement.setString(3, "Pending");
            statement.setString(4, "Pending");
            statement.setString(5, camp.getStart_date());
            statement.setString(6, camp.getEnd_date());
            statement.setFloat(7, camp.getPrice());
            statement.setInt(8, camp.getPno());

            rowsInserted += statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CampaignMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return (rowsInserted == 1);
    }

    protected boolean acceptCampaign(int kno) {
        boolean status = true;
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString2 = "update kampagne set oprettelse_dato = ? where kno = ?";

            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            PreparedStatement statement = null;
            Date date = new Date();
            String sdate = (dateFormat.format(date));
            statement = con.prepareStatement(sqlString2);
            statement.setInt(2, kno);
            statement.setString(1, sdate);

            statement.executeUpdate();

        } catch (SQLException ex) {
            status = false;
            ex.printStackTrace();
        }
        return status;
    }

    protected boolean updateCampaign(int kno) {
        int rowsUpdated = 0;
        String status = getCampaignStatus(kno);
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "update kampagne set status = ? where kno = ?";

            PreparedStatement statement = null;
            statement = con.prepareStatement(sqlString);
            status = status.toLowerCase();

            switch (status) {
                case "pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "In-Progress");
                    break;

                case "in-progress":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Pending");
                    break;

                case "poe declined":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Pending");
                    break;

                case "poe pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Accepted");
                    break;

                case "poe accepted":
                    statement.setInt(2, kno);
                    statement.setString(1, "Invoice Pending");
                    break;

                case "invoice pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "Complete");
                    break;

                case "complete":
                    statement.setInt(2, kno);
                    statement.setString(1, "Complete");
                    break;

                default:
                    statement.setInt(2, kno);
                    statement.setString(1, "Noget gik update galt");
                    break;
            }
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CampaignMapper.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return !status.equals(getCampaignStatus(kno));
    }

    protected boolean rollBackCampaign(int kno) {
        int rowsUpdated = 0;
        String status = getCampaignStatus(kno);
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "update kampagne set status = ? where kno = ?";
            PreparedStatement statement = null;
            statement = con.prepareStatement(sqlString);
            status = status.toLowerCase();

            switch (status) {
                case "pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "Pending");
                    break;

                case "in-progress":
                    statement.setInt(2, kno);
                    statement.setString(1, "Pending");
                    break;

                case "poe pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Declined");
                    break;

                case "poe accepted":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Pending");
                    break;

                case "invoice pending":
                    statement.setInt(2, kno);
                    statement.setString(1, "POE Accepted");
                    break;

                case "poe declined":
                    statement.setInt(2, kno);
                    statement.setString(2, "POE Declined");
                    break;

                case "complete":
                    statement.setInt(2, kno);
                    statement.setString(1, "Invoice Pending");
                    break;

                default:
                    statement.setInt(2, kno);
                    statement.setString(1, "Noget gik galt");
                    break;
            }
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CampaignMapper.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return !status.equals(getCampaignStatus(kno));

    }

    protected String getCampaignStatus(int kno) {
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select status from kampagne where kno = ?";
            PreparedStatement statement = null;

            statement = con.prepareStatement(sqlString);
            statement.setInt(1, kno);
            ResultSet rs = statement.executeQuery();

            String status = "tom";
            if (rs.next()) {
                status = rs.getString(1);
            }

            return status;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Something Went Wrong";
    }

    protected int getPris(int kno) {
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select pris from kampagne where kno = ?";
            PreparedStatement statement = null;

            statement = con.prepareStatement(sqlString);
            statement.setInt(1, kno);
            ResultSet rs = statement.executeQuery();

            int i = 0;
            if (rs.next()) {
                i = rs.getInt(1);
            }

            return i;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected ArrayList<Campaign> getAllPendingCampaigns() {
        ArrayList<Campaign> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select kno,beskrivelse,status,oprettelse_dato,start_dato,slut_dato,pris,kampagne.pno,navn,cvr from kampagne join partner on kampagne.PNO = PARTNER.PNO";
            PreparedStatement statement = null;

            statement = con.prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Campaign tmp = new Campaign(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getInt(8), rs.getString(9), rs.getString(10));

                tmp.setKno(rs.getInt(1));
                if (tmp.getStatus().equals("Pending") || tmp.getStatus().equals("POE Pending") || tmp.getStatus().equals("Invoice Pending")) {
                    list.add(tmp);
                }
            }

        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * * Returnerer alle kampagner sorteret med den nyest oprettet først **
     */
    protected ArrayList<Campaign> getAllNewestCampaigns() {
        ArrayList<Campaign> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select kno,beskrivelse,status,oprettelse_dato,start_dato,slut_dato,pris,kampagne.pno,navn,cvr from kampagne join partner on kampagne.PNO = PARTNER.PNO order by oprettelse_dato desc";
            PreparedStatement statement = null;
            int count = 0;

            statement = con.prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Campaign tmp = new Campaign(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getInt(8), rs.getString(9), rs.getString(10));
                tmp.setKno(rs.getInt(1));
                if (!tmp.getCreated_date().equals("Pending")) {
                    list.add(tmp);
                    count++;
                }
            }

        } catch (SQLException e) {
        }

        return list;
    }

    protected ArrayList<Campaign> getAllPartnerAcceptedCampaigns() {
        ArrayList<Campaign> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select kno,beskrivelse,status,oprettelse_dato,start_dato,slut_dato,pris,kampagne.pno,navn,cvr from kampagne where kampagne.PNO = PARTNER.PNO AND cvr = ?";
            PreparedStatement statement = null;
            int count = 0;

            statement = con.prepareStatement(sqlString);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Campaign tmp = new Campaign(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getInt(8), rs.getString(9), rs.getString(10));
                tmp.setKno(rs.getInt(1));
                if (!tmp.getCreated_date().equals("Pending")) {
                    list.add(tmp);
                    count++;
                }

            }

        } catch (SQLException e) {
        }

        return list;

    }

    protected ArrayList<Campaign> getAllOwnPartnerCampaigns(int pno) {
        ArrayList<Campaign> list = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlString = "select kno,beskrivelse,status,oprettelse_dato,start_dato,slut_dato,pris,kampagne.pno,navn,cvr from kampagne join partner on kampagne.PNO = PARTNER.PNO where kampagne.PNO = ?";
            PreparedStatement statement = null;

            statement = con.prepareStatement(sqlString);
            statement.setInt(1, pno);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Campaign tmp = new Campaign(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getFloat(7), rs.getInt(8), rs.getString(9), rs.getString(10));
                tmp.setKno(rs.getInt(1));
                list.add(tmp);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    protected boolean deleteCampaign(int kno) {
        int i = 0;
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sql = "delete from kampagne where kno = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, kno);
            i = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i == 1;
    }
    
    protected void newQuarterCampaign(){
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)){
            String sql1 = "drop table kampagne cascade constraints";
            String sql2 = "Create table kampagne(kno integer primary key, beskrivelse varchar2(750), status varchar2(30), oprettelse_dato Varchar2(20), start_dato Varchar2(20), slut_dato Varchar2(20), pris float, pno integer, constraints kampagne_fk foreign key(pno) references partner(pno))";
            PreparedStatement statement = con.prepareStatement(sql1);
            statement.executeUpdate();
            statement = con.prepareStatement(sql2);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}