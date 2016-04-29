package scotip.app.dao.line;

import scotip.app.model.Line;

import java.util.List;

/**
 * Created by Pierre on 29/04/2016.
 */
public interface LineDao {
    List<Line> getSharedLines();

    Line findSharedById(int id);

    boolean isAccessCodeAvailable(int code, Line line);
}
