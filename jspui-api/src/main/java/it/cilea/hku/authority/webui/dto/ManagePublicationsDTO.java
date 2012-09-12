/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 * 
 * http://www.dspace.org/license/
 * 
 * The document has moved 
 * <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>
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
