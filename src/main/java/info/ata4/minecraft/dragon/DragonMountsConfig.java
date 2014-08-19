/*
 ** 2013 May 30
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package info.ata4.minecraft.dragon;

import net.minecraftforge.common.config.Configuration;

/**
 *
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 */
public class DragonMountsConfig {
    
    // config properties
    private boolean eggsInChests = false;
    private int dragonEntityID = -1;
    private boolean debug = false;
    
    public DragonMountsConfig(Configuration config) {
        eggsInChests = config.get("server", "eggsInChests", eggsInChests, "Spawns dragon eggs in generated chests when enabled").getBoolean(eggsInChests);
        dragonEntityID = config.get("server", "dragonEntityID", dragonEntityID, "Overrides the entity ID for dragons to fix problems with manual IDs from other mods.\nSet to -1 for automatic assignment (recommended).\nWarning: wrong values may cause crashes and loss of data!").getInt(dragonEntityID);
        debug = config.get("client", "debug", debug, "Debug mode. Unless you're a developer or are told to activate it, you don't want to set this to true.").getBoolean(debug);
        
        if (config.hasChanged()) {
            config.save();
        }
    }

    public boolean isEggsInChests() {
        return eggsInChests;
    }

    public int getDragonEntityID() {
        return dragonEntityID;
    }

    public boolean isDebug() {
        return debug;
    }
}
