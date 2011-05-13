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


//**********************************************
//**********************************************
//* Index settings in Edit/Preferences
//**********************************************
//**********************************************

package cx.fbn.nevernote.dialog;

import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

import cx.fbn.nevernote.Global;

public class ConfigIndexPage extends QWidget {

	private final QSpinBox weightSpinner;
	private final QSpinBox sleepSpinner;
	private final QCheckBox indexAttachmentsLocally;
	private final QLineEdit regexEdit;
	
	public ConfigIndexPage(QWidget parent) {
//		super(parent);
							
		// Recognition weight
		QGroupBox weightGroup = new QGroupBox(tr("Recognition"));
		QLabel weightLabel = new QLabel(tr("Minimum Recognition Weight"));
		weightSpinner = new QSpinBox();
		weightSpinner.setRange(1,100);
		weightSpinner.setSingleStep(1);
		weightSpinner.setValue(Global.getRecognitionWeight());
		
		QHBoxLayout weightLayout = new QHBoxLayout();
		weightLayout.addWidget(weightLabel);
		weightLayout.addWidget(weightSpinner);
		weightGroup.setLayout(weightLayout);
		
		// Local attachment indexing
		QGroupBox attachmentGroup = new QGroupBox(tr("Attachments"));
		indexAttachmentsLocally = new QCheckBox(tr("Index Attachments Locally"));
		indexAttachmentsLocally.setChecked(Global.indexAttachmentsLocally());
		
		QHBoxLayout attachmentLayout = new QHBoxLayout();
		attachmentLayout.addWidget(indexAttachmentsLocally);
		attachmentGroup.setLayout(attachmentLayout);

		// Index sleep interval
		QGroupBox sleepGroup = new QGroupBox(tr("Index Interval"));
		QLabel sleepLabel = new QLabel(tr("Seconds between looking for unindexed notes"));
		sleepSpinner = new QSpinBox();
		sleepSpinner.setRange(30,600);
		sleepSpinner.setSingleStep(1);
		sleepSpinner.setValue(Global.getIndexThreadSleepInterval());

		QHBoxLayout sleepLayout = new QHBoxLayout();
		sleepLayout.addWidget(sleepLabel);
		sleepLayout.addWidget(sleepSpinner);
		sleepGroup.setLayout(sleepLayout);
		
		// Regular Expressions for word parsing
		QGroupBox regexGroup = new QGroupBox(tr("Word Parse"));
		QLabel regexLabel = new QLabel(tr("Regular Expression"));
		regexEdit = new QLineEdit();
		regexEdit.setText(Global.getWordRegex());

		QHBoxLayout regexLayout = new QHBoxLayout();
		regexLayout.addWidget(regexLabel);
		regexLayout.addWidget(regexEdit);		
		regexGroup.setLayout(regexLayout);
		
		
		QVBoxLayout mainLayout = new QVBoxLayout();
		mainLayout.addWidget(sleepGroup);
		mainLayout.addWidget(weightGroup);
		mainLayout.addWidget(attachmentGroup);
		mainLayout.addWidget(regexGroup);
		mainLayout.addStretch(1);
		setLayout(mainLayout);


	}
	
	
	//*****************************************
	//* Get for flag to index attachments 
	//*****************************************
	public boolean getIndexAttachmentsLocally() {
		return indexAttachmentsLocally.isChecked();
	}
	
	//*****************************************
	//* Word length get/set methods 
	//*****************************************
	public void setSleepInterval(int len) {
		sleepSpinner.setValue(len);
	}
	public int getSleepInterval() {
		return sleepSpinner.value();
	}


	
	//*****************************************
	//* Recognition Weight 
	//*****************************************
	public void setRecognitionWeight(int len) {
		weightSpinner.setValue(len);
	}
	public int getRecognitionWeight() {
		return weightSpinner.value();
	}
	
	
	
	//*****************************************
	//* Regex get/set methods 
	//*****************************************
	public void setRegex(String s) {
		regexEdit.setText(s);
	}
	public String getRegex() {
		return regexEdit.text();
	}

}
