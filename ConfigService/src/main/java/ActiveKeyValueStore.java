import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

/**
 * Created by dheeraj.khatri on 05/11/17.
 */
public class ActiveKeyValueStore extends ConnectionWatcher {

    private static final Charset CHARSET = Charset.forName("UTF-8");
    private static final int MAX_RETRIES = 5;
    private static final int RETRY_PERIOD_SECOND = 1;

    public void write(String path, String value) throws InterruptedException, KeeperException {
        int retries = 0;
        while(true) {
            try {
                Stat stat = zk.exists(path, false);
                if (null == stat) {
                    zk.create(path, value.getBytes(CHARSET), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                } else {
                    zk.setData(path, value.getBytes(CHARSET), -1);
                }
            } catch (KeeperException.SessionExpiredException ex) {
                throw ex;
            } catch (KeeperException ex /*recoverable exception like connection loss etc*/) {
                if(retries++ == MAX_RETRIES) {
                    throw ex;
                }
                TimeUnit.SECONDS.sleep(RETRY_PERIOD_SECOND);
            }

        }
    }

    public String read(String path, Watcher watcher) throws KeeperException, InterruptedException {
        byte[] data = zk.getData(path, watcher, null /*metadata*/);
        return new String(data, CHARSET);
    }
}
