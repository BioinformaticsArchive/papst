/**
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO ANY PERSON OBTAINING A COPY 
 * OF THIS SOFTWARE AND ASSOCIATED DOCUMENTATION FILES (THE "SOFTWARE"), TO USE
 * THE SOFTWARE WITHOUT RESTRICTION, INCLUDING WITHOUT LIMITATION THE RIGHTS TO
 * USE, COPY, MODIFY, MERGE, PUBLISH, DISTRIBUTE, AND/OR SELL COPIES OF THE 
 * SOFTWARE, AND TO PERMIT PERSONS TO WHOM THE SOFTWARE IS FURNISHED TO DO SO,
 * SUBJECT TO THE FOLLOWING LIMITATIONS OF LIABILITY AND SUCH LANGUAGE SHALL BE 
 * INCLUDED IN ALL COPIES OR REDISTRIBUTIONS OF ANY COMPLETE OR PORTION OF THIS 
 * SOFTWARE.
 * 
 * SOFTWARE IS BEING DEVELOPED IN PART AT THE NATIONAL INSTITUTE OF ARTHRITIS 
 * AND MUSCULOSKELTAL AND SKIN DISEASES (NIAMS), NATIONAL INSTITUTES OF HEALTH
 * (NIH) BY AN EMPLOYEE OF THE UNITED STATES GOVERNMENT IN THE COURSE OF THEIR 
 * OFFICIAL DUTIES. PURSUANT TO TITLE 17, SECTION 105 OF THE UNITED STATES CODE, 
 * THIS SOFTWARE IS NOT SUBJECT TO COPYRIGHT PROTECTION AND IS IN THE PUBLIC 
 * DOMAIN. EXCEPT AS CONTAINED IN THIS NOTICE, THE NAME OF THE AUTHORS, THE
 * NATIONAL INSTITUTE OF ARTHRITIS AND MUSCULOSKELTAL AND SKIN DISEASES, OR THE
 * NATIONAL INSTITUTES OF HEALTH  MAY NOT  BE USED TO ENDORSE OR PROMOTE PRODUCTS
 * DERIVED FROM THIS SOFTWARE WITHOUT SPECIFIC PRIOR WRITTEN PERMISSION FROM 
 * THE NIAMS OR THE NIH. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF 
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO WARRANTIES OF 
 * MERCHANTABILITY, FITNESS  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO
 * EVENT SHALL THE AUTHORS OR UNITED STATES GOVERNMENT OR ANY AGENCY THEREOF BE 
 * LIABLE FOR ANY CLAIM, DAMAGES OR ANY OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE THEREOF.
 * 
 * Author: Paul W. Bible
 */
package papst.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import papst.parsers.GenomeParserFactory;
import papst.parsers.GenomeParserInterface;
import papst.parsers.ParameterizedGenomeParser;


