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


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class LootFilter$Commands implements CommandExecutor
{
    Logger mcLogger;

    public LootFilter$Commands(Logger mcLogger)
    {
        this.mcLogger = mcLogger;
    }

    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        if( s.equalsIgnoreCase( "lootfilter" ) )
        {
            mcLogger.info( strings[0] );
        }

        return false;
    }
}
