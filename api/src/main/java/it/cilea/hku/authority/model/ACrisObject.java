package it.cilea.hku.authority.model;

import it.cilea.osd.common.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ACrisObject implements UUIDSupport, Identifiable
{
    /** Cris internal unique identifier, must be null */
    @Column(nullable = true, unique = true)
    private String sourceID;
    

    @Column(nullable = false, unique = true)
    private String uuid;
    
    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setSourceID(String sourceID)
    {
        this.sourceID = sourceID;
    }

    public String getSourceID()
    {
        return sourceID;
    }

    public abstract String getPublicPath();
}
