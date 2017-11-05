import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * Created by dheeraj.khatri on 05/11/17.
 */
public class DeleteGroup extends ConnectionWatcher {

    public void deleteGroup(String groupName) throws KeeperException, InterruptedException {
        String path = "/" + groupName;
        try {
            List<String> children = zk.getChildren(path, false);
            for(String child : children) {
                zk.delete(path + "/" + child, -1);
            }
            System.out.printf("delete group %s with sessionId %s\n", path, zk.getSessionId());
            zk.delete(path, -1);
        } catch (KeeperException.NoNodeException ex) {
            System.out.printf("Group %s doesn't exists\n", path);
            System.exit(1);
        }
    }
}
