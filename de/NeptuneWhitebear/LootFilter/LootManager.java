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

public class LootManager
{

    public static LootManager instance;
    private static HashMap<Player,HashMap<Material,Integer>> playerData;

    public LootManager()
    {
        instance = this;
    }

    public static HashMap<Material,Integer> getPlayerData(Player player)
    {
        if( playerData.containsKey( player ) )
        {
            return playerData.get( player );
        }
        return null;
    }

    private static void newPlayerData(Player player)
    {
        //TODO load from file
        playerData.put( player, new HashMap<Material, Integer>() );
    }

    public static void setPlayerData(Player player, Material material, Integer amount)
    {
        if( !playerData.containsKey( player ))
        {
            newPlayerData(player);
            //playerData.put( player, new HashMap<Material, Integer>() );
        }

        HashMap<Material, Integer> playerData = getPlayerData( player );
        playerData.put( material, amount );
    }

    public static int getPickupAmount( Player player, Material material, Integer amount )
    {
        HashMap<Material, Integer> playerData = getPlayerData( player );
        if( !playerData.containsKey( material ) ) return -1;
        if( playerData.get( material ) == 0 ) return 0;
        int playerAmount = 0;
        for( ItemStack itemStack : player.getInventory().getContents())
        {
            if( itemStack.getType() == material)
            {
                playerAmount += itemStack.getAmount();
            }
        }
        if( playerAmount + amount < playerData.get( material ) )
            return -1;
        if( playerAmount + 1 < playerData.get( material )  )
        {
            return amount - ( (playerAmount + amount) - playerData.get( material ) );
        }
        return 0;

    }

}
