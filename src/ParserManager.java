import java.util.HashMap;

public class ParserManager {
	private HashMap<String, Parser> parsers = new HashMap<>();
	
//	public ParserManager() {
//		this.parsers = new HashMap<>();
//	}
	
	public void addParser(String string, Parser parser) {//provider future support of new parsers
		parsers.put(string, parser);
	}
	
	public void readFile(String name) {
		int i = name.lastIndexOf('.');
		if(i == -1) throw new RuntimeException(name + " has no extension.");
		String fileType = getFileType(name);
		Parser parser = parsers.get(fileType);
		if(parser == null) throw new RuntimeException("No parser registered for " + fileType + " files.");
		try {
			parser.parse(name);
		} catch(Exception e) {
			throw new RuntimeException("Failed to parse " + name);
		}
	}

	private static String getFileType(String file) {
		if(file == null) return null;
		return file.substring(file.lastIndexOf('.') + 1);
	}

	public void merge(String resultPath) {
		System.out.println("Merging entities...");
		EntityGroup entities = new EntityGroup();
		for(String parser: parsers.keySet()) {
			if(parsers.get(parser).getEntity() != null) {
				entities.addEntity(parsers.get(parser).getEntity());
			}
		}
		entities.merge(resultPath);
	}
}
