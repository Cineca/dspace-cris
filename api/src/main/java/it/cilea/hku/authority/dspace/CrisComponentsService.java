package it.cilea.hku.authority.dspace;

import it.cilea.osd.jdyna.components.IComponent;

import java.util.Map;

public class CrisComponentsService
{
    private Map<String, IComponent> components;

    public void setComponents(Map<String, IComponent> components)
    {
        this.components = components;
    }

    public Map<String, IComponent> getComponents()
    {
        return components;
    }
    
    
}
