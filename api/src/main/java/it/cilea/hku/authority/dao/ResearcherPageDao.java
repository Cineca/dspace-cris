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
package it.cilea.hku.authority.dao;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.osd.common.dao.PaginableObjectDao;

import java.util.Date;
import java.util.List;

/**
 * This interface define the methods available to retrieve ResearcherPage
 * 
 * @author cilea
 * 
 */
public interface ResearcherPageDao extends
        PaginableObjectDao<ResearcherPage, Integer>
{
    public ResearcherPage uniqueBySourceID(String staffNo);

    public List<ResearcherPage> findAllResearcherPageByStatus(Boolean status);

    public List<ResearcherPage> findAllResearcherByName(String name);

    public long count();

    public long countAllResearcherByName(String name);

    public long countAllResearcherByNameExceptResearcher(String name, Integer id);

    public List<ResearcherPage> findAllResearcherByField(Integer id);

    public List<ResearcherPage> findAllResearcherByNamesTimestampLastModified(
            Date nameTimestampLastModified);

    public List<ResearcherPage> findAllResearcherInDateRange(Date start,
            Date end);

    public List<ResearcherPage> findAllResearcherByCreationDateBefore(Date end);

    public List<ResearcherPage> findAllResearcherByCreationDateAfter(Date start);

    public List<ResearcherPage> findAllNextResearcherByIDStart(Integer start);

    public List<ResearcherPage> findAllPrevResearcherByIDEnd(Integer end);

    public List<ResearcherPage> findAllResearcherInIDRange(Integer start,
            Integer end);

    public List<ResearcherPage> findAllNextResearcherBySourceIDStart(String start);

    public List<ResearcherPage> findAllPrevResearcherBySourceIDEnd(String end);

    public List<ResearcherPage> findAllResearcherInSourceIDRange(String start,
            String end);
    
    public List<RPProperty> findAnagraficaByRPID(Integer id);

    public ResearcherPage uniqueResearcherPageByEPersonId(Integer id);
}
