package repository;

import domain.Vanzare;
import jdbcUtils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MasterRepo {
    private final JdbcUtils dbUtils;

    public MasterRepo(Properties properties) {
        this.dbUtils = new JdbcUtils(properties);
    }

    public void addVanzare(Vanzare v){
        Connection con=dbUtils.getConnection();
        try (PreparedStatement preparedStatement=con.prepareStatement("insert into Vanzari (id_spectacol, data_vanzare) VALUES (?,?);")){
            preparedStatement.setString(2,v.getDataVanzare());
            preparedStatement.setInt(1,v.getID_Spectacol());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    v.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Integer loc: v.getLista_locuri_vandute()) {
            try (PreparedStatement preparedStatement=con.prepareStatement("insert into VanzariLocuri (id_vanzare, nr_loc) values (?,?);")){
                preparedStatement.setInt(1,v.getId());
                preparedStatement.setInt(2,loc);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Integer> getLocuriVandute(Integer spectacolID){
        //SELECT nr_loc from Vanzari inner join VanzariLocuri VL on Vanzari.id_vanzare = VL.id_vanzare where id_spectacol=2;
        Connection con=dbUtils.getConnection();
        List<Integer> rez=new ArrayList<>();
        try (PreparedStatement statement=con.prepareStatement("SELECT nr_loc from Vanzari inner join VanzariLocuri VL on Vanzari.id_vanzare = VL.id_vanzare where id_spectacol=?;")){
            statement.setInt(1,spectacolID);
            try (ResultSet set=statement.executeQuery()){
                while (set.next()){
                    rez.add(set.getInt("nr_loc"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rez;
    }

    public int getPretBilet(int spectacolID) {
        Connection con=dbUtils.getConnection();
        try (PreparedStatement statement=con.prepareStatement("select pret_bilet from Spectacole where id_spectacol = ?;")){
            statement.setInt(1, spectacolID);
            try (ResultSet set=statement.executeQuery()){
                while (set.next()){
                    return set.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getNrSpectacole() {
        Connection con=dbUtils.getConnection();
        try (PreparedStatement statement=con.prepareStatement("select count(*) from Spectacole;")){
            try (ResultSet set=statement.executeQuery()){
                while (set.next()){
                    return set.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
