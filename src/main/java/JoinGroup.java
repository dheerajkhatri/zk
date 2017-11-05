import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by dheeraj.khatri on 03/11/17.
 */
public class JoinGroup extends ConnectionWatcher {

    public void join(String groupName, String memberName) throws InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        try {
            String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.printf("Member %s joining group %s\n", createdPath, "/" + groupName);
        } catch (KeeperException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Path %s already exists", path);
        }
    }
}
