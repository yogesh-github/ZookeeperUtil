package visitor;

import visitor.interfaces.DataVisitor;

import java.util.Properties;

public class XMLBuilder implements DataVisitor {
    private StringBuilder xmlTree = new StringBuilder();
    private static final String ACL = "acl";
    private static final String STAT = "stat";
    private int tabSpace = 0;

    public String getXMLTree() {
        return xmlTree.toString();
    }

    public void startElement(String node, Properties properties, boolean isLastDirFile) {
        indentation(tabSpace);
        xmlTree.append("<").append(node).append(">");
        indentation(tabSpace + 1);
        xmlTree.append("<properties>");
        indentation(tabSpace + 2);
        xmlTree.append("<acl>");
        String acl = properties.getProperty(ACL);
        acl = acl.substring(1, acl.length() - 1).trim();
        xmlTree.append(acl);
        xmlTree.append("</acl>");
        indentation(tabSpace + 2);
        xmlTree.append("<stat>");
        xmlTree.append(properties.getProperty(STAT).trim());
        xmlTree.append("</stat>");
        indentation(tabSpace + 1);
        xmlTree.append("</properties>");
        tabSpace++;
    }

    private void indentation(int tabSpace) {
        xmlTree.append("\n");
        for (int i = 0; i < tabSpace; i++)
            xmlTree.append("\t");
    }

    public void endElement(String node) {
        tabSpace--;
        indentation(tabSpace);
        xmlTree.append("</").append(node).append(">");
    }

    public void nodeData(String data) {
        indentation(tabSpace);
        xmlTree.append("<data>");
        xmlTree.append(data);
        xmlTree.append("</data>");
    }
}
