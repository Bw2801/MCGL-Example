package de.bwueller.mcgl.example;

import de.bwueller.mcgl.Chain;
import de.bwueller.mcgl.Generator;
import de.bwueller.mcgl.command.CommandType;
import de.bwueller.mcgl.constant.Item;
import de.bwueller.mcgl.entity.Entities;
import de.bwueller.mcgl.entity.EntityType;
import de.bwueller.mcgl.nbt.NBTObject;

/**
 * The player teleports to any location an arrow he shoots lands at.
 */
public class WarpArrowExample extends Chain {

    public WarpArrowExample() {
        super(CommandType.REPEAT);
        run();
    }
    
    private void run() {
        Entities arrow = Entities.getSingle(EntityType.Arrow);
        Entities player = Entities.Player;

        pushExecutionAs(player.withMinScore("useBow", 1));
        {
            addTag(arrow.InRadius(null, 2).withTag("!teleport"), "teleport");
            giveItem(player, Item.ARROW);
            resetScore(player, "useBow");
        }
        popExecution();

        pushExecutionAs(arrow.withTag("teleport"), NBTObject.InGround);
        {
            teleportToEntity(player, arrow.InRadius(null, 0));
            kill(arrow.InRadius(null, 0));
        }
        popExecution();

        Generator.generateOneClickCommands(getCommands());
        
        // The following output will be displayed in the console by <code>generateOneClickCommands()</code>.
        // NOTE: create the scoreboard useBow of type stat.useItem.minecraft.bow in order to use this.
        //
        // !! MAKE SURE THERE ARE LEAST 12 BLOCKS OF AIR ABOVE THE COMMAND BLOCK !!
        // 1. Copy the top most command and paste it in a command block.
        // 2. Power the command block and wait until the commandblocks have been spawned.
        // ---
        // summon FallingSand ~ ~1 ~ {Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Passengers:[{Block:"redstone_block",Time:1s,id:"FallingSand"}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"fill ~-1 ~-11 ~ ~-1 ~ ~ redstone_block"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"fill ~-1 ~-10 ~ ~ ~2 ~ air"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~10.0 ~-10.0 ~0.0 minecraft:chain_command_block 3 replace {auto:1s,Command:\"scoreboard players tag @e[c=1,type=Arrow,tag=teleport] remove MCGL_EXECUTING {inGround:1b}\",x:8s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~9.0 ~-9.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"execute @e[c=1,type=Arrow,tag=MCGL_EXECUTING] ~0.0 ~0.0 ~0.0 kill @e[c=1,type=Arrow,r=0]\",x:7s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~8.0 ~-8.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"execute @e[c=1,type=Arrow,tag=MCGL_EXECUTING] ~0.0 ~0.0 ~0.0 tp @p @e[c=1,type=Arrow,r=0]\",x:6s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~7.0 ~-7.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"scoreboard players tag @e[c=1,type=Arrow,tag=teleport] add MCGL_EXECUTING {inGround:1b}\",x:5s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~6.0 ~-6.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"scoreboard players tag @p[score_useBow_min=1] remove MCGL_EXECUTING {}\",x:4s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~5.0 ~-5.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"execute @p[tag=MCGL_EXECUTING,score_useBow_min=1] ~0.0 ~0.0 ~0.0 scoreboard players reset @p useBow\",x:3s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~4.0 ~-4.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"execute @p[tag=MCGL_EXECUTING,score_useBow_min=1] ~0.0 ~0.0 ~0.0 give @p minecraft:arrow 1 0 {}\",x:2s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~3.0 ~-3.0 ~0.0 minecraft:chain_command_block 5 replace {auto:1s,Command:\"execute @p[tag=MCGL_EXECUTING,score_useBow_min=1] ~0.0 ~0.0 ~0.0 scoreboard players tag @e[c=1,type=Arrow,r=2,tag=!teleport] add teleport {}\",x:1s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"command_block",Time:1s,id:"FallingSand",TileEntityData:{Command:"setblock ~2.0 ~-2.0 ~0.0 minecraft:repeating_command_block 5 replace {auto:0s,Command:\"scoreboard players tag @p[score_useBow_min=1] add MCGL_EXECUTING {}\",x:0s,y:0s,z:0s,id:\"Control\",SuccessCount:0s,TrackOutput:0b}"}}],Block:"stone",Time:1s}
        // ---
    }
}
