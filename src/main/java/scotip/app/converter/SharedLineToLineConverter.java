package scotip.app.converter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import scotip.app.model.Line;
import scotip.app.service.line.LineService;

/**
 * Created by Pierre on 29/04/2016.
 */
@Component
public class SharedLineToLineConverter implements Converter<Object, Line> {

    @Autowired
    LineService lineService;


    @Override
    public Line convert(Object element) {
        Integer id = Integer.parseInt((String) element);
        Line line = lineService.findSharedById(id);
        System.out.println("Line : " + line);
        return line;
    }
}