public class GenomeParserWizzard extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String[] options = new String[]{"--","accession","chrom","strand","start","end","exonCount","exStarts","exEnds","name"};
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTable table;
	private File file;
	private JPanel dataPanel;
	private JCheckBox tabCheckBox;
	private JCheckBox spaceCheckBox;
	private JCheckBox commaCheckBox;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel_1;
	private JTextField txtDefaultparsername;
	
	private GenomeParserInterface genomeParser;
	private String genomeParserName;
	private GenomeParserFactory parserFactory;

	/**
	 * Create the dialog.
	 */
	public GenomeParserWizzard(File file,GenomeParserFactory parserFactory) {
		this.file = file;
		this.parserFactory = parserFactory;
		this.setModalityType(JDialog.ModalityType.APPLICATION_MODAL);
		
		this.setTitle("Create a custom parser");
		
		//------------  Autogenerated by Eclipse Window Builder -----------------
		setBounds(100, 100, 658, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel filePanel = new JPanel();
			contentPanel.add(filePanel, BorderLayout.NORTH);
			filePanel.setLayout(new BorderLayout(0, 0));
			{
				JLabel lblNewLabel = new JLabel("Current File: ");
				filePanel.add(lblNewLabel, BorderLayout.WEST);
			}
			{
				textField = new JTextField();
				textField.setEditable(false);
				filePanel.add(textField, BorderLayout.CENTER);
				textField.setColumns(10);
				textField.setText(file.getAbsolutePath());
			}
			{
				btnNewButton = new JButton("Preview");
				btnNewButton.addActionListener(getPreviewListener());
				filePanel.add(btnNewButton, BorderLayout.EAST);
			}
		}
		{
			dataPanel = new JPanel();
			contentPanel.add(dataPanel, BorderLayout.CENTER);
			dataPanel.setLayout(new BorderLayout(0, 0));
			{
				JPanel delimiterPanel = new JPanel();
				dataPanel.add(delimiterPanel, BorderLayout.NORTH);
				{
					JLabel delimitersLabel = new JLabel("Select Delimiters:");
					delimiterPanel.add(delimitersLabel);
				}
				{
					tabCheckBox = new JCheckBox("Tab");
					tabCheckBox.setSelected(true);
					delimiterPanel.add(tabCheckBox);
					tabCheckBox.addActionListener(getPreviewListener());
				}
				{
					spaceCheckBox = new JCheckBox("Space");
					delimiterPanel.add(spaceCheckBox);
					spaceCheckBox.addActionListener(getPreviewListener());
				}
				{
					commaCheckBox = new JCheckBox("Comma");
					delimiterPanel.add(commaCheckBox);
					commaCheckBox.addActionListener(getPreviewListener());
				}
			}
			{
				scrollPane = new JScrollPane();
				dataPanel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(getOkListener());
				{
					lblNewLabel_1 = new JLabel("Custom Parser Name: ");
					buttonPane.add(lblNewLabel_1);
				}
				{
					txtDefaultparsername = new JTextField();
					txtDefaultparsername.setText("default_parser_name");
					buttonPane.add(txtDefaultparsername);
					txtDefaultparsername.setColumns(30);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(getCancelListener());
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		//---------------- END autogenerated portion ----------------

		genomeParser = null;
		firePreviewAction();
		
	}//end constructor
	
	/*
	 * Print a logging and debugging message
	 */
	public static void print(String msg){
		System.out.println("GenomeParserWizzard: "+msg);
	}
	
	
	private String getRegexDelimiters(){
		
		String[] delimiters = new String[]{"\t"," ",","};
		
		String useRegex = "";
		
		int delimCount = 0;
		
		if(tabCheckBox.isSelected()){
			useRegex += delimiters[0];
			delimCount += 1;
		}
		
		if(spaceCheckBox.isSelected()){
			
			if(delimCount > 0){
				useRegex += "|";
			}
			useRegex += delimiters[1];
			delimCount += 1;
		}
		
		if(commaCheckBox.isSelected()){
			
			if(delimCount > 0){
				useRegex += "|";
			}
			useRegex += delimiters[2];
		}
		
		
		return useRegex;
	}//end method, getRegexDelimiters
	
	
	private DefaultTableModel getTablePreview(){
		
		//control variables
		int maxLines = 5;
		int numLines = 0;
		
		//get regex corresponding to 
		String regex = getRegexDelimiters();
		
		//create the data table
		Vector<Vector<Object>> data = new Vector<Vector<Object> >();
		
		boolean reachedTest = false;
		
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			
			//parser loop
			while(scanner.hasNext() && numLines < maxLines){
				
				reachedTest = true;
				
				//get the current line
				String line = scanner.nextLine();
				
				//skip common comment delimiters
				if(line.startsWith("#") || line.startsWith("!")){
					continue;
				}
				
				//get parts of the line, split by the regex
				String[] parts = line.split(regex);
				
				//Populate the row vector
				Vector<Object> row = new Vector<Object>();
				for(String val: parts){
					//print(s);
					row.add(val);
				}
				
				//add row to the table matrix
				data.add(row);
			
				//increment control variable
				++numLines;
			}
			
			
			
			
			//close the scanner
			scanner.close();
			if(reachedTest == false){
				return null;
			}
			
		} catch (FileNotFoundException e) {
			//should not reach, provide info if it does.
			e.printStackTrace();
		}
		
		//add strings for a type row above each column
		Vector<Object> types = new Vector<Object>();
		Vector<String> columns = new Vector<String>();
		for(int i = 0; i < data.get(0).size(); ++i){
			types.add("--");
			columns.add("column_"+(i+1));
		}
		
		//insert types at the top
		data.insertElementAt(types, 0);
		
		//create a default model specifying that only the first row is editable.
		DefaultTableModel model = new DefaultTableModel(data,columns){
			private static final long serialVersionUID = 1L;

			//allow only the first row to be edited.
			@Override
			public boolean isCellEditable(int row,int col){
				if(row == 0){
					return true;
				}else{
					return false;
				}
			}
		};
		
		
		
		//return this updated model
		return model;
	}//end DefaultTableModel
	
	

	
	//get the action to be performed n each preview update
	private ActionListener getPreviewListener(){
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				firePreviewAction();
			}
		};
	}//end method, getPreviewListener
	
	
	private void firePreviewAction(){
		
		DefaultTableModel tableModel = getTablePreview();
		
		if(tableModel == null){
			JOptionPane.showMessageDialog(scrollPane,"The file '"+file.getName()+"' is either empty or not a text file.","Non-text or Empty File Error",JOptionPane.ERROR_MESSAGE);
			dispose();
		}else{
		
			//creat the table based on the model derived from the file.
			table = new JTable(tableModel){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
	
				//must override to make the type row editable.
				@Override
				public TableCellEditor getCellEditor(int row,int column){
					if(row == 0){
						return new DefaultCellEditor(getTrackValueComboBox());
					}else{
						return super.getCellEditor(row,column);
					}
				}
			};
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tableModel.fireTableDataChanged();
			scrollPane.setViewportView(table);
		
		}
		
	}//end method, firePreviewAction
	
	
	private JComboBox<String> getTrackValueComboBox(){
		
		JComboBox<String> comboBox = new JComboBox<String>(options);
		return comboBox;
	}//end method, getTrackValueComboBox
	
	
	private ActionListener getCancelListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//dispose of the dialog
				// action has been cancelled
				dispose();
				
			}
		};
		
	}
	
	private ActionListener getOkListener(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				//table is populated
				if(table.getRowCount() > 0){
					
					String parserName = txtDefaultparsername.getText();
					
					
					
					StringBuffer errorMsg = new StringBuffer("");
					HashMap<String, Integer> indexMap = checkTableTypes(errorMsg);
					
					
					
					if(indexMap == null){
						//print("ERRORS: "+errorMsg.toString());
						JOptionPane.showMessageDialog(scrollPane,errorMsg.toString(),"Column Error",JOptionPane.ERROR_MESSAGE);
					
					}else if(parserFactory.hasParser(parserName)){
						JOptionPane.showMessageDialog(scrollPane,"The parser name: '"+parserName+"' already exists.\nPlease Choose another name.","Duplicate Parser Error",JOptionPane.ERROR_MESSAGE);
					}else{
						//print("parser name: "+parserName);
						//print(""+table.getRowCount() + " " + table.getColumnCount());
						String regex = getRegexDelimiters();
						//print (regex);
						
						//TODO
						genomeParser = new ParameterizedGenomeParser(indexMap, regex);
						genomeParserName = parserName;
						
						dispose();
					}
					
				}else{
					//print("error");
					JOptionPane.showMessageDialog(scrollPane,"An unknown error has occurred.","Unknown Error",JOptionPane.ERROR_MESSAGE);
					dispose();
				}//end else, not enough rows, unknown table error
				
			}//end overridden method, actionPerformed
		}; // end annonymous call return
	}//end method, getOkListener
	
	
	private HashMap<String,Integer> checkTableTypes(StringBuffer msg){
		
		if(table.getRowCount() >= 2){
			
			boolean areTypesGood = true;
			
			//initialize a cost most
			HashMap<String,Integer> costMap = new HashMap<String,Integer>();
			for(String key: options){
				if(key.compareTo("--") == 0){
					continue;
				}else{
					costMap.put(key, new Integer(1));
				}
			}
			
			int totalCost = 0;
			
			
			HashMap<String,Integer> valueIndexMap = new HashMap<String, Integer>();
			
			for(int i = 0; i < table.getColumnCount(); ++i){
				String value = (String)table.getValueAt(0, i);
				
				// if not default value
				if(costMap.containsKey(value)){
					//get the key cost
					int keyCost = costMap.get(value);
					//add cost to total
					totalCost += keyCost;
					
					if(keyCost == 1){
						valueIndexMap.put(value, new Integer(i));
					}
					
					//add a high value to break the totalCost count 
					costMap.put(value, options.length);
				}
			}
			
			//TODO error checking
			
			if(totalCost < 5){
				msg.append("Not enough columns selected.\n");
				msg.append("Must select one each of 'chorm','strand','start','end', and 'accession'\n");
				areTypesGood = false;
			}
			
			if(totalCost > 9){
				msg.append("Too many columns selected.\n");
				msg.append("Must select one each of 'chorm','strand','start','end', and 'accession'.\n'exonCount, exStarts,exEnds and name are optional.'\n");
				areTypesGood = false;
			}
			
			
			//Chrom
			if(!valueIndexMap.containsKey("chrom")){
				areTypesGood = false;
				msg.append("Must choose a chrom column\n");
			}
			
			//strand
			if(!valueIndexMap.containsKey("strand")){
				areTypesGood = false;
				msg.append("Must choose a strand column\n");
			}else{
				String value = (String)table.getValueAt(1, valueIndexMap.get("strand"));
				
				if(!(value.startsWith("+") || value.startsWith("-")) ){
					areTypesGood = false;
					msg.append("Strand must be + or -.");
				}
				
			}
			
			//start
			if(!valueIndexMap.containsKey("start")){
				areTypesGood = false;
				msg.append("Must choose a start column\n");
			}else if(valueIndexMap.containsKey("start")){
				String value = (String)table.getValueAt(1, valueIndexMap.get("start"));
				
				try{
					Integer.parseInt(value);
				}catch(Exception e){
					areTypesGood = false;
					msg.append("Start column must be a numeric integer.\n");
				}
			}
			
			//end
			if(!valueIndexMap.containsKey("end")){
				areTypesGood = false;
				msg.append("Must choose an end column\n");
			}else if(valueIndexMap.containsKey("end")){
				String value = (String)table.getValueAt(1, valueIndexMap.get("end"));
				
				try{
					Integer.parseInt(value);
				}catch(Exception e){
					areTypesGood = false;
					msg.append("End column must be a numeric integer.\n");
				}
			}
			
			
			if(!valueIndexMap.containsKey("accession")){
				areTypesGood = false;
				msg.append("Must choose an accession column\n");
			}
			
			
			if(valueIndexMap.containsKey("exCount")){
				String value = (String)table.getValueAt(1, valueIndexMap.get("end"));
				
				try{
					Integer.parseInt(value);
				}catch(Exception e){
					areTypesGood = false;
					msg.append("exCount column must be a numeric integer.\n");
				}
			}
			
			
			if(areTypesGood){
				return valueIndexMap;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	public GenomeParserInterface getGenomeParser(){
		return genomeParser;
	}
	
	public String getGenomeParserName(){
		return genomeParserName;
	}
	
	public boolean isGenomeParserGood(){
		return genomeParser != null;
	}
	
	
	
	
	
	

}
