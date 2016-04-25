package scotip.app.tools;

import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.extension.NodeVisitorFactory;
import com.mitchellbosecke.pebble.extension.Test;
import com.mitchellbosecke.pebble.operator.BinaryOperator;
import com.mitchellbosecke.pebble.operator.UnaryOperator;
import com.mitchellbosecke.pebble.tokenParser.TokenParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.extension.Extension;

/**
 * Created by Pierre on 24/04/2016.
 */
public class CustomViewFunctions implements Extension {

    @Override
    public Map<String, Filter> getFilters() {
        return null;
    }

    @Override
    public Map<String, Test> getTests() {
        return null;
    }

    @Override
    public Map<String, Function> getFunctions() {
        HashMap<String, Function> m = new HashMap<>();

        m.put("typeob", new Function() {
            @Override
            public Object execute(Map<String, Object> args) {
                String s = "";

                Object o = args.get("object");

                if (o != null) {
                    s = "Type: " + o.getClass();
                } else {
                    s = "Objet nul";
                }
                return s;
            }

            @Override
            public List<String> getArgumentNames() {
                ArrayList<String> ar = new ArrayList<>();
                ar.add("object");
                return ar;
            }
        });

        return m;
    }

    @Override
    public List<TokenParser> getTokenParsers() {
        return null;
    }

    @Override
    public List<BinaryOperator> getBinaryOperators() {
        return null;
    }

    @Override
    public List<UnaryOperator> getUnaryOperators() {
        return null;
    }

    @Override
    public Map<String, Object> getGlobalVariables() {
        return null;
    }

    @Override
    public List<NodeVisitorFactory> getNodeVisitors() {
        return null;
    }

}
