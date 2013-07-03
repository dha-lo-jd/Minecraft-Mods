package org.lo.d.site.mods.mod_accounter;

import org.lo.d.site.AbstractModContent;
import org.springframework.stereotype.Component;

@Component("mod_accounter.mods")
public class Mods extends AbstractModContent {

	@Override
	protected String getModFileName() {
		return "EntityMode_Accounter";
	}

	@Override
	protected String getModName() {
		return "Accounter";
	}

}
