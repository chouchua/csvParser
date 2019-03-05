
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;
import au.com.bytecode.opencsv.CSVWriter;

public class EntityGroup {
	HashMap<String, Integer> idMapToEntity; //maps id to entity
	HashMap<Integer, EntityRecord> entityMap; //each file type is an entity
	TreeSet<String> keySet;//consolidates keys from different entities
	TreeSet<String> idSet; //consolidates record id across different entities
	Integer entityCount = 0;
	private static final String FILE_NAME = "combined.csv";
	
	public EntityGroup() {
		this.entityMap = new HashMap<Integer, EntityRecord>();
		this.idMapToEntity = new HashMap<String, Integer>();
		this.keySet = new TreeSet<String>();
		this.idSet = new TreeSet<String>();
	}
	
	void addEntity(EntityRecord e) {
		this.keySet.addAll(e.keySet);
		this.idSet.addAll(e.idSet);
		entityCount++;
		entityMap.put(entityCount, e);
		for(String id: e.idSet) {//this builds an index, we can access record from the right entity
			idMapToEntity.put(id, entityCount);//this record belongs in a certain entity
		}
	}

	public void merge(String resultPath) {
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(new FileWriter(resultPath + "/"+ FILE_NAME));
			String[] header = this.keySet.toArray(new String[this.keySet.size()]);
			System.out.println("Consolidated keys:");
			System.out.println(Arrays.toString(header));
			csvWriter.writeNext(header);
			for(String id : this.idSet) {
				String[] record = new String[this.keySet.size()];
				Integer index = 0;
				for(String key: keySet) {
					record[index] = this.entityMap.get(idMapToEntity.get(id)).getRecord(id).get(key);
					index++;
				}
				csvWriter.writeNext(record);
			}
		}
		catch(Exception ee)
		{
			ee.printStackTrace();
		}
		finally
		{
			try
			{
				csvWriter.close();
				System.out.println("Generated results in: " + resultPath + "/" + FILE_NAME);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
		}
	}
}
