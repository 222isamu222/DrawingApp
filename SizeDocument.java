import java.util.regex.*;
import javax.swing.text.*;

public class SizeDocument extends PlainDocument{
	private static final Pattern DIGITS = Pattern.compile("\\d+");

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		if (str == null) {
			return;
		}

		String currentText = getText(0, getLength());
		String newText = currentText.substring(0, offs) + str + currentText.substring(offs);
		if (isNumeric(newText)) {
			if(Integer.parseInt(newText) <= 2000){
				super.insertString(offs, str, a);
			}else{
				remove(offs, offs);
				super.insertString(0, "2000", a);
			}
		}
	}

	private boolean isNumeric(String text) {
		return DIGITS.matcher(text).matches();
	}
}

