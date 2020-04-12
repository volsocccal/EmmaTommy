package emmaTommy.EmmaDataModel;

import org.apache.logging.log4j.LogManager;

import junit.framework.TestCase;

public abstract class XMLParserTest extends TestCase {

	static org.apache.logging.log4j.Logger logger = LogManager.getLogger("dataModel");
	protected String test_data_folder = "data_xml_test/";
	protected String test_data_folder_membro = test_data_folder + "membro/";
	protected String test_data_folder_missione = test_data_folder + "missione/";
	protected String test_data_folder_paziente = test_data_folder + "paziente/";
	protected String test_data_folder_tratta = test_data_folder + "tratta/";
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public XMLParserTest( String testName )
    {
        super( testName );
    }
	
}
