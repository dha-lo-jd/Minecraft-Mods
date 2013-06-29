package org.lo.d.site.mods.mod_hk;

import org.lo.d.site.AbstractThymlPage;
import org.lo.d.site.Content;
import org.lo.d.site.Section;
import org.lo.d.site.SimpleSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class ModHk extends AbstractThymlPage {

	private static final Section[] sections = SimpleSection.newSimpleSections("index", "updates", "description",
			"download");

	@Autowired
	@Qualifier("mod_hk.updates")
	private Content updates;

	public ModHk() {
		super("index", sections);
	}

	public Content getUpdates() {
		return updates;
	}

	@Override
	public String outputName() {
		return "index";
	}

}
