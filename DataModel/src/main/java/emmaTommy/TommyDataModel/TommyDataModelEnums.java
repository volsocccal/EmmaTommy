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
    
    public static final String eventoEmergenzaConvenzioneH6 = "EMER-H6";
   
    // Convenzioni
    public static final String noConvenzione = "NOCONVENTION";
    public static final String eventoEmergenzaConvenzioneH8 = "EMER-H8";
    public static final String eventoEmergenzaConvenzioneH12 = "EMER-H12";
    public static final String eventoEmergenzaConvenzioneH24 = "EMER-H24";
    public static final String eventoEmergenzaGettone = "EMER-GET";
    public static final String eventoEmergenzaGara = "EMER-GARA";
    public static final HashMap<String, String> tipoEventoEmmaTommyConversion = new HashMap<String, String>() {   
     
		private static final long serialVersionUID = 1L;
		{			
			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoNoConvention, emmaTommy.TommyDataModel.TommyDataModelEnums.noConvenzione);
    
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH6_ECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH6);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH6_NOECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH6);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH6_ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH6);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenzaH6_NOECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH6);

			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H8_ECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH8);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H8_NOECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH8);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H8_ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH8);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H8_NOECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH8);

			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_ECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_NOECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_NOECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);   
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_ECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H12_NOECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12);

			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_ECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_NOECG_2plus, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_ECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_NOECG_3, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_ECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoEmergenza_H24_NOECG_3_OLD, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);  
            
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_2_ECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_2_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_3_ECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoGettone_3_NOECG, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            
            
            put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.tipoEventoExhibitions, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGara);
        }
		
    };
    public static final HashMap<String, String> tipoEventoTommyConversion = new HashMap<String, String>() {   
        
		private static final long serialVersionUID = 1L;
		{						
    
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.noConvenzione, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH6, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH8, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH12, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaConvenzioneH24);
			
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGettone);
            
			put(emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGara, emmaTommy.TommyDataModel.TommyDataModelEnums.eventoEmergenzaGara);
			
        }
		
    };
		
    
    public static final String codiceMezzoTommy101 = "VOLCAL+101";    
    
    // Mezzi
    public static final String codiceMezzoTommy103 = "VOLCAL+103";       
    public static final String codiceMezzoTommy104 = "VOLCAL+104";       
    public static final String codiceMezzoTommy105 = "VOLCAL+105";       
    public static final String codiceMezzoTommy106 = "VOLCAL+106";    
	public static final HashMap<String, String> codiceMezzoConversion = new HashMap<String, String>() {        
		private static final long serialVersionUID = 1L;
		{			
			    
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus101, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash101, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus201, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash201, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus301, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash301, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy101);
			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus103, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash103, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus203, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash203, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus303, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash303, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy103);
			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus104, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash104, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus204, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash204, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus304, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash304, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy104);
			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus105, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash105, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus205, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash205, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus305, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash305, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy105);
			
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus106, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash106, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus206, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash206, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaPlus306, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmmaDash306, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
			put(emmaTommy.EmmaDataModel.EmmaDataModelEnums.codiceMezzoEmma10605A1, emmaTommy.TommyDataModel.TommyDataModelEnums.codiceMezzoTommy106);
		
		}
	};
   
    
}
