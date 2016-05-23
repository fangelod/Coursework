package lec17.ex2.v7;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


class SongListingWidget extends JPanel implements ActionListener {
	private Playlist plist;
	private int index;
	
	public SongListingWidget(Playlist plist, int index) {
		this.plist = plist;
		this.index = index;

		this.setBorder(BorderFactory.createEtchedBorder());

		setLayout(new BorderLayout());
		
		JLabel slabel = new JLabel(plist.getSong(index).toString()); 
		add(slabel, BorderLayout.CENTER);
		
		JPanel button_panel = new JPanel();
		
		JButton remove_button = new JButton("X");
		remove_button.setActionCommand("remove");
		remove_button.addActionListener(this);
		button_panel.add(remove_button);
		
		JButton up_button = new JButton("\u25b2");
		up_button.setActionCommand("up");
		up_button.addActionListener(this);
		button_panel.add(up_button);
		
		JButton down_button = new JButton("\u25bc");
		down_button.setActionCommand("down");
		down_button.addActionListener(this);
		button_panel.add(down_button);

		add(button_panel, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("remove")) {
			plist.removeSong(plist.getSong(index));
		} else if (cmd.equals("up")) {
			plist.moveSong(index, index-1);
		} else if (cmd.equals("down")) {
			plist.moveSong(index, index+1);
		}
	}
}
