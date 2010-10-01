/*
 * This file is part of NeverNote 
 * Copyright 2009 Randy Baumgarte
 * 
 * This file may be licensed under the terms of of the
 * GNU General Public License Version 2 (the ``GPL'').
 *
 * Software distributed under the License is distributed
 * on an ``AS IS'' basis, WITHOUT WARRANTY OF ANY KIND, either
 * express or implied. See the GPL for the specific language
 * governing rights and limitations.
 *
 * You should have received a copy of the GPL along with this
 * program. If not, go to http://www.gnu.org/licenses/gpl.html
 * or write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 *
*/

package cx.fbn.nevernote.dialog;

import com.trolltech.qt.gui.QDialog;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QListWidget;
import com.trolltech.qt.gui.QPushButton;

public class SpellCheck extends QDialog {

	private boolean 	replacePressed;
	private boolean		cancelPressed;
	private final QLabel	currentWord;
	private final QLineEdit	replacementWord;
	private String misspelledWord;
	private final QPushButton replace;
	private final QPushButton ignore;
	private final QListWidget suggestions;
	
	
	// Constructor
	public SpellCheck() {
		replacePressed = false;
		cancelPressed = false;
		setWindowTitle(tr("Spell Check"));
		QGridLayout grid = new QGridLayout();
		setLayout(grid);
		QGridLayout suggestionGrid = new QGridLayout();
		QGridLayout buttonGrid = new QGridLayout();
		
		currentWord = new QLabel(misspelledWord);
		replacementWord = new QLineEdit();
		suggestions = new QListWidget();
		
		replacementWord.textChanged.connect(this, "validateInput()");
		suggestions.itemSelectionChanged.connect(this, "replacementChosen()");
		
		suggestionGrid.addWidget(currentWord, 1, 1);
		suggestionGrid.addWidget(new QLabel(tr("Suggestion")), 2,1);
		suggestionGrid.addWidget(replacementWord, 3, 1);
		suggestionGrid.addWidget(suggestions,4,1);
		suggestionGrid.setContentsMargins(10, 10,  -10, -10);
		grid.addLayout(suggestionGrid,1,1);
		
		replace = new QPushButton(tr("Replace"));
		ignore = new QPushButton(tr("Ignore"));
		replace.clicked.connect(this, "replaceButtonPressed()");
		ignore.clicked.connect(this, "ignoreButtonPressed()");
		QPushButton cancel = new QPushButton(tr("Cancel"));
		cancel.clicked.connect(this, "cancelButtonPressed()");
		suggestionGrid.addWidget(replace, 1, 2);
		suggestionGrid.addWidget(ignore, 2, 2);
		buttonGrid.addWidget(new QLabel(), 1,1);
		buttonGrid.addWidget(cancel, 1,2);
		buttonGrid.addWidget(new QLabel(), 1,3);
		buttonGrid.setColumnStretch(1, 10);
		buttonGrid.setColumnStretch(3, 10);
		grid.addLayout(buttonGrid,2,1);
	}
	
	// The OK button was pressed
	@SuppressWarnings("unused")
	private void replaceButtonPressed() {
		replacePressed = true;
		cancelPressed = false;
		close();
	}
	
	// The CANCEL button was pressed
	@SuppressWarnings("unused")
	private void cancelButtonPressed() {
		replacePressed = false;
		cancelPressed = true;
		close();
	}
	
	// The ignore button was pressed
	@SuppressWarnings("unused")
	private void ignoreButtonPressed() {
		replacePressed = false;
		cancelPressed = false;
		close();
	}
	
	// Get the userid from the field
	public String getReplacementWord() {
		return replacementWord.text();
	}
	
	// Set the current misspelled word
	public void setWord(String w) {
		misspelledWord = w;
		currentWord.setText(tr("Word: ") +misspelledWord);
	}
	
	// Check if the OK button was pressed
	public boolean replacePressed() {
		return replacePressed;
	}
	
	// Check if the OK button was pressed
	public boolean cancelPressed() {
		return cancelPressed;
	}
	
	// Validate user input
	public void validateInput() {
		replace.setEnabled(true);
		if (replacementWord.text().trim().equals("")) {
			replace.setEnabled(false);
			return;
		}
	}
	
	private void replacementChosen() {
		String sel = suggestions.currentItem().text();
		replacementWord.setText(sel);
	}
	
	
	// Add a suggestion
	public void addSuggestion(String word) {
		suggestions.addItem(word);
	}
	
	// Set the current suggestion
	public void setCurrentSuggestion(String word) {
		replacementWord.setText(word);
	}
	
	public void setNoSuggestions(boolean enable) {
		if (enable) {
			replacementWord.setEnabled(true);
			replace.setEnabled(true);
			suggestions.setEnabled(true);
		} else {
			replacementWord.setEnabled(false);
			replace.setEnabled(false);
			suggestions.setEnabled(false);
		}
	}
	
	public void setSelectedSuggestion(int index) {
		if (index < suggestions.count())
			suggestions.setCurrentRow(index);
	}
}