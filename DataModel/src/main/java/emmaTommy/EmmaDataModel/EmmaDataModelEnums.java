package emmaTommy.EmmaDataModel;

import java.util.ArrayList;


public class EmmaDataModelEnums {
	
	// SI / YES / NO
	public static final String PEDIATRIC_SI = "S";
	public static final String PEDIATRIC_NO = "N";
	
	// Qualifiche
	public static final String AUTISTA_ADD = "AUT IN ADD";
	public static final String AUTISTA = "AUTISTA";
	public static final String CAPOSERVIZIO = "CAPO SERVIZIO";
	public static final String COMANDANTE = "COMANDANTE";
	public static final String INFERMIERE_ADD = "INFERM IN ADD";
	public static final String INFERMIERE = "INFERMIERE";
	public static final String MEDICO_ADD = "MEDICO IN ADD";
	public static final String MEDICO = "MEDICO";
	public static final String SERVIZIO_CIVILE = "OP.SERV.CIVILE";
	public static final String PILOTA = "PILOTA";
	public static final String SOCCORRITORE_ADD = "SOCCORRITORE IN ADD";
	public static final String SOCCORRITORE = "SOCCORRITORE";
	public static final String TECNICO = "TECNICO";
	public static final String TECNICO_CNAS = "TECNICO CNAS";
	public static final String TECNICO_ELI = "TECNICO ELI";
	public static final ArrayList<String> acceptedQualifiche = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(AUTISTA_ADD); 
            add(AUTISTA); 
            add(CAPOSERVIZIO);             
            add(COMANDANTE);
            add(INFERMIERE_ADD);
            add(INFERMIERE);
            add(MEDICO_ADD);
            add(MEDICO);
            add(SERVIZIO_CIVILE);
            add(PILOTA);
            add(SOCCORRITORE_ADD);
            add(SOCCORRITORE);
            add(TECNICO);
            add(TECNICO_CNAS);
            add(TECNICO_ELI);
        } 
    }; 
    
    // Tipo Evento
    public static final String tipoEventoEmergenzaH12ECG_3 = "H12_ECG_3";
    public static final String tipoEventoEmergenzaH24ECG_3 = "H24_ECG_3";
    public static final String tipoEventoEmergenzaH12_3 = "H12_3";
    public static final String tipoEventoEmergenzaH24_3 = "H24_3";
    public static final String tipoEventoGettone_2 = "GET_2";
    public static final String tipoEventoGettone_3 = "GET_3";

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
    
    // Coscience
    public static final String COSCIENCE_NOT_SET = "O";
    public static final String COSCIENCE_ALERT = "A";
    public static final String COSCIENCE_VERBAL = "V";
    public static final String COSCIENCE_PAIN = "P";
    public static final String COSCIENCE_UNRESPONSIVE = "U";
    public static final ArrayList<String> acceptedCosciences = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(COSCIENCE_NOT_SET); 
            add(COSCIENCE_ALERT); 
            add(COSCIENCE_VERBAL);             
            add(COSCIENCE_PAIN);
            add(COSCIENCE_UNRESPONSIVE);
        } 
    }; 
    
    // GRAVITY CODES
    public static final String CODES_ROSSO = "R";
    public static final String CODES_GIALLO = "G";
    public static final String CODES_VERDE = "V";
    
    // RESPIRATORY FREQUENCE (FR)
    public static final int FR_NOT_SET = 0;
    public static final int FR_NORMALE = 1;
    public static final int FR_DIFFICOLTOSO = 2;
	public static final int FR_ASSENTE = 3;
	public static final ArrayList<Integer> acceptedFR = new ArrayList<Integer>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(FR_NOT_SET); 
            add(FR_NORMALE); 
            add(FR_DIFFICOLTOSO);             
            add(FR_ASSENTE);
        } 
    };
    
}
