package org.lo.d.site.mods.kawo_commons;

import org.lo.d.site.AbstractModContent;
import org.springframework.stereotype.Component;

@Component("kawo_commons.mods")
public class Mods extends AbstractModContent {

	@Override
	protected String getModFileName() {
		return "EntityMode_Kawo_LMM_Extension";
	}

	@Override
	protected String getModName() {
		return "LMM_Extension";
	}

}
