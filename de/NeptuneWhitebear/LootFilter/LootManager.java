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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;

public class LootManager
{

    public static  LootManager                                 instance;
    private static HashMap<Player, HashMap<Material, Integer>> playerData;

    public LootManager()
    {
        instance = this;
        playerData = new HashMap<Player, HashMap<Material, Integer>>();
    }

    public static HashMap<Material, Integer> getPlayerData( Player player )
    {
        if ( playerData.containsKey( player ) )
        {
            return playerData.get( player );
        }
        return null;
    }

    private static void newPlayerData( Player player )
    {
        //TODO load from file
        playerData.put( player, new HashMap<Material, Integer>() );
    }

    public static boolean setPlayerData( Player player, Material material, Integer amount )
    {
        if ( !playerData.containsKey( player ) )
        {
            newPlayerData( player );
            //playerData.put( player, new HashMap<Material, Integer>() );
        }

        HashMap<Material, Integer> playerData = getPlayerData( player );
        if ( playerData.containsKey( material ) )
        {
            if ( playerData.get( material ).intValue() != amount.intValue() )
            {
                playerData.put( material, amount );
                return true;
            }
            return false;
        } else
        {
            playerData.put( material, amount );
            return true;
        }
    }

    public static boolean unsetPlayerData( Player player, Material material )
    {
        if ( !playerData.containsKey( player ) )
        {
            return false;
        }

        HashMap<Material, Integer> playerData = getPlayerData( player );
        if ( playerData.containsKey( material ) )
        {
            playerData.remove( material );
            return true;
        } else
            return false;
    }

    public static HashSet<Material> getIgnoredMaterials( Player player )
    {
        HashMap<Material, Integer> playerData = getPlayerData( player );
        if ( playerData == null )
        {
            return null;
        } else
        {
            return new HashSet<Material>( playerData.keySet() );
        }
    }

    public static void clearPlayerData( Player player )
    {
        if ( getPlayerData( player ) != null )
        {
            playerData.remove( player );
        }
    }

    public static void clearMaterials( Player player, boolean limited )
    {
        HashMap<Material, Integer> playerData = getPlayerData( player );
        if ( playerData == null ) return;

        for ( Material mat : playerData.keySet() )
        {
            if ( ( !limited && playerData.get( mat ) == 0 ) || ( limited && playerData.get( mat ) > 0 ) )
            {
                playerData.remove( mat );
            }
        }
    }

    public static void clearLimitedMaterials( Player player )
    {
        clearMaterials( player, true );

    }

    public static void clearIgnoredMaterials( Player player )
    {
        clearMaterials( player, false );
    }

    public static int getPickupAmount( Player player, Material material, Integer amount )
    {
        HashMap<Material, Integer> playerData = getPlayerData( player );
        if ( playerData == null )
        {
            return -1;
        }
        if ( !playerData.containsKey( material ) ) return -1;
        if ( playerData.get( material ) == 0 )
        {
            if ( player.hasPermission( "lootfilter.ignore" ) )
                return 0;
            else
                return -1;
        }
        int playerAmount = 0;
        for ( ItemStack itemStack : player.getInventory().getContents() )
        {
            if ( itemStack == null ) continue;
            if ( itemStack.getType() == material )
            {
                playerAmount += itemStack.getAmount();
            }
        }
        if ( playerAmount + amount <= playerData.get( material ) )
            return -1;
        if ( playerAmount + 1 < playerData.get( material ) )
        {
            return amount - ( ( playerAmount + amount ) - playerData.get( material ) );
        }
        return 0;

    }

}
