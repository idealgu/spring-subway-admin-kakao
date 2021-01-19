package subway.station;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import subway.exceptions.DuplicateStationNameException;
import subway.exceptions.InvalidStationArgumentException;

import java.util.List;

@Repository
public class StationDao {

    public static final int NO_DELETE_ROW = 0;
    public static final String INSERT_STATION = "insert into STATION(name) VALUES (?)";
    public static final String SELECT_STATION_BY_NAME = "select id, name from STATION where name = ?";
    public static final String SELECT_ALL_ID_NAME_FROM_STATION = "select id, name from station";
    public static final String DELETE_STATION_BY_ID = "delete from station where id = ?";
    public static final String SELECT_STATION_BY_ID = "select id, name from station where id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Station> stationRowMapper = (resultSet, rowNum) -> {
        Station station = new Station(
                resultSet.getLong("id"),
                resultSet.getString("name")
        );
        return station;
    };

    public Station save(Station station) {
        try {
            jdbcTemplate.update(INSERT_STATION, station.getName());
        } catch (Exception e) {
            throw new DuplicateStationNameException("중복된 역 이름입니다.");
        }
        return jdbcTemplate.queryForObject(SELECT_STATION_BY_NAME, stationRowMapper, station.getName());
    }

    public List<Station> findAll() {
        return jdbcTemplate.query(SELECT_ALL_ID_NAME_FROM_STATION, stationRowMapper);
    }

    public void deleteById(Long id) {
        int deletedRow = jdbcTemplate.update(DELETE_STATION_BY_ID, Long.valueOf(id));
        if(deletedRow == NO_DELETE_ROW) {
            throw new InvalidStationArgumentException("해당되는 역이 존재하지 않습니다.");
        }
    }

    public Station findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_STATION_BY_ID, stationRowMapper, Long.valueOf(id));
    }
}
