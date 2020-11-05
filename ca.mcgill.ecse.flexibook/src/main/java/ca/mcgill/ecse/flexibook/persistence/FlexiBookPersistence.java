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
// requires method reinitialize() to works
	    else {
	    	flexiBook.reinitialize();
	    }
	    return flexiBook;
	}
	
// this should be in UMP file	
//	public void reinitialize() {
//		BookableService.reintializeUniquebookableservicesByName(this.getClass());
//	}
	
	public static void setFileName(String fileName) {
		FlexiBookPersistence.filename = fileName;
	}
	
	
	public static String getFileName() {
		return FlexiBookPersistence.filename;
	}
}
