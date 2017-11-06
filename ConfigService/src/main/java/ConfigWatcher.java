import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.io.IOException;

/**
 * Created by dheeraj.khatri on 05/11/17.
 */
public class ConfigWatcher implements Watcher {
    private ActiveKeyValueStore store;

    public ConfigWatcher(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void displayConfig() throws KeeperException, InterruptedException {
        String value = store.read(ConfigUpdater.PATH, this);
        System.out.printf("Read %s as %s\n",ConfigUpdater.PATH, value);
    }

    @Override
    public void process(WatchedEvent event) {
        if(event.getType() == Event.EventType.NodeDataChanged) {
            try {
                displayConfig();
            } catch (InterruptedException ex) {
                System.err.println("Interrupted. Existing.");
                Thread.currentThread().interrupt();
            } catch(KeeperException ex) {
                System.err.printf("KeeperException: %s. Existing.\n", ex);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, KeeperException, IOException {
        ConfigWatcher configWatcher = new ConfigWatcher(args[0]);
        configWatcher.displayConfig();

        Thread.sleep(Long.MAX_VALUE);
    }
}
