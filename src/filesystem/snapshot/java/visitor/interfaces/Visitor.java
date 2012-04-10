package visitor.interfaces;

import java.util.Properties;

public interface Visitor {
    void startElement(String node, Properties properties, boolean isLastDirFile);

    void endElement(String node);
}
