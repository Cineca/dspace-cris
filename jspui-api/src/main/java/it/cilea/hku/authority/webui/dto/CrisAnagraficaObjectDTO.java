package it.cilea.hku.authority.webui.dto;

import it.cilea.hku.authority.model.ACrisObject;
import it.cilea.osd.jdyna.dto.AnagraficaObjectAreaDTO;

public class CrisAnagraficaObjectDTO extends AnagraficaObjectAreaDTO 
{

    private Boolean status;
    private String sourceID;
    
    public CrisAnagraficaObjectDTO(ACrisObject object)
    {
        super();
        setSourceID(object.getSourceID());
        setStatus(object.getStatus());
    }

    public Boolean getStatus()
    {
        return status;
    }

    public void setStatus(Boolean status)
    {
        this.status = status;
    }

    public String getSourceID()
    {
        return sourceID;
    }

    public void setSourceID(String souceID)
    {
        this.sourceID = souceID;
    }
}
