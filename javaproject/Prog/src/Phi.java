import java.io.IOException;
import java.io.IOException;
import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class Phi {
	static ZooKeeper zk;
	static State state = State.thinking;
	static int drinkingcount = 40;
	static int sleepingcount = 40;
	static int playingcount = 20;
	static int waitplayingcount = 20;

	public enum State {
		thinking, hungry, eating, thristy, drinking, sleeping, playing, waitingPlaying
	}

	public static void main(String args[]) throws IOException, KeeperException, InterruptedException {
		int id = Integer.parseInt(args[0]);
		int left = id - 1;
		if (left == 0) {
			left = 5;
		}
		int right = id + 1;
		if (right == 6) {
			right = 1;
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
		if (!ifExist("/12P")) {
			createNode("/12P", "0");
		}
		if (!ifExist("/23P")) {
			createNode("/23P", "0");
		}
		if (!ifExist("/34P")) {
			createNode("/34P", "0");
		}
		if (!ifExist("/45P")) {
			createNode("/45P", "0");
		}
		if (!ifExist("/51P")) {
			createNode("/51P", "0");
		}

		setData("/12", "true");
		setData("/23", "true");
		setData("/34", "true");
		setData("/45", "true");
		setData("/51", "true");
		setData("/12P", "0");
		setData("/23P", "0");
		setData("/34P", "0");
		setData("/45P", "0");
		setData("/51P", "0");
		setData("/c", "true");

		Thread.sleep(10000L);
		Random r = new Random();
		int rand;
		while (true) {
			Thread.sleep(1000L);
			System.out.println(state);
			rand = r.nextInt(10);
			if (rand == 1 && state == State.thinking) {
				state = State.hungry;
			} else if (rand == 2 && state == State.hungry) {
				if (getData("/" + left + id).equals("true") && getData("/" + id + right).equals("true")) {
					state = State.eating;
					setData("/" + left + id, "false");
					setData("/" + id + right, "false");

				}
			} else if (rand == 3 && state == State.eating) {
				state = State.thinking;
				setData("/" + left + id, "true");
				setData("/" + id + right, "true");
			} else if (rand == 4 && state == State.thinking) {
				state = State.thristy;
			} else if (rand == 5 && state == State.thristy) {
				if (getData("/c").equals("true")) {
					state = State.drinking;
					setData("/c", "false");
				}
			} else if (drinkingcount > 0 && state == State.drinking) {
				drinkingcount--;
			} else if (drinkingcount <= 0 && state == State.drinking) {
				state = State.sleeping;
				setData("/c", "true");
				drinkingcount = 40;
			} else if (sleepingcount > 0 && state == State.sleeping) {
				sleepingcount--;
				setData("/"+left+id+"P", "0");
			} else if (sleepingcount <= 0 && state == State.sleeping) {
				state = State.thinking;
				sleepingcount = 40;
			} else if (rand == 6 && state == State.thinking) {
				state = state.waitingPlaying;
				setData("/"+id + right + "P", "1");
			} else if (state == State.waitingPlaying) {
				waitplayingcount--;
				String s = getData("/"+id + right + "P");
				if (s.equals( "2")) {
					state = State.playing;
					setData("/"+id + right + "P", "0");
					waitplayingcount=20;
				}
				if (s.equals( "0")) {
					state = State.thinking;
					waitplayingcount=20;
				}
				if(	waitplayingcount<0){
					state = State.thinking;
					waitplayingcount=20;
					setData("/"+id + right + "P", "0");
				}
			} else if (state == State.playing && playingcount > 0) {
				playingcount--;
			} else if (state == State.playing && playingcount <= 0) {
				state = State.thinking;
				playingcount = 20;
			} else if (state == State.hungry || state == State.thinking || state == State.thristy) {
				String s = getData("/"+left + id + "P");
				if (s.equals( "1")) {
					state = State.playing;
					setData("/"+left + id + "P", "2");
				}
			}
		}

	}

	public static boolean ifExist(String s) throws KeeperException, InterruptedException {
		return zk.exists(s, false) != null;
	}

	public static void setData(String path, String s) throws KeeperException, InterruptedException {
		zk.setData(path, s.getBytes(), -1);
	}

	public static void createNode(String path, String s) throws KeeperException, InterruptedException {
		zk.create(path, s.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public static String getData(String s) throws KeeperException, InterruptedException {
		byte[] data = zk.getData(s, false, null);
		return new String(data);
	}

}
