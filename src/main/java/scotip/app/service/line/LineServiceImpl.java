package scotip.app.service.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scotip.app.dao.line.LineDao;
import scotip.app.model.Line;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
@Service("lineService")
@Transactional
public class LineServiceImpl implements LineService {

    @Autowired
    private LineDao lineDao;

    @Override
    public List<Line> getSharedLines() {
        return lineDao.getSharedLines();
    }

    @Override
    public Line findSharedById(int id) {
        return lineDao.findSharedById(id);
    }

    @Override
    public boolean isAccessCodeAvailable(int code, Line line) {
        return lineDao.isAccessCodeAvailable(code, line);
    }

}
