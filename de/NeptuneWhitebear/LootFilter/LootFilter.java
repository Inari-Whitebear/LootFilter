/*
 *   This file is part of LootFilter.
 *
 *
 *   LootFilter is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   LootFilter is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with LootFilter. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package de.NeptuneWhitebear.LootFilter;


import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class LootFilter extends JavaPlugin
{

    Logger                    mcLogger;
    LootFilter$PlayerListener listener;

    public void onEnable()
    {
        mcLogger = getLogger();

        new LootManager();
        listener = new LootFilter$PlayerListener( mcLogger );
        getServer().getPluginManager().registerEvents( listener, this );


        getCommand( "lootfilter" ).setExecutor( new LootFilter$Commands( mcLogger ) );

    }

    public static void playerMessage( Player player, String message )
    {
        player.sendMessage( "[LootFilter] " + message );
    }

}
