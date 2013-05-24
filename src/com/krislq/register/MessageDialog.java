package com.krislq.register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import com.krislq.register.util.Resourse;

/**
 *
 * @author kris
 * @since May 6, 2013
 * @version 1.0.0
 */
public class MessageDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static final String BUTTON_CMD_BROWSE = "bro";
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 130;

    private Register mRegister = null;
    private File mSelectDir = null;

    private JPanel mAboutPanel = null;

    private JLabel mDisLabel = null;
    private JButton mOKBtn = null;

    public MessageDialog(Register instance,String message) {

        super(instance, true);

        mRegister = instance;

        this.setTitle("Init your report workspace");
        this.setBounds(mRegister.getX() + mRegister.getWidth() / 2 - DIALOG_WIDTH / 2, mRegister.getY() + mRegister.getHeight() / 2 - DIALOG_HEIGHT / 2, DIALOG_WIDTH, DIALOG_HEIGHT);
        this.setIconImage(Resourse.app_icon.getImage());

        mAboutPanel = new JPanel(null);
        mAboutPanel.setBounds(0, 0, DIALOG_WIDTH, DIALOG_HEIGHT);
        mAboutPanel.setOpaque(false);
        
        mDisLabel = new JLabel( message);
        mDisLabel.setForeground(Color.BLACK);
        mDisLabel.setForeground(Color.RED);
        mDisLabel.setBounds(100, 20, 200, 30);
        mDisLabel.setFont(new Font("宋体", Font.BOLD, 15));

        mOKBtn = new JButton("OK");
        mOKBtn.addActionListener(this);
        mOKBtn.setBounds((DIALOG_WIDTH-80)/2, 70, 80, 25);

        mAboutPanel.add(mDisLabel);
        mAboutPanel.add(mOKBtn);

        this.add(mAboutPanel);
        setResizable(false);
    }
    public void showMessage(String msg) {
        mDisLabel.setText(msg);
        mDisLabel.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();
        if (event == mOKBtn) {
            this.setVisible(false);
            this.dispose();
        }
    }
}
