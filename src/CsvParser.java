import au.com.bytecode.opencsv.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class CsvParser extends Parser {
//	private static final String SAMPLE_CSV_FILE_PATH = "/Users/chouchua/Downloads/cantest/data/convertcsv.csv";
	private static final String SAMPLE_CSV_FILE_PATH = "/Users/chouchua/Documents/weddingPickUp.csv";
	List<EntityRecord> processedFiles;
	EntityRecord entityBuilder;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvParser testReader = new CsvParser();
		testReader.parse(SAMPLE_CSV_FILE_PATH);
	}
	
	public CsvParser() {
		processedFiles = new ArrayList<EntityRecord>();
		entityBuilder = new EntityRecord();
	}
	
	public EntityRecord getEntity() {
		return this.entityBuilder;
	}
	
	public void parse(String fileName) {
		CSVReader csvReader = null;
		try {
			System.out.println("Reading: " + fileName);
            Reader reader = new BufferedReader(new FileReader(fileName));
            csvReader = new CSVReader(reader);
            String[] nextRecord;
            HashMap<Integer, String> keyMap;
          	//first row of CSV is the set of key names
            if((nextRecord = csvReader.readNext()) != null) {
            	entityBuilder.addKeys(nextRecord);
            } else {
            	System.out.println("invalid file format");
            }
            // Reading records one by one in a String array
            while ((nextRecord = csvReader.readNext()) != null) {//Reads the next line from the buffer and converts to a string array.
                entityBuilder.addRecord(nextRecord);
            }
            System.out.println("Entities built so far in .csv files...");
            System.out.println(entityBuilder);
        } catch(IOException e) {
        	System.out.println(e);
        } finally {
        	try
    		{
    			csvReader.close();
    		}
    		catch(Exception ee)
    		{
    			ee.printStackTrace();
    		}
        }
	}
}
