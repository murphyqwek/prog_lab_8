package org.example.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.exception.InvalidArgumentsException;
import org.example.base.model.Coordinates;
import org.example.base.model.Label;
import org.example.base.model.MusicBand;
import org.example.base.model.MusicGenre;
import org.example.exception.*;
import org.example.manager.MusicBandWithOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CollectionDataBaseService - класс для работы с датой базой коллекции.
 *
 * @version 1.0
 */

public class CollectionDataBaseService {
    private final Connection connection;
    private final Logger logger = LogManager.getLogger();

    public CollectionDataBaseService(Connection connection) {
        this.connection = connection;
    }

    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS Collection(id SERIAL PRIMARY KEY, name TEXT NOT NULL, x INTEGER NOT NULL, y BIGINT NOT NULL, creationDate TIMESTAMP NOT NULL, numberOfParticipants BIGINT NOT NULL, albumsCount BIGINT NOT NULL, genre TEXT NOT NULL, sales DOUBLE PRECISION NOT NULL, owner TEXT NOT NULL);";
        try {
            var statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            throw new CannotConnectToDataBaseException();
        }
    }

    public void addNewMusicBand(MusicBand newMusicBand, String ownerLogin) throws CouldnotAddMusicBandToDataBaseExcpetion, CannotConnectToDataBaseException {
        String sqlInsert = "INSERT INTO Collection (name, x, y, creationDate, numberOfParticipants, albumsCount, " +
                            "genre, sales, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try(PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            try {
                ps.setString(1, newMusicBand.getName());
                ps.setInt(2, newMusicBand.getCoordinates().getX());
                ps.setLong(3, newMusicBand.getCoordinates().getY());
                ps.setTimestamp(4, new Timestamp(newMusicBand.getCreationDate().getTime()));
                ps.setLong(5, newMusicBand.getNumberOfParticipants());
                ps.setLong(6, newMusicBand.getAlbumsCount());
                ps.setString(7, newMusicBand.getGenre().toString());
                ps.setDouble(8, newMusicBand.getLabel().getSales());
                ps.setString(9, ownerLogin);

                try (ResultSet generatedKeys = ps.executeQuery()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);
                        newMusicBand.setId(newId);
                    }
                    else {
                        logger.error("Не удалось отправить MusicBand в дата базу:\n" + newMusicBand.toString());
                        throw new CouldnotAddMusicBandToDataBaseExcpetion();
                    }
                }
            } catch (SQLException ex) {
                logger.error("Не удалось отправить MusicBand в дата базу:\n" + newMusicBand.toString());
                throw new CouldnotAddMusicBandToDataBaseExcpetion();
            }
        }
        catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            logger.error(ex.getMessage());
            throw new CannotConnectToDataBaseException();
        }
    }

    public synchronized void updateMusicBand(MusicBand newMusicBand) throws CannotConnectToDataBaseException, CannotUpdateMusicBandException  {
        String sqlInsert = "UPDATE Collection SET(name, x, y, creationDate, numberOfParticipants, albumsCount, " +
                "genre, sales, owner) = (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "WHERE id = ?";
        try(PreparedStatement ps = connection.prepareStatement(sqlInsert)) {
            try {
                ps.setString(1, newMusicBand.getName());
                ps.setInt(2, newMusicBand.getCoordinates().getX());
                ps.setLong(3, newMusicBand.getCoordinates().getY());
                ps.setTimestamp(4, new Timestamp(newMusicBand.getCreationDate().getTime()));
                ps.setLong(5, newMusicBand.getNumberOfParticipants());
                ps.setLong(6, newMusicBand.getAlbumsCount());
                ps.setString(7, newMusicBand.getGenre().toString());
                ps.setDouble(8, newMusicBand.getLabel().getSales());
                ps.setInt(9, newMusicBand.getId());
                int musicBandUpdated = ps.executeUpdate();

                if(musicBandUpdated == 0) {
                    logger.error("Не удалось обновить MusicBand в базе данных:\n" + newMusicBand.toString());
                    throw new CannotUpdateMusicBandException();
                }

            } catch (SQLException ex) {
                logger.error("Не удалось обновить MusicBand в базе данных:\n" + newMusicBand.toString());
                logger.error(ex);
                throw new CannotUpdateMusicBandException();
            }
        }
        catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            logger.error(ex.getMessage());
            throw new CannotConnectToDataBaseException();
        }
    }

     public synchronized void deleteMusicBandById(int id) throws CannotConnectToDataBaseException, CannotDeleteFromDataBaseException {
        String sqlDelete = "DELETE FROM Collection WHERE id = ?";

        try(PreparedStatement ps = connection.prepareStatement(sqlDelete)) {
            try {
                ps.setInt(1, id);
                int rowDeleted = ps.executeUpdate();

                if(rowDeleted != 1) {
                    logger.error("Не удалось удалить элемент из базы данных");
                    throw new CannotConnectToDataBaseException();
                }
            } catch (SQLException ex) {
                logger.error("Не удалось удалить элемент из базы данных");
                logger.error(ex.getMessage());
                throw new CannotDeleteFromDataBaseException();
            }
        } catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            logger.error(ex.getMessage());
            throw new CannotConnectToDataBaseException();
        }
    }

    public synchronized void clearCollection() {
        String sqlTruncate = "TRUNCATE TABLE Collection";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sqlTruncate);
        } catch (SQLException ex) {
            logger.error("Не удалось выполнить запрос к базе данных к таблице Users");
            logger.error(ex.getMessage());
            throw new CannotConnectToDataBaseException();
        }
    }

    public List<MusicBandWithOwner> loadInMemory() throws SQLException, CannotUploadCollectionException {
        List<MusicBandWithOwner> uploaded = new ArrayList<MusicBandWithOwner>();
        String sql = "Select * from Collection;";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    MusicBandWithOwner mb = this.createMusicBandFromCurrentRow(rs);
                    uploaded.add(mb);
                }
            }
        } catch(InvalidArgumentsException ex) {
            logger.error("Не удалось загрузить коллекцию:\n" + ex);
        }

        return uploaded;
    }

    private MusicBandWithOwner createMusicBandFromCurrentRow(ResultSet rs) throws SQLException, InvalidArgumentsException {
        int id = rs.getInt(1);
        String name = rs.getString(2);
        Integer x = rs.getInt(3);
        long y = rs.getLong(4);
        Date creationDate = new Date(rs.getTimestamp(5).getTime());
        long numberofParticipants = rs.getLong(6);
        long albumsCount = rs.getLong(7);
        MusicGenre genre = MusicGenre.valueOf(rs.getString(8));
        double sales = rs.getDouble(9);
        String owner = rs.getString(10);

        MusicBand mb = new MusicBand(id, name, new Coordinates(x, y), creationDate, numberofParticipants, albumsCount, genre, new Label(sales));

        MusicBandWithOwner musicBandWithOwner = new MusicBandWithOwner(mb, owner);

        return musicBandWithOwner;
    }
}
