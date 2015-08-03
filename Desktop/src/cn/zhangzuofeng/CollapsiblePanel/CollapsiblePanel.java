package cn.zhangzuofeng.CollapsiblePanel;

import cn.zhangzuofeng.ZAlarm.ui.IconButton;
import cn.zhangzuofeng.daily.util.ZResource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CollapsiblePanel extends JPanel {

    private JLabel iconLabel;
    private IconButton addLabel;
    private IconButton deleteLabel;
    private JPanel contentPanel;
    private JLabel titleLabel;
    /**
     * 压缩的图标
     */
    private ImageIcon expandedIcon = ZResource.getImageIcon("expanded");
    /**
     * 扩展开的图标
     */
    private ImageIcon coexpandedIcon = ZResource.getImageIcon("coexpanded");

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public CollapsiblePanel() {
        initComponents();
//        setBorder(BorderFactory.createLineBorder(Color.yellow));
        loadAction();
        recalculateMaxSize();
    }

    private void initComponents() {
        setMinimumSize(new Dimension(200, 20));

        iconLabel = new JLabel();
        titleLabel = new JLabel();
        addLabel = new IconButton();
        deleteLabel = new IconButton();
        JPanel titlePanel = new JPanel();
        contentPanel = new JPanel();

        titlePanel.setMaximumSize(new Dimension(2000, 15));
//        titlePanel.setBackground(Color.CYAN);

        addLabel.setOpaque(false);
        deleteLabel.setOpaque(false);
        
//        addLabel.setBackground(Color.red);
        addLabel.setFocusPainted(true);
        
        iconLabel.setIcon(coexpandedIcon);
        titleLabel.setText(" TITLE ");
        addLabel.setText(ZResource.getLabel("add"));
        deleteLabel.setText(ZResource.getLabel("delete"));

        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(iconLabel, BorderLayout.WEST);
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        JPanel tempPanel = new JPanel();
        tempPanel.add(addLabel);
        tempPanel.add(deleteLabel);
        titlePanel.add(tempPanel, BorderLayout.EAST);

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        this.setLayout(new BorderLayout());
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public void setTitle(String text) {
        this.titleLabel.setText(text);
    }

    public void addContentPanel(Component panel) {
        this.contentPanel.add(panel);
        this.contentPanel.validate();
    }

    public boolean removeContentPanel(JPanel panel) {
        int componentCount = contentPanel.getComponentCount();
        if (componentCount == 0) {
            System.err.println("contentPanel is empty!");
            return false;
        }
        contentPanel.remove(panel);
        return true;
    }

    public static void main(String[] args) {
        JFrame jFrame;
        jFrame = new JFrame();
        jFrame.setAlwaysOnTop(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CollapsiblePanel collapsiblePanel = new CollapsiblePanel();
        for (int i = 0; i < 10; i++) {
            JPanel jPanel = new JPanel();
            jPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
            collapsiblePanel.addContentPanel(jPanel);
        }
        jFrame.add(collapsiblePanel);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    /**
     * 展开为true 缩小为false
     */
    private boolean status = true;

    private void loadAction() {
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (status) {
                    setMaximumSize(new Dimension(2000, 20));
                    iconLabel.setText("");
                    iconLabel.setIcon(expandedIcon);
                    contentPanel.setVisible(false);
                    CollapsiblePanel.this.validate();
                    status = false;
                } else {
                    recalculateMaxSize();
                    iconLabel.setText("");
                    iconLabel.setIcon(coexpandedIcon);
                    contentPanel.setVisible(true);
                    CollapsiblePanel.this.validate();
                    status = true;
                }
            }
        });
    }

    public void addAddActionListener(ActionListener addButtActionListener) {
        addLabel.addActionListener(addButtActionListener);
    }

    public void addDeleteActionListener(ActionListener addButtActionListener) {
        deleteLabel.addActionListener(addButtActionListener);
    }

    private void recalculateMaxSize() {
        int componentCount = contentPanel.getComponentCount();
        int height = 20;
        for (int i = 0; i < componentCount; i++) {
            Component component = contentPanel.getComponent(i);
            Dimension maximumSize = component.getMaximumSize();
            height += maximumSize.height;
        }
        setMaximumSize(new Dimension(2000, height));
    }
}
