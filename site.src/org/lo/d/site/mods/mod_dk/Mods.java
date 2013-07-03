package org.lo.d.site.mods.mod_dk;

import org.lo.d.site.AbstractModContent;
import org.springframework.stereotype.Component;

@Component("mod_dk.mods")
public class Mods extends AbstractModContent {

	@Override
	protected String getModFileName() {
		return "EntityMode_DoorKeeper";
	}

	@Override
	protected String getModName() {
		return "DoorKeeper";
	}

}
