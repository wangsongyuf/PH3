import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class Phi {
	public static void main(String args[]) throws IOException, KeeperException, InterruptedException {
        new Phi();
    }
	public Phi() throws IOException, KeeperException, InterruptedException {
	ZooKeeper zk = new ZooKeeper("137.112.89.139:2181", 3000, null);
	System.out.println(zk.exists("s", false));
}}
