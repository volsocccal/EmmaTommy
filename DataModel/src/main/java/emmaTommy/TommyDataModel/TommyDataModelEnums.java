package emmaTommy.TommyDataModel;

import java.util.ArrayList;
import java.util.HashMap;


public class TommyDataModelEnums {

	// Qualifiche Tommy
	public static final String AUTISTA = "AUTISTA";
	public static final String CAPOEQUIPAGGIO = "CAP";
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
    public static final String OUTCOME_TRASPORTO_ALLONTANA = "REVOCA - SI ALLONTANA";
    public static final String OUTCOME_TRASPORTO_TRATTENUTO = "TRATTENUTO DA ALTRI";
    public static final String OUTCOME_TRASPORTO_RINVIATO_MEDICINA_GENERALE = "RINVIATO MEDICINA GENERALE";
    public static final String OUTCOME_TRASPORTO_REVOCATO_INTERVENTO = "REVOCATO INTERVENTO";
    public static final String OUTCOME_TRASPORTO_TRATTATO_SUL_POSTO = "TRATTATO SUL POSTO";
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
            add(OUTCOME_TRASPORTO_RINVIATO_MEDICINA_GENERALE);
            add(OUTCOME_TRASPORTO_REVOCATO_INTERVENTO);
            add(OUTCOME_TRASPORTO_TRATTATO_SUL_POSTO);
        } 
    }; 
    
    public static final String eventoEmergenzaConvenzioneH12 = "EMER-H12";
    public static final String eventoEmergenzaConvenzioneH24 = "EMER-H24";
    public static final String eventoEmergenzaGettone = "EMER-GET";
    public static final String eventoEmergenzaGara = "EMER-GARA";
    public static final HashMap<String, String> tipoEventoConversion = new HashMap<String, String>() {        
		private static final long serialVersionUID = 1L;
		{						    
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12ECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12ECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH12_3_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
            
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24ECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24_3_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24ECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH24NOECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_2_ECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_2_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_3_ECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_3_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            
            //put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.SOCCORRITORE, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGara);
        }
    }; 
    
}
