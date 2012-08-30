/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.webui.dto;

import org.dspace.content.Item;

public class ManagePublicationsDTO
{
    private int[] selected;
    private int[] unlinked;
    private int[] active;
    private int[] hided;
    public int[] getSelected()
    {
        return selected;
    }
    public void setSelected(int[] selected)
    {
        this.selected = selected;
    }
    public int[] getUnlinked()
    {
        return unlinked;
    }
    public void setUnlinked(int[] unlinked)
    {
        this.unlinked = unlinked;
    }
    public int[] getActive()
    {
        return active;
    }
    public void setActive(int[] active)
    {
        this.active = active;
    }
    public int[] getHided()
    {
        return hided;
    }
    public void setHided(int[] hided)
    {
        this.hided = hided;
    }
    
    
}
