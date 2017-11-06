import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by dheeraj.khatri on 05/11/17.
 */
public class ConfigUpdater {

    public static final String PATH = "/config";

    private ActiveKeyValueStore store;
    private Random random = new Random();

    public ConfigUpdater(String hosts) throws IOException, InterruptedException {
        store = new ActiveKeyValueStore();
        store.connect(hosts);
    }

    public void run() throws KeeperException, InterruptedException {
        while(true) {
            String value = random.nextInt(100) + "";
            store.write(PATH, value);
            System.out.printf("Set %s to %s\n", PATH, value);
            TimeUnit.SECONDS.sleep(random.nextInt(10));
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {

        while (true) {
            try {
                ConfigUpdater resilientConfigUpdater = new ConfigUpdater(args[0]);
                resilientConfigUpdater.run();
            } catch (KeeperException.SessionExpiredException ex) {
                //loop will make new connection
            } catch (KeeperException ex) {
                //already retried
                ex.printStackTrace();
                break;
            }
        }
    }
}
