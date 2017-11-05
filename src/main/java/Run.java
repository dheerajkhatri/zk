import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Op;

import java.io.IOException;

/**
 * Created by dheeraj.khatri on 02/11/17.
 */
public class Run {

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        CreateGroup createGroup = new CreateGroup();
        createGroup.connect(args[0]);
        createGroup.create(args[1]);
        createGroup.close();

        JoinGroup joinGroup = new JoinGroup();
        joinGroup.connect(args[0]);
        joinGroup.join(args[1],args[2]);
        joinGroup.join(args[1], args[3]);

        ListGroup listGroup = new ListGroup();
        listGroup.connect(args[0]);
        listGroup.list(args[1]);

        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(args[0]);
        deleteGroup.deleteGroup(args[1]);
        listGroup.list(args[1]);

        joinGroup.close();
        listGroup.close();
        deleteGroup.close();
    }
}
