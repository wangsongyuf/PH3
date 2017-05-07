import java.awt.EventQueue;

import javax.swing.JFrame;

import org.apache.zookeeper.KeeperException;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnThirsty = new JButton("Thirsty");
		btnThirsty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Phi.reset(Phi.left,Phi.right,Phi.id,Phi.State.thristy);
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnThirsty, BorderLayout.NORTH);

		JButton btnSleeping = new JButton("Sleeping");
		btnSleeping.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Phi.reset(Phi.left,Phi.right,Phi.id,Phi.State.sleeping);
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnSleeping, BorderLayout.WEST);

		JButton btnPlaying = new JButton("playing");
		btnPlaying.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Phi.reset(Phi.left,Phi.right,Phi.id,Phi.State.waitingPlaying);
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					Phi.setData("/"+Phi.id + Phi.right + "P", "1");
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnPlaying, BorderLayout.EAST);

		JButton btnThinking = new JButton("thinking");
		btnThinking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Phi.reset(Phi.left,Phi.right,Phi.id,Phi.State.thinking);
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnThinking, BorderLayout.CENTER);

		JButton btnHungry = new JButton("Hungry");
		btnHungry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Phi.reset(Phi.left,Phi.right,Phi.id,Phi.State.hungry);
				} catch (KeeperException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnHungry, BorderLayout.SOUTH);
	}

}
