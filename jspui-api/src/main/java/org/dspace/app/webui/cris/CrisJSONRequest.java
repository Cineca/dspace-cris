package org.dspace.app.webui.cris;

import flexjson.JSONSerializer;
import it.cilea.osd.jdyna.web.json.BoxJSON;
import it.cilea.osd.jdyna.web.json.TabJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dspace.app.webui.json.JSONRequest;
import org.dspace.authorize.AuthorizeException;
import org.dspace.core.Context;

public class CrisJSONRequest extends JSONRequest {
	@Override
	public void doJSONRequest(Context context, HttpServletRequest req,
			HttpServletResponse resp) throws AuthorizeException, IOException {

		List<TabJSON> tabs = new ArrayList<TabJSON>();
		TabJSON tab1 = new TabJSON();
		tab1.setTitle("Personal Information");
		tab1.setShortName("information");
		tab1.setId(100);
		tab1.setBoxes(new ArrayList<BoxJSON>());

		TabJSON tab2 = new TabJSON();
		tab2.setTitle("Other");
		tab2.setShortName("otherinfo");
		tab2.setId(250);
		tab2.setBoxes(new ArrayList<BoxJSON>());

		TabJSON tab3 = new TabJSON();
		tab3.setTitle("Test");
		tab3.setShortName("test");
		tab3.setId(302);
		tab3.setBoxes(new ArrayList<BoxJSON>());

		BoxJSON box1 = new BoxJSON();
		box1.setCollapsed(false);
		box1.setId(50);
		box1.setShortName("namecard");
		box1.setTitle("Name Card");

		BoxJSON box2 = new BoxJSON();
		box2.setCollapsed(true);
		box2.setId(200);
		box2.setShortName("education");
		box2.setTitle("Education");

		BoxJSON box3 = new BoxJSON();
		box3.setCollapsed(false);
		box3.setId(250);
		box3.setShortName("media");
		box3.setTitle("Media Contact");

		tab1.getBoxes().add(box1);
		tab1.getBoxes().add(box2);
		tab2.getBoxes().add(box3);
		tabs.add(tab1);
		tabs.add(tab2);
		tabs.add(tab3);
		JSONSerializer serializer = new JSONSerializer();
		serializer.rootName("navigation");
		serializer.exclude("class");
		serializer.deepSerialize(tabs, resp.getWriter());
	}
}
