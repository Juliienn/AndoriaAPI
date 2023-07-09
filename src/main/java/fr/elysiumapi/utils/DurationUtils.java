package fr.elysiumapi.utils;

import com.google.common.collect.Maps;
import fr.elysiumapi.exceptions.IllegalDurationException;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public final class DurationUtils {

    private static final HashMap<String, Long> durationKeys = Maps.newHashMap();

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
        if(millis < 0) {
            throw new IllegalDurationException();
        }

        long[] durations = new long[]{
                toMonths(millis),
                TimeUnit.MILLISECONDS.toDays(millis) % 30,
                TimeUnit.MILLISECONDS.toHours(millis) % 24,
                TimeUnit.MILLISECONDS.toMinutes(millis) % 60,
                TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        };

        String[] units = new String[]{" Mois ", " Jours ", " Heures ", " Minutes ", " Secondes"};
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < durations.length; i++) {
            if (durations[i] > 0) {
                sb.append(durations[i]).append(units[i]);
            }
        }

        return sb.toString();

    }
}