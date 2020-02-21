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
    public static final String OUTCOME_TRASPORTO_PAZIENTE_DECEDUTO = "DECEDUTO";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_NC = "N.C.";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_NON_NECESSITA = "NON NECESSITA";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_NON_RINVENUTO = "NON RINVENUTO";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_REGOLARE = "REGOLARE";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_RIFIUTO_TRASPORTO = "RIFIUTA TRASPORTO";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_ALLONTANA = "SI ALLONTANA";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_TRATTENUTO = "TRATTENUTO DA ALTRI";
    public static final ArrayList<String> acceptedTransportPazienteOutcome = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(OUTCOME_TRASPORTO_PAZIENTE_DECEDUTO); 
            add(OUTCOME_TRASPORTO_PAZIENTE_NC); 
            add(OUTCOME_TRASPORTO_PAZIENTE_NON_NECESSITA);             
            add(OUTCOME_TRASPORTO_PAZIENTE_NON_RINVENUTO);
            add(OUTCOME_TRASPORTO_PAZIENTE_REGOLARE);            
            add(OUTCOME_TRASPORTO_PAZIENTE_RIFIUTO_TRASPORTO);             
            add(OUTCOME_TRASPORTO_PAZIENTE_ALLONTANA);
            add(OUTCOME_TRASPORTO_PAZIENTE_TRATTENUTO);
        } 
    }; 
    
    // Coscience
    public static final String COSCIENCE_NOT_SET = "O";
    public static final String COSCIENCE_NOT_SET_NUMERIC = "0";
    public static final String COSCIENCE_ALERT = "A";
    public static final String COSCIENCE_VERBAL = "V";
    public static final String COSCIENCE_PAIN = "P";
    public static final String COSCIENCE_UNRESPONSIVE = "U";
    public static final ArrayList<String> acceptedCosciences = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
			add(COSCIENCE_NOT_SET_NUMERIC);
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
    
    
    // MISSIONI Outcomes
    public static final String MISSIONE_OUTCOME_ALTRO = "ALTRO - VEDI NOTE";
    public static final String MISSIONE_OUTCOME_CONCLUSA_IN_POSTO = "CONCLUSA IN POSTO";
    public static final String MISSIONE_OUTCOME_DECEDUTO = "DECEDUTO";
    public static final String MISSIONE_OUTCOME_ERRORE_OPERATIVO = "ERRORE OPERATIVO";
    public static final String MISSIONE_OUTCOME_EVACUATO_ALS = "EVACUATO DA ALS";
    public static final String MISSIONE_OUTCOME_INTERROTTA = "INTERROTTA";
    public static final String MISSIONE_OUTCOME_INTERROTTA_AVARIA = "INTERROTTA - AVARIA";
    public static final String MISSIONE_OUTCOME_INTERROTTA_CHIAMATA_DOPPIA = "INTERROTTA - CHIAMATA DOPPIA";
    public static final String MISSIONE_OUTCOME_INTERROTTA_DOPPIA_CHIAMATA = "INTERROTTA DOPPIA - CHIAMATA";
    public static final String MISSIONE_OUTCOME_INTERROTTA_DA_RICHIEDENTE = "INTERROTTA - DA RICHIEDENTE";
    public static final String MISSIONE_OUTCOME_INTERROTTA_DIROTTATA = "INTERROTTA - DIROTTATA";
    public static final String MISSIONE_OUTCOME_INTERROTTA_INCIDENTE = "INTERROTTA - INCIDENTE";
    public static final String MISSIONE_OUTCOME_INTERROTTA_INDIRIZZO_ERRATO = "INTERROTTA - INDIRIZZO ERRATO";
    public static final String MISSIONE_OUTCOME_INTERROTTA_METEO = "INTERROTTA - METEO";
    public static final String MISSIONE_OUTCOME_INTERROTTA_MEZZO_PIU_COMPETITIVO = "INTERROTTA - MEZZO + COMPETITIVO"; 
    public static final String MISSIONE_OUTCOME_INTERROTTA_NI = "INTERROTTA - NI";
    public static final String MISSIONE_OUTCOME_INTERROTTA_NIM = "INTERROTTA - NIM";
    public static final String MISSIONE_OUTCOME_INTERROTTA_NIS = "INTERROTTA - NIS";
    public static final String MISSIONE_OUTCOME_INTERROTTA_NOTE = "INTERROTTA - NOTE";  
    public static final String MISSIONE_OUTCOME_NON_OPERATIVO = "NON OPERATIVO";
    public static final String MISSIONE_OUTCOME_NON_TRASPORTA = "NON TRASPORTA";
    public static final String MISSIONE_OUTCOME_ESERCITAZIONE = "PROVA - ESERCITAZIONE";
    public static final String MISSIONE_OUTCOME_REGOLARE = "REGOLARE";
    public static final String MISSIONE_OUTCOME_REGOLARE_NON_TRASPORTA = "REGOLARE - NON TRASPORTA";
    public static final String MISSIONE_OUTCOME_RIFIUTO = "RIFIUTO";
    public static final String MISSIONE_OUTCOME_RIFIUTO_METEO = "RIFIUTO PER METEO";
    public static final String MISSIONE_OUTCOME_RIFIUTO_SCADENZA_EFFEMERIDI = "RIFIUTO SCADENZA EFFEMERIDI";
    public static final String MISSIONE_OUTCOME_RIFIUTO_TECNICO_AEREONAUTICO = "RIFIUTO TECNICO/AEREONAUTICO";
    public static final String MISSIONE_OUTCOME_SI_ALLONTANA = "SI ALLONTANA";
    public static final String MISSIONE_OUTCOME_TRASF_PPI_CA = "TRASF.PPI CA";
    public static final String MISSIONE_OUTCOME_TRASF_PPI_PED = "TRASF.PPI PED";
    public static final String MISSIONE_OUTCOME_TRASF_PPI_PS = "TRASF.PPI PS";
	public static final ArrayList<String> acceptedMissioniOutcome = new ArrayList<String>() { 
		private static final long serialVersionUID = 1L;
		{ 
            add(MISSIONE_OUTCOME_ALTRO); 
            add(MISSIONE_OUTCOME_CONCLUSA_IN_POSTO); 
            add(MISSIONE_OUTCOME_DECEDUTO); 
            add(MISSIONE_OUTCOME_ERRORE_OPERATIVO); 
            add(MISSIONE_OUTCOME_EVACUATO_ALS); 
            add(MISSIONE_OUTCOME_INTERROTTA); 
            add(MISSIONE_OUTCOME_INTERROTTA_AVARIA); 
            add(MISSIONE_OUTCOME_INTERROTTA_CHIAMATA_DOPPIA); 
            add(MISSIONE_OUTCOME_INTERROTTA_DOPPIA_CHIAMATA); 
            add(MISSIONE_OUTCOME_INTERROTTA_DA_RICHIEDENTE); 
            add(MISSIONE_OUTCOME_INTERROTTA_DIROTTATA); 
            add(MISSIONE_OUTCOME_INTERROTTA_INCIDENTE); 
            add(MISSIONE_OUTCOME_INTERROTTA_INDIRIZZO_ERRATO); 
            add(MISSIONE_OUTCOME_INTERROTTA_METEO); 
            add(MISSIONE_OUTCOME_INTERROTTA_MEZZO_PIU_COMPETITIVO); 
            add(MISSIONE_OUTCOME_INTERROTTA_NI); 
            add(MISSIONE_OUTCOME_INTERROTTA_NIM); 
            add(MISSIONE_OUTCOME_INTERROTTA_NIS); 
            add(MISSIONE_OUTCOME_INTERROTTA_NOTE); 
            add(MISSIONE_OUTCOME_NON_OPERATIVO); 
            add(MISSIONE_OUTCOME_NON_TRASPORTA); 
            add(MISSIONE_OUTCOME_ESERCITAZIONE); 
            add(MISSIONE_OUTCOME_REGOLARE); 
            add(MISSIONE_OUTCOME_REGOLARE_NON_TRASPORTA); 
            add(MISSIONE_OUTCOME_RIFIUTO); 
            add(MISSIONE_OUTCOME_RIFIUTO_METEO); 
            add(MISSIONE_OUTCOME_RIFIUTO_SCADENZA_EFFEMERIDI); 
            add(MISSIONE_OUTCOME_RIFIUTO_TECNICO_AEREONAUTICO); 
            add(MISSIONE_OUTCOME_SI_ALLONTANA); 
            add(MISSIONE_OUTCOME_TRASF_PPI_CA); 
            add(MISSIONE_OUTCOME_TRASF_PPI_PED); 
            add(MISSIONE_OUTCOME_TRASF_PPI_PS); 
        } 
    };
    
    
    
}
