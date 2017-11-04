import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by dheeraj.khatri on 03/11/17.
 */
public class ListGroup extends ConnectionWatcher {

    public void list(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            if(children.isEmpty()) {
                System.out.printf("No members in group %s \n", groupName);
                System.exit(1);
            }
            for(String child : children) {
                System.out.println(child);
            }
        } catch (KeeperException.NoNodeException ex) {
            System.out.printf("Group %s does not exists \n", groupName);
            System.exit(1);
        }
    }
}
