import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class EntityRecord {
	ArrayList<HashMap> recordCollection = new ArrayList<HashMap>();
	Map<String, HashMap> idMap = new HashMap<>();
	HashMap<Integer, String> currentKeyMap;
	TreeSet<String> keySet; //keySet used for
	TreeSet<String> idSet; //idSet used for
	private static final String ID_IDENTIFIER = "ID";
	int idIndex;//the position of id element in the record
	
	public EntityRecord() {
		idSet = new TreeSet<String>();
		//id is sorted as we insert, no need to sort a potential huge list of id's when generating combined.csv
		keySet = new TreeSet<String>();
		//TreeSet is chosen as data structure, for following reasons
		// - stores unique keys (when new files are read, new keys are added)
		// - o(logN) insertion of new keys (combining files 
		// - sorts keys upon insertion
	}
	
	public void addRecord(String[] record) {//assumes each record has an id field
		//each record(row) is a string array
		HashMap<String, String> entity;
		String id = record[idIndex];
		idSet.add(id);
		if(idMap.containsKey(id)) {
			entity = idMap.get(id);
		} else {
			entity = new HashMap<>();
		}
		for(int i = 0; i < record.length; i++) {
			entity.put(currentKeyMap.get(i), record[i]);
		}
		idMap.put(id, entity);
	}
	
	public HashMap<String, String> getRecord(String id) {
		return idMap.get(id);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String id: idSet) {
			HashMap record = idMap.get(id);
			String comma = "";
			for(String key: keySet) {
				if(record.containsKey(key)) {
					sb.append(comma);
					comma = ", ";
					sb.append(key);
					sb.append(":");
					sb.append(record.get(key));
				}
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public void addKeys(String[] keys) {
		System.out.println("Creating key map:");
		this.currentKeyMap = new HashMap<Integer,String>();
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].equals(ID_IDENTIFIER)) idIndex = i;
			currentKeyMap.put(i, keys[i]);
			keySet.add(keys[i]);
		}
		System.out.println(this.currentKeyMap);
	}
}
