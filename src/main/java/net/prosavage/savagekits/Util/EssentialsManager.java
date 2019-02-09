package net.prosavage.savagekits.Util;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import org.bukkit.Bukkit;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import static com.earth2me.essentials.I18n.tl;

public class EssentialsManager {

    public static Essentials getEssentialsInstance() {
        return (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
    }

    public static long getNextUse(final User user, String kitName, Map<String, Object> kit) throws Exception {
        if (user.isAuthorized("essentials.kit.exemptdelay")) {
            return 0L;
        }

        final Calendar time = new GregorianCalendar();

        double delay = 0;
        try {
            // Make sure delay is valid
            delay = kit.containsKey("delay") ? ((Number) kit.get("delay")).doubleValue() : 0.0d;
        } catch (Exception e) {
            throw new Exception(tl("kitError2"));
        }

        // When was the last kit used?
        final long lastTime = user.getKitTimestamp(kitName);

        // When can be use the kit again?
        final Calendar delayTime = new GregorianCalendar();
        delayTime.setTimeInMillis(lastTime);
        delayTime.add(Calendar.SECOND, (int) delay);
        delayTime.add(Calendar.MILLISECOND, (int) ((delay * 1000.0) % 1000.0));

        if (lastTime == 0L || lastTime > time.getTimeInMillis()) {
            // If we have no record of kit use, or its corrupted, give them benefit of the doubt.
            return 0L;
        } else if (delay < 0d) {
            // If the kit has a negative kit time, it can only be used once.
            return -1;
        } else if (delayTime.before(time)) {
            // If the kit was used in the past, but outside the delay time, it can be used.
            return 0L;
        } else {
            // If the kit has been used recently, return the next time it can be used.
            return delayTime.getTimeInMillis();
        }
    }

}
