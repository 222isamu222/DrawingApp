import javax.swing.*;
import java.awt.*;

public class CircularButton extends JButton {
	private int penSize;

	public CircularButton() {
		super();
		setContentAreaFilled(false); // ボタンの内部の色を透明に設定
		setFocusPainted(false); // フォーカス時の描画を無効にする
		setBorderPainted(false); // ボーダーの描画を無効にする
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(getBackground());
		}
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}

	@Override
    protected void paintBorder(Graphics g) {
        // 枠線の描画を無効にするため、空の実装を提供する
    }

	public void setPenSize(int penSize){
		this.penSize = penSize;
	}
	public int getPenSize(){
		return this.penSize;
	}
}