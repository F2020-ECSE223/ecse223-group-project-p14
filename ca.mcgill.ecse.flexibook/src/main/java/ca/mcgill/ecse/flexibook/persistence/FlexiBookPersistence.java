package ca.mcgill.ecse.flexibook.persistence;

import ca.mcgill.ecse.flexibook.model.FlexiBook;

public class FlexiBookPersistence{
	private static String filename = "data.flexiBook";
	
	
	public static void save(FlexiBook flexiBook){
	    PersistenceObjectStream.serialize(flexiBook);
	}

	public static FlexiBook load() {
	    PersistenceObjectStream.setFilename(filename);
	    FlexiBook flexiBook = (FlexiBook) PersistenceObjectStream.deserialize();
	    // model cannot be loaded - create empty FlexiBook
	    if (flexiBook == null) {
	        flexiBook = new FlexiBook();
	    }
	    return flexiBook;
	}
	
	
	public static void setFileName(String fileName) {
		FlexiBookPersistence.filename = fileName;
	}
	
	
	public static String getFileName() {
		return FlexiBookPersistence.filename;
	}
}
