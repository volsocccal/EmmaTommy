package emmaTommy.TommyDataModel;

import java.util.ArrayList;
import java.util.HashMap;


public class TommyDataModelEnums {

	// Qualifiche Tommy
	public static final String AUTISTA = "AUT";
	public static final String CAPOEQUIPAGGIO = "CAPO";
	public static final String SOCCORRITORE_ADD = "SOCC_ADD";
	public static final String SOCCORRITORE = "SOCC";
	public static final ArrayList<String> acceptedQualifiche = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(AUTISTA); 
            add(CAPOEQUIPAGGIO);  
            add(SOCCORRITORE_ADD);
            add(SOCCORRITORE);
        } 
    }; 
    public static final HashMap<String, String> qualificheConversion = new HashMap<String, String>(){
        
		private static final long serialVersionUID = 1L;
		{
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.AUTISTA, emmaTommy.TommyDataModel.TommyDataModelEnums.AUTISTA);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.AUTISTA_ADD, emmaTommy.TommyDataModel.TommyDataModelEnums.SOCCORRITORE);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.CAPOSERVIZIO, emmaTommy.TommyDataModel.TommyDataModelEnums.CAPOEQUIPAGGIO);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.SOCCORRITORE, emmaTommy.TommyDataModel.TommyDataModelEnums.SOCCORRITORE);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.SOCCORRITORE_ADD, emmaTommy.TommyDataModel.TommyDataModelEnums.SOCCORRITORE_ADD);
        }
    }; 

    // Genders
    public static final String MALE_GENDER = "M";
    public static final String FEMALE_GENDER = "F";
    
    // Transport Outcome
    public static final String OUTCOME_TRASPORTO_DECEDUTO = "DECEDUTO";
    public static final String OUTCOME_TRASPORTO_NC = "N.C.";
    public static final String OUTCOME_TRASPORTO_NON_NECESSITA = "NON_NECESSITA";
    public static final String OUTCOME_TRASPORTO_NON_RINVENUTO = "NON RINVENUTO";
    public static final String OUTCOME_TRASPORTO_REGOLARE = "REGOLARE";
    public static final String OUTCOME_TRASPORTO_RIFIUTO_TRASPORTO = "RIFIUTA TRASPORTO";
    public static final String OUTCOME_TRASPORTO_ALLONTANA = "SI ALLONTANA";
    public static final String OUTCOME_TRASPORTO_TRATTENUTO = "TRATTENUTO DA ALTRI";
    public static final ArrayList<String> acceptedTransportOutcome = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(OUTCOME_TRASPORTO_DECEDUTO); 
            add(OUTCOME_TRASPORTO_NC); 
            add(OUTCOME_TRASPORTO_NON_NECESSITA);             
            add(OUTCOME_TRASPORTO_NON_RINVENUTO);
            add(OUTCOME_TRASPORTO_REGOLARE);            
            add(OUTCOME_TRASPORTO_RIFIUTO_TRASPORTO);             
            add(OUTCOME_TRASPORTO_ALLONTANA);
            add(OUTCOME_TRASPORTO_TRATTENUTO);
        } 
    }; 
    
    public static final String eventoEmergenzaConvenzioneH12 = "EMER-H12";
    public static final String eventoEmergenzaConvenzioneH24 = "EMER-H24";
    public static final String eventoEmergenzaGettone = "EMER-GET";
    public static final String eventoEmergenzaGara = "EMER-GARA";
    public static final HashMap<String, String> tipoEventoConversion = new HashMap<String, String>(){
        
		private static final long serialVersionUID = 1L;
		{
						    
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_2, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            
            //put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.SOCCORRITORE, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGara);
        }
    }; 
    
}
