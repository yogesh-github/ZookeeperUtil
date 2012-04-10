package visitor;

import visitor.interfaces.DataVisitor;

import java.util.ArrayList;
import java.util.Properties;

public class TreeBuilder implements DataVisitor {
    private StringBuilder tree = new StringBuilder();
    private ArrayList<Boolean> indentation = new ArrayList<Boolean>();

    public String getTree() {
        return tree.toString();
    }

    public void startElement(String node, Properties properties, boolean isLastDirFile) {
        tree.append("\n");
        for (Boolean blank : indentation) {
            tree.append(blank ? "   " : "|  ");
        }
        tree.append(isLastDirFile ? "`--" : "|--");
        tree.append(node);
        indentation.add(isLastDirFile);
    }

    public void endElement(String node) {
        indentation.remove(indentation.size() - 1);
    }

    public void nodeData(String data) {
        tree.append(" <data=\"").append(data).append("\">");
    }
}
