package org.dspace.app.cris.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SourceReference
{
    /** Cris internal unique identifier, must be null */
    @Column(nullable=true)
    private String sourceID;
    @Column(nullable=true)
    private String sourceRef;
    
    public String getSourceID()
    {
        return sourceID;
    }
    public void setSourceID(String sourceID)
    {
        this.sourceID = sourceID;
    }
    public String getSourceRef()
    {
        return sourceRef;
    }
    public void setSourceRef(String sourceRef)
    {
        this.sourceRef = sourceRef;
    }
    
}
