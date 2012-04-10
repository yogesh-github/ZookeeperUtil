import element.generator.ZKNodeIterator;
import element.generator.interfaces.TreeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.TreeBuilder;
import visitor.XMLBuilder;

public class PrintTree {
    private static final Logger logger = LoggerFactory.getLogger(DebugInfo.ZOOKEEPER_TOOLS);

    public static void printTree(String path, String connectionString, String treeType) {
        try {
            TreeIterator treeIterator = new ZKNodeIterator(path, connectionString);
            if (treeType != null) {
                if (treeType.equals("xml")) {
                    XMLBuilder fileXMLBuilder = new XMLBuilder();
                    treeIterator.accept(fileXMLBuilder);
                    System.out.println(fileXMLBuilder.getXMLTree());
                } else {
                    System.out.println("Bad argument : argument \"" + treeType + "\" is invalid");
                }
            } else {
                TreeBuilder filePrinter = new TreeBuilder();
                treeIterator.accept(filePrinter);
                System.out.println(filePrinter.getTree());
            }
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("PrintTree.printTree() : error in printing tree; path = {}, connectionString = {},", path, connectionString);
            }
        }
    }

    public static void main(String[] args) {
        String path = args[0];
        String connectionString = args[1];
        String treeType;
        if (args.length > 2) {
            treeType = args[2];
        } else {
            treeType = null;
        }
        printTree(path, connectionString, treeType);
    }
}
