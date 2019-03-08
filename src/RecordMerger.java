import java.util.Arrays;
public class RecordMerger {
	public static final String FILENAME_COMBINED = "combined.csv";
	private static final String testCsv1 = "/Users/chouchua/Downloads/cantest/data/second.csv";
	private static final String testCsv2= "/Users/chouchua/Downloads/cantest/data/example2.csv";
	String resultPath;
	String[] files;
	ParserManager manager;
	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv.
	 * @throws Exception bad things had happened.
	 */
	public static void main(final String[] args) throws Exception {
		// your code starts here.
//		String[] testFiles = {testCsv1, testCsv2};
		String[] testFiles = {"/Users/chouchua/Downloads/cantest/data/rr2.csv"};
		String[] filesToMerge = args.length > 0 ? args: testFiles;
		System.out.println("Reading records from...");
		System.out.println(Arrays.asList(filesToMerge));
		//REMEMBER to change your desired output path right below
		String resultPath = "/Users/chouchua/Downloads/cantest/data";
		
		//case 1: merge ingredient_list and bom_list
		RecordMerger merger = new RecordMerger(filesToMerge, resultPath);
		merger.readInputSources();
		merger.merge();//generates combined.csv
	}
	
	public RecordMerger(String[] files, String resultPath) {
		this.files = files;
		this.resultPath = resultPath;
		if(files.length == 0) {
			System.out.println("No files to read.");
		}
		manager = new ParserManager();
		//add all available parsers
		manager.addParser("csv", new CsvParser());
		manager.addParser("html", new HTMLParser());
	}
	
	/**
	 * Combine all entities processed across all files
	 */
	private void merge() {
		this.manager.merge(resultPath);
	}
	
	public void readInputSources() {
		for(String file : this.files) {
			this.manager.readFile(file);
		}
		return;
	}
}
