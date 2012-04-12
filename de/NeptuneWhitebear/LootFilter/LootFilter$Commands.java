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


import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.logging.Logger;

public class LootFilter$Commands implements CommandExecutor
{
    Logger mcLogger;

    public LootFilter$Commands( Logger mcLogger )
    {
        this.mcLogger = mcLogger;
    }

    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        if ( s.equalsIgnoreCase( "lootfilter" ) )
        {
            if ( strings[ 0 ].equalsIgnoreCase( "limit" ) || strings[ 0 ].equalsIgnoreCase( "unlimit" ) ||
                 strings[ 0 ].equalsIgnoreCase( "ignore" ) || strings[ 0 ].equalsIgnoreCase( "unignore" ) )
            {
                String base = strings[ 0 ];
                String perm = "limit";
                if ( strings[ 0 ].equalsIgnoreCase( "unignore" ) || base.equalsIgnoreCase( "ignore" ) )
                {
                    perm = "ignore";
                }
                if ( !commandSender.hasPermission( "lootfilter." + perm ) )
                {
                    LootFilter.playerMessage( (Player)commandSender, "You are not allowed to use this function." );
                    return true;
                }
                if ( strings.length < 2 )
                {
                    LootFilter.playerMessage( (Player)commandSender, "Usage: /lootfilter " + base + " <item>" +
                                                                     ( base.endsWith( "limit" ) ? " <amount>" : "" ) );
                    return true;
                }

                if ( strings[ 1 ].equalsIgnoreCase( "all" ) )
                {
                    if ( base.equalsIgnoreCase( "unlimit" ) || base.equalsIgnoreCase( "unignore" ) )
                    {
                        if ( base.equalsIgnoreCase( "unlimit" ) )
                            LootManager.clearLimitedMaterials( (Player)commandSender );
                        else
                            LootManager.clearIgnoredMaterials( (Player)commandSender );
                        LootFilter.playerMessage( (Player)commandSender, "Cleared " + perm + " list." );
                        return true;
                    }
                }

                Material mat = Material.matchMaterial( strings[ 1 ] );
                if ( mat == null )
                {
                    LootFilter.playerMessage( (Player)commandSender,
                                              "Material " + strings[ 1 ] + " could not be found." );
                    return true;
                }

                boolean result;
                if ( base.equalsIgnoreCase( "ignore" ) || base.equalsIgnoreCase( "limit" ) )
                {
                    try
                    {
                        int amount = 0;
                        if ( base.equalsIgnoreCase( "limit" ) )
                        {
                            amount = Integer.parseInt( strings[ 1 ] );
                        }
                        result = LootManager.setPlayerData( (Player)commandSender, mat, amount );
                    } catch ( NumberFormatException ex )
                    {
                        LootFilter.playerMessage( (Player)commandSender, "Invalid number format." );
                        return true;
                    }
                } else
                {
                    result = LootManager.unsetPlayerData( (Player)commandSender, mat );
                }
                if ( result )
                    LootFilter.playerMessage( (Player)commandSender,
                                              ( strings[ 0 ].equalsIgnoreCase( "ignore" ) ? "I" : "Uni" ) + "gnored " +
                                              mat.name().toLowerCase() + "." );
                else
                    LootFilter.playerMessage( (Player)commandSender, mat.name().toLowerCase() + " is " + ( strings[ 0 ]
                                                                                                                   .equalsIgnoreCase(
                                                                                                                           "ignore" ) ? "already" : "not" ) +
                                                                     " on your ignore list." );
                return true;
            } else if ( strings[ 0 ].equalsIgnoreCase( "list" ) )
            {
                HashMap<Material, Integer> materials = LootManager.getPlayerData( (Player)commandSender );

                if ( materials == null || materials.size() == 0 )
                {
                    LootFilter.playerMessage( (Player)commandSender, "Nothing ignored or limited." );
                    return true;
                }

                String out = "Materials limited/ignored: ";

                for ( Material mat : materials.keySet() )
                {
                    if ( materials.get( mat ) == -1 ) continue;
                    out += mat.name().toLowerCase();
                    if ( materials.get( mat ) != 0 )
                    {
                        out += " (" + materials.get( mat ) + ")";
                    }
                    out += ", ";
                }
                if ( materials.size() > 0 )
                {
                    out = out.substring( 0, out.length() - 2 );
                }

                LootFilter.playerMessage( (Player)commandSender, out );
                return true;
            } else
            {
                LootFilter.playerMessage( (Player)commandSender, "Option " + strings[ 0 ] + " not found." );
                return true;
            }

        }

        return false;
    }
}
