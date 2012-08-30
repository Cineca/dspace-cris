/**
 * <!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
 * <html><head>
 * <title>301 Moved Permanently</title>
 * </head><body>
 * <h1>Moved Permanently</h1>
 * <p>The document has moved <a href="https://svn.duraspace.org/dspace/licenses/LICENSE_HEADER">here</a>.</p>
 * </body></html>
 */
package it.cilea.hku.authority.dao;

import java.util.Date;
import java.util.List;

import it.cilea.hku.authority.model.ResearcherPage;
import it.cilea.hku.authority.model.dynamicfield.RPAdditionalFieldStorage;
import it.cilea.hku.authority.model.dynamicfield.RPProperty;
import it.cilea.osd.common.dao.PaginableObjectDao;

/**
 * This interface define the methods available to retrieve ResearcherPage
 * 
 * @author cilea
 * 
 */
public interface ResearcherPageDao extends
        PaginableObjectDao<ResearcherPage, Integer>
{
    public ResearcherPage uniqueResearcherPageByStaffNo(String staffNo);

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

    public List<ResearcherPage> findAllNextResearcherByStaffNoStart(String start);

    public List<ResearcherPage> findAllPrevResearcherByStaffNoEnd(String end);

    public List<ResearcherPage> findAllResearcherInStaffNoRange(String start,
            String end);
    
    public List<RPProperty> findAnagraficaByRPID(Integer id);
}
