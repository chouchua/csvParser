import java.util.ArrayList;
import java.util.List;

public class HTMLParser extends Parser {
	List<EntityRecord> processedFiles;
	EntityRecord entityBuilder;
	
	public HTMLParser() {
		processedFiles = new ArrayList<EntityRecord>();
		entityBuilder = new EntityRecord();
	}
	
	public EntityRecord getEntity() {
		return this.entityBuilder;
	}
	
	public void parse(String fileName) {
		//tbd
		//did not have time to complete html parser
		//this logic in this code block does the following:
		// - get key names of records, then call entityBuilder.addKeys(keys);
		// - add records by calling entityBuilder.addRecord(record)
		// - the functions are defined in entityRecord class
	}
}
