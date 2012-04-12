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

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

public class LootFilter$PlayerListener implements Listener
{

    Logger mcLogger;

    public LootFilter$PlayerListener(Logger mcLogger)
    {
        this.mcLogger = mcLogger;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPickup(PlayerPickupItemEvent ev)
    {
        if( ev.isCancelled() ) return;
        if( !ev.getPlayer().hasPermission( "lootfilter.ignore" ) ) return;

        int pickupAmount = LootManager.getPickupAmount( ev.getPlayer(), ev.getItem().getItemStack().getType(), ev.getItem().getItemStack().getAmount() );
        if( pickupAmount == -1 ) return;
        if( pickupAmount >= 0 )
        {
            ev.setCancelled( true );
            return;
        }

            /*
        Item item = ev.getItem().getWorld().spawn( ev.getItem().getLocation(), Item.class );
        item.setPickupDelay( 3*20 );
        item.setItemStack( new ItemStack( ev.getItem().getItemStack() ) );
        item.getItemStack().setAmount( ev.getItem().getItemStack().getAmount() - pickupAmount );
        ev.getItem().getItemStack().setAmount( pickupAmount );  */

    }

}
