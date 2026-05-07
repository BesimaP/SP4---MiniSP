# SP4 - MiniSP

**Entities:**

- **Patient** 
  The central entity in the system. Represents the person undergoing fertility treatment. 
  All other data is connected to the patient via Round. 
  In this version there is only one patient in the system (no login), but the entity is included so the system is correctly modelled and easy to extend. 
  Important attributes: name and date of birth are used to identify the patient. diagnosis describes the reason for treatment (e.g. PCOS or unexplained infertility).


- **Journey (abstract)**
  A Journey is the most important container in the system — everything else (events, medication, diary, appointments) belongs to a specific journey. 
  Journey is abstract, meaning you never create a plain Journey — only a specific type such as FertilityJourney. 
  All journey types share the same core data: start date and status. Important attributes: startDate (when the journey began), status (Active / Completed / Paused)


- **FertilityJourney**
  A FertilityJourney is a concrete journey type that extends Journey with fertility-specific data. 
  It represents one complete IVF treatment process from stimulation to pregnancy test. 
  A patient can have multiple rounds if the first attempt is unsuccessful. 
  Important attributes: roundNumber, eggsRetrieved and eggsFertilised are used to compare rounds in the history. 
  result (Positive / Negative / Pending) records the outcome of the pregnancy test.


- **Event**
  An event is a concrete step in the process — e.g. "Stimulation started", "Egg retrieval completed" or "Transfer performed". 
  Events are used to build the timeline and give the patient an overview of what has happened and when. 
  Important attributes: type indicates which phase the event belongs to. date and description provide context on the timeline.


- **MedicationLog** 
  During an IVF process the patient takes daily hormone-stimulating medication such as Gonal-F or Cetrotide — often for several weeks. 
  MedicationLog records what was taken, when and in what dose. 
  The taken field (boolean) makes it possible to use as a daily checklist. 
  Important attributes: medication (the name of the medication), dose (e.g. "150 IU"), taken (whether it was taken that day).


- **HormoneLog**
  During the stimulation period the patient's hormone levels are measured regularly — typically oestradiol and LH — to assess how the ovaries are responding to the medication. 
  HormoneLog stores these measurements so they can be displayed as a graph over time, giving the patient insight into their body's response to the treatment. 
  HormoneLog belongs specifically to FertilityJourney as hormone tracking is unique to fertility treatment. 
  Important attributes: hormone (e.g. "Oestradiol"), value (the measured value), unit (e.g. "pmol/L").


- **DiaryEntry**
  An IVF process is emotionally demanding. 
  The diary gives the patient a private place to write notes to themselves — symptoms, thoughts, questions for the doctor or simply how the day has been. 
  It is the most personal part of the system and distinguishes FertiliTrack from the clinics' own systems. 
  Important attributes: title (short headline), content (free text without limitation), date (automatically filled with today's date).


- **Appointment** 
  During an IVF process there are many appointments — scans, consultations, egg retrieval, transfer. 
  The Appointment entity collects them in one place so the patient does not have to keep track of them in a calendar or on paper. 
  The system displays upcoming appointments on the dashboard. Important attributes: type (e.g. "Follicle scan", "Transfer"), location (clinic or hospital), date and notes for practical details.