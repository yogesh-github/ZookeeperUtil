package element.generator;

import element.generator.interfaces.TreeIterator;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import visitor.interfaces.DataVisitor;
import visitor.interfaces.Visitor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ZKNodeIterator implements TreeIterator {
    private String path;
    private ZooKeeper zk;

    public ZKNodeIterator(String path, String connectionString) throws IOException {
        this.path = path;
        zk = new ZooKeeper(connectionString, 3000, null);
    }

    public void accept(Visitor visitor) throws InterruptedException, KeeperException {
        iterateTree(path, visitor);
    }

    private void iterateTree(String path, Visitor visitor) throws InterruptedException, KeeperException {
        List<String> nodeList = zk.getChildren(path, false);
        Stat stat = new Stat();
        if (nodeList != null && !nodeList.isEmpty()) {
            int j = 1;
            for (String node : nodeList) {
                Properties properties = new Properties();
                properties.setProperty("acl", zk.getACL(path + "/" + node, stat).toString());
                properties.setProperty("stat", stat.toString());
                if (j != nodeList.size())
                    visitor.startElement(node, properties, false);
                else
                    visitor.startElement(node, properties, true);
                if (visitor instanceof DataVisitor) {
                    byte[] data = zk.getData(path + "/" + node, false, null);
                    DataVisitor dataVisitor = (DataVisitor) visitor;
                    dataVisitor.nodeData(data == null ? "" : new String(data));
                }
                if (!path.equals("/")) {
                    iterateTree(path + "/" + node, visitor);
                } else {
                    iterateTree(path + node, visitor);
                }
                visitor.endElement(node);
                j++;
            }
        }
    }
}
