import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by dheeraj.khatri on 03/11/17.
 */
public class CreateGroup extends ConnectionWatcher {

    public void create(String groupName) throws InterruptedException {
        String path = "/" + groupName;
        try {
            String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("created: " + createdPath);
        } catch (KeeperException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
