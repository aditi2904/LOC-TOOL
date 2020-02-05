import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import org.apache.commons.io.FileUtils;
import java.util.Base64;
import java.io.InputStreamReader;
import java.nio.file.Files;



public class DuplicateFileRemoverTwo {

	public static HashMap<String, String> hashmap = new HashMap<String, String>();
	public static HashMap<String, String> dupHashMap = new HashMap<String, String>();

	int dupNum = 0;
	int regNum = 0;
	static int dupCount = 0;
	//specify the path (folder) where you want to search files
	 static String fileLocation ;

	//extension .java
	 static String searchThisExtn ;

	public static void dupScan(String folderToScan){
		try
		{
			File dir = new File(folderToScan);
if(dir.isFile()==false){
			String[] extensions = new String[] { "java" };
			String searchThisExtn= ".java";


			@SuppressWarnings("unchecked")

			List<File> fileList = (List<File>) FileUtils.listFiles(dir, extensions, true);
			System.out.print(fileList.size() + " - ");
			for(File oneFile : fileList){
				if(oneFile.isFile())
				{
					MessageDigest md = MessageDigest.getInstance("MD5");
					byte[] fileBytes = Files.readAllBytes(oneFile.toPath());
					String fileString = Base64.getEncoder().encodeToString(md.digest(fileBytes));

					if(hashmap.containsKey(fileString))
					{
						String original = hashmap.get(fileString);
						String duplicate = oneFile.getAbsolutePath();
						dupHashMap.put(Integer.toString(dupCount++),duplicate);
					}
					else
					{
						hashmap.put(fileString, oneFile.getAbsolutePath());
					}
				}else if(oneFile.isDirectory())
				{
					dupScan(oneFile.getAbsolutePath());
				}
			}
			int bc=0;
			boolean comment1 = false;
			int lineCount=0;
			int total=0;
			// Getting an iterator
	        Iterator hmIterator = hashmap.entrySet().iterator();
	        while (hmIterator.hasNext()) {
	        	  Map.Entry mapElement = (Map.Entry)hmIterator.next();
	        	   String key = (String)mapElement.getValue();
	        	  FileInputStream fstream = new FileInputStream(key);
	        	  BufferedReader br = new BufferedReader(new InputStreamReader(fstream ,"UTF-8"));
	        	 String strLine;

	        		  while ((strLine = br.readLine()) != null)   {

		        	      if (strLine.trim().isEmpty()){

		        	         bc++;


		        	      }
		        	      strLine = strLine.trim();

		        			 if(strLine.startsWith("/*") && strLine.endsWith("*/")){
		        				lineCount++;
		    				} else if(strLine.startsWith("/*") && !strLine.endsWith("*/")){
		    					lineCount ++;
		    					comment1 = true;
		    				} else if(true == comment1){
		    					lineCount ++;
		    					if(strLine.endsWith("*/")){
		    						comment1 = false;
		    					}
		    				} else if(strLine.startsWith("//")){
		    					lineCount++;

		    				}

total++;



		  			}
	        	  //Close the input stream
	        	  br.close();

	        }

	        System.out.print( hashmap.size() +" - ");// unique file
	        System.out.print(bc + " - "); // blank lines
	        System.out.print(lineCount + " - "); // comment sline
	        System.out.print((total-lineCount-bc)); // loc
			}
else
{
	int bc=0;
	int total=0;
	int lineCount=0;
	boolean comment1 = false;
	FileInputStream fstream = new FileInputStream(dir);
	  BufferedReader br = new BufferedReader(new InputStreamReader(fstream));


String strLine;
while ((strLine = br.readLine()) != null)   {

	  if (strLine.trim().isEmpty()){

	     bc++;

	  }
	  if(strLine.startsWith("/*") && strLine.endsWith("*/")){
			lineCount++;
		} else if(strLine.startsWith("/*") && !strLine.endsWith("*/")){
			lineCount ++;
			comment1 = true;
		} else if(true == comment1){
			lineCount ++;
			if(strLine.endsWith("*/")){
				comment1 = false;
			}
		} else if(strLine.startsWith("//")){
			lineCount++;

		}
	  total++;

}
System.out.print(1 + " - ");

System.out.print(1 + " - ");
System.out.print(bc+ " - ");
System.out.print(lineCount + " - "); // comment sline
System.out.print((total-lineCount-bc)); // loc



}
		}

		catch (Exception x)
		{
			System.out.println("Error Message : "  + x.getMessage());
		}




	}





	public static void main(String[] args) {

		fileLocation = args[0];

		dupScan(fileLocation);





	}
}