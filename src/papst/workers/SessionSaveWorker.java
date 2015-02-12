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
package papst.workers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.NotSerializableException;
import java.util.zip.GZIPOutputStream;
import javax.swing.SwingWorker;


import papst.PapstModel; 


public class SessionSaveWorker extends SwingWorker<Void, Void> {
	private File inFile;
	private PapstModel model;
	
	public SessionSaveWorker(File inFile,PapstModel model){
		this.inFile = inFile;
		this.model = model;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		//System.out.println("doInBackground called");
		//Wrap the output
		// Buffer -> GZip -> File 
		FileOutputStream fos = new FileOutputStream(inFile);
		GZIPOutputStream gz = new GZIPOutputStream(fos);
		BufferedOutputStream bufferOut = new BufferedOutputStream(gz);
		
		//Wrap the object stream
		// object -> byte array
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		
		try{
			out.writeObject(model);
		}catch(NotSerializableException e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		//get byte array
		byte[] bytes = bos.toByteArray();
		
		//get total size
		double totalSize = bytes.length;

		double count = 0;
		
		//write the bytes to the buffered output
		for(byte b: bytes){
			++count;
			bufferOut.write(b);
			
			//calculate percentage
			int percent = (int)(100*(count/totalSize));
			
			//get listeners about the progress change
			setProgress(percent);
		}
		
		out.close();
		bos.close();
		bufferOut.close();
		gz.close();
		fos.close();
		
		return null;
	}
}
