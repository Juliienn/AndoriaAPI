package fr.andoriaapi.utils;

import com.google.common.collect.Maps;
import fr.andoriaapi.exceptions.IllegalDurationException;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public final class DurationUtils {

    private static final HashMap<String, Long> durationKeys = Maps.newHashMap();

    public static final Long TIMESTAMP_LIMIT = 2147480047000L;

    static{
        durationKeys.put("y", (long)12*30*24*3600*1000);
        durationKeys.put("mo", (long)30*24*3600*1000);
        durationKeys.put("w", (long)7*24*3600*1000);
        durationKeys.put("d", (long)24*3600*1000);
        durationKeys.put("h", (long)3600*1000);
        durationKeys.put("m", (long)60*1000);
        durationKeys.put("s", (long)1000);
    }

    public static boolean dateFormatCorrect(String text){
        String regExp = "^(?:(\\d+y)?(\\d+mo)?(\\d+w)?(\\d+d)?(\\d+h)?(\\d+m)?(\\d+s)?)$";
        return text.matches(regExp);
    }
    private static long toMonths(long millis) {
        return TimeUnit.MILLISECONDS.toDays(millis) / 30;
    }
    private static long toYears(long millis) {
        return TimeUnit.MILLISECONDS.toDays(millis) / 365;
    }

    public static long getTimeFromString(String text){
        long time = 0;
        String pretext = "";
        char[] times = text.toCharArray();
        for (int i = 0; i < times.length; i++){
            char c = times[i];
            if(StringUtils.isNumeric(c)){
                pretext+=c;
            }else{
                if(String.valueOf(c).equalsIgnoreCase("m")){
                    if(times.length >= i+2 && String.valueOf(times[i+1]).equals("o")){
                        time += (long) durationKeys.get("mo")*Integer.parseInt(pretext);
                        i++;
                        pretext = "";
                        continue;
                    }
                }
                time += (long) durationKeys.get(String.valueOf(c).toLowerCase())*Integer.parseInt(pretext);
                pretext = "";
            }
        }
        return time;
    }

    public static String getDuration(long millis) throws IllegalDurationException {
        if (millis < 0) {
            throw new IllegalDurationException();
        }

        long[] durations = new long[]{
                toYears(millis),
                toMonths(millis) % 12,
                TimeUnit.MILLISECONDS.toDays(millis) % 30,
                TimeUnit.MILLISECONDS.toHours(millis) % 24,
                TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        };

        String[] units = new String[]{" Années ", " Mois ", " Jours ", " Heures ", " Minutes ", " Secondes"};
        StringBuilder sb = new StringBuilder();

        int count = 0; // Compteur pour suivre le nombre de durées ajoutées
        for (int i = 0; i < durations.length; i++) {
            if (durations[i] > 0) {
                sb.append(durations[i]).append(units[i]);
                count++;

                if (count >= 2) {
                    break; // Sortir de la boucle après avoir ajouté les deux durées les plus élevées
                }
            }
        }

        return sb.toString();
    }
    public static String getDurationLimited(long millis) throws IllegalDurationException {
        if (millis < 0) {
            throw new IllegalDurationException();
        }

        long[] durations = new long[]{
                toYears(millis),
                toMonths(millis) % 12,
                TimeUnit.MILLISECONDS.toDays(millis) % 30,
                TimeUnit.MILLISECONDS.toHours(millis) % 24,
                TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        };

        String[] units = new String[]{"y", "mo", "d", "h", "m", "s"};
        StringBuilder sb = new StringBuilder();

        int count = 0; // Compteur pour suivre le nombre de durées ajoutées
        for (int i = 0; i < durations.length; i++) {
            if (durations[i] > 0) {
                sb.append(durations[i]).append(units[i]);
                count++;

                if (count >= 2) {
                    break; // Sortir de la boucle après avoir ajouté les deux durées les plus élevées
                }
            }
        }

        return sb.toString();
    }
}