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
	public static final String SOCCORRITORE_ADD = "SOCC IN ADD";
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
    public static final String tipoEventoEmergenzaH6_ECG_2plus = "H6_2+";
    public static final String tipoEventoEmergenzaH6_NOECG_2plus = "H6_2+_NE";
    public static final String tipoEventoEmergenzaH6_ECG_3 = "H6_3";
    public static final String tipoEventoEmergenzaH6_NOECG_3 = "H6_3_NE";   
    public static final String tipoEventoEmergenza_H8_ECG_2plus = "H8_2+";
    public static final String tipoEventoEmergenza_H8_NOECG_2plus = "H8_2+_NE";
    public static final String tipoEventoEmergenza_H8_ECG_3 = "H8_3";
    public static final String tipoEventoEmergenza_H8_NOECG_3 = "H8_3_NE";
    public static final String tipoEventoEmergenza_H12_ECG_2plus = "H12_2+";
    public static final String tipoEventoEmergenza_H12_NOECG_2plus = "H12_2+_NE";
    public static final String tipoEventoEmergenza_H12_ECG_3 = "H12_3";
    public static final String tipoEventoEmergenza_H12_NOECG_3 = "H12_3_NE";    
    public static final String tipoEventoEmergenza_H12_ECG_3_OLD = "H12_ECG_3";
    public static final String tipoEventoEmergenza_H12_NOECG_3_OLD = "H12_3";    
    public static final String tipoEventoEmergenza_H24_ECG_2plus = "H24_2+";
    public static final String tipoEventoEmergenza_H24_NOECG_2plus = "H24_2+_NE";    
    public static final String tipoEventoEmergenza_H24_ECG_3 = "H24_3";  
    public static final String tipoEventoEmergenza_H24_NOECG_3 = "H24_3_NE";  
    public static final String tipoEventoEmergenza_H24_ECG_3_OLD = "H24_ECG_3";
    public static final String tipoEventoEmergenza_H24_NOECG_3_OLD = "H24_3";    
    public static final String tipoEventoGettone_2_ECG = "GET_2";
    public static final String tipoEventoGettone_2_NOECG = "GET_2_NE";
    public static final String tipoEventoGettone_3_ECG = "GET_3";
    public static final String tipoEventoGettone_3_NOECG = "GET_3_NE";
    public static final String tipoEventoExhibitions = "EXHIBITIONs";
    
    // Ambulances
    public static final String codiceMezzoEmmaPlus101 = "VOLCAL+101";
    public static final String codiceMezzoEmmaDash101 = "VOLCAL_101";
    public static final String codiceMezzoEmmaPlus201 = "VOLCAL+201";
    public static final String codiceMezzoEmmaDash201 = "VOLCAL_201";
    public static final String codiceMezzoEmmaPlus301 = "VOLCAL+301";
    public static final String codiceMezzoEmmaDash301 = "VOLCAL_301";
    public static final String codiceMezzoEmmaPlus103 = "VOLCAL+103";
    public static final String codiceMezzoEmmaDash103 = "VOLCAL_103";
    public static final String codiceMezzoEmmaPlus203 = "VOLCAL+203";
    public static final String codiceMezzoEmmaDash203 = "VOLCAL_203";
    public static final String codiceMezzoEmmaPlus303 = "VOLCAL+303";
    public static final String codiceMezzoEmmaDash303 = "VOLCAL_303";    
    public static final String codiceMezzoEmmaPlus104 = "VOLCAL+104";
    public static final String codiceMezzoEmmaDash104 = "VOLCAL_104";
    public static final String codiceMezzoEmmaPlus204 = "VOLCAL+204";
    public static final String codiceMezzoEmmaDash204 = "VOLCAL_204";
    public static final String codiceMezzoEmmaPlus304 = "VOLCAL+304";
    public static final String codiceMezzoEmmaDash304 = "VOLCAL_304";    
    public static final String codiceMezzoEmmaPlus105 = "VOLCAL+105";
    public static final String codiceMezzoEmmaDash105 = "VOLCAL_105";
    public static final String codiceMezzoEmmaPlus205 = "VOLCAL+205";
    public static final String codiceMezzoEmmaDash205 = "VOLCAL_205";
    public static final String codiceMezzoEmmaPlus305 = "VOLCAL+305";
    public static final String codiceMezzoEmmaDash305 = "VOLCAL_305";    
    public static final String codiceMezzoEmmaPlus106 = "VOLCAL+106";
    public static final String codiceMezzoEmmaDash106 = "VOLCAL_106";
    public static final String codiceMezzoEmmaPlus206 = "VOLCAL+206";
    public static final String codiceMezzoEmmaDash206 = "VOLCAL_206";
    public static final String codiceMezzoEmmaPlus306 = "VOLCAL+306";
    public static final String codiceMezzoEmmaDash306 = "VOLCAL_306";
    public static final String codiceMezzoEmma10605A1 = "VOLCAL_106.05A1";

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
    public static final String OUTCOME_TRASPORTO_PAZIENTE_ALLONTANA = "REVOCA - SI ALLONTANA";
    public static final String OUTCOME_TRASPORTO_PAZIENTE_TRATTENUTO = "TRATTENUTO DA ALTRI";
    public static final String OUTCOME_TRASPORTO_RINVIATO_MEDICINA_GENERALE = "RINVIATO MEDICINA GENERALE";
    public static final String OUTCOME_TRASPORTO_REVOCATO_INTERVENTO = "REVOCATO INTERVENTO";
    public static final String OUTCOME_TRASPORTO_TRATTATO_SUL_POSTO = "TRATTATO SUL POSTO";
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
            add(OUTCOME_TRASPORTO_RINVIATO_MEDICINA_GENERALE);
            add(OUTCOME_TRASPORTO_REVOCATO_INTERVENTO);
            add(OUTCOME_TRASPORTO_TRATTATO_SUL_POSTO);
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
    public static final String MISSIONE_OUTCOME_VUOTO = "VUOTO"; // Paziente Non Trovato
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
            add(MISSIONE_OUTCOME_VUOTO);
        } 
    };
    
    
    
}
