package org.lo.d.site.mods.mod_hk;

import java.nio.file.Path;
import java.util.List;

import org.lo.d.site.AbstractThymlPage;
import org.lo.d.site.Content;
import org.lo.d.site.Section;
import org.lo.d.site.SimpleSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.google.common.collect.Lists;

@Controller
public class ModHk extends AbstractThymlPage {

	private static final Section[] sections = SimpleSection.newSimpleSections("index", "updates", "description",
			"download");

	@Autowired
	@Qualifier("mod_hk.updates")
	private Content updates;

	@Autowired
	@Qualifier("mod_hk.mods")
	private Mods mods;

	public ModHk() {
		super("index", sections);
	}

	public Content getMods() {
		return mods;
	}

	@Override
	public Iterable<Path> getResources() {
		List<Path> list = Lists.newArrayList();
		list.addAll(mods.getResources());
		return list;
	}

	public Content getUpdates() {
		return updates;
	}

	@Override
	public String outputName() {
		return "index";
	}

}
