package ca.mcgill.ecse.flexibook.persistence;

import ca.mcgill.ecse.flexibook.model.FlexiBook;

public class FlexiBookPersistence{
	private static String filename = "data.flexiBook";
	
	/**
	 * 
	 * @param flexiBook
	 * @author AntoineW
	 * @author mikewang
	 * @author jedla
	 * @author chengchen
	 * @author Catherine
	 * @author gtjarvis
	 */
	public static void save(FlexiBook flexiBook){
	    PersistenceObjectStream.serialize(flexiBook);
	}

	/**
	 * @author AntoineW
	 * @author mikewang
	 * @author jedla
	 * @author chengchen
	 * @author Catherine
	 * @author gtjarvis
	 */
	public static FlexiBook load() {
	    PersistenceObjectStream.setFilename(filename);
	    FlexiBook flexiBook = (FlexiBook) PersistenceObjectStream.deserialize();
	    // model cannot be loaded - create empty FlexiBook
	    if (flexiBook == null) {
	        flexiBook = new FlexiBook();
	    }
	    else {
	    	flexiBook.reinitialize();
	    }
	    return flexiBook;
	}
	
    /**
     * 
     * @param fileName
     * @author AntoineW
	 * @author mikewang
	 * @author jedla
	 * @author chengchen
	 * @author Catherine
	 * @author gtjarvis
     * 
     */
	public static void setFileName(String fileName) {
		FlexiBookPersistence.filename = fileName;
	}
	
	/**
     * @author AntoineW
	 * @author mikewang
	 * @author jedla
	 * @author chengchen
	 * @author Catherine
	 * @author gtjarvis
	 */
	public static String getFileName() {
		return FlexiBookPersistence.filename;
	}
}
