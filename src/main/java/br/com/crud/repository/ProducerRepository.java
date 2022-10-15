package br.com.crud.repository;

import br.com.crud.conn.ConnectionFactory;
import br.com.crud.dominio.Producer;
import lombok.extern.log4j.Log4j2;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class ProducerRepository {

    public static List<Producer> findByName(String name) {
        log.info("Encontrar producer pelo nome '{}'", name);
        List<Producer> producers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementFindByName(conn, name); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producer producer = Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            log.error("Erro ao tentar encontrar todos os produces", e);
        }
        return producers;
    }
    private static PreparedStatement createPrepareStatementFindByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT * FROM `anime_store_code`.`producer` WHERE `name` LIKE ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, String.format("%s%%", name));
        return ps;
    }

    public static Optional<Producer> findById(Integer id) {
        log.info("Encontrar producer pelo id '{}'", id);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementFindById(conn, id); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return Optional.empty();
            return Optional.of(Producer.builder().id(rs.getInt("id")).name(rs.getString("name")).build());
        } catch (SQLException e) {
            log.error("Erro ao tentar encontrar todos os produtos", e);
        }
        return Optional.empty();
    }
    private static PreparedStatement createPrepareStatementFindById(Connection conn, Integer id) throws SQLException {
        String sql = "SELECT * FROM `anime_store_code`.`producer` WHERE id = ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void delete(int id) {
        try (Connection conn = ConnectionFactory.getConnection(); PreparedStatement ps = createPrepareStatementDelete(conn, id)) {
            ps.execute();
            log.info("Deletando producer nas linhas do banco de dados afetados {}", id);
        } catch (SQLException e) {
            log.error("Erro ao tentar deletar producer '{}'", id, e);
        }
    }
    private static PreparedStatement createPrepareStatementDelete(Connection conn, Integer id) throws SQLException {
        String sql = "DELETE FROM `anime_store_code`.`producer` WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        return ps;
    }

    public static void save(Producer producer) {
        log.info("Salvando Producer '{}'", producer);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPreparedStatementSave(conn, producer)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Erro ao tentar atualizar producer '{}'", producer.getId(), e);
        }
    }
    private static PreparedStatement createPreparedStatementSave(Connection conn, Producer producer) throws SQLException {
        String sql = "INSERT INTO `anime_store_code`.`producer` (`name`) VALUES (?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        return ps;
    }

    public static void update(Producer producer) {
        log.info("Atualizando producer '{}'", producer);
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = createPrepareStatementUpdate(conn, producer)) {
            ps.execute();
        } catch (SQLException e) {
            log.error("Erro ao tentar atualizar producer '{}'", producer.getId(), e);
        }
    }
    private static PreparedStatement createPrepareStatementUpdate(Connection conn, Producer producer) throws SQLException {
        String sql = "UPDATE `anime_store_code`.`producer` SET `name` = ? WHERE (`id` = ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, producer.getName());
        ps.setInt(2, producer.getId());
        return ps;
    }
}
