package org.lo.d.site.mods.mod_hk;

import org.lo.d.site.AbstractModContent;
import org.springframework.stereotype.Component;

@Component("mod_hk.mods")
public class Mods extends AbstractModContent {

	@Override
	protected String getModFileName() {
		return "EntityMode_HouseKeeper";
	}

	@Override
	protected String getModName() {
		return "HouseKeeper";
	}

}
