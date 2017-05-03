import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Phi {
	static ZooKeeper zk;
	static State state=State.thinking;
	public enum State {
		thinking, hungry, eating, thristy, drinking, sleeping, playing, waitingPlaying
	}

	public static void main(String args[]) throws IOException, KeeperException, InterruptedException {
		int id = Integer.parseInt(args[0]);
		int left= id-1;
		if(left==0){
			left=5;
		}
		int right=id+1;
		if(right==6){
			right=1;
		}
		zk = new ZooKeeper("137.112.89.139:2181", 9999, null);
		if (!ifExist("/12")) {
			createNode("/12", "true");
		}
		if (!ifExist("/23")) {
			createNode("/23", "true");
		}
		if (!ifExist("/34")) {
			createNode("/34", "true");
		}
		if (!ifExist("/45")) {
			createNode("/45", "true");
		}
		if (!ifExist("/51")) {
			createNode("/51", "true");
		}
		if (!ifExist("/c")) {
			createNode("/c", "true");
		}

		setData("/12", "true");
		setData("/23", "true");
		setData("/34", "true");
		setData("/45", "true");
		setData("/51", "true");
		setData("/c", "true");
		

		Thread.sleep(10000L);
		Random r = new Random();
		int rand;
		while (true) {
			Thread.sleep(1000L);
			rand = r.nextInt(10);
			if(rand==1 && state==State.thinking){
				state=State.hungry;
			}
			else if(rand==2 && state==State.hungry){
				if(getData("/"+left+id).equals("true")&&getData("/"+id+right).equals("true")){
					state=State.eating;
					setData("/"+left+id,"false");
					setData("/"+id+right,"false");
					
				}
			}else if(rand==3 && state==State.eating){
				state=State.thinking;
				setData("/"+left+id,"true");
				setData("/"+id+right,"true");
			}else if(rand==4 && state==State.thinking){
				state=State.thristy;
			}else if (rand==5 && state==State.thristy){
				if(getData("/c").equals("true")){
					state=State.drinking;
					setData("/c","false");
				}
			}
			//TODO: other cases
		}

	}

	public static boolean ifExist(String s) throws KeeperException, InterruptedException {
		return zk.exists(s, false) != null;
	}
	public static void setData(String path,String s) throws KeeperException, InterruptedException{
		zk.setData(path, s.getBytes(), zk.exists(s, true).getVersion());
	}
	public static void createNode(String path,String s) throws KeeperException, InterruptedException{
		zk.create(path, s.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	public static String getData(String s) throws KeeperException, InterruptedException{
		byte[] data = zk.getData(s, false, null);
		return new String(data);
	}

}
