import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;

/**
 * Created by dheeraj.khatri on 03/11/17.
 */
public class JoinGroup extends ConnectionWatcher {

    public void join(String groupName, String memberName, CreateMode createMode) throws InterruptedException {
        String path = "/" + groupName + "/" + memberName;
        CreateMode finalCreateMode = createMode == null ? CreateMode.EPHEMERAL : createMode;
        try {
            String createdPath = zk.create(path, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, finalCreateMode);
            System.out.printf("Member %s joining group %s\n", createdPath, "/" + groupName);
        } catch (KeeperException ex) {
            System.out.println(ex.getMessage());
            System.out.printf("Path %s already exists", path);
        }
    }
}
