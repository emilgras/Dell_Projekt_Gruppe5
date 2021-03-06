package Model;

import Entities.CustomFile;
import Entities.Poe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * @author Emil **
 */
public class PoeMapper {

    protected PoeMapper() {
        try {
            Class.forName(DBDetail.DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoginMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected boolean uploadPoe(int kno, ArrayList<CustomFile> files) {
        boolean success = true;
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            String sqlPrimary = "select poeNo_increment.nextval from dual";
            String sqlPoe = "insert into poe values (?, ?)";
            String sqlFil = "insert into filer values(?, ?, ?)";

            /**
             * * Makes POE primary number **
             */
            PreparedStatement statement = con.prepareStatement(sqlPrimary);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int poeNo = rs.getInt(1);

            /**
             * * Creates POE tuple and inserts data **
             */
            statement = con.prepareStatement(sqlPoe);
            statement.setInt(1, poeNo);
            statement.setInt(2, kno);
            statement.executeUpdate();

            /**
             * * Creates multiple FILE tuples and inserts data **
             */
            statement = con.prepareStatement(sqlFil);
            for (CustomFile file : files) {
                statement.setString(1, file.getName());
                statement.setString(2, file.getExtension());
                statement.setInt(3, poeNo);
                statement.executeUpdate();
            }

        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(CampaignMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }

    protected Poe getPoe(int kno) {
        Poe poe = new Poe();
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {

            String sql = "select navn, extension from filer join poe on filer.poeno = poe.poeno where kno = ?";

            PreparedStatement statement = null;

            statement = con.prepareStatement(sql);
            statement.setInt(1, kno);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String ext = rs.getString(2);
                CustomFile file = new CustomFile(name, ext);

                /**
                 * * image files **
                 */
                if (ext.equals("png") || ext.equals("jpg") || ext.equals("jpeg") || ext.equals("pdf")) {
                    poe.addImageFile(file);
                }
                /**
                 * * video files **
                 */
                if (ext.equals("mp4") || ext.equals("avi")) {
                    poe.addVideoFile(file);
                }
                /**
                 * * music files **
                 */
                if (ext.equals("mp3")) {
                    poe.addMusicFile(file);
                }
                /**
                 * * text files **
                 */
                if (ext.equals("doc")) {
                    poe.addTextFile(file);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return poe;
    }

    protected boolean deleteOldPoe(int kno) {
        boolean success = true;
        int result = 0;
        int poeNo = 0;
        
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)) {
            PreparedStatement statement = null;
            ResultSet rs = null;

            String sql1 = "select POENO from POE join KAMPAGNE on kampagne.kno = poe.KNO where kampagne.kno = ?";
            String sql2 = "delete from FILER where POENO = ?";
            String sql3 = "delete from POE where KNO = ?";

            /**
             * * Get PoeNo **
             */

            statement = con.prepareStatement(sql1);
            statement.setInt(1, kno);
            rs = statement.executeQuery();

            if (rs.next()) {
                poeNo = rs.getInt(1);
            }

            /**
             * * Delete Old Poe Files **
             */
            statement = con.prepareStatement(sql2);
            statement.setInt(1, poeNo);
            result = statement.executeUpdate();

            /**
             * * Delete Old Poe Files **
             */
            statement = con.prepareStatement(sql3);
            statement.setInt(1, kno);
            result = statement.executeUpdate();

        } catch (SQLException ex) {
            success = false;
            Logger.getLogger(PoeMapper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return success;
    }
   
    protected void newQuarterPoe(){
        try (Connection con = DriverManager.getConnection(DBDetail.URL, DBDetail.ID, DBDetail.PW)){

            String sql1 = "drop table filer cascade constraints";
            String sql2 = "drop table poe cascade constraints";
            String sql3 = "Create table poe(poeno integer primary key, kno integer, constraints poe_fk foreign key(kno) references kampagne(kno))";
            String sql4 = "Create table filer(navn varchar2(100), extension varchar2(100), poeno integer, constraints filer_fk foreign key(poeno) references poe(poeno))";
            PreparedStatement statement = con.prepareStatement(sql1);
            statement.executeUpdate();
            statement = con.prepareStatement(sql2);
            statement.executeUpdate();
            statement = con.prepareStatement(sql3);
            statement.executeUpdate();
            statement = con.prepareStatement(sql4);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
