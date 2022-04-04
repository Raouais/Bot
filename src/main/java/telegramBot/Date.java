package telegramBot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Date {
    private String dateExpirationBlocageStaking;
    private String dateDuMoisEnCours;
    private int nbJoursEnStaking;

    Date(){
    }

    Date(int nbJoursEnStaking){
        setDateExpirationBlocageStaking(expirationBlocageStaking());
        setDateDuMoisEnCours(moisEnCours());
        setNbJoursEnStaking(nbJoursEnStaking);
    }

    public String getDateExpirationBlocageStaking() {
        return dateExpirationBlocageStaking;
    }

    public String getDateDuMoisEnCours() {
        return dateDuMoisEnCours;
    }

    public int getNbJoursEnStaking() {
        return nbJoursEnStaking;
    }

    public void setDateExpirationBlocageStaking(String dateExpirationBlocageStaking) {
        this.dateExpirationBlocageStaking = dateExpirationBlocageStaking;
    }

    public void setDateDuMoisEnCours(String dateDuMoisEnCours) {
        this.dateDuMoisEnCours = dateDuMoisEnCours;
    }

    public void setNbJoursEnStaking(int nbJoursEnStaking) {
        if (nbJoursEnStaking < 14){
            this.nbJoursEnStaking = 14;
        }
        this.nbJoursEnStaking = nbJoursEnStaking;
    }

    public String expirationBlocageStaking(){
        Calendar dateDuJour = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        dateDuJour.add(Calendar.DAY_OF_MONTH, 14);
        dateDuJour.setLenient(false);
        String dateExpiration = sdf.format(dateDuJour.getTime());
        return dateExpiration;
    }

    public String expirationBlocageStaking(int nbJoursEnStaking){
        Calendar dateDuJour = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        dateDuJour.add(Calendar.DAY_OF_MONTH, nbJoursEnStaking);
        dateDuJour.setLenient(false);
        String dateExpiration = sdf.format(dateDuJour.getTime());
        return dateExpiration;
    }

    public String expirationBlocageStaking(int nbJoursEnStaking, long timeStamp){
        timeStamp *= 1000;

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis( timeStamp );

        System.out.println( cal.get( Calendar.YEAR ) );
        System.out.println( cal.get( Calendar.MONTH ) );
        System.out.println( cal.get( Calendar.DATE ) );

        cal.add(Calendar.DAY_OF_MONTH, nbJoursEnStaking);

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        cal.setLenient(false);

        String dateExpiration = sdf.format(cal.getTime());
        return dateExpiration;
    }

    public String moisEnCours(){
        GregorianCalendar startDate = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        startDate.setLenient(false);
        //System.out.println(sdf.format(startDate.getTime()));
        String moisEnCours = sdf.format(startDate.getTime());
        return moisEnCours;
    }

    public Calendar calendrierExpirationBlocageStaking(int nbJoursEnStaking, long timeStamp){
        timeStamp *= 1000;

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis( timeStamp );
        cal.add(Calendar.DAY_OF_MONTH, nbJoursEnStaking);


        return cal;
    }


    public Calendar calendrierMoisEnCours(){
        return new GregorianCalendar();
    }

    /*
    public Calendar calendrierJourDepot(long timeStamp){
        timeStamp *= 1000;

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis( timeStamp );

        return cal;
    }
     */

    public String timeStampToGregorianCalendar(long timeStamp){
        timeStamp *= 1000;

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTimeInMillis( timeStamp );

        System.out.println( cal.get( Calendar.YEAR ) );
        System.out.println( cal.get( Calendar.MONTH ) );
        System.out.println( cal.get( Calendar.DATE ) );


        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        cal.setLenient(false);
        String moisEnCours = sdf.format(cal.getTime());
        return moisEnCours;
    }

    // surchage pour laisser l'user choisir
    public long calculDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        long res = 0;
        try {
            java.util.Date dateAvant = sdf.parse(moisEnCours());
            java.util.Date dateApres = sdf.parse(expirationBlocageStaking());
            long diff = dateApres.getTime() - dateAvant.getTime();
            res = (diff / (1000*60*60*24));
            //System.out.println("Nombre de jours entre les deux dates est: "+res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res + 1;
    }

    /*
    public long calculDate(String premiereDate, String dateExpiration){
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        long res = 0;
        try {
            java.util.Date dateAvant = sdf.parse(premiereDate);
            java.util.Date dateApres = sdf.parse(dateExpiration);
            long diff = dateApres.getTime() - dateAvant.getTime();
            res = (diff / (1000*60*60*24));
            //System.out.println("Nombre de jours entre les deux dates est: "+res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
     */

    /*
    public int anneeEnCours(){
        // Création d’un objet correspondant à la date système (date du jour)
        GregorianCalendar dateDuJour = new GregorianCalendar();
        // Récupération de l’année en cours à partir de la date du jour
        int anneeEnCours = dateDuJour.get(Calendar.YEAR);
        return anneeEnCours;
    }
     */
}
