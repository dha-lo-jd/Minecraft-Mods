package org.lo.d.site.mods.kawo_commons;

import org.lo.d.site.AbstractThymlPage;
import org.lo.d.site.Content;
import org.lo.d.site.Section;
import org.lo.d.site.SimpleSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class KawoCommons extends AbstractThymlPage {

	private static final Section[] sections = SimpleSection.newSimpleSections("index", "updates", "description",
			"download");

	@Autowired
	@Qualifier("kawo_commons.updates")
	private Content updates;

	public KawoCommons() {
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
