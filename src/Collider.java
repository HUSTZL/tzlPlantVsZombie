import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/*
MouseListener用于接收组件上“感兴趣”的鼠标事件（按下、释放、单击、进入或离开）的侦听器接口。（要跟踪鼠标移动和鼠标拖动，请使用 MouseMotionListener。）
旨在处理鼠标事件的类要么实现此接口（及其包含的所有方法），要么扩展为新类。
然后使用组件的 addMouseListener 方法将从该类所创建的侦听器对象向该组件注册。当按下、释放或单击（按下并释放）鼠标时会生成鼠标事件。鼠标光标进入或离开组件时也会生成鼠标事件。发生鼠标事件时，将调用该侦听器对象中的相应方法，并将MouseEvent 传递给该方法。
其包含五个方法：
1.public void mouseClicked(MouseEvent e)            鼠标按键在组件上单击（按下并释放）时调用。
2.public void mouseEntered(MouseEvent e)            鼠标进入到组件上时调用。
3.public void mouseExited(MouseEvent e)               鼠标离开组件时调用。
4.public void mousePressed(MouseEvent e)           鼠标按键在组件上按下时调用。
5.public void mouseReleased(MouseEvent e)         鼠标按钮在组件上释放时调用。
 */
// 每个方格的接受事件的监听器
public class Collider extends JPanel implements MouseListener {

    private ActionListener al;
    public Plant assignedPlant;

    public Collider() {
        setOpaque(false);
        addMouseListener(this);
        setSize(100, 120);
    }

    public void setPlant(Plant p) {
        assignedPlant = p;
    }

    public void removePlant() {
        assignedPlant.stop();
        assignedPlant = null;
    }

    public boolean isInsideCollider(int tx) {
        return (tx > getLocation().x) && (tx < getLocation().x + 100);
    }

    public void setAction(ActionListener al) {
        this.al = al;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (al != null) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, ""));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
