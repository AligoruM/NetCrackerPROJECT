package catalogApp.server.dao.song;

import catalogApp.server.dao.BasicDAO;
import catalogApp.shared.model.Song;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("songDAO")
@Transactional
public class SongDAOImpl extends BasicDAO implements ISongDAO {
    public List<Song> findAllSongs() {
        Criteria criteria = getSession().createCriteria((Song.class));
        List result = criteria.list();
        result.forEach(System.out::println);
        return criteria.list();
    }
}
