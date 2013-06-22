package com.krislq.register;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.krislq.register.bean.Member;
import com.krislq.register.dao.MemberDao;
import com.krislq.register.util.Resourse;
import com.krislq.register.util.Utils;

public class Register extends JFrame implements ActionListener,MouseListener{
    /**
     * 
     */
    private static final long serialVersionUID = -990464036839342319L;
    public static final String APP_NAME = "eoe同城会自我介绍系统-Kris";
    private static final String BUTTON_CMD_REGISTER = "REGISTER";
    private static final String BUTTON_CMD_SKIP = "SKIP";

    private int mAppWidth = 700;
    private int mAppHeight = 500;
    private String[] mColumnNames = new String[] { "eoe 马甲", "真实姓名","是否介绍" };
    private JSplitPane             mMainPane          = null;
    private JTable            mJTable      = null;
    private JLabel  mMessageLabel = null;
    private JScrollPane  mScrollPane = null;
    private JButton mOkButton = null;
    private JButton mSkipButton = null;
    private     DefaultTableModel           mDdtm               = null;
    private List<Member>    mMembers = null;
    private Member      mDisplayMember = null;
    private int     mLastIndex = 0;
    
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Register frame = new Register();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Register() {
        //the the default ui
        Utils.setLookAndFeel();

        getContentPane().setLayout(new BorderLayout());
        //set the app frame is in the center of the screen
        int screenWith = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenheight = Toolkit.getDefaultToolkit().getScreenSize().height;
        setBounds((screenWith-mAppWidth)/2, (screenheight - mAppHeight)/2, mAppWidth, mAppHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        setTitle(APP_NAME);
        setIconImage(this.getToolkit().createImage(Resourse.url_icon));


        initMainFrame();
        mMembers = getMembers();
        refreshTable();
        if(mMembers !=null && mMembers.size() >0 ) {
            refreshDisplay(mMembers.get(0));
        }
    }
    private void initMainFrame() {

        mMainPane = new JSplitPane();
        mMainPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        mMainPane.setResizeWeight(0.4);
        mMainPane.setOneTouchExpandable(true);
        
        mDdtm   =   new DefaultTableModel(mColumnNames,0)
        {
           /**
            * 
            */
           private static final long serialVersionUID = 1L;
           @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }            
        };
        mJTable = new JTable();
        mJTable.setModel(mDdtm);
        mJTable.setEnabled(true);
        mJTable.setRowHeight(30);
        mJTable.setBorder(BorderFactory.createEtchedBorder());
        mJTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        mJTable.getTableHeader().setReorderingAllowed(false);
        mJTable.setShowGrid(false);
        mJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mJTable.setDefaultRenderer(Object.class, new TableRenderer());
        mJTable.setVisible(true);
        mJTable.addMouseListener(this);
        
        mScrollPane = new JScrollPane(mJTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mScrollPane.setMinimumSize(new Dimension(20, mAppHeight));
        
        JPanel rightPane = new JPanel();
        rightPane.setLayout(new BorderLayout(10,10)); 
        
        mMessageLabel = new JLabel();
        mMessageLabel.setHorizontalAlignment(JLabel.CENTER);
        mMessageLabel.setFont(new Font("宋体", Font.BOLD, 45));
        mMessageLabel.setForeground(Color.BLACK);
        mMessageLabel.setVisible(true);
        mMessageLabel.setBounds(20, 90, 100, 100);
        mMessageLabel.setText("eoe(eoe id)");
        rightPane.add(mMessageLabel,BorderLayout.CENTER);

        mOkButton = new JButton("介绍完了,换一个");
        mOkButton.setForeground(Color.BLACK);
        mOkButton.setFont(new Font("宋体", Font.BOLD, 15));
        mOkButton.setBounds(20, 10, 150, 30);
        mOkButton.setActionCommand(BUTTON_CMD_REGISTER);
        mOkButton.setToolTipText("upload logging to pc");
        mOkButton.setPreferredSize(new Dimension(150, 30));
        mOkButton.addActionListener(this);

        
        mSkipButton= new JButton("还没来,TT");
        mSkipButton.setForeground(Color.BLACK);
        mSkipButton.setFont(new Font("宋体", Font.BOLD, 15));
        mSkipButton.setBounds(10, 10, 150, 30);
        mSkipButton.setPreferredSize(new Dimension(150, 30));
        mSkipButton.setActionCommand(BUTTON_CMD_SKIP);
        mSkipButton.setToolTipText("upload logging to pc");
        mSkipButton.addActionListener(this);

        JPanel southPane = new JPanel();
        southPane.setLayout(new BorderLayout(10,10));
        southPane.add(mOkButton,BorderLayout.WEST);
        southPane.add(mSkipButton,BorderLayout.EAST);
        
        rightPane.add(southPane,BorderLayout.SOUTH);
        
        mMainPane.setLeftComponent(mScrollPane);
        mMainPane.setRightComponent(rightPane);
        mMainPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
                    resizeComponent(Integer.parseInt(evt.getNewValue().toString()));
                }
            }
        });
        add(mMainPane,BorderLayout.CENTER);
    }
    public void resizeComponent(int newValue){
        System.out.println("LeftWidth:"+newValue);
        TableColumn column = mJTable.getColumn(mColumnNames[2]);
        column.setPreferredWidth(60);
        int newWidth = newValue-60-2;
        JScrollBar scrollBar = mScrollPane.getVerticalScrollBar();
        if(scrollBar.isShowing()) {
            newWidth = newWidth - scrollBar.getWidth();
        }
        column = mJTable.getColumn(mColumnNames[1]);
        column.setPreferredWidth(newWidth/2);
        column = mJTable.getColumn(mColumnNames[0]);
        column.setPreferredWidth(newWidth/2);
    }
    private void showMessage(String message) {
        (new MessageDialog(this,message)).setVisible(true);
    }
    class TableRenderer implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            System.out.println("row:"+row+"#cloumn:"+column);
            String valueStr = value.toString();
            JLabel label = new JLabel();
            label.setFont(new Font(table.getFont().toString(), Font.BOLD, 14));
            label.setText(valueStr);
            label.setVisible(true);
            label.setOpaque(true);
            if(isSelected) {
                label.setBackground(Color.GRAY);
            } else {
                label.setBackground(table.getParent().getBackground());
            }
            label.setForeground(Color.BLACK);
            if(column == (mColumnNames.length-1)) {
                if(valueStr.equals("false")) {
                    label.setForeground(Color.RED);
                }else {
                    label.setForeground(Color.BLUE);
                }
            }
            return label;
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(mMembers == null || mMembers.size() == 0) {
            showMessage("No member to display!");
            return;
        }
        String comm = e.getActionCommand();
        if(BUTTON_CMD_REGISTER.endsWith(comm)) {
            mDisplayMember.setRegister(true);
            refreshTable();
        }else {
            mLastIndex += 1;
        }
        refreshDisplay();
    }
    private List<Member> getMembers(){
        return new MemberDao().getMembers();
    }
    public void refreshTable() {
        try {
            removeAllData();
            for(Member m :mMembers) {
                mDdtm.insertRow(mJTable.getRowCount(), new Object[]{m.getName(),m.getEoeId(),m.isRegister()});
            }
            mJTable.updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removeAllData() {
        int row = mDdtm.getRowCount();
        for(int i=0;i<row;i++) {
            mDdtm.removeRow(0);
        }
    }
    private void refreshDisplay() {
        if(mMembers == null) {
            return;
        }
        if(isAllRegister()) {
            showMessage("No member to display!");
            return;
        }
        int size = mMembers.size();
        for(int i = mLastIndex;i<size;i++) {
            Member m = mMembers.get(i);
            if(!m.isRegister()) {
                mLastIndex = i;
                refreshDisplay(m);
                return;
            }
        }
        mLastIndex=0;
        for(int i = mLastIndex;i<size;i++) {
            Member m = mMembers.get(i);
            if(!m.isRegister()) {
                mLastIndex = i;
                refreshDisplay(m);
                return;
            }
        }
    }
    private boolean isAllRegister() {
        for(Member m:mMembers) {
            if(!m.isRegister()) {
                return false;
            }
        }
        return true;
    }
    private void refreshDisplay(Member member){
        mDisplayMember = member;
        mMessageLabel.setText(member.getName()+"("+member.getEoeId()+")");
        mJTable.setRowSelectionInterval(mLastIndex, mLastIndex);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) {
            return;
        }
        mLastIndex = mJTable.rowAtPoint(e.getPoint());
        refreshDisplay(mMembers.get(mLastIndex));
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
