package element.generator.interfaces;

import org.apache.zookeeper.KeeperException;
import visitor.interfaces.Visitor;

public interface TreeIterator {
    void accept(Visitor visitor) throws InterruptedException, KeeperException;
}
