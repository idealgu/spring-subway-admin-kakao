package subway.station;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private StationDao stationDao;

    public StationService(StationDao stationDao) {
        this.stationDao = stationDao;
    }

    public Station save(Station station) {
        return stationDao.save(station);
    }

    public List<Station> findAll() {
        return stationDao.findAll();
    }

    public void deleteById(Long id) {
        stationDao.deleteById(id);
    }

    public Station findById(Long id) { return stationDao.findById(id); }

}
